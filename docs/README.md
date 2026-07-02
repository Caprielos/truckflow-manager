# TruckFlow Manager — Documentazione del dominio

Questa cartella contiene la documentazione ufficiale di TruckFlow Manager, con la fondazione del dominio puro, il primo ciclo completo dell’application layer fino al Punto 6M, l’apertura documentale del Punto 7A — Infrastructure Layer Blueprint, la foundation tecnica leggera del Punto 7B e il wiring Spring controllato del Punto 7C, il blueprint di mapping del Punto 7D, il primo prototipo di repository reale del Punto 7E, l'espansione repository del Punto 7F, il rafforzamento tecnico del Punto 7G e il freeze finale del Punto 7H.

Il documento `29-final-roadmap-documentation-alignment.md` chiarisce lo stato reale dopo il Punto 6M: il Punto 6 è chiuso, la descrizione corretta del Punto 6G Documents è register/find/activate/archive e le dipendenze Spring presenti nel `pom.xml` sono preparatorie, non ancora usate come architettura REST o persistence. Il documento `30-infrastructure-layer-blueprint.md` apre il Punto 7A e definisce confini, filosofia, roadmap 7A→7H e regole dell’infrastructure layer prima di qualunque implementazione reale. Il documento `31-infrastructure-foundation.md` descrive il Punto 7B e formalizza package, eccezioni e convenzioni tecniche della foundation infrastrutturale. Il documento `32-spring-wiring-foundation.md` descrive il Punto 7C e spiega come Spring viene usato solo come wiring tecnico non web, mantenendo domain e application layer privi di dipendenze framework. Il documento `33-persistence-mapping-blueprint.md` descrive il Punto 7D e formalizza il catalogo dei mapping blueprint domain ↔ persistence senza introdurre ancora JPA, database o repository reali. Il documento `34-real-repository-prototype.md` descrive il Punto 7E e introduce il primo repository reale prototipale file-backed per Locations, senza database, JPA, Spring Data o REST API. Il documento `35-repository-expansion.md` descrive il Punto 7F e l'espansione file-backed verso Cargo, Documents e Compliance. Il documento `36-infrastructure-testing.md` descrive il Punto 7G e rafforza i test tecnici su storage, integrazione use case ↔ repository file-backed e confini architetturali. Il documento `37-infrastructure-review-freeze.md` chiude il Punto 7H, congela lo scope infrastrutturale validato e prepara il progetto al futuro Punto 8 — API Layer.

Il progetto ha completato la fondazione del **domain layer**. L'**application layer** ha consolidato i primi casi d'uso, ha completato la prima espansione controllata verso Documents, l'ha rafforzata con una review tecnica e documentale, ha aggiunto i primi use case applicativi Vehicles e Operational Roles, ha rafforzato Operational Roles con il Punto 6K, ha introdotto i primi use case Compliance base nel Punto 6L e ha chiuso il primo ciclo dell'application layer con il Punto 6M. La fondazione di dominio contiene i seguenti package principali:

- `domain.users`
- `domain.qualifications`
- `domain.operational`
- `domain.vehicles`
- `domain.cargo`
- `domain.locations`
- `domain.triptemplates`
- `domain.shipments`
- `domain.documents`
- `domain.compliance`

L’obiettivo di questa documentazione è spiegare in italiano, in modo chiaro e professionale, **perché il dominio è stato modellato così**, quali scelte sono state fatte, cosa ogni package rappresenta e come il progetto sta preparando il livello applicativo.


## Documentazione digitale HTML + CSS

È stata aggiunta una prima documentazione digitale separata nella cartella [`../digitalDocs`](../digitalDocs/index.html).

Questa nuova documentazione non sostituisce i file Markdown esistenti. Serve come primo prototipo di documentazione navigabile in HTML + CSS, con contenuto principale in inglese e traduzioni italiane disponibili tramite tooltip o visualizzazione diretta.

Il sistema usa un flag globale sul tag `<body>`:

- `tooltip-enabled` per mostrare le traduzioni italiane tramite hover;
- `tooltip-disabled` per mostrare le traduzioni italiane direttamente nella pagina.

I nomi tecnici rimangono in inglese e allineati al codice Java.

## Indice consigliato

