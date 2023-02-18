package org.oxerr.viagogo.client.rescu.sale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.oxerr.viagogo.model.response.sale.Sale;

@Path("/{version}")
public interface SaleResource {

	@GET
	@Path("/sales/{saleId}")
	Sale getSale(@PathParam("saleId") Integer saleId);

}
