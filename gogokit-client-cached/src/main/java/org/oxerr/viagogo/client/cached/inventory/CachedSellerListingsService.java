package org.oxerr.viagogo.client.cached.inventory;

public interface CachedSellerListingsService {

	void updateEvent(Event event);

	/**
	 * Returns the size of the cache.
	 *
	 * @return the size of the cache.
	 */
	long cacheSize();

	/**
	 * Returns the listing count which status is LISTED.
	 *
	 * @return the listing count.
	 */
	long listedCount();

}
