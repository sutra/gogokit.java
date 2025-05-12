package org.oxerr.viagogo.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoCachedListing extends CachedListing<CreateSellerListingRequest> {

	private static final long serialVersionUID = 2023031801L;

	private ViagogoCachedEvent event;

	public ViagogoCachedListing() {
	}

	public ViagogoCachedListing(ViagogoEvent event, ViagogoListing listing, Status status) {
		this(new ViagogoCachedEvent(event), listing, status);
	}

	public ViagogoCachedListing(ViagogoCachedEvent event, ViagogoListing listing, Status status) {
		this(event, listing.getRequest(), status);
	}

	public ViagogoCachedListing(ViagogoCachedEvent event, CreateSellerListingRequest request, Status status) {
		super(request, status);
		this.event = event;
	}

	public ViagogoCachedEvent getEvent() {
		return event;
	}

	public void setEvent(ViagogoCachedEvent event) {
		this.event = event;
	}

	public ViagogoListing toMarketplaceListing() {
		return new ViagogoListing(this.getRequest().getExternalId(), this.event.getMarketplaceEventId(), this.getRequest());
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
		if (!(obj instanceof ViagogoCachedListing)) {
			return false;
		}
		ViagogoCachedListing rhs = (ViagogoCachedListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
