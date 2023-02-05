package org.oxerr.viagogo.client;

import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerListingsService;

public interface ViagogoClient {

	EventService getEventService();

	SellerListingsService getSellerListingsService();

}
