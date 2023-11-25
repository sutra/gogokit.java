package org.oxerr.viagogo.model.request.inventory;

import java.util.Locale;

import org.oxerr.viagogo.model.Link;
import org.oxerr.viagogo.model.request.PagedRequest;

import io.openapitools.jackson.dataformat.hal.HALLink;

/**
 * Query parameter for <a href=
 * "https://developer.viagogo.net/api-reference/inventory#operation/SellerEvents_GetSellerEvents">List
 * seller events</a>.
 */
public class SellerEventRequest extends PagedRequest {

	private static final long serialVersionUID = 2023112401L;

	public enum Sort implements PagedRequest.Sort {

		ID,

		NUMBER_OF_TICKETS,

		RESOURCE_VERSION;

		@Override
		public String getCode() {
			return this.name().toLowerCase(Locale.US);
		}

	}

	public static SellerEventRequest from(HALLink halLink) {
		return new SellerEventRequest(new Link(halLink));
	}

	public SellerEventRequest() {
	}

	public SellerEventRequest(Link link) {
		super(link);
	}

}
