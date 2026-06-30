# TruckFlow Manager — Documentazione completa del dominio

Questa è la pagina di ingresso della documentazione del dominio di TruckFlow Manager.

La documentazione ufficiale aggiornata si trova nella cartella [`docs`](docs/README.md).

## Stato attuale del dominio

Il progetto è attualmente concentrato sul **domain layer puro**. I package principali sono:

- `domain.users`
- `domain.qualifications`
- `domain.operational`
- `domain.vehicles`
- `domain.cargo`
- `domain.locations`
- `domain.triptemplates`
- `domain.shipments`

Il dominio è stato costruito seguendo una regola precisa: modellare prima i concetti reali dell'azienda, senza introdurre database, controller, JPA, REST API, JWT, microservizi, tracking, disponibilità o pianificazione operativa.

## Documenti principali

- [`docs/01-project-overview.md`](docs/01-project-overview.md) — visione generale del progetto.
- [`docs/02-domain-users.md`](docs/02-domain-users.md) — account applicativi e autorizzazioni.
- [`docs/03-domain-qualifications.md`](docs/03-domain-qualifications.md) — catalogo qualificazioni.
- [`docs/04-domain-operational.md`](docs/04-domain-operational.md) — figure operative aziendali.
- [`docs/05-domain-vehicles.md`](docs/05-domain-vehicles.md) — veicoli, allestimenti, combinazioni e schede tecniche.
- [`docs/06-architecture-decisions.md`](docs/06-architecture-decisions.md) — decisioni architetturali.
- [`docs/07-domain-cargo.md`](docs/07-domain-cargo.md) — merce e requisiti cargo.
- [`docs/08-domain-locations.md`](docs/08-domain-locations.md) — luoghi logistici.
- [`docs/09-domain-triptemplates.md`](docs/09-domain-triptemplates.md) — percorsi tipo e missioni tecniche astratte.
- [`docs/10-domain-shipments.md`](docs/10-domain-shipments.md) — richieste di spedizione.

## Nota sul checkup

Questa versione mantiene `domain.shipments` organizzato in sottopackage tematici:

- `core`
- `items`
- `legs`
- `requirements`
- `metrics`
- `properties`
- `notes`
- `references`

La divisione in sottopackage non crea nuovi aggregate. `Shipment` rimane l'unico aggregate root del dominio shipments.

