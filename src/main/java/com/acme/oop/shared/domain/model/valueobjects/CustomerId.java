package com.acme.oop.shared.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a customer across the bounded contexts.
 *
 * @param value the customer identifier, it must not be null
 *
 * @author Open Source Application Development Team
 */
public record CustomerId(UUID value) {
    /**
     * Constructs a CustomerId object with validation.
     * @param value the customer identifier, it must not be null
     */
    public CustomerId {
        if (Objects.isNull(value))
            throw new IllegalArgumentException("Customer identifier cannot be null");
    }

    /**
     * Constructs a CustomerId object with a random UUID value.
     */
    public CustomerId() {
        this(UUID.randomUUID());
    }

    public String toString() {
        return value.toString();
    }
}
