package org.oxerr.viagogo.model.request.inventory;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SellerEventRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

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
	 * the given timestamp
	 */
	private Instant updatedSince;

	/**
	 * Determines the ordering of items.
	 * A comma-separated string containing {@code id},
	 * {@code number_of_tickets}, {@code orresource_version}.
	 */
	private String sort;

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
		SellerEventRequest rhs = (SellerEventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
