package org.oxerr.viagogo.model.response.inventory;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.catalog.Venue;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class SellerEvent extends Event {

	private static final long serialVersionUID = 2023021301L;

	private List<Event> mergedEvents;

	/**
	 * True if the event is a last minute sale event; otherwise, false.
	 */
	private Boolean lms;

	/**
	 * Create listing for the currently authenticated user.
	 */
	@Link("sellerevent:createsellerlisting")
	private HALLink createSellerListing;

	/**
	 * Event listings that belong to the currently authenticated user.
	 */
	@Link("sellerevent:sellerlistings")
	private HALLink sellerListings;

	@EmbeddedResource
	private Venue salesAggregates;

	@Override
	public List<Event> getMergedEvents() {
		return mergedEvents;
	}

	@Override
	public void setMergedEvents(List<Event> mergedEvents) {
		this.mergedEvents = mergedEvents;
	}

	public Boolean getLms() {
		return lms;
	}

	public void setLms(Boolean lms) {
		this.lms = lms;
	}

	public HALLink getCreateSellerListing() {
		return createSellerListing;
	}

	public void setCreateSellerListing(HALLink createSellerListing) {
		this.createSellerListing = createSellerListing;
	}

	public HALLink getSellerListings() {
		return sellerListings;
	}

	public void setSellerListings(HALLink sellerListings) {
		this.sellerListings = sellerListings;
	}

	public Venue getSalesAggregates() {
		return salesAggregates;
	}

	public void setSalesAggregates(Venue salesAggregates) {
		this.salesAggregates = salesAggregates;
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
		SellerEvent rhs = (SellerEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
