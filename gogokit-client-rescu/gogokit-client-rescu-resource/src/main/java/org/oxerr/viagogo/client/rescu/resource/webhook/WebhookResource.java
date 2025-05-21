package org.oxerr.viagogo.client.rescu.resource.webhook;

import org.oxerr.viagogo.client.rescu.resource.ViagogoException;
import org.oxerr.viagogo.model.response.webhook.Webhook;
import org.oxerr.viagogo.model.response.webhook.Webhooks;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/{version}/webhooks")
public interface WebhookResource {

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_Get">List webhooks</a>
	 *
	 * <p>List webhooks for the authenticated user.</p>
	 *
	 * @param page Specifies which page of data to retrieve.
	 * @param pageSize Set custom page sizes on response.
	 * @param sort Determines the ordering of items. A comma-separated string containing {@code resource_version}.
	 * @return all webhooks on the viagogo platform.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Webhooks getWebhooks(
		@QueryParam("page") Integer page,
		@QueryParam("page_size") Integer pageSize,
		@QueryParam("sort") String sort
	) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_Post">Create a webhook</a>
	 *
	 * @param webhook the webhook.
	 * @return the created webhook.
	 * @throws ViagogoException indicates business exception
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Webhook createWebhook(Webhook webhook) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_Get2">Get a webhook</a>
	 *
	 * @param webhookId the webhook ID.
	 * @return the webhook.
	 * @throws ViagogoException indicates business exception
	 */
	@GET
	@Path("/{webhookId}")
	Webhook getWebhook(@PathParam("webhookId") Integer webhookId) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_Patch">Update a webhook</a>
	 *
	 * @param webhookId the webhook ID.
	 * @param webhook the webhook.
	 * @return the updated webhook.
	 * @throws ViagogoException indicates business exception
	 */
	@PATCH
	@Path("/{webhookId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Webhook updateWebhook(@PathParam("webhookId") Integer webhookId, Webhook webhook) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_Delete">Delete a webhook</a>
	 *
	 * @param webhookId the webhook ID.
	 * @throws ViagogoException indicates business exception
	 */
	@DELETE
	@Path("/{webhookId}")
	void deleteWebhook(@PathParam("webhookId") Integer webhookId) throws ViagogoException;

	/**
	 * <a href="https://developer.viagogo.net/api-reference/webhooks#operation/Webhooks_PingWebhook">Ping a webhook</a>
	 *
	 * <p>Triggers a request to be sent to your webhook. Call this endpoint to test your webhook.</p>
	 *
	 * @param webhookId the webhook ID.
	 * @throws ViagogoException indicates business exception
	 */
	@POST
	@Path("/{webhookId}/ping")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	void ping(@PathParam("webhookId") Integer webhookId) throws ViagogoException;

}
