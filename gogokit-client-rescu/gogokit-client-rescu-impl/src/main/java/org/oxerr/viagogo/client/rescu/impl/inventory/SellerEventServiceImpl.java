package org.oxerr.viagogo.client.rescu.impl.inventory;

import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.rescu.resource.inventory.SellerEventResource;
import org.oxerr.viagogo.model.request.inventory.CreateSellerEventRequest;
import org.oxerr.viagogo.model.request.inventory.SellerEventRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.inventory.SellerEvent;

public class SellerEventServiceImpl implements SellerEventService {

	private final SellerEventResource sellerEventResource;

	public SellerEventServiceImpl(SellerEventResource sellerEventResource) {
		this.sellerEventResource = sellerEventResource;
	}

	@Override
	public PagedResource<SellerEvent> getSellerEvents(SellerEventRequest sellerEventRequest) {
		return this.sellerEventResource.getSellerEvents(
			sellerEventRequest.getPage(),
			sellerEventRequest.getPageSize(),
			sellerEventRequest.getUpdatedSince(),
			sellerEventRequest.getSort()
		);
	}

	@Override
	public SellerEvent createSellerEvent(CreateSellerEventRequest createSellerEventRequest) {
		return this.sellerEventResource.createSellerEvent(createSellerEventRequest);
	}

}
