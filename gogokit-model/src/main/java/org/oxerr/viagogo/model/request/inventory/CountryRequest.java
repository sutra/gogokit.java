package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CountryRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The two-letter ISO 3166 code for the country.
	 */
	private String code;

	public CountryRequest() {
	}

	public CountryRequest(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		CountryRequest rhs = (CountryRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
