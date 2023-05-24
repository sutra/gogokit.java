package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

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

}
