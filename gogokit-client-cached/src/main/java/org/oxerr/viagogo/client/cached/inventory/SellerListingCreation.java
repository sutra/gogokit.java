package org.oxerr.viagogo.client.cached.inventory;

import java.beans.Transient;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.request.inventory.NewSellerListing;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

public class SellerListingCreation implements Serializable {

	private static final long serialVersionUID = 2023020301L;

	private NewSellerListing newSellerListing;

	private SellerListing sellerListing;

	private IOException exception;

	public SellerListingCreation() {
	}

	public SellerListingCreation(
		NewSellerListing newSellerListing,
		SellerListing sellerListing
	) {
		this.newSellerListing = newSellerListing;
		this.sellerListing = sellerListing;
	}

	public SellerListingCreation(IOException exception) {
		this.exception = exception;
	}

	public NewSellerListing getNewSellerListing() {
		return newSellerListing;
	}

	public void setNewSellerListing(NewSellerListing newSellerListing) {
		this.newSellerListing = newSellerListing;
	}

	public SellerListing getSellerListing() {
		return sellerListing;
	}

	public void setSellerListing(SellerListing sellerListing) {
		this.sellerListing = sellerListing;
	}

	public IOException getException() {
		return exception;
	}

	public void setException(IOException exception) {
		this.exception = exception;
	}

	@Transient
	public boolean isEmpty() {
		return this.newSellerListing == null;
	}

	@Transient
	public boolean isEqual(NewSellerListing newSellerListing) {
		return Objects.equals(this.newSellerListing, newSellerListing);
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
