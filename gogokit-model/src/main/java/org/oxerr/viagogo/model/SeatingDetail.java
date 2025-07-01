package org.oxerr.viagogo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatingDetail extends Seating {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * (SeatingState (object or null))
	 * This field indicates whether the listing's seating is visible on the
	 * website on the venue map.
	 */
	private SeatingState state;

	public SeatingDetail() {
	}

	public SeatingDetail(String section, String row, String seatFrom, String seatTo) {
		super(section, row, seatFrom, seatTo);
	}

	public SeatingState getState() {
		return state;
	}

	public void setState(SeatingState state) {
		this.state = state;
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
		if (!(obj instanceof SeatingDetail)) {
			return false;
		}
		SeatingDetail rhs = (SeatingDetail) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
