package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("SalesOrder Aggregate Tests")
class SalesOrderTest {

    private CustomerId sampleCustomerId;
    private final Currency usd = Currency.getInstance("USD");
    private final Currency eur = Currency.getInstance("EUR");

    @BeforeEach
    void setUp() {
        sampleCustomerId = new CustomerId();
    }

    @Nested
    @DisplayName("US03: Create a Sales Order")
    class CreateOrderTests {

        @Test
        @DisplayName("Scenario: Successful Order Creation with default USD currency")
        void shouldCreateSalesOrderSuccessfully() {
            SalesOrder order = new SalesOrder(sampleCustomerId);

            assertThat(order.getId()).isNotNull();
            assertThat(order.getCustomerId()).isEqualTo(sampleCustomerId);
            assertThat(order.getOrderDate()).isNotNull();
            assertThat(order.getCurrency()).isEqualTo(usd);
            assertThat(order.getItems()).isEmpty();
            assertThat(order.getTotalAmount()).isEqualTo(Money.zero(usd));
            assertThat(order.getTotalAmountAsString()).isEqualTo("0 USD");
        }

        @Test
        @DisplayName("Scenario: Successful Order Creation with specified EUR currency")
        void shouldCreateSalesOrderWithCustomCurrency() {
            SalesOrder order = new SalesOrder(sampleCustomerId, eur);

            assertThat(order.getCurrency()).isEqualTo(eur);
            assertThat(order.getTotalAmount()).isEqualTo(Money.zero(eur));
            assertThat(order.getTotalAmountAsString()).isEqualTo("0 EUR");
        }

        @Test
        @DisplayName("Scenario: Invalid Customer ID - Null customer ID throws exception")
        void shouldThrowExceptionWhenCustomerIdIsNull() {
            assertThatThrownBy(() -> new SalesOrder(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer identifier cannot be null");
        }
    }

    @Nested
    @DisplayName("US04: Add Item to Sales Order")
    class AddItemTests {

        private SalesOrder order;

        @BeforeEach
        void initOrder() {
            order = new SalesOrder(sampleCustomerId, usd);
        }

        @Test
        @DisplayName("Scenario: Successful Item Addition - single item updates total")
        void shouldAddItemAndCalculateTotal() {
            ProductId productId = new ProductId();
            Money unitPrice = new Money(new BigDecimal("29.99"), usd);

            order.addItem(productId, 2, unitPrice);

            assertThat(order.getItems()).hasSize(1);
            assertThat(order.getTotalAmount().amount()).isEqualTo(new BigDecimal("59.98"));
            assertThat(order.getTotalAmountAsString()).isEqualTo("59.98 USD");
        }

        @Test
        @DisplayName("Scenario: Successful Item Addition - multiple items calculate cumulative total")
        void shouldAddMultipleItemsAndCalculateCumulativeTotal() {
            ProductId product1 = new ProductId();
            ProductId product2 = new ProductId();
            Money price1 = new Money(new BigDecimal("10.00"), usd);
            Money price2 = new Money(new BigDecimal("25.50"), usd);

            order.addItem(product1, 3, price1); // 30.00
            order.addItem(product2, 2, price2); // 51.00

            assertThat(order.getItems()).hasSize(2);
            assertThat(order.getTotalAmount().amount()).isEqualTo(new BigDecimal("81.00"));
            assertThat(order.getTotalAmountAsString()).isEqualTo("81.00 USD");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -5})
        @DisplayName("Scenario: Invalid Item Details - Non-positive quantity throws exception")
        void shouldThrowWhenAddingItemWithInvalidQuantity(int invalidQuantity) {
            ProductId productId = new ProductId();
            Money unitPrice = new Money(new BigDecimal("10.00"), usd);

            assertThatThrownBy(() -> order.addItem(productId, invalidQuantity, unitPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Quantity must be greater than zero");
        }

        @Test
        @DisplayName("Scenario: Invalid Item Details - Null or zero unit price throws exception")
        void shouldThrowWhenAddingItemWithZeroOrNullPrice() {
            ProductId productId = new ProductId();

            assertThatThrownBy(() -> order.addItem(productId, 2, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Unit price cannot be null");

            assertThatThrownBy(() -> order.addItem(productId, 2, Money.zero(usd)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Unit price must be greater than zero");
        }

        @Test
        @DisplayName("Scenario: Invalid Item Details - Null ProductId throws exception")
        void shouldThrowWhenAddingItemWithNullProductId() {
            Money unitPrice = new Money(new BigDecimal("10.00"), usd);

            assertThatThrownBy(() -> order.addItem(null, 1, unitPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Product identifier cannot be null");
        }

        @Test
        @DisplayName("Should throw exception when item currency does not match order currency")
        void shouldThrowWhenItemCurrencyMismatchesOrderCurrency() {
            ProductId productId = new ProductId();
            Money eurPrice = new Money(new BigDecimal("20.00"), eur);

            assertThatThrownBy(() -> order.addItem(productId, 1, eurPrice))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Unit price currency (EUR) does not match order currency (USD)");
        }

        @Test
        @DisplayName("Returned items list should be unmodifiable")
        void returnedItemsListShouldBeUnmodifiable() {
            ProductId productId = new ProductId();
            Money unitPrice = new Money(new BigDecimal("10.00"), usd);
            order.addItem(productId, 1, unitPrice);

            assertThatThrownBy(() -> order.getItems().clear())
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("Lifecycle & Equality")
    class LifecycleTests {

        @Test
        @DisplayName("Should support withOrderDate for historical registration")
        void shouldSupportWithOrderDate() {
            SalesOrder order = new SalesOrder(sampleCustomerId);
            LocalDateTime specificDate = LocalDateTime.of(2025, 1, 15, 10, 30);

            order.withOrderDate(specificDate);

            assertThat(order.getOrderDate()).isEqualTo(specificDate);
        }

        @Test
        @DisplayName("Two orders with the same UUID should be equal")
        void shouldBeEqualWhenIdsMatch() {
            UUID orderId = UUID.randomUUID();
            SalesOrder o1 = new SalesOrder(orderId, sampleCustomerId, LocalDateTime.now(), usd);
            SalesOrder o2 = new SalesOrder(orderId, new CustomerId(), LocalDateTime.now(), usd);

            assertThat(o1).isEqualTo(o2);
            assertThat(o1.hashCode()).isEqualTo(o2.hashCode());
        }
    }
}
