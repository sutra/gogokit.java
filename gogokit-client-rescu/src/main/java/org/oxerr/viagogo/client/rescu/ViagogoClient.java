package org.oxerr.viagogo.client.rescu;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

import org.oxerr.viagogo.client.inventory.SellerListingsClient;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;

public class ViagogoClient {

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	public ViagogoClient(String token) {
		this("https://api.viagogo.net", token);
	}

	public ViagogoClient(String baseUrl, String token) {
		this.baseUrl = baseUrl;

		this.clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(PathParam.class, "version", "v2");
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "Bearer " + token);

		this.restProxyFactory = new RestProxyFactorySingletonImpl();
	}

	public SellerListingsClient sellerListingsClient() {
		return createProxy(SellerListingsClient.class);
	}

	public <I> I createProxy(Class<I> restInterface) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig);
	}

}
