package org.oxerr.viagogo.model.response.catalog;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.response.Resource;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

/**
 * An event on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Event">Event</a>
 */
@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Event extends Resource {

	/**
	 * The event identifier.
	 */
	private Long id;

	/**
	 * The name of the event.
	 */
	private String name;

	/**
	 * The date when the event starts.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "UTC")
	private Instant startDate;

	/**
	 * The date when the event ends.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "UTC")
	private Instant endDate;

	/**
	 * The date when tickets for the event will go onsale.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "UTC")
	private Instant onSaleDate;

	/**
	 * True if the event start and end date have been confirmed; Otherwise, false.
	 */
	private Boolean dateConfirmed;

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
	private HALLink webpage;

	/**
	 * The categories for this event.
	 */
	@EmbeddedResource
	private List<Category> categories;

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
	private List<Long> mergedEvents;

	/**
	 * The venue where the event is taking place.
	 */
	@EmbeddedResource
	private Venue venue;

	public Event() {
	}

	public Event(String name, Instant startDate) {
		this.name = name;
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Instant getOnSaleDate() {
		return onSaleDate;
	}

	public void setOnSaleDate(Instant onSaleDate) {
		this.onSaleDate = onSaleDate;
	}

	public Boolean getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Boolean dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
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

	public HALLink getWebpage() {
		return webpage;
	}

	public void setWebpage(HALLink webpage) {
		this.webpage = webpage;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
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

	public List<Long> getMergedEvents() {
		return mergedEvents;
	}

	public void setMergedEvents(List<Long> mergedEvents) {
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
