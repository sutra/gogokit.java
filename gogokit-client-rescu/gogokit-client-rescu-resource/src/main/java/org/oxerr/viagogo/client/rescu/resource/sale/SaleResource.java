package org.oxerr.viagogo.client.rescu.resource.sale;

import java.time.Instant;

import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

/**
 * <a href="https://developer.viagogo.net/api-reference/sales">viagogo Sales API</a>.
 *
 * <p>View your sales details and fulfill your sales.</p>
 */
@Path("/{version}")
public interface SaleResource {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/sales#operation/Sales_GetSalesRecentUpdates">List sales (recent updates)</a>.
	 *
	 * <p>List sales for the authenticated user that have been created or updated over a certain period of time.</p>
	 *
	 * @param updatedSince Filters the response to only return items that have been updated since the given timestamp.
	 * @return sales for the authenticated user that have been created or updated over a certain period of time.
	 * @throws ViagogoException indicates business exception.
	 */
	@GET
	@Path("/sales/recentupdates")
	PagedResource<Sale> getSalesRecentUpdates(@QueryParam("updated_since") Instant updatedSince)
		throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/sales#operation/Sales_GetSales">List sales</a>.
	 *
	 * <p>List sales for the authenticated user.</p>
	 *
	 * @param page specifies which page of data to retrieve.
	 * @param pageSize set custom page sizes on response.
	 * @param updatedSince filters the response to only return items
	 * that have been updated since the given timestamp.
	 * @param sort determines the ordering of items.
	 * A comma-separated string containing {@code created_at},
	 * {@code event_date}, {@code inhand_at}, {@code payment_amount},
	 * {@code quantity}, or {@code resource_version}.
	 * @return the sales for the authenticated user.
	 * @throws ViagogoException indicates business exception.
	 */
	@GET
	@Path("/sales")
	PagedResource<Sale> getSales(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort
	) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/sales#operation/Sales_Get">Get a sale</a>.
	 *
	 * @param saleId the sale ID.
	 * @return the sale.
	 * @throws ViagogoException indicates business exception.
	 */
	@GET
	@Path("/sales/{saleId}")
	Sale getSale(@PathParam("saleId") Integer saleId) throws ViagogoException;

}
