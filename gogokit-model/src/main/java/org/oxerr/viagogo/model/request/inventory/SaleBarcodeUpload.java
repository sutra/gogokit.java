package org.oxerr.viagogo.model.request.inventory;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SaleBarcodeUpload extends BaseBarcodeUpload {

	private static final long serialVersionUID = 2023021801L;

	private List<String> arcodeValues;

	public List<String> getArcodeValues() {
		return arcodeValues;
	}

	public void setArcodeValues(List<String> arcodeValues) {
		this.arcodeValues = arcodeValues;
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
		if (!(obj instanceof SaleBarcodeUpload)) {
			return false;
		}
		SaleBarcodeUpload rhs = (SaleBarcodeUpload) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
