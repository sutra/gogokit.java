package org.oxerr.viagogo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatingDetail extends Seating {

	private static final long serialVersionUID = 2023021301L;

	public SeatingDetail() {
	}

	public SeatingDetail(String section, String row, String seatFrom, String seatTo) {
		super(section, row, seatFrom, seatTo);
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
