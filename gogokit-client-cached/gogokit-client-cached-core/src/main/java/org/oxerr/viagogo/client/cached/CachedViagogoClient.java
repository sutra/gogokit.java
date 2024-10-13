package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingService;

public interface CachedViagogoClient {

	ViagogoClient getClient();

	CachedSellerListingService getCachedSellerListingsService();

}
