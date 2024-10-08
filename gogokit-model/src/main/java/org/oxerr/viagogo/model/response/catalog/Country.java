package org.oxerr.viagogo.model.response.catalog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Country extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The two-letter ISO 3166 code for the country.
	 */
	private String code;

	/**
	 * The name of the country.
	 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if (!(obj instanceof Country)) {
			return false;
		}
		Country rhs = (Country) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