1. [`01-project-overview.md`](01-project-overview.md) — visione generale del progetto e principi architetturali.
2. [`02-domain-users.md`](02-domain-users.md) — documentazione completa del dominio utenti.
3. [`03-domain-qualifications.md`](03-domain-qualifications.md) — documentazione completa del catalogo abilitazioni.
4. [`04-domain-operational.md`](04-domain-operational.md) — documentazione completa delle figure operative aziendali.
5. [`05-domain-vehicles.md`](05-domain-vehicles.md) — documentazione completa del dominio veicoli e combinazioni.
6. [`06-architecture-decisions.md`](06-architecture-decisions.md) — decisioni architetturali e regole generali del dominio puro.
7. [`07-domain-cargo.md`](07-domain-cargo.md) — documentazione completa del dominio cargo e dei requisiti della merce.
8. [`08-domain-locations.md`](08-domain-locations.md) — documentazione completa del dominio locations e dei luoghi logistici.
9. [`09-domain-triptemplates.md`](09-domain-triptemplates.md) — documentazione completa dei percorsi tipo e delle missioni tecniche astratte.
10. [`10-domain-shipments.md`](10-domain-shipments.md) — documentazione completa delle spedizioni richieste e dei loro requisiti.
11. [`11-domain-documents.md`](11-domain-documents.md) — documentazione completa del dominio documents e del concetto puro di documento aziendale.
12. [`12-domain-compliance.md`](12-domain-compliance.md) — documentazione completa del dominio compliance e dei requisiti astratti di conformità.
13. [`13-domain-rules.md`](13-domain-rules.md) — regole ufficiali della TruckFlow Domain Foundation v1.0 e roadmap della domain review.
14. [`14-domain-review-patches.md`](14-domain-review-patches.md) — riepilogo degli interventi correttivi eseguiti durante la prima review concreta del dominio puro.
15. [`15-domain-test-suite-review.md`](15-domain-test-suite-review.md) — revisione finale della test suite del dominio puro, con cosa è stato aggiunto, cosa manca e perché.
16. [`16-application-layer-blueprint.md`](16-application-layer-blueprint.md) — blueprint del Punto 6A: obiettivi, struttura, package, use case, repository port, repository in memory, test strategy e roadmap dell'application layer.
17. [`17-application-foundation.md`](17-application-foundation.md) — foundation del Punto 6B: package application, contratti base, eccezioni applicative, test architetturali e cosa manca prima dei primi use case.
18. [`18-application-repository-ports.md`](18-application-repository-ports.md) — repository port del Punto 6C: `RepositoryPort`, `LocationRepository`, `CargoUnitRepository`, `ShipmentRepository`, contratti per ID/codice e prossimo passaggio verso repository in memory.
19. [`19-application-in-memory-repositories.md`](19-application-in-memory-repositories.md) — repository in memory del Punto 6D: `InMemoryLocationRepository`, `InMemoryCargoUnitRepository`, `InMemoryShipmentRepository`, regole sui duplicati, test e limiti dello step.
20. [`20-application-first-use-cases.md`](20-application-first-use-cases.md) — primi use case del Punto 6E: command, result, port in, application service e flusso Locations + Cargo + Shipments.
21. [`21-application-use-case-hardening.md`](21-application-use-case-hardening.md) — hardening del Punto 6F: review dei primi use case, `CancelShipmentUseCase`, copy-on-write delle mutazioni shipment, test negativi e protezione dalle mutazioni parziali.
22. [`22-application-use-case-expansion.md`](22-application-use-case-expansion.md) — espansione del Punto 6G: primi use case applicativi Documents, `DocumentRepository`, `InMemoryDocumentRepository`, command/result/port/service e test applicativi.
23. [`23-application-use-case-expansion-review.md`](23-application-use-case-expansion-review.md) — review del Punto 6H: contratti `UseCase`, service allineati alle port in, result null-safe, repository in memory uniformi, copy-on-write Documents e documentazione aggiornata.
24. [`24-application-use-cases-expansion-vehicles.md`](24-application-use-cases-expansion-vehicles.md) — espansione del Punto 6I: primi use case applicativi Vehicles, `VehicleUnitRepository`, `VehicleCombinationRepository`, repository in memory, command/result/port/service e test applicativi.
25. [`25-application-use-cases-expansion-operational-roles.md`](25-application-use-cases-expansion-operational-roles.md) — espansione del Punto 6J: primi use case applicativi Operational Roles, repository port Operational, repository in memory Operational e test applicativi.
26. [`26-application-operational-use-case-hardening.md`](26-application-operational-use-case-hardening.md) — hardening del Punto 6K: review Operational Roles, copertura completa dei service di stato, test copy-on-write sulle attivazioni fallite e documentazione allineata.
27. [`27-application-compliance-base-use-cases.md`](27-application-compliance-base-use-cases.md) — espansione del Punto 6L: primi use case Compliance base, `ComplianceRequirementRepository`, `InMemoryComplianceRequirementRepository` e flusso register/find/status.
28. [`28-application-layer-final-review-freeze.md`](28-application-layer-final-review-freeze.md) — chiusura del Punto 6M: review finale, freeze dell'application layer, controlli architetturali e documentazione di cosa resta fuori.
29. [`29-final-roadmap-documentation-alignment.md`](29-final-roadmap-documentation-alignment.md) — allineamento documentale finale: roadmap reale 1 → 6M, correzione del 6G Documents, nota sulle dipendenze Spring preparatorie e Guided Links.
30. [`30-infrastructure-layer-blueprint.md`](30-infrastructure-layer-blueprint.md) — Punto 7A: blueprint dell'Infrastructure Layer, confini architetturali, roadmap 7A → 7H, ruolo di Spring, repository reali, adapter, mapping e relazione con il futuro Punto 8 API Layer.
31. [`31-infrastructure-foundation.md`](31-infrastructure-foundation.md) — Punto 7B: foundation tecnica leggera dell'Infrastructure Layer, package base, eccezioni tecniche, convenzioni, marker infrastructure e test architetturale.
32. [`32-spring-wiring-foundation.md`](32-spring-wiring-foundation.md) — Punto 7C: wiring Spring controllato, entry point tecnico, configurazioni bean, profilo memory, runtime non web e test architetturale dedicato.
33. [`33-persistence-mapping-blueprint.md`](33-persistence-mapping-blueprint.md) — Punto 7D: blueprint del mapping domain ↔ persistence, catalogo dei contesti applicativi, regole su ID/codice/stato/value object/reference e test architetturale dedicato.
34. [`34-real-repository-prototype.md`](34-real-repository-prototype.md) — Punto 7E: primo repository reale prototipale per Locations, con persistence record, mapper concreto, adapter file-backed e test tecnici dedicati.
35. [`35-repository-expansion.md`](35-repository-expansion.md) — Punto 7F: espansione file-backed verso Cargo, Documents e Compliance, con supporto file condiviso, mapper, codec e test tecnici.
36. [`36-infrastructure-testing.md`](36-infrastructure-testing.md) — Punto 7G: rafforzamento dei test infrastrutturali su storage file-backed, integrazione use case/repository e confini architetturali.
37. [`37-infrastructure-review-freeze.md`](37-infrastructure-review-freeze.md) — Punto 7H: review e freeze finale dell'Infrastructure Layer prima del futuro Punto 8 API Layer.

