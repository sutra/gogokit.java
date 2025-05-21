package org.oxerr.viagogo.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof RedissonCachedViagogoClient)) {
			return false;
		}
		RedissonCachedViagogoClient rhs = (RedissonCachedViagogoClient) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
