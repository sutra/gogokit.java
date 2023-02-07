package org.oxerr.viagogo.model.request.inventory;

public class CreateSellerEventRequest {

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

}
