package org.oxerr.viagogo.client.cached.redisson.inventory;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.viagogo.client.cached.inventory.ViagogoEvent;

public class ViagogoCachedEvent implements Serializable {

	private static final long serialVersionUID = 2024092201L;

	private String id;

	private Long viagogoEventId;

	private OffsetDateTime startDate;

	public ViagogoCachedEvent() {
	}

	public ViagogoCachedEvent(String id, Long viagogoEventId, OffsetDateTime startDate) {
		this.id = id;
		this.viagogoEventId = viagogoEventId;
		this.startDate = startDate;
	}

	public ViagogoCachedEvent(ViagogoEvent event) {
		this(event.getId(), event.getViagogoEventId(), event.getStartDate());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getViagogoEventId() {
		return viagogoEventId;
	}

	public void setViagogoEventId(Long viagogoEventId) {
		this.viagogoEventId = viagogoEventId;
	}

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public ViagogoEvent toViagogoEvent() {
		return new ViagogoEvent(id, startDate, viagogoEventId);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ViagogoCachedEvent)) {
			return false;
		}
		ViagogoCachedEvent rhs = (ViagogoCachedEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
