package org.oxerr.viagogo.model.response.catalog;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Genre)) {
			return false;
		}
		Genre rhs = (Genre) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
