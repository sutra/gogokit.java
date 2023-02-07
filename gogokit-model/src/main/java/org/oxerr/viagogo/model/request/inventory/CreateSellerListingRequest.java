package org.oxerr.viagogo.model.request.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;

public class CreateSellerListingRequest {

	private Money ticketPrice;

	private Seating seating;

	private String ticketType;

	private String splitType;

	private Integer numberOfTickets;

	private String externalId;

	private String notes;

	private EventRequest event;

	private VenueRequest venue;

	public Money getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Money ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Seating getSeating() {
		return seating;
	}

	public void setSeating(Seating seating) {
		this.seating = seating;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getSplitType() {
		return splitType;
	}

	public void setSplitType(String splitType) {
		this.splitType = splitType;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public EventRequest getEvent() {
		return event;
	}

	public void setEvent(EventRequest event) {
		this.event = event;
	}

	public VenueRequest getVenue() {
		return venue;
	}

	public void setVenue(VenueRequest venue) {
		this.venue = venue;
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
		CreateSellerListingRequest rhs = (CreateSellerListingRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
