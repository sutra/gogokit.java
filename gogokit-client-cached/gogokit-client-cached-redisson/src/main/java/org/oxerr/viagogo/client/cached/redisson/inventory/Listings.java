package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.util.Objects;

import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.SeatingDetail;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

final class Listings {

	private Listings() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(CreateSellerListingRequest r, SellerListing l) {
		return Objects.equals(r.getNumberOfTickets(), l.getNumberOfTickets())
			&& isSame(r.getSeating(), l.getSeating())
			&& Objects.compare(r.getTicketPrice(), l.getTicketPrice(), Money::compareTo) == 0;
	}

	public static boolean isSame(Seating seating, SeatingDetail seatingDetail) {
		return Objects.equals(seating.getSection(), seatingDetail.getSection())
			&& Objects.equals(seating.getRow(), seatingDetail.getRow())
			&& Objects.equals(seating.getSeatFrom(), seatingDetail.getSeatFrom())
			&& Objects.equals(seating.getSeatTo(), seatingDetail.getSeatTo());
	}

}
