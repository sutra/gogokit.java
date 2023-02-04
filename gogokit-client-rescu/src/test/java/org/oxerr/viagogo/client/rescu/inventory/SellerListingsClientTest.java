package org.oxerr.viagogo.client.rescu.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.model.ViagogoException;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.SellerListing;

class SellerListingsClientTest {

	private final Logger log = LogManager.getLogger();

	@Test
	@Disabled("Token is required")
	void testGetAllByEventId() throws ViagogoException, IOException {
		var client = RescuViagogoClientTest.getClient();
		var sellerListings = client.sellerListingsClient().getAll(null, null, null, null, null, null);
		assertNotNull(sellerListings);
		log.info("Total items: {}", sellerListings.getTotalItems());
	}

	@Test
	@Disabled("Delete all seller listings")
	void testDeleteAll() throws IOException {
		var client = RescuViagogoClientTest.getClient();

		PagedResource<SellerListing> all;

		Set<String> externalIds = new HashSet<>();

		int page = 1;
		int pageSize = 10_000;

		do {
			try {
				all = client.sellerListingsClient().getAll(null, null, page, pageSize, null, null);
				page++;
			} catch (IOException e) {
				log.warn("Read all seller listings failed: {}", e.getMessage());
				all = null;
				continue;
			}

			assertNotNull(all);

			externalIds.addAll(all.getItems().stream().map(SellerListing::getExternalId).collect(Collectors.toList()));
			log.info("total items: {}, externalIds.size: {}", all.getTotalItems(), externalIds.size());
		} while (all == null || all.getItems().size() > 0);

		var executor = Executors.newFixedThreadPool(100);

		for (String externalId : externalIds) {
			log.trace("Deleting {}", externalId);

			executor.execute(() -> {
				try {
					client.sellerListingsClient().delete(externalId);
				} catch (IOException e) {
					log.warn("Delete {} failed: {}", externalId, e.getMessage());
				}
			});
		}

		try {
			executor.awaitTermination(60, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			log.warn("{}", e.getMessage());
		}
	}

}
