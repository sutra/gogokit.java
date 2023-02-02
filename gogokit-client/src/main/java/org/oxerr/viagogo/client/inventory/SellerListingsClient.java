package org.oxerr.viagogo.client.inventory;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{version}/externalsellerlistings")
public interface SellerListingsClient {

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
	@Path("/{externalId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	void delete(@PathParam("externalId") String externalId) throws IOException;

}
