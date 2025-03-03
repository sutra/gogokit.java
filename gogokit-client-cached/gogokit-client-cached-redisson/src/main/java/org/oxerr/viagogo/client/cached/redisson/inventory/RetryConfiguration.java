package org.oxerr.viagogo.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof RetryConfiguration)) {
			return false;
		}
		RetryConfiguration rhs = (RetryConfiguration) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
