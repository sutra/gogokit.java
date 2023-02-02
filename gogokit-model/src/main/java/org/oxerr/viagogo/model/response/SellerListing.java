package org.oxerr.viagogo.model.response;

import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.viagogo.model.BarcodeInformation;
import org.oxerr.viagogo.model.Money;
import org.oxerr.viagogo.model.Seating;
import org.oxerr.viagogo.model.SeatingDetail;

import io.openapitools.jackson.dataformat.hal.annotation.Resource;

/**
 * A set of tickets for sale on the viagogo marketplace that belong to the
 * currently authenticated user.
 *
 * <a href=
 * "https://developer.viagogo.net/api-reference/inventory#tag/Resource_SellerListing">SellerListing</a>
 */
@Resource
public class SellerListing {

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
	private Seating seating;

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
	private BarcodeInformation[] barcodes;

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

	public Seating getSeating() {
		return seating;
	}

	public void setSeating(Seating seating) {
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

	public BarcodeInformation[] getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(BarcodeInformation[] barcodes) {
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
		SellerListing rhs = (SellerListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
