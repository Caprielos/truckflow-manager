# 3. Domain, Application and Infrastructure

## Domain contexts

The current domain foundation includes:

- `users`;
- `qualifications`;
- `operational`;
- `vehicles`;
- `cargo`;
- `locations`;
- `triptemplates`;
- `shipments`;
- `documents`;
- `compliance`;
- shared domain exceptions.

These contexts represent business concepts independently from persistence and delivery concerns.

## Application capabilities

The Application Layer currently covers use cases for:

- Locations;
- Cargo;
- Shipments;
- Documents;
- Vehicles;
- Operational Roles;
- Compliance requirements.

It includes command/result objects, inbound ports, outbound repository ports and application services.

## Infrastructure capabilities

The Infrastructure Layer currently includes:

- `infrastructure.memory` repositories for test and local development;
- infrastructure foundation markers and exceptions;
- Spring non-web wiring under `infrastructure.config.spring`;
- persistence mapping blueprint support;
- file-backed repository prototype and expansion for safe catalog-like contexts;
- infrastructure technical tests.

## File-backed repository scope

At the end of Punto 7, the validated file-backed repository scope is intentionally limited to:

- Locations;
- Cargo;
- Documents;
- Compliance.

Shipments, Vehicles and Operational Roles are not yet persisted using full real repository implementations because their mapping is more complex and should be handled in a dedicated future step.
