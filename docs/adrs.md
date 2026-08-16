# Architecture Decision Records (ADRs)

This document consolidates key Architecture Decision Records (ADRs) capturing the structural and design decisions made in the **OOP Sample** codebase.

---

## Index of Decisions

- [ADR-0001: Use Java Records for Domain Value Objects](#adr-0001-use-java-records-for-domain-value-objects)
- [ADR-0002: Bounded Contexts Separation and Shared Kernel Strategy](#adr-0002-bounded-contexts-separation-and-shared-kernel-strategy)
- [ADR-0003: Aggregate Root Encapsulation and Invariant Enforcement](#adr-0003-aggregate-root-encapsulation-and-invariant-enforcement)
- [ADR-0004: Monetary Representation Using BigDecimal and Currency](#adr-0004-monetary-representation-using-bigdecimal-and-currency)

---

## ADR-0001: Use Java Records for Domain Value Objects

### Status
Accepted

### Context
In Domain-Driven Design (DDD), Value Objects describe descriptive aspects of the domain with no conceptual identity. They must be immutable, comparable by structural equality (all attributes match), and validated upon instantiation. Traditionally in Java, creating value objects required significant boilerplate (`equals`, `hashCode`, `toString`, getters) or third-party annotations.

### Decision
We adopt **Java Records** (introduced in Java 14/16 and supported in Java 26) as the standard implementation for all domain Value Objects (`Money`, `Address`, `CustomerId`, `ProductId`). Compact constructors are used to enforce domain invariants and defensive normalization (`strip()`).

### Consequences
#### Positive
- Built-in immutability (shallow immutability of fields).
- Automatic generation of value-based `equals()`, `hashCode()`, and `toString()`.
- Clean validation in compact constructors without boilerplate field assignments.
- Enhanced readability and expressiveness in domain models.

#### Negative / Considerations
- Records do not support inheritance from other classes (only interface implementation).
- Mutable reference fields inside records require defensive handling if introduced.

---

## ADR-0002: Bounded Contexts Separation and Shared Kernel Strategy

### Status
Accepted

### Context
The application domain encompasses distinct subdomains: Customer Relationship Management (CRM) and Sales Order Management. We need a clean domain architecture that avoids monolithic coupling while allowing clean cross-context references.

### Decision
We organize the domain into distinct packages following tactical DDD bounded contexts:
1. **CRM Context (`com.acme.oop.crm`)**: Manages customer profiles and contact details.
2. **Sales Context (`com.acme.oop.sales`)**: Manages sales orders, products, and order item lines.
3. **Shared Kernel (`com.acme.oop.shared`)**: Contains shared domain types and value objects (`CustomerId`, `Money`, `Address`).

Cross-context references occur strictly through identifiers (`CustomerId`) rather than direct aggregate object references, preventing leaky aggregate boundaries.

### Consequences
#### Positive
- Strict domain separation, preventing tight coupling between CRM and Sales domains.
- Contexts can evolve independently with clear boundaries.
- Ubiquitous language is localized within each bounded context.

#### Negative / Considerations
- Requires shared coordination when modifying types in the `shared` kernel.

---

## ADR-0003: Aggregate Root Encapsulation and Invariant Enforcement

### Status
Accepted

### Context
Aggregates in Domain-Driven Design are clusters of domain objects that can be treated as a single unit for data changes. The Aggregate Root is the only member accessible from outside the aggregate boundary. Direct manipulation of internal entities or collections bypasses invariant checks and compromises domain integrity.

### Decision
1. **Defensive Collection Exposure:** `SalesOrder` protects its internal item list by exposing only an unmodifiable view via `Collections.unmodifiableList(items)`.
2. **Encapsulated State Mutation:** Items can only be added to a `SalesOrder` via `addItem(productId, quantity, unitPrice)`, which validates quantity, price, and currency compatibility before recalculating total order amounts.
3. **Identity-Based Equality:** Entities (`Customer`, `SalesOrder`) implement `equals()` and `hashCode()` strictly on their unique identifier (`id`), preserving entity semantics across property changes.

### Consequences
#### Positive
- Aggregates maintain continuous consistency and validity.
- Domain invariants are impossible to bypass from consumer code.
- Entity lifecycle and reconstitution behaviors are clear and tested.

#### Negative / Considerations
- Callers cannot mutate collections directly and must interact through designated aggregate root methods.

---

## ADR-0004: Monetary Representation Using BigDecimal and Currency

### Status
Accepted

### Context
Financial calculations in software are prone to precision loss and rounding errors when using primitive floating-point types (`double`, `float`). Additionally, monetary amounts are meaningless without their corresponding ISO 4217 currency.

### Decision
We represent all monetary amounts using an immutable `Money` Value Object backed by:
- `java.math.BigDecimal` for arbitrary-precision numeric amounts.
- `java.util.Currency` for ISO 4217 currency representation.

Invariants enforced:
1. `amount` must not be null and must be non-negative ($\ge 0$).
2. `currency` must not be null.
3. `amount.scale()` must not exceed `currency.getDefaultFractionDigits()`.
4. Arithmetic additions require matching currencies.

### Consequences
#### Positive
- Prevents floating-point rounding errors and invalid monetary representations.
- Prevents accidental addition or aggregation of different currencies.
- Strict decimal scale checks guarantee financial formatting consistency.

#### Negative / Considerations
- Requires explicit instantiation and scale management compared to primitive types.
