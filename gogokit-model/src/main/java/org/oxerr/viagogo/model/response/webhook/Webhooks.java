package org.oxerr.viagogo.model.response.webhook;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.PagedResource;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

public class Webhooks extends PagedResource<Webhook> {

	private static final long serialVersionUID = 2023080201L;

	@Link("webhook:create")
	private HALLink createLink;

	public HALLink getCreateLink() {
		return createLink;
	}

	public void setCreateLink(HALLink createLink) {
		this.createLink = createLink;
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
		if (!(obj instanceof Webhooks)) {
			return false;
		}
		Webhooks rhs = (Webhooks) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
