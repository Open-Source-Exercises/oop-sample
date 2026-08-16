# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.1.0-SNAPSHOT] - Unreleased

### Added
- Architecture Decision Records (`docs/adr/`) documenting Records for VOs, Bounded Contexts, Aggregate Root Encapsulation, and Money modeling.
- Requirements Traceability Matrix in `docs/user-stories.md` mapping US01–US04 to domain aggregates and unit tests.
- Package-level documentation (`package-info.java`) for all bounded contexts and shared kernel.
- Explicit `maven-javadoc-plugin` and `exec-maven-plugin` configurations in `pom.xml`.
- Project contribution guide (`CONTRIBUTING.md`).
- Visual ASCII architecture diagram and project structure map in `README.md`.

### Changed
- Resolved Javadoc compiler warnings in `Main.java`.
- Updated repository URLs in `README.md` to reference `main` branch and current upstream repository.

---

## [1.0.0] - 2026-08-16

### Added
- Initial implementation of DDD Customer Relationship Management (CRM) and Sales Bounded Contexts.
- `Customer` Aggregate Root in CRM context.
- `SalesOrder` Aggregate Root and `SalesOrderItem` entity in Sales context.
- Immutable Value Objects (`Money`, `Address`, `CustomerId`, `ProductId`) using Java 26 Records.
- Comprehensive JUnit 5 and AssertJ test suite covering 89 test scenarios.
- Interactive `Main.java` demonstration script showcasing domain validation and invariants.
