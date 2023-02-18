package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;

import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.response.sale.Sale;

public class SaleServiceImpl implements SaleService {

	private final SaleResource saleResource;

	public SaleServiceImpl(SaleResource saleResource) {
		this.saleResource = saleResource;
	}

	@Override
	public Sale getSale(Integer saleId) throws IOException {
		return this.saleResource.getSale(saleId);
	}

}
