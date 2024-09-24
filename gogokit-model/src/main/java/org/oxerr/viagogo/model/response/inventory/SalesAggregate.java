package org.oxerr.viagogo.model.response.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class SalesAggregate extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The total number of sales aggregated in this resource.
	 */
	private Integer numberOfSales;

	/**
	 * The total amount that the seller will receive for the sale.
	 */
	private Money proceeds;

	/**
	 * The total number of tickets for the sales aggregated in this resource.
	 */
	private Integer numberOfTickets;

	/**
	 * A short localised string describing the current status of the aggregated sales.
	 */
	private String status;

	/**
	 * The sales aggregated in this resource.
	 */
	@Link("salesaggregate:sales")
	private HALLink salesLink;

	public Integer getNumberOfSales() {
		return numberOfSales;
	}

	public void setNumberOfSales(Integer numberOfSales) {
		this.numberOfSales = numberOfSales;
	}

	public Money getProceeds() {
		return proceeds;
	}

	public void setProceeds(Money proceeds) {
		this.proceeds = proceeds;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HALLink getSalesLink() {
		return salesLink;
	}

	public void setSalesLink(HALLink salesLink) {
		this.salesLink = salesLink;
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
		if (!(obj instanceof SalesAggregate)) {
			return false;
		}
		SalesAggregate rhs = (SalesAggregate) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
