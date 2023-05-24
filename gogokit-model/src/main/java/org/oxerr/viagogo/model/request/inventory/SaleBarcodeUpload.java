package org.oxerr.viagogo.model.request.inventory;

import java.util.List;

public class SaleBarcodeUpload extends BaseBarcodeUpload {

	private static final long serialVersionUID = 2023021801L;

	private List<String> arcodeValues;

	public List<String> getArcodeValues() {
		return arcodeValues;
	}

	public void setArcodeValues(List<String> arcodeValues) {
		this.arcodeValues = arcodeValues;
	}

}
