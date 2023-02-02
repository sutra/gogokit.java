package org.oxerr.viagogo.client.rescu;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;

class RestProxyFactorySingletonImplTest {

	@Test
	void testCreateProxyClassOfIStringClientConfigInterceptorArray() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingsClient.class, null, null), f.createProxy(SellerListingsClient.class, null, null));
	}

	@Test
	void testCreateProxyClassOfIString() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingsClient.class, null), f.createProxy(SellerListingsClient.class, null));
	}

}
