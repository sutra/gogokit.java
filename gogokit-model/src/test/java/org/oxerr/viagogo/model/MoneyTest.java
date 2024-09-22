package org.oxerr.viagogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoneyTest {

	@Test
	void test() {
		assertEquals(0, Money.of("1", "USD").compareTo(Money.of("1.0", "USD")));
	}

}
