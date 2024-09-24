package org.oxerr.viagogo.model.response.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class SplitType extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The type of split.
	 * Can be {@code Any}, {@code None}, {@code AvoidOne},
	 * {@code AvoidOneAndThree}, or {@code Pairs}.
	 */
	private String type;

	/**
	 * The localised name of the type of split.
	 */
	private String name;

	/**
	 * The localised description of the split.
	 */
	private String description;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(obj instanceof SplitType)) {
			return false;
		}
		SplitType rhs = (SplitType) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
