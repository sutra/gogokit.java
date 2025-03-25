package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingService;
import org.oxerr.viagogo.client.cached.inventory.CachedSellerListingsService;
import org.oxerr.viagogo.client.cached.inventory.CheckOptions;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;
import org.oxerr.viagogo.client.cached.inventory.ViagogoListing;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.model.request.inventory.CreateSellerListingRequest;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.redisson.api.RedissonClient;

public class RedissonCachedSellerListingService
	extends RedissonCachedListingServiceSupport<String, String, CreateSellerListingRequest, ViagogoListing, ViagogoEvent, ViagogoCachedListing>
	implements CachedSellerListingsService, CachedSellerListingService {

	private final Logger log = LogManager.getLogger();

	private final SellerListingService sellerListingService;

	/**
	 * The page size when do check.
	 *
	 * @deprecated
	 */
	@Deprecated(forRemoval = true, since = "6.6.0")
	private final int pageSize;

	private final RetryConfiguration retryConfig;

	@Deprecated(since = "5.0.0", forRemoval = true)
	public RedissonCachedSellerListingService(
		SellerListingService sellerListingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		this(sellerListingService, redissonClient, keyPrefix, executor, new ListingConfiguration(create, true, true), 10_000, new RetryConfiguration());
	}

	/**
	 * Constructs with default {@link ListingConfiguration} and default {@link RetryConfiguration}.
	 *
	 * @param sellerListingService the seller listing service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 *
	 * @since 5.0.0
	 */
	public RedissonCachedSellerListingService(
		SellerListingService sellerListingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(sellerListingService, redissonClient, keyPrefix, executor, new ListingConfiguration(), 10_000, new RetryConfiguration());
	}

	/**
	 * Constructs with specified {@link ListingConfiguration} and specified {@link RetryConfiguration}.
	 *
	 * @param sellerListingService the seller listing service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 * @param pageSize the page size when do check.
	 * @param retryConfiguration the retry configuration.
	 *
	 * @since 5.0.0
	 * @deprecated since 6.6.0 for removal in 7.0.0
	 */
	@Deprecated(forRemoval = true, since = "6.6.0")
	public RedissonCachedSellerListingService(
		SellerListingService sellerListingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		ListingConfiguration listingConfiguration,
		int pageSize,
		RetryConfiguration retryConfiguration
	) {
		super(redissonClient, keyPrefix, executor, listingConfiguration);
		this.sellerListingService = sellerListingService;
		this.pageSize = pageSize;
		this.retryConfig = retryConfiguration;
	}

	/**
	 * Constructs with specified {@link ListingConfiguration} and specified {@link RetryConfiguration}.
	 *
	 * @param sellerListingService the seller listing service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 * @param retryConfiguration the retry configuration.
	 *
	 * @since 6.6.0
	 */
	public RedissonCachedSellerListingService(
		SellerListingService sellerListingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		ListingConfiguration listingConfiguration,
		RetryConfiguration retryConfiguration
	) {
		super(redissonClient, keyPrefix, executor, listingConfiguration);
		this.sellerListingService = sellerListingService;
		this.pageSize = 10_000;
		this.retryConfig = retryConfiguration;
	}

	@Override
	protected boolean shouldCreate(
		@Nonnull ViagogoEvent event,
		@Nonnull ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		boolean shouldCreate = super.shouldCreate(event, listing, cachedListing);
		return shouldCreate || (cachedListing != null && !listing.getMarketplaceEventId().equals(cachedListing.getEvent().getMarketplaceEventId()));
	}

	@Override
	protected boolean shouldUpdate(
		@Nonnull ViagogoEvent event,
		@Nonnull ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		boolean shouldUpdate = super.shouldUpdate(event, listing, cachedListing);
		return shouldUpdate || (cachedListing != null && !listing.getMarketplaceEventId().equals(cachedListing.getEvent().getMarketplaceEventId()));
	}

	@Override
	protected int getPriority(
		@Nonnull ViagogoEvent event,
		@Nullable ViagogoListing listing,
		@Nullable ViagogoCachedListing cachedListing
	) {
		if (listing == null || cachedListing == null) {
			return 0;
		}

		int priority = 0;

		var r = listing.getRequest();
		var cr = cachedListing.getRequest();

		priority += Objects.equals(r.getNumberOfTickets(), cr.getNumberOfTickets()) ? 0 : 1;
		priority += Objects.equals(r.getSeating(), cr.getSeating()) ? 0 : 1;
		priority += Objects.equals(r.getNotes(), cr.getNotes()) ? 0 : 1;

		log.trace("[getPriority] listing request: {}, cached listing request: {} priority: {}", r, cr, priority);

		return priority;
	}

	@Override
	protected boolean shouldDelete(
		@Nonnull ViagogoEvent event,
		@Nonnull Set<String> inventoryListingIds,
		@Nonnull String listingId,
		@Nonnull ViagogoCachedListing cachedListing
	) {
		return super.shouldDelete(event, inventoryListingIds, listingId, cachedListing)
			|| !event.getMarketplaceEventId().equals(cachedListing.getEvent().getMarketplaceEventId());
	}

	@Override
	protected void createListing(ViagogoEvent event, ViagogoListing listing) throws IOException {
		this.sellerListingService.createListing(listing.getMarketplaceEventId(), listing.getRequest());
	}

	@Override
	protected void deleteListing(ViagogoEvent event, String listingId) throws IOException {
		this.sellerListingService.deleteListingByExternalListingId(listingId);
	}

	@Override
	protected ViagogoCachedListing toCached(ViagogoEvent event, ViagogoListing listing, Status status) {
		return new ViagogoCachedListing(new ViagogoCachedEvent(event), listing, status);
	}

	@Override
	public void check() {
		check(CheckOptions.defaults().pageSize(pageSize));
	}

	@Override
	public void check(CheckOptions options) {
		doCheck(options);
	}

	private class CheckContext {

		private final CheckOptions options;

		private final Map<String, String> externalIdToCacheName;

		/**
		 * The external IDs listed on the marketplace.
		 */
		private final Set<String> listedExternalIds;

		/**
		 * The checking tasks.
		 */
		private final List<CompletableFuture<PagedResource<SellerListing>>> checkings;

		/**
		 * The tasks to delete or update the listings.
		 */
		private final List<CompletableFuture<Void>> tasks;

		public CheckContext(CheckOptions options, Map<String, String> externalIdToCacheName) {
			this.options = options;
			this.externalIdToCacheName = Collections.unmodifiableMap(externalIdToCacheName);
			this.listedExternalIds = ConcurrentHashMap.newKeySet();
			this.checkings = Collections.synchronizedList(new ArrayList<>());
			this.tasks = Collections.synchronizedList(new ArrayList<>());
		}

		public Map<String, String> getExternalIdToCacheName() {
			return externalIdToCacheName;
		}

		/**
		 * Creates a seller listing request.
		 *
		 * @param page the page.
		 * @param options the check options.
		 * @return a seller listing request.
		 */
		public SellerListingRequest request(int page) {
			var r = new SellerListingRequest();
			r.setSort(SellerListingRequest.Sort.EVENT_DATE);
			r.setPage(page);
			r.setPageSize(options.pageSize());
			return r;
		}

		public int checkingCount() {
			return checkings.size();
		}

		public boolean addChecking(CompletableFuture<PagedResource<SellerListing>> e) {
			return checkings.add(e);
		}

		public void joinCheckings() {
			CompletableFuture.allOf(checkings.toArray(CompletableFuture[]::new)).join();
		}

		public int taskCount() {
			return tasks.size();
		}

		public boolean addTask(CompletableFuture<Void> e) {
			return tasks.add(e);
		}

		public boolean addTasks(Collection<? extends CompletableFuture<Void>> c) {
			return tasks.addAll(c);
		}

		public void joinTasks() {
			CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();
		}

		/**
		 * Adds external IDs which is listed on the marketplace.
		 *
		 * @param externalId the external ID.
		 */
		public void addListedExternalId(String externalId) {
			listedExternalIds.add(externalId);
		}

		/**
		 * Returns the missing external IDs on the marketplace.
		 *
		 * @return the missing external IDs.
		 */
		public Set<String> getMissingExternalIds() {
			var missingExternalIds = new HashSet<>(externalIdToCacheName.keySet());
			missingExternalIds.removeAll(listedExternalIds);
			log.debug("missingExternalIds count: {}", missingExternalIds::size);
			return missingExternalIds;
		}

	}

	private void doCheck(CheckOptions options) {
		log.info("[check] begin.");

		// Create a stop watch to measure the time taken to check the listings.
		StopWatch stopWatch = StopWatch.createStarted();

		// Create a new check context.
		CheckContext ctx = newCheckContext(options);

		// Check the first page.
		PagedResource<SellerListing> listings = this.check(ctx, 1).join();

		if (listings == null) {
			throw new RetryableException("Retrieve first page failed.");
		}

		// Check the next page to the last page.
		log.debug("[check] total items: {}, next link: {}, last link: {}",
			listings::getTotalItems, listings::getNextLink, listings::getLastLink);

		// Check subsequent pages if available
		// When only 1 page left, the next link and last link is null.
		Optional.ofNullable(listings.getNextLink()).map(SellerListingRequest::from)
			.ifPresent(next -> Optional.ofNullable(listings.getLastLink()).map(SellerListingRequest::from)
				.ifPresent(last -> IntStream.rangeClosed(next.getPage(), last.getPage())
					.mapToObj(page -> this.check(ctx, page))
					.forEach(ctx::addChecking)
				)
			);

		// Wait all checking to complete.
		log.debug("[check] checking size: {}", ctx::checkingCount);
		ctx.joinCheckings();

		// Wait all tasks to complete.
		log.debug("[check] tasks size: {}", ctx::taskCount);
		ctx.joinTasks();

		// Create the listings which in cache but not on the marketplace.
		ctx.getMissingExternalIds().forEach(t -> {
			var cacheName = ctx.getExternalIdToCacheName().get(t);
			var cache = this.getCache(cacheName);
			var viagogoCachedListing = cache.get(t);

			if (viagogoCachedListing != null) {
				// Double check if the cached listing still exists.
				var viagogoEvent = viagogoCachedListing.getEvent().toViagogoEvent();
				var viagogoListing = viagogoCachedListing.toViagogoListing();
				try {
					this.createListing(viagogoEvent, viagogoListing);
				} catch (IOException e) {
					log.warn("Create listing failed, external ID: {}.", viagogoListing.getId(), e);
				}
			}
		});

		// Log the time taken to check the listings.
		stopWatch.stop();
		log.info("[check] end. Checked {} items in {}", listings::getTotalItems, () -> stopWatch);
	}

	/**
	 * Creates a new check context.
	 *
	 * @param options the check options.
	 * @return a new check context.
	 */
	private CheckContext newCheckContext(CheckOptions options) {
		// The mapping of external IDs to their corresponding cache names.
		var externalIdToCacheName = this.getExternalIdToCacheName(options);

		// The context for checking.
		return new CheckContext(options, externalIdToCacheName);
	}

	/**
	 * Retrieves a mapping of external IDs to their corresponding cache names.
	 *
	 * This method iterates over all available cache names, retrieves each cache, 
	 * and then creates a map entry for each external ID pointing to its cache name.
	 *
	 * @param options the check options.
	 * @return a map where the keys are external IDs and the values are cache names.
	 */
	private Map<String, String> getExternalIdToCacheName(CheckOptions options) {
		// Create a stop watch to measure the time taken to retrieve the external ID to cache
		StopWatch stopWatch = StopWatch.createStarted();

		// Create a map to hold the external ID to cache name mapping
		Map<String, String> externalIdToCacheName =
			this.getCacheNamesStream(options.chunkSize()) // Stream of cache names
				.flatMap(cacheName ->
					// Retrieve the cache and create a stream of externalId-to-cacheName entries
					this.getCache(cacheName).keySet().stream()
						.map(externalId -> Map.entry(externalId, cacheName))
				)
				// Collect the entries into a map
				.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

		// Log the time taken to retrieve the external ID to cache name mapping
		stopWatch.stop();
		log.debug("[getExternalIdToCacheName] end. Retrieved {} external IDs in {}", externalIdToCacheName::size, () -> stopWatch);

		// Return the map of external IDs to cache names
		return externalIdToCacheName;
	}

	/**
	 * Checks the listings of the request.
	 *
	 * @param ctx the context.
	 * @param int the page.
	 * @return the page in checking.
	 */
	private CompletableFuture<PagedResource<SellerListing>> check(CheckContext ctx, int page) {
		return callAsync(() -> {
			var pagedResource = this.getSellerListings(ctx.request(page));
			Optional.ofNullable(pagedResource).ifPresent(t -> this.check(ctx, t));
			log.debug("[check] page: {}, tasks size: {}", () -> page, ctx::taskCount);
			return pagedResource;
		});
	}

	/**
	 * Checks the listings in the page.
	 *
	 * @param ctx the context.
	 * @param page the page.
	 */
	private void check(CheckContext ctx, PagedResource<SellerListing> page) {
		// Delete the listings not in the page
		var deleteTasks = page.getItems().stream()
			.filter(listing -> !ctx.getExternalIdToCacheName().containsKey(listing.getExternalId()))
			.map(listing -> this.<Void>callAsync(() -> {
				this.sellerListingService.deleteListingByExternalListingId(listing.getExternalId());
				return null;
			})).collect(Collectors.toUnmodifiableList());
		ctx.addTasks(deleteTasks);

		// Check the listings in the page.
		page.getItems().stream()
			.filter(listing -> ctx.getExternalIdToCacheName().containsKey(listing.getExternalId()))
			.forEach((SellerListing listing) -> check(ctx, listing));
	}

	/**
	 * Checks the listing.
	 *
	 * If the listing is not cached, delete the listing from the marketplace.
	 * If the listing is not same as the cached listing, update the listing.
	 *
	 * @param ctx the context.
	 * @param listing the listing.
	 */
	private void check(CheckContext ctx, SellerListing listing) {
		log.trace("Checking {}", listing::getExternalId);

		ctx.addListedExternalId(listing.getExternalId());

		String cacheName = ctx.getExternalIdToCacheName().get(listing.getExternalId());
		ViagogoCachedListing cachedListing = this.getCache(cacheName).get(listing.getExternalId());

		if (cachedListing == null) {
			// Double check the listing if it is not cached.
			// If the listing is not cached, delete the listing from the marketplace.
			ctx.addTask(this.<Void>callAsync(() -> {
				log.trace("Deleting {}", listing::getExternalId);
				this.sellerListingService.deleteListingByExternalListingId(listing.getExternalId());
				return null;
			}));
		} else if (!isSame(listing, cachedListing.getRequest())) {
			// If the listing is not same as the cached listing, update the listing.
			ctx.addTask(this.<Void>callAsync(() -> {
				log.trace("Updating {}", listing::getExternalId);

				var e = cachedListing.getEvent().toViagogoEvent();
				var l = cachedListing.toViagogoListing();
				var p = getPriority(e, l, cachedListing);

				if (e.getMarketplaceEventId().equals(listing.getEvent().getId())) {
					this.updateListing(e, l, cachedListing, p);
				} else {
					log.warn("Viagogo Event ID mismatch:  {} != {}, event ID = {}",
						e::getMarketplaceEventId, () -> listing.getEvent().getId(), e::getId);
					this.deleteListing(e, listing.getExternalId(), cachedListing, p);
				}
				return null;
			}));
		}
	}

	private boolean isSame(SellerListing l, CreateSellerListingRequest r) {
		var same = Listings.isSame(l, r);

		log.trace("[isSame] externalId: {}, numberOfTickets: {} -> {}, seating: {} -> {}, ticketPrice: {} {} -> {} {}, isSame: {}",
			l::getExternalId,
			l::getNumberOfTickets,
			r::getNumberOfTickets,
			l::getSeating,
			r::getSeating,
			() -> l.getTicketPrice().getCurrencyCode(),
			() -> l.getTicketPrice().getAmount(),
			() -> r.getTicketPrice().getCurrencyCode(),
			() -> r.getTicketPrice().getAmount(),
			() -> same
		);

		return same;
	}

	/**
	 * Gets the seller listings.
	 *
	 * @param request the request.
	 * @return the seller listings.
	 */
	@Nullable
	private PagedResource<SellerListing> getSellerListings(SellerListingRequest request) {
		return this.retry(() -> {
			try {
				return this.sellerListingService.getSellerListings(request);
			} catch (IOException e) {
				throw new RetryableException(e);
			}
		});
	}

	private final Random random = new Random();

	@Nullable
	private <T> T retry(Supplier<T> supplier) {
		int attempts = 0;

		T t = null;

		try {
			t = supplier.get();
		} catch (RetryableException e) {
			if (++attempts < retryConfig.getMaxAttempts()) {
				long delay = random.nextInt(retryConfig.getMaxDelay());
				sleep(delay);
			} else {
				log.debug("attempts: {}", attempts);
				throw e;
			}
		}

		return t;
	}

	private void sleep(long millis) {
		if (millis < 0) {
			return;
		}

		log.debug("sleeping {}", millis);
		ThreadUtils.sleepQuietly(Duration.ofMillis(millis));
	}

	private static class RetryableException extends RuntimeException {

		private static final long serialVersionUID = 2023120801L;

		public RetryableException() {
			super();
		}

		public RetryableException(String message) {
			super(message);
		}

		public RetryableException(Throwable cause) {
			super(cause);
		}

	}

}
