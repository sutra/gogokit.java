package org.oxerr.viagogo.model.request.inventory;

import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Link;
import org.oxerr.viagogo.model.request.PagedRequest;
import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.inventory.SellerListing;

import io.openapitools.jackson.dataformat.hal.HALLink;

public class SellerListingRequest extends PagedRequest {

	private static final long serialVersionUID = 2023112501L;

	private Long eventId;

	private String requestedEventId;

	public enum Sort implements PagedRequest.Sort {

		AVAILABLE_TICKETS,

		/**
		 * @see SellerListing#getCreatedAt()
		 */
		CREATED_AT,

		/**
		 * @see SellerListing#getEvent()
		 * @see Event#getStartDate()
		 */
		EVENT_DATE,

		/**
		 * @see SellerListing#getEvent()
		 * @see Event#getName()
		 */
		EVENT_NAME,

		/**
		 * @see SellerListing#getExpiresAt()
		 */
		EXPIRATION_DATE,

		PRICE,

		RESOURCE_VERSION,

		TICKET_AVAILABILITY_DATE;

		@Override
		public String getCode() {
			return this.name().toLowerCase(Locale.US);
		}

	}

	/**
	 * Obtains an instance of {@code SellerListingRequest} from a {@link HALLink}.
	 *
	 * @param halLink the {@link HALLink}, not null
	 * @return the {@code SellerListingRequest}, not null
	 */
	public static SellerListingRequest from(HALLink halLink) {
		return new SellerListingRequest(new Link(halLink));
	}

	public SellerListingRequest() {
	}

	public SellerListingRequest(Link link) {
		super(link);
		this.setEventId(link.getFirstAsLong("event_id"));
		this.setRequestedEventId(link.getFirstAsString("requested_event_id"));
	}

	public SellerListingRequest(Long eventId) {
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getRequestedEventId() {
		return requestedEventId;
	}

	public void setRequestedEventId(String requestedEventId) {
		this.requestedEventId = requestedEventId;
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
		SellerListingRequest rhs = (SellerListingRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
