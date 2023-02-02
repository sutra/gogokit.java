package org.oxerr.viagogo.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Seating {

	private String section;

	private String row;

	private String seatFrom;

	private String seatTo;

	public Seating() {
	}

	public Seating(String section, String row, String seatFrom, String seatTo) {
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
		Seating rhs = (Seating) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
