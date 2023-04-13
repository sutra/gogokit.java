package org.oxerr.viagogo.client.rescu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

class ViagogoExceptionTest {

	@Test
	void test404() throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try (var src = this.getClass().getResourceAsStream("404.json")) {
			var e = objectMapper.readValue(src, ViagogoException.class);
			assertEquals("https://tools.ietf.org/html/rfc7231#section-6.5.4", e.getType());
			assertEquals("Not Found", e.getTitle());
			assertEquals("Not Found (HTTP status code: 0)", e.getMessage());
			assertEquals(404, e.getStatus().intValue());
			assertEquals("00-d8db844d908adc759c0509d92faeb1d2-018cf9ec6ef5cff8-00", e.getTraceId());
		}
	}

}
