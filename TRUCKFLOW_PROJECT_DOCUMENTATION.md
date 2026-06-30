# TruckFlow Manager — Documentazione completa del dominio

Questa è la pagina di ingresso della documentazione del dominio di TruckFlow Manager.

La documentazione ufficiale aggiornata si trova nella cartella [`docs`](docs/README.md).

## Stato attuale del dominio

Il progetto è attualmente concentrato sul **domain layer puro**.

La versione attuale rappresenta la **TruckFlow Domain Foundation v1.0**: la fondazione del dominio puro è definita, la roadmap della Domain Review Finale è approvata e il progetto è pronto per una review concreta dominio per dominio.

I package principali sono:

- `domain.users`;
- `domain.qualifications`;
- `domain.operational`;
- `domain.vehicles`;
- `domain.cargo`;
- `domain.locations`;
- `domain.triptemplates`;
- `domain.shipments`;
- `domain.documents`;
- `domain.compliance`.

Il dominio è stato costruito seguendo una regola precisa: modellare prima i concetti reali dell'azienda, senza introdurre database, controller, JPA, REST API, JWT, microservizi, tracking, disponibilità, pianificazione operativa, audit, workflow o controlli concreti di compliance.

## Documenti principali

- [`docs/README.md`](docs/README.md) — indice della documentazione del dominio.
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
- [`docs/11-domain-documents.md`](docs/11-domain-documents.md) — documento aziendale come concetto puro.
- [`docs/12-domain-compliance.md`](docs/12-domain-compliance.md) — requisiti astratti di conformità.
- [`docs/13-domain-rules.md`](docs/13-domain-rules.md) — regole ufficiali della TruckFlow Domain Foundation v1.0.

## Regole fondamentali della Domain Foundation

Le regole principali sono:

- ogni dominio mantiene confini chiari;
- un dominio non importa aggregate root completi di altri domini;
- i riferimenti tra domini avvengono tramite ID, value object stabili o concetti astratti;
- value object simili non vengono unificati se hanno significato diverso;
- le eccezioni custom sono introdotte gradualmente;
- `ComplianceViolationException` non viene introdotta ora perché le violazioni concrete non appartengono ancora al dominio puro;
- application layer e infrastructure rimangono separati dal dominio.

## Eccezioni di dominio

Sono state introdotte le eccezioni base condivise:

- `DomainException`;
- `DomainValidationException`;
- `InvariantViolationException`.

Sono state inoltre definite eccezioni specifiche per i domini principali, come `InvalidUserException`, `InvalidShipmentException`, `InvalidDocumentException` e `InvalidComplianceRequirementException`.

Queste eccezioni non obbligano a modificare immediatamente tutte le classi esistenti. Il refactoring dalle eccezioni standard Java alle eccezioni custom deve avvenire gradualmente, dominio per dominio, aggiornando i test.

## Prossimi step consigliati

La roadmap consigliata è:

1. review concreta dominio per dominio;
2. controllo aggregate root, value object, invarianti, entità e nomenclatura;
3. introduzione graduale delle eccezioni custom nei punti più importanti;
4. aggiornamento costante di [`docs/13-domain-rules.md`](docs/13-domain-rules.md);
5. pulizia finale del dominio puro;
6. inizio del livello application.
