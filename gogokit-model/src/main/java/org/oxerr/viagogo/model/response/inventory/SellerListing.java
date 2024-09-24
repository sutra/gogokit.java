package org.oxerr.viagogo.model.response.inventory;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.BarcodeInformation;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.SeatingDetail;
import org.oxerr.viagogo.model.response.Resource;
import org.oxerr.viagogo.model.response.catalog.Event;
import org.oxerr.viagogo.model.response.catalog.Venue;

import io.openapitools.jackson.dataformat.hal.annotation.EmbeddedResource;

/**
 * A set of tickets for sale on the viagogo marketplace that belong to the
 * currently authenticated user.
 *
 * <a href=
 * "https://developer.viagogo.net/api-reference/inventory#tag/Resource_SellerListing">SellerListing</a>
 */
@io.openapitools.jackson.dataformat.hal.annotation.Resource
public class SellerListing extends Resource {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The listing identifier.
	 */
	private Long id;

	/**
	 * The date when the listing was created.
	 */
	private Instant createdAt;

	/**
	 * The number of tickets available for purchase.
	 */
	private Integer numberOfTickets;

	/**
	 * The seating information for the ticket(s) in this listing.
	 */
	private SeatingDetail seating;

	/**
	 * The seating information that will be displayed to buyers for the ticket(s) in
	 * this listing.
	 */
	private SeatingDetail displaySeating;

	/**
	 * The price of each ticket in the listing.
	 */
	private Money ticketPrice;

	/**
	 * The date when the listing was last updated.
	 */
	private Instant updatedAt;

	/**
	 * An identifier that has been assigned to the listing in an external inventory
	 * management system.
	 */
	private String externalId;

	/**
	 * The date when the listing will be automatically unpublished from the
	 * marketplace.
	 */
	private Instant expiresAt;

	/**
	 * The number of tickets that should be displayed to buyers as available for
	 * purchase.
	 */
	private Integer displayNumberOfTickets;

	/**
	 * The price printed on the ticket, not including any booking fees.
	 */
	private Money faceValue;

	/**
	 * The amount that the seller will receive for each ticket sold.
	 */
	private Money ticketProceeds;

	/**
	 * The date when the seller will have the tickets in hand.
	 */
	private Instant inHandAt;

	/**
	 * Barcode(s) for the ticket(s) in this listing.
	 */
	private List<BarcodeInformation> barcodes;

	private Boolean instantDelivery;

	/**
	 * Shipment of the ticket to the buyer cannot be guaranteed before the event. To
	 * increase the deliverability of electronic type tickets, preupload them (e.g.
	 * pre-upload barcodes or PDF files.) To increase deliverability of paper
	 * tickets, opt your tickets into our LMS program.
	 */
	private Boolean undeliverable;

	/**
	 * The purchase price paid by the seller for each ticket in the listing.
	 */
	private Money purchasePricePerTicket;

	/**
	 * The total purchase price paid by the seller.
	 */
	private Money totalPurchasePrice;

	/**
	 * True if the seller already paid sales tax for the ticket(s); otherwise,
	 * false.
	 */
	private Boolean salesTaxPaid;

	@EmbeddedResource
	private ListingConstraints constraints;

	@EmbeddedResource
	private Event event;

	@EmbeddedResource
	private List<ListingNote> listingNotes;

	@EmbeddedResource
	private SplitType splitType;

	@EmbeddedResource
	private TicketType ticketType;

	@EmbeddedResource
	private Venue venue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public SeatingDetail getSeating() {
		return seating;
	}

	public void setSeating(SeatingDetail seating) {
		this.seating = seating;
	}

	public SeatingDetail getDisplaySeating() {
		return displaySeating;
	}

	public void setDisplaySeating(SeatingDetail displaySeating) {
		this.displaySeating = displaySeating;
	}

	public Money getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Money ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Integer getDisplayNumberOfTickets() {
		return displayNumberOfTickets;
	}

	public void setDisplayNumberOfTickets(Integer displayNumberOfTickets) {
		this.displayNumberOfTickets = displayNumberOfTickets;
	}

	public Money getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Money faceValue) {
		this.faceValue = faceValue;
	}

	public Money getTicketProceeds() {
		return ticketProceeds;
	}

	public void setTicketProceeds(Money ticketProceeds) {
		this.ticketProceeds = ticketProceeds;
	}

	public Instant getInHandAt() {
		return inHandAt;
	}

	public void setInHandAt(Instant inHandAt) {
		this.inHandAt = inHandAt;
	}

	public List<BarcodeInformation> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<BarcodeInformation> barcodes) {
		this.barcodes = barcodes;
	}

	public Boolean getInstantDelivery() {
		return instantDelivery;
	}

	public void setInstantDelivery(Boolean instantDelivery) {
		this.instantDelivery = instantDelivery;
	}

	public Boolean getUndeliverable() {
		return undeliverable;
	}

	public void setUndeliverable(Boolean undeliverable) {
		this.undeliverable = undeliverable;
	}

	public Money getPurchasePricePerTicket() {
		return purchasePricePerTicket;
	}

	public void setPurchasePricePerTicket(Money purchasePricePerTicket) {
		this.purchasePricePerTicket = purchasePricePerTicket;
	}

	public Money getTotalPurchasePrice() {
		return totalPurchasePrice;
	}

	public void setTotalPurchasePrice(Money totalPurchasePrice) {
		this.totalPurchasePrice = totalPurchasePrice;
	}

	public Boolean getSalesTaxPaid() {
		return salesTaxPaid;
	}

	public void setSalesTaxPaid(Boolean salesTaxPaid) {
		this.salesTaxPaid = salesTaxPaid;
	}

	public ListingConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(ListingConstraints constraints) {
		this.constraints = constraints;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<ListingNote> getListingNotes() {
		return listingNotes;
	}

	public void setListingNotes(List<ListingNote> listingNotes) {
		this.listingNotes = listingNotes;
	}

	public SplitType getSplitType() {
		return splitType;
	}

	public void setSplitType(SplitType splitType) {
		this.splitType = splitType;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
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
		if (!(obj instanceof SellerListing)) {
			return false;
		}
		SellerListing rhs = (SellerListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return String.format("seller listing id=%s(externalId=%s)", this.id, this.externalId);
	}

}
