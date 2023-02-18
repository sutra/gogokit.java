package org.oxerr.viagogo.client.rescu.sale;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.RescuViagogoClientTest;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.model.response.sale.Sale;

class SaleServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final SaleService saleService
		= RescuViagogoClientTest.getClient().getSaleService();

	@Test
	@Disabled("Token is required")
	void testGetSale() throws IOException {
		Integer saleId = 512171600;
		Sale sale = this.saleService.getSale(saleId);
		assertNotNull(sale);
		log.info("sale: {}", ToStringBuilder.reflectionToString(sale, ToStringStyle.MULTI_LINE_STYLE));
	}

}
