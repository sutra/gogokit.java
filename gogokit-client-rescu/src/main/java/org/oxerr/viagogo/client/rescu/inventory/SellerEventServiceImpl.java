package org.oxerr.viagogo.client.rescu.inventory;

import java.io.IOException;

import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

public class SellerEventServiceImpl implements SellerEventService {

	private final SellerEventResource sellerEventResource;

	public SellerEventServiceImpl(SellerEventResource sellerEventResource) {
		this.sellerEventResource = sellerEventResource;
	}

	@Override
	public PagedResource<SellerEvent> getSellerEvents(SellerEventRequest r) throws IOException {
		return this.sellerEventResource.getSellerEvents(
			r.getPage(),
			r.getPageSize(),
			r.getUpdatedSince(),
			r.getSort()
		);
	}

}
