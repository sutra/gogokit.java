package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;
import java.time.Instant;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/{version}")
public interface SaleResource {

	/**
	 * List sales for the authenticated user.
	 *
	 * @return the sales for the authenticated user.
	 */
	@GET
	@Path("/sales")
	PagedResource<Sale> getSales(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort
	) throws IOException, ViagogoException;

	/**
	 * Returns the sale.
	 *
	 * @param saleId the sale ID.
	 * @return the sale.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/sales/{saleId}")
	Sale getSale(@PathParam("saleId") Integer saleId) throws IOException, ViagogoException;

}
