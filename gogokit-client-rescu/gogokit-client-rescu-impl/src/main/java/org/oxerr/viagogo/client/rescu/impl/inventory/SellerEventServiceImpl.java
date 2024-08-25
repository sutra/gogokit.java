package org.oxerr.viagogo.client.rescu.impl.inventory;

import java.io.IOException;

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
	public PagedResource<SellerEvent> getSellerEvents(SellerEventRequest sellerEventRequest) throws IOException {
		return this.sellerEventResource.getSellerEvents(
			sellerEventRequest.getPage(),
			sellerEventRequest.getPageSize(),
			sellerEventRequest.getUpdatedSince(),
			sellerEventRequest.getSort()
		);
	}

	@Override
	public SellerEvent createSellerEvent(CreateSellerEventRequest createSellerEventRequest) throws IOException {
		return this.sellerEventResource.createSellerEvent(createSellerEventRequest);
	}

}
