package org.oxerr.viagogo.client.sale;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.viagogo.model.response.sale.Sale;

public interface SaleService {

	Optional<Sale> getSale(Integer saleId) throws IOException;

}
