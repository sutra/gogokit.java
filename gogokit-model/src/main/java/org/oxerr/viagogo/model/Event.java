package org.oxerr.viagogo.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Event {

	private Integer id;

	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private Instant startDate;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private Instant endDate;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private Instant onSaleDate;

	private Boolean dateConfirmed;

	private Boolean timeConfirmed;

	private String type;

	private Money minTicketPrice;

	public Event() {
	}

	public Event(String name, Instant startDate) {
		this.name = name;
		this.startDate = startDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

}
