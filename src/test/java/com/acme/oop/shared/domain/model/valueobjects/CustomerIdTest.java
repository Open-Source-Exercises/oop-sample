package com.acme.oop.shared.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CustomerId Value Object Tests")
class CustomerIdTest {

    @Test
    @DisplayName("Should create CustomerId with random UUID by default")
    void shouldCreateRandomCustomerId() {
        CustomerId customerId = new CustomerId();

        assertThat(customerId.value()).isNotNull();
        assertThat(customerId.toString()).isEqualTo(customerId.value().toString());
    }

    @Test
    @DisplayName("Should create CustomerId with specific UUID")
    void shouldCreateSpecificCustomerId() {
        UUID uuid = UUID.randomUUID();
        CustomerId customerId = new CustomerId(uuid);

        assertThat(customerId.value()).isEqualTo(uuid);
        assertThat(customerId.toString()).isEqualTo(uuid.toString());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowExceptionWhenValueIsNull() {
        assertThatThrownBy(() -> new CustomerId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer identifier cannot be null");
    }

    @Test
    @DisplayName("Two CustomerId instances with same UUID should be equal")
    void shouldBeEqualForSameUUID() {
        UUID uuid = UUID.randomUUID();
        CustomerId id1 = new CustomerId(uuid);
        CustomerId id2 = new CustomerId(uuid);

        assertThat(id1).isEqualTo(id2);
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }
}
