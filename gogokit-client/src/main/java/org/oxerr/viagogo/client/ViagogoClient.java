package org.oxerr.viagogo.client;

import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.sale.SaleService;

public interface ViagogoClient {

	EventService getEventService();

	SellerListingService getSellerListingService();

	SellerEventService getSellerEventService();

	SaleService getSaleService();

}
