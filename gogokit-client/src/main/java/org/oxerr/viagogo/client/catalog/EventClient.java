package org.oxerr.viagogo.client.catalog;

import java.time.Instant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.oxerr.viagogo.model.response.Event;
import org.oxerr.viagogo.model.response.PagedResource;

@Path("/catalog/events")
public interface EventClient {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/catalog#operation/Events_GetEvents">List Events</a>
	 *
	 * <p>Lists all events on the viagogo platform. Use this endpoint
	 * to sync the entire viagogo event catalog with your application.</p>
	 *
	 * @param page Specifies which page of data to retrieve.
	 * @param pageSize Set custom page sizes on response.
	 * @param updatedSince Filters the response to only return items that have been updated since the given timestamp
	 * @param sort Determines the ordering of items. A comma-separated string containing {@code resource_version}.
	 * @param minResourceVersion
	 * @param countryCode Filters results to only include events located in the specified country.
	 * @param latitude When provided with longitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param longitude When provided with latitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param maxDistanceInmeters When provided with latitude and longitude filters events returned to ones within the specified distance of the lat/long.
	 * @param genreId Filters results to only include events for the specified genre id.
	 * @return all events on the viagogo platform.
	 */
	@GET
	PagedResource<Event> getAll(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort,
		@QueryParam("min_resource_version") Long minResourceVersion,
		@QueryParam("country_code") String countryCode,
		@QueryParam("latitude") Double latitude,
		@QueryParam("longitude") Double longitude,
		@QueryParam("max_distance_in_meters") Integer maxDistanceInmeters,
		@QueryParam("genre_id") Integer genreId
	);

}
