package org.oxerr.viagogo.model.response.inventory;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.response.Resource;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class ListingConstraints extends Resource {

	private static final long serialVersionUID = 2023021301L;

	private Money minTicketPrice;

	private Money maxTicketPrice;

	private Integer minNumberOfTickets;

	private Integer maxNumberOfTickets;

	private Boolean ticketLocationRequired;

	private Boolean seatsRequired;

	private List<Section> sections;

	private Boolean primaryOrderIdRequired;

	private Boolean homeOrAwayRequired;

	@EmbeddedResource
	private List<ListingNote> connectedSellerListingNotes;

	/**
	 * The valid currencies for the listing ticket price.
	 */
	@EmbeddedResource
	private List<Currency> currencies;

	/**
	 * The available notes for this listing.
	 */
	@EmbeddedResource
	private List<ListingNote> listingNodes;

	@EmbeddedResource
	private List<ListingNote> requiredUniqueListingNotes;

	@EmbeddedResource
	private List<ListingNote> restrictedOnUseListingNotes;

	/**
	 * The way that tickets are allowed to be split up when sold.
	 */
	@EmbeddedResource
	private List<SplitType> splitTypes;

	/**
	 * The valid types of tickets.
	 */
	@EmbeddedResource
	private List<TicketType> ticketTypes;

	public Money getMinTicketPrice() {
		return minTicketPrice;
	}

	public void setMinTicketPrice(Money minTicketPrice) {
		this.minTicketPrice = minTicketPrice;
	}

	public Money getMaxTicketPrice() {
		return maxTicketPrice;
	}

	public void setMaxTicketPrice(Money maxTicketPrice) {
		this.maxTicketPrice = maxTicketPrice;
	}

	public Integer getMinNumberOfTickets() {
		return minNumberOfTickets;
	}

	public void setMinNumberOfTickets(Integer minNumberOfTickets) {
		this.minNumberOfTickets = minNumberOfTickets;
	}

	public Integer getMaxNumberOfTickets() {
		return maxNumberOfTickets;
	}

	public void setMaxNumberOfTickets(Integer maxNumberOfTickets) {
		this.maxNumberOfTickets = maxNumberOfTickets;
	}

	public Boolean getTicketLocationRequired() {
		return ticketLocationRequired;
	}

	public void setTicketLocationRequired(Boolean ticketLocationRequired) {
		this.ticketLocationRequired = ticketLocationRequired;
	}

	public Boolean getSeatsRequired() {
		return seatsRequired;
	}

	public void setSeatsRequired(Boolean seatsRequired) {
		this.seatsRequired = seatsRequired;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Boolean getPrimaryOrderIdRequired() {
		return primaryOrderIdRequired;
	}

	public void setPrimaryOrderIdRequired(Boolean primaryOrderIdRequired) {
		this.primaryOrderIdRequired = primaryOrderIdRequired;
	}

	public Boolean getHomeOrAwayRequired() {
		return homeOrAwayRequired;
	}

	public void setHomeOrAwayRequired(Boolean homeOrAwayRequired) {
		this.homeOrAwayRequired = homeOrAwayRequired;
	}

	public List<ListingNote> getConnectedSellerListingNotes() {
		return connectedSellerListingNotes;
	}

	public void setConnectedSellerListingNotes(List<ListingNote> connectedSellerListingNotes) {
		this.connectedSellerListingNotes = connectedSellerListingNotes;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	public List<ListingNote> getListingNodes() {
		return listingNodes;
	}

	public void setListingNodes(List<ListingNote> listingNodes) {
		this.listingNodes = listingNodes;
	}

	public List<ListingNote> getRequiredUniqueListingNotes() {
		return requiredUniqueListingNotes;
	}

	public void setRequiredUniqueListingNotes(List<ListingNote> requiredUniqueListingNotes) {
		this.requiredUniqueListingNotes = requiredUniqueListingNotes;
	}

	public List<ListingNote> getRestrictedOnUseListingNotes() {
		return restrictedOnUseListingNotes;
	}

	public void setRestrictedOnUseListingNotes(List<ListingNote> restrictedOnUseListingNotes) {
		this.restrictedOnUseListingNotes = restrictedOnUseListingNotes;
	}

	public List<SplitType> getSplitTypes() {
		return splitTypes;
	}

	public void setSplitTypes(List<SplitType> splitTypes) {
		this.splitTypes = splitTypes;
	}

	public List<TicketType> getTicketTypes() {
		return ticketTypes;
	}

	public void setTicketTypes(List<TicketType> ticketTypes) {
		this.ticketTypes = ticketTypes;
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
		ListingConstraints rhs = (ListingConstraints) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
