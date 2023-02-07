package org.oxerr.viagogo.client.rescu;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.inventory.SellerListingResource;

class RestProxyFactorySingletonImplTest {

	@Test
	void testCreateProxyClassOfIStringClientConfigInterceptorArray() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingResource.class, null, null), f.createProxy(SellerListingResource.class, null, null));
	}

	@Test
	void testCreateProxyClassOfIString() {
		RestProxyFactorySingletonImpl f = new RestProxyFactorySingletonImpl();
		assertSame(f.createProxy(SellerListingResource.class, null), f.createProxy(SellerListingResource.class, null));
	}

}
