package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("SalesOrderItem Aggregate Component Tests")
class SalesOrderItemTest {

    private final Currency usd = Currency.getInstance("USD");

    @Nested
    @DisplayName("Creation & Invariant Validation")
    class CreationTests {

        @Test
        @DisplayName("Should create valid SalesOrderItem and compute item amount")
        void shouldCreateValidItem() {
            ProductId productId = new ProductId();
            Money unitPrice = new Money(new BigDecimal("19.99"), usd);

            SalesOrderItem item = new SalesOrderItem(productId, 3, unitPrice);

            assertThat(item.getProductId()).isEqualTo(productId);
            assertThat(item.getQuantity()).isEqualTo(3);
            assertThat(item.getUnitPrice()).isEqualTo(unitPrice);
            assertThat(item.calculateItemAmount().amount()).isEqualTo(new BigDecimal("59.97"));
            assertThat(item.calculateItemAmount().currency()).isEqualTo(usd);
        }

        @Test
        @DisplayName("Should throw exception when productId is null")
        void shouldThrowWhenProductIdIsNull() {
            Money unitPrice = new Money(new BigDecimal("19.99"), usd);

            assertThatThrownBy(() -> new SalesOrderItem(null, 2, unitPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Product identifier cannot be null");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -10})
        @DisplayName("Should throw exception when quantity is zero or negative")
        void shouldThrowWhenQuantityIsNotPositive(int invalidQuantity) {
            ProductId productId = new ProductId();
            Money unitPrice = new Money(new BigDecimal("19.99"), usd);

            assertThatThrownBy(() -> new SalesOrderItem(productId, invalidQuantity, unitPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Quantity must be greater than zero");
        }

        @Test
        @DisplayName("Should throw exception when unitPrice is null")
        void shouldThrowWhenUnitPriceIsNull() {
            ProductId productId = new ProductId();

            assertThatThrownBy(() -> new SalesOrderItem(productId, 2, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Unit price cannot be null");
        }

        @Test
        @DisplayName("Should throw exception when unitPrice amount is zero")
        void shouldThrowWhenUnitPriceIsZero() {
            ProductId productId = new ProductId();
            Money zeroPrice = Money.zero(usd);

            assertThatThrownBy(() -> new SalesOrderItem(productId, 2, zeroPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Unit price must be greater than zero");
        }
    }

    @Nested
    @DisplayName("Equality & Representation")
    class EqualityTests {

        @Test
        @DisplayName("Two items with same product, quantity, and unit price should be equal")
        void shouldBeEqualWhenAttributesMatch() {
            ProductId productId = new ProductId();
            Money price = new Money(new BigDecimal("10.00"), usd);

            SalesOrderItem item1 = new SalesOrderItem(productId, 2, price);
            SalesOrderItem item2 = new SalesOrderItem(productId, 2, price);

            assertThat(item1).isEqualTo(item2);
            assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
        }
    }
}
