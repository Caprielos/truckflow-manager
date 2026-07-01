# TruckFlow Digital Domain Documentation

This folder contains the HTML + CSS version of the official TruckFlow domain documentation.

## Purpose

The page is a faithful digital mirror of the Markdown documentation files.

The left sidebar contains the documentation index. Selecting an item shows the corresponding document on the right.

## Tooltip modes

The tooltip behavior is controlled only by the class on the `<body>` tag in `index.html`.

```html
<body class="tooltip-enabled">
```

Shows Italian explanations through hover tooltips.

```html
<body class="tooltip-disabled">
```

Shows Italian explanations directly inline.

The HTML markup of technical terms does not need to change. Only the global CSS class changes the behavior.

## Source documents included

This digital documentation mirrors:

- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`
- `docs/README.md`
- `docs/01-project-overview.md`
- `docs/02-domain-users.md`
- `docs/03-domain-qualifications.md`
- `docs/04-domain-operational.md`
- `docs/05-domain-vehicles.md`
- `docs/06-architecture-decisions.md`
- `docs/07-domain-cargo.md`
- `docs/08-domain-locations.md`
- `docs/09-domain-triptemplates.md`
- `docs/10-domain-shipments.md`
- `docs/11-domain-documents.md`
- `docs/12-domain-compliance.md`
- `docs/13-domain-rules.md`
- `docs/14-domain-review-patches.md`
- `docs/15-domain-test-suite-review.md`
- `docs/16-application-layer-blueprint.md`
- `docs/17-application-foundation.md`
- `docs/18-application-repository-ports.md`
- `docs/19-application-in-memory-repositories.md`
- `docs/20-application-first-use-cases.md`
- `docs/21-application-use-case-hardening.md`
- `docs/22-application-use-case-expansion.md`
- `docs/23-application-use-case-expansion-review.md`
- `docs/24-application-use-cases-expansion-vehicles.md`
- `docs/25-application-use-cases-expansion-operational-roles.md`
- `docs/26-application-operational-use-case-hardening.md`
- `command_basic.md`


## Application layer blueprint

The digital documentation now also mirrors the Punto 6A application layer blueprint. This step explains the intended structure for command, result, port in, port out, use cases, repository ports, in-memory repositories and application tests before introducing REST APIs or databases.

## Aggiornamento Punto 6B

La documentazione digitale include ora anche il riferimento al Punto 6B — Application Foundation, che introduce i primi package e contratti dell'application layer senza modificare la documentazione Markdown esistente.

Documento di riferimento: `docs/17-application-foundation.md`.


## Aggiornamento Punto 6C

La documentazione digitale include ora anche il Punto 6C — Application Repository Ports. Questo step aggiunge le prime porte repository specifiche per Locations, Cargo e Shipments, mantenendo l’application layer indipendente da database, Spring, JPA e infrastructure concreta.

Documento di riferimento: `docs/18-application-repository-ports.md`.


## Aggiornamento Punto 6D

La documentazione digitale include ora anche il Punto 6D — In-Memory Repositories. Questo step aggiunge le prime implementazioni concrete e leggere delle repository port per Locations, Cargo e Shipments, senza introdurre database, JPA, Spring Data o persistenza definitiva.

Documento di riferimento: `docs/19-application-in-memory-repositories.md`.


## Aggiornamento Punto 6E

La documentazione digitale include ora anche il Punto 6E — First Use Cases. Questo step aggiunge i primi command, result, port in e application service per Locations, Cargo e Shipments, più il primo flusso applicativo completo testato con repository in memory.

Documento di riferimento: `docs/20-application-first-use-cases.md`.

## Aggiornamento Punto 6F

La documentazione digitale include ora anche il Punto 6F — Application Use Case Review & Hardening.

Questo aggiornamento documenta `CancelShipmentUseCase`, la protezione copy-on-write dei service di mutazione shipment, i test di hardening dei primi use case e la distinzione tra errori applicativi ed errori di dominio. La documentazione HTML resta un mirror navigabile della documentazione Markdown e non sostituisce i file ufficiali nella cartella `docs`.

Documento di riferimento: `docs/21-application-use-case-hardening.md`.

## Aggiornamento Punto 6G

La documentazione digitale include ora anche il Punto 6G — Application Use Cases Expansion.

Questo aggiornamento documenta la prima espansione controllata dell'application layer verso `documents`: command, result, port in, port out, service applicativi, `DocumentRepository`, `InMemoryDocumentRepository` e test per registrare, trovare, attivare e archiviare documenti logici aziendali.

La fase resta coerente con i vincoli architetturali già decisi: niente REST API, controller Spring, database, JPA, file upload, file storage, workflow documentale, audit trail o compliance check concreti.

Documento di riferimento: `docs/22-application-use-case-expansion.md`.

## Aggiornamento Punto 6H

La documentazione digitale include ora anche il Punto 6H — Application Use Case Expansion Review & Documentation Alignment.

Questo aggiornamento documenta la review tecnica successiva all'espansione Documents: contratti `UseCase`, service allineati alle port in, result applicativi null-safe, repository in memory con validazione uniforme, copy-on-write documentale e allineamento della documentazione Markdown + HTML + CSS.

La fase resta una review: non introduce REST API, controller Spring, database, JPA, security, tracking, planning, dashboard, file upload, workflow documentali o compliance check concreti.

Documento di riferimento: `docs/23-application-use-case-expansion-review.md`.


## Aggiornamento Punto 6I

La documentazione digitale include ora anche il Punto 6I — Application Use Cases Expansion II: Vehicles.

Questo aggiornamento documenta l'espansione controllata dell'application layer verso `vehicles`: command, result, port in, port out, service applicativi, `VehicleUnitRepository`, `VehicleCombinationRepository`, repository in memory e test per registrare/trovare unità veicolo, cambiare stato alle `VehicleUnit`, registrare combinazioni veicolo e trovarle per ID.

La fase resta coerente con i vincoli architetturali già decisi: niente REST API, controller Spring, database, JPA, security, tracking, planning, dispatching, manutenzione, disponibilità mezzi, dashboard o workflow enterprise.

Documento di riferimento: `docs/24-application-use-cases-expansion-vehicles.md`.

## Aggiornamento Punto 6J

La documentazione digitale include ora anche il Punto 6J — Application Use Cases Expansion III: Operational Roles.

Questo aggiornamento documenta l'espansione controllata dell'application layer verso `operational`: command, result, port in, port out, service applicativi, repository port Operational, repository in memory e test per registrare/trovare Driver, Mechanic, WarehouseOperator, Dispatcher e Manager e cambiarne lo stato applicativo.

La fase resta coerente con i vincoli architetturali già decisi: niente REST API, controller Spring, database, JPA, security, tracking, planning, dispatching reale, turni, disponibilità persone, payroll, dashboard o workflow enterprise.

Documento di riferimento: `docs/25-application-use-cases-expansion-operational-roles.md`.

## Aggiornamento Punto 6K

La documentazione digitale include ora anche il Punto 6K — Application Operational Use Case Review & Hardening.

Questo aggiornamento documenta la review dei use case Operational Roles: copertura completa dei service di stato, test copy-on-write sulle attivazioni fallite, controllo di command nulli e dependency repository nulle, più allineamento della documentazione Markdown + HTML + CSS.

La fase resta una review: non introduce REST API, controller Spring, database, JPA, security, tracking, planning, dispatching reale, turni, payroll, dashboard o nuovi use case business.

Documento di riferimento: `docs/26-application-operational-use-case-hardening.md`.
