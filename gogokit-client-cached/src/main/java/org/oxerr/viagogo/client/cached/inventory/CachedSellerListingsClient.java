package org.oxerr.viagogo.client.cached.inventory;

import java.io.IOException;

import javax.ws.rs.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;
import org.oxerr.viagogo.model.ViagogoException;
import org.oxerr.viagogo.model.request.NewSellerListing;
import org.oxerr.viagogo.model.response.SellerListing;
import org.redisson.api.RMap;
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

	public SellerListing create(NewSellerListing newSellerListing) throws ViagogoException, IOException {
		final SellerListing sellerListing;

		final String externalId = newSellerListing.getExternalId();

		final RMap<String, SellerListingCreation> cache = this.getSellerListingCreationCache();

		final RReadWriteLock rwLock = cache.getReadWriteLock(externalId);

		rwLock.writeLock().lock();

		try {
			final SellerListingCreation creation = cache.get(externalId);
			log.debug("[{}] Creation: {}", externalId, creation);

			if (creation != null && creation.isEqual(newSellerListing)) {
				log.debug("[{}] Skip calling API, return seller listing from cache directly.", externalId);
				sellerListing = creation.getSellerListing();
			} else {
				log.debug("[]{} Calling API.", externalId);
				sellerListing = this.sellerListingsClient.create(newSellerListing);
				cache.put(externalId, new SellerListingCreation(newSellerListing, sellerListing));
			}

		} finally {
			rwLock.writeLock().unlock();
		}

		return sellerListing;
	}

	public void delete(@PathParam("externalId") String externalId) throws IOException {
		final RMap<String, SellerListingCreation> cache = this.getSellerListingCreationCache();

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
			this.getSellerListingCreationCache().put(externalId, new SellerListingCreation());

			log.debug("[{}] Deleted.", externalId);
		} catch (IOException e) {
			log.debug("[{}] Delete failed: {}", externalId, e.getMessage());

			if (e instanceof HttpStatusIOException) {
				final int httpStatusCode = ((HttpStatusIOException) e).getHttpStatusCode();

				if (httpStatusCode == 404) {
					this.getSellerListingCreationCache().put(externalId, new SellerListingCreation(e));
					log.debug("[{}] Not exists, marked as empty in cache.", externalId);
				}
			}

			throw e;
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	protected RMap<String, SellerListingCreation> getSellerListingCreationCache() {
		log.trace("Getting map: {}", this.cacheName);
		return redisson.getMap(this.cacheName);
	}

}
