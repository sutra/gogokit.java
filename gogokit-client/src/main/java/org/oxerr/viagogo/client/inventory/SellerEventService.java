package org.oxerr.viagogo.client.inventory;

import java.io.IOException;

import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

public interface SellerEventService {

	PagedResource<SellerEvent> getSellerEvents(SellerEventRequest r) throws IOException;

}
