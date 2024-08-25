package org.oxerr.viagogo.client.rescu.impl.sale;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.client.rescu.resource.sale.SaleResource;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.request.sale.SaleRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

import si.mazi.rescu.HttpStatusIOException;

public class SaleServiceImpl implements SaleService {

	private final SaleResource saleResource;

	public SaleServiceImpl(SaleResource saleResource) {
		this.saleResource = saleResource;
	}

	@Override
	public PagedResource<Sale> getSalesRecentUpdates(Instant updatedSince) throws IOException {
		return this.saleResource.getSalesRecentUpdates(updatedSince);
	}

	@Override
	public PagedResource<Sale> getSales(SaleRequest saleRequest) throws IOException {
		return this.saleResource.getSales(
			saleRequest.getPage(),
			saleRequest.getPageSize(),
			saleRequest.getUpdatedSince(),
			saleRequest.getSort()
		);
	}

	@Override
	public Optional<Sale> getSale(Integer saleId) throws IOException {
		try {
			return Optional.ofNullable(this.saleResource.getSale(saleId));
		} catch (ViagogoException | HttpStatusIOException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

}
