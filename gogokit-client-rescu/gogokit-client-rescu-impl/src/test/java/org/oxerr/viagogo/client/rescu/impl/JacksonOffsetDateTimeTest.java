package org.oxerr.viagogo.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class JacksonOffsetDateTimeTest {

	private final Logger log = LogManager.getLogger();

	static class Event {

		private OffsetDateTime startDate;

		public OffsetDateTime getStartDate() {
			return startDate;
		}

		public void setStartDate(OffsetDateTime startDate) {
			this.startDate = startDate;
		}
	}

	@Test
	void testOffsetDateTime() throws JsonMappingException, JsonProcessingException {
		// given
		String content = "{\"start_date\": \"2023-05-03T20:00:00-07:00\"}";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

		// when
		Event event = objectMapper.readValue(content, Event.class);

		// then
		log.info("startDate: {}", event.getStartDate());
		assertEquals(OffsetDateTime.parse("2023-05-03T20:00:00-07:00"), event.getStartDate());
	}

}
