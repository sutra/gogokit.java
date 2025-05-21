package org.oxerr.viagogo.client.rescu.impl.webhook;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.client.rescu.resource.webhook.WebhookResource;
import org.oxerr.viagogo.client.webhook.WebhookService;
import org.oxerr.viagogo.model.response.webhook.Webhook;
import org.oxerr.viagogo.model.response.webhook.Webhooks;

public class WebhookServiceImpl implements WebhookService {

	private final WebhookResource webhookResource;

	public WebhookServiceImpl(WebhookResource webhookResource) {
		this.webhookResource = webhookResource;
	}

	@Override
	public Webhooks getWebhooks(Integer page, Integer pageSize, String sort) {
		return this.webhookResource.getWebhooks(page, pageSize, sort);
	}

	@Override
	public Webhook createWebhook(Webhook webhook) {
		return this.webhookResource.createWebhook(webhook);
	}

	@Override
	public Webhook getWebhook(Integer id) {
		return this.webhookResource.getWebhook(id);
	}

	@Override
	public Webhook updateWebhook(Integer id, Webhook webhook) {
		return this.webhookResource.updateWebhook(id, webhook);
	}

	@Override
	public void deleteWebhook(Integer id) {
		try {
			this.webhookResource.deleteWebhook(id);
		} catch (ViagogoException e) {
			if (e.getHttpStatusCode() != NOT_FOUND.getStatusCode()) {
				throw e;
			}
		}
	}

	@Override
	public void ping(Integer id) {
		this.webhookResource.ping(id);
	}

}
