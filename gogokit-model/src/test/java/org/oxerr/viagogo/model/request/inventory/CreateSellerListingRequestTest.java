package org.oxerr.viagogo.model.request.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.model.Money;

class CreateSellerListingRequestTest {

	@Test
	void test() {
		assertEquals(new CreateSellerListingRequest().hashCode(), new CreateSellerListingRequest().hashCode());

		boolean equals = new CreateSellerListingRequest().equals(new CreateSellerListingRequest());
		assertTrue(equals);

		var a = new CreateSellerListingRequest();
		var b = new CreateSellerListingRequest();
		b.setTicketPrice(new Money());

		assertNotEquals(a.hashCode(), b.hashCode());
		equals = a.equals(b);
		assertFalse(equals);
	}

}
