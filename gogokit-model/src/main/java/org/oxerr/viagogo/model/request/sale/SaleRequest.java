package org.oxerr.viagogo.model.request.sale;

import java.util.Locale;

import org.oxerr.viagogo.model.Link;
import org.oxerr.viagogo.model.request.PagedRequest;

import io.openapitools.jackson.dataformat.hal.HALLink;

/**
 * Query parameter for <a href=
 * "https://developer.viagogo.net/api-reference/sales#operation/Sales_GetSales">List
 * sales</a>.
 */
public class SaleRequest extends PagedRequest {

	private static final long serialVersionUID = 2023112401L;

	public enum Sort implements PagedRequest.Sort {

		CREATED_AT,

		EVENT_DATE,

		INHAND_AT,

		PAYMENT_AMOUNT,

		QUANTITY,

		RESOURCE_VERSION;

		@Override
		public String getCode() {
			return this.name().toLowerCase(Locale.US);
		}

	}

	public static SaleRequest from(HALLink halLink) {
		return new SaleRequest(new Link(halLink));
	}

	public SaleRequest() {
	}

	public SaleRequest(Link link) {
		super(link);
	}

}
