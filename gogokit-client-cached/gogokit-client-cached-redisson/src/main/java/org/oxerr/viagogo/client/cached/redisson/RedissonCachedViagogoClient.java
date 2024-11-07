package org.oxerr.viagogo.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.CachedViagogoClient;
import org.oxerr.viagogo.client.cached.redisson.inventory.RedissonCachedSellerListingService;
import org.redisson.api.RedissonClient;

public class RedissonCachedViagogoClient implements CachedViagogoClient {

	private final ViagogoClient viagogoClient;

	private final RedissonCachedSellerListingService cachedSellerListingService;

	public RedissonCachedViagogoClient(
		ViagogoClient viagogoClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this(viagogoClient, redissonClient, keyPrefix, ForkJoinPool.commonPool());
	}

	public RedissonCachedViagogoClient(
		ViagogoClient viagogoClient,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(
			viagogoClient,
			new RedissonCachedSellerListingService(
				viagogoClient.getSellerListingService(),
				redissonClient,
				keyPrefix,
				executor
			)
		);
	}

	@Deprecated(since = "5.0.0", forRemoval = true)
	public RedissonCachedViagogoClient(
		ViagogoClient viagogoClient,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		this(
			viagogoClient,
			new RedissonCachedSellerListingService(
				viagogoClient.getSellerListingService(),
				redissonClient,
				keyPrefix,
				executor,
				create
			)
		);
	}

	public RedissonCachedViagogoClient(
		ViagogoClient viagogoClient,
		RedissonCachedSellerListingService cachedSellerListingService
	) {
		this.viagogoClient = viagogoClient;
		this.cachedSellerListingService = cachedSellerListingService;
	}

	@Override
	public ViagogoClient getClient() {
		return viagogoClient;
	}

	@Override
	public RedissonCachedSellerListingService getCachedSellerListingsService() {
		return this.getCachedSellerListingService();
	}

	@Override
	public RedissonCachedSellerListingService getCachedSellerListingService() {
		return cachedSellerListingService;
	}

}