## Stato del progetto documentato

Questa documentazione descrive la versione del progetto in cui il dominio contiene:

- account applicativi e autorizzazioni di base;
- catalogo statico delle qualificazioni e abilitazioni;
- figure operative reali dell’azienda;
- unità veicolo, allestimenti, combinazioni, schede tecniche, capacità, agganci e ruoli operativi dei mezzi, con `domain.vehicles` organizzato in sottopackage (`unit`, `combination`, `coupling`, `specification`, `body`, `operation`, `common`);
- dominio cargo per descrivere la merce, le sue caratteristiche e i suoi requisiti di trasporto senza introdurre pianificazione o compatibilità implementata;
- dominio locations per descrivere luoghi logistici riutilizzabili come depositi, hub, yard, porti, clienti e fornitori;
- dominio triptemplates per descrivere percorsi tipo e missioni tecniche astratte senza assegnare mezzi, autisti, cargo o orari reali;
- dominio shipments per descrivere richieste di spedizione, item cargo, tratte logiche, priorità, livelli di servizio, requisiti e riferimenti senza introdurre pianificazione o tracking, con `domain.shipments` organizzato in sottopackage (`core`, `items`, `legs`, `requirements`, `metrics`, `properties`, `notes`, `references`);
- dominio documents per descrivere il concetto puro di documento aziendale, con identità, codice, tipo, categoria, stato, metadati, contenuto logico e riferimenti astratti, senza introdurre file fisici, upload, storage, workflow, scadenze o compliance operativa;
- dominio compliance per descrivere requisiti astratti di conformità, categorie, livelli di obbligatorietà, severità, target, regole, fonti e giurisdizioni senza introdurre controlli automatici, workflow, audit, scadenze o risultati di verifica;
- application layer base chiuso al Punto 6M con use case, repository port, repository in memory, test e documentazione allineati per Locations, Cargo, Shipments, Documents, Vehicles, Operational Roles e Compliance base;
- roadmap finale chiarita nel documento `29-final-roadmap-documentation-alignment.md`, che sostituisce le descrizioni intermedie ormai superate;
- blueprint del Punto 7A documentato in `30-infrastructure-layer-blueprint.md`, che apre il ciclo Infrastructure Layer senza introdurre ancora codice operativo, database, REST API, controller, JPA o security;
- foundation tecnica del Punto 7B documentata in `31-infrastructure-foundation.md`, che introduce package infrastrutturali, eccezioni tecniche, marker di adapter, profili tecnici e test architetturale senza ancora introdurre database, JPA, Spring Data, controller o repository reali completi;
- wiring Spring controllato del Punto 7C documentato in `32-spring-wiring-foundation.md`, che collega repository in memory e use case tramite configurazioni Spring, mantenendo il runtime non web e lasciando domain/application privi di dipendenze framework;
- regole ufficiali di dominio per guidare la TruckFlow Domain Foundation v1.0, la review finale del dominio puro e l'introduzione graduale delle eccezioni custom;
- prima review correttiva del dominio puro completata con otto interventi mirati: mutazioni atomiche, eccezioni custom, `OperationalCode` obbligatorio, test catalogo qualificazioni meno fragili, pulizia dei file locali/generati e introduzione di `LicensePlate` e `VehicleIdentificationNumber` come value object del dominio veicoli, più `LanguageCode`, `UserTheme`, `CountryCode`, `JurisdictionRegion`, `ComplianceJurisdictionScope` e `OperationalScopeCode` per ridurre primitive obsession;
- revisione finale della test suite del dominio puro documentata in `15-domain-test-suite-review.md`, con test architetturali, test contrattuali dei value object, casi limite cargo e shipment e spiegazione esplicita di cosa rimane fuori perché appartiene a moduli futuri;
- blueprint del primo application layer documentato in `16-application-layer-blueprint.md`, che definisce struttura, responsabilità, package, command, result, port, use case, repository in memory e test strategy prima di introdurre codice applicativo;
- foundation del primo application layer documentata in `17-application-foundation.md`, che introduce package application, contratti base, eccezioni applicative e test architetturali senza ancora aggiungere use case specifici o repository in memory;
- repository port del primo application layer documentate in `18-application-repository-ports.md`, con `RepositoryPort`, `LocationRepository`, `CargoUnitRepository` e `ShipmentRepository` come primi contratti outbound per Locations, Cargo e Shipments;
- repository in memory del primo infrastructure adapter documentate in `19-application-in-memory-repositories.md`, con `InMemoryLocationRepository`, `InMemoryCargoUnitRepository` e `InMemoryShipmentRepository` per test e sviluppo locale senza database;
- primi use case applicativi documentati in `20-application-first-use-cases.md`, con command, result, port in e service per Locations, Cargo e Shipments;
- hardening dei primi use case documentato in `21-application-use-case-hardening.md`, con `CancelShipmentUseCase`, copy-on-write dei service di mutazione shipment, test negativi, controllo degli errori applicativi e verifica delle mutazioni fallite;
- espansione controllata dei use case applicativi documentata in `22-application-use-case-expansion.md`, con `RegisterDocumentUseCase`, `FindDocumentUseCase`, `ActivateDocumentUseCase`, `ArchiveDocumentUseCase`, `DocumentRepository`, `InMemoryDocumentRepository` e test del flusso documentale logico;
- review e allineamento del Punto 6H documentati in `23-application-use-case-expansion-review.md`, con controlli su contratti `UseCase`, service, result null-safe, repository in memory e copy-on-write Documents;
- espansione controllata dei use case Vehicles documentata in `24-application-use-cases-expansion-vehicles.md`, con `RegisterVehicleUnitUseCase`, `FindVehicleUnitUseCase`, use case di stato VehicleUnit, `RegisterVehicleCombinationUseCase`, `FindVehicleCombinationUseCase`, repository port Vehicles, repository in memory Vehicles e test applicativi;
- espansione controllata dei use case Operational Roles documentata in `25-application-use-cases-expansion-operational-roles.md`, con use case per Driver, Mechanic, WarehouseOperator, Dispatcher e Manager, repository port Operational, repository in memory Operational e test applicativi.


