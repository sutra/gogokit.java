package org.oxerr.viagogo.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.ViagogoClient;
import org.oxerr.viagogo.model.request.sale.SaleRequest;

@Disabled("Token is required")
public class ResCUViagogoClientTest {

	private final Logger log = LogManager.getLogger();

	public static ResCUViagogoClient getClient() {
		Properties accounts = getAccounts();
		String token = (String) accounts.values().stream().findFirst().orElseThrow();
		return new ResCUViagogoClient(token);
	}

	private static Properties getAccounts() {
		Properties props = new Properties();
		String name = "/viagogo.properties";
		try (InputStream in = ResCUViagogoClientTest.class.getResourceAsStream(name)) {
			if (in != null) {
				props.load(in);
			} else {
				throw new IllegalArgumentException(String.format("No resource found: %s", name));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Read " + name + " failed.");
		}
		return props;
	}

	@Test
	@Disabled("Token is required")
	void testGetClient() {
		assertNotNull(ResCUViagogoClientTest.getClient());
	}

	@Test
	@Disabled("Token is required")
	void testSumTotalSalesParallelStream() {
		log.info("Runtime.getRuntime().availableProcessors(): {}", Runtime.getRuntime().availableProcessors());

		ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);
		ViagogoClient client = new ResCUViagogoClient(() -> token.get());

		SaleRequest saleRequest = new SaleRequest();

		StopWatch stopWatch = StopWatch.createStarted();

		long totalSales = getAccounts().entrySet().parallelStream().map(entry -> {
			token.set((String) entry.getValue());

			try {
				var sales = client.getSaleService().getSales(saleRequest);
				log.debug("{}: {}", entry.getKey(), sales.getTotalItems());
				return sales.getTotalItems();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).mapToLong(Integer::longValue).sum();
		stopWatch.stop();

		log.info("[testSumTotalSalesParallelStream] Total sales: {}, time used: {}", totalSales, stopWatch);
		assertTrue(totalSales >= 0);
	}

	@Test
	@Disabled("Token is required")
	void testSumTotalSalesExecutorService() {
		ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);
		ViagogoClient client = new ResCUViagogoClient(() -> token.get());

		SaleRequest saleRequest = new SaleRequest();

		ExecutorService executorService = Executors.newFixedThreadPool(32);

		StopWatch stopWatch = StopWatch.createStarted();
		long totalSales = getAccounts().entrySet().parallelStream().map(entry -> {
			Callable<Integer> task = () -> {
				token.set((String) entry.getValue());

				try {
					var sales = client.getSaleService().getSales(saleRequest);
					log.debug("{}: {}", entry.getKey(), sales.getTotalItems());
					return sales.getTotalItems();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};
			return executorService.submit(task);
		}).mapToLong(future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).sum();
		stopWatch.stop();

		log.info("[testSumTotalSalesExecutorService] Total sales: {}, time used: {}", totalSales, stopWatch);

		assertTrue(totalSales >= 0);

		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			log.error("Termination interrupted", e);
		}
	}

}
