package org.oxerr.viagogo.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.rescu.impl.ResCUViagogoClientTest;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingForRequestedEventRequest;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.EventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.VenueRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

@Disabled("Token is required")
class SellerListingServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final SellerListingService sellerListingService
		= ResCUViagogoClientTest.getClient().getSellerListingService();

	@Test
	@Disabled("Token is required")
	void testGetSellerListing() {
		long listingId = 0L;
		SellerListing sellerListing = this.sellerListingService.getSellerListing(listingId).get();
		assertNotNull(sellerListing);
		log.info("{}", ToStringBuilder.reflectionToString(sellerListing, ToStringStyle.MULTI_LINE_STYLE));
	}

	@Test
	@Disabled("Token is required")
	void testGetSellerListingsRecentUpdates() {
		Instant updatedSince = Instant.now().minus(1, ChronoUnit.HOURS);
		PagedResource<SellerListing> recentUpdates = this.sellerListingService.getSellerListingsRecentUpdates(updatedSince);
		log.info("total items: {}", recentUpdates.getTotalItems());

		for (SellerListing item : recentUpdates.getItems()) {
			log.info(
				"external ID: {}, created at: {}, updated at: {}",
				item.getExternalId(),
				item.getCreatedAt(),
				item.getUpdatedAt()
			);
		}
	}

	@Test
	@Disabled("Token is required")
	void testGetSellerListings() {
		SellerListingRequest r = new SellerListingRequest();
		r.setPageSize(10_000); // seems the maximum page size is 10_000
		r.setSort(SellerListingRequest.Sort.EVENT_DATE);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		PagedResource<SellerListing> sellerListings = sellerListingService.getSellerListings(r);
		stopWatch.stop();
		assertNotNull(sellerListings);

		log.info("[{}]Total items: {}", stopWatch, sellerListings.getTotalItems());

		log.info("Next link: {}", sellerListings.getNextLink());

		OffsetDateTime now = OffsetDateTime.now();
		for (SellerListing l : sellerListings.getItems()) {
			log.info("{} event start date: {}({}), created at: {}, expires at: {}",
				l.getSelfLink().getHref(),
				l.getEvent().getStartDate().atZoneSameInstant(ZoneId.systemDefault()),
				Duration.between(now, l.getEvent().getStartDate()),
				l.getCreatedAt(),
				l.getExpiresAt().atZone(ZoneId.systemDefault())
			);
		}
	}

	@Test
	@Disabled("Token is required")
	void testGetAllSellerListingsOfOneEvent() {
		long eventId = 150471162L;
		List<SellerListing> sellerListings = sellerListingService.getAllSellerListings(eventId);
		assertNotNull(sellerListings);
		List<SellerListing> listings = sellerListings.stream()
			.sorted((a, b) -> a.getSeating().compareTo(b.getSeating())).collect(Collectors.toList());
		int i = 0;
		for (SellerListing listing : listings) {
			print(++i, listing);
		}
	}

	@Test
	@Disabled("Token is required")
	void testCreateListingForRequestedEvent() {
		// given
		final String externalId = "1";

		CreateSellerListingForRequestedEventRequest cslr = new CreateSellerListingForRequestedEventRequest();
		cslr.setTicketPrice(Money.of("5000", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "5"));
		cslr.setTicketType("TicketMasterMobile");
		cslr.setSplitType("any");
		cslr.setNumberOfTickets(3); // Seat names: 1, 3, 5
		cslr.setExternalId(externalId);
		cslr.setNotes("test");

		cslr.setEvent(new EventRequest("Hamilton (NY)", OffsetDateTime.parse("2023-06-22T00:00:00Z").toLocalDateTime()));

		String venueName = "Richard Rodgers Theatre";
		String venueCity = "New York";

		cslr.setVenue(new VenueRequest(venueName, venueCity));

		// when: create
		SellerListing sl1 = sellerListingService.createListingForRequestedEvent(cslr);

		// then
		assertNotNull(sl1);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(0, new BigDecimal("5000").compareTo(sl1.getTicketPrice().getAmount()));

		// when: get
		SellerListing sellerListing1 = sellerListingService.getSellerListing(sl1.getId()).get();

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
		assertEquals("2023-06-22T00:00Z", sellerListing1.getEvent().getStartDate().toString());

		// given
		cslr.setTicketPrice(Money.of("5001", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "3"));
		cslr.setNumberOfTickets(2); // Seat names: 1, 3

		// when: create again
		SellerListing sl2 = sellerListingService.createListingForRequestedEvent(cslr);

		// then
		assertNotNull(sl2);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(sl1.getId(), sl2.getId());
		assertEquals(0, new BigDecimal("5001").compareTo(sl2.getTicketPrice().getAmount()));

		// when: get again
		SellerListing sellerListing2 = sellerListingService.getSellerListing(sl1.getId()).get();

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
		sellerListingService.deleteListingByExternalListingId("1");
	}


	@Test
	@Disabled("Token is required")
	void testCreateListing() {
		// given
		final String externalId = "1";

		Long eventId1 = 151369736L;
		Long eventId2 = 151369737L;

		CreateSellerListingRequest cslr = new CreateSellerListingRequest();
		cslr.setTicketPrice(Money.of("5000", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "5"));
		cslr.setTicketType("TicketMasterMobile");
		cslr.setSplitType("Any");
		cslr.setNumberOfTickets(3); // Seat names: 1, 3, 5
		cslr.setExternalId(externalId);
		cslr.setNotes("test");

		// when: create
		SellerListing sl1 = sellerListingService.createListing(eventId1, cslr);

		// then
		assertNotNull(sl1);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(0, new BigDecimal("5000").compareTo(sl1.getTicketPrice().getAmount()));

		// when: get
		SellerListing sellerListing1 = sellerListingService.getSellerListing(sl1.getId()).get();

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
		assertEquals(eventId1, sellerListing1.getEvent().getId());
		assertEquals("2023-05-03T20:00-07:00", sellerListing1.getEvent().getStartDate().toString());

		// given
		cslr.setTicketPrice(Money.of("5001", "USD"));
		cslr.setSeating(new Seating("S1", "R1", "1", "3"));
		cslr.setNumberOfTickets(2); // Seat names: 1, 3

		// when: create again
		SellerListing sl2 = sellerListingService.createListing(eventId2, cslr);

		// then
		assertNotNull(sl2);
		log.info("SellerListing ID: {}", sl1.getId());
		assertEquals(sl1.getId(), sl2.getId());
		assertEquals(0, new BigDecimal("5001").compareTo(sl2.getTicketPrice().getAmount()));

		// when: get again
		SellerListing sellerListing2 = sellerListingService.getSellerListing(sl2.getId()).get();

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
		assertEquals(eventId1, sellerListing2.getEvent().getId()); // event ID keeps eventId1, so it does not support to change event ID.
		assertEquals("2023-05-03T20:00-07:00", sellerListing1.getEvent().getStartDate().toString());

		// when: delete
		sellerListingService.deleteListingByExternalListingId("1");
	}

	@Test
	@Disabled("Token is required")
	void testGetSellerListingByExternalListingId() {
		String externalListingId = "1626048103687655433";
		SellerListing sellerListing = sellerListingService.getSellerListingByExternalId(externalListingId).get();
		sellerListing.getSeating();
		this.print(1, sellerListing);
	}

	@Test
	@Disabled("Delete all seller listings")
	void testDeleteAll() {
		PagedResource<SellerListing> all;

		Set<String> externalIds = new HashSet<>();

		Integer page = 1;
		Integer pageSize = 10_000;

		do {
			try {
				SellerListingRequest r = new SellerListingRequest();
				r.setPage(page);
				r.setPageSize(pageSize);
				all = sellerListingService.getSellerListings(r);
				page++;
			} catch (Exception e) {
				log.warn("Read all seller listings failed: {}", e.getMessage());
				all = null;
				continue;
			}

			assertNotNull(all);

			externalIds.addAll(all.getItems().stream().map(SellerListing::getExternalId).collect(Collectors.toList()));
			log.info("total items: {}, externalIds.size: {}", all.getTotalItems(), externalIds.size());
		} while (all == null || all.getItems().size() > 0);

		ExecutorService executorService = Executors.newFixedThreadPool(100);

		for (String externalId : externalIds) {
			log.trace("Deleting {}", externalId);

			executorService.execute(() -> {
				try {
					sellerListingService.deleteListingByExternalListingId(externalId);
				} catch (Exception e) {
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

	private void print(int num, SellerListing listing) {
		Event event = listing.getEvent();
		Seating seating = listing.getSeating();

		log.info(
			"{} {}({})\t{} | Row {} | Seat {}-{}\t{}\t{}\tE{} {}@{}",
			() -> StringUtils.leftPad(String.valueOf(num), 2),
			listing::getId,
			listing::getExternalId,
			() -> StringUtils.leftPad(seating.getSection(), 5),
			() -> StringUtils.leftPad(seating.getRow(), 2),
			seating::getSeatFrom,
			seating::getSeatTo,
			() -> StringUtils.leftPad(listing.getNumberOfTickets().toString(), 2),
			listing::getTicketPrice,
			event::getId,
			event::getName,
			event::getStartDate
		);
	}

}
