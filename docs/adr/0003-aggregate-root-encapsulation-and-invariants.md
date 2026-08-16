# ADR-0003: Aggregate Root Encapsulation and Invariant Enforcement

## Status
Accepted

## Context
Aggregates in Domain-Driven Design are clusters of domain objects that can be treated as a single unit for data changes. The Aggregate Root is the only member accessible from outside the aggregate boundary. Direct manipulation of internal entities or collections bypasses invariant checks and compromises domain integrity.

## Decision
1. **Defensive Collection Exposure:** `SalesOrder` protects its internal item list by exposing only an unmodifiable view via `Collections.unmodifiableList(items)`.
2. **Encapsulated State Mutation:** Items can only be added to a `SalesOrder` via `addItem(productId, quantity, unitPrice)`, which validates quantity, price, and currency compatibility before recalculating total order amounts.
3. **Identity-Based Equality:** Entities (`Customer`, `SalesOrder`) implement `equals()` and `hashCode()` strictly on their unique identifier (`id`), preserving entity semantics across property changes.

## Consequences
### Positive
- Aggregates maintain continuous consistency and validity.
- Domain invariants are impossible to bypass from consumer code.
- Entity lifecycle and reconstitution behaviors are clear and tested.

### Negative / Considerations
- Callers cannot mutate collections directly and must interact through designated aggregate root methods.
