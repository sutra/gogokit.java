package org.oxerr.viagogo.model.response;

import java.util.Collections;
import java.util.Map;

public class ViagogoException extends RuntimeException {

	private static final long serialVersionUID = 2023020201L;

	private final String code;

	private final String message;

	private final Map<String, String> errors;

	public ViagogoException() {
		this(null, null, Collections.emptyMap());
	}

	public ViagogoException(String code, String message, Map<String, String> errors) {
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
