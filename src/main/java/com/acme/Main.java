package com.acme;

import com.acme.shared.domain.model.valueobjects.Address;

public class Main {
    public static void main(String[] args) {
        // Shared context
        System.out.println("Shared context");
        Address address = new Address("123 Main St", "Anytown", "12345", "USA");
        Address anotherAddress = new Address("456 Main St", "Anytown", "12345", "USA");
        System.out.print("Are both addresses equal?: ");
        System.out.println(address.equals(anotherAddress));
    }
}