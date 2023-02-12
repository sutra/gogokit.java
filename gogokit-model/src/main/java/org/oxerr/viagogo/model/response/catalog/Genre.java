package org.oxerr.viagogo.model.response.catalog;

import java.util.List;

import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Genre extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The genre identifier.
	 */
	private String id;

	/**
	 * The name of the genre.
	 */
	private String name;

	/**
	 * The external mappings for this genre.
	 */
	@EmbeddedResource
	private List<ExternalMapping> externalMappings;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExternalMapping> getExternalMappings() {
		return externalMappings;
	}

	public void setExternalMappings(List<ExternalMapping> externalMappings) {
		this.externalMappings = externalMappings;
	}

}
