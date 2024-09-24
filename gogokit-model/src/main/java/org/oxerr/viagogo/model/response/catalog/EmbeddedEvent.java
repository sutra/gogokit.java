package org.oxerr.viagogo.model.response.catalog;

import java.time.OffsetDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

/**
 * An event on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Event">Event</a>
 */
@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class EmbeddedEvent extends Resource {

	private static final long serialVersionUID = 2023021301L;

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
	private OffsetDateTime startDate;

	/**
	 * The date when the event ends.
	 */
	private OffsetDateTime endDate;

	/**
	 * The date when tickets for the event will go onsale.
	 */
	private OffsetDateTime onSaleDate;

	/**
	 * True if the event start and end date have been confirmed; Otherwise, false.
	 */
	private Boolean dateConfirmed;

	public EmbeddedEvent() {
	}

	public EmbeddedEvent(String name, OffsetDateTime startDate) {
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

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}

	public OffsetDateTime getOnSaleDate() {
		return onSaleDate;
	}

	public void setOnSaleDate(OffsetDateTime onSaleDate) {
		this.onSaleDate = onSaleDate;
	}

	public Boolean getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Boolean dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
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
		if (!(obj instanceof EmbeddedEvent)) {
			return false;
		}
		EmbeddedEvent rhs = (EmbeddedEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
