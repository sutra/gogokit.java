package org.oxerr.viagogo.client.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.oxerr.viagogo.model.request.inventory.CreateSellerListingForRequestedEventRequest;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public interface SellerListingService {

	/**
	 * Get a seller listing
	 *
	 * @param listingId the listing ID.
	 * @return the seller listing of the specified listing ID.
	 * @throws IOException indicates IO exception.
	 */
	SellerListing getSellerListing(Long listingId) throws IOException;

	/**
	 * List seller listings.
	 *
	 * @param r the request.
	 * @return the seller listings.
	 * @throws IOException indicates IO exception.
	 */
	PagedResource<SellerListing> getSellerListings(SellerListingRequest sellerListingRequest) throws IOException;

	/**
	 * List all seller listings.
	 *
	 * @param r the request.
	 * @return the seller listings.
	 * @throws IOException indicates IO exception.
	 */
	default List<SellerListing> getAllSellerListings(SellerListingRequest sellerListingRequest) throws IOException {
		List<SellerListing> sellerListings = new ArrayList<>();
		PagedResource<SellerListing> res;
		do {
			res = this.getSellerListings(sellerListingRequest);
			sellerListings.addAll(res.getItems());
			sellerListingRequest.setPage(sellerListingRequest.getPage() + 1);
		} while (res.getNextLink() != null);
		return sellerListings;
	}

	/**
	 * List all seller listings of the specified event ID.
	 *
	 * @param eventId the event ID.
	 * @return the seller listings.
	 * @throws IOException indicates IO exception.
	 */
	default List<SellerListing> getAllSellerListings(Long eventId) throws IOException {
		SellerListingRequest r = new SellerListingRequest(eventId);
		r.setPage(1);
		return getAllSellerListings(r);
	}

	/**
	 * Create a seller listing for a requested event.
	 *
	 * @param r the request.
	 * @return the created seller listing.
	 * @throws IOException indicates IO exception.
	 */
	SellerListing createListingForRequestedEvent(CreateSellerListingForRequestedEventRequest createSellerListingForRequestedEventRequest) throws IOException;

	/**
	 * Create a seller listing.
	 *
	 * @param eventId the event ID.
	 * @param r the request.
	 * @return the created seller listing.
	 * @throws IOException
	 */
	SellerListing createListing(Long eventId, CreateSellerListingRequest createSellerListingRequest) throws IOException;

	/**
	 * Get a seller listing by external ID.
	 * <p>
	 * Get a seller listing by identifier that has been assigned to the listing
	 * in an external inventory management system.
	 * </p>
	 * @param externalListingId the external ID.
	 * @return the seller listing.
	 */
	SellerListing getSellerListingByExternalId(String externalListingId);

	/**
	 * Delete a seller listing by external ID.
	 * <p>
	 * Delete a seller listing by identifier that has been assigned to the
	 * listing in an external inventory management system.
	 * </p>
	 * 
	 * @param externalId the external ID.
	 * @throws IOException indicates IO exception.
	 */
	void deleteListingByExternalListingId(String externalId) throws IOException;

}
