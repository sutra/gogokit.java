package org.oxerr.viagogo.client.rescu;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.inventory.SellerListingsResource;

class RestProxyFactorySingletonImplTest {

	@Test
	void testCreateProxyClassOfIStringClientConfigInterceptorArray() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingsResource.class, null, null), f.createProxy(SellerListingsResource.class, null, null));
	}

	@Test
	void testCreateProxyClassOfIString() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingsResource.class, null), f.createProxy(SellerListingsResource.class, null));
	}

}
