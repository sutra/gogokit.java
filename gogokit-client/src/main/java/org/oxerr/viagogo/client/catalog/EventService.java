package org.oxerr.viagogo.client.catalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

import io.openapitools.jackson.dataformat.hal.HALLink;

public interface EventService {

	PagedResource<Event> getEvents(EventRequest eventRequest) throws IOException;

	default PagedResource<Event> getEvents(HALLink link) throws IOException {
		return this.getEvents(EventRequest.from(link));
	}

	PagedResource<Event> searchEvents(SearchEventRequest searchEventRequest) throws IOException;

	default PagedResource<Event> searchEvents(HALLink link) throws IOException {
		return this.searchEvents(SearchEventRequest.from(link));
	}

	default List<Event> searchAll(SearchEventRequest searchEventRequest, Predicate<Event> predicate) throws IOException {
		var pagedEvents = this.searchEvents(searchEventRequest);
		var matched = new ArrayList<Event>(pagedEvents.getTotalItems());
		matched.addAll(pagedEvents.getItems().stream().filter(predicate).collect(Collectors.toList()));

		while (pagedEvents.getNextLink() != null) {
			pagedEvents = this.searchEvents(pagedEvents.getNextLink());
			matched.addAll(this.findAll(pagedEvents, predicate));
		}

		return matched;
	}

	default Optional<Event> searchFirst(SearchEventRequest searchEventRequest, Predicate<Event> predicate) throws IOException {
		var pagedEvents = this.searchEvents(searchEventRequest);
		var matched = this.findFirst(pagedEvents, predicate);

		while (matched.isEmpty() && pagedEvents.getNextLink() != null) {
			pagedEvents = this.searchEvents(pagedEvents.getNextLink());
			matched = findFirst(pagedEvents, predicate);
		}

		return matched;
	}

	default Optional<Event> findFirst(PagedResource<Event> pagedEvents, Predicate<Event> predicate) {
		for (var event : pagedEvents.getItems()) {
			if (predicate.test(event)) {
				return Optional.of(event);
			}
		}
		return Optional.empty();
	}

	default List<Event> findAll(PagedResource<Event> pagedEvents, Predicate<Event> predicate) {
		return pagedEvents.getItems().stream().filter(predicate).collect(Collectors.toList());
	}

}
