package org.oxerr.viagogo.client.rescu;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.catalog.EventClient;
import org.oxerr.viagogo.client.inventory.SellerListingsClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
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
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
			}

			@Override
			protected ObjectMapper createInstance() {
				return new HALMapper();
			}
		};

		this.clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(PathParam.class, "version", "v2");
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "Bearer " + token);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);

		this.restProxyFactory = new RestProxyFactorySingletonImpl();
	}

	@Override
	public EventClient eventClient() {
		return createProxy(EventClient.class);
	}

	@Override
	public SellerListingsClient sellerListingsClient() {
		return createProxy(SellerListingsClient.class);
	}

	protected <I> I createProxy(Class<I> restInterface) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig);
	}

}
