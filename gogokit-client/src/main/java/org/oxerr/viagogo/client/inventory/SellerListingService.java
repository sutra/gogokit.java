package org.oxerr.viagogo.client.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public interface SellerListingService {

	PagedResource<SellerListing> getSellerListings(SellerListingRequest r) throws IOException;

	default List<SellerListing> getAllSellerListings(SellerListingRequest r) throws IOException {
		List<SellerListing> sellerListings = new ArrayList<>();
		PagedResource<SellerListing> res;
		do {
			res = this.getSellerListings(r);
			sellerListings.addAll(res.getItems());
			r.setPage(r.getPage() + 1);
		} while (res.getNext() != null);
		return sellerListings;
	}

	default List<SellerListing> getAllSellerListings(Long eventId) throws IOException {
		SellerListingRequest r = new SellerListingRequest(eventId);
		r.setPage(1);
		return getAllSellerListings(r);
	}

	SellerListing createListingForRequestedEvent(CreateSellerListingRequest r) throws IOException;

	void deleteListingByExternalListingId(String externalId) throws IOException;

}
