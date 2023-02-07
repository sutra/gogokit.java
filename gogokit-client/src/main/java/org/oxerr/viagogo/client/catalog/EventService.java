package org.oxerr.viagogo.client.catalog;

import java.io.IOException;

import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

import io.openapitools.jackson.dataformat.hal.HALLink;

public interface EventService {

	PagedResource<Event> getEvents(EventRequest r) throws IOException;

	default PagedResource<Event> getEvents(HALLink link) throws IOException {
		return this.getEvents(EventRequest.from(link));
	}

	PagedResource<Event> searchEvents(SearchEventRequest r) throws IOException;

}