## Nota sul packaging di `domain.vehicles`

Il dominio veicoli è stato riorganizzato in sottopackage per renderlo più leggibile:

- `domain.vehicles.unit` per le unità fisiche;
- `domain.vehicles.combination` per bilici, autotreni e mezzi singoli operativi;
- `domain.vehicles.coupling` per agganci e traino;
- `domain.vehicles.specification` per le schede tecniche;
- `domain.vehicles.body` per allestimenti e profili di allestimento;
- `domain.vehicles.operation` per capacità e ruoli operativi;
- `domain.vehicles.common` per validazioni condivise.

Questa divisione non è per tipo di mezzo, ma per concetto di dominio. È stata scelta perché molte classi sono condivise tra camion, trattori, rimorchi, semirimorchi e mezzi di magazzino.

## Cosa significa “domain puro”

Nel progetto TruckFlow, “domain puro” significa che le classi di dominio descrivono il business e le sue regole essenziali, senza dipendere da framework o infrastruttura.

Quindi il dominio non contiene:

- controller REST;
- database;
- JPA;
- Spring;
- JWT;
- microservizi;
- chiamate HTTP;
- disponibilità giornaliera;
- pianificazione dei viaggi;
- documenti PDF;
- scadenze amministrative;
- GPS o telematica.

