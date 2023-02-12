package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

public class VenueRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

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

	public VenueRequest() {
	}

	public VenueRequest(String name, String city) {
		this.name = name;
		this.city = city;
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

}
