package org.oxerr.viagogo.model.response.webhook;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.HALLink;
import io.openapitools.jackson.dataformat.hal.annotation.Link;

public class Webhook extends Resource {

	private static final long serialVersionUID = 2023080201L;

	private Integer id;

	private String name;

	private String url;

	private Instant createdAt;

	private List<String> topics;

	private String authorizationHeader;

	@Link("webhook:delete")
	private HALLink deleteLink;

	@Link("webhook:ping")
	private HALLink pingLink;

	@Link("webhook:update")
	private HALLink updateLink;

	public Webhook() {
	}
	
	public Webhook(String name, String url, String... topics) {
		this.name = name;
		this.url = url;
		this.topics = Arrays.asList(topics);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public String getAuthorizationHeader() {
		return authorizationHeader;
	}

	public void setAuthorizationHeader(String authorizationHeader) {
		this.authorizationHeader = authorizationHeader;
	}

	public HALLink getDeleteLink() {
		return deleteLink;
	}

	public void setDeleteLink(HALLink deleteLink) {
		this.deleteLink = deleteLink;
	}

	public HALLink getPingLink() {
		return pingLink;
	}

	public void setPingLink(HALLink pingLink) {
		this.pingLink = pingLink;
	}

	public HALLink getUpdateLink() {
		return updateLink;
	}

	public void setUpdateLink(HALLink updateLink) {
		this.updateLink = updateLink;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Webhook rhs = (Webhook) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
