package org.oxerr.viagogo.model.topic;

import java.io.Serializable;

import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.catalog.Venue;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.oxerr.viagogo.model.response.sale.Sale;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Resource;

@Resource
public class SalesTopic implements Serializable {

	private static final long serialVersionUID = 2023080601L;

	private String topic;

	private String action;

	@EmbeddedResource
	private Event event;

	@EmbeddedResource
	private Sale sale;

	@EmbeddedResource
	private SellerListing sellerListing;

	@EmbeddedResource
	private Venue venue;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public SellerListing getSellerListing() {
		return sellerListing;
	}

	public void setSellerListing(SellerListing sellerListing) {
		this.sellerListing = sellerListing;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

}
