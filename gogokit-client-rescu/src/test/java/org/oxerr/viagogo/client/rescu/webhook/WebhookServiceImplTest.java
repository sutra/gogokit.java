package org.oxerr.viagogo.client.rescu.webhook;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.viagogo.client.rescu.ResCUViagogoClientTest;
import org.oxerr.viagogo.client.webhook.WebhookService;
import org.oxerr.viagogo.model.response.webhook.Webhook;

/**
 * Tests for {@link WebhookServiceImpl}.
 */
@Disabled("Token is required")
class WebhookServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final WebhookService webhookService = ResCUViagogoClientTest.getClient().getWebhookService();

	/**
	 * Test for {@link WebhookServiceImpl#getWebhooks(Integer, Integer, String)}.
	 */
	@Test
	@Disabled("Token is required")
	void testGetWebhooks() throws IOException {
		var webhooks = this.webhookService.getWebhooks(null, null, null);
		log.info("webhook count: {}", webhooks.getItems().size());
	}

	/**
	 * Test for {@link WebhookServiceImpl#createWebhook(Webhook)}.
	 */
	@Test
	@Disabled("Token is required")
	void testCreateWebhook() throws IOException {
		var webhook = new Webhook("hello webhook", "https://www.example.com", "Sales");
		var createdWebhook = this.webhookService.createWebhook(webhook);
		log.info("created webhook: {}", createdWebhook.getId());
	}

	/**
	 * Test for {@link WebhookServiceImpl#getWebhook(Integer)}.
	 * @throws IOException
	 */
	@Test
	@Disabled("Token is required")
	void testGetWebhook() throws IOException {
		var webhook = this.webhookService.getWebhook(88431);
		log.info("webhook: {}", webhook.getSelfLink().getHref());
		log.info("webhook: {}", webhook.getDeleteLink().getHref());
	}

	/**
	 * Tests for {@link WebhookServiceImpl#updateWebhook(Integer, Webhook)}.
	 */
	@Test
	@Disabled("Token is required")
	void testUpdateWebhook() throws IOException {
		var id = 1;
		var webhook = new Webhook();
		this.webhookService.updateWebhook(id, webhook);
	}

	/**
	 * Test for {@link WebhookServiceImpl#deleteWebhook(Integer)}.
	 */
	@Test
	@Disabled("Token is required")
	void testDeleteWebhook() throws IOException {
		var id = 1;
		this.webhookService.deleteWebhook(id);
	}

	/**
	 * Test for {@link WebhookServiceImpl#ping(Integer)}.
	 */
	@Test
	@Disabled("Token is required")
	void testPing() throws IOException {
		var id = 1;
		this.webhookService.ping(id);
	}

}
