package org.oxerr.viagogo.model.request.sale;

import org.oxerr.viagogo.model.request.ListRequest;

/**
 * Query parameter for <a href=
 * "https://developer.viagogo.net/api-reference/sales#operation/Sales_GetSales">List
 * sales</a>.
 */
public class SaleRequest extends ListRequest {

	private static final long serialVersionUID = 2023112401L;

	public enum Sort implements ListRequest.Sort {

		CREATED_AT("created_at"),

		EVENT_DATE("event_date"),

		INHAND_AT("inhand_at"),

		PAYMENT_AMOUNT("payment_amount"),

		QUANTITY("quantity"),

		RESOURCE_VERSION("resource_version");

		private String code;

		Sort(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}

	}

}
