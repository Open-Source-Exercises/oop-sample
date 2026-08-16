# Architecture Decision Records (ADRs)

This document consolidates all Architecture Decision Records (ADRs) capturing key architectural, structural, and design decisions made in the **OOP Sample** codebase.

---

## Index of Decisions

| ID                                                                               | Title                                                  |   Status   | Bounded Context             | Primary Artifacts                                                                                                                                                                                                                                                                                                                                                           |                                                    Related Stories                                                     |
|:---------------------------------------------------------------------------------|:-------------------------------------------------------|:----------:|:----------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------:|
| **[ADR-0001](#adr-0001-use-java-records-for-domain-value-objects)**              | Use Java Records for Domain Value Objects              | `Accepted` | Shared Kernel / All         | [`Money`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Money.java), [`Address`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Address.java), [`CustomerId`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/CustomerId.java), [`ProductId`](../src/main/java/com/acme/oop/sales/domain/model/valueobjects/ProductId.java) | [US01](user-stories.md#us01-register-a-new-customer), [US02](user-stories.md#us02-update-customer-contact-information) |
| **[ADR-0002](#adr-0002-bounded-contexts-separation-and-shared-kernel-strategy)** | Bounded Contexts Separation and Shared Kernel Strategy | `Accepted` | Architecture / CRM / Sales  | [`crm`](../src/main/java/com/acme/oop/crm), [`sales`](../src/main/java/com/acme/oop/sales), [`shared`](../src/main/java/com/acme/oop/shared)                                                                                                                                                                                                                                |                                                      All Stories                                                       |
| **[ADR-0003](#adr-0003-aggregate-root-encapsulation-and-invariant-enforcement)** | Aggregate Root Encapsulation and Invariant Enforcement | `Accepted` | CRM Context / Sales Context | [`Customer`](../src/main/java/com/acme/oop/crm/domain/model/aggregates/Customer.java), [`SalesOrder`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrder.java), [`SalesOrderItem`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItem.java)                                                                                     |        [US03](user-stories.md#us03-create-a-sales-order), [US04](user-stories.md#us04-add-item-to-sales-order)         |
| **[ADR-0004](#adr-0004-monetary-representation-using-bigdecimal-and-currency)**  | Monetary Representation Using BigDecimal and Currency  | `Accepted` | Shared Kernel               | [`Money`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Money.java), [`SalesOrderItem`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItem.java)                                                                                                                                                                                   |                                  [US04](user-stories.md#us04-add-item-to-sales-order)                                  |

---

## ADR-0001: Use Java Records for Domain Value Objects

| Attribute             | Details                                                                                                                                                                                                                                                                                                                                                                                                     |
|:----------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Status**            | `Accepted`                                                                                                                                                                                                                                                                                                                                                                                                  |
| **Scope**             | Shared Kernel (`com.acme.oop.shared`), Sales (`com.acme.oop.sales`)                                                                                                                                                                                                                                                                                                                                         |
| **Primary Artifacts** | [`Money`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Money.java), [`Address`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Address.java), [`CustomerId`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/CustomerId.java), [`ProductId`](../src/main/java/com/acme/oop/sales/domain/model/valueobjects/ProductId.java)                                 |
| **Test Suites**       | [`MoneyTest`](../src/test/java/com/acme/oop/shared/domain/model/valueobjects/MoneyTest.java), [`AddressTest`](../src/test/java/com/acme/oop/shared/domain/model/valueobjects/AddressTest.java), [`CustomerIdTest`](../src/test/java/com/acme/oop/shared/domain/model/valueobjects/CustomerIdTest.java), [`ProductIdTest`](../src/test/java/com/acme/oop/sales/domain/model/valueobjects/ProductIdTest.java) |
| **Related Stories**   | [US01: Register a New Customer](user-stories.md#us01-register-a-new-customer), [US02: Update Customer Contact Information](user-stories.md#us02-update-customer-contact-information)                                                                                                                                                                                                                        |
| **Related ADRs**      | [ADR-0004: Monetary Representation Using BigDecimal and Currency](#adr-0004-monetary-representation-using-bigdecimal-and-currency)                                                                                                                                                                                                                                                                          |

### Context & Problem Statement
In Domain-Driven Design (DDD), Value Objects describe descriptive aspects of the domain with no conceptual identity. They must be immutable, comparable by structural equality (all attributes match), and validated upon instantiation. Traditionally in Java, creating value objects required significant boilerplate (`equals`, `hashCode`, `toString`, getters) or third-party annotation processors (e.g., Project Lombok).

### Decision Drivers
- Need for native language support for immutability without external third-party dependencies.
- Elimination of boilerplate code for value-based equality and hashing.
- Standardized invariant validation at object instantiation time.

### Considered Options
1. **Java Records (Standard Java 14+)** *(Chosen)*
2. **Traditional Java Classes with Manual Boilerplate**
3. **Project Lombok (`@Value`, `@EqualsAndHashCode`)**

### Decision Outcome
We adopt **Java Records** as the standard implementation mechanism for all domain Value Objects (`Money`, `Address`, `CustomerId`, `ProductId`). Compact constructors are leveraged to enforce domain invariants, perform defensive normalization (`strip()`), and guarantee structural integrity.

```java
public record CustomerId(UUID value) {
    public CustomerId {
        Objects.requireNonNull(value, "CustomerId value cannot be null");
    }
}
```

> [!IMPORTANT]
> **Key Value Object Rules:**
> - Value Objects are immutable representations with no identity lifecycle.
> - Any state modification returns a new Value Object instance.
> - Compact constructors validate all arguments prior to instance creation.

### Consequences

| Positive (+)                                                                     | Negative / Considerations (-)                                        |
|:---------------------------------------------------------------------------------|:---------------------------------------------------------------------|
| Built-in shallow immutability of fields.                                         | Records cannot inherit from other classes (only interfaces).         |
| Automatic generation of value-based `equals()`, `hashCode()`, and `toString()`.  | Mutable referenced fields inside records require defensive handling. |
| Clean validation in compact constructors without field reassignment boilerplate. |                                                                      |
| Enhanced readability and expressiveness in domain models.                        |                                                                      |

[⬆ Back to Decision Matrix](#index-of-decisions)

---

## ADR-0002: Bounded Contexts Separation and Shared Kernel Strategy

| Attribute             | Details                                                                                                                                                                                                                                            |
|:----------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Status**            | `Accepted`                                                                                                                                                                                                                                         |
| **Scope**             | Cross-Context Domain Architecture                                                                                                                                                                                                                  |
| **Primary Artifacts** | [`com.acme.oop.crm`](../src/main/java/com/acme/oop/crm), [`com.acme.oop.sales`](../src/main/java/com/acme/oop/sales), [`com.acme.oop.shared`](../src/main/java/com/acme/oop/shared)                                                                |
| **Test Suites**       | [`CustomerTest`](../src/test/java/com/acme/oop/crm/domain/model/aggregates/CustomerTest.java), [`SalesOrderTest`](../src/test/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderTest.java)                                                 |
| **Related Stories**   | All User Stories ([US01](user-stories.md#us01-register-a-new-customer), [US02](user-stories.md#us02-update-customer-contact-information), [US03](user-stories.md#us03-create-a-sales-order), [US04](user-stories.md#us04-add-item-to-sales-order)) |

### Decision Drivers
- Decouple distinct business domains into manageable, independent subdomains.
- Avoid tight object-graph coupling across aggregates of different contexts.
- Provide a standardized Shared Kernel for cross-cutting value objects (`CustomerId`, `Money`, `Address`).

### Considered Options
1. **Bounded Contexts with a Shared Kernel via Identifiers** *(Chosen)*
2. **Monolithic Unified Domain Model (Single Package)**
3. **Fully Independent Microservices with Distributed Data Stores**

### Decision Outcome
We organize the domain into distinct packages following tactical DDD bounded contexts:

1. **CRM Context (`com.acme.oop.crm`)**: Manages customer profiles, identity, and contact details.
2. **Sales Context (`com.acme.oop.sales`)**: Manages sales orders, line items, and product references.
3. **Shared Kernel (`com.acme.oop.shared`)**: Contains shared domain types and value objects (`CustomerId`, `Money`, `Address`).

Cross-context references occur strictly through identifiers (`CustomerId`) rather than direct aggregate object references, preventing leaky aggregate boundaries.

> [!NOTE]
> `SalesOrder` references customer entities solely by `CustomerId`, ensuring that changes to `Customer` internal state or persistence do not ripple into sales aggregates.

### Consequences

| Positive (+)                                                                      | Negative / Considerations (-)                                                      |
|:----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| Strict domain separation preventing tight coupling between CRM and Sales domains. | Requires coordination across context maintainers when modifying types in `shared`. |
| Contexts can evolve independently with localized ubiquitous language.             | Cross-context data queries require explicit ID resolution.                         |
| Prevents aggregate graph bloat and circular dependencies.                         |                                                                                    |

[⬆ Back to Decision Matrix](#index-of-decisions)

---

## ADR-0003: Aggregate Root Encapsulation and Invariant Enforcement

| Attribute             | Details                                                                                                                                                                                                                                                                                                         |
|:----------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Status**            | `Accepted`                                                                                                                                                                                                                                                                                                      |
| **Scope**             | CRM Context (`com.acme.oop.crm`), Sales Context (`com.acme.oop.sales`)                                                                                                                                                                                                                                          |
| **Primary Artifacts** | [`Customer`](../src/main/java/com/acme/oop/crm/domain/model/aggregates/Customer.java), [`SalesOrder`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrder.java), [`SalesOrderItem`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItem.java)                         |
| **Test Suites**       | [`CustomerTest`](../src/test/java/com/acme/oop/crm/domain/model/aggregates/CustomerTest.java), [`SalesOrderTest`](../src/test/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderTest.java), [`SalesOrderItemTest`](../src/test/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItemTest.java) |
| **Related Stories**   | [US03: Create a Sales Order](user-stories.md#us03-create-a-sales-order), [US04: Add Item to Sales Order](user-stories.md#us04-add-item-to-sales-order)                                                                                                                                                          |
| **Related ADRs**      | [ADR-0002: Bounded Contexts Separation and Shared Kernel Strategy](#adr-0002-bounded-contexts-separation-and-shared-kernel-strategy), [ADR-0004: Monetary Representation Using BigDecimal and Currency](#adr-0004-monetary-representation-using-bigdecimal-and-currency)                                        |

### Context & Problem Statement
Aggregates in Domain-Driven Design are clusters of domain objects treated as a single unit for data state changes. The Aggregate Root is the sole external access point. Direct manipulation of internal entities or collections bypasses invariant checks and compromises domain integrity.

### Decision Drivers
- Ensure aggregate invariants remain valid at all times across all state changes.
- Prevent external callers from modifying internal item collections directly.
- Preserve entity semantics where equality is determined strictly by unique identity.

### Considered Options
1. **Encapsulated Aggregate Roots with Defensive Copies and Domain Methods** *(Chosen)*
2. **Anemic Domain Models with Public Getters/Setters and Exposed Collections**

### Decision Outcome
We implement strict aggregate encapsulation through the following patterns:

1. **Defensive Collection Exposure:** `SalesOrder` protects its internal item list by exposing only an unmodifiable view via `Collections.unmodifiableList(items)`.
2. **Encapsulated State Mutation:** Items can only be added to a `SalesOrder` via `addItem(productId, quantity, unitPrice)`, which validates quantity, price, and currency compatibility before recalculating total order amounts.
3. **Identity-Based Equality:** Entities (`Customer`, `SalesOrder`) implement `equals()` and `hashCode()` strictly on their unique identifier (`id`), preserving entity semantics across property mutations.

```java
public List<SalesOrderItem> getItems() {
    return Collections.unmodifiableList(items);
}
```

> [!IMPORTANT]
> **Enforced Invariants:**
> - External callers cannot mutate collections directly; all modifications must pass through aggregate methods.
> - Entity identity is immutable once assigned.

### Consequences

| Positive (+)                                                               | Negative / Considerations (-)                                                           |
|:---------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|
| Aggregates maintain continuous consistency and validity.                   | Callers cannot mutate collections directly and must use aggregate root methods.         |
| Domain invariants cannot be bypassed by consumer code.                     | Reconstituting aggregates from persistence requires explicit factory/constructor paths. |
| Entity lifecycle and reconstitution behaviors are clear and deterministic. |                                                                                         |

[⬆ Back to Decision Matrix](#index-of-decisions)

---

## ADR-0004: Monetary Representation Using BigDecimal and Currency

| Attribute             | Details                                                                                                                                                                                                                                                                                                        |
|:----------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Status**            | `Accepted`                                                                                                                                                                                                                                                                                                     |
| **Scope**             | Shared Kernel (`com.acme.oop.shared`)                                                                                                                                                                                                                                                                          |
| **Primary Artifacts** | [`Money`](../src/main/java/com/acme/oop/shared/domain/model/valueobjects/Money.java), [`SalesOrderItem`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItem.java), [`SalesOrder`](../src/main/java/com/acme/oop/sales/domain/model/aggregates/SalesOrder.java)                         |
| **Test Suites**       | [`MoneyTest`](../src/test/java/com/acme/oop/shared/domain/model/valueobjects/MoneyTest.java), [`SalesOrderItemTest`](../src/test/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderItemTest.java), [`SalesOrderTest`](../src/test/java/com/acme/oop/sales/domain/model/aggregates/SalesOrderTest.java) |
| **Related Stories**   | [US04: Add Item to Sales Order](user-stories.md#us04-add-item-to-sales-order)                                                                                                                                                                                                                                  |
| **Related ADRs**      | [ADR-0001: Use Java Records for Domain Value Objects](#adr-0001-use-java-records-for-domain-value-objects), [ADR-0003: Aggregate Root Encapsulation and Invariant Enforcement](#adr-0003-aggregate-root-encapsulation-and-invariant-enforcement)                                                               |

### Context & Problem Statement
Financial calculations in software are prone to precision loss and rounding errors when using primitive binary floating-point types (`double`, `float`). Additionally, monetary amounts are meaningless without their corresponding ISO 4217 currency.

### Decision Drivers
- Eliminate IEEE 754 floating-point rounding errors during financial calculations.
- Prevent invalid cross-currency operations (e.g., adding USD to EUR).
- Enforce strict scale alignment with ISO 4217 currency fractional digit standards.

### Considered Options
1. **Immutable `Money` Value Object (`BigDecimal` + `Currency`)** *(Chosen)*
2. **Primitive `double` / `float` with standalone currency codes**
3. **Integer / Long Cent Representation (e.g., amount in cents)**

### Decision Outcome
We represent all monetary amounts using an immutable `Money` Value Object backed by:
- `java.math.BigDecimal` for arbitrary-precision numeric amounts.
- `java.util.Currency` for ISO 4217 currency representation.

> [!IMPORTANT]
> **Enforced Invariants:**
> 1. `amount` must not be null and must be non-negative ($\ge 0$).
> 2. `currency` must not be null.
> 3. `amount.scale()` must not exceed `currency.getDefaultFractionDigits()`.
> 4. Arithmetic additions (`add()`) require matching currencies.

```java
public Money add(Money other) {
    Objects.requireNonNull(other, "Other money cannot be null");
    if (!this.currency.equals(other.currency())) {
        throw new IllegalArgumentException("Cannot add money with different currencies: "
                + this.currency + " and " + other.currency());
    }
    return new Money(this.amount.add(other.amount()), this.currency);
}
```

### Consequences

| Positive (+)                                                                  | Negative / Considerations (-)                                                     |
|:------------------------------------------------------------------------------|:----------------------------------------------------------------------------------|
| Prevents floating-point rounding errors and invalid monetary representations. | Requires explicit instantiation and scale management compared to primitive types. |
| Prevents accidental addition or aggregation of different currencies.          | Slight memory and CPU overhead compared to primitive values.                      |
| Strict decimal scale checks guarantee financial formatting consistency.       |                                                                                   |

[⬆ Back to Decision Matrix](#index-of-decisions)
