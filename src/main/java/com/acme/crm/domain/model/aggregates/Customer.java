package com.acme.crm.domain.model.aggregates;

import com.acme.shared.domain.model.valueobjects.Address;
import com.acme.shared.domain.model.valueobjects.CustomerId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a Customer aggregate in the CRM bounded context.
 *
 * @author Open Source Application Development Team
 */
@Getter
public class Customer {
    private final CustomerId id;
    @Setter @NonNull private String name;
    @Setter @NonNull private String email;
    @Setter @NonNull private Address address;

    /**
     * Creates a Customer aggregate with the given information.
     * @param name      the customer name, it must not be null or blank
     * @param email     the customer email, it must not be null or blank
     * @param address   the customer address, it must not be null
     *
     * @throws IllegalArgumentException if any of the parameters is null or blank
     */
    public Customer(String name, String email, Address address) {
        if (Objects.isNull(name) || name.isBlank())
            throw new IllegalArgumentException("Customer name cannot be null or blank");
        if (Objects.isNull(email) || email.isBlank())
            throw new IllegalArgumentException("Customer email cannot be null or blank");
        if (Objects.isNull(address))
            throw new IllegalArgumentException("Customer address cannot be null");

        this.id = new CustomerId();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * Updates the contact information of the customer.
     * @param email     the customer email, it must not be null or blank
     * @param address   the customer address, it must not be null
     */
    public void updateContactInfo(@NonNull String email, @NonNull Address address) {
        if (email.isBlank())
            throw new IllegalArgumentException("Customer email cannot be null or blank");
        this.email = email;
        this.address = address;
    }

    public String getContactInfo() {
        return String.format("%s <%s>, %s", name, email, address);
    }
}
