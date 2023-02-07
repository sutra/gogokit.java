package org.oxerr.viagogo.client.cached;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.redisson.api.RedissonClient;

public class CachedViagogoClient implements ViagogoClient {

	private ViagogoClient client;

	private CachedSellerListingsService cachedSellerListingsService;

	public CachedViagogoClient(ViagogoClient client, RedissonClient redission) {
		this.client = client;

		this.cachedSellerListingsService = new CachedSellerListingsService(
			client.getSellerListingService(),
			redission
		);
	}

	@Override
	public EventService getEventService() {
		return this.client.getEventService();
	}

	@Override
	public SellerListingService getSellerListingService() {
		return this.cachedSellerListingsService;
	}

	@Override
	public SellerEventService getSellerEventService() {
		return this.client.getSellerEventService();
	}

}
