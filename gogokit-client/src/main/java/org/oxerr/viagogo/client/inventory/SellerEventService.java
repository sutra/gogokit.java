package org.oxerr.viagogo.client.inventory;

import org.oxerr.viagogo.model.request.inventory.CreateSellerEventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

public interface SellerEventService {

	PagedResource<SellerEvent> getSellerEvents(SellerEventRequest sellerEventRequest);

	SellerEvent createSellerEvent(CreateSellerEventRequest createSellerEventRequest);

}
