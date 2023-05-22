package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.client.cached.inventory.Listing;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class CachedListing implements Serializable {

	private static final long serialVersionUID = 2023031801L;

	private Status status;

	private Long viagogoEventId;

	private CreateSellerListingRequest request;

	public static CachedListing pending(Listing listing) {
		return new CachedListing(Status.PENDING_LIST, listing.getViagogoEventId(), listing.getRequest());
	}

	public static CachedListing listed(Listing listing) {
		return new CachedListing(Status.LISTED, listing.getViagogoEventId(), listing.getRequest());
	}

	public static boolean shouldCreate(
		@Nonnull Listing listing,
		@Nullable  CachedListing cachedListing) {
		return cachedListing == null
			|| cachedListing.getStatus() == Status.PENDING_LIST
			|| !cachedListing.getRequest().equals(listing.getRequest());
	}

	public CachedListing() {
	}

	public CachedListing(Status status, Long viagogoEventId, CreateSellerListingRequest request) {
		this.status = status;
		this.viagogoEventId = viagogoEventId;
		this.request = request;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getViagogoEventId() {
		return viagogoEventId;
	}

	public void setViagogoEventId(Long viagogoEventId) {
		this.viagogoEventId = viagogoEventId;
	}

	public CreateSellerListingRequest getRequest() {
		return request;
	}

	public void setRequest(CreateSellerListingRequest request) {
		this.request = request;
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
