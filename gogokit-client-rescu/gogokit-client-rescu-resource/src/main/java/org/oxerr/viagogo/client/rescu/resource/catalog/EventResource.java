package org.oxerr.viagogo.client.rescu.resource.catalog;

import java.time.Instant;
import java.time.LocalDateTime;

import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/catalog/events")
public interface EventResource {

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
	 * @param minResourceVersion the minimum resource version.
	 * @param countryCode Filters results to only include events located in the specified country.
	 * @param latitude When provided with longitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param longitude When provided with latitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param maxDistanceInmeters When provided with latitude and longitude filters events returned to ones within the specified distance of the lat/long.
	 * @param genreId Filters results to only include events for the specified genre id.
	 * @return all events on the viagogo platform.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	PagedResource<Event> getEvents(
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
	) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/catalog#operation/Events_GetEventByExternalEventId">
	 * Get an event on the viagogo platform using an identifier from an external platform.
	 * </a>
	 *
	 * @param platform The name of the external platform. Can be legacy_stubhub
	 * @param externalEventId The event identifier from the external platform.
	 * @return the event.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/external_mappings/{platform}/{externalEventId}")
	Event getEventByExternalEventId(
		@PathParam("platform") String platform,
		@PathParam("externalEventId") Long externalEventId
	) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/catalog#operation/Events_GetEvent">Get an event</a>
	 * 
	 * <p>Note that the eventID being passed can already be merged with
	 * another event. And in this case, details of the new event will be
	 * returned, if a different eventId is returned then it means that the
	 * event has been merged with the returned event. Also inside _embedded
	 * field of the response, a mapping between the original eventId
	 * and new eventId will be provided.
	 * </p>
	 * @param eventId The event identifier.
	 * @return the event.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/{eventId}")
	Event getEvent(@PathParam("eventId") Long eventId) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/catalog#operation/Events_SearchEvents">Search events</a>
	 *
	 * <p>Search for events on the viagogo platform. Use this endpoint to search the entire viagogo event catalog with your application.</p>
	 *
	 * @param q The query text to be used to match events.
	 * @param dateLocal The specific date of the event, this is optional
	 * @param page Specifies which page of data to retrieve.
	 * @param pageSize Set custom page sizes on response.
	 * @param updatedSince Filters the response to only return items that have been updated since the given timestamp
	 * @param sort Determines the ordering of items. A comma-separated string containing {@code resource_version}.
	 * @param minResourceVersion the minimum resource version.
	 * @param countryCode Filters results to only include events located in the specified country.
	 * @param latitude When provided with longitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param longitude When provided with latitude and distance filters events returned to ones within the specified distance of the lat/long.
	 * @param maxDistanceInMeters When provided with latitude and longitude filters events returned to ones within the specified distance of the lat/long.
	 * @param genreId Filters results to only include events for the specified genre id.
	 * @return events.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/search")
	PagedResource<Event> searchEvents(
		@QueryParam("q") String q,
		@QueryParam("dateLocal") LocalDateTime dateLocal,
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("updated_since") Instant updatedSince,
		@QueryParam("sort") String sort,
		@QueryParam("min_resource_version") Long minResourceVersion,
		@QueryParam("country_code") String countryCode,
		@QueryParam("latitude") Double latitude,
		@QueryParam("longitude") Double longitude,
		@QueryParam("max_distance_in_meters") Integer maxDistanceInMeters,
		@QueryParam("genre_id") Integer genreId
	) throws ViagogoException;

}
