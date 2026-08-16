package com.acme.oop.crm.domain.model.aggregates;

import com.acme.oop.shared.domain.model.valueobjects.Address;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Customer Aggregate Tests")
class CustomerTest {

    private Address sampleAddress;

    @BeforeEach
    void setUp() {
        sampleAddress = new Address("123 Main St", "Anytown", "12345", "USA");
    }

    @Nested
    @DisplayName("US01: Register a New Customer")
    class RegisterCustomerTests {

        @Test
        @DisplayName("Scenario: Successful Customer Registration with valid details")
        void shouldRegisterCustomerSuccessfully() {
            Customer customer = new Customer("John Doe", "john.doe@example.com", sampleAddress);

            assertThat(customer.getId()).isNotNull();
            assertThat(customer.getName()).isEqualTo("John Doe");
            assertThat(customer.getEmail()).isEqualTo("john.doe@example.com");
            assertThat(customer.getAddress()).isEqualTo(sampleAddress);
            assertThat(customer.getContactInfo()).isEqualTo("John Doe <john.doe@example.com>, 123 Main St, Anytown, 12345, USA");
        }

        @Test
        @DisplayName("Should instantiate Customer with specific CustomerId for reconstitution")
        void shouldReconstituteCustomerWithGivenId() {
            CustomerId customerId = new CustomerId(UUID.randomUUID());
            Customer customer = new Customer(customerId, "Jane Doe", "jane.doe@example.com", sampleAddress);

            assertThat(customer.getId()).isEqualTo(customerId);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Scenario: Invalid Customer Details - Empty/blank/null name throws exception")
        void shouldThrowExceptionWhenNameIsInvalid(String invalidName) {
            assertThatThrownBy(() -> new Customer(invalidName, "john.doe@example.com", sampleAddress))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer name cannot be null or blank");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Scenario: Invalid Customer Details - Empty/blank/null email throws exception")
        void shouldThrowExceptionWhenEmailIsInvalid(String invalidEmail) {
            assertThatThrownBy(() -> new Customer("John Doe", invalidEmail, sampleAddress))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer email cannot be null or blank");
        }

        @Test
        @DisplayName("Scenario: Invalid Customer Details - Null address throws exception")
        void shouldThrowExceptionWhenAddressIsNull() {
            assertThatThrownBy(() -> new Customer("John Doe", "john.doe@example.com", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer address cannot be null");
        }
    }

    @Nested
    @DisplayName("US02: Update Customer Contact Information")
    class UpdateContactInfoTests {

        private Customer customer;

        @BeforeEach
        void initCustomer() {
            customer = new Customer("John Doe", "john.doe@example.com", sampleAddress);
        }

        @Test
        @DisplayName("Scenario: Successful Update of email and address")
        void shouldUpdateContactInfoSuccessfully() {
            Address newAddress = new Address("456 Market St", "Metropolis", "67890", "USA");

            customer.updateContactInfo("john.new@example.com", newAddress);

            assertThat(customer.getEmail()).isEqualTo("john.new@example.com");
            assertThat(customer.getAddress()).isEqualTo(newAddress);
            assertThat(customer.getContactInfo()).isEqualTo("John Doe <john.new@example.com>, 456 Market St, Metropolis, 67890, USA");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Scenario: Invalid Update Details - Empty/blank email throws exception")
        void shouldThrowExceptionWhenUpdatingWithInvalidEmail(String invalidEmail) {
            Address newAddress = new Address("456 Market St", "Metropolis", "67890", "USA");

            assertThatThrownBy(() -> customer.updateContactInfo(invalidEmail, newAddress))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer email cannot be null or blank");
        }

        @Test
        @DisplayName("Scenario: Invalid Update Details - Null address throws exception")
        void shouldThrowExceptionWhenUpdatingWithNullAddress() {
            assertThatThrownBy(() -> customer.updateContactInfo("john.new@example.com", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer address cannot be null");
        }

        @Test
        @DisplayName("Should successfully update customer name")
        void shouldUpdateCustomerName() {
            customer.updateName("Johnathan Doe");

            assertThat(customer.getName()).isEqualTo("Johnathan Doe");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t"})
        @DisplayName("Should throw exception when updating name with invalid value")
        void shouldThrowWhenUpdatingWithInvalidName(String invalidName) {
            assertThatThrownBy(() -> customer.updateName(invalidName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Customer name cannot be null or blank");
        }
    }

    @Nested
    @DisplayName("Entity Equality")
    class EqualityTests {

        @Test
        @DisplayName("Two customer entities with the same CustomerId should be equal")
        void shouldBeEqualWhenIdsMatch() {
            CustomerId customerId = new CustomerId();
            Customer c1 = new Customer(customerId, "John Doe", "john@example.com", sampleAddress);
            Customer c2 = new Customer(customerId, "Different Name", "other@example.com", sampleAddress);

            assertThat(c1).isEqualTo(c2);
            assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
        }

        @Test
        @DisplayName("Two customer entities with different CustomerId should not be equal")
        void shouldNotBeEqualWhenIdsDiffer() {
            Customer c1 = new Customer("John Doe", "john@example.com", sampleAddress);
            Customer c2 = new Customer("John Doe", "john@example.com", sampleAddress);

            assertThat(c1).isNotEqualTo(c2);
        }
    }
}
