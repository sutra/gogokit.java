package org.oxerr.viagogo.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoCachedListing extends CachedListing<CreateSellerListingRequest> {

	private static final long serialVersionUID = 2023031801L;

	private Long viagogoEventId;

	public ViagogoCachedListing() {
	}

	public ViagogoCachedListing(ViagogoListing listing, Status status) {
		this(status, listing.getViagogoEventId(), listing.getRequest());
	}

	public ViagogoCachedListing(Status status, Long viagogoEventId, CreateSellerListingRequest request) {
		super(status, request);
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
