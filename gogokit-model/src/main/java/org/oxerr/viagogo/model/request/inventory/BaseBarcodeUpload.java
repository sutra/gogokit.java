package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseBarcodeUpload implements Serializable {

	private static final long serialVersionUID = 2023021801L;

	private Integer seatOrdinal;

	private String seat;

	private String row;

	public Integer getSeatOrdinal() {
		return seatOrdinal;
	}

	public void setSeatOrdinal(Integer seatOrdinal) {
		this.seatOrdinal = seatOrdinal;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
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
		if (!(obj instanceof BaseBarcodeUpload)) {
			return false;
		}
		BaseBarcodeUpload rhs = (BaseBarcodeUpload) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
