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

	private static final long serialVersionUID = 2023031901L;

	private Long viagogoEventId;

	public ViagogoEvent() {
		this(null, null, null, Collections.emptyList());
	}

	public ViagogoEvent(String id, OffsetDateTime startDate, Long viagogoEventId) {
		this(id, startDate, viagogoEventId, Collections.emptyList());
	}

	public ViagogoEvent(String id, OffsetDateTime startDate, Long viagogoEventId, List<ViagogoListing> listings) {
		super(id, startDate, listings);
		this.viagogoEventId = viagogoEventId;
	}

	public Long getViagogoEventId() {
		return viagogoEventId;
	}

	public void setViagogoEventId(Long viagogoEventId) {
		this.viagogoEventId = viagogoEventId;
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
