package org.oxerr.viagogo.client.rescu.catalog;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

import si.mazi.rescu.HttpStatusIOException;

public class EventServiceImpl implements EventService {

	private final EventResource eventResource;

	public EventServiceImpl(EventResource eventResource) {
		this.eventResource = eventResource;
	}

	@Override
	public PagedResource<Event> getEvents(EventRequest r) throws IOException {
		return this.eventResource.getEvents(
			r.getPage(),
			r.getPageSize(),
			r.getUpdatedSince(),
			r.getSort(),
			r.getMinResourceVersion(),
			r.getCountryCode(),
			r.getLatitude(),
			r.getLongitude(),
			r.getMaxDistanceInMeters(),
			r.getGenreId()
		);
	}

	@Override
	public Optional<Event> getEventByExternalEventId(String platform, Long externalEventId) throws IOException {
		try {
			return Optional.ofNullable(this.eventResource.getEventByExternalEventId(platform, externalEventId));
		} catch (ViagogoException | HttpStatusIOException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public Optional<Event> getEvent(Long eventId) throws IOException {
		try {
			return Optional.ofNullable(this.eventResource.getEvent(eventId));
		} catch (ViagogoException | HttpStatusIOException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public PagedResource<Event> searchEvents(SearchEventRequest r) throws IOException {
		return this.eventResource.searchEvents(
			r.getQ(),
			r.getDateLocal(),
			r.getPage(),
			r.getPageSize(),
			r.getUpdatedSince(),
			r.getSort(),
			r.getMinResourceVersion(),
			r.getCountryCode(),
			r.getLatitude(),
			r.getLongitude(),
			r.getMaxDistanceInMeters(),
			r.getGenreId()
		);
	}

}
