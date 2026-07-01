# Punto 6G — Application Use Cases Expansion

Il Punto 6G espande l'application layer dopo l'hardening del primo nucleo Locations + Cargo + Shipments.

La scelta fatta in questa fase è intenzionalmente controllata: aggiungere i primi use case applicativi del dominio `documents`, senza introdurre ancora REST API, database, JPA, Spring controller, security, workflow documentali, upload file, storage fisico o compliance operativa.

Il dominio `documents` era già stato modellato come dominio puro. Il Punto 6G lo rende ora utilizzabile dal livello applicativo tramite command, result, port in, port out, application service, repository in memory e test.

## Obiettivo dello step

L'obiettivo non è creare un sistema documentale completo.

L'obiettivo è introdurre il primo blocco applicativo per gestire documenti logici aziendali, mantenendo chiari i confini:

- il dominio `documents` continua a rappresentare il concetto puro di documento;
- l'application layer orchestra i casi d'uso;
- la repository port resta un contratto astratto;
- l'infrastructure memory resta un adapter temporaneo per sviluppo e test;
- non viene introdotta nessuna persistenza definitiva;
- non viene introdotto nessun controller web;
- non viene introdotto nessun workflow documentale avanzato.

## Package aggiunti

Sono stati aggiunti nuovi package applicativi dedicati ai documents:

```text
src/main/java/it/gabriele/truckflow/application/command/documents
src/main/java/it/gabriele/truckflow/application/result/documents
src/main/java/it/gabriele/truckflow/application/port/in/documents
src/main/java/it/gabriele/truckflow/application/port/out/documents
src/main/java/it/gabriele/truckflow/application/usecase/documents
src/main/java/it/gabriele/truckflow/infrastructure/memory/documents
```

Questi package seguono la stessa struttura già usata per Locations, Cargo e Shipments.

## Command aggiunti

Sono stati aggiunti quattro command applicativi:

- `RegisterDocumentCommand`;
- `FindDocumentCommand`;
- `ActivateDocumentCommand`;
- `ArchiveDocumentCommand`.

I command rappresentano input applicativi, non DTO REST.

Per questo motivo:

- non contengono annotazioni web;
- non contengono annotazioni Spring;
- non dipendono da JSON, HTTP o controller;
- validano solo input applicativi obbligatori;
- delegano le regole profonde al dominio.

`RegisterDocumentCommand` richiede:

- `DocumentCode`;
- `DocumentType`;
- `DocumentCategory`;
- `DocumentStatus`;
- `DocumentMetadata`.

Accetta inoltre:

- `DocumentContent`;
- `Set<DocumentReference>`;
- `notes`.

Le references nulle vengono normalizzate a set vuoto. Le references presenti non possono contenere elementi nulli.

## Result aggiunto

È stato aggiunto:

```text
DocumentResult
```

Il result espone una vista applicativa essenziale del documento:

- id;
- code;
- type;
- category;
- status;
- title;
- presenza di contenuto logico;
- numero di riferimenti.

`DocumentResult` non espone dettagli tecnici di file fisici, path, bucket, upload, download o storage, perché questi concetti non appartengono ancora a questa fase.

## Repository port aggiunta

È stata aggiunta la porta outbound:

```text
DocumentRepository
```

La porta consente:

- `save(Document document)`;
- `findById(DocumentId id)`;
- `findByCode(DocumentCode code)`;
- `existsById(DocumentId id)`;
- `existsByCode(DocumentCode code)`.

Questa porta è un contratto dell'application layer. Non è un repository JPA e non conosce database, query SQL, Spring Data o transazioni.

## Use case port aggiunte

Sono state aggiunte quattro port in:

- `RegisterDocumentUseCase`;
- `FindDocumentUseCase`;
- `ActivateDocumentUseCase`;
- `ArchiveDocumentUseCase`.

Queste interfacce rappresentano le azioni applicative disponibili verso il dominio documents.

## Application service aggiunti

Sono stati aggiunti quattro service applicativi:

- `RegisterDocumentService`;
- `FindDocumentService`;
- `ActivateDocumentService`;
- `ArchiveDocumentService`.

Ogni service:

- riceve un command;
- valida la presenza del command;
- usa la repository port;
- distingue errori applicativi da errori di dominio;
- restituisce un result applicativo;
- non importa infrastructure concreta;
- non importa Spring, REST, JPA o web.

## Mutazioni documentali copy-on-write

È stato aggiunto:

```text
DocumentMutationSupport
```

Come per le shipment nel Punto 6F, anche le mutazioni documentali lavorano su una copia dell'aggregate prima del salvataggio.

In questa fase le mutazioni documentali sono semplici:

- activate;
- archive.

L'approccio copy-on-write viene comunque introdotto subito per mantenere coerenza con l'hardening applicativo già fatto e per preparare in modo ordinato future mutazioni più delicate, come cambio metadati, sostituzione contenuto logico o modifica dei riferimenti.

## Repository in memory aggiunta

È stata aggiunta:

```text
InMemoryDocumentRepository
```

Questa repository:

- implementa `DocumentRepository`;
- salva documenti in mappe in memoria;
- indicizza per `DocumentId`;
- indicizza per `DocumentCode`;
- rifiuta input nulli con `UseCaseValidationException`;
- rifiuta codici duplicati con `DuplicateResourceException`.

