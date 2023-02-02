package org.oxerr.viagogo.client.inventory;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.oxerr.viagogo.model.ViagogoException;
import org.oxerr.viagogo.model.request.NewSellerListing;
import org.oxerr.viagogo.model.response.SellerListing;

@Path("/{version}")
public interface SellerListingsClient {

	/**
	 * Create a seller listing for a requested event
	 * 
	 * @param newSellerListing
	 * @return
	 */
	@POST
	@Path("/sellerlistings")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SellerListing create(NewSellerListing newSellerListing) throws ViagogoException, IOException;

	/**
	 * <a href=
	 * "https://developer.viagogo.net/api-reference/inventory#operation/SellerListings_DeleteListingByExternalListingId">Delete
	 * a seller listing by external ID</a>
	 *
	 * Delete a seller listing by identifier that has been assigned to the listing
	 * in an external inventory management system.
	 *
	 * @param externalId An identifier that has been assigned to the listing in an
	 *                   external inventory management system.
	 */
	@DELETE
	@Path("/externalsellerlistings/{externalId}")
	void delete(@PathParam("externalId") String externalId) throws IOException;

}
