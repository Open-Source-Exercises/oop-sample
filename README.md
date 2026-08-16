# OOP Sample

[![Java](https://img.shields.io/badge/Java-26-orange.svg)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE.md)
[![Tests](https://img.shields.io/badge/Tests-89%20Passed-brightgreen.svg)](src/test/java)

A sample Java application illustrating **Object-Oriented Programming (OOP)** and **Domain-Driven Design (DDD)** principles across two bounded contexts: **CRM** (Customer Relationship Management) and **Sales** (Sales Order Management), supported by a **Shared Kernel**.

---

## Table of Contents
- [Architecture & Domain Model](#architecture--domain-model)
- [Project Structure](#project-structure)
- [Key Features & Principles](#key-features--principles)
- [Prerequisites](#prerequisites)
- [Installation & Execution](#installation--execution)
- [Documentation & Architectural Records](#documentation--architectural-records)
- [Contributing](#contributing)
- [Authors](#authors)
- [License](#license)

---

## Architecture & Domain Model

The domain architecture is structured into tactical Domain-Driven Design (DDD) patterns:

```text
+-------------------------------------------------------------------------+
|                              Shared Kernel                              |
|   - Money (Value Object Record)                                         |
|   - Address (Value Object Record)                                       |
|   - CustomerId (Value Object Record)                                    |
+-----------------------------------+-------------------------------------+
                                    |
            +-----------------------+-----------------------+
            |                                               |
            v                                               v
+-----------------------------+               +-----------------------------+
|         CRM Context         |               |        Sales Context        |
|  - Customer (Aggregate Root)|               |  - SalesOrder (Aggregate)   |
|                             |               |  - SalesOrderItem (Entity)  |
|                             |               |  - ProductId (Value Object) |
+-----------------------------+               +-----------------------------+
```

### Class Diagram
The application domain model is specified in [docs/class-diagram.puml](docs/class-diagram.puml).

![Class Diagram](https://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/upc-pre-202620-1asi0729-sandbox/oop-sample/refs/heads/main/docs/class-diagram.puml)

---

## Project Structure

```text
oop-sample/
├── docs/
│   ├── adr/                     # Architecture Decision Records (ADRs)
│   ├── class-diagram.puml       # PlantUML domain model diagram
│   └── user-stories.md          # User stories, acceptance criteria & traceability matrix
├── src/
│   ├── main/java/com/acme/oop/
│   │   ├── Main.java            # Interactive domain demonstration application
│   │   ├── crm/domain/model/
│   │   │   └── aggregates/      # Customer aggregate root
│   │   ├── sales/domain/model/
│   │   │   ├── aggregates/      # SalesOrder aggregate & SalesOrderItem entity
│   │   │   └── valueobjects/    # ProductId value object
│   │   └── shared/domain/model/
│   │       └── valueobjects/    # Money, Address, CustomerId value objects
│   └── test/java/com/acme/oop/  # JUnit 5 & AssertJ unit test suites
├── CHANGELOG.md                 # Version release notes and migration history
├── CONTRIBUTING.md              # Contributor guide and coding conventions
├── LICENSE.md                   # MIT License
├── pom.xml                      # Maven build and plugin definitions
└── README.md                    # Project documentation
```

---

## Key Features & Principles

- **OOP Principles:** Strict encapsulation, immutability, entity equality by identifier, and single responsibility.
- **Domain-Driven Design (DDD):** Explicit bounded contexts, aggregate roots, entities, value objects, and domain invariants.
- **Modern Java 26:** Record classes for value objects with compact constructor validation.
- **Precision Monetary Logic:** Immutable `Money` value object backed by `BigDecimal` and `java.util.Currency` with decimal scale safety.
- **Living Documentation:** JUnit 5 `@Nested` tests mirroring User Story acceptance criteria with AssertJ fluent assertions.

---

## Prerequisites

- **Java Development Kit (JDK):** Version 26
- **Apache Maven:** Version 3.9+

---

## Installation & Execution

1. **Clone the repository:**
   ```bash
   git clone https://github.com/upc-pre-202620-1asi0729-sandbox/oop-sample.git
   cd oop-sample
   ```

2. **Run the test suite (89 unit and acceptance tests):**
   ```bash
   mvn clean test
   ```

3. **Generate Javadoc API documentation:**
   ```bash
   mvn javadoc:javadoc
   # HTML output generated in target/site/apidocs/index.html
   ```

4. **Build and package the JAR:**
   ```bash
   mvn clean package
   ```

5. **Run the interactive application demo:**
   ```bash
   mvn exec:java
   ```

---

## Documentation & Architectural Records

- [User Stories & Traceability Matrix](docs/user-stories.md)
- [Class Diagram Source](docs/class-diagram.puml)
- [Architecture Decision Records (ADRs)](docs/adrs.md)
- [Contributing Guidelines](CONTRIBUTING.md)
- [Changelog](CHANGELOG.md)

---

## Contributing

We welcome contributions from the community. Please refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file for guidelines on how to contribute, report issues, and submit pull requests.

---

## Authors

- **Open-Source Application Development Team**

---

## License

This project is licensed under the MIT License – see the [LICENSE.md](LICENSE.md) file for details.