Tutte queste parti potranno essere aggiunte in futuro in layer o moduli separati, senza sporcare il dominio.

## Nota sulle dipendenze Spring preparatorie

Il `pom.xml` può contenere dipendenze Spring Boot, Web, Validation o OpenAPI già aggiunte in precedenza.

Questa presenza non cambia lo stato architetturale del progetto: fino al Punto 6M non sono stati introdotti controller REST, entity JPA, repository Spring Data, security configuration o persistence reale. Il dominio e l'application layer restano indipendenti da Spring.

## Nota su `domain.locations` e `domain.triptemplates`

Le location sono state modellate come dominio separato perché i luoghi non appartengono solo ai percorsi: in futuro saranno utili anche per clienti, fornitori, magazzini, spedizioni, tracking, documenti e pianificazione.

I percorsi astratti sono stati modellati come `TripTemplate`, non come `Trip`, per evitare confusione con il viaggio reale operativo. Un `TripTemplate` descrive la struttura del percorso; il viaggio eseguito con veicolo, autista, cargo e orari reali verrà modellato più avanti in planning/dispatching.

## Nota su `domain.shipments`

Il dominio shipments rappresenta la richiesta di spedizione: cosa deve essere spedito, quali cargo compongono la spedizione, da quali location parte, verso quali location arriva e quali requisiti devono essere rispettati.

Una shipment non è ancora un viaggio operativo reale. Per questo non contiene veicoli, autisti, orari reali, tracking, documenti operativi o costi. Questi concetti verranno introdotti più avanti nei moduli di planning, dispatching, transport execution, tracking e documents.

Il package `domain.shipments` è stato riorganizzato in sottopackage tematici per migliorare la leggibilità:

- `domain.shipments.core` per l'aggregate root `Shipment`, ID, codice, stato, priorità, livello di servizio e validazioni condivise;
- `domain.shipments.items` per gli item cargo della spedizione;
- `domain.shipments.legs` per le tratte logiche della spedizione;
- `domain.shipments.requirements` per i requisiti di trasporto dichiarati;
- `domain.shipments.metrics` per peso e volume dichiarati;
- `domain.shipments.properties` per proprietà generali e temperatura;
- `domain.shipments.notes` per note interne ed esterne;
- `domain.shipments.references` per riferimenti cliente, fornitore e interni.

Questa divisione non crea micro-aggregate. `Shipment` rimane l'unico aggregate root; tutti gli altri elementi rimangono entity interne o value object appartenenti alla shipment.

## Nota su `domain.documents`

Il dominio documents rappresenta il documento aziendale come concetto astratto e riusabile. Non gestisce file PDF, upload, path filesystem, URL, storage, firma digitale, scadenze o workflow.

Un documento contiene solo identità, codice aziendale, tipo, categoria, stato astratto, metadati, contenuto logico opzionale e riferimenti generici verso altri domini tramite `DocumentReference`.

`DocumentReference` non importa gli ID concreti degli altri domini: usa `DocumentReferenceType` e un `referencedId` testuale. Questa scelta mantiene `domain.documents` disaccoppiato da vehicles, cargo, shipments, locations, triptemplates e operational.


## Nota su `domain.compliance`

Il dominio compliance rappresenta i requisiti astratti di conformità di TruckFlow. Un `ComplianceRequirement` descrive una regola, la sua categoria, il suo tipo, il livello di obbligatorietà, la severità, il target astratto, la fonte e la giurisdizione.

Il dominio non esegue controlli concreti e non contiene violazioni, audit, workflow, scadenze, approvazioni o notifiche. Questi concetti saranno introdotti più avanti nei moduli applicativi di compliance check, planning, dispatching e audit.

Questa scelta completa la prima grande fondazione del dominio puro enterprise di TruckFlow, mantenendo separati requisiti astratti e verifiche operative.



## Nota su `docs/14-domain-review-patches.md`

Il documento `14-domain-review-patches.md` riepiloga la prima review correttiva concreta del dominio puro.

Non descrive procedure operative di applicazione tecnica, ma spiega perché sono stati eseguiti gli otto interventi principali:

