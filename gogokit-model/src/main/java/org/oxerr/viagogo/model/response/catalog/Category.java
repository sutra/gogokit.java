package org.oxerr.viagogo.model.response.catalog;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Category extends Resource {

	/**
	 * The category identifier.
	 */
	private Integer id;

	/**
	 * The name of the category.
	 */
	private String name;

	/**
	 * The role of the category for a particular event.
	 * Can be {@code MainArtist}, {@code SupportingArtist}, {@code HomeTeam},
	 * {@code AwayTeam}, {@code Tournament}, {@code HeadlineAct},
	 * {@code StandardAct}, {@code Festival}, {@code SportsLeague},
	 * {@code ConcertTour}.
	 */
	private String role;

	/**
	 * The external mappings for this category.
	 */
	@EmbeddedResource
	private List<ExternalMapping> externalMappings;

	/**
	 * The categories that have been merged into this category.
	 */
	@EmbeddedResource
	private List<Integer> mergedCategories;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<ExternalMapping> getExternalMappings() {
		return externalMappings;
	}

	public void setExternalMappings(List<ExternalMapping> externalMappings) {
		this.externalMappings = externalMappings;
	}

	public List<Integer> getMergedCategories() {
		return mergedCategories;
	}

	public void setMergedCategories(List<Integer> mergedCategories) {
		this.mergedCategories = mergedCategories;
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
		Category rhs = (Category) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
