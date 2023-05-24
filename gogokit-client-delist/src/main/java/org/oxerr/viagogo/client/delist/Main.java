package org.oxerr.viagogo.client.delist;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.oxerr.viagogo.client.rescu.RescuViagogoClient;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.HttpStatusIOException;

public class Main implements AutoCloseable {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RescuViagogoClient client;

	private final ExecutorService executorService;

	private final int pageSize;

	public Main(String token, int pageSize) {
		this.pageSize = pageSize;
		this.client = new RescuViagogoClient(token);
		this.executorService = Executors.newFixedThreadPool(pageSize);
	}

	public void delist() {
		PagedResource<SellerListing> listings = null;

		SellerListingRequest r = new SellerListingRequest();
		r.setPageSize(this.pageSize);

		do {
			try {
				listings = client.getSellerListingService().getSellerListings(r);
				delist(listings);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} while (listings == null || listings.getTotalItems() > 0);

	}

	private void delist(PagedResource<SellerListing> listings) {
		log.info("Left items: {}", listings.getTotalItems());

		for (SellerListing item : listings.getItems()) {
			this.executorService.execute(() -> {
				try {
					this.client.getSellerListingService().deleteListingByExternalListingId(item.getExternalId());
				} catch (IOException e) {
					// if (e instanceof HttpStatusIOException hsioe && hsioe.getHttpStatusCode() == 404) {
					if (e instanceof HttpStatusIOException && ((HttpStatusIOException) e).getHttpStatusCode() == 404) {
						// ignore
					} else {
						log.error(e.getMessage());
					}
				}
			});
		}
	}

	@Override
	public void close() {
		List<Runnable> tasksNeverCommencedExecution = null;

		try {
			if (!executorService.awaitTermination(this.pageSize, TimeUnit.SECONDS)) {
				tasksNeverCommencedExecution = executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			tasksNeverCommencedExecution = executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}

		if (tasksNeverCommencedExecution != null) {
			log.warn("{} task(s) that never commenced execution.", tasksNeverCommencedExecution.size());
		}
	}

	public static void main(String[] args) {
		String token = args[0];
		int pageSize = Integer.parseInt(args[1]);
		try (Main main = new Main(token, pageSize)) {
			main.delist();
		}
	}

}
