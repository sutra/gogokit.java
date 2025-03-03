package org.oxerr.viagogo.model.request.inventory;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SellerListingBarcodeUpload extends BaseBarcodeUpload {

	private static final long serialVersionUID = 2023021801L;

	private List<String> barcodeValuesSha256Hashed;

	public List<String> getBarcodeValuesSha256Hashed() {
		return barcodeValuesSha256Hashed;
	}

	public void setBarcodeValuesSha256Hashed(List<String> barcodeValuesSha256Hashed) {
		this.barcodeValuesSha256Hashed = barcodeValuesSha256Hashed;
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
		if (!(obj instanceof SellerListingBarcodeUpload)) {
			return false;
		}
		SellerListingBarcodeUpload rhs = (SellerListingBarcodeUpload) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
