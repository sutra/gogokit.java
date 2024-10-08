package org.oxerr.viagogo.model.response.catalog;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class EmbeddedCategory extends Resource {

	private static final long serialVersionUID = 2023021301L;

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

	public EmbeddedCategory() {
	}

	public EmbeddedCategory(Integer id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}

	/**
	 * The external mappings for this category.
	 */
	@EmbeddedResource
	private List<ExternalMapping> externalMappings;

	/**
	 * The categories that have been merged into this category.
	 */
	@EmbeddedResource
	private List<EmbeddedCategory> mergedCategories;

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

	public List<EmbeddedCategory> getMergedCategories() {
		return mergedCategories;
	}

	public void setMergedCategories(List<EmbeddedCategory> mergedCategories) {
		this.mergedCategories = mergedCategories;
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
		if (!(obj instanceof EmbeddedCategory)) {
			return false;
		}
		EmbeddedCategory rhs = (EmbeddedCategory) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
