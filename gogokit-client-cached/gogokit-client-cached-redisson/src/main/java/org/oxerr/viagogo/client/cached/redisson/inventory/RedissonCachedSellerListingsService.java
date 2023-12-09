package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
		log.debug("[check]");

		// The external IDs in cache.
		Set<String> externalIds = this.getCacheNamesStream()
			.map(name -> this.getCache(name).keySet().stream())
			.flatMap(Function.identity())
			.collect(Collectors.toUnmodifiableSet());
		log.debug("[check] externalIds.size: {}", externalIds.size());

		var deleting = new ArrayList<CompletableFuture<Void>>();

		// The first page.
		var r = new SellerListingRequest();
		r.setPageSize(10_000);
		r.setSort(SellerListingRequest.Sort.CREATED_AT);

		var listings = this.retry(() -> {
			try {
				return this.sellerListingsService.getSellerListings(r);
			} catch (IOException e) {
				throw new RetryableException(e);
			}
		});
		deleting.addAll(this.check(listings.getItems(), externalIds));
		log.debug("[check] deleting.size: {}", deleting.size());

		// Do until the last page
		while(listings.getNextLink() != null) {
			try {
				listings = this.sellerListingsService.getSellerListings(listings.getNextLink());
			} catch (IOException e) {
				throw new RetryableException(e);
			}
			deleting.addAll(this.check(listings.getItems(), externalIds));
			log.debug("[check] deleting.size: {}", deleting.size());
		}

		// Wait all future to complete.
		CompletableFuture.allOf(deleting.toArray(CompletableFuture[]::new)).join();
	}

	private List<CompletableFuture<Void>> check(List<SellerListing> listings, Set<String> externalIds) {
		return listings.parallelStream()
			.filter(listing -> !externalIds.contains(listing.getExternalId()))
			.map(listing ->
				RedissonCachedListingServiceSupport.<Void>callAsync(() -> {
						this.sellerListingsService.deleteListingByExternalListingId(listing.getExternalId());
						return null;
					},
					this.executor
				)
			)
			.collect(Collectors.toUnmodifiableList());
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
			if (++attempts >= maxAttempts) {
				throw e;
			} else {
				long delay = random.nextLong() * maxDelay;
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
				throw e;
			}
		}

		return t;
	}

	private static class RetryableException extends RuntimeException {

		private static final long serialVersionUID = 2023120801L;

		public RetryableException(Throwable cause) {
			super(cause);
		}

	}

}
