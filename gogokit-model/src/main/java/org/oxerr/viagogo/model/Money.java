package org.oxerr.viagogo.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Returned for monetary values, such as ticket prices, fees charged and tax amounts.
 *
 * <a href="https://developer.viagogo.net/api-reference/catalog#tag/BasicType_Money">Money</a>
 */
public class Money implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	/**
	 * The decimal amount of the money.
	 */
	@Nullable
	private BigDecimal amount;

	/**
	 * The ISO 4217 currency code that the monetary value is represented in.
	 */
	private String currencyCode;

	/**
	 * A user-friendly string representing the monetary value.
	 */
	private String display;

	public static Money of(String amount, String currencyCode) {
		return new Money(new BigDecimal(amount), currencyCode, null);
	}

	public Money() {
	}

	public Money(BigDecimal amount, String currencyCode, String display) {
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.display = display;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
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
		Money rhs = (Money) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return this.display;
	}

}
