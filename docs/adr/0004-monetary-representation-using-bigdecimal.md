# ADR-0004: Monetary Representation Using BigDecimal and Currency

## Status
Accepted

## Context
Financial calculations in software are prone to precision loss and rounding errors when using primitive floating-point types (`double`, `float`). Additionally, monetary amounts are meaningless without their corresponding ISO 4217 currency.

## Decision
We represent all monetary amounts using an immutable `Money` Value Object backed by:
- `java.math.BigDecimal` for arbitrary-precision numeric amounts.
- `java.util.Currency` for ISO 4217 currency representation.

Invariants enforced:
1. `amount` must not be null and must be non-negative ($\ge 0$).
2. `currency` must not be null.
3. `amount.scale()` must not exceed `currency.getDefaultFractionDigits()`.
4. Arithmetic additions require matching currencies.

## Consequences
### Positive
- Prevents floating-point rounding errors and invalid monetary representations.
- Prevents accidental addition or aggregation of different currencies.
- Strict decimal scale checks guarantee financial formatting consistency.

### Negative / Considerations
- Requires explicit instantiation and scale management compared to primitive types.
