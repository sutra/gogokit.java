package org.oxerr.viagogo.client.cached.redisson.inventory;

class RetryableException extends RuntimeException {

	private static final long serialVersionUID = 2023120801L;

	public RetryableException() {
		super();
	}

	public RetryableException(String message) {
		super(message);
	}

	public RetryableException(Throwable cause) {
		super(cause);
	}

}
