package org.oxerr.viagogo.model.response.catalog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * An venue on the viagogo platform.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/Resource_Venue">Venue</a>
 */
public class Venue extends EmbeddedVenue {

	private static final long serialVersionUID = 2023021301L;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Venue)) {
			return false;
		}
		Venue rhs = (Venue) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
