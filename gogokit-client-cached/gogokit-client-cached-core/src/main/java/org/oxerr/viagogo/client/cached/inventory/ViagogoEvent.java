package org.oxerr.viagogo.client.cached.inventory;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.Event;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoEvent extends Event<String, String, CreateSellerListingRequest, ViagogoListing> {

	private static final long serialVersionUID = 2024032401L;

	private Long marketplaceEventId;

	public ViagogoEvent() {
		this(null, null, null, Collections.emptyList());
	}

	public ViagogoEvent(String id, OffsetDateTime startDate, Long marketplaceEventId) {
		this(id, startDate, marketplaceEventId, Collections.emptyList());
	}

	public ViagogoEvent(String id, OffsetDateTime startDate, Long marketplaceEventId, List<ViagogoListing> listings) {
		super(id, startDate, listings);
		this.marketplaceEventId = marketplaceEventId;
	}

	public Long getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public void setMarketplaceEventId(Long marketplaceEventId) {
		this.marketplaceEventId = marketplaceEventId;
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
		if (!(obj instanceof ViagogoEvent)) {
			return false;
		}
		ViagogoEvent rhs = (ViagogoEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
