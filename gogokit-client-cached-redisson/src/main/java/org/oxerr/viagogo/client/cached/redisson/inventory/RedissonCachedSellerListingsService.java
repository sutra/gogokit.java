package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.cached.inventory.Event;
import org.oxerr.viagogo.client.cached.inventory.Listing;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

public class RedissonCachedSellerListingsService implements CachedSellerListingsService {

	private final Logger log = LogManager.getLogger();

	private final SellerListingService sellerListingsService;

	private final RedissonClient redisson;

	/**
	 * The key prefix for Redis entries(lock & cache).
	 */
	private final String keyPrefix;

	// Event ID -> <Listing ID, CachedListing>
	private final RMapCache<String, Map<String, CachedListing>> listingCache;

	public RedissonCachedSellerListingsService(
		SellerListingService sellerListingsService,
		RedissonClient redisson,
		String keyPrefix
	) {
		this.sellerListingsService = sellerListingsService;
		this.redisson = redisson;
		this.keyPrefix = keyPrefix;

		var cacheName = String.format("%s:externalId-sellerListingCreation", keyPrefix);
		this.listingCache = redisson.getMapCache(cacheName, MapOptions.defaults());
	}

	public RMapCache<String, Map<String, CachedListing>> getListingCache() {
		return listingCache;
	}

	public void updateEvent(Event event) {
		String lockName = String.format("%s:event:lock:%s", keyPrefix, event.getId());
		RReadWriteLock rwLock = redisson.getReadWriteLock(lockName);

		rwLock.writeLock().lock();

		try {
			this.doUpdateEvent(event);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	public void doUpdateEvent(Event event) {
		Map<String, CachedListing> cache = this.getOrCreateCache(event);

		try {
			this.updateEvent(event, cache);
		} finally {
			this.saveCache(event, cache);
		}
	}

	private void updateEvent(Event event, Map<String, CachedListing> cache) {
		// delete
		this.delete(event, cache);

		// create/update
		this.create(event, cache);
	}

	private void delete(Event event, Map<String, CachedListing> cache) {
		Set<String> inventoryTicketIds = event.getListings()
			.stream()
			.map(Listing::getId)
			.collect(Collectors.toSet());

		Set<String> pendingDeleteTicketIds = cache.keySet()
			.stream()
			.filter(t -> !inventoryTicketIds.contains(t))
			.collect(Collectors.toSet());

		for (String ticketId : pendingDeleteTicketIds) {
			log.trace("deleting {}", ticketId);

			try {
				this.sellerListingsService.deleteListingByExternalListingId(ticketId);
			} catch (IOException e) {
				log.warn("Delete ticket {} failed.", ticketId, e);
			}
		}
	}

	private void create(Event event, Map<String, CachedListing> cache) {
		List<Listing> pendingCreateListings = event.getListings()
			.stream()
			.filter(listing -> CachedListing.shouldCreate(listing, cache.get(listing.getId())))
			.collect(Collectors.toList());

		Map<String, CachedListing> pendings = pendingCreateListings
			.stream()
			.collect(Collectors.toMap(Listing::getId, CachedListing::pending));

		cache.putAll(pendings);

		// Make sure the cache is saved even if program is killed
		this.saveCache(event, cache);

		// create
		for (Listing listing : pendingCreateListings) {
			log.trace("Creating {}", listing.getId());

			try {
				this.sellerListingsService.createListing(listing.getViagogoEventId(), listing.getRequest());
				cache.put(listing.getId(), CachedListing.listed(listing));
			} catch (IOException e) {
				log.warn("Create ticket {} failed.", listing.getId(), e);
			}
		}
	}

	private Map<String, CachedListing> getOrCreateCache(Event event) {
		Map<String, CachedListing> cache = this.listingCache.get(event.getId());
		return Optional.ofNullable(cache).orElseGet(HashMap::new);
	}

	private void saveCache(Event event, Map<String, CachedListing> listings) {
		long ttl = ttl(event);
		this.listingCache.fastPut(event.getId(), listings, ttl, TimeUnit.MINUTES);
	}

	private long ttl(Event event) {
		return Math.max(1, Duration.between(Instant.now(), event.getStartDate()).toMinutes());
	}

	@Override
	public long cacheSize() {
		return this.getListingCache().size();
	}

	@Override
	public long listedCount() {
		return countListings(this.getListingCache());
	}

	static long countListings(RMapCache<String, Map<String, CachedListing>> listingsCache) {
		return listingsCache.values()
			.stream()
			.map((Map<String, CachedListing> listings) -> listings.values().stream().filter(l -> l.getStatus() == Status.LISTED).count())
			.reduce(0L, Long::sum);
	}

}
