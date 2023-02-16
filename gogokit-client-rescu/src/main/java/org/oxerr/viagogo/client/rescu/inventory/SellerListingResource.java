package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;
import java.time.Instant;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

@Path("/{version}")
public interface SellerListingResource {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_Get">Get a seller listing</a>
	 *
	 * @param listingId the listing ID.
	 * @return the seller listing of the specified listing ID.
	 */
	@GET
	@Path("/sellerlistings/{listingId}")
	SellerListing getSellerListing(@PathParam("listingId") Long listingId);

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_GetSellerListings">List seller listings</a>
	 *
	 * <p>List seller listings for the authenticated user.</p>
	 *
	 * @param eventId
	 * @param requestedEventId
	 * @param page Specifies which page of data to retrieve.
	 * @param pageSize Set custom page sizes on response.
	 * @param updatedSince Filters the response to only return items that have been updated since the given timestamp
	 * @param sort
	 * Determines the ordering of items.
	 * A comma-separated string containing
	 * {@code available_tickets}, {@code created_at}, {@code event_date},
	 * {@code event_name}, {@code expiration_date}, {@code price},
	 * {@code resource_version}, {@code orticket_availability_date}.
	 * @return seller listings
	 * @throws ViagogoException indicates business exception
	 * @throws IOException indicates IO exception
	 */
	@GET
	@Path("/sellerlistings")
	PagedResource<SellerListing> getSellerListings(
		@QueryParam("event_id") Long eventId,
		@QueryParam("requested_event_id") String requestedEventId,
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort
	) throws ViagogoException, IOException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_CreateListingForRequestedEvent">Create a seller listing for a requested event</a>
	 *
	 * @param newSellerListing PostRequestedEventSellerListingRequest
	 * @return {@link SellerListing}
	 */
	@POST
	@Path("/sellerlistings")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SellerListing createListingForRequestedEvent(CreateSellerListingRequest newSellerListing) throws ViagogoException, IOException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_GetByExternalListingId">Get a seller listing by external ID</a>
	 *
	 * @param externalListingId the external listing ID.
	 * @return the seller listing.
	 */
	@GET
	@Path("/externalsellerlistings/{externalListingId}")
	SellerListing getSellerListingByExternalId(@PathParam("externalListingId") String externalListingId);

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_DeleteListingByExternalListingId">Delete a seller listing by external ID</a>
	 *
	 * <p>Delete a seller listing by identifier that has been assigned to the listing
	 * in an external inventory management system.</p>
	 *
	 * @param externalId An identifier that has been assigned to the listing in an
	 *                   external inventory management system.
	 */
	@DELETE
	@Path("/externalsellerlistings/{externalId}")
	void deleteListingByExternalListingId(@PathParam("externalId") String externalId) throws IOException;

}
