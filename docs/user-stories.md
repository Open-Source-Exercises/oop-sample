# User Stories

This document contains the user stories for the OOP Sample.

## US01: Register a New Customer
**As a** CRM user,  
**I want to** register a new customer with their contact information,  
**so that** I can track and manage customer details effectively.

### Acceptance Criteria
- **Scenario: Successful Customer Registration**
    - **Given** a valid customer name, email, and address details are provided,
    - **When** the system creates a new customer,
    - **Then** a customer is registered with a unique ID and the provided details are stored.

- **Scenario: Invalid Customer Details**
    - **Given** an empty name or email is provided,
    - **When** the system attempts to create a new customer,
    - **Then** an exception is thrown with an appropriate error message.

## US02: Update Customer Contact Information
**As a** CRM user,  
**I want to** update a customer's contact information,  
**so that** I can keep customer records current.

### Acceptance Criteria
- **Scenario: Successful Update**
    - **Given** an existing customer exists and valid new email and address details are provided,
    - **When** the system updates the contact information,
    - **Then** the customer's email and address are updated accordingly.

- **Scenario: Invalid Update Details**
    - **Given** an existing customer exists and an empty email is provided,
    - **When** the system attempts to update the contact information,
    - **Then** an exception is thrown with an appropriate error message.

## US03: Create a Sales Order
**As a** sales representative,  
**I want to** create a sales order for a customer,  
**so that** I can process their purchase.

### Acceptance Criteria
- **Scenario: Successful Order Creation**
    - **Given** a valid customer ID is provided,
    - **When** the system creates a new sales order,
    - **Then** an order is created with a unique ID, linked to the customer, and initialized with zero total.

- **Scenario: Invalid Customer ID**
    - **Given** a null customer ID is provided,
    - **When** the system attempts to create a sales order,
    - **Then** an exception is thrown with an appropriate error message.

## US04: Add Item to Sales Order
**As a** sales representative,  
**I want to** add items to a sales order,  
**so that** I can specify what the customer is purchasing.

### Acceptance Criteria
- **Scenario: Successful Item Addition**
    - **Given** an existing sales order exists, and a valid product ID, positive quantity, and positive unit price are provided,
    - **When** the system adds an item to the order,
    - **Then** the item is added to the order and the total amount is updated correctly.

- **Scenario: Invalid Item Details**
    - **Given** an existing sales order exists and invalid parameters (e.g., zero quantity, negative price) are provided,
    - **When** the system attempts to add an item,
    - **Then** an exception is thrown with an appropriate error message.