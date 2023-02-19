package org.oxerr.viagogo.client.rescu.catalog;

import java.io.IOException;

import org.oxerr.viagogo.client.catalog.EventService;
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
