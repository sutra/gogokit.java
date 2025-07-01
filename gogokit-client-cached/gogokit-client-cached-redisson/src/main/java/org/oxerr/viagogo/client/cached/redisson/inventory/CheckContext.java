package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.oxerr.viagogo.client.cached.inventory.CheckOptions;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

class CheckContext {

	private final CheckOptions options;

	private final Map<String, String> externalIdToCacheName;

	/**
	 * The external IDs listed on the marketplace.
	 */
	private final Set<String> listedExternalIds;

	/**
	 * The external IDs which listings are visible in the venue map
	 * that will be shown to buyers.
	 */
	private final Set<String> visibleInVenueMapExternalIds;

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
		this.visibleInVenueMapExternalIds = ConcurrentHashMap.newKeySet();
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
	 * Adds listing which is listed on the marketplace.
	 *
	 * @param listing the seller listing.
	 */
	public void addListed(SellerListing listing) {
		listedExternalIds.add(listing.getExternalId());

		Boolean visible = listing.getDisplaySeating().getState().getVisibleInVenueMap();
		if (Boolean.TRUE.equals(visible)) {
			visibleInVenueMapExternalIds.add(listing.getExternalId());
		}
	}

	/**
	 * Returns the missing external IDs on the marketplace.
	 *
	 * @return the missing external IDs.
	 */
	public Set<String> getMissingExternalIds() {
		var missingExternalIds = new HashSet<>(externalIdToCacheName.keySet());
		missingExternalIds.removeAll(listedExternalIds);
		return missingExternalIds;
	}

}
