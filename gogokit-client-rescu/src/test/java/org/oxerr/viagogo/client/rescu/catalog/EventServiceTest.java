package org.oxerr.viagogo.client.rescu.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

class EventServiceTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetEvents() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();
		PagedResource<Event> events = client.getEventService().getEvents(new EventRequest());
		assertNotNull(events);
		log.info("{}", ToStringBuilder.reflectionToString(events, ToStringStyle.MULTI_LINE_STYLE));
		assertEquals("https://api.viagogo.net/catalog/events?page_size=500&sort=resource_version", events.getSelf().getHref());
		assertEquals("https://api.viagogo.net/catalog/events?min_resource_version=132719748114&page_size=500&sort=resource_version", events.getNext().getHref());
		assertEquals(0, events.getDeletedItems().size());
		assertEquals(500, events.getItems().size());
		var event = events.getItems().get(0);
		assertEquals(8988375L, event.getId());
		assertEquals("ABBARAMA: The Ultimate Abba Tribute Experience", event.getName());
		assertEquals("2023-02-25T19:30:00+11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
			.withZone(ZoneId.of("Pacific/Ponape")).format(event.getStartDate()));
		assertEquals("Main", event.getType());
		assertEquals("Normal", event.getStatus());
		assertEquals("https://www.stubhub.com/a-tribute-to-abba-southbank-tickets-2-25-2023/event/132533943/", event.getWebpage().getHref());
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
		assertEquals("https://api.viagogo.net/catalog/events/search?q=Penn%20%26%20Teller&dateLocal=2023-02-05T05%3A00%3A00Z&page=1&page_size=500", events.getSelf().getHref());
		Event event = events.getItems().get(0);
		assertEquals(150517204L, event.getId());
		assertEquals("Penn and Teller", event.getName());
	}

}
