package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingService;

public interface CachedViagogoClient {

	ViagogoClient getClient();

	@Deprecated(since = "6.5.0", forRemoval = true)
	CachedSellerListingService getCachedSellerListingsService();

	CachedSellerListingService getCachedSellerListingService();

}
