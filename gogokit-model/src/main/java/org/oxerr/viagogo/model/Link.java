package org.oxerr.viagogo.model;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.openapitools.jackson.dataformat.hal.HALLink;

public final class Link {

	private Map<String, List<String>> params;

	public Link(String href) {
		URI uri = URI.create(href);
		this.params = Pattern.compile("&")
			.splitAsStream(uri.getQuery())
			.map(s -> Arrays.copyOf(s.split("=", 2), 2))
			.collect(Collectors.groupingBy(s -> decode(s[0]), Collectors.mapping(s -> decode(s[1]), Collectors.toList())));
	}
	public Link(HALLink halLink) {
		this(halLink.getHref());
	}

	public Optional<String> getFirst(String name) {
		return Optional.ofNullable(params.get(name))
			.orElseGet(Collections::emptyList)
			.stream()
			.findFirst();
	}

	public String getFirstAsString(String name) {
		return getFirst(name).orElse(null);
	}

	public Long getFirstAsLong(String name) {
		return getFirst(name).map(Long::parseLong).orElse(null);
	}

	public Integer getFirstAsInteger(String name) {
		return getFirst(name).map(Integer::parseInt).orElse(null);
	}

	public Double getFirstAsDouble(String name) {
		return getFirst(name).map(Double::parseDouble).orElse(null);
	}

	public Instant getFirstAsInstant(String name) {
		return getFirst(name).map(Instant::parse).orElse(null);
	}

	public LocalDateTime getFirstAsLocalDateTime(String name) {
		return getFirst(name).map(LocalDateTime::parse).orElse(null);
	}

	private static String decode(final String encoded) {
		return Optional.ofNullable(encoded)
			.map(e -> URLDecoder.decode(e, StandardCharsets.UTF_8))
			.orElse(null);
	}

}
