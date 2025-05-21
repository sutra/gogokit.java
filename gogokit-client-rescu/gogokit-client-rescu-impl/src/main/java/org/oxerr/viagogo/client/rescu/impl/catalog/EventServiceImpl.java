package org.oxerr.viagogo.client.rescu.impl.catalog;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.Optional;

import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.client.rescu.resource.catalog.EventResource;
import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

public class EventServiceImpl implements EventService {

	private final EventResource eventResource;

	public EventServiceImpl(EventResource eventResource) {
		this.eventResource = eventResource;
	}

	@Override
	public PagedResource<Event> getEvents(EventRequest eventRequest) {
		return this.eventResource.getEvents(
			eventRequest.getPage(),
			eventRequest.getPageSize(),
			eventRequest.getUpdatedSince(),
			eventRequest.getSort(),
			eventRequest.getMinResourceVersion(),
			eventRequest.getCountryCode(),
			eventRequest.getLatitude(),
			eventRequest.getLongitude(),
			eventRequest.getMaxDistanceInMeters(),
			eventRequest.getGenreId()
		);
	}

	@Override
	public Optional<Event> getEventByExternalEventId(String platform, Long externalEventId) {
		try {
			return Optional.ofNullable(this.eventResource.getEventByExternalEventId(platform, externalEventId));
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public Optional<Event> getEvent(Long eventId) {
		try {
			return Optional.ofNullable(this.eventResource.getEvent(eventId));
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public PagedResource<Event> searchEvents(SearchEventRequest searchEventRequest) {
		return this.eventResource.searchEvents(
			searchEventRequest.getQ(),
			searchEventRequest.getDateLocal(),
			searchEventRequest.getPage(),
			searchEventRequest.getPageSize(),
			searchEventRequest.getUpdatedSince(),
			searchEventRequest.getSort(),
			searchEventRequest.getMinResourceVersion(),
			searchEventRequest.getCountryCode(),
			searchEventRequest.getLatitude(),
			searchEventRequest.getLongitude(),
			searchEventRequest.getMaxDistanceInMeters(),
			searchEventRequest.getGenreId()
		);
	}

}
