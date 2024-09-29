package org.oxerr.viagogo.client.cached.redisson.inventory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.SeatingDetail;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

class ListingsTest {

	@Test
	void testIsSame_CreateSellerListingRequest_SellerListing() {
		CreateSellerListingRequest r = new CreateSellerListingRequest();
		r.setNumberOfTickets(1);
		r.setTicketPrice(Money.of("1", "GBP"));
		r.setSeating(new Seating());

		SellerListing l = new SellerListing();
		l.setNumberOfTickets(1);
		l.setTicketPrice(Money.of("1.0", "GBP"));
		l.setSeating(new SeatingDetail());

		assertTrue(Listings.isSame(r, l));
	}

	@Test
	void testIsSame_Seating() {
		Seating seating = new Seating();
		seating.setSection("section");
		seating.setRow("row");
		seating.setSeatFrom("1");
		seating.setSeatTo("2");

		SeatingDetail seatingDetail = new SeatingDetail();
		seatingDetail.setSection("section");
		seatingDetail.setRow("row");
		seatingDetail.setSeatFrom("1");
		seatingDetail.setSeatTo("2");

		assertTrue(Listings.isSame(seating, seatingDetail));
	}

}
