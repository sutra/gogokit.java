package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SellerListingRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	private Long eventId;

	private String requestedEventId;

	/**
	 * Specifies which page of data to retrieve.
	 */
	private Integer page;

	/**
	 * Set custom page sizes on response.
	 */
	private Integer pageSize;

	/**
	 * Filters the response to only return items that have been updated since
	 * the given timestamp.
	 */
	private Instant updatedSince;

	/**
	 * Determines the ordering of items.
	 * A comma-separated string containing
	 * {@code available_tickets}, {@code created_at}, {@code event_date},
	 * {@code event_name}, {@code expiration_date}, {@code price},
	 * {@code resource_version}, {@code orticket_availability_date}.
	 */
	private String sort;

	public SellerListingRequest() {
	}

	public SellerListingRequest(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getRequestedEventId() {
		return requestedEventId;
	}

	public void setRequestedEventId(String requestedEventId) {
		this.requestedEventId = requestedEventId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Instant getUpdatedSince() {
		return updatedSince;
	}

	public void setUpdatedSince(Instant updatedSince) {
		this.updatedSince = updatedSince;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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
		SellerListingRequest rhs = (SellerListingRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
