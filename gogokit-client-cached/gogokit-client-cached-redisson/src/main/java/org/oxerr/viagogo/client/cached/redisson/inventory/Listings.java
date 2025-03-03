package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.SeatingDetail;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

final class Listings {

	private Listings() {
		throw new AssertionError("No " + this.getClass() + " instances for you!");
	}

	public static boolean isSame(SellerListing l, CreateSellerListingRequest r) {
		return Objects.equals(l.getNumberOfTickets(), r.getNumberOfTickets())
			&& isSame(l.getSeating(), r.getSeating())
			&& Objects.compare(l.getTicketPrice(), r.getTicketPrice(), Money::compareTo) == 0;
	}

	public static boolean isSame(SeatingDetail seatingDetail, Seating seating) {
		return Objects.equals(seatingDetail.getSection(), seating.getSection())
			&& Objects.equals(seatingDetail.getRow(), seating.getRow())
			&& Objects.equals(seatingDetail.getSeatFrom(), seating.getSeatFrom())
			&& Objects.equals(seatingDetail.getSeatTo(), seating.getSeatTo());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Listings)) {
			return false;
		}
		Listings rhs = (Listings) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
