package com.acme.oop.sales.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a product within Sales bounded context.
 *
 * @param value the product identifier, it must not be null
 *
 * @author Open Source Application Development Team
 */
public record ProductId(UUID value) {
    /**
     * Constructs a ProductId object with validation.
     * @param value the product identifier, it must not be null
     * @throws IllegalArgumentException if the value is null
     */
    public ProductId {
        if (Objects.isNull(value))
            throw new IllegalArgumentException("Product identifier cannot be null");
    }

    /**
     * Constructs a ProductId object with a random UUID value.
     */
    public ProductId() {
        this(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
