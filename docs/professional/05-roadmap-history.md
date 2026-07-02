# 5. Roadmap History

## Punto 1 → Punto 5: Domain Foundation

The first roadmap cycle focused on the pure domain model.

Completed activities included:

- final domain review;
- concrete review domain by domain;
- custom domain exceptions;
- domain rules documentation;
- final cleanup of the pure domain.

## Punto 6A → Punto 6M: Application Layer

The second major cycle introduced the Application Layer.

Key steps:

- 6A Application Layer Blueprint;
- 6B Application Foundation;
- 6C Repository Ports;
- 6D In-Memory Repositories;
- 6E First Use Cases;
- 6F Use Case Hardening;
- 6G Documents use case expansion;
- 6H Review and documentation alignment;
- 6I Vehicles use cases;
- 6J Operational Roles use cases;
- 6K Operational hardening;
- 6L Compliance base use cases;
- 6M Application Layer Final Review and Freeze.

## Punto 7A → Punto 7H: Infrastructure Layer

The third cycle introduced Infrastructure in a controlled way.

Key steps:

- 7A Infrastructure Blueprint;
- 7B Infrastructure Foundation;
- 7C Spring Wiring Foundation;
- 7D Persistence Mapping Blueprint;
- 7E Real Repository Prototype;
- 7F Repository Expansion;
- 7G Infrastructure Testing;
- 7H Infrastructure Review and Freeze.

## Punto 8A: API Layer Blueprint

The fourth cycle has started with a controlled blueprint step.

Completed 8A activities include:

- official API Layer Blueprint document;
- API versioning strategy through `/api/v1`;
- first REST context selection: Locations;
- future endpoint direction: `POST /api/v1/locations` and `GET /api/v1/locations/{id}`;
- architecture rules for API → Application → Domain;
- future-proof `ApiLayerArchitectureTest`;
- documentation update for the new API roadmap.

## Current conclusion

Punto 7 is closed and Punto 8A is formalized. The project is prepared for **Punto 8B — API Layer Foundation**, where the API package structure can be introduced in a controlled way.
