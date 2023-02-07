package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;
import java.time.Instant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

@Path("/{version}/sellerevents")
public interface SellerEventResource {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/inventory#operation/SellerEvents_GetSellerEvents">List seller events</a>
	 *
	 * @param page Specifies which page of data to retrieve.
	 * @param pageSize Set custom page sizes on response.
	 * @param updatedSince Filters the response to only return items that have
	 * been updated since the given timestamp
	 * @param sort Determines the ordering of items. A comma-separated string
	 * containing {@code id}, {@code number_of_tickets},
	 * 	{@code orresource_version}.
	 * @return events with listings that belong to the authenticated user.
	 */
	@GET
	PagedResource<SellerEvent> getSellerEvents(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort
	) throws ViagogoException, IOException;

}
