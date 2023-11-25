package org.oxerr.viagogo.model.request.inventory;

import org.oxerr.viagogo.model.request.ListRequest;

/**
 * Query parameter for <a href=
 * "https://developer.viagogo.net/api-reference/inventory#operation/SellerEvents_GetSellerEvents">List
 * seller events</a>.
 */
public class SellerEventRequest extends ListRequest {

	private static final long serialVersionUID = 2023112401L;

	public enum Sort implements ListRequest.Sort {

		ID("id"),

		NUMBER_OF_TICKETS("number_of_tickets"),

		RESOURCE_VERSION("resource_version");

		private String code;

		Sort(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return this.code;
		}

	}

}
