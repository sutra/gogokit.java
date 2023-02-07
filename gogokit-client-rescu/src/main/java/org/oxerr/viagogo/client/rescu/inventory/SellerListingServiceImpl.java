package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;

import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.NewSellerListing;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public class SellerListingServiceImpl implements SellerListingService {

	private final SellerListingResource sellerListingsResource;

	public SellerListingServiceImpl(SellerListingResource sellerListingsResource) {
		this.sellerListingsResource = sellerListingsResource;
	}

	@Override
	public PagedResource<SellerListing> getSellerListings(SellerListingRequest r) throws IOException {
		return this.sellerListingsResource.getSellerListings(
			r.getEventId(),
			r.getRequestedEventId(),
			r.getPage(),
			r.getPageSize(),
			r.getUpdatedSince(),
			r.getSort()
		);
	}

	@Override
	public SellerListing createListingForRequestedEvent(NewSellerListing newSellerListing) throws IOException {
		return this.sellerListingsResource.createListingForRequestedEvent(newSellerListing);
	}

	@Override
	public void deleteListingByExternalListingId(String externalId) throws IOException {
		this.sellerListingsResource.deleteListingByExternalListingId(externalId);
	}

}
