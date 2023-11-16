package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;

import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

public class RedissonCachedSellerListingsService
	extends RedissonCachedListingServiceSupport<String, String, CreateSellerListingRequest, ViagogoListing, ViagogoEvent, ViagogoCachedListing>
	implements CachedSellerListingsService {

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

	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create,
		RMapCache<String, ConcurrentMap<String, ViagogoCachedListing>> listingCache
	) {
		super(redissonClient, keyPrefix, executor, create, listingCache);
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

}
