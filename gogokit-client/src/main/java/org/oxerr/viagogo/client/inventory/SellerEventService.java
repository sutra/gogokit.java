package org.oxerr.viagogo.client.inventory;

import java.io.IOException;

import org.oxerr.viagogo.model.request.inventory.CreateSellerEventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

public interface SellerEventService {

	PagedResource<SellerEvent> getSellerEvents(SellerEventRequest sellerEventRequest) throws IOException;

	SellerEvent createSellerEvent(CreateSellerEventRequest createSellerEventRequest) throws IOException;

}
