package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;
import java.time.Instant;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.request.inventory.CreateSellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	PagedResource<SellerEvent> getSellerEvents(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort
	) throws IOException, ViagogoException;

	/**
	 * Creates seller event.
	 *
	 * @param r the {@link CreateSellerEventRequest}
	 * @return the seller event.
	 * @throws IOException indicates I/O exception
	 * @throws ViagogoException indicates business exception
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SellerEvent createSellerEvent(CreateSellerEventRequest r) throws IOException, ViagogoException;

}
