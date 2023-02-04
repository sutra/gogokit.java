package org.oxerr.viagogo.model.response;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public abstract class Resource {

	@Link
	private HALLink self;

	public HALLink getSelf() {
		return self;
	}

	public void setSelf(HALLink self) {
		this.self = self;
	}

}
