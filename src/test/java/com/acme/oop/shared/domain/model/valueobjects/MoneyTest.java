package com.acme.oop.shared.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Money Value Object Tests")
class MoneyTest {

    private final Currency usd = Currency.getInstance("USD");
    private final Currency eur = Currency.getInstance("EUR");

    @Nested
    @DisplayName("Creation & Validation")
    class CreationTests {

        @Test
        @DisplayName("Should create valid Money instance")
        void shouldCreateValidMoney() {
            Money money = new Money(new BigDecimal("10.50"), usd);

            assertThat(money.amount()).isEqualTo(new BigDecimal("10.50"));
            assertThat(money.currency()).isEqualTo(usd);
            assertThat(money.toString()).isEqualTo("10.50 USD");
        }

        @Test
        @DisplayName("Should create zero USD money by default")
        void shouldCreateZeroUSD() {
            Money zero = Money.zero();

            assertThat(zero.amount()).isEqualTo(BigDecimal.ZERO);
            assertThat(zero.currency()).isEqualTo(usd);
            assertThat(zero.toString()).isEqualTo("0 USD");
        }

        @Test
        @DisplayName("Should create zero money for specific currency")
        void shouldCreateZeroForSpecificCurrency() {
            Money zeroEur = Money.zero(eur);

            assertThat(zeroEur.amount()).isEqualTo(BigDecimal.ZERO);
            assertThat(zeroEur.currency()).isEqualTo(eur);
            assertThat(zeroEur.toString()).isEqualTo("0 EUR");
        }

        @Test
        @DisplayName("Should create Money via of factories")
        void shouldCreateViaFactories() {
            Money m1 = Money.of(new BigDecimal("25.00"), usd);
            Money m2 = Money.of("25.00", "USD");

            assertThat(m1).isEqualTo(m2);
        }

        @Test
        @DisplayName("Should throw exception when amount is negative")
        void shouldThrowWhenAmountIsNegative() {
            assertThatThrownBy(() -> new Money(new BigDecimal("-1.00"), usd))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Amount cannot be null or less than zero");
        }

        @Test
        @DisplayName("Should throw exception when currency is null")
        void shouldThrowWhenCurrencyIsNull() {
            assertThatThrownBy(() -> new Money(new BigDecimal("10.00"), null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Currency cannot be null");
        }

        @Test
        @DisplayName("Should throw exception when amount scale exceeds currency fraction digits")
        void shouldThrowWhenScaleExceedsFractionDigits() {
            assertThatThrownBy(() -> new Money(new BigDecimal("10.555"), usd))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Amount scale must be less than or equal to the currency fraction digits");
        }
    }

    @Nested
    @DisplayName("Arithmetic Operations")
    class ArithmeticTests {

        @Test
        @DisplayName("Should add two Money instances of the same currency")
        void shouldAddSameCurrency() {
            Money m1 = new Money(new BigDecimal("10.50"), usd);
            Money m2 = new Money(new BigDecimal("20.25"), usd);

            Money result = m1.add(m2);

            assertThat(result.amount()).isEqualTo(new BigDecimal("30.75"));
            assertThat(result.currency()).isEqualTo(usd);
        }

        @Test
        @DisplayName("Should throw exception when adding null Money")
        void shouldThrowWhenAddingNull() {
            Money m1 = new Money(new BigDecimal("10.00"), usd);

            assertThatThrownBy(() -> m1.add(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Money to add cannot be null");
        }

        @Test
        @DisplayName("Should throw exception when adding different currencies")
        void shouldThrowWhenAddingDifferentCurrencies() {
            Money m1 = new Money(new BigDecimal("10.00"), usd);
            Money m2 = new Money(new BigDecimal("10.00"), eur);

            assertThatThrownBy(() -> m1.add(m2))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Cannot add different currencies");
        }

        @Test
        @DisplayName("Should multiply Money by positive factor")
        void shouldMultiplyByPositiveFactor() {
            Money m = new Money(new BigDecimal("12.50"), usd);

            Money result = m.multiply(3);

            assertThat(result.amount()).isEqualTo(new BigDecimal("37.50"));
            assertThat(result.currency()).isEqualTo(usd);
        }

        @Test
        @DisplayName("Should multiply Money by zero")
        void shouldMultiplyByZero() {
            Money m = new Money(new BigDecimal("12.50"), usd);

            Money result = m.multiply(0);

            assertThat(result.amount()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Should throw exception when multiplying by negative factor")
        void shouldThrowWhenMultiplyingByNegativeFactor() {
            Money m = new Money(new BigDecimal("12.50"), usd);

            assertThatThrownBy(() -> m.multiply(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Multiplier cannot be negative");
        }
    }
}
