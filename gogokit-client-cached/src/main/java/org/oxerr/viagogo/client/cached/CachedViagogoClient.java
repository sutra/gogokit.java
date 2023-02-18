package org.oxerr.viagogo.client.cached;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.sale.SaleService;
import org.redisson.api.RedissonClient;

public class CachedViagogoClient implements ViagogoClient {

	private final Logger log = LogManager.getLogger();

	private final ViagogoClient client;

	private final CachedSellerListingsService cachedSellerListingsService;

	private boolean deleteOnlyInCache;

	public CachedViagogoClient(ViagogoClient client, RedissonClient redission) {
		log.info("[CachedViagogoClient] init.");

		this.client = client;

		this.cachedSellerListingsService = new CachedSellerListingsService(
			this,
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

	@Override
	public SaleService getSaleService() {
		return this.client.getSaleService();
	}

	public boolean isDeleteOnlyInCache() {
		return deleteOnlyInCache;
	}

	public CachedViagogoClient deleteOnlyInCache(boolean deleteOnlyInCache) {
		this.deleteOnlyInCache = deleteOnlyInCache;
		return this;
	}

}
