package org.oxerr.viagogo.client.delist;

import java.io.IOException;

import org.oxerr.viagogo.client.rescu.RescuViagogoClient;
import org.oxerr.viagogo.model.request.inventory.SellerListingRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RescuViagogoClient client;

	public Main(String token) {
		this.client = new RescuViagogoClient(token);
	}

	public void delist() {
		PagedResource<SellerListing> listings = null;

		var r = new SellerListingRequest();

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

		for (var item : listings.getItems()) {
			try {
				this.client.getSellerListingService().deleteListingByExternalListingId(item.getExternalId());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		var token = args[0];
		var main = new Main(token);
		main.delist();
	}

}
