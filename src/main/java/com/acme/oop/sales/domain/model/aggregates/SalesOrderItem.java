package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents an item within a Sales Order.
 * This entity is managed by the Sales Order aggregate.
 * @author Open Source Application Development Team
 */
@Getter
public class SalesOrderItem {
    private final ProductId productId;
    @Setter private int quantity;
    @Setter private Money unitPrice;

    /**
     * Constructs a SalesOrderItem instance.
     * @param productId the product identifier, it must not be null
     * @param quantity  the quantity of the product, it must be greater than zero
     * @param unitPrice the unit price of the product, it must not be null and must be greater than zero
     */
    SalesOrderItem(@NonNull ProductId productId, int quantity, @NonNull Money unitPrice) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Unit price must be greater than zero");
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the total amount of the item.
     * @return the total amount for the item
     */
    public Money calculateItemAmount() {
        return unitPrice.multiply(quantity);
    }
}
