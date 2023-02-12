package org.oxerr.viagogo.model.request.catalog;

import java.io.Serializable;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.openapitools.jackson.dataformat.hal.HALLink;

public class EventRequest implements Serializable {

	private static final long serialVersionUID = 2023021301L;

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

	public static EventRequest from(HALLink link) {
		URI uri = URI.create(link.getHref());
		Map<String, List<String>> params = Pattern.compile("&")
			.splitAsStream(uri.getQuery())
			.map(s -> Arrays.copyOf(s.split("=", 2), 2))
			.collect(Collectors.groupingBy(s -> decode(s[0]), Collectors.mapping(s -> decode(s[1]), Collectors.toList())));
		EventRequest r = new EventRequest();
		r.setPage(getFirstAsInteger(params, "page"));
		r.setPageSize(getFirstAsInteger(params, "page_size"));
		r.setSort(getFirst(params, "sort").orElse(null));
		r.setMinResourceVersion(getFirstAsLong(params, "min_resource_version"));
		r.setCountryCode(getFirstAsString(params, "country_code"));
		r.setLatitude(getFirstAsDouble(params, "latitude"));
		r.setLongitude(getFirstAsDouble(params, "longitude"));
		r.setMaxDistanceInMeters(getFirstAsInteger(params, "max_distance_in_meters"));
		r.setGenreId(getFirstAsInteger(params, "genre_id"));
		return r;
	}

	private static Optional<String> getFirst(Map<String, List<String>> params, String name) {
		return Optional.ofNullable(params.get(name))
			.orElseGet(Collections::emptyList)
			.stream()
			.findFirst();
	}

	private static String getFirstAsString(Map<String, List<String>> params, String name) {
		return getFirst(params, name).orElse(null);
	}

	private static Long getFirstAsLong(Map<String, List<String>> params, String name) {
		return getFirst(params, name).map(Long::parseLong).orElse(null);
	}

	private static Integer getFirstAsInteger(Map<String, List<String>> params, String name) {
		return getFirst(params, name).map(Integer::parseInt).orElse(null);
	}

	private static Double getFirstAsDouble(Map<String, List<String>> params, String name) {
		return getFirst(params, name).map(Double::parseDouble).orElse(null);
	}

	private static String decode(final String encoded) {
		return Optional.ofNullable(encoded)
			.map(e -> URLDecoder.decode(e, StandardCharsets.UTF_8))
			.orElse(null);
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
		EventRequest rhs = (EventRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
