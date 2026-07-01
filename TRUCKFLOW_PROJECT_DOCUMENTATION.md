# TruckFlow Manager — Documentazione completa del dominio

Questa è la pagina di ingresso della documentazione del dominio di TruckFlow Manager.

La documentazione ufficiale aggiornata si trova nella cartella [`docs`](docs/README.md).


## Documentazione digitale HTML + CSS

Oltre alla documentazione Markdown nella cartella [`docs`](docs/README.md), il progetto ora include una prima documentazione digitale nella cartella [`digitalDocs`](digitalDocs/index.html).

Questa documentazione è pensata per essere full English e coerente con il codice: i nomi di classi, entità, value object, variabili e concetti architetturali rimangono in inglese. La spiegazione italiana è disponibile tramite tooltip CSS oppure può essere mostrata direttamente modificando solo la classe globale del tag `<body>`.

Il comportamento è controllato da due flag CSS globali:

- `tooltip-enabled` — mostra la traduzione italiana al passaggio del mouse;
- `tooltip-disabled` — mostra la traduzione italiana direttamente nella pagina.

Il markup HTML resta identico in entrambi i casi. Cambia solo il CSS applicato dal flag globale.

## Stato attuale del dominio

Il progetto è attualmente concentrato sul **domain layer puro**.

La versione attuale rappresenta la **TruckFlow Domain Foundation v1.0** rafforzata dalla prima review correttiva del dominio puro. La fondazione è definita, le regole sono documentate e sono stati applicati interventi mirati su invarianti, eccezioni, codici aziendali, test e pulizia del repository.

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
- [`docs/14-domain-review-patches.md`](docs/14-domain-review-patches.md) — riepilogo degli interventi correttivi della prima review concreta del dominio puro.

## Regole fondamentali della Domain Foundation

Le regole principali sono:

- ogni dominio mantiene confini chiari;
- un dominio non importa aggregate root completi di altri domini;
- i riferimenti tra domini avvengono tramite ID, value object stabili o concetti astratti;
- value object simili non vengono unificati se hanno significato diverso;
- le eccezioni custom sono introdotte gradualmente;
- `ComplianceViolationException` non viene introdotta ora perché le violazioni concrete non appartengono ancora al dominio puro;
- application layer e infrastructure rimangono separati dal dominio;
- file locali dell'IDE, artefatti Maven, file macOS generati e file temporanei come `.patch` e `.sh` restano fuori dal versionamento.

## Eccezioni di dominio

Sono state introdotte le eccezioni base condivise:

- `DomainException`;
- `DomainValidationException`;
- `InvariantViolationException`.

Sono state inoltre definite eccezioni specifiche per i domini principali, come `InvalidUserException`, `InvalidShipmentException`, `InvalidDocumentException` e `InvalidComplianceRequirementException`.

Queste eccezioni non obbligano a modificare immediatamente tutte le classi esistenti. Il refactoring dalle eccezioni standard Java alle eccezioni custom deve avvenire gradualmente, dominio per dominio, aggiornando i test.

## Review correttiva completata

La prima review correttiva del dominio puro ha consolidato sette aspetti principali:

1. validazione completa prima delle mutazioni di stato degli aggregate;
2. uso delle eccezioni custom nei domini semplici;
3. uso delle eccezioni custom nei domini complessi;
4. obbligatorietà di `OperationalCode` per le figure operative;
5. test del catalogo qualificazioni basati su comportamento e coerenza, non su conteggi fragili;
6. pulizia della documentazione e delle regole sui file locali, IDE, build e artefatti temporanei.
7. modellazione di `LicensePlate` e `VehicleIdentificationNumber` come value object del dominio veicoli.

Questi interventi non aggiungono nuove funzionalità operative, ma rendono il dominio più sicuro, leggibile e pronto per essere consumato dal livello application.

## Prossimi step consigliati

La roadmap consigliata è:

1. verificare sul Mac `mvn spotless:apply` e `mvn clean test` dopo ogni intervento importante;
2. mantenere aggiornata la documentazione `docs/13-domain-rules.md` e `docs/14-domain-review-patches.md` quando cambiano le regole di dominio;
3. iniziare il livello application con use case piccoli e chiari;
4. introdurre porte in ingresso e porte repository in uscita;
5. aggiungere repository in-memory per test e scenari;
6. rimandare API REST, database e integrazioni esterne finché l'application layer non è stabile.
