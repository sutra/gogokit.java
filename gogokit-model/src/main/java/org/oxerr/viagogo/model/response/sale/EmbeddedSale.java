package org.oxerr.viagogo.model.response.sale;

import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.response.Resource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class EmbeddedSale extends Resource {

	private static final long serialVersionUID = 2023021801L;

	/**
	 * The sale identifier.
	 */
	private Integer id;

	/**
	 * The date when the sale was created.
	 */
	private Instant createdAt;

	/**
	 * The seating information for the ticket(s) that have been sold.
	 */
	private Seating seating;

	/**
	 * The total amount that the seller will receive for the sale.
	 */
	private Money proceeds;

	/**
	 * The total amount that the seller will receive for the sale.
	 */
	private Money displayProceeds;

	/**
	 * The number of tickets that have been sold.
	 */
	private Integer numberOfTickets;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Seating getSeating() {
		return seating;
	}

	public void setSeating(Seating seating) {
		this.seating = seating;
	}

	public Money getProceeds() {
		return proceeds;
	}

	public void setProceeds(Money proceeds) {
		this.proceeds = proceeds;
	}

	public Money getDisplayProceeds() {
		return displayProceeds;
	}

	public void setDisplayProceeds(Money displayProceeds) {
		this.displayProceeds = displayProceeds;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
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
		if (!(obj instanceof EmbeddedSale)) {
			return false;
		}
		EmbeddedSale rhs = (EmbeddedSale) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
