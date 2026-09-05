package com.acme.oop;

import com.acme.oop.crm.domain.model.aggregates.Customer;
import com.acme.oop.sales.domain.model.aggregates.SalesOrder;
import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Address;
import com.acme.oop.shared.domain.model.valueobjects.Money;

import java.util.Currency;

/**
 * Entry point and interactive demonstration for the OOP Sample application.
 * <p>
 * This class demonstrates the creation and interaction of Domain-Driven Design (DDD)
 * aggregates, entities, and value objects across the CRM and Sales bounded contexts,
 * as well as runtime validation of domain invariants.
 * </p>
 *
 * @author Open Source Application Development Team
 * @since 1.0.0
 */
public class Main {

    /**
     * Private constructor to prevent instantiation of the utility/entrypoint class.
     */
    private Main() {
    }

    /**
     * Executes the domain demonstration scenarios.
     *
     */
    static void main() {
        System.out.println("=== 1. Shared Context: Value Objects ===");
        Address address = new Address("123 Main St", "Anytown", "12345", "USA");
        Address newAddress = new Address("456 Market Ave", "Springfield", "67890", "USA");

        Customer customer = new Customer("John Doe", "john.doe@example.com", address);
        System.out.println("Customer Contact Info: " + customer.getContactInfo());

        customer.updateContactInfo("john.new@example.com", newAddress);
        System.out.println("Updated Contact Info: " + customer.getContactInfo());

        System.out.println("\n=== 3. Sales Context: Sales Order Aggregate ===");
        SalesOrder order = new SalesOrder(customer.getId(), Currency.getInstance("USD"));
        ProductId laptopId = new ProductId();
        ProductId mouseId = new ProductId();

        Money laptopPrice = Money.of("999.99", "USD");
        Money mousePrice = Money.of("25.50", "USD");

        order.addItem(laptopId, 1, laptopPrice);
        order.addItem(mouseId, 2, mousePrice);

        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Total Amount: " + order.getTotalAmountAsString());

        try {
            order.addItem(new ProductId(), 1, Money.of("10.00", "EUR"));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected invariant violation: " + e.getMessage());
        }

        try {
            new Customer("   ", "invalid@example.com", address);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected invariant violation: " + e.getMessage());
        }

        System.out.println("\n=== OOP Sample Demo Completed Successfully ===");
        System.out.println("Mi primera modificacion");
    }
}