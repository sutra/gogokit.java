package org.oxerr.viagogo.model.request.catalog;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Link;

import io.openapitools.jackson.dataformat.hal.HALLink;

public class SearchEventRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	private String q;

	private LocalDateTime dateLocal;

	private Integer page;

	private Integer pageSize;

	private Instant updatedSince;

	private String sort;

	private Long minResourceVersion;

	private String countryCode;

	private Double latitude;

	private Double longitude;

	private Integer maxDistanceInMeters;

	private Integer genreId;

	public static SearchEventRequest from(HALLink halLink) {
		Link link = new Link(halLink);
		SearchEventRequest r = new SearchEventRequest();
		r.setQ(link.getFirstAsString("q"));
		r.setDateLocal(link.getFirstAsLocalDateTime("dateLocal"));
		r.setPage(link.getFirstAsInteger("page"));
		r.setPageSize(link.getFirstAsInteger("page_size"));
		r.setUpdatedSince(link.getFirstAsInstant("updated_since"));
		r.setSort(link.getFirstAsString("sort"));
		r.setMinResourceVersion(link.getFirstAsLong("min_resource_version"));
		r.setCountryCode(link.getFirstAsString("country_code"));
		r.setLatitude(link.getFirstAsDouble("latitude"));
		r.setLongitude(link.getFirstAsDouble("longitude"));
		r.setMaxDistanceInMeters(link.getFirstAsInteger("max_distance_in_meters"));
		r.setGenreId(link.getFirstAsInteger("genre_id"));
		return r;
	}

	public SearchEventRequest() {
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

	public Integer getMaxDistanceInMeters() {
		return maxDistanceInMeters;
	}

	public void setMaxDistanceInMeters(Integer maxDistanceInMeters) {
		this.maxDistanceInMeters = maxDistanceInMeters;
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
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
		SearchEventRequest rhs = (SearchEventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
