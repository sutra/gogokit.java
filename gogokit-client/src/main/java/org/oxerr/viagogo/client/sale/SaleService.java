package org.oxerr.viagogo.client.sale;

import java.io.IOException;

import org.oxerr.viagogo.model.response.sale.Sale;

public interface SaleService {

	Sale getSale(Integer saleId) throws IOException;

}
