package org.oxerr.viagogo.model.response;

import java.io.Serializable;
import java.util.List;

import javax.annotation.CheckForNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class PagedResource<T extends Serializable> extends Resource {

	private static final long serialVersionUID = 2023021301L;

	private Integer totalItems;

	private Integer page;

	private Integer pageSize;

	@EmbeddedResource
	private List<T> deletedItems;

	@EmbeddedResource
	private List<T> items;

	@Link("next")
	private HALLink nextLink;

	@Link("prev")
	private HALLink prevLink;

	@Link("first")
	private HALLink firstLink;

	@Link("last")
	private HALLink lastLink;

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
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

	public List<T> getDeletedItems() {
		return deletedItems;
	}

	public void setDeletedItems(List<T> deletedItems) {
		this.deletedItems = deletedItems;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	@CheckForNull
	public HALLink getNextLink() {
		return nextLink;
	}

	public void setNextLink(HALLink nextLink) {
		this.nextLink = nextLink;
	}

	@CheckForNull
	public HALLink getPrevLink() {
		return prevLink;
	}

	public void setPrevLink(HALLink prevLink) {
		this.prevLink = prevLink;
	}

	@CheckForNull
	public HALLink getFirstLink() {
		return firstLink;
	}

	public void setFirstLink(HALLink firstLink) {
		this.firstLink = firstLink;
	}

	@CheckForNull
	public HALLink getLastLink() {
		return lastLink;
	}

	public void setLastLink(HALLink lastLink) {
		this.lastLink = lastLink;
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
		if (!(obj instanceof PagedResource)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		PagedResource<T> rhs = (PagedResource<T>) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
