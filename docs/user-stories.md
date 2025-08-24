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