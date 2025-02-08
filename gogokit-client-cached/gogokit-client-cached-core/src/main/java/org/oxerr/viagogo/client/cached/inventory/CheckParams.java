package org.oxerr.viagogo.client.cached.inventory;

public class CheckParams implements CheckOptions {

	/**
	 * The chunk size to load keys from cache.
	 */
	private int pageSize = 10_000;

	/**
	 * The page size when do check.
	 */
	private int chunkSize = 0;

	@Override
	public CheckOptions pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	@Override
	public CheckOptions chunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
		return this;
	}

	public int pageSize() {
		return pageSize;
	}

	public int chunkSize() {
		return chunkSize;
	}

}
