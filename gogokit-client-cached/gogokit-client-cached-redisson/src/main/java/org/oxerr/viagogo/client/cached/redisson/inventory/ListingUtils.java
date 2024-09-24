package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.util.Objects;

import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

final class ListingUtils {

	private ListingUtils() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(CreateSellerListingRequest r, SellerListing l) {
		return Objects.equals(r.getNumberOfTickets(), l.getNumberOfTickets())
			&& Objects.compare(r.getTicketPrice(), l.getTicketPrice(), Money::compareTo) == 0
			&& Objects.equals(r.getSeating(), l.getSeating());
	}

}
