package org.oxerr.viagogo.client.sale;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.viagogo.model.request.sale.SaleRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

public interface SaleService {

	PagedResource<Sale> getSalesRecentUpdates(@Nullable Instant updatedSince);

	PagedResource<Sale> getSales(@Nonnull SaleRequest saleRequest);

	Optional<Sale> getSale(@Nonnull Integer saleId);

}
