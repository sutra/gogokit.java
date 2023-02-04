package org.oxerr.viagogo.client.cached.inventory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;
import org.oxerr.viagogo.model.ViagogoException;
import org.oxerr.viagogo.model.request.NewSellerListing;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.SellerListing;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import si.mazi.rescu.HttpStatusIOException;

public class CachedSellerListingsClient implements SellerListingsClient {

	private final Logger log = LogManager.getLogger();

	private final SellerListingsClient sellerListingsClient;

	private final RedissonClient redisson;

	private final String cacheName;

	public CachedSellerListingsClient(
		SellerListingsClient sellerListingsClient,
		RedissonClient redisson
	) {
		this.redisson = redisson;
		this.sellerListingsClient = sellerListingsClient;

		this.cacheName = String.format(
			"%s:externalId-sellerListingCreation",
			this.getClass().getName()
		);
	}

	@Override
	public PagedResource<SellerListing> getAll(Long eventId, String requestedEventId, int page, int pageSize,
			Instant updatedSince, String sort) throws ViagogoException, IOException {
		return this.sellerListingsClient.getAll(eventId, requestedEventId, page, pageSize, updatedSince, sort);
	}

	public SellerListing create(NewSellerListing newSellerListing) throws ViagogoException, IOException {
		final SellerListing sellerListing;

		final String externalId = newSellerListing.getExternalId();

		var cache = this.getSellerListingCreationCache();

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			SellerListingCreation creation = cache.get(externalId);
			log.debug("[{}] Creation: {}", externalId, creation);

			if (creation != null && creation.isEqual(newSellerListing)) {
				log.debug("[{}] Skip calling API, return seller listing from cache directly.", externalId);
				sellerListing = creation.getSellerListing();
			} else {
				log.debug("[]{} Calling API.", externalId);
				sellerListing = this.sellerListingsClient.create(newSellerListing);
				creation = new SellerListingCreation(newSellerListing, sellerListing);
				var ttl = Duration.between(Instant.now(), newSellerListing.getEvent().getStartDate()).toDays();
				cache.fastPut(externalId, creation, ttl, TimeUnit.DAYS);
			}

		} finally {
			rwLock.writeLock().unlock();
		}

		return sellerListing;
	}

	public void delete(@PathParam("externalId") String externalId) throws IOException {
		var cache = this.getSellerListingCreationCache();

		final SellerListingCreation creation = cache.get(externalId);

		if (creation != null && creation.isEmpty()) {
			log.debug("[{}] Creation is empty, skip deleting.", externalId);

			final IOException e = creation.getException();
			if (e != null) {
				throw e;
			}
		}

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			this.sellerListingsClient.delete(externalId);
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
