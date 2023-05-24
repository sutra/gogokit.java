package org.oxerr.viagogo.client.rescu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class RescuViagogoClientTest {

	private static final Logger LOG = LogManager.getLogger();

	public static RescuViagogoClient getClient() {
		Properties props = new Properties();
		String name = "/viagogo.properties";
		try (InputStream in = RescuViagogoClientTest.class.getResourceAsStream(name)) {
			if (in != null) {
				props.load(in);
			} else {
				LOG.warn("No resource found: {}", name);
			}
		} catch (IOException e) {
			throw new java.lang.IllegalArgumentException("Read " + name + " failed.");
		}

		String token = props.getProperty("token");
		return new RescuViagogoClient(token);
	}

	@Test
	void testGetClient() {
		assertNotNull(RescuViagogoClientTest.getClient());
	}

}
