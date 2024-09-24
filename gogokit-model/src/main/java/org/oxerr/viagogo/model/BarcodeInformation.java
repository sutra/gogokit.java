package org.oxerr.viagogo.model;

import java.io.Serializable;
import java.util.List;

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
	 * Barcode(s) values for this seat.
	 */
	private List<String> barcodeValues;

	/**
	 * SHA256-hashed barcode(s) for this seat.
	 */
	private List<String> barcodeValuesSha256Hashed;

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

	public List<String> getBarcodeValues() {
		return barcodeValues;
	}

	public void setBarcodeValues(List<String> barcodeValues) {
		this.barcodeValues = barcodeValues;
	}

	public List<String> getBarcodeValuesSha256Hashed() {
		return barcodeValuesSha256Hashed;
	}

	public void setBarcodeValuesSha256Hashed(List<String> barcodeValuesSha256Hashed) {
		this.barcodeValuesSha256Hashed = barcodeValuesSha256Hashed;
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
		if (!(obj instanceof BarcodeInformation)) {
			return false;
		}
		BarcodeInformation rhs = (BarcodeInformation) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
