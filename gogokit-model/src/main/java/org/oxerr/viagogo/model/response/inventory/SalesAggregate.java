package org.oxerr.viagogo.model.response.inventory;

import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class SalesAggregate extends Resource {

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
	private HALLink sales;

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

	public HALLink getSales() {
		return sales;
	}

	public void setSales(HALLink sales) {
		this.sales = sales;
	}

}
