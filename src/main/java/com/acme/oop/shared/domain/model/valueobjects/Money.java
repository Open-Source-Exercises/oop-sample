package com.acme.oop.shared.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Represents a monetary amount with a currency.
 * This value object is immutable and shared across the bounded contexts.
 *
 * @param amount    the monetary amount, it must not be null and must be greater than or equal to zero
 * @param currency  the currency, it must not be null
 *
 * @author Open Source Application Development Team
 */
public record Money(BigDecimal amount, Currency currency) {
    /**
     * Constructs a Money object with validation.
     *
     * @param amount    the monetary amount, it must not be null and must be greater than or equal to zero, with decimal digits according to the currency
     * @param currency  the currency, it must not be null
     *
     * @throws IllegalArgumentException if the amount is null or less than zero, or if the currency is null
     */
    public Money {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be null or less than zero");
        }
        if (Objects.isNull(currency)) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if (amount.scale() > currency.getDefaultFractionDigits()) {
            throw new IllegalArgumentException("Amount scale must be less than or equal to the currency fraction digits");
        }
    }

    /**
     * Creates a zero-amount Money instance in USD.
     *
     * @return a Money instance with zero amount value in USD
     */
    public static Money zero() {
        return zero(Currency.getInstance("USD"));
    }

    /**
     * Creates a zero-amount Money instance in the specified currency.
     *
     * @param currency the currency, it must not be null
     * @return a Money instance with zero amount in the specified currency
     */
    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    /**
     * Factory method to create a Money instance.
     *
     * @param amount   the monetary amount
     * @param currency the currency
     * @return a new Money instance
     */
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    /**
     * Factory method to create a Money instance from string values.
     *
     * @param amount       the monetary amount as string
     * @param currencyCode the ISO 4217 currency code
     * @return a new Money instance
     */
    public static Money of(String amount, String currencyCode) {
        Objects.requireNonNull(currencyCode, "Currency code cannot be null");
        return new Money(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

    /**
     * Adds another Money instance to this one.
     *
     * @param other the Money value to add, must have the same currency
     * @return a new Money instance with the summed amount
     * @throws IllegalArgumentException if other is null or currencies differ
     */
    public Money add(Money other) {
        if (Objects.isNull(other)) {
            throw new IllegalArgumentException("Money to add cannot be null");
        }
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(amount.add(other.amount), currency);
    }

    /**
     * Multiplies this Money instance by a factor.
     *
     * @param multiplier the multiplication factor, must be non-negative
     * @return a new Money instance with the multiplied amount
     * @throws IllegalArgumentException if multiplier is negative
     */
    public Money multiply(int multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }
        return new Money(amount.multiply(BigDecimal.valueOf(multiplier)), this.currency);
    }

    @Override
    public String toString() {
        return String.format("%s %s", amount.toPlainString(), currency.getCurrencyCode());
    }
}
