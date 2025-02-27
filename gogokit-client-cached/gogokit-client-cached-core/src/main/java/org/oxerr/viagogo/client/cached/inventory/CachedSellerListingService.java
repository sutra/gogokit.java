package org.oxerr.viagogo.client.cached.inventory;

import org.oxerr.ticket.inventory.support.cached.CachedListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;

public interface CachedSellerListingService
	extends CachedListingService<String, String, CreateSellerListingRequest, ViagogoListing, ViagogoEvent> {

	/**
	 * Check all listings and delete which not in cache.
	 */
	void check();

	/**
	 * Check all listings and delete which not in cache.
	 *
	 * @param options the check options.
	 * @since 6.6.0
	 */
	void check(CheckOptions options);

}
