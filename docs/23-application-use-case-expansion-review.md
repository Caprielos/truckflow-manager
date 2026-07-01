# Punto 6H — Application Use Case Expansion Review & Documentation Alignment

Il Punto 6H chiude la prima espansione controllata dell'application layer verso `documents` con una review tecnica e documentale.

Questo step non aggiunge nuovi domini applicativi. Serve invece a verificare che quanto introdotto tra il Punto 6E, il Punto 6F e il Punto 6G sia coerente, stabile, testabile e ben documentato prima di procedere con un'altra espansione.

## Obiettivo dello step

L'obiettivo del Punto 6H è rafforzare la qualità dell'application layer dopo l'aggiunta dei use case Documents.

La review controlla:

- coerenza dei contratti `UseCase`;
- coerenza tra port in e application service;
- error handling applicativo;
- comportamento dei result applicativi;
- comportamento delle repository in memory;
- mutazioni copy-on-write;
- documentazione Markdown e HTML;
- confini tra application, domain e infrastructure.

## Cosa non viene introdotto

Il Punto 6H non introduce:

- nuovi domini applicativi;
- nuovi use case business;
- REST API;
- controller Spring;
- DTO web;
- database;
- JPA;
- Spring Data;
- security;
- tracking;
- planning;
- dashboard;
- workflow documentali;
- upload o download file;
- audit trail;
- compliance check concreti.

Questa fase è volutamente una fase di review, hardening leggero e allineamento documentale.

## Stato applicativo dopo il Punto 6G

Prima del Punto 6H, l'application layer copriva già quattro aree:

- Locations;
- Cargo;
- Shipments;
- Documents.

I use case disponibili erano:

- registrare e trovare location;
- registrare e trovare cargo unit;
- creare, trovare, confermare e cancellare shipment;
- aggiungere item e leg a una shipment;
- registrare, trovare, attivare e archiviare documenti logici.

Il Punto 6H non cambia questa lista. La rende più controllata.

## Rafforzamento dei result applicativi

Sono stati rafforzati i factory method `from(...)` dei result applicativi:

- `LocationResult.from(Location location)`;
- `CargoUnitResult.from(CargoUnit cargoUnit)`;
- `ShipmentResult.from(Shipment shipment)`;
- `DocumentResult.from(Document document)`.

Prima, una chiamata accidentale con oggetto dominio nullo avrebbe prodotto un errore Java generico.

Ora tutti questi result rifiutano input nulli con:

```text
UseCaseValidationException
```

Questa scelta rende l'errore coerente con il resto dell'application layer: un input applicativo mancante produce una validazione applicativa, non una `NullPointerException` implicita.

## Review dei contratti UseCase

È stato aggiunto il test:

```text
ApplicationUseCaseReviewTest
```

Questo test verifica che tutte le port in attualmente disponibili estendano il contratto base:

```text
UseCase<C, R>
```

Le port verificate includono:

- `RegisterLocationUseCase`;
- `FindLocationUseCase`;
- `RegisterCargoUnitUseCase`;
- `FindCargoUnitUseCase`;
- `CreateShipmentUseCase`;
- `FindShipmentUseCase`;
- `AddShipmentItemUseCase`;
- `AddShipmentLegUseCase`;
- `ConfirmShipmentUseCase`;
- `CancelShipmentUseCase`;
- `RegisterDocumentUseCase`;
- `FindDocumentUseCase`;
- `ActivateDocumentUseCase`;
- `ArchiveDocumentUseCase`.

Questo protegge la struttura applicativa da future port create fuori standard.

## Review dei service applicativi

Lo stesso test verifica anche che ogni application service implementi la propria port in.

Esempi:

- `RegisterDocumentService` implementa `RegisterDocumentUseCase`;
- `ActivateDocumentService` implementa `ActivateDocumentUseCase`;
- `CancelShipmentService` implementa `CancelShipmentUseCase`;
- `AddShipmentLegService` implementa `AddShipmentLegUseCase`.

Questa verifica è importante perché il progetto sta crescendo: man mano che aumentano i use case, è facile creare service concreti non allineati al contratto pubblico.

## Review dei result null-safe

`ApplicationUseCaseReviewTest` verifica anche che i result applicativi rifiutino oggetti dominio nulli tramite `UseCaseValidationException`.

Sono coperti:

- `LocationResult`;
- `CargoUnitResult`;
- `ShipmentResult`;
- `DocumentResult`.

Questo standardizza il comportamento dei result e rende più chiara la distinzione tra errore applicativo e errore tecnico accidentale.

## Rafforzamento delle repository in memory

`InMemoryRepositoryTest` è stato ampliato.

Prima controllava input nulli su:

- `save(null)`;
- `findById(null)`;
- `findByCode(null)`.

