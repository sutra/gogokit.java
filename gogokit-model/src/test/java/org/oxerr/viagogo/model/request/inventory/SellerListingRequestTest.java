package org.oxerr.viagogo.model.request.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;

class SellerListingRequestTest {

	@Test
	void testFrom() throws IOException {
		HALMapper objectMapper = newObjectMapper();

		try (var content = this.getClass().getResourceAsStream("sellerListings.json")) {
			var sellerListings = objectMapper.readValue(content, new TypeReference<PagedResource<SellerListing>>() {
			});
			assertEquals("https://api.viagogo.net/v2/sellerlistings?page_size=1&page=2",
					sellerListings.getNextLink().getHref());
			var r = SellerListingRequest.from(sellerListings.getNextLink());
			assertNotNull(r);
			assertEquals(2, r.getPage().intValue());
			assertEquals(1, r.getPageSize().intValue());
		}
	}

	private HALMapper newObjectMapper() {
		HALMapper objectMapper = new HALMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		objectMapper.setSerializationInclusion(Include.NON_ABSENT);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
		return objectMapper;
	}

}
