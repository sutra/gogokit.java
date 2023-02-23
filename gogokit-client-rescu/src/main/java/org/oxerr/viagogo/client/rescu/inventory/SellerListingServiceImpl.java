package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;
import java.time.Instant;

import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingForRequestedEventRequest;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public class SellerListingServiceImpl implements SellerListingService {

	private final SellerListingResource sellerListingsResource;

	public SellerListingServiceImpl(SellerListingResource sellerListingsResource) {
		this.sellerListingsResource = sellerListingsResource;
	}

	@Override
	public SellerListing getSellerListing(Long listingId) throws IOException {
		return this.sellerListingsResource.getSellerListing(listingId);
	}

	@Override
	public PagedResource<SellerListing> getSellerListingsRecentUpdates(Instant updatedSince) throws IOException {
		return this.sellerListingsResource.getSellerListingsRecentUpdates(updatedSince);
	}

	@Override
	public PagedResource<SellerListing> getSellerListings(SellerListingRequest sellerListingRequest) throws IOException {
		return this.sellerListingsResource.getSellerListings(
			sellerListingRequest.getEventId(),
			sellerListingRequest.getRequestedEventId(),
			sellerListingRequest.getPage(),
			sellerListingRequest.getPageSize(),
			sellerListingRequest.getUpdatedSince(),
			sellerListingRequest.getSort()
		);
	}

	@Override
	public SellerListing createListingForRequestedEvent(CreateSellerListingForRequestedEventRequest createSellerListingForRequestedEventRequest) throws IOException {
		return this.sellerListingsResource.createListingForRequestedEvent(createSellerListingForRequestedEventRequest);
	}

	@Override
	public SellerListing createListing(Long eventId, CreateSellerListingRequest createSellerListingRequest) throws IOException {
		return this.sellerListingsResource.createListing(eventId, createSellerListingRequest);
	}

	@Override
	public SellerListing getSellerListingByExternalId(String externalListingId) {
		return this.sellerListingsResource.getSellerListingByExternalId(externalListingId);
	}

	@Override
	public void deleteListingByExternalListingId(String externalId) throws IOException {
		this.sellerListingsResource.deleteListingByExternalListingId(externalId);
	}

}
