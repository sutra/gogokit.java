package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CreateSellerEventRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The event information.
	 */
	private EventRequest event;

	/**
	 * The venue where the event will take place.
	 */
	private VenueRequest venue;

	/**
	 * The country where the event will take place.
	 */
	private CountryRequest country;

	public CreateSellerEventRequest() {
	}

	public CreateSellerEventRequest(
		EventRequest event,
		VenueRequest venue,
		CountryRequest country
	) {
		this.event = event;
		this.venue = venue;
		this.country = country;
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

	public CountryRequest getCountry() {
		return country;
	}

	public void setCountry(CountryRequest country) {
		this.country = country;
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
		CreateSellerEventRequest rhs = (CreateSellerEventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
