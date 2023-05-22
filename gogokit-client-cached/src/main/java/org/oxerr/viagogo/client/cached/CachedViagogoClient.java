package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;

public interface CachedViagogoClient {

	ViagogoClient getClient();

	CachedSellerListingsService getCachedSellerListingsService();

}
