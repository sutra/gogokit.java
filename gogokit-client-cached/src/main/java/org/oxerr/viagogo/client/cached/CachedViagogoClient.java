package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsClient;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;
import org.redisson.api.RedissonClient;

public class CachedViagogoClient implements ViagogoClient {

	private CachedSellerListingsClient cachedSellerListingsClient;

	public CachedViagogoClient(ViagogoClient client, RedissonClient redission) {
		this.cachedSellerListingsClient = new CachedSellerListingsClient(
			client.sellerListingsClient(),
			redission
		);
	}

	@Override
	public SellerListingsClient sellerListingsClient() {
		return this.cachedSellerListingsClient;
	}

}