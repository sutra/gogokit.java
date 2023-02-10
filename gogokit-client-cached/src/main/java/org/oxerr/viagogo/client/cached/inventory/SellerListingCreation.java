package org.oxerr.viagogo.client.cached.inventory;

import java.beans.Transient;
import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public class SellerListingCreation {

	private CreateSellerListingRequest request;

	private SellerListing response;

	private IOException exception;

	public SellerListingCreation() {
	}

	public SellerListingCreation(
		CreateSellerListingRequest request,
		SellerListing response
	) {
		this.request = request;
		this.response = response;
	}

	public SellerListingCreation(IOException exception) {
		this.exception = exception;
	}

	public CreateSellerListingRequest getRequest() {
		return request;
	}

	public void setRequest(CreateSellerListingRequest request) {
		this.request = request;
	}

	public SellerListing getResponse() {
		return response;
	}

	public void setResponse(SellerListing response) {
		this.response = response;
	}

	public IOException getException() {
		return exception;
	}

	public void setException(IOException exception) {
		this.exception = exception;
	}

	@Transient
	public boolean isEmpty() {
		return this.response == null;
	}

	@Transient
	public boolean isNotEmpty() {
		return !this.isEmpty();
	}

	@Transient
	public boolean isEqual(CreateSellerListingRequest request) {
		return Objects.equals(this.request, request);
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
		SellerListingCreation rhs = (SellerListingCreation) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
