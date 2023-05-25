package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.Set;

import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.redisson.api.RedissonClient;

public class RedissonCachedSellerListingsService
	extends RedissonCachedListingServiceSupport<CreateSellerListingRequest, ViagogoListing, ViagogoEvent, ViagogoCachedListing>
	implements CachedSellerListingsService {

	private final SellerListingService sellerListingsService;

	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redisson,
		String keyPrefix
	) {
		super(redisson, keyPrefix);
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
	protected void doDelete(String ticketId) throws IOException {
		this.sellerListingsService.deleteListingByExternalListingId(ticketId);
	}

	@Override
	protected boolean shouldCreate(ViagogoEvent event, ViagogoListing listing, ViagogoCachedListing cachedListing) {
		boolean shouldCreate = super.shouldCreate(event, listing, cachedListing);
		return shouldCreate || !listing.getViagogoEventId().equals(cachedListing.getViagogoEventId());
	}

	@Override
	protected void doCreate(ViagogoEvent event, ViagogoListing listing) throws IOException {
		this.sellerListingsService.createListing(listing.getViagogoEventId(), listing.getRequest());
	}

	@Override
	protected ViagogoCachedListing toCached(ViagogoEvent event, ViagogoListing listing, Status status) {
		return new ViagogoCachedListing(listing, status);
	}

}