package org.oxerr.viagogo.model.request;

import java.beans.Transient;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class ListRequest implements Serializable {

	public interface Sort {

		String getCode();

	}

	private static final long serialVersionUID = 2023112401L;

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
	 * A comma-separated string.
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

	@Transient
	public void setSort(Sort... sorts) {
		this.setSort(Arrays.stream(sorts).map(Sort::getCode).collect(Collectors.joining(",")));
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
		ListRequest rhs = (ListRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
