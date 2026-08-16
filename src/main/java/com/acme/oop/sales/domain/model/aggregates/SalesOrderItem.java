package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents an item within a Sales Order.
 * This entity is managed by the Sales Order aggregate.
 * @author Open Source Application Development Team
 */
@Getter
public class SalesOrderItem {
    private final ProductId productId;
    private final int quantity;
    private final Money unitPrice;

    /**
     * Constructs a SalesOrderItem instance.
     *
     * @param productId the product identifier, it must not be null
     * @param quantity  the quantity of the product, it must be greater than zero
     * @param unitPrice the unit price of the product, it must not be null and must be greater than zero
     * @throws IllegalArgumentException if parameters are null or non-positive
     */
    SalesOrderItem(ProductId productId, int quantity, Money unitPrice) {
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
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the total amount of the item.
     *
     * @return the total amount for the item
     */
    public Money calculateItemAmount() {
        return unitPrice.multiply(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesOrderItem that = (SalesOrderItem) o;
        return quantity == that.quantity &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return String.format("SalesOrderItem[productId=%s, quantity=%d, unitPrice=%s]", productId, quantity, unitPrice);
    }
}
