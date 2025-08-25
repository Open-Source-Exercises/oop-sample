package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Sales Order aggregate in the Sales context.
 * This aggregate manages the lifecycle of a sales order and its items.
 * @author Open Source Application Development Team
 */

public class SalesOrder {
    @Getter
    private final UUID id;
    @Getter
    private final CustomerId customerId;
    @Getter
    private LocalDateTime orderDate;
    private final List<SalesOrderItem> items;
    @Getter
    private Money totalAmount;

    /**
     * Constructs a SalesOrder instance.
     * @param customerId the customer identifier, it must not be null
     */
    public SalesOrder(@NonNull CustomerId customerId) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalAmount = Money.zero();
    }

    /**
     * Adds a new item to the order.
     * @param productId the product identifier, it must not be null
     * @param quantity  the quantity of the product, it must be greater than zero
     * @param unitPrice the unit price of the product, it must not be null and must be greater than zero
     */
    public void addItem(@NonNull ProductId productId, int quantity, @NonNull Money unitPrice) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice.amount().compareTo(Money.zero().amount()) <= 0)
            throw new IllegalArgumentException("Unit price must be greater than zero");
        SalesOrderItem newItem = new SalesOrderItem(productId, quantity, unitPrice);
        this.items.add(newItem);
        this.totalAmount = calculateTotalAmount();
    }

    /**
     * Calculates the total amount of the order.
     * @return the total amount for the order
     */
    public Money calculateTotalAmount() {
        return this.items.stream().map(SalesOrderItem::calculateItemAmount).reduce(Money.zero(), Money::add);
    }

    /**
     * Sets the order date.
     * This method is used when the system is registering an offline order with a specific date.
     * @param orderDate the order date, it must not be null
     * @return this Sales Order instance
     */
    public SalesOrder withOrderDate(@NonNull LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * Returns the total amount of the order as a string.
     * @return the total amount as a string, formatted as "amount currency"
     */
    public String getTotalAmountAsString() {
        return this.totalAmount.amount() + " " + this.totalAmount.currency().getCurrencyCode();
    }

}
