package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerListingsService;
import org.redisson.api.RedissonClient;

public class CachedViagogoClient implements ViagogoClient {

	private ViagogoClient client;

	private CachedSellerListingsService cachedSellerListingsService;

	public CachedViagogoClient(ViagogoClient client, RedissonClient redission) {
		this.client = client;

		this.cachedSellerListingsService = new CachedSellerListingsService(
			client.getSellerListingsService(),
			redission
		);
	}

	@Override
	public EventService getEventService() {
		return this.client.getEventService();
	}

	@Override
	public SellerListingsService getSellerListingsService() {
		return this.cachedSellerListingsService;
	}

}
