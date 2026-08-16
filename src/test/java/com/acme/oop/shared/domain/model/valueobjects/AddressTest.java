package com.acme.oop.shared.domain.model.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Address Value Object Tests")
class AddressTest {

    @Nested
    @DisplayName("Creation & Validation")
    class CreationTests {

        @Test
        @DisplayName("Should successfully instantiate a valid address and trim whitespace")
        void shouldCreateValidAddress() {
            Address address = new Address("  123 Main St  ", " Anytown ", " 12345 ", " USA ");

            assertThat(address.street()).isEqualTo("123 Main St");
            assertThat(address.city()).isEqualTo("Anytown");
            assertThat(address.postalCode()).isEqualTo("12345");
            assertThat(address.country()).isEqualTo("USA");
            assertThat(address.toString()).isEqualTo("123 Main St, Anytown, 12345, USA");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Should throw IllegalArgumentException when street is null or blank")
        void shouldThrowExceptionWhenStreetIsInvalid(String invalidStreet) {
            assertThatThrownBy(() -> new Address(invalidStreet, "Anytown", "12345", "USA"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Street cannot be null or blank");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Should throw IllegalArgumentException when city is null or blank")
        void shouldThrowExceptionWhenCityIsInvalid(String invalidCity) {
            assertThatThrownBy(() -> new Address("123 Main St", invalidCity, "12345", "USA"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("City cannot be null or blank");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Should throw IllegalArgumentException when postalCode is null or blank")
        void shouldThrowExceptionWhenPostalCodeIsInvalid(String invalidPostalCode) {
            assertThatThrownBy(() -> new Address("123 Main St", "Anytown", invalidPostalCode, "USA"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Postal code cannot be null or blank");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Should throw IllegalArgumentException when country is null or blank")
        void shouldThrowExceptionWhenCountryIsInvalid(String invalidCountry) {
            assertThatThrownBy(() -> new Address("123 Main St", "Anytown", "12345", invalidCountry))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Country cannot be null or blank");
        }
    }

    @Nested
    @DisplayName("Equality & Immutability")
    class EqualityTests {

        @Test
        @DisplayName("Two address records with identical values should be equal")
        void shouldBeEqualWhenValuesMatch() {
            Address address1 = new Address("123 Main St", "Anytown", "12345", "USA");
            Address address2 = new Address("123 Main St", "Anytown", "12345", "USA");

            assertThat(address1).isEqualTo(address2);
            assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
        }
    }
}