Ora controlla anche:

- `existsById(null)`;
- `existsByCode(null)`.

Il controllo vale per tutte le repository in memory attuali:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`;
- `InMemoryDocumentRepository`.

In questo modo tutte le operazioni pubbliche delle repository in memory hanno lo stesso comportamento di validazione applicativa.

## Review copy-on-write sui Documents

Il Punto 6G aveva introdotto `DocumentMutationSupport`, usato da:

- `ActivateDocumentService`;
- `ArchiveDocumentService`.

Nel Punto 6H il test del flusso documentale è stato rafforzato per verificare esplicitamente che activate/archive non mutino direttamente l'istanza precedente già letta dalla repository.

Il comportamento atteso è:

1. il service legge il documento;
2. crea una copia dell'aggregate;
3. applica la mutazione sulla copia;
4. salva solo la copia aggiornata;
5. l'istanza precedente resta invariata.

Questo mantiene coerente Documents con l'hardening già applicato alle Shipment nel Punto 6F.

## Documentazione aggiornata

Il Punto 6H aggiorna la documentazione ufficiale in modo completo.

Sono stati allineati:

- `docs/23-application-use-case-expansion-review.md`;
- `docs/README.md`;
- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`;
- `digitalDocs/index.html`;
- `digitalDocs/styles.css`;
- `digitalDocs/README.md`;
- `digitalDocs/truckflow-manager-enterprise-documentation.html`;
- `command_basic.md`.

La documentazione digitale HTML resta un mirror navigabile della documentazione Markdown.

## Confini architetturali confermati

Dopo il Punto 6H resta confermato che:

- il domain layer non dipende dall'application layer;
- l'application layer non dipende da infrastructure concreta;
- l'application layer non importa Spring, JPA, web o persistence framework;
- le repository port restano nell'application layer;
- le repository in memory restano adapter sostituibili;
- i command non sono DTO REST;
- i result non sono response web;
- i service applicativi orchestrano, ma non diventano controller.

## Stato finale dopo il Punto 6H

Dopo questa review, l'application layer è più stabile perché:

- tutti i result principali sono null-safe;
- tutte le port in attuali sono verificate contro il contratto base `UseCase`;
- tutti i service attuali sono verificati contro la propria port in;
- le repository in memory hanno validazione uniforme su tutte le operazioni pubbliche;
- i document use case sono verificati anche rispetto al comportamento copy-on-write;
- la documentazione Markdown, HTML e CSS è allineata allo stato reale del progetto.

## Prossimo step consigliato

Il prossimo passo consigliato diventa:

**Punto 6I — Application Use Cases Expansion II**.

Solo dopo questa review ha senso scegliere una nuova espansione applicativa.

Le opzioni più coerenti sono:

- Vehicles, con use case anagrafici iniziali e senza planning;
- Operational Roles, con registrazione/ricerca di figure operative senza scheduling;
- Compliance, con catalogazione requisiti astratti senza controlli reali;
- ulteriore Documents, ma solo su metadati logici e riferimenti, senza file upload o workflow.

La scelta va fatta mantenendo gli stessi vincoli: niente REST API, niente database, niente JPA, niente security e niente moduli enterprise operativi finché l'application layer non è stabile.

## Aggiornamento successivo — Punto 6I Vehicles

Dopo questa review, il progetto ha applicato il Punto 6I — Application Use Cases Expansion II: Vehicles.

Il Punto 6I aggiunge i primi use case applicativi Vehicles mantenendo validi i principi consolidati qui:

- tutte le nuove port in estendono `UseCase`;
- tutti i nuovi service implementano la propria port in;
- i nuovi result rifiutano input dominio nulli con `UseCaseValidationException`;
- le nuove repository in memory rifiutano input nulli;
- le mutazioni di stato delle `VehicleUnit` usano approccio copy-on-write;
- non vengono introdotti REST API, database, JPA, controller, security, tracking, planning o dashboard.

La documentazione del nuovo step è in `24-application-use-cases-expansion-vehicles.md`.

## Aggiornamento successivo — Punto 6J Operational Roles

Dopo Vehicles, il progetto ha applicato il Punto 6J verso Operational Roles. Anche questa espansione mantiene i result null-safe, le port in basate su `UseCase`, service allineati alle port e repository in memory usate solo come adapter temporanei.

## Aggiornamento dopo il Punto 6K

Il Punto 6K applica agli Operational Roles la stessa logica di review introdotta nel Punto 6H dopo Documents.

La differenza è che il Punto 6K si concentra sulla copertura completa dei service di stato e sulla protezione copy-on-write delle attivazioni fallite per Driver, Mechanic, WarehouseOperator, Dispatcher e Manager.
