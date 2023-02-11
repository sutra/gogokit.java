package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
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
