# OOP Sample

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE.md)

## Overview

This project is a sample Java application illustrating Object-Oriented Programming (OOP) and Domain-Driven Design (DDD) principles in a Customer Relationship Management (CRM) domain. It features two bounded contexts: CRM (for customer management) and Sales (for sales order management), implemented with Object-Oriented and Functional Java features.

### Authors
- **Open Source Application Development Team**

### Features
- **OOP Principles**: Strict encapsulation, immutability, entity equality, and single responsibility
- **Domain-Driven Design Concepts**: Bounded contexts, aggregates, entities, and value objects
- **Java 26**: Records for value objects and modern language features
- **Lombok**: Boilerplate reduction for entity getters
- **JUnit 5 & AssertJ**: Comprehensive unit testing and acceptance criteria validation
- **Maven**: Build and lifecycle management
- **Javadoc**: Comprehensive domain documentation

## Class Diagram
The following class diagram illustrates the structure of the application, including the main classes and their relationships.

![classDiagram](https://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/upc-pre-202610-1asi0729-sandbox/oop-sample/refs/heads/master/docs/class-diagram.puml)

## Prerequisites
- Java 26 (JDK 26)
- Maven 3.9+

## Installation & Execution

1. Clone the repository:
   ```bash
   git clone https://github.com/<organization-name>/oop-sample.git
   cd oop-sample
   ```

2. Run the test suite:
   ```bash
   mvn clean test
   ```

3. Build and package the project:
   ```bash
   mvn clean package
   ```

4. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.acme.oop.Main"
   ```