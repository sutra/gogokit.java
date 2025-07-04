package org.oxerr.viagogo.model;

import java.io.Serializable;

public class SeatingState implements Serializable {

	private static final long serialVersionUID = 2025070201L;

	/**
	 * True when the seat is visible in the venue map that will be shown to
	 * buyers; Otherwise, false.
	 */
	private Boolean visibleInVenueMap;

	public Boolean getVisibleInVenueMap() {
		return visibleInVenueMap;
	}

	public void setVisibleInVenueMap(Boolean visibleInVenueMap) {
		this.visibleInVenueMap = visibleInVenueMap;
	}

}
