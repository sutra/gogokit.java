package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EventRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The name of the event.
	 */
	private String name;

	/**
	 * The date when the event starts.
	 */
	private LocalDateTime startDate;

	/**
	 * True if the event start and end date have been confirmed;
	 * Otherwise, false.
	 */
	private Boolean dateConfirmed;

	/**
	 * Additional notes on the event.
	 */
	private String note;

	public EventRequest() {
	}

	public EventRequest(String name, LocalDateTime startDate) {
		this.name = name;
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Boolean getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Boolean dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
		if (!(obj instanceof EventRequest)) {
			return false;
		}
		EventRequest rhs = (EventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
