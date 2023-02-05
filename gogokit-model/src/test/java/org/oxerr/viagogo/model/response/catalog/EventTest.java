package org.oxerr.viagogo.model.response.catalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;

class EventTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testSetStartDate() throws JsonProcessingException {
		Event event = new Event();
		event.setStartDate(Instant.now());

		ObjectMapper objectMapper = new HALMapper()
			.registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		var json = objectMapper.writeValueAsString(event);
		assertNotNull(json);

		log.debug("json: {}", json);
	}

}
