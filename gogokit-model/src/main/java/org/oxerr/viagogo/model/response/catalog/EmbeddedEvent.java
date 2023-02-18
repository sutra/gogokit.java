package org.oxerr.viagogo.model.response.catalog;

import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	public EmbeddedEvent() {
	}

	public EmbeddedEvent(String name, Instant startDate) {
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
		EmbeddedEvent rhs = (EmbeddedEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
