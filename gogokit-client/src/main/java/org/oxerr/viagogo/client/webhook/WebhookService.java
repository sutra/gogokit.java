package org.oxerr.viagogo.client.webhook;

import java.io.IOException;

import org.oxerr.viagogo.model.response.webhook.Webhook;
import org.oxerr.viagogo.model.response.webhook.Webhooks;

public interface WebhookService {

	Webhooks getWebhooks(Integer page, Integer pageSize, String sort) throws IOException;

	Webhook createWebhook(Webhook webhook) throws IOException;

	Webhook getWebhook(Integer id) throws IOException;

	Webhook updateWebhook(Integer id, Webhook webhook) throws IOException;

	void deleteWebhook(Integer id) throws IOException;

	void ping(Integer id) throws IOException;

}
