package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a Sales Order aggregate in the Sales context.
 * This aggregate manages the lifecycle of a sales order and its items.
 * @author Open Source Application Development Team
 */

@Getter
public class SalesOrder {
    private final UUID id;
    private final CustomerId customerId;
    private final Currency currency;
    private LocalDateTime orderDate;
    private final List<SalesOrderItem> items;
    private Money totalAmount;

    /**
     * Constructs a SalesOrder instance for repository reconstitution.
     *
     * @param id         the unique identifier, it must not be null
     * @param customerId the customer identifier, it must not be null
     * @param orderDate  the order date, it must not be null
     * @param currency   the order currency, it must not be null
     * @throws IllegalArgumentException if any parameter is null
     */
    public SalesOrder(UUID id, CustomerId customerId, LocalDateTime orderDate, Currency currency) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Order identifier cannot be null");
        }
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("Customer identifier cannot be null");
        }
        if (Objects.isNull(orderDate)) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
        if (Objects.isNull(currency)) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.currency = currency;
        this.items = new ArrayList<>();
        this.totalAmount = Money.zero(currency);
    }

    /**
     * Constructs a SalesOrder instance with a specified currency.
     *
     * @param customerId the customer identifier, it must not be null
     * @param currency   the order currency, it must not be null
     * @throws IllegalArgumentException if customerId or currency is null
     */
    public SalesOrder(CustomerId customerId, Currency currency) {
        this(UUID.randomUUID(), customerId, LocalDateTime.now(), currency);
    }

    /**
     * Constructs a SalesOrder instance in USD currency.
     *
     * @param customerId the customer identifier, it must not be null
     * @throws IllegalArgumentException if customerId is null
     */
    public SalesOrder(CustomerId customerId) {
        this(customerId, Currency.getInstance("USD"));
    }

    /**
     * Adds a new item to the order.
     *
     * @param productId the product identifier, it must not be null
     * @param quantity  the quantity of the product, it must be greater than zero
     * @param unitPrice the unit price of the product, it must match the order currency and be greater than zero
     * @throws IllegalArgumentException if parameters are invalid or currencies mismatch
     */
    public void addItem(ProductId productId, int quantity, Money unitPrice) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("Product identifier cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (Objects.isNull(unitPrice)) {
            throw new IllegalArgumentException("Unit price cannot be null");
        }
        if (unitPrice.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        if (!unitPrice.currency().equals(this.currency)) {
            throw new IllegalArgumentException(String.format(
                    "Unit price currency (%s) does not match order currency (%s)",
                    unitPrice.currency().getCurrencyCode(), this.currency.getCurrencyCode()
            ));
        }
        SalesOrderItem newItem = new SalesOrderItem(productId, quantity, unitPrice);
        this.items.add(newItem);
        this.totalAmount = calculateTotalAmount();
    }

    /**
     * Calculates the total amount of the order.
     *
     * @return the total amount for the order
     */
    public Money calculateTotalAmount() {
        return this.items.stream()
                .map(SalesOrderItem::calculateItemAmount)
                .reduce(Money.zero(this.currency), Money::add);
    }

    /**
     * Returns an unmodifiable list of the order items.
     *
     * @return unmodifiable list of sales order items
     */
    public List<SalesOrderItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    /**
     * Sets the order date.
     *
     * @param orderDate the order date, it must not be null
     * @return this Sales Order instance
     */
    public SalesOrder withOrderDate(LocalDateTime orderDate) {
        if (Objects.isNull(orderDate)) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
        this.orderDate = orderDate;
        return this;
    }

    /**
     * Returns the total amount of the order as a string.
     *
     * @return the total amount as a string, formatted as "amount currency"
     */
    public String getTotalAmountAsString() {
        return this.totalAmount.amount().toPlainString() + " " + this.totalAmount.currency().getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesOrder that = (SalesOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("SalesOrder[id=%s, customerId=%s, orderDate=%s, items=%d, totalAmount=%s]",
                id, customerId, orderDate, items.size(), totalAmount);
    }
}
