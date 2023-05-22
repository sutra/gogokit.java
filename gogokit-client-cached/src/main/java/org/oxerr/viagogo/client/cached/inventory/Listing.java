package org.oxerr.viagogo.client.cached.inventory;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public class Listing implements Serializable {

	private static final long serialVersionUID = 2023031901L;

	private String id;

	private Long viagogoEventId;

	private CreateSellerListingRequest request;

	public Listing() {
	}

	public Listing(String id, Long viagogoEventId, CreateSellerListingRequest request) {
		this.id = id;
		this.viagogoEventId = viagogoEventId;
		this.request = request;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
