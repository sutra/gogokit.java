package org.oxerr.viagogo.client.inventory;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.oxerr.viagogo.model.request.inventory.CreateSellerListingForRequestedEventRequest;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

import io.openapitools.jackson.dataformat.hal.HALLink;

public interface SellerListingService {

	/**
	 * Get a seller listing
	 *
	 * @param listingId the listing ID.
	 * @return the seller listing of the specified listing ID.
	 * @throws IOException indicates any I/O exception.
	 */
	Optional<SellerListing> getSellerListing(Long listingId) throws IOException;

	/**
	 * List seller listings (recent updates)
	 *
	 * <p>List seller listings for the authenticated user
	 * that have been created or updated over a certain period of time.</p>
	 *
	 * @param updatedSince Filters the response to only return items
	 * that have been updated since the given timestamp
	 * @return recent updates
	 * @throws IOException indicates any I/O exception.
	 */
	PagedResource<SellerListing> getSellerListingsRecentUpdates(Instant updatedSince) throws IOException;

	/**
	 * List seller listings.
	 *
	 * @param sellerListingRequest the request.
	 * @return the seller listings.
	 * @throws IOException indicates any I/O exception.
	 */
	PagedResource<SellerListing> getSellerListings(SellerListingRequest sellerListingRequest) throws IOException;

	default PagedResource<SellerListing> getSellerListings(HALLink link) throws IOException {
		return this.getSellerListings(SellerListingRequest.from(link));
	}

	/**
	 * List all seller listings.
	 *
	 * @param sellerListingRequest the request.
	 * @return the seller listings.
	 * @throws IOException indicates any I/O exception.
	 */
	default List<SellerListing> getAllSellerListings(SellerListingRequest sellerListingRequest) throws IOException {
		PagedResource<SellerListing> pagedSellerListings = this.getSellerListings(sellerListingRequest);
		List<SellerListing> sellerListings = new ArrayList<>(pagedSellerListings.getTotalItems());
		sellerListings.addAll(pagedSellerListings.getItems());

		while (pagedSellerListings.getNextLink() != null) {
			pagedSellerListings = this.getSellerListings(pagedSellerListings.getNextLink());
			sellerListings.addAll(pagedSellerListings.getItems());
		}

		return sellerListings;
	}

	/**
	 * List all seller listings of the specified event ID.
	 *
	 * @param eventId the event ID.
	 * @return the seller listings.
	 * @throws IOException indicates any I/O exception.
	 */
	default List<SellerListing> getAllSellerListings(Long eventId) throws IOException {
		SellerListingRequest r = new SellerListingRequest(eventId);
		r.setPage(1);
		return getAllSellerListings(r);
	}

	/**
	 * Create a seller listing for a requested event.
	 *
	 * @param createSellerListingForRequestedEventRequest the request.
	 * @return the created seller listing.
	 * @throws IOException indicates any I/O exception.
	 */
	SellerListing createListingForRequestedEvent(
		CreateSellerListingForRequestedEventRequest
			createSellerListingForRequestedEventRequest) throws IOException;

	/**
	 * Create a seller listing.
	 *
	 * @param eventId the event ID.
	 * @param createSellerListingRequest the request.
	 * @return the created seller listing.
	 * @throws IOException indicates any I/O exception.
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
	 * @throws IOException indicates any I/O exception.
	 */
	Optional<SellerListing> getSellerListingByExternalId(String externalListingId) throws IOException;

	/**
	 * Delete a seller listing by external ID.
	 * <p>
	 * Delete a seller listing by identifier that has been assigned to the
	 * listing in an external inventory management system.
	 * </p>
	 * 
	 * @param externalId the external ID.
	 * @throws IOException indicates any I/O exception.
	 */
	void deleteListingByExternalListingId(String externalId) throws IOException;

}
