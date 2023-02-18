package org.oxerr.viagogo.model.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public abstract class Resource implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	@Link("self")
	private HALLink selfLink;

	public HALLink getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(HALLink selfLink) {
		this.selfLink = selfLink;
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
		Resource rhs = (Resource) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
