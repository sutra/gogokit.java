package org.oxerr.viagogo.client;

import org.oxerr.viagogo.client.catalog.EventClient;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;

public interface ViagogoClient {

	EventClient eventClient();

	SellerListingsClient sellerListingsClient();

}
