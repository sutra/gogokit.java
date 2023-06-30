package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.response.sale.Sale;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/{version}")
public interface SaleResource {

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
