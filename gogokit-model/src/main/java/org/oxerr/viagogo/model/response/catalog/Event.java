package org.oxerr.viagogo.model.response.catalog;

import java.time.OffsetDateTime;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

/**
 * An event on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Event">Event</a>
 */
@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Event extends EmbeddedEvent {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * True if the event start and end time have been confirmed; Otherwise, false.
	 */
	private Boolean timeConfirmed;

	/**
	 * The type of the event. Can be {@code Main} or {@code Parking}.
	 */
	private String type;

	/**
	 * The minimum ticket price of the event.
	 */
	private Money minTicketPrice;

	/**
	 * The status of the event.
	 * Can be {@code Normal}, {@code Postponed}, {@code Cancelled},
	 * {@code Rescheduled}, {@code Relocated}, {@code RelocatedAndRescheduled},
	 * {@code Draft}, {@code Contingent} and {@code Deleted}.
	 */
	private String status;

	/**
	 * Url on the website for the event.
	 */
	@Link("event:webpage")
	private HALLink webPageLink;

	/**
	 * The categories for this event.
	 */
	@EmbeddedResource
	private List<EmbeddedCategory> categories;

	/**
	 * The external mappings for this event.
	 */
	@EmbeddedResource
	private List<ExternalMapping> externalMappings;

	/**
	 * The genre for this event.
	 */
	@EmbeddedResource
	private Genre genre;

	/**
	 * The events that have been merged into this event.
	 */
	@EmbeddedResource
	private List<Event> mergedEvents;

	/**
	 * The venue where the event is taking place.
	 */
	@EmbeddedResource
	private Venue venue;

	public Event() {
	}

	public Event(String name, OffsetDateTime startDate) {
		super(name, startDate);
	}

	public Boolean getTimeConfirmed() {
		return timeConfirmed;
	}

	public void setTimeConfirmed(Boolean timeConfirmed) {
		this.timeConfirmed = timeConfirmed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Money getMinTicketPrice() {
		return minTicketPrice;
	}

	public void setMinTicketPrice(Money minTicketPrice) {
		this.minTicketPrice = minTicketPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HALLink getWebPageLink() {
		return webPageLink;
	}

	public void setWebPageLink(HALLink webPageLink) {
		this.webPageLink = webPageLink;
	}

	public List<EmbeddedCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<EmbeddedCategory> categories) {
		this.categories = categories;
	}

	public List<ExternalMapping> getExternalMappings() {
		return externalMappings;
	}

	public void setExternalMappings(List<ExternalMapping> externalMappings) {
		this.externalMappings = externalMappings;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public List<Event> getMergedEvents() {
		return mergedEvents;
	}

	public void setMergedEvents(List<Event> mergedEvents) {
		this.mergedEvents = mergedEvents;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
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
		Event rhs = (Event) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
