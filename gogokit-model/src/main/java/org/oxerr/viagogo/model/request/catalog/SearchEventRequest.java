package org.oxerr.viagogo.model.request.catalog;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Link;

import io.openapitools.jackson.dataformat.hal.HALLink;

public class SearchEventRequest extends EventRequest {

	private static final long serialVersionUID = 2023021301L;

	private String q;

	private LocalDateTime dateLocal;

	public static SearchEventRequest from(HALLink halLink) {
		return new SearchEventRequest(new Link(halLink));
	}

	public SearchEventRequest() {
	}

	public SearchEventRequest(Link link) {
		super(link);

		this.setQ(link.getFirstAsString("q"));
		this.setDateLocal(link.getFirstAsLocalDateTime("dateLocal"));
	}

	public SearchEventRequest(String q, LocalDateTime dateLocal) {
		this.q = q;
		this.dateLocal = dateLocal;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public LocalDateTime getDateLocal() {
		return dateLocal;
	}

	public void setDateLocal(LocalDateTime dateLocal) {
		this.dateLocal = dateLocal;
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
		if (!(obj instanceof SearchEventRequest)) {
			return false;
		}
		SearchEventRequest rhs = (SearchEventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
