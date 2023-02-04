package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsClient;
import org.oxerr.viagogo.client.catalog.EventClient;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;
import org.redisson.api.RedissonClient;

public class CachedViagogoClient implements ViagogoClient {

	private ViagogoClient client;

	private CachedSellerListingsClient cachedSellerListingsClient;

	public CachedViagogoClient(ViagogoClient client, RedissonClient redission) {
		this.client = client;

		this.cachedSellerListingsClient = new CachedSellerListingsClient(
			client.sellerListingsClient(),
			redission
		);
	}

	@Override
	public EventClient eventClient() {
		return this.client.eventClient();
	}

	@Override
	public SellerListingsClient sellerListingsClient() {
		return this.cachedSellerListingsClient;
	}

}
