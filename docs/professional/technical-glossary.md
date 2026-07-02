# Technical Glossary

This glossary provides precise technical definitions for the main concepts used in TruckFlow Manager.

## Adapter

**Category:** Architecture / Infrastructure Layer

**Definition:** A concrete implementation that connects an abstract port to a technical mechanism.

**Usage in TruckFlow Manager:** `InMemoryLocationRepository` and `FileLocationRepository` are adapters implementing application repository ports.

**Related terms:** Port, Repository, Infrastructure Layer.

## Aggregate

**Category:** Domain-Driven Design

**Definition:** A consistency boundary that groups domain behavior and protects internal invariants.

**Usage in TruckFlow Manager:** Concepts such as shipments, vehicle units and documents are modeled as important domain objects with rules.

**Related terms:** Entity, Invariant, Domain Layer.

## Application Layer

**Category:** Architecture

**Definition:** The layer responsible for orchestrating use cases and coordinating domain operations through ports.

**Usage in TruckFlow Manager:** Contains commands, results, inbound ports, outbound ports and application services.

**Related terms:** Use Case, Port In, Port Out, Command, Result.

## Bean

**Category:** Spring

**Definition:** An object managed by the Spring container.

**Usage in TruckFlow Manager:** Spring wiring configuration creates beans for application services and repository adapters.

**Related terms:** Spring Wiring, Dependency Injection.

## Blueprint

**Category:** Roadmap / Architecture

**Definition:** A design step that defines structure, rules and direction before full implementation.

**Usage in TruckFlow Manager:** Punto 7D defines a persistence mapping blueprint without introducing JPA or database schema.

**Related terms:** Freeze, Hardening.

## Command

**Category:** Application Layer

**Definition:** An input object carrying the data required to execute a use case.

**Usage in TruckFlow Manager:** `RegisterLocationCommand` represents the data needed to register a location.

**Related terms:** Use Case, Result.

## Controller

**Category:** API Layer

**Definition:** A delivery-layer class that handles HTTP requests and delegates to application use cases.

**Usage in TruckFlow Manager:** Controllers are not yet introduced and belong to the future Punto 8.

**Related terms:** REST, DTO, API Layer.

## Dependency Injection

**Category:** Architecture / Spring

**Definition:** A technique where dependencies are provided to objects instead of being created internally.

**Usage in TruckFlow Manager:** Spring configuration wires use case services with repository port implementations.

**Related terms:** Bean, Spring Wiring.

## Domain Layer

**Category:** Architecture

**Definition:** The layer containing business rules, domain concepts, invariants and domain exceptions.

**Usage in TruckFlow Manager:** Contains contexts such as shipments, vehicles, cargo, documents and compliance.

**Related terms:** Aggregate, Value Object, Invariant.

## DTO

**Category:** API Layer

**Definition:** Data Transfer Object used to move data across boundaries, often in HTTP APIs.

**Usage in TruckFlow Manager:** DTOs are not yet introduced and belong to the future API Layer.

**Related terms:** Controller, REST, API Layer.

## Entity

**Category:** Domain-Driven Design

**Definition:** A domain object with identity.

**Usage in TruckFlow Manager:** Domain concepts with stable identifiers are modeled as entities or aggregate-like objects.

**Related terms:** Aggregate, Value Object.

## File-Backed Repository

**Category:** Infrastructure Layer

**Definition:** Repository implementation that persists records to local files.

**Usage in TruckFlow Manager:** File-backed repositories currently exist for Locations, Cargo, Documents and Compliance.

**Related terms:** Repository, Persistence Record, Persistence Mapper.

## Freeze

**Category:** Roadmap / Quality

**Definition:** A final review step that stabilizes a cycle and documents what is complete and what remains excluded.

**Usage in TruckFlow Manager:** Punto 6M and Punto 7H are freeze steps.

**Related terms:** Hardening, Blueprint.

## Hardening

**Category:** Quality

**Definition:** A strengthening phase focused on validation, negative tests, consistency and boundary protection.

**Usage in TruckFlow Manager:** Application use cases and operational role use cases were hardened before freeze.

**Related terms:** Freeze, Test Suite.

## Hexagonal Architecture

**Category:** Architecture

**Definition:** Architectural style that separates core logic from external systems using ports and adapters.

