package org.oxerr.viagogo.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BarcodeInformation implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * An ordinal number for a seat.
	 */
	private Long seatOrdinal;

	/**
	 * Seat number.
	 */
	private String seat;

	/**
	 * Row of ticket.
	 */
	private String row;

	/**
	 * Status of the barcode. Can be {@code Received} or {@code FailedValidation}
	 */
	private String status;

	/**
	 * SHA256-hashed barcode(s) for this seat.
	 */
	private String[] barcodeValuesSha256Hashed;

	public Long getSeatOrdinal() {
		return seatOrdinal;
	}

	public void setSeatOrdinal(Long seatOrdinal) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getBarcodeValuesSha256Hashed() {
		return barcodeValuesSha256Hashed;
	}

	public void setBarcodeValuesSha256Hashed(String[] barcodeValuesSha256Hashed) {
		this.barcodeValuesSha256Hashed = barcodeValuesSha256Hashed;
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
		BarcodeInformation rhs = (BarcodeInformation) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
