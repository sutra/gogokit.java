package org.oxerr.viagogo.client.rescu.impl.sale;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.impl.ResCUViagogoClientTest;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.request.sale.SaleRequest;
import org.oxerr.viagogo.model.response.PagedResource;
import org.oxerr.viagogo.model.response.sale.Sale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled("Token is required")
class SaleServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final SaleService saleService
		= ResCUViagogoClientTest.getClient().getSaleService();

	@Test
	@Disabled("Token is required")
	void testGetSalesRecentUpdates() throws IOException {
		Instant updatedSince = Instant.now().minus(1, ChronoUnit.DAYS);
		var sales = this.saleService.getSalesRecentUpdates(updatedSince);
		this.log(sales);
	}

	@Test
	@Disabled("Token is required")
	void testGetSales() throws IOException {
		SaleRequest r = new SaleRequest();
		var sales = this.saleService.getSales(r);
		this.log(sales);
	}

	@Test
	@Disabled("Token is required")
	void testGetSale() throws IOException {
		Integer saleId = 512171600;
		Sale sale = this.saleService.getSale(saleId).get();
		this.log(sale);
	}

	void log(PagedResource<Sale> sales) {
		log.info("Total items: {}", sales.getTotalItems());

		for (var sale : sales.getItems()) {
			this.log(sale);
		}
	}

	void log(Sale sale) {
		log.info("sale: {}", () -> {
			try {
				return new ObjectMapper().findAndRegisterModules().writeValueAsString(sale);
			} catch (JsonProcessingException e) {
				log.error("{}", e.getMessage(), e);
				return null;
			}
		});
		log.info(
			"Sale ID: {}, created at: {}, external listing ID: {}, event ID: {}, event start date: {}, status: {}",
			sale.getId(), sale.getCreatedAt(), sale.getExternalListingId(),
			sale.getEvent().getId(), sale.getEvent().getStartDate(),
			sale.getStatus()
		);
	}

}