- validare prima di mutare lo stato degli aggregate;
- usare eccezioni custom nei domini semplici;
- usare eccezioni custom nei domini complessi;
- rendere `OperationalCode` obbligatorio;
- rendere meno fragili i test del catalogo qualificazioni;
- tenere fuori dal repository file locali, artefatti generati e file temporanei;
- trasformare targa e VIN da primitive `String` a value object del dominio veicoli;
- rafforzare preferenze utente, giurisdizione compliance e scope operativi con `LanguageCode`, `UserTheme`, `CountryCode`, `JurisdictionRegion`, `ComplianceJurisdictionScope` e `OperationalScopeCode`.

Questo documento serve come storico architetturale della review e come riferimento per le prossime fasi.

## Nota su `docs/13-domain-rules.md`

Il documento `13-domain-rules.md` definisce la roadmap ufficiale della TruckFlow Domain Foundation v1.0.

La Domain Review Finale della TruckFlow Domain Foundation v1.0 è considerata completata per il perimetro attuale del dominio puro. Future review saranno necessarie solo quando verranno introdotti nuovi punti roadmap come infrastructure, API, planning, dispatching, workflow o moduli enterprise avanzati.

Le regole principali sono:

- non unificare value object simili se hanno significato diverso;
- non importare aggregate root completi da altri domini;
- usare riferimenti tramite ID, value object stabili o concetti astratti;
- introdurre eccezioni custom di dominio in modo graduale;
- non aggiungere ancora concetti come `ComplianceViolationException`, perché le violazioni concrete appartengono a moduli futuri di compliance check, audit o planning.


## Nota su `docs/15-domain-test-suite-review.md`

Il documento `15-domain-test-suite-review.md` descrive la revisione finale della test suite del dominio puro.

Spiega cosa è stato aggiunto nei test, perché sono stati aggiunti test architetturali e test contrattuali dei value object, quali invarianti sono stati rafforzati e quali aree non vengono ancora testate perché appartengono a moduli futuri come availability, maintenance, planning, dispatching, workflow documentali e compliance check concreti.

## Nota su `docs/16-application-layer-blueprint.md`

Il documento `16-application-layer-blueprint.md` apre ufficialmente il Punto 6A.

Non introduce ancora controller REST, database, JPA, Spring, API o workflow operativi. Definisce invece come TruckFlow Manager dovrà costruire il livello che orchestra il dominio: command, result, port in, port out, application service, eccezioni applicative, repository astratti, repository in memory e test dei casi d'uso.

Il primo blocco applicativo consigliato è `Locations + Cargo + Shipments`, perché permette di costruire un flusso reale senza anticipare planning, dispatching, tracking o compatibilità cargo-veicolo operative.

## Nota su `docs/17-application-foundation.md`

Il documento `17-application-foundation.md` descrive il Punto 6B.

Questo step inizia il codice dell'application layer senza introdurre ancora funzionalità operative complete. Aggiunge package applicativi, contratti base, eccezioni applicative e test architetturali.

La foundation ha preparato i successivi step: repository port specifici, repository in memory e primi use case del blocco Locations + Cargo + Shipments.

## Nota su `docs/18-application-repository-ports.md`

Il documento `18-application-repository-ports.md` descrive il Punto 6C.

Questo step aggiunge i primi contratti outbound specifici dell’application layer: `RepositoryPort`, `LocationRepository`, `CargoUnitRepository` e `ShipmentRepository`.

Le porte permettono ai futuri use case di salvare e recuperare aggregate tramite ID e codice, senza conoscere database, JPA, Spring, file system o infrastructure concreta.

Storicamente, dopo questo step il progetto ha creato repository in memory ufficiali per testare i primi flussi applicativi senza database. Oggi questo lavoro è già stato completato e consolidato fino al Punto 6M.

## Nota su `docs/19-application-in-memory-repositories.md`

Il documento `19-application-in-memory-repositories.md` descrive il Punto 6D.

Questo step aggiunge le prime implementazioni concrete e leggere delle repository port: `InMemoryLocationRepository`, `InMemoryCargoUnitRepository` e `InMemoryShipmentRepository`.

Queste implementazioni permettono test e sviluppo locale senza database, proteggendo input nulli e codici duplicati.

## Nota su `docs/20-application-first-use-cases.md`

Il documento `20-application-first-use-cases.md` descrive il Punto 6E.

Questo step introduce i primi command, result, port in e application service per Locations, Cargo e Shipments. Il primo scenario applicativo completo registra due location, registra un cargo, crea una shipment draft, aggiunge item e leg, conferma la shipment e la recupera.

Il Punto 6E dimostra che TruckFlow Manager sta iniziando a funzionare come applicazione a casi d'uso, ma senza introdurre ancora REST API, database, JPA, Spring, security o frontend.

