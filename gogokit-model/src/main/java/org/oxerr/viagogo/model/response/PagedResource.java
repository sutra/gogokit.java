package org.oxerr.viagogo.model.response;

import java.util.List;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class PagedResource<T> extends Resource {

	private static final long serialVersionUID = 2023021301L;

	private Integer totalItems;

	private Integer page;

	private Integer pageSize;

	@EmbeddedResource
	private List<T> deletedItems;

	@EmbeddedResource
	private List<T> items;

	@Link
	private HALLink next;

	@Link
	private HALLink prev;

	@Link
	private HALLink first;

	@Link
	private HALLink last;

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

	public HALLink getNext() {
		return next;
	}

	public void setNext(HALLink next) {
		this.next = next;
	}

	public HALLink getPrev() {
		return prev;
	}

	public void setPrev(HALLink prev) {
		this.prev = prev;
	}

	public HALLink getFirst() {
		return first;
	}

	public void setFirst(HALLink first) {
		this.first = first;
	}

	public HALLink getLast() {
		return last;
	}

	public void setLastLink(HALLink last) {
		this.last = last;
	}

}
