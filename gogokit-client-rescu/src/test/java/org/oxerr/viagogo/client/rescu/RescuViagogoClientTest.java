package org.oxerr.viagogo.client.rescu;

import java.io.IOException;
import java.util.Properties;

public class RescuViagogoClientTest {

	public static RescuViagogoClient getClient() {
		Properties props = new Properties();
		try (var in = RescuViagogoClientTest.class.getResourceAsStream("/viagogo.properties")) {
			props.load(in);
		} catch (IOException e) {
			throw new java.lang.IllegalArgumentException("Read /viagogo.properties failed.");
		}

		var token = props.getProperty("token");
		return new RescuViagogoClient(token);
	}

}
