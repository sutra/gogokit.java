package org.oxerr.viagogo.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;
import io.openapitools.jackson.dataformat.hal.annotation.Resource;

/**
 * An venue on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Venue">Venue</a>
 */
@Resource
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Venue {

	/**
	 * The venue identifier.
	 */
	private Integer id;

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

	@Link
	private HALLink self;

	public Venue() {
	}

	public Venue(String name, String city) {
		this.name = name;
		this.city = city;
	}

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

	public HALLink getSelf() {
		return self;
	}

	public void setSelf(HALLink self) {
		this.self = self;
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
