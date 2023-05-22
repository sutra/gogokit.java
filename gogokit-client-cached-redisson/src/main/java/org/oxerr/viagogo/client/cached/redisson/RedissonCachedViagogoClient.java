package org.oxerr.viagogo.client.cached.redisson;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.CachedViagogoClient;
import org.oxerr.viagogo.client.cached.redisson.inventory.RedissonCachedSellerListingsService;
import org.redisson.api.RedissonClient;

public class RedissonCachedViagogoClient implements CachedViagogoClient {

	private final ViagogoClient client;

	private final RedissonCachedSellerListingsService cachedSellerListingsService;

	public RedissonCachedViagogoClient(
		ViagogoClient client,
		RedissonClient redission,
		String keyPrefix
	) {
		this.client = client;
		this.cachedSellerListingsService = new RedissonCachedSellerListingsService(
			client.getSellerListingService(),
			redission,
			keyPrefix
		);
	}

	@Override
	public ViagogoClient getClient() {
		return client;
	}

	@Override
	public RedissonCachedSellerListingsService getCachedSellerListingsService() {
		return cachedSellerListingsService;
	}

}
