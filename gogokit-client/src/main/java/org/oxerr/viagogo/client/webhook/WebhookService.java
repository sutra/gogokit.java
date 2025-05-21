package org.oxerr.viagogo.client.webhook;

import org.oxerr.viagogo.model.response.webhook.Webhook;
import org.oxerr.viagogo.model.response.webhook.Webhooks;

public interface WebhookService {

	Webhooks getWebhooks(Integer page, Integer pageSize, String sort);

	Webhook createWebhook(Webhook webhook);

	Webhook getWebhook(Integer id);

	Webhook updateWebhook(Integer id, Webhook webhook);

	void deleteWebhook(Integer id);

	void ping(Integer id);

}
