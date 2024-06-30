package org.oxerr.viagogo.client.cached.redisson.inventory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

class ThreadUtilsTest {

	@Test
	void testSleepQuietlyWithZeroTimeout() {
		var duration = Duration.ofMillis(0);
		assertDoesNotThrow(() -> ThreadUtils.sleepQuietly(duration));
	}

	@Test
	void testSleepQuietlyWithNegativeTimeout() {
		var duration = Duration.ofMillis(-1);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> ThreadUtils.sleepQuietly(duration));
		String expectedMessage = "timeout value is negative";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

}
