package org.oxerr.viagogo.client.rescu.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.request.catalog.EventRequest;
import org.oxerr.viagogo.model.request.catalog.SearchEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;

import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.Status;
import com.evanlennick.retry4j.config.RetryConfig;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;

import io.openapitools.jackson.dataformat.hal.HALLink;

class EventServiceTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetEvents() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();

		// when
		PagedResource<Event> events = client.getEventService().getEvents(new EventRequest());

		// then
		assertNotNull(events);
		log.info("{}", ToStringBuilder.reflectionToString(events, ToStringStyle.MULTI_LINE_STYLE));

		log.info("events.self: {}", events.getSelfLink().getHref());
		log.info("events.next: {}", events.getNextLink().getHref());

		assertEquals("https://api.viagogo.net/catalog/events?page_size=500&sort=resource_version", events.getSelfLink().getHref());

		assertEquals(0, events.getDeletedItems().size());
		assertEquals(500, events.getItems().size());
		var event = events.getItems().get(0);
		log.info("event.id: {}", event.getId());
		log.info("event.name: {}", event.getName());
		log.info("event.startDate: {}", event.getStartDate());
		log.info("event.type: {}", event.getType());
		log.info("event.status: {}", event.getStatus());
		log.info("event.webpage: {}", event.getWebPageLink().getHref());

		assertEquals(8988375L, event.getId());
		assertEquals("ABBARAMA: The Ultimate Abba Tribute Experience", event.getName());
		assertEquals("2023-02-25T19:30:00+11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
			.withZone(ZoneId.of("Pacific/Ponape")).format(event.getStartDate()));
		assertEquals("Main", event.getType());
		assertEquals("Normal", event.getStatus());
		assertEquals("https://www.stubhub.com/a-tribute-to-abba-southbank-tickets-2-25-2023/event/132533943/", event.getWebPageLink().getHref());

		// when
		events = client.getEventService().getEvents(events.getNextLink());

		// then
		assertNotNull(events);
		log.info("events.self: {}", events.getSelfLink().getHref());
		log.info("events.next: {}", events.getSelfLink().getHref());

		assertEquals(0, events.getDeletedItems().size());
		assertEquals(500, events.getItems().size());
		event = events.getItems().get(0);
		log.info("event.id: {}", event.getId());
		log.info("event.name: {}", event.getName());
		log.info("event.startDate: {}", event.getStartDate());
		log.info("event.type: {}", event.getType());
		log.info("event.status: {}", event.getStatus());
		log.info("event.webpage: {}", event.getWebPageLink().getHref());
	}

	@Test
	@Disabled("Token is required")
	void testGetAllEvents() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();

		AtomicLong counter = new AtomicLong();

		EventRequest eventRequest = new EventRequest();
		eventRequest.setPageSize(1_000);

		RetryConfig config = new RetryConfigBuilder()
			.retryOnSpecificExceptions(IOException.class)
			.withMaxNumberOfTries(10)
			.withDelayBetweenTries(30, ChronoUnit.SECONDS)
			.withExponentialBackoff()
			.build();

		var events = client.getEventService().getEvents(eventRequest);

		while(events.getNextLink() != null) {
			log.info("events.self: {}", Optional.ofNullable(events.getSelfLink()).map(HALLink::getHref).orElse(null));
			log.info("events.first: {}", Optional.ofNullable(events.getFirstLink()).map(HALLink::getHref).orElse(null));
			log.info("events.prev: {}", Optional.ofNullable(events.getPrevLink()).map(HALLink::getHref).orElse(null));
			log.info("events.next: {}", Optional.ofNullable(events.getNextLink()).map(HALLink::getHref).orElse(null));
			log.info("events.last: {}", Optional.ofNullable(events.getLastLink()).map(HALLink::getHref).orElse(null));
			long count = counter.addAndGet(events.getItems().size());
			log.info("count: {}", count);

			var next = events.getNextLink();
			Callable<PagedResource<Event>> callable = () -> {
				return client.getEventService().getEvents(next);
			};

			try {
				@SuppressWarnings("unchecked")
				Status<PagedResource<Event>> status = new CallExecutorBuilder<>()
					.config(config)
					.build()
					.execute(callable);
				status.getResult();
				events = status.getResult(); // the result of the callable logic, if it returns one
			} catch (RetriesExhaustedException ree) {
				// the call exhausted all tries without succeeding
				throw ree;
			} catch (UnexpectedException ue) {
				// the call threw an unexpected exception
				throw ue;
			}
		}

	}

	@Test
	@Disabled("Token is required")
	void testSearchEvents() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();
		var q = "Penn & Teller";
		var dateLocal = Instant.parse("2023-02-05T05:00:00.000Z");
		SearchEventRequest r = new SearchEventRequest();
		r.setQ(q);
		r.setDateLocal(dateLocal);
		PagedResource<Event> events = client.getEventService().searchEvents(r);
		assertNotNull(events);
		assertEquals(1, events.getTotalItems());
		assertEquals("https://api.viagogo.net/catalog/events/search?q=Penn%20%26%20Teller&dateLocal=2023-02-05T05%3A00%3A00Z&page=1&page_size=500", events.getSelfLink().getHref());
		Event event = events.getItems().get(0);
		assertEquals(150517204L, event.getId());
		assertEquals("Penn and Teller", event.getName());
	}

}
