package org.oxerr.viagogo.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoListing extends Listing<String, CreateSellerListingRequest> {

	private static final long serialVersionUID = 2023031901L;

	private Long viagogoEventId;

	public ViagogoListing() {
	}

	public ViagogoListing(String id, Long viagogoEventId, CreateSellerListingRequest request) {
		super(id, request);
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
		if (!(obj instanceof ViagogoListing)) {
			return false;
		}
		ViagogoListing rhs = (ViagogoListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
