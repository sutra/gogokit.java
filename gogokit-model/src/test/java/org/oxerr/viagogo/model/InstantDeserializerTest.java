package org.oxerr.viagogo.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class InstantDeserializerTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testDateFormat() throws ParseException {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").withZone(ZoneId.systemDefault());
		var s = fmt.format(Instant.now());
		log.info("{}", s);

		Instant instant = Instant.from(fmt.parse("2023-02-24T19:30:00+11:00"));
		assertNotNull(instant);
	}

}
