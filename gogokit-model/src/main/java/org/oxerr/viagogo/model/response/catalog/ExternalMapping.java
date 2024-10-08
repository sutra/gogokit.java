package org.oxerr.viagogo.model.response.catalog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class ExternalMapping extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The identifier of the resource in the external platform
	 */
	private String id;

	/**
	 * The name of the external platform. Can be {@code legacy_stubhub}.
	 */
	private String platformName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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
		if (!(obj instanceof ExternalMapping)) {
			return false;
		}
		ExternalMapping rhs = (ExternalMapping) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
