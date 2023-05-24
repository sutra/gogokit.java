package org.oxerr.viagogo.client.rescu;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.rescu.catalog.EventResource;
import org.oxerr.viagogo.client.rescu.catalog.EventServiceImpl;
import org.oxerr.viagogo.client.rescu.inventory.SellerEventResource;
import org.oxerr.viagogo.client.rescu.inventory.SellerEventServiceImpl;
import org.oxerr.viagogo.client.rescu.inventory.SellerListingResource;
import org.oxerr.viagogo.client.rescu.inventory.SellerListingServiceImpl;
import org.oxerr.viagogo.client.rescu.sale.SaleResource;
import org.oxerr.viagogo.client.rescu.sale.SaleServiceImpl;
import org.oxerr.viagogo.client.sale.SaleService;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class RescuViagogoClient implements ViagogoClient {

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	private final EventService eventService;

	private final SellerListingService sellerListingService;

	private final SellerEventService sellerEventService;

	private final SaleService saleService;

	public RescuViagogoClient(String token, Interceptor... interceptors) {
		this("https://api.viagogo.net", token, interceptors);
	}

	public RescuViagogoClient(String baseUrl, String token, Interceptor... interceptors) {
		this.baseUrl = baseUrl;

		JacksonObjectMapperFactory jacksonObjectMapperFactory = new DefaultJacksonObjectMapperFactory() {

			@Override
			public void configureObjectMapper(ObjectMapper objectMapper) {
				super.configureObjectMapper(objectMapper);
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
				objectMapper.setSerializationInclusion(Include.NON_ABSENT);
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
				objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
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

		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());

		this.eventService = new EventServiceImpl(createProxy(EventResource.class, interceptors));
		this.sellerListingService = new SellerListingServiceImpl(createProxy(SellerListingResource.class, interceptors));
		this.sellerEventService = new SellerEventServiceImpl(createProxy(SellerEventResource.class, interceptors));
		this.saleService = new SaleServiceImpl(createProxy(SaleResource.class, interceptors));
	}

	@Override
	public EventService getEventService() {
		return this.eventService;
	}

	@Override
	public SellerListingService getSellerListingService() {
		return this.sellerListingService;
	}

	@Override
	public SellerEventService getSellerEventService() {
		return this.sellerEventService;
	}

	@Override
	public SaleService getSaleService() {
		return this.saleService;
	}

	protected <I> I createProxy(Class<I> restInterface, Interceptor... interceptors) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig, interceptors);
	}

}
