package org.oxerr.viagogo.model.response.sale;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeliveryMethod implements Serializable {

	private static final long serialVersionUID = 2023021801L;

	/**
	 * The delivery method identifier.
	 */
	private Integer id;

	/**
	 * The localised name of the delivery method.
	 */
	private String name;

	/**
	 * The type of delivery method.
	 * Can be {@code ETicket}, {@code Post} or {@code Pickup}.
	 */
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		if (!(obj instanceof DeliveryMethod)) {
			return false;
		}
		DeliveryMethod rhs = (DeliveryMethod) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
