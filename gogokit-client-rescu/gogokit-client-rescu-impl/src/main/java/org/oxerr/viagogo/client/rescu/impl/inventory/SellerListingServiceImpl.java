package org.oxerr.viagogo.client.rescu.impl.inventory;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.time.Instant;
import java.util.Optional;

import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.client.rescu.resource.inventory.SellerListingResource;
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
	public Optional<SellerListing> getSellerListing(Long listingId) {
		try {
			return Optional.ofNullable(this.sellerListingsResource.getSellerListing(listingId));
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public PagedResource<SellerListing> getSellerListingsRecentUpdates(Instant updatedSince) {
		return this.sellerListingsResource.getSellerListingsRecentUpdates(updatedSince);
	}

	@Override
	public PagedResource<SellerListing> getSellerListings(SellerListingRequest sellerListingRequest) {
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
	public SellerListing createListingForRequestedEvent(CreateSellerListingForRequestedEventRequest createSellerListingForRequestedEventRequest) {
		return this.sellerListingsResource.createListingForRequestedEvent(createSellerListingForRequestedEventRequest);
	}

	@Override
	public SellerListing createListing(Long eventId, CreateSellerListingRequest createSellerListingRequest) {
		return this.sellerListingsResource.createListing(eventId, createSellerListingRequest);
	}

	@Override
	public Optional<SellerListing> getSellerListingByExternalId(String externalListingId) {
		try {
			return Optional.ofNullable(this.sellerListingsResource.getSellerListingByExternalId(externalListingId));
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public void deleteListingByExternalListingId(String externalId) {
		try {
			this.sellerListingsResource.deleteListingByExternalListingId(externalId);
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() != NOT_FOUND.getStatusCode()) {
				throw e;
			}
		}
	}

}
