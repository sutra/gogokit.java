package org.oxerr.viagogo.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoListing extends Listing<CreateSellerListingRequest> {

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
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