## Nota su `docs/21-application-use-case-hardening.md`

Il documento `21-application-use-case-hardening.md` descrive il Punto 6F.

Questo step rafforza i primi use case applicativi introdotti nel Punto 6E. Aggiunge `CancelShipmentUseCase`, `CancelShipmentCommand` e `CancelShipmentService`, introduce una protezione copy-on-write per i service che mutano shipment, estende la copertura dei test applicativi e verifica che command nulli, dependency nulle, risorse mancanti, duplicati e mutazioni fallite siano gestiti correttamente.

Il Punto 6F non introduce ancora REST API, database, Spring, JPA, security, planning o tracking. Serve a rendere stabile il primo nucleo applicativo prima di estendere i casi d'uso ad altri domini.


## Nota su `docs/22-application-use-case-expansion.md`

Il documento `22-application-use-case-expansion.md` descrive il Punto 6G.

La descrizione corretta del blocco Documents è: registrazione, ricerca, attivazione e archiviazione di documenti logici aziendali. Non introduce update documentale completo, attach di file fisici, generazione PDF, upload, storage, firma digitale, versioning o workflow documentale.

## Nota su `docs/23-application-use-case-expansion-review.md`

Il documento `23-application-use-case-expansion-review.md` descrive il Punto 6H.

Questo step è una review tecnica e documentale dopo Documents: controlla contratti `UseCase`, service, result null-safe, repository in memory e copy-on-write documentale. Non aggiunge nuovi domini e non introduce REST API o database.

## Nota su `docs/24-application-use-cases-expansion-vehicles.md`

Il documento `24-application-use-cases-expansion-vehicles.md` descrive il Punto 6I.

Questo step aggiunge i primi use case Vehicles per registro logico di unità e combinazioni veicolo. Non introduce planning, dispatching, tracking, manutenzione, disponibilità mezzi o assegnazioni operative.

## Nota su `docs/25-application-use-cases-expansion-operational-roles.md`

Il documento `25-application-use-cases-expansion-operational-roles.md` descrive il Punto 6J.

Questo step aggiunge i primi use case Operational Roles per Driver, Mechanic, WarehouseOperator, Dispatcher e Manager. Non introduce turni, availability, payroll o assegnazioni reali.

## Nota su `docs/26-application-operational-use-case-hardening.md`

Il documento `26-application-operational-use-case-hardening.md` descrive il Punto 6K.

Questo step non aggiunge nuovi use case business. Rafforza invece i use case Operational Roles introdotti nel Punto 6J con `ApplicationOperationalUseCaseHardeningTest`, copertura completa dei service di stato per Driver, Mechanic, WarehouseOperator, Dispatcher e Manager, controlli su command nulli, dependency nulle e protezione copy-on-write sulle attivazioni fallite.

La fase mantiene fuori REST API, controller, database, JPA, security, planning, dispatching reale, turni, payroll, tracking e dashboard.

## Nota su `docs/27-application-compliance-base-use-cases.md`

Il documento `27-application-compliance-base-use-cases.md` descrive il Punto 6L.

Questo step aggiunge i primi use case applicativi Compliance base per `ComplianceRequirement`: registrazione, ricerca, attivazione, sospensione, archiviazione e dismissione.

La fase non introduce controlli legali reali, violazioni concrete, audit trail, workflow, REST API, database, JPA o Spring Data.


## Nota su `docs/29-final-roadmap-documentation-alignment.md`

Il documento `29-final-roadmap-documentation-alignment.md` chiude la pulizia documentale dopo il Punto 6M.

Serve a rendere esplicita la roadmap reale dal Punto 1 al Punto 6M, correggere la descrizione del Punto 6G Documents, chiarire il ruolo preparatorio delle dipendenze Spring nel `pom.xml` e formalizzare il concetto di Guided Links nella documentazione digitale.


## Punto 7A — Infrastructure Layer Blueprint

Il Punto 7A apre ufficialmente il nuovo ciclo roadmap dedicato all'infrastructure layer.

Questa fase è solo documentale e architetturale: definisce principi, confini, dipendenze consentite, dipendenze vietate, ruolo degli adapter, ruolo dei repository reali, ruolo dei repository in memory, ruolo di Spring come wiring tecnico e relazione tra Punto 7 e Punto 8.

La roadmap ufficiale del Punto 7 è:

- 7A — Infrastructure Blueprint;
- 7B — Infrastructure Foundation;
- 7C — Spring Wiring Foundation;
- 7D — Persistence Mapping Blueprint;
- 7E — Real Repository Prototype;
- 7F — Repository Expansion;
- 7G — Infrastructure Testing;
- 7H — Infrastructure Review & Freeze.

