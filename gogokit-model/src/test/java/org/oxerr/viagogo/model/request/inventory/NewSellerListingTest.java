package org.oxerr.viagogo.model.request.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.model.Money;

class NewSellerListingTest {

	@Test
	void test() {
		assertEquals(new NewSellerListing().hashCode(), new NewSellerListing().hashCode());

		boolean equals = new NewSellerListing().equals(new NewSellerListing());
		assertTrue(equals);

		var a = new NewSellerListing();
		var b = new NewSellerListing();
		b.setTicketPrice(new Money());

		assertNotEquals(a.hashCode(), b.hashCode());
		equals = a.equals(b);
		assertFalse(equals);
	}

}
