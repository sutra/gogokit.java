package org.oxerr.viagogo.client.cached.inventory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.viagogo.client.cached.CachedViagogoClient;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import si.mazi.rescu.HttpStatusIOException;

public class CachedSellerListingsService implements SellerListingService {

	private final Logger log = LogManager.getLogger();

	private final CachedViagogoClient client;

	private final SellerListingService sellerListingsService;

	private final RedissonClient redisson;

	private final String cacheName;

	public CachedSellerListingsService(
		CachedViagogoClient cachedViagogoClient,
		SellerListingService sellerListingsService,
		RedissonClient redisson
	) {
		this.client = cachedViagogoClient;
		this.sellerListingsService = sellerListingsService;
		this.redisson = redisson;

		this.cacheName = String.format(
			"%s:externalId-sellerListingCreation",
			this.getClass().getName()
		);
	}

	@Override
	public PagedResource<SellerListing> getSellerListings(SellerListingRequest r) throws IOException {
		return this.sellerListingsService.getSellerListings(r);
	}

	@Override
	public SellerListing createListingForRequestedEvent(CreateSellerListingRequest createSellerListingRequest) throws IOException {
		final SellerListing sellerListing;

		final String externalId = createSellerListingRequest.getExternalId();

		var cache = this.getSellerListingCreationCache();

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			SellerListingCreation creation = cache.get(externalId);
			log.debug("[{}] Creation: {}", externalId, creation);

			if (creation != null && creation.isEqual(createSellerListingRequest)) {
				log.debug("[{}] Skip calling API, return seller listing from cache directly.", externalId);
				sellerListing = creation.getSellerListing();
			} else {
				log.debug("[]{} Calling API.", externalId);
				sellerListing = this.sellerListingsService.createListingForRequestedEvent(createSellerListingRequest);
				creation = new SellerListingCreation(createSellerListingRequest, sellerListing);
				var ttl = Duration.between(Instant.now(), createSellerListingRequest.getEvent().getStartDate()).toDays();
				cache.fastPut(externalId, creation, ttl, TimeUnit.DAYS);
			}

		} finally {
			rwLock.writeLock().unlock();
		}

		return sellerListing;
	}

	@Override
	public void deleteListingByExternalListingId(String externalId) throws IOException {
		var cache = this.getSellerListingCreationCache();

		final SellerListingCreation creation = cache.get(externalId);

		if (creation == null && this.client.isDeleteOnlyInCache()) {
			log.debug("[{}] Creation is null, but only delete the listing that in cahce, skip deleting.", externalId);
		} else if (creation != null && creation.isEmpty()) {
			log.debug("[{}] Creation is empty, skip deleting.", externalId);

			final IOException e = creation.getException();

			if (e != null) {
				throw e;
			}
		} else {
			this.deleteListingByExternalListingId(externalId, cache);
		}
	}

	private void deleteListingByExternalListingId(
		String externalId,
		RMapCache<String, SellerListingCreation> cache
	) throws IOException {
		log.debug("[{}] Deleting...", externalId);

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			this.sellerListingsService.deleteListingByExternalListingId(externalId);
			cache.fastPut(externalId, new SellerListingCreation(), 365, TimeUnit.DAYS);

			log.debug("[{}] Deleted.", externalId);
		} catch (IOException e) {
			log.debug("[{}] Delete failed: {}", externalId, e.getMessage());

			if (e instanceof HttpStatusIOException) {
				final int httpStatusCode = ((HttpStatusIOException) e).getHttpStatusCode();

				if (httpStatusCode == 404) {
					cache.fastPut(externalId, new SellerListingCreation(e), 365, TimeUnit.DAYS);
					log.debug("[{}] Not exists, marked as empty in cache.", externalId);
				}
			}

			throw e;
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	protected RMapCache<String, SellerListingCreation> getSellerListingCreationCache() {
		log.trace("Getting map: {}", this.cacheName);
		return redisson.getMapCache(this.cacheName, MapOptions.defaults());
	}

}
