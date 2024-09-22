package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
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

	private final int pageSize;

	private final RetryConfiguration retryConfig;

	@Deprecated(since = "5.0.0", forRemoval = true)
	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		this(sellerListingsService, redissonClient, keyPrefix, executor, new ListingConfiguration(create, true, true), 10_000, new RetryConfiguration());
	}

	/**
	 * Constructs with default {@link ListingConfiguration} and default {@link RetryConfiguration}.
	 *
	 * @param sellerListingsService the seller listings service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 *
	 * @since 5.0.0
	 */
	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(sellerListingsService, redissonClient, keyPrefix, executor, new ListingConfiguration(), 10_000, new RetryConfiguration());
	}

	/**
	 * Constructs with specified {@link ListingConfiguration} and specified {@link RetryConfiguration}.
	 *
	 * @param sellerListingsService the seller listings service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 * @param pageSize the page size when do check.
	 * @param retryConfiguration the retry configuration.
	 *
	 * @since 5.0.0
	 */
	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		ListingConfiguration listingConfiguration,
		int pageSize,
		RetryConfiguration retryConfiguration
	) {
		super(redissonClient, keyPrefix, executor, listingConfiguration);
		this.sellerListingsService = sellerListingsService;
		this.pageSize = pageSize;
		this.retryConfig = retryConfiguration;
	}

	@Override
	protected boolean shouldCreate(
		@Nonnull ViagogoEvent event,
		@Nonnull ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		boolean shouldCreate = super.shouldCreate(event, listing, cachedListing);
		return shouldCreate || (cachedListing != null && !listing.getViagogoEventId().equals(cachedListing.getViagogoEventId()));
	}

	@Override
	protected boolean shouldUpdate(
		@Nonnull ViagogoEvent event,
		@Nonnull ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		boolean shouldUpdate = super.shouldUpdate(event, listing, cachedListing);
		return shouldUpdate || (cachedListing != null && !listing.getViagogoEventId().equals(cachedListing.getViagogoEventId()));
	}

	@Override
	protected int getPriority(
		@Nonnull ViagogoEvent event,
		@Nullable ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		if (listing == null || cachedListing == null) {
			return 0;
		}

		int priority = 0;

		var r = listing.getRequest();
		var cr = cachedListing.getRequest();

		priority += Objects.equals(r.getNumberOfTickets(), cr.getNumberOfTickets()) ? 0 : 1;
		priority += Objects.equals(r.getSeating(), cr.getSeating()) ? 0 : 1;
		priority += Objects.equals(r.getNotes(), cr.getNotes()) ? 0 : 1;

		return priority;
	}

	@Override
	protected boolean shouldDelete(
		@Nonnull ViagogoEvent event,
		@Nonnull Set<String> inventoryListingIds,
		@Nonnull String listingId,
		@Nonnull ViagogoCachedListing cachedListing
	) {
		return super.shouldDelete(event, inventoryListingIds, listingId, cachedListing)
			|| !event.getViagogoEventId().equals(cachedListing.getViagogoEventId());
	}

	@Override
	protected void createListing(ViagogoEvent event, ViagogoListing listing) throws IOException {
		this.sellerListingsService.createListing(listing.getViagogoEventId(), listing.getRequest());
	}

	@Override
	protected void deleteListing(ViagogoEvent event, String listingId) throws IOException {
		this.sellerListingsService.deleteListingByExternalListingId(listingId);
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

		// The tasks to delete or update the listings.
		var tasks = Collections.synchronizedList(new ArrayList<CompletableFuture<Void>>());

		// Check the first page.
		var listings = this.check(request(1), externalIds, tasks).join();

		// Check the next page to the last page.
		log.debug("[check] total items: {}, next link: {}, last link: {}",
			listings.getTotalItems(), listings.getNextLink(), listings.getLastLink());

		// When only 1 page left, the next link and last link is null.
		var next = Optional.ofNullable(listings.getNextLink()).map(SellerListingRequest::from);
		var last = Optional.ofNullable(listings.getLastLink()).map(SellerListingRequest::from);

		var checking = new ArrayList<CompletableFuture<PagedResource<SellerListing>>>();

		if (next.isPresent() && last.isPresent()) {
			for(int i = next.get().getPage(); i <= last.get().getPage(); i++) {
				checking.add(this.check(request(i), externalIds, tasks));
			}
		}

		// Wait all checking to complete.
		log.debug("[check] checking size: {}", checking.size());
		CompletableFuture.allOf(checking.toArray(CompletableFuture[]::new)).join();

		// Wait all deleting to complete.
		log.debug("[check] deleting size: {}", tasks.size());
		CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();

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

	/**
	 * Checks the listings of the request.
	 *
	 * @param request the request.
	 * @param externalIds the external IDs in cache.
	 * @param tasks the tasks to delete or update the listings.
	 * @return the page in checking.
	 */
	private CompletableFuture<PagedResource<SellerListing>> check(
		SellerListingRequest request,
		Set<String> externalIds,
		List<CompletableFuture<Void>> tasks
	) {
		return this.<PagedResource<SellerListing>>callAsync(() -> {
			var page = this.getSellerListings(request);
			tasks.addAll(this.check(page, externalIds));
			log.debug("[check] page: {}, deleting size: {}", request.getPage(), tasks.size());
			return page;
		});
	}

	/**
	 * Checks the listings in the page.
	 *
	 * @param page the page.
	 * @param externalIds the external IDs in cache.
	 * @return the tasks to delete or update the listings.
	 */
	private List<CompletableFuture<Void>> check(
		PagedResource<SellerListing> page,
		Set<String> externalIds
	) {
		return page.getItems().stream()
			.filter(listing -> !externalIds.contains(listing.getExternalId()))
			.map(listing -> this.<Void>callAsync(() -> {
				this.sellerListingsService.deleteListingByExternalListingId(listing.getExternalId());
				return null;
			})).collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Gets the seller listings.
	 *
	 * @param request the request.
	 * @return the seller listings.
	 */
	private PagedResource<SellerListing> getSellerListings(SellerListingRequest request) {
		return this.retry(() -> {
			try {
				return this.sellerListingsService.getSellerListings(request);
			} catch (IOException e) {
				throw new RetryableException(e);
			}
		});
	}

	/**
	 * Creates a seller listing request.
	 *
	 * @param page the page.
	 * @return a seller listing request.
	 */
	private SellerListingRequest request(int page) {
		var r = new SellerListingRequest();
		r.setSort(SellerListingRequest.Sort.EVENT_DATE);
		r.setPage(page);
		r.setPageSize(pageSize);
		return r;
	}

	private final Random random = new Random();

	private <T> T retry(Supplier<T> supplier) {
		int attempts = 0;

		T t = null;

		try {
			t = supplier.get();
		} catch (RetryableException e) {
			if (++attempts < retryConfig.getMaxAttempts()) {
				long delay = random.nextInt(retryConfig.getMaxDelay());
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
