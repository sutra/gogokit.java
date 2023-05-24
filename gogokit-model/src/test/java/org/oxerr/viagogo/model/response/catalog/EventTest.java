package org.oxerr.viagogo.model.response.catalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;

class EventTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testSetStartDate() throws JsonProcessingException {
		Event event = new Event();
		event.setStartDate(OffsetDateTime.now());

		ObjectMapper objectMapper = new HALMapper()
			.registerModule(new JavaTimeModule())
			.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
			.setSerializationInclusion(Include.NON_ABSENT)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String json = objectMapper.writeValueAsString(event);
		assertNotNull(json);

		log.info("json: {}", json);
	}

}
