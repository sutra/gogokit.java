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

	/**
	 * List events.
	 *
	 * @param eventRequest the request.
	 * @return the events.
	 * @throws IOException indicates any I/O exception.
	 */
	PagedResource<Event> getEvents(EventRequest eventRequest) throws IOException;

	default PagedResource<Event> getEvents(HALLink link) throws IOException {
		return this.getEvents(EventRequest.from(link));
	}

	/**
	 * Get an event on the viagogo platform using an identifier from an external platform.
	 *
	 * @param platform The name of the external platform.
	 * Can be {@code legacy_stubhub}.
	 * @param externalEventId The event identifier from the external platform.
	 * @return the event.
	 * @throws IOException indicates any I/O exception.
	 */
	Optional<Event> getEventByExternalEventId(String platform, Long externalEventId) throws IOException;

	/**
	 * Get an event.
	 *
	 * @param eventId The event identifier.
	 * @return the event.
	 * @throws IOException indicates any I/O exception.
	 */
	Optional<Event> getEvent(Long eventId) throws IOException;

	/**
	 * Search events.
	 *
	 * @param searchEventRequest the request.
	 * @return the events.
	 * @throws IOException indicates any I/O exception.
	 */
	PagedResource<Event> searchEvents(SearchEventRequest searchEventRequest) throws IOException;

	default PagedResource<Event> searchEvents(HALLink link) throws IOException {
		return this.searchEvents(SearchEventRequest.from(link));
	}

	default List<Event> searchAll(SearchEventRequest searchEventRequest, Predicate<Event> predicate) throws IOException {
		PagedResource<Event> pagedEvents = this.searchEvents(searchEventRequest);
		List<Event> matched = new ArrayList<Event>(pagedEvents.getTotalItems());
		matched.addAll(pagedEvents.getItems().stream().filter(predicate).collect(Collectors.toList()));

		while (pagedEvents.getNextLink() != null) {
			pagedEvents = this.searchEvents(pagedEvents.getNextLink());
			matched.addAll(this.findAll(pagedEvents, predicate));
		}

		return matched;
	}

	default Optional<Event> searchFirst(SearchEventRequest searchEventRequest, Predicate<Event> predicate) throws IOException {
		PagedResource<Event> pagedEvents = this.searchEvents(searchEventRequest);
		Optional<Event> matched = this.findFirst(pagedEvents, predicate);

		while (!matched.isPresent() && pagedEvents.getNextLink() != null) {
			pagedEvents = this.searchEvents(pagedEvents.getNextLink());
			matched = findFirst(pagedEvents, predicate);
		}

		return matched;
	}

	default Optional<Event> findFirst(PagedResource<Event> pagedEvents, Predicate<Event> predicate) {
		for (Event event : pagedEvents.getItems()) {
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
