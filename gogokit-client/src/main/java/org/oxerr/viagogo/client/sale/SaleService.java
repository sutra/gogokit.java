package org.oxerr.viagogo.client.sale;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.viagogo.model.request.sale.SaleRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

public interface SaleService {

	PagedResource<Sale> getSales(SaleRequest r) throws IOException;

	Optional<Sale> getSale(Integer saleId) throws IOException;

}
