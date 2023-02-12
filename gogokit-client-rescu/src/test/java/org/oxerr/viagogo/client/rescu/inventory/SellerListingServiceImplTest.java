package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.SelectableChannel;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.EventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.VenueRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

class SellerListingServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetSellerListings() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();
		var r = new SellerListingRequest();
		r.setPageSize(1);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		var sellerListings = client.getSellerListingService().getSellerListings(r);
		stopWatch.stop();
		assertNotNull(sellerListings);

		log.info("[{}]Total items: {}", stopWatch, sellerListings.getTotalItems());

		for (SellerListing l : sellerListings.getItems()) {
			log.info(l.getSelf().getHref());
		}
	}

	@Test
	@Disabled("Token is required")
	void testCreateListingForRequestedEvent() throws IOException {
		var client = RescuViagogoClientTest.getClient();

		// given
		var cslr = new CreateSellerListingRequest();
		cslr.setTicketPrice(Money.of("5000", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "5"));
		cslr.setTicketType("TicketMasterMobile");
		cslr.setSplitType("any");
		cslr.setNumberOfTickets(3); // Seat names: 1, 3, 5
		cslr.setExternalId("1");
		cslr.setNotes("test");

		cslr.setEvent(new EventRequest("Hamilton (NY)", Instant.parse("2023-06-22T00:00:00Z")));

		String venueName = "Richard Rodgers Theatre";
		String venueCity = "New York";

		cslr.setVenue(new VenueRequest(venueName, venueCity));

		// when: create
		SellerListing sl1 = client.getSellerListingService().createListingForRequestedEvent(cslr);

		// then
		assertNotNull(sl1);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(0, new BigDecimal("5000").compareTo(sl1.getTicketPrice().getAmount()));

		// when: get
		var sellerListing1 = client.getSellerListingService().getSellerListing(sl1.getId());

		// then
		assertEquals(0, new BigDecimal("5000").compareTo(sellerListing1.getTicketPrice().getAmount()));
		assertEquals("S1", sellerListing1.getSeating().getSection());
		assertEquals("R1", sellerListing1.getSeating().getRow());
		assertEquals("1", sellerListing1.getSeating().getSeatFrom());
		assertEquals("5", sellerListing1.getSeating().getSeatTo());
		assertEquals("Ticketmaster Mobile Ticket", sellerListing1.getTicketType().getName());
		assertEquals("Any", sellerListing1.getSplitType().getType());
		assertEquals(3, sellerListing1.getNumberOfTickets().intValue());
		assertEquals("1", sellerListing1.getExternalId());
		assertNotNull(sellerListing1.getEvent());

		// given
		cslr.setTicketPrice(Money.of("5001", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "3"));
		cslr.setNumberOfTickets(2); // Seat names: 1, 3

		// when: create again
		SellerListing sl2 = client.getSellerListingService().createListingForRequestedEvent(cslr);

		// then
		assertNotNull(sl2);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(sl1.getId(), sl2.getId());
		assertEquals(0, new BigDecimal("5001").compareTo(sl2.getTicketPrice().getAmount()));

		// when: get again
		var sellerListing2 = client.getSellerListingService().getSellerListing(sl1.getId());

		// then
		assertEquals(0, new BigDecimal("5001").compareTo(sellerListing2.getTicketPrice().getAmount()));
		assertEquals("S1", sellerListing2.getSeating().getSection());
		assertEquals("R1", sellerListing2.getSeating().getRow());
		assertEquals("1", sellerListing2.getSeating().getSeatFrom());
		assertEquals("3", sellerListing2.getSeating().getSeatTo());
		assertEquals("Ticketmaster Mobile Ticket", sellerListing2.getTicketType().getName());
		assertEquals("Any", sellerListing2.getSplitType().getType());
		assertEquals(2, sellerListing2.getNumberOfTickets().intValue());
		assertEquals("1", sellerListing2.getExternalId());
		assertNotNull(sellerListing2.getEvent());

		// when: delete
		client.getSellerListingService().deleteListingByExternalListingId("1");
	}

	@Test
	@Disabled("Delete all seller listings")
	void testDeleteAll() throws IOException {
		var client = RescuViagogoClientTest.getClient();

		PagedResource<SellerListing> all;

		Set<String> externalIds = new HashSet<>();

		Integer page = 1;
		Integer pageSize = 10_000;

		do {
			try {
				var r = new SellerListingRequest();
				r.setPage(page);
				r.setPageSize(pageSize);
				all = client.getSellerListingService().getSellerListings(r);
				page++;
			} catch (IOException e) {
				log.warn("Read all seller listings failed: {}", e.getMessage());
				all = null;
				continue;
			}

			assertNotNull(all);

			externalIds.addAll(all.getItems().stream().map(SellerListing::getExternalId).collect(Collectors.toList()));
			log.info("total items: {}, externalIds.size: {}", all.getTotalItems(), externalIds.size());
		} while (all == null || all.getItems().size() > 0);

		var executorService = Executors.newFixedThreadPool(100);

		for (String externalId : externalIds) {
			log.trace("Deleting {}", externalId);

			executorService.execute(() -> {
				try {
					client.getSellerListingService().deleteListingByExternalListingId(externalId);
				} catch (IOException e) {
					log.warn("Delete {} failed: {}", externalId, e.getMessage());
				}
			});
		}

		executorService.shutdown();

		List<Runnable> tasksNeverCommencedExecution = null;

		try {
			if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
				tasksNeverCommencedExecution = executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			tasksNeverCommencedExecution = executorService.shutdownNow();
		}

		if (tasksNeverCommencedExecution != null) {
			log.warn("{} task(s) that never commenced execution.", tasksNeverCommencedExecution.size());
		}
	}

}
