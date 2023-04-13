package org.oxerr.viagogo.client.rescu;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusExceptionSupport;

public class ViagogoException extends HttpStatusExceptionSupport {

	private static final long serialVersionUID = 2023020201L;

	private final String type;

	private final String title;

	private final Integer status;

	private final String traceId;

	private final String code;

	private final Map<String, String> errors;

	public ViagogoException(
		@JsonProperty("type") String type,
		@JsonProperty("title") String title,
		@JsonProperty("status") int status,
		@JsonProperty("traceId") String traceId,
		@JsonProperty("code") String code,
		@JsonProperty("message") String message,
		@JsonProperty("errors") Map<String, String> errors
	) {
		super(StringUtils.defaultString(title, message));
		this.type = type;
		this.title = title;
		this.status = status;
		this.traceId = traceId;
		this.code = code;
		this.errors = errors;
	}

	public String getType() {
		return type;
	}


	public String getTitle() {
		return title;
	}


	public Integer getStatus() {
		return status;
	}


	public String getTraceId() {
		return traceId;
	}

	public String getCode() {
		return code;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
