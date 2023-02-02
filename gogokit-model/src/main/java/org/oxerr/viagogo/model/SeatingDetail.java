package org.oxerr.viagogo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatingDetail {

	/**
	 * The section of the ticket(s).
	 */
	private String section;

	/**
	 * The row of the ticket(s).
	 */
	private String row;

	/**
	 * The first in a contiguous block of seats to which the tickets have been allocated.
	 */
	private String seatFrom;

	/**
	 * The last in a contiguous block of seats to which the tickets have been allocated.
	 */
	private String seatTo;

	public SeatingDetail() {
	}

	public SeatingDetail(String section, String row, String seatFrom, String seatTo) {
		this.section = section;
		this.row = row;
		this.seatFrom = seatFrom;
		this.seatTo = seatTo;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getSeatFrom() {
		return seatFrom;
	}

	public void setSeatFrom(String seatFrom) {
		this.seatFrom = seatFrom;
	}

	public String getSeatTo() {
		return seatTo;
	}

	public void setSeatTo(String seatTo) {
		this.seatTo = seatTo;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		SeatingDetail rhs = (SeatingDetail) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
