package org.oxerr.viagogo.model.request.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CreateSellerListingForRequestedEventRequest extends CreateSellerListingRequest {

	private static final long serialVersionUID = 2023021301L;

	private EventRequest event;

	private VenueRequest venue;

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
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CreateSellerListingForRequestedEventRequest)) {
			return false;
		}
		CreateSellerListingForRequestedEventRequest rhs = (CreateSellerListingForRequestedEventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
