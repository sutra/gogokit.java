package org.oxerr.viagogo.model.response.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.response.Resource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class ListingNote extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The listing note identifier.
	 */
	private Integer id;

	/**
	 * The localized note.
	 */
	private String note;

	/**
	 * The listing note type name.
	 * Can be {@code Information}, {@code Perk}, {@code Defect}
	 * and {@code RestrictionOnUse}.
	 */
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
		if (!(obj instanceof ListingNote)) {
			return false;
		}
		ListingNote rhs = (ListingNote) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
