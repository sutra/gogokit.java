package org.oxerr.viagogo.client.rescu.impl;

import java.util.function.Supplier;

import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.client.catalog.EventService;
import org.oxerr.viagogo.client.inventory.SellerEventService;
import org.oxerr.viagogo.client.inventory.SellerListingService;
import org.oxerr.viagogo.client.rescu.impl.catalog.EventServiceImpl;
import org.oxerr.viagogo.client.rescu.impl.inventory.SellerEventServiceImpl;
import org.oxerr.viagogo.client.rescu.impl.inventory.SellerListingServiceImpl;
import org.oxerr.viagogo.client.rescu.impl.sale.SaleServiceImpl;
import org.oxerr.viagogo.client.rescu.impl.webhook.WebhookServiceImpl;
import org.oxerr.viagogo.client.rescu.resource.catalog.EventResource;
import org.oxerr.viagogo.client.rescu.resource.inventory.SellerEventResource;
import org.oxerr.viagogo.client.rescu.resource.inventory.SellerListingResource;
import org.oxerr.viagogo.client.rescu.resource.sale.SaleResource;
import org.oxerr.viagogo.client.rescu.resource.webhook.WebhookResource;
import org.oxerr.viagogo.client.sale.SaleService;
import org.oxerr.viagogo.client.webhook.WebhookService;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.openapitools.jackson.dataformat.hal.HALMapper;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class ResCUViagogoClient implements ViagogoClient {

	private static final String DEFAULT_BASE_URL = "https://api.viagogo.net";

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	private final EventService eventService;

	private final SellerListingService sellerListingService;

	private final SellerEventService sellerEventService;

	private final SaleService saleService;

	private final WebhookService webhookService;

	public ResCUViagogoClient(String token, Interceptor... interceptors) {
		this(DEFAULT_BASE_URL, token, interceptors);
	}

	public ResCUViagogoClient(String baseUrl, String token, Interceptor... interceptors) {
		this(baseUrl, () -> token, interceptors);
	}

	public ResCUViagogoClient(Supplier<String> token, Interceptor... interceptors) {
		this(DEFAULT_BASE_URL, token, interceptors);
	}

	public ResCUViagogoClient(String baseUrl, Supplier<String> tokenSupplier, Interceptor... interceptors) {
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
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", new Object() {

			@Override
			public String toString() {
				return "Bearer " + tokenSupplier.get();
			}

		});
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);

		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());

		this.eventService = new EventServiceImpl(createProxy(EventResource.class, interceptors));
		this.sellerListingService = new SellerListingServiceImpl(createProxy(SellerListingResource.class, interceptors));
		this.sellerEventService = new SellerEventServiceImpl(createProxy(SellerEventResource.class, interceptors));
		this.saleService = new SaleServiceImpl(createProxy(SaleResource.class, interceptors));
		this.webhookService = new WebhookServiceImpl(createProxy(WebhookResource.class, interceptors));
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

	@Override
	public WebhookService getWebhookService() {
		return this.webhookService;
	}

	protected <I> I createProxy(Class<I> restInterface, Interceptor... interceptors) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig, interceptors);
	}

}
