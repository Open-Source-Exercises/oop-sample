package com.acme.oop.sales.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductId Value Object Tests")
class ProductIdTest {

    @Test
    @DisplayName("Should create ProductId with random UUID by default")
    void shouldCreateRandomProductId() {
        ProductId productId = new ProductId();

        assertThat(productId.value()).isNotNull();
        assertThat(productId.toString()).isEqualTo(productId.value().toString());
    }

    @Test
    @DisplayName("Should create ProductId with specific UUID")
    void shouldCreateSpecificProductId() {
        UUID uuid = UUID.randomUUID();
        ProductId productId = new ProductId(uuid);

        assertThat(productId.value()).isEqualTo(uuid);
        assertThat(productId.toString()).isEqualTo(uuid.toString());
    }

    @Test
    @DisplayName("Should throw exception when UUID is null")
    void shouldThrowExceptionWhenNull() {
        assertThatThrownBy(() -> new ProductId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product identifier cannot be null");
    }

    @Test
    @DisplayName("Two ProductId instances with same UUID should be equal")
    void shouldBeEqualForSameUUID() {
        UUID uuid = UUID.randomUUID();
        ProductId p1 = new ProductId(uuid);
        ProductId p2 = new ProductId(uuid);

        assertThat(p1).isEqualTo(p2);
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
    }
}
