package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;
import java.time.Instant;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/{version}")
public interface SellerListingResource {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_Get">Get a seller listing</a>
	 *
	 * @param listingId the listing ID.
	 * @return the seller listing of the specified listing ID.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/sellerlistings/{listingId}")
	SellerListing getSellerListing(@PathParam("listingId") Long listingId) throws IOException, ViagogoException;

	/**
	 * List seller listings (recent updates)
	 *
	 * <p>List seller listings for the authenticated user
	 * that have been created or updated over a certain period of time.</p>
	 *
	 * @param updatedSince Filters the response to only return items
	 * that have been updated since the given timestamp
	 * @return recent updates
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/sellerlistings/recentupdates")
	PagedResource<SellerListing> getSellerListingsRecentUpdates(@QueryParam("updated_since") Instant updatedSince) throws IOException, ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_GetSellerListings">List seller listings</a>
	 *
	 * <p>List seller listings for the authenticated user.</p>
	 *
	 * @param eventId the event ID.
	 * @param requestedEventId the requested event ID.
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
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
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
	) throws IOException, ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_CreateListingForRequestedEvent">Create a seller listing for a requested event</a>
	 *
	 * @param createSellerListingRequest PostRequestedEventSellerListingRequest
	 * @return {@link SellerListing}
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@POST
	@Path("/sellerlistings")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SellerListing createListingForRequestedEvent(CreateSellerListingRequest createSellerListingRequest) throws IOException, ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/Sales_Delete">Create a seller listing</a>
	 *
	 * @param eventId the event ID.
	 * @param createSellerListingRequest the request.
	 * @return the created seller listing.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@POST
	@Path("/events/{eventId}/sellerlistings")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SellerListing createListing(@PathParam("eventId") Long eventId, CreateSellerListingRequest createSellerListingRequest) throws IOException, ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_GetByExternalListingId">Get a seller listing by external ID</a>
	 *
	 * @param externalListingId the external listing ID.
	 * @return the seller listing.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/externalsellerlistings/{externalListingId}")
	SellerListing getSellerListingByExternalId(@PathParam("externalListingId") String externalListingId) throws IOException, ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_DeleteListingByExternalListingId">Delete a seller listing by external ID</a>
	 *
	 * <p>Delete a seller listing by identifier that has been assigned to the listing
	 * in an external inventory management system.</p>
	 *
	 * @param externalId An identifier that has been assigned to the listing in an
	 *                   external inventory management system.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@DELETE
	@Path("/externalsellerlistings/{externalId}")
	void deleteListingByExternalListingId(@PathParam("externalId") String externalId) throws IOException, ViagogoException;

}
