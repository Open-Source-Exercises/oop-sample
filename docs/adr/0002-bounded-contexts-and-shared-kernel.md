# ADR-0002: Bounded Contexts Separation and Shared Kernel Strategy

## Status
Accepted

## Context
The application domain encompasses distinct subdomains: Customer Relationship Management (CRM) and Sales Order Management. We need a clean domain architecture that avoids monolithic coupling while allowing clean cross-context references.

## Decision
We organize the domain into distinct packages following tactical DDD bounded contexts:
1. **CRM Context (`com.acme.oop.crm`)**: Manages customer profiles and contact details.
2. **Sales Context (`com.acme.oop.sales`)**: Manages sales orders, products, and order item lines.
3. **Shared Kernel (`com.acme.oop.shared`)**: Contains shared domain types and value objects (`CustomerId`, `Money`, `Address`).

Cross-context references occur strictly through identifiers (`CustomerId`) rather than direct aggregate object references, preventing leaky aggregate boundaries.

## Consequences
### Positive
- Strict domain separation preventing tight coupling between CRM and Sales domains.
- Contexts can evolve independently with clear boundaries.
- Ubiquitous language is localized within each bounded context.

### Negative / Considerations
- Requires shared coordination when modifying types in the `shared` kernel.
