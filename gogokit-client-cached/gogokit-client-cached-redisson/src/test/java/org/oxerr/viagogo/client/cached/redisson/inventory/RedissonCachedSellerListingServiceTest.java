package org.oxerr.viagogo.client.cached.redisson.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

class RedissonCachedSellerListingServiceTest {

	@Test
	void testGetPriorityViagogoEventViagogoListingViagogoCachedListing() {
		var s = new RedissonCachedSellerListingService(null, null, null, null);

		// same
		assertEquals(0, s.getPriority(newViagogoEvent(), newViagogoListing(1, "A", "A", null), newViagogoCachedListing(1, "A", "A", null)));

		// numberOfTickets and seatTo are different
		assertEquals(2, s.getPriority(newViagogoEvent(), newViagogoListing(1, "A", "A", null), newViagogoCachedListing(2, "A", "B", null)));

		// notes are different
		assertEquals(1, s.getPriority(newViagogoEvent(), newViagogoListing(2, "A", "B", "Notes 1"), newViagogoCachedListing(2, "A", "B", "Notes 2")));
	}

	private ViagogoEvent newViagogoEvent() {
		return new ViagogoEvent("1", null, 1L);
	}

	private ViagogoListing newViagogoListing(Integer numberOfTickets, String seatFrom, String seatTo, String notes) {
		var viagogoEvent = new ViagogoEvent("1", null, 1L);
		var createSellerListingRequest = newCreateSellerListingRequest(numberOfTickets, seatFrom, seatTo, notes);
		return new ViagogoListing("1", viagogoEvent.getMarketplaceEventId(), createSellerListingRequest);
	}

	private ViagogoCachedListing newViagogoCachedListing(Integer numberOfTickets, String seatFrom, String seatTo, String notes) {
		var viagogoEvent = new ViagogoEvent("1", null, 1L);
		var cachedCreateSellerListingRequest = newCreateSellerListingRequest(numberOfTickets, seatFrom, seatTo, notes);
		var cachedViagogoListing = new ViagogoListing("1", viagogoEvent.getMarketplaceEventId(), cachedCreateSellerListingRequest);
		return new ViagogoCachedListing(viagogoEvent, cachedViagogoListing, Status.LISTED);
	}

	private CreateSellerListingRequest newCreateSellerListingRequest(Integer numberOfTickets, String seatFrom, String seatTo, String notes) {
		var createSellerListingRequest = new CreateSellerListingRequest();
		createSellerListingRequest.setNumberOfTickets(numberOfTickets);
		var section = "211";
		var row = "NN";
		var seating = new Seating(section, row, seatFrom, seatTo);
		createSellerListingRequest.setSeating(seating);
		createSellerListingRequest.setNotes(notes);
		return createSellerListingRequest;
	}

}
