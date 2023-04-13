package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.response.sale.Sale;

import si.mazi.rescu.HttpStatusIOException;

public class SaleServiceImpl implements SaleService {

	private final SaleResource saleResource;

	public SaleServiceImpl(SaleResource saleResource) {
		this.saleResource = saleResource;
	}

	@Override
	public Optional<Sale> getSale(Integer saleId) throws IOException {
		try {
			return Optional.ofNullable(this.saleResource.getSale(saleId));
		} catch (ViagogoException | HttpStatusIOException e) {
			if (e.getHttpStatusCode() == 404) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

}
