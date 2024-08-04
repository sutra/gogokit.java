package org.oxerr.viagogo.client.cached.redisson.inventory;

/**
 * Represents a retry configuration.
 *
 * @since 4.2.0
 */
public class RetryConfiguration {

	private final int maxAttempts;

	private final int maxDelay;

	public RetryConfiguration() {
		this(10, 1_000);
	}

	public RetryConfiguration(int maxAttempts, int maxDelay) {
		this.maxAttempts = maxAttempts;
		this.maxDelay = maxDelay;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public int getMaxDelay() {
		return maxDelay;
	}

}
