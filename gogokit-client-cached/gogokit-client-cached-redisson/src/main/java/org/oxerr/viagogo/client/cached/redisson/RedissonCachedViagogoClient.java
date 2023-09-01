package org.oxerr.viagogo.client.cached.redisson;

import java.util.concurrent.Executor;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.CachedViagogoClient;
import org.oxerr.viagogo.client.cached.redisson.inventory.RedissonCachedSellerListingsService;
import org.redisson.api.RedissonClient;

public class RedissonCachedViagogoClient implements CachedViagogoClient {

	private final ViagogoClient client;

	private final RedissonCachedSellerListingsService cachedSellerListingsService;

	public RedissonCachedViagogoClient(
		ViagogoClient client,
		RedissonCachedSellerListingsService cachedSellerListingsService
	) {
		this.client = client;
		this.cachedSellerListingsService = cachedSellerListingsService;
	}

	public RedissonCachedViagogoClient(
		ViagogoClient client,
		RedissonClient redissionClient,
		Executor executor,
		String keyPrefix
	) {
		this(client, redissionClient, keyPrefix, executor, true);
	}

	public RedissonCachedViagogoClient(
		ViagogoClient client,
		RedissonClient redissionClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		this.client = client;
		this.cachedSellerListingsService = new RedissonCachedSellerListingsService(
			client.getSellerListingService(),
			redissionClient,
			keyPrefix,
			executor,
			create
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
