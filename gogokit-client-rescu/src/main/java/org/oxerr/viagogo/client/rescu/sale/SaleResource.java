package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.response.sale.Sale;

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
