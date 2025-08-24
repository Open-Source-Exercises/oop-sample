package com.acme.oop;

import com.acme.oop.crm.domain.model.aggregates.Customer;
import com.acme.oop.shared.domain.model.valueobjects.Address;

public class Main {
    public static void main(String[] args) {
        // Shared context

        Address address = new Address("123 Main St", "Anytown", "12345", "USA");
        System.out.println("First Address: " + address.toString());
        Address anotherAddress = new Address("456 Main St", "Anytown", "12345", "USA");
        System.out.println("Second Address: " + anotherAddress.toString());
        // CRM context
        Customer customer = new Customer("John Doe", "john.doe@gmail.com", address);
        System.out.println("Customer: " + customer.getContactInfo());
        // Update customer contact info
        System.out.println("Updating customer contact info...");
        customer.updateContactInfo(customer.getEmail(), anotherAddress);
        System.out.println("Customer: " + customer.getContactInfo());
    }
}