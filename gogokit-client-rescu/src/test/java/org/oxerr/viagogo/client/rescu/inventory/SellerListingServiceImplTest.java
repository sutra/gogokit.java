package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.EventRequest;
import org.oxerr.viagogo.model.request.inventory.VenueRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

class SellerListingServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testCreateListingForRequestedEvent() throws IOException {
		var client = RescuViagogoClientTest.getClient();

		var request = new CreateSellerListingRequest();
		request.setTicketPrice(new Money(new BigDecimal("5000"), "USD", null));
		request.setSeating(new Seating("A", "A", "1", "1"));
		request.setTicketType("TicketMasterMobile");
		request.setSplitType("any");
		request.setNumberOfTickets(1);
		request.setExternalId("1");
		request.setNotes("test");

		request.setEvent(new EventRequest("Hamilton (NY)", Instant.parse("2023-06-22T00:00:00Z")));

		String venueName = "Richard Rodgers Theatre";
		String venueCity = "New York";

		request.setVenue(new VenueRequest(venueName, venueCity));

		SellerListing sl = client.getSellerListingService().createListingForRequestedEvent(request);
		assertNotNull(sl);
		log.info("SellerListing ID: {}", sl.getId());
	}

}
