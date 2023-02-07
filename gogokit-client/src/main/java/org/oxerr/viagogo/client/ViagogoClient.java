package org.oxerr.viagogo.client;

import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;

public interface ViagogoClient {

	EventService getEventService();

	SellerListingService getSellerListingService();

	SellerEventService getSellerEventService();

}
