# TruckFlow Manager — Documentazione completa del progetto

Questa è la pagina di ingresso della documentazione ufficiale di TruckFlow Manager.

La documentazione ufficiale aggiornata si trova nella cartella [`docs`](docs/README.md).


## Documentazione digitale HTML + CSS

Oltre alla documentazione Markdown nella cartella [`docs`](docs/README.md), il progetto ora include una prima documentazione digitale nella cartella [`digitalDocs`](digitalDocs/index.html).

Questa documentazione è pensata per essere full English e coerente con il codice: i nomi di classi, entità, value object, variabili e concetti architetturali rimangono in inglese. La spiegazione italiana è disponibile tramite tooltip CSS oppure può essere mostrata direttamente modificando solo la classe globale del tag `<body>`.

Il comportamento è controllato da due flag CSS globali:

- `tooltip-enabled` — mostra la traduzione italiana al passaggio del mouse;
- `tooltip-disabled` — mostra la traduzione italiana direttamente nella pagina.

Il markup HTML resta identico in entrambi i casi. Cambia solo il CSS applicato dal flag globale.

## Stato attuale del dominio

Il progetto ha consolidato il **domain layer puro** e ha completato il **Punto 6D — In-Memory Repositories** e sta introducendo il **Punto 6E — First Use Cases**.

La versione attuale rappresenta la **TruckFlow Domain Foundation v1.0** rafforzata dalla prima review correttiva del dominio puro. La fondazione è definita, le regole sono documentate e sono stati applicati interventi mirati su invarianti, eccezioni, codici aziendali, test e pulizia del repository. Il passo attuale è implementare i primi use case applicativi per Locations, Cargo e Shipments, senza introdurre ancora REST API, database, JPA, Spring Data o persistenza definitiva.

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
- [`docs/15-domain-test-suite-review.md`](docs/15-domain-test-suite-review.md) — revisione finale della test suite del dominio puro, con cosa è stato aggiunto, cosa manca e perché.
- [`docs/16-application-layer-blueprint.md`](docs/16-application-layer-blueprint.md) — blueprint del Punto 6A, dedicato a struttura application, command, result, port, repository, use case e test applicativi.
- [`docs/17-application-foundation.md`](docs/17-application-foundation.md) — foundation del Punto 6B: package application, contratti base, eccezioni applicative e test architetturali.
- [`docs/18-application-repository-ports.md`](docs/18-application-repository-ports.md) — repository port del Punto 6C: `RepositoryPort`, `LocationRepository`, `CargoUnitRepository` e `ShipmentRepository`.
- [`docs/19-application-in-memory-repositories.md`](docs/19-application-in-memory-repositories.md) — repository in memory del Punto 6D: implementazioni leggere per Locations, Cargo e Shipments.
- [`docs/20-application-first-use-cases.md`](docs/20-application-first-use-cases.md) — primi use case del Punto 6E: command, result, port in, application service e primo flusso applicativo Locations + Cargo + Shipments.

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

La prima review correttiva del dominio puro ha consolidato otto aspetti principali:

1. validazione completa prima delle mutazioni di stato degli aggregate;
2. uso delle eccezioni custom nei domini semplici;
3. uso delle eccezioni custom nei domini complessi;
4. obbligatorietà di `OperationalCode` per le figure operative;
5. test del catalogo qualificazioni basati su comportamento e coerenza, non su conteggi fragili;
6. pulizia della documentazione e delle regole sui file locali, IDE, build e artefatti temporanei;
7. modellazione di `LicensePlate` e `VehicleIdentificationNumber` come value object del dominio veicoli;
8. rafforzamento di preferenze utente, giurisdizione compliance e scope operativi con `LanguageCode`, `UserTheme`, `CountryCode`, `JurisdictionRegion`, `ComplianceJurisdictionScope` e `OperationalScopeCode`.

Questi interventi non aggiungono nuove funzionalità operative, ma rendono il dominio più sicuro, leggibile e pronto per essere consumato dal livello application.

Dopo questi interventi è stata aggiunta anche una revisione finale della test suite del dominio puro. Questa revisione introduce test architetturali sui confini tra domini, test contrattuali sui value object principali, casi limite aggiuntivi su cargo e shipment e un documento dedicato che chiarisce cosa è coperto e cosa rimane fuori perché appartiene a moduli futuri.

È stato inoltre aggiunto il blueprint del Punto 6A. Questo step ha definito come organizzare use case, command, result, port in, port out, repository in memory, eccezioni applicative e test dell'application layer.

Dopo il blueprint è stata avviata la foundation del Punto 6B: sono stati creati i package applicativi principali, i contratti base `ApplicationCommand`, `ApplicationResult` e `UseCase`, le prime eccezioni applicative e i test architetturali che proteggono il nuovo livello.

Con il Punto 6C sono state introdotte le prime repository port specifiche: `RepositoryPort`, `LocationRepository`, `CargoUnitRepository` e `ShipmentRepository`. Questi contratti permettono ai futuri use case di salvare e recuperare aggregate tramite ID e codice senza conoscere implementazioni concrete.

