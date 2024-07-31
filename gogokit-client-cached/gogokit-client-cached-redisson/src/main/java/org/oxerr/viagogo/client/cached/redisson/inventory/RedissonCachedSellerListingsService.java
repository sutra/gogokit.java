package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.redisson.api.RedissonClient;

public class RedissonCachedSellerListingsService
	extends RedissonCachedListingServiceSupport<String, String, CreateSellerListingRequest, ViagogoListing, ViagogoEvent, ViagogoCachedListing>
	implements CachedSellerListingsService {

	private final Logger log = LogManager.getLogger();

	private final SellerListingService sellerListingsService;

	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		super(redissonClient, keyPrefix, executor, create);
		this.sellerListingsService = sellerListingsService;
	}

	@Override
	protected boolean shouldDelete(
		ViagogoEvent event,
		Set<String> inventoryTicketIds,
		String ticketId,
		ViagogoCachedListing cachedListing
	) {
		return super.shouldDelete(event, inventoryTicketIds, ticketId, cachedListing)
			|| !event.getViagogoEventId().equals(cachedListing.getViagogoEventId());
	}

	@Override
	protected boolean shouldCreate(ViagogoEvent event, ViagogoListing listing, ViagogoCachedListing cachedListing) {
		boolean shouldCreate = super.shouldCreate(event, listing, cachedListing);
		return shouldCreate || !listing.getViagogoEventId().equals(cachedListing.getViagogoEventId());
	}

	@Override
	protected void deleteListing(ViagogoEvent event, String ticketId) throws IOException {
		this.sellerListingsService.deleteListingByExternalListingId(ticketId);
	}

	@Override
	protected void createListing(ViagogoEvent event, ViagogoListing listing) throws IOException {
		this.sellerListingsService.createListing(listing.getViagogoEventId(), listing.getRequest());
	}

	@Override
	protected ViagogoCachedListing toCached(ViagogoEvent event, ViagogoListing listing, Status status) {
		return new ViagogoCachedListing(listing, status);
	}

	@Override
	public void check() {
		log.info("[check] begin.");

		StopWatch stopWatch = StopWatch.createStarted();

		// The external IDs in cache.
		var externalIds = this.getExternalIds();

		var deleting = Collections.synchronizedList(new ArrayList<CompletableFuture<Void>>());

		// Check the first page.
		var listings = this.check(request(1), externalIds, deleting).join();

		// Check the next page to the last page.
		log.debug("[check] total items: {}, next link: {}, last link: {}",
			listings.getTotalItems(), listings.getNextLink(), listings.getLastLink());

		// When only 1 page left, the next link and last link is null.
		var next = Optional.ofNullable(listings.getNextLink()).map(SellerListingRequest::from);
		var last = Optional.ofNullable(listings.getLastLink()).map(SellerListingRequest::from);

		var checking = new ArrayList<CompletableFuture<PagedResource<SellerListing>>>();

		if (next.isPresent() && last.isPresent()) {
			for(int i = next.get().getPage(); i <= last.get().getPage(); i++) {
				checking.add(this.check(request(i), externalIds, deleting));
			}
		}

		// Wait all checking to complete.
		log.debug("[check] checking size: {}", checking.size());
		CompletableFuture.allOf(checking.toArray(CompletableFuture[]::new)).join();

		// Wait all deleting to complete.
		log.debug("[check] deleting size: {}", deleting.size());
		CompletableFuture.allOf(deleting.toArray(CompletableFuture[]::new)).join();

		stopWatch.stop();
		log.info("[check] end. Checked {} items in {}", listings.getTotalItems(), stopWatch);
	}

	private Set<String> getExternalIds() {
		var externalIds = this.getCacheNamesStream()
			.map(name -> this.getCache(name).keySet().stream())
			.flatMap(Function.identity())
			.collect(Collectors.toUnmodifiableSet());
		log.debug("[check] externalIds size: {}", externalIds.size());
		return externalIds;
	}

	private CompletableFuture<PagedResource<SellerListing>> check(
		SellerListingRequest request,
		Set<String> externalIds,
		List<CompletableFuture<Void>> deleting
	) {
		return this.<PagedResource<SellerListing>>callAsync(() -> {
			var page = this.getSellerListings(request);
			deleting.addAll(this.check(page, externalIds));
			log.debug("[check] page: {}, deleting size: {}", request.getPage(), deleting.size());
			return page;
		});
	}

	private List<CompletableFuture<Void>> check(
		PagedResource<SellerListing> page,
		Set<String> externalIds
	) {
		return page.getItems().parallelStream()
			.filter(listing -> !externalIds.contains(listing.getExternalId()))
			.map(listing -> this.<Void>callAsync(() -> {
				this.sellerListingsService.deleteListingByExternalListingId(listing.getExternalId());
				return null;
			})).collect(Collectors.toUnmodifiableList());
	}

	private PagedResource<SellerListing> getSellerListings(SellerListingRequest request) {
		return this.retry(() -> {
			try {
				return this.sellerListingsService.getSellerListings(request);
			} catch (IOException e) {
				throw new RetryableException(e);
			}
		});
	}

	private SellerListingRequest request(int page) {
		var r = new SellerListingRequest();
		r.setSort(SellerListingRequest.Sort.EVENT_DATE);
		r.setPage(page);
		r.setPageSize(10_000);
		return r;
	}

	private final Random random = new Random();

	private <T> T retry(Supplier<T> supplier) {
		int maxAttempts = 10;
		int maxDelay = 100;
		int attempts = 0;

		T t = null;

		try {
			t = supplier.get();
		} catch (RetryableException e) {
			if (++attempts < maxAttempts) {
				long delay = random.nextInt(maxDelay);
				sleep(delay);
			} else {
				log.debug("attempts: {}", attempts);
				throw e;
			}
		}

		return t;
	}

	private void sleep(long millis) {
		if (millis < 0) {
			return;
		}

		log.debug("sleeping {}", millis);
		ThreadUtils.sleepQuietly(Duration.ofMillis(millis));
	}

	private static class RetryableException extends RuntimeException {

		private static final long serialVersionUID = 2023120801L;

		public RetryableException(Throwable cause) {
			super(cause);
		}

	}

}