Il Punto 7A non introduce REST API, controller, DTO web, JSON, security HTTP, database, JPA, repository reali o servizi esterni. Prepara soltanto la base teorica necessaria per procedere in modo ordinato con il Punto 7B.

Il Punto 7A è documentato in [`30-infrastructure-layer-blueprint.md`](30-infrastructure-layer-blueprint.md).

## Punto 7B — Infrastructure Foundation

Il Punto 7B trasforma il blueprint del Punto 7A in una foundation tecnica leggera.

Questa fase crea package infrastrutturali base, eccezioni tecniche, contratti marker per adapter/repository/service, un contratto generico di mapping e un test architetturale dedicato.

Il Punto 7B mantiene fuori database, JPA, Hibernate, Spring Data, controller REST, DTO web, security HTTP, servizi esterni operativi e repository reali completi.

La struttura infrastrutturale ufficiale introdotta è:

- `infrastructure.adapter`;
- `infrastructure.config`;
- `infrastructure.exception`;
- `infrastructure.mapping`;
- `infrastructure.repository`;
- `infrastructure.service`;
- `infrastructure.memory`.

Il package `infrastructure.memory` resta valido e non viene sostituito. I nuovi package preparano gli step successivi del Punto 7.

Il Punto 7B è documentato in [`31-infrastructure-foundation.md`](31-infrastructure-foundation.md).

## Punto 7C — Spring Wiring Foundation

Il Punto 7C introduce Spring solo come motore di wiring tecnico.

La configurazione Spring vive nel layer infrastructure e collega i port.out ai repository in memory e le port.in ai service applicativi già esistenti. L'application layer non viene annotato e il dominio resta completamente framework-free.

`application.yml` mantiene `spring.main.web-application-type: none`, quindi il progetto non avvia ancora un server HTTP e non espone API. REST, DTO web, controller, JPA, Spring Data e security rimangono fuori da questo step.

Il Punto 7C è documentato in [`32-spring-wiring-foundation.md`](32-spring-wiring-foundation.md).

## Punto 7D — Persistence Mapping Blueprint

Il Punto 7D formalizza il blueprint del mapping domain ↔ persistence.

Questa fase aggiunge un catalogo tecnico dei mapping per Locations, Cargo, Shipments, Documents, Vehicles, Operational Roles e Compliance base. Il catalogo descrive ID, codici business, stati, value object, enum, collection e reference senza introdurre ancora database, JPA, Spring Data, schema SQL o repository reali.

Il Punto 7D è documentato in [`33-persistence-mapping-blueprint.md`](33-persistence-mapping-blueprint.md).

## Punto 7E — Real Repository Prototype

Il Punto 7E introduce il primo repository reale prototipale del progetto.

Il dominio pilota è Locations. La patch aggiunge `FileLocationRepository`, `LocationPersistenceRecord`, `LocationPersistenceMapper` e `FileLocationRepositoryPrototypeTest`. Il prototipo usa un file locale come meccanismo tecnico di salvataggio, ma non introduce database, JPA, Spring Data, repository reali per tutti i domini, REST API, controller o security.

Il Punto 7E è documentato in [`34-real-repository-prototype.md`](34-real-repository-prototype.md).

## Punto 7F — Repository Expansion

Il Punto 7F estende il pattern file-backed validato nel Punto 7E.

Dopo il prototipo Locations, questa fase aggiunge repository reali file-backed per `CargoUnit`, `Document` e `ComplianceRequirement`, insieme ai rispettivi persistence record, mapper, codec e test tecnici.

La fase introduce anche supporto file-backed condiviso nel package `infrastructure.repository.file`, così la logica tecnica di lettura, scrittura e codifica non viene duplicata in ogni adapter.

Il Punto 7F mantiene fuori database, JPA, Hibernate, Spring Data, schema SQL, REST API, controller, security, servizi esterni, storage binario documentale, workflow e audit trail.

Il Punto 7F è documentato in [`35-repository-expansion.md`](35-repository-expansion.md).


---

## Punto 7H — Infrastructure Review & Freeze

Il Punto 7H chiude il primo ciclo dell'Infrastructure Layer. Non aggiunge nuovi repository business e non introduce database, JPA, Spring Data, REST API, controller o security.

La fase aggiunge `InfrastructureLayerFinalFreezeTest`, congela lo scope dei repository file-backed validati, conferma che Spring resta wiring non web, conferma che domain/application restano indipendenti dall'infrastructure layer e rende esplicito che il prossimo ciclo naturale sarà il Punto 8 — API Layer.

Il Punto 7H è documentato in [`37-infrastructure-review-freeze.md`](37-infrastructure-review-freeze.md).