Con il Punto 6D sono state introdotte le prime implementazioni in memory: `InMemoryLocationRepository`, `InMemoryCargoUnitRepository` e `InMemoryShipmentRepository`. Questi adapter sono utili per test e sviluppo locale, proteggono duplicati di codice e input nulli, ma non sostituiscono un database enterprise definitivo.

Con il Punto 6E sono stati introdotti i primi use case applicativi reali: registrazione e recupero di Locations e Cargo, creazione di una Shipment draft, aggiunta di item e leg, conferma e recupero della Shipment. Questo step dimostra il primo flusso applicativo completo senza introdurre ancora REST API, database o framework.



## Punto 6A — Application Layer Blueprint

Il Punto 6A ha progettato il livello applicativo prima di scrivere codice operativo. L'application layer dovrà orchestrare il dominio tramite casi d'uso, senza duplicare le regole di business e senza introdurre subito controller, database, JPA, Spring o API REST.

La struttura prevista include command, result, port in, port out, application service, eccezioni applicative e repository astratti. Il primo flusso consigliato parte da Locations + Cargo + Shipments, perché permette di creare un caso d'uso reale mantenendo separati dominio, applicazione e infrastruttura.

## Punto 6B — Application Foundation

Il Punto 6B introduce la prima base concreta dell'application layer. Sono stati aggiunti i package `application.command`, `application.result`, `application.port.in`, `application.port.out`, `application.usecase` e `application.exception`.

La foundation include i contratti `ApplicationCommand`, `ApplicationResult` e `UseCase`, più le eccezioni applicative `ApplicationException`, `UseCaseValidationException`, `ResourceNotFoundException` e `DuplicateResourceException`.

Questa fase non introduce ancora use case specifici, repository port specifici, repository in memory, API REST o database. Serve a rendere stabile la struttura prima di costruire Locations, Cargo e Shipments nel livello applicativo.

## Punto 6C — Repository Ports

Il Punto 6C aggiunge il primo gruppo di porte repository dell'application layer. Sono contratti astratti, non repository concreti.

Le prime porte sono dedicate a:

- Locations, tramite `LocationRepository`;
- Cargo, tramite `CargoUnitRepository`;
- Shipments, tramite `ShipmentRepository`.

Ogni porta permette salvataggio, ricerca per ID, ricerca per codice e verifica di esistenza per ID o codice. Questa scelta prepara i futuri use case senza introdurre database, JPA, Spring, file system o infrastructure concreta.

Il Punto 6C è documentato in [`docs/18-application-repository-ports.md`](docs/18-application-repository-ports.md).


## Punto 6D — In-Memory Repositories

Il Punto 6D aggiunge le prime implementazioni concrete delle repository port. Sono adapter tecnici leggeri, collocati in `infrastructure.memory`, e implementano i contratti definiti dall’application layer.

Le implementazioni introdotte sono:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`.

Queste repository permettono salvataggio, ricerca per ID, ricerca per codice e verifica di esistenza. Inoltre rifiutano input nulli con `UseCaseValidationException` e codici duplicati con `DuplicateResourceException`.

Questa fase non introduce ancora database, JPA, Spring Data, transazioni o query avanzate. Serve a preparare test e primi use case applicativi mantenendo dominio, application e infrastruttura separati.

Il Punto 6D è documentato in [`docs/19-application-in-memory-repositories.md`](docs/19-application-in-memory-repositories.md).

## Punto 6E — First Use Cases

Il Punto 6E aggiunge il primo comportamento applicativo reale. Sono stati introdotti command, result, port in e application service per Locations, Cargo e Shipments.

I primi use case permettono di registrare location, registrare cargo, creare una shipment draft, aggiungere item, aggiungere leg, confermare la shipment e recuperarla. Il flusso viene testato usando le repository in memory introdotte nel Punto 6D.

Questa fase non introduce ancora web, database, JPA, Spring, transazioni o security. Il suo scopo è dimostrare che l'application layer orchestra correttamente il dominio e distingue errori applicativi da errori di dominio.

Il Punto 6E è documentato in [`docs/20-application-first-use-cases.md`](docs/20-application-first-use-cases.md).

## Prossimi step consigliati

La roadmap consigliata è:

1. verificare sul Mac `mvn spotless:apply` e `mvn clean test` dopo ogni intervento importante;
2. mantenere aggiornata la documentazione `docs/13-domain-rules.md`, `docs/14-domain-review-patches.md`, `docs/15-domain-test-suite-review.md` e `docs/16-application-layer-blueprint.md` quando cambiano regole, test, confini o struttura applicativa;
3. mantenere il Punto 6A come blueprint ufficiale dell'application layer;
4. mantenere stabile il Punto 6B con foundation applicativa, package, eccezioni applicative, command, result e test;
5. mantenere stabile il Punto 6C con le prime repository port specifiche;
6. mantenere stabile il Punto 6D con le prime repository in memory per test e scenari locali;
7. mantenere stabile il Punto 6E con i primi use case Locations + Cargo + Shipments;
8. eseguire una review e un hardening dei primi use case nel Punto 6F;
9. rimandare API REST, database e integrazioni esterne finché l'application layer non è stabile.
