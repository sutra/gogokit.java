package org.oxerr.viagogo.client.rescu.webhook;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;

import org.oxerr.viagogo.client.rescu.ViagogoException;
import org.oxerr.viagogo.client.webhook.WebhookService;
import org.oxerr.viagogo.model.response.webhook.Webhook;
import org.oxerr.viagogo.model.response.webhook.Webhooks;

import si.mazi.rescu.HttpStatusIOException;

public class WebhookServiceImpl implements WebhookService {

	private final WebhookResource webhookResource;

	public WebhookServiceImpl(WebhookResource webhookResource) {
		this.webhookResource = webhookResource;
	}

	@Override
	public Webhooks getWebhooks(Integer page, Integer pageSize, String sort) throws IOException {
		return this.webhookResource.getWebhooks(page, pageSize, sort);
	}

	@Override
	public Webhook createWebhook(Webhook webhook) throws IOException {
		return this.webhookResource.createWebhook(webhook);
	}

	@Override
	public Webhook getWebhook(Integer id) throws IOException {
		return this.webhookResource.getWebhook(id);
	}

	@Override
	public Webhook updateWebhook(Integer id, Webhook webhook) throws IOException {
		return this.webhookResource.updateWebhook(id, webhook);
	}

	@Override
	public void deleteWebhook(Integer id) throws IOException {
		try {
			this.webhookResource.deleteWebhook(id);
		} catch (ViagogoException | HttpStatusIOException e) {
			if (e.getHttpStatusCode() != NOT_FOUND.getStatusCode()) {
				throw e;
			}
		}
	}

	@Override
	public void ping(Integer id) throws IOException {
		this.webhookResource.ping(id);
	}

}
