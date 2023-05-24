package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClient;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.model.request.inventory.CountryRequest;
import org.oxerr.viagogo.model.request.inventory.CreateSellerEventRequest;
import org.oxerr.viagogo.model.request.inventory.EventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.request.inventory.VenueRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

class SellerEventServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetSellerEvents() throws IOException {
		RescuViagogoClient client = RescuViagogoClientTest.getClient();

		PagedResource<SellerEvent> sellerEvents = client.getSellerEventService().getSellerEvents(new SellerEventRequest());
		assertNotNull(sellerEvents);
		log.info("Seller events total items: {}", sellerEvents.getTotalItems());

		sellerEvents.getItems().forEach(sellerEvent -> log.info("ID: {}", sellerEvent.getId()));
	}

	@Test
	@Disabled("Token is required")
	void testCreateSellerEvent() throws IOException {
		RescuViagogoClient client = RescuViagogoClientTest.getClient();

		PagedResource<SellerEvent> sellerEvents = client.getSellerEventService().getSellerEvents(new SellerEventRequest());
		SellerEvent existingSellerEvent = sellerEvents.getItems().get(0);
		assertNotNull(existingSellerEvent);

		EventRequest event = new EventRequest(existingSellerEvent.getName(), existingSellerEvent.getStartDate().toLocalDateTime());
		VenueRequest venue = new VenueRequest(existingSellerEvent.getVenue().getName(), existingSellerEvent.getVenue().getCity());
		CountryRequest country = new CountryRequest(existingSellerEvent.getVenue().getCountry().getCode());

		CreateSellerEventRequest r = new CreateSellerEventRequest(event, venue, country);
		SellerEvent createdSellerEvent = client.getSellerEventService().createSellerEvent(r);
		assertNotNull(createdSellerEvent);

		log.info("Existing seller event ID: {}", existingSellerEvent.getId());
		log.info("Created seller event ID: {}", createdSellerEvent.getId());
	}

}
