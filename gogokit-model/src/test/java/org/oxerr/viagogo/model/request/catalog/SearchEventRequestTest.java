package org.oxerr.viagogo.model.request.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.openapitools.jackson.dataformat.hal.HALLink;

class SearchEventRequestTest {

	@Test
	void testFrom() {
		String href = "https://api.viagogo.net/catalog/events/search?q=The&dateLocal=2023-05-03T20%3A00&page=2&page_size=500";
		HALLink halLink = new HALLink.Builder(href).build();
		SearchEventRequest r = SearchEventRequest.from(halLink);
		assertEquals("The", r.getQ());
		assertEquals("2023-05-03T20:00", r.getDateLocal().toString());
		assertEquals(2, r.getPage().intValue());
		assertEquals(500, r.getPageSize().intValue());
	}

}
