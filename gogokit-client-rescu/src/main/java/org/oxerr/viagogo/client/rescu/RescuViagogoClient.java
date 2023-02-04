package org.oxerr.viagogo.client.rescu;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class RescuViagogoClient implements ViagogoClient {

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	public RescuViagogoClient(String token) {
		this("https://api.viagogo.net", token);
	}

	public RescuViagogoClient(String baseUrl, String token) {
		this.baseUrl = baseUrl;

		JacksonObjectMapperFactory jacksonObjectMapperFactory = new DefaultJacksonObjectMapperFactory() {

			@Override
			public void configureObjectMapper(ObjectMapper objectMapper) {
				super.configureObjectMapper(objectMapper);
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
			}

			@Override
			protected ObjectMapper createInstance() {
				return new HALMapper()
					.registerModule(new JavaTimeModule());
			}
		};

		this.clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(PathParam.class, "version", "v2");
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "Bearer " + token);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);

		this.restProxyFactory = new RestProxyFactorySingletonImpl();
	}

	public SellerListingsClient sellerListingsClient() {
		return createProxy(SellerListingsClient.class);
	}

	protected <I> I createProxy(Class<I> restInterface) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig);
	}

}
