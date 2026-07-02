# 2. Architecture

## Architectural style

TruckFlow Manager follows a layered architecture inspired by Clean Architecture and Hexagonal Architecture.

The guiding rule is that dependencies must point inward:

```text
Infrastructure -> Application -> Domain
```

The Domain Layer does not depend on Application or Infrastructure. The Application Layer depends on Domain contracts and models, but not on Spring, REST, JPA or persistence technologies. The Infrastructure Layer implements technical details and can depend on Application outbound ports and Domain models.

## Domain Layer

The Domain Layer contains business concepts, invariants, value objects, domain exceptions and aggregate behavior.

It is framework-free.

## Application Layer

The Application Layer contains commands, results, inbound ports, outbound ports and use case services.

It orchestrates operations but delegates business rules to the domain.

## Infrastructure Layer

The Infrastructure Layer contains technical adapters, configuration, mapping support, file-backed repositories, in-memory repositories, technical exceptions and Spring wiring.

Spring is used only as a technical wiring mechanism. The runtime remains non-web.

## Future API Layer

The future API Layer will introduce REST controllers, HTTP DTOs, request/response mapping and delivery-specific error handling.

It must call application use cases rather than bypassing them.

## Boundary rules

- Domain must not import Spring, infrastructure, persistence or application packages.
- Application must not import infrastructure, Spring Web, JPA or database packages.
- Infrastructure may implement application outbound ports.
- REST delivery belongs to Punto 8, not Punto 7.
