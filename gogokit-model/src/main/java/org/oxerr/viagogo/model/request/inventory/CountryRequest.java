package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;

public class CountryRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The two-letter ISO 3166 code for the country.
	 */
	private String code;

	public CountryRequest() {
	}

	public CountryRequest(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
