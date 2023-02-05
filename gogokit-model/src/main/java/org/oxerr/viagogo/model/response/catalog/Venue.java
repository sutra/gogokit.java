package org.oxerr.viagogo.model.response.catalog;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

/**
 * An venue on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Venue">Venue</a>
 */
@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class Venue extends Resource {

	/**
	 * The venue identifier.
	 */
	private Long id;

	/**
	 * The name of the venue.
	 */
	private String name;

	/**
	 * The name of the city where the venue is located.
	 */
	private String city;

	/**
	 * The name of the State or Province where the venue is located.
	 */
	private String stateProvince;

	/**
	 * The postal code for the venue.
	 */
	private String postalCode;

	/**
	 * The latitude for the venue.
	 */
	private Double latitude;

	/**
	 * The longitude for the venue.
	 */
	private Double longitude;

	/**
	 * The Country where the venue is located.
	 */
	@EmbeddedResource
	private Country country;

	/**
	 * The external mappings for this venue.
	 */
	@EmbeddedResource
	private List<ExternalMapping> externalMappings;

	public Venue() {
	}

	public Venue(String name, String city) {
		this.name = name;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Venue rhs = (Venue) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
