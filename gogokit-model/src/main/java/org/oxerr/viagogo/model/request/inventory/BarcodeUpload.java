package org.oxerr.viagogo.model.request.inventory;

import java.util.List;

public class BarcodeUpload extends BaseBarcodeUpload {

	private static final long serialVersionUID = 2023021801L;

	private List<String> values;

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
