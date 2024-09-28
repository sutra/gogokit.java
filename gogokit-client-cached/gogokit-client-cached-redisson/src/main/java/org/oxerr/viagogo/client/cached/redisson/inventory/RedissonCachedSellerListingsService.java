package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.util.concurrent.Executor;

import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.redisson.api.RedissonClient;

@Deprecated(since = "6.5.0", forRemoval = true)
public class RedissonCachedSellerListingsService extends RedissonCachedSellerListingService {

	@Deprecated(since = "5.0.0", forRemoval = true)
	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		super(sellerListingService, redissonClient, keyPrefix, executor, create);
	}

	public RedissonCachedSellerListingsService(SellerListingService sellerListingService, RedissonClient redissonClient,
			String keyPrefix, Executor executor) {
		super(sellerListingService, redissonClient, keyPrefix, executor);
	}

	public RedissonCachedSellerListingsService(SellerListingService sellerListingService, RedissonClient redissonClient,
			String keyPrefix, Executor executor, ListingConfiguration listingConfiguration, int pageSize,
			RetryConfiguration retryConfiguration) {
		super(sellerListingService, redissonClient, keyPrefix, executor, listingConfiguration, pageSize, retryConfiguration);
	}

}
