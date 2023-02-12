package org.oxerr.viagogo.model.response;

import java.io.Serializable;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public abstract class Resource implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	@Link
	private HALLink self;

	public HALLink getSelf() {
		return self;
	}

	public void setSelf(HALLink self) {
		this.self = self;
	}

}
