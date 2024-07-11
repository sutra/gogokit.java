package org.oxerr.viagogo.client.sale;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.viagogo.model.request.sale.SaleRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

public interface SaleService {

	PagedResource<Sale> getSalesRecentUpdates(@Nullable Instant updatedSince) throws IOException;

	PagedResource<Sale> getSales(@Nonnull SaleRequest saleRequest) throws IOException;

	Optional<Sale> getSale(@Nonnull Integer saleId) throws IOException;

}
