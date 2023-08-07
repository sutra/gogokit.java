package org.oxerr.viagogo.model.topic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;

class SalesTopicTest {

	private static HALMapper HAL_MAPPER;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		HAL_MAPPER = new HALMapper();
		HAL_MAPPER.registerModule(new JavaTimeModule());
		HAL_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		HAL_MAPPER.setSerializationInclusion(Include.NON_ABSENT);
		HAL_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		HAL_MAPPER.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		HAL_MAPPER.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
	}

	@Test
	void test() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("sales.json")) {
			SalesTopic salesTopic = HAL_MAPPER.readValue(src, SalesTopic.class);
			assertEquals("Sales", salesTopic.getTopic());
			assertEquals("Created", salesTopic.getAction());
			assertEquals(151566529L, salesTopic.getEvent().getId().longValue());
			assertEquals("Counting Crows", salesTopic.getEvent().getName());
			assertEquals("2023-08-05T20:00-04:00", salesTopic.getEvent().getStartDate().toString());
			assertEquals("2023-03-14T10:00-04:00", salesTopic.getEvent().getOnSaleDate().toString());
			assertEquals(false, salesTopic.getEvent().getDateConfirmed().booleanValue());
			assertEquals("https://api.viagogo.net/v2/events/151566529", salesTopic.getEvent().getSelfLink().getHref());
			assertEquals(false, salesTopic.getEvent().getSelfLink().getTemplated().booleanValue());
			assertEquals(520443304, salesTopic.getSale().getId());
			assertEquals("2023-08-05T01:36:32.123Z", salesTopic.getSale().getCreatedAt().toString());
			assertEquals("204", salesTopic.getSale().getSeating().getSection());
			assertEquals("K", salesTopic.getSale().getSeating().getRow());
			assertEquals("3", salesTopic.getSale().getSeating().getSeatFrom());
			assertEquals("3", salesTopic.getSale().getSeating().getSeatTo());
			assertEquals(new BigDecimal("144.16"), salesTopic.getSale().getProceeds().getAmount());
			assertEquals("USD", salesTopic.getSale().getProceeds().getCurrencyCode());
			assertEquals("$144.16", salesTopic.getSale().getProceeds().getDisplay());
			assertEquals(1, salesTopic.getSale().getNumberOfTickets().intValue());
			assertEquals("https://api.viagogo.net/v2/sales/520443304", salesTopic.getSale().getSelfLink().getHref());
			assertEquals(false, salesTopic.getSale().getSelfLink().getTemplated());
			assertEquals(6312463625L, salesTopic.getSellerListing().getId().longValue());
			assertEquals("1637496733673984041", salesTopic.getSellerListing().getExternalId());
			assertEquals("2023-05-13T13:31:30.303Z", salesTopic.getSellerListing().getCreatedAt().toString());
			assertEquals(0, salesTopic.getSellerListing().getNumberOfTickets().intValue());
			assertEquals("204", salesTopic.getSellerListing().getSeating().getSection());
			assertEquals("K", salesTopic.getSellerListing().getSeating().getRow());
			assertEquals("4", salesTopic.getSellerListing().getSeating().getSeatFrom());
			assertEquals("3", salesTopic.getSellerListing().getSeating().getSeatTo());
			assertEquals(new BigDecimal("160.18"), salesTopic.getSellerListing().getTicketPrice().getAmount());
			assertEquals("USD", salesTopic.getSellerListing().getTicketPrice().getCurrencyCode());
			assertEquals("$160.18", salesTopic.getSellerListing().getTicketPrice().getDisplay());
			assertEquals("https://api.viagogo.net/v2/sellerlistings/6312463625", salesTopic.getSellerListing().getSelfLink().getHref());
			assertEquals(false, salesTopic.getSellerListing().getSelfLink().getTemplated());
			assertEquals(96533L, salesTopic.getVenue().getId().longValue());
			assertEquals("Hard Rock Live at Seminole Hard Rock Hotel & Casino Hollywood - Complex", salesTopic.getVenue().getName());
			assertEquals("Hollywood", salesTopic.getVenue().getCity());
			assertEquals(26.0513, salesTopic.getVenue().getLatitude().doubleValue());
			assertEquals(-80.2121, salesTopic.getVenue().getLongitude().doubleValue());
			assertEquals("https://api.viagogo.net/v2/venues/96533", salesTopic.getVenue().getSelfLink().getHref());
			assertEquals(false, salesTopic.getVenue().getSelfLink().getTemplated());
			assertEquals("US", salesTopic.getVenue().getCountry().getCode());
			assertEquals("USA", salesTopic.getVenue().getCountry().getName());
			assertEquals("https://api.viagogo.net/v2/countries/US", salesTopic.getVenue().getCountry().getSelfLink().getHref());
			assertEquals(false, salesTopic.getVenue().getCountry().getSelfLink().getTemplated());
		}
	}

}
