package org.oxerr.viagogo.model.request.catalog;

import java.time.Instant;

public class SearchEventRequest {

	private String q;

	private Instant dateLocal;

	private Integer page;

	private Integer pageSize;

	private Instant updatedSince;

	private String sort;

	private Long minResourceVersion;

	private String countryCode;

	private Double latitude;

	private Double longitude;

	private Integer maxDistanceInmeters;

	private Integer genreId;

	public SearchEventRequest() {
	}

	public SearchEventRequest(String q, Instant dateLocal) {
		this.q = q;
		this.dateLocal = dateLocal;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Instant getDateLocal() {
		return dateLocal;
	}

	public void setDateLocal(Instant dateLocal) {
		this.dateLocal = dateLocal;
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

	public Long getMinResourceVersion() {
		return minResourceVersion;
	}

	public void setMinResourceVersion(Long minResourceVersion) {
		this.minResourceVersion = minResourceVersion;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getMaxDistanceInmeters() {
		return maxDistanceInmeters;
	}

	public void setMaxDistanceInmeters(Integer maxDistanceInmeters) {
		this.maxDistanceInmeters = maxDistanceInmeters;
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

}
