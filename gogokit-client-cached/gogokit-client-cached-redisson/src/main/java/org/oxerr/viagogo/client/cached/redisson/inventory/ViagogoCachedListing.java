package org.oxerr.viagogo.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class ViagogoCachedListing extends CachedListing<CreateSellerListingRequest> {

	private static final long serialVersionUID = 2023031801L;

	private ViagogoCachedEvent event;

	private Long viagogoEventId;

	public ViagogoCachedListing() {
	}

	public ViagogoCachedListing(ViagogoCachedEvent event, ViagogoListing listing, Status status) {
		this(event, listing.getRequest(), status);
	}

	public ViagogoCachedListing(ViagogoCachedEvent event, CreateSellerListingRequest request, Status status) {
		super(request, status);
		this.event = event;
		this.viagogoEventId = event.getViagogoEventId();
	}

	public ViagogoCachedEvent getEvent() {
		return event;
	}

	public void setEvent(ViagogoCachedEvent event) {
		this.event = event;
	}

	public Long getViagogoEventId() {
		return viagogoEventId;
	}

	public void setViagogoEventId(Long viagogoEventId) {
		this.viagogoEventId = viagogoEventId;
	}

	public ViagogoListing toViagogoListing() {
		return new ViagogoListing(this.getRequest().getExternalId(), this.event.getViagogoEventId(), this.getRequest());
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
