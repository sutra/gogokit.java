package org.oxerr.viagogo.client.catalog;

import java.io.IOException;

import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

public interface EventService {

	PagedResource<Event> getEvents(EventRequest r) throws IOException;

	PagedResource<Event> searchEvents(SearchEventRequest r) throws IOException;

}
