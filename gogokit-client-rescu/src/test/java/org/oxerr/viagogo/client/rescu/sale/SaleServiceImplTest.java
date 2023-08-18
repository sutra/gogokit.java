package org.oxerr.viagogo.client.rescu.sale;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.ResCUViagogoClientTest;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.response.sale.Sale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class SaleServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final SaleService saleService
		= ResCUViagogoClientTest.getClient().getSaleService();

	@Test
	@Disabled("Token is required")
	void testGetSale() throws IOException {
		Integer saleId = 512171600;
		Sale sale = this.saleService.getSale(saleId).get();
		log.info("sale: {}", () -> {
			try {
				return new ObjectMapper().findAndRegisterModules().writeValueAsString(sale);
			} catch (JsonProcessingException e) {
				log.error("{}", e.getMessage(), e);
				return null;
			}
		});
		log.info(
			"Sale ID: {}, created at: {}, external listing ID: {}, event ID: {}, event start date: {}",
			sale.getId(), sale.getCreatedAt(), sale.getExternalListingId(), sale.getEvent().getId(), sale.getEvent().getStartDate());
	}

}