**Usage in TruckFlow Manager:** Application outbound ports are implemented by infrastructure adapters.

**Related terms:** Clean Architecture, Ports & Adapters.

## In-Memory Repository

**Category:** Infrastructure Layer

**Definition:** Repository implementation storing data in memory only.

**Usage in TruckFlow Manager:** In-memory repositories remain valid for tests and local development.

**Related terms:** Repository, Adapter.

## Infrastructure Layer

**Category:** Architecture

**Definition:** Layer containing technical implementations, adapters, configuration, mapping and repository implementations.

**Usage in TruckFlow Manager:** Includes memory repositories, file-backed repositories, mapping support and Spring wiring.

**Related terms:** Adapter, Repository, Spring Wiring.

## Invariant

**Category:** Domain-Driven Design

**Definition:** A rule that must always remain true for a domain object to be valid.

**Usage in TruckFlow Manager:** Domain objects validate their own rules and reject invalid states.

**Related terms:** Domain Layer, Aggregate.

## JPA

**Category:** Persistence Technology

**Definition:** Java Persistence API, a standard for object-relational mapping.

**Usage in TruckFlow Manager:** JPA is intentionally not introduced yet.

**Related terms:** Spring Data, Database, Entity.

## Persistence Mapper

**Category:** Infrastructure Layer

**Definition:** Component that translates between domain objects and persistence records.

**Usage in TruckFlow Manager:** File-backed repositories use mappers to keep domain objects separate from storage representation.

**Related terms:** Persistence Record, File-Backed Repository.

## Persistence Record

**Category:** Infrastructure Layer

**Definition:** Technical data representation used for storage.

**Usage in TruckFlow Manager:** File-backed repositories store records rather than exposing storage details to the domain.

**Related terms:** Persistence Mapper, Repository.

## Port In

**Category:** Application Layer

**Definition:** Inbound contract representing an action offered by the application.

**Usage in TruckFlow Manager:** `RegisterLocationUseCase` is a port in.

**Related terms:** Use Case, Application Layer.

## Port Out

**Category:** Application Layer

**Definition:** Outbound contract representing a dependency required by the application.

**Usage in TruckFlow Manager:** Repository interfaces are port out contracts.

**Related terms:** Adapter, Repository, Infrastructure Layer.

## Repository

**Category:** Application / Infrastructure

**Definition:** Abstraction used to store and retrieve domain objects.

**Usage in TruckFlow Manager:** Application defines repository ports; infrastructure provides implementations.

**Related terms:** Port Out, Adapter.

## REST

**Category:** API Layer

**Definition:** Architectural style for exposing resources over HTTP.

**Usage in TruckFlow Manager:** REST API is planned for Punto 8 and is not yet implemented.

**Related terms:** Controller, DTO, API Layer.

## Result

**Category:** Application Layer

**Definition:** Output object returned by a use case.

**Usage in TruckFlow Manager:** Use cases return result objects to avoid exposing domain objects directly in application responses.

**Related terms:** Command, Use Case.

## Security

**Category:** Cross-cutting / API Layer

**Definition:** Authentication, authorization and protection of application access.

**Usage in TruckFlow Manager:** HTTP security is intentionally not introduced yet.

**Related terms:** Controller, API Layer.

## Spring Data

**Category:** Persistence Technology

**Definition:** Spring project that simplifies repository implementations for persistence technologies.

**Usage in TruckFlow Manager:** Spring Data is intentionally not used yet.

**Related terms:** JPA, Repository.

## Spring Wiring

**Category:** Infrastructure / Spring

**Definition:** Technical configuration that creates and connects beans.

**Usage in TruckFlow Manager:** Spring wiring is non-web and lives in infrastructure configuration packages.

**Related terms:** Bean, Dependency Injection.

## Use Case

**Category:** Application Layer

**Definition:** Application action representing a user/system operation.

**Usage in TruckFlow Manager:** Examples include registering locations, cargo, documents, vehicles and compliance requirements.

**Related terms:** Command, Result, Port In.

## Value Object

**Category:** Domain-Driven Design

**Definition:** Immutable domain object identified by its value rather than an identity.

**Usage in TruckFlow Manager:** Domain-specific values are modeled as value objects to keep meaning explicit.

**Related terms:** Domain Layer, Entity.
