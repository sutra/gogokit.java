package org.oxerr.viagogo.client.inventory;

import java.io.IOException;
import java.time.Instant;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.oxerr.viagogo.model.ViagogoException;
import org.oxerr.viagogo.model.request.NewSellerListing;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.SellerListing;

@Path("/{version}")
public interface SellerListingsClient {

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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	PagedResource<SellerListing> getAll(
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
	@Consumes(MediaType.APPLICATION_JSON)
	SellerListing create(@BeanParam NewSellerListing newSellerListing) throws ViagogoException, IOException;

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
	void delete(@PathParam("externalId") String externalId) throws IOException;

}
