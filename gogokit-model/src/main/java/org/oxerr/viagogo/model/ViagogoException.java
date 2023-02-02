package org.oxerr.viagogo.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ViagogoException extends RuntimeException {

	private static final long serialVersionUID = 2023020201L;

	private final String code;

	private final String message;

	private final Map<String, String> errors;

	public ViagogoException(
		@JsonProperty("code") String code,
		@JsonProperty("message") String message,
		@JsonProperty("errors") Map<String, String> errors
	) {
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
