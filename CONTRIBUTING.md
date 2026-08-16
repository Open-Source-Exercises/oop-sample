# Contributing Guidelines

Thank you for your interest in contributing to the **OOP Sample** project! This repository serves as an educational and architectural reference for Object-Oriented Programming (OOP) and Domain-Driven Design (DDD) in modern Java.

---

## 1. Code of Conduct & Standards

To maintain code quality and architectural integrity, all contributions must adhere to the following principles:

1. **Domain-Driven Design (DDD):**
   - Value Objects must be immutable Java Records placed in appropriate `domain.model.valueobjects` packages.
   - Aggregate Roots must guard their internal entities and maintain business invariants.
   - Cross-context references should occur through identifier value objects (e.g., `CustomerId`), not direct object references.

2. **Clean Code & Encapsulation:**
   - Prefer constructor validation with explicit `IllegalArgumentException` messages.
   - Prevent external mutation of internal collections using `Collections.unmodifiableList()`.
   - Ensure entities implement identity-based `equals()` and `hashCode()` using their unique identifier.

3. **Documentation:**
   - Every public class, record, and method must contain clear Javadoc comments.
   - New packages must include a `package-info.java` file describing context responsibilities.
   - Architectural changes must be documented via an Architecture Decision Record (ADR) in `docs/adr/`.

4. **Testing Standards:**
   - All business logic and invariant checks must be covered by JUnit 5 tests organized in `@Nested` classes.
   - Use AssertJ fluent assertions (`assertThat`, `assertThatThrownBy`).
   - Test display names should follow BDD conventions mirroring acceptance criteria.

---

## 2. Git Workflow & Branching

We follow a structured Git branching model:

* **`main`**: Production-ready, stable releases.
* **`develop`**: Integration branch for upcoming features.
* **`feature/<name>`**: Dedicated branches for specific features or refactorings.
* **`release/vX.Y.Z`**: Preparation branch for new releases.

### Commit Message Convention
Use [Conventional Commits](https://www.conventionalcommits.org/):
* `feat(context): add new domain capability`
* `fix(sales): correct currency calculation error`
* `docs(readme): update build documentation`
* `refactor(crm): simplify customer validation`
* `test(shared): add money scale edge cases`

---

## 3. Build & Verification Commands

Before opening a pull request, ensure that all tests, Javadocs, and builds pass locally:

```bash
# 1. Run unit test suite
mvn clean test

# 2. Verify Javadoc generation without warnings
mvn javadoc:javadoc

# 3. Verify interactive demo
mvn exec:java
```
