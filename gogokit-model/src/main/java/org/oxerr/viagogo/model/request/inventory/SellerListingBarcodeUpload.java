package org.oxerr.viagogo.model.request.inventory;

import java.util.List;

public class SellerListingBarcodeUpload extends BaseBarcodeUpload {

	private static final long serialVersionUID = 2023021801L;

	private List<String> barcodeValuesSha256Hashed;

	public List<String> getBarcodeValuesSha256Hashed() {
		return barcodeValuesSha256Hashed;
	}

	public void setBarcodeValuesSha256Hashed(List<String> barcodeValuesSha256Hashed) {
		this.barcodeValuesSha256Hashed = barcodeValuesSha256Hashed;
	}

}
