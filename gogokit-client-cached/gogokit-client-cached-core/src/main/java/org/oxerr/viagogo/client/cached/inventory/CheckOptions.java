package org.oxerr.viagogo.client.cached.inventory;

/**
 * The options in check listings.
 *
 * @since 6.6.0
 */
public interface CheckOptions {

	static CheckOptions defaults() {
		return new CheckParams();
	}

	CheckOptions pageSize(int pageSize);

	CheckOptions chunkSize(int chunkSize);

	int pageSize();

	int chunkSize();

}