Questa implementazione è solo un adapter tecnico temporaneo.

Non sostituisce:

- database;
- JPA;
- file storage;
- document storage enterprise;
- repository definitivo;
- audit trail;
- versioning documentale reale.

## Test aggiunti e aggiornati

È stato aggiunto:

```text
ApplicationUseCaseExpansionTest
```

Il test verifica:

- registrazione documento;
- ricerca documento;
- attivazione documento;
- archiviazione documento;
- persistenza dello stato dopo activate/archive;
- duplicati di codice;
- risorse documentali mancanti;
- command nulli;
- repository dependency nulle;
- input applicativi obbligatori;
- normalizzazione references nulle a set vuoto.

Sono stati aggiornati anche:

- `ApplicationRepositoryPortTest`;
- `InMemoryRepositoryTest`.

Questi test ora includono anche `DocumentRepository` e `InMemoryDocumentRepository`.

## Confini mantenuti

Il Punto 6G non introduce:

- REST API;
- controller Spring;
- DTO web;
- database;
- JPA;
- Hibernate;
- Spring Data;
- security;
- JWT;
- upload file;
- download file;
- file system storage;
- document versioning operativo;
- workflow approvativi;
- audit trail;
- scadenze documentali;
- compliance check concreti;
- dashboard;
- tracking;
- planning.

Il documento resta un aggregate puro e logico. L'application layer ora può registrarlo, recuperarlo, attivarlo e archiviarlo.

## Perché Documents prima di altri domini

Documents è stato scelto come primo dominio di espansione del Punto 6G perché:

- il dominio era già puro e indipendente;
- non richiede assegnazioni operative;
- non richiede disponibilità mezzi/autisti;
- non richiede scheduling;
- non richiede tracking;
- non richiede storage fisico;
- permette di estendere l'application layer senza rompere i confini architetturali.

Vehicles, Compliance, Operational Roles e altri moduli potranno essere aggiunti in step successivi, ma sono più delicati perché rischiano di introdurre presto pianificazione, regole paese, abilitazioni operative o workflow enterprise.

## Stato finale dopo il Punto 6G

Dopo questo step l'application layer copre:

- Locations;
- Cargo;
- Shipments;
- Documents.

Le repository in memory disponibili sono:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`;
- `InMemoryDocumentRepository`.

I use case applicativi documentali disponibili sono:

- registrare documento;
- trovare documento;
- attivare documento;
- archiviare documento.

## Prossimo step consigliato

Il prossimo passo consigliato diventa:

**Punto 6H — Application Use Cases Expansion Review & Documentation Alignment**.

Prima di introdurre nuovi domini applicativi, conviene rivedere la crescita del livello application dopo Documents, verificando:

- coerenza tra use case di domini diversi;
- naming di command/result/port/service;
- error handling;
- test negativi;
- duplicati;
- comportamento delle repository in memory;
- documentazione Markdown e HTML;
- confini con dominio e infrastructure.

Solo dopo questo review step sarà opportuno scegliere la prossima espansione tra Vehicles, Compliance, Operational Roles o ulteriori azioni su Shipments/Documents.

## Aggiornamento dopo il Punto 6H

Il Punto 6H ha completato la review del blocco Documents introdotto in questo step.

La review ha rafforzato:

- result applicativi null-safe;
- contratti `UseCase` verificati da test;
- service concreti verificati contro le proprie port in;
- repository in memory con validazione uniforme;
- test copy-on-write su activate/archive documentali;
- documentazione Markdown e HTML.

Il prossimo step consigliato non è più la review 6H, perché ora è completata. Il prossimo passo diventa il Punto 6I, cioè una nuova espansione applicativa controllata da scegliere tra Vehicles, Operational Roles, Compliance o ulteriori azioni logiche su Documents.

## Aggiornamento successivo — Punto 6I Vehicles

Dopo la prima espansione verso Documents del Punto 6G e la review del Punto 6H, il Punto 6I ha eseguito una seconda espansione controllata verso Vehicles.

Questa nuova fase non modifica il significato del Punto 6G: Documents rimane il primo dominio applicativo espanso dopo Locations, Cargo e Shipments. Vehicles viene aggiunto come step successivo separato, con documentazione dedicata in `24-application-use-cases-expansion-vehicles.md`.

## Aggiornamento successivo — Punto 6J Operational Roles

Dopo Documents, review 6H e Vehicles 6I, il Punto 6J ha aggiunto i primi use case applicativi Operational Roles. Il principio rimane lo stesso: command, result, port, service e repository astratte, senza REST API, database o workflow enterprise.

## Aggiornamento dopo il Punto 6K

Il Punto 6K non modifica i use case Documents introdotti nel Punto 6G. Conferma però la regola generale delle espansioni applicative: ogni nuovo dominio applicativo deve essere seguito da review, test negativi e allineamento documentale prima di procedere oltre.

## Aggiornamento successivo — Punto 6L Compliance base

Dopo Documents, Vehicles e Operational Roles, il Punto 6L aggiunge una nuova espansione controllata verso Compliance base.

Il principio rimane lo stesso: vengono aggiunti use case applicativi per un aggregate già puro (`ComplianceRequirement`) senza introdurre controlli reali di violazione, motori regole, audit trail, workflow, REST API o database.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
