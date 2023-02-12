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

	private final RMapCache<String, SellerListingCreation> sellerListingCreationCache;

	public CachedSellerListingsService(
		CachedViagogoClient cachedViagogoClient,
		SellerListingService sellerListingsService,
		RedissonClient redisson
	) {
		this.client = cachedViagogoClient;
		this.sellerListingsService = sellerListingsService;

		var cacheName = String.format("%s:externalId-sellerListingCreation", getClass().getName());
		this.sellerListingCreationCache = redisson.getMapCache(cacheName, MapOptions.defaults());
	}

	@Override
	public SellerListing getSellerListing(Long listingId) throws IOException {
		return this.sellerListingsService.getSellerListing(listingId);
	}

	@Override
	public PagedResource<SellerListing> getSellerListings(SellerListingRequest r) throws IOException {
		return this.sellerListingsService.getSellerListings(r);
	}

	@Override
	public SellerListing createListingForRequestedEvent(CreateSellerListingRequest createSellerListingRequest) throws IOException {
		final SellerListing sellerListing;

		final String externalId = createSellerListingRequest.getExternalId();

		final RReadWriteLock rwLock = this.sellerListingCreationCache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			SellerListingCreation creation = this.sellerListingCreationCache.get(externalId);

			log.debug("externalId[{}]. Creation: {}.", externalId, creation);

			if (creation != null && creation.isEqual(createSellerListingRequest)) {
				log.debug("externalId[{}]. Skip calling API, return seller listing from cache directly.", externalId);

				sellerListing = creation.getResponse();
			} else {
				log.debug("externalId[{}]. Calling API.", externalId);

				sellerListing = this.sellerListingsService.createListingForRequestedEvent(createSellerListingRequest);
				creation = new SellerListingCreation(createSellerListingRequest, sellerListing);
				var ttl = Duration.between(Instant.now(), createSellerListingRequest.getEvent().getStartDate()).toDays();
				this.sellerListingCreationCache.fastPut(externalId, creation, ttl, TimeUnit.DAYS);
			}

		} finally {
			rwLock.writeLock().unlock();
		}

		return sellerListing;
	}

	@Override
	public void deleteListingByExternalListingId(String externalId) throws IOException {
		final SellerListingCreation creation = this.sellerListingCreationCache.get(externalId);

		if (creation == null && this.client.isDeleteOnlyInCache()) {
			log.debug("externalId[{}]. Creation is null, but only delete the listing that in cahce, skip deleting.", externalId);
		} else if (creation != null && creation.isEmpty()) {
			log.debug("externalId[{}]. Creation is empty, skip deleting.", externalId);

			final IOException e = creation.getException();

			if (e != null) {
				throw e;
			}
		} else {
			this.deleteListingByExternalListingId(externalId, this.sellerListingCreationCache);
		}
	}

	private void deleteListingByExternalListingId(
		String externalId,
		RMapCache<String, SellerListingCreation> cache
	) throws IOException {
		log.debug("externalId[{}]. Deleting...", externalId);

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			this.sellerListingsService.deleteListingByExternalListingId(externalId);
			cache.fastPut(externalId, new SellerListingCreation(), 365, TimeUnit.DAYS);

			log.debug("externalId[{}]. Deleted.", externalId);
		} catch (IOException e) {
			log.debug("externalId[{}]. Delete failed: {}", externalId, e.getMessage());

			if (e instanceof HttpStatusIOException) {
				final int httpStatusCode = ((HttpStatusIOException) e).getHttpStatusCode();

				if (httpStatusCode == 404) {
					cache.fastPut(externalId, new SellerListingCreation(e), 365, TimeUnit.DAYS);

					log.debug("externalId[{}]. Not exists, marked as empty in cache.", externalId);
				}
			}

			throw e;
		} finally {
			rwLock.writeLock().unlock();
		}
	}

}
