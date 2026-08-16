package com.acme.oop.crm.domain.model.aggregates;

import com.acme.oop.shared.domain.model.valueobjects.Address;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a Customer aggregate in the CRM bounded context.
 *
 * @author Open Source Application Development Team
 */
@Getter
public class Customer {
    private final CustomerId id;
    private String name;
    private String email;
    private Address address;

    /**
     * Constructs a Customer aggregate with an existing ID, for repository reconstitution.
     *
     * @param id      the customer unique identifier, which must not be null
     * @param name    the customer name, which must not be null or blank
     * @param email   the customer email, which must not be null or blank
     * @param address the customer address, which must not be null
     * @throws IllegalArgumentException if any argument is null or blank
     */
    public Customer(CustomerId id, String name, String email, Address address) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Customer identifier cannot be null");
        }
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank");
        }
        if (Objects.isNull(email) || email.isBlank()) {
            throw new IllegalArgumentException("Customer email cannot be null or blank");
        }
        if (Objects.isNull(address)) {
            throw new IllegalArgumentException("Customer address cannot be null");
        }

        this.id = id;
        this.name = name.strip();
        this.email = email.strip();
        this.address = address;
    }

    /**
     * Creates a new Customer aggregate with a generated ID.
     *
     * @param name    the customer name, it must not be null or blank
     * @param email   the customer email, it must not be null or blank
     * @param address the customer address, it must not be null
     * @throws IllegalArgumentException if any of the parameters is null or blank
     */
    public Customer(String name, String email, Address address) {
        this(new CustomerId(), name, email, address);
    }

    /**
     * Updates the contact information of the customer.
     *
     * @param email   the new customer email, which must not be null or blank
     * @param address the new customer address, which must not be null
     * @throws IllegalArgumentException thrown if email is null/blank or address is null
     */
    public void updateContactInfo(String email, Address address) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new IllegalArgumentException("Customer email cannot be null or blank");
        }
        if (Objects.isNull(address)) {
            throw new IllegalArgumentException("Customer address cannot be null");
        }
        this.email = email.strip();
        this.address = address;
    }

    /**
     * Updates the customer's name.
     *
     * @param name the new customer name, which must not be null or blank
     * @throws IllegalArgumentException if the name is null or blank
     */
    public void updateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank");
        }
        this.name = name.strip();
    }

    /**
     * Returns formatted customer contact information.
     *
     * @return contact information string representation
     */
    public String getContactInfo() {
        return String.format("%s <%s>, %s", name, email, address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, name=%s, email=%s, address=%s]", id, name, email, address);
    }
}
