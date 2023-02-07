package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

class SellerEventServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetSellerEvents() throws IOException {
		var client = RescuViagogoClientTest.getClient();

		PagedResource<SellerEvent> sellerEvents = client.getSellerEventService().getSellerEvents(new SellerEventRequest());
		assertNotNull(sellerEvents);
		log.info("Seller events total items: {}", sellerEvents.getTotalItems());

		sellerEvents.getItems().forEach(sellerEvent -> log.info("{}", ToStringBuilder.reflectionToString(sellerEvent)));
	}

}
