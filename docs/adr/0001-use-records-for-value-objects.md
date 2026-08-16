# ADR-0001: Use Java Records for Domain Value Objects

## Status
Accepted

## Context
In Domain-Driven Design (DDD), Value Objects describe descriptive aspects of the domain with no conceptual identity. They must be immutable, comparable by structural equality (all attributes match), and validated upon instantiation. Traditionally in Java, creating value objects required significant boilerplate (`equals`, `hashCode`, `toString`, getters) or third-party annotations.

## Decision
We adopt **Java Records** (introduced in Java 14/16 and supported in Java 26) as the standard implementation for all domain Value Objects (`Money`, `Address`, `CustomerId`, `ProductId`). Compact constructors are used to enforce domain invariants and defensive normalization (`strip()`).

## Consequences
### Positive
- Built-in immutability (shallow immutability of fields).
- Automatic generation of value-based `equals()`, `hashCode()`, and `toString()`.
- Clean validation in compact constructors without boilerplate field assignments.
- Enhanced readability and expressiveness in domain models.

### Negative / Considerations
- Records do not support inheritance from other classes (only interface implementation).
- Mutable reference fields inside records require defensive handling if introduced.
