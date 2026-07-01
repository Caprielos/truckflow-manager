# 13 – Domain Rules

## 1. Overview

Questo documento definisce le regole architetturali del dominio puro di TruckFlow.

Le regole qui descritte servono a mantenere il dominio coerente, pulito, disaccoppiato e pronto per essere utilizzato dal livello application.

Questo documento rappresenta la base della **TruckFlow Domain Foundation v1.0**. Dopo la prima review correttiva, le regole qui descritte sono state applicate a interventi mirati su mutazioni di stato, eccezioni custom, codici aziendali, test di catalogo e pulizia del repository.

## 2. Stato della Domain Review Finale

La **Domain Review Finale** è stata avviata e la prima review correttiva del dominio puro è stata completata con otto interventi mirati.

Questa fase ha rafforzato il dominio, ma non sostituisce le future review che saranno necessarie quando verranno introdotti application layer, repository, API, workflow, audit, planning e dispatching.

## 3. Confini del dominio

Ogni dominio deve contenere solo concetti propri.

Un dominio non deve importare aggregate root completi di altri domini.

Sono ammessi riferimenti tramite:

- ID;
- value object stabili;
- concetti astratti.

Esempi corretti:

- `ShipmentItem -> CargoId`;
- `ShipmentLeg -> LocationId`;
- `TripTemplateSegment -> LocationId`;
- `Operational -> UserId`.

Esempi da evitare:

- `ShipmentItem -> CargoUnit`;
- `ShipmentLeg -> Location`;
- `TripTemplateSegment -> Location`.

La regola operativa è: i domini possono conoscersi solo attraverso riferimenti leggeri e stabili, non attraverso aggregate completi.

## 4. Aggregate Root

Ogni aggregate root rappresenta un punto di coerenza del dominio.

Un aggregate root deve:

- controllare invarianti;
- proteggere il proprio stato;
- coordinare le proprie entità interne;
- non orchestrare processi applicativi;
- non dipendere da infrastructure;
- non dipendere da framework.

Aggregate root principali attuali:

- `User`;
- `Driver`;
- `Mechanic`;
- `WarehouseOperator`;
- `Dispatcher`;
- `Manager`;
- `VehicleUnit`;
- `VehicleCombination`;
- `CargoUnit`;
- `Location`;
- `TripTemplate`;
- `Shipment`;
- `Document`;
- `ComplianceRequirement`.

## 5. Value Object e concetti duplicati

I value object rappresentano concetti immutabili del dominio.

Non bisogna unificare value object simili se hanno significato diverso nei rispettivi domini.

I codici aziendali interni devono essere obbligatori quando rappresentano l'identificativo leggibile usato dall'azienda, come `OperationalCode`, `FleetCode`, `CargoCode`, `ShipmentCode`, `DocumentCode` e `ComplianceRequirementCode`.

Anche gli identificativi tecnici o ufficiali del mezzo non devono rimanere primitive generiche. Nel dominio veicoli, `LicensePlate` e `VehicleIdentificationNumber` sono value object dedicati, non semplici `String`.

La stessa regola vale per altri concetti con semantica propria: `LanguageCode`, `UserTheme`, `CountryCode`, `JurisdictionRegion`, `ComplianceJurisdictionScope` e `OperationalScopeCode` non devono essere trattati come stringhe libere.

Esempi:

- `CargoWeights`;
- `ShipmentWeight`;
- `VehicleWeights`;
- `Distance`.

Questi concetti possono restare separati perché rappresentano peso o distanza in contesti diversi.

La regola corretta è: un value object va condiviso solo se rappresenta davvero lo stesso concetto aziendale in più domini.

## 5.1 Test di catalogo e dati statici

I test sui cataloghi statici del dominio non devono essere fragili.

Devono preferire controlli di comportamento e coerenza rispetto a conteggi rigidi.

Esempi corretti:

- verificare che i codici siano univoci;
- verificare che la ricerca per codice funzioni;
- verificare che ogni categoria restituisca solo elementi della propria categoria;
- verificare la presenza delle qualificazioni fondamentali;
- verificare che ogni elemento abbia metadati completi.

Esempio da evitare:

- bloccare il test su un numero totale fisso quando il catalogo è destinato a crescere.


## 5.2 Targhe e identificativi veicolo

La targa è un concetto di dominio e deve essere modellata tramite `LicensePlate`.

Regole:

- `LicensePlate` appartiene alla singola `VehicleUnit`;
- `VehicleCombination` non possiede una `LicensePlate`;
- ogni `VehicleUnit` stradale deve avere una `LicensePlate`;
- le `VehicleUnit` non stradali possono non avere una targa;
- rimorchi e semirimorchi hanno una propria `LicensePlate`, distinta da quella del trattore o della motrice;
- `VehicleIdentificationNumber` rappresenta VIN, telaio o identificativo tecnico della singola unità fisica.

Questa regola evita primitive obsession e protegge la semantica del dominio veicoli.

## 6. Invarianti

Gli invarianti devono essere chiari, espliciti e localizzati nel dominio.

Devono essere protetti nel costruttore o nei metodi di modifica dell'aggregate root, dell'entità o del value object che possiede la regola.

A regime, gli invarianti devono lanciare eccezioni di dominio dedicate.

Le eccezioni standard Java devono essere evitate nei punti di validazione del dominio quando esiste un'eccezione custom specifica. Durante evoluzioni future possono essere accettate solo temporaneamente, se localizzate e sostituite nella patch di refactoring del relativo dominio.

## 7. Entità

Ogni entità deve avere:

- identità chiara;
- responsabilità limitata;
- nessuna logica di orchestrazione;
- nessuna dipendenza da infrastructure;
- nessuna dipendenza da application;
- nessuna dipendenza da framework.

Le entità non devono decidere processi completi. Devono proteggere il proprio stato e le proprie regole.

## 8. Regole di business

Le regole di business fondamentali devono stare nel dominio puro.

Non devono essere duplicate tra più classi e non devono essere mescolate con:

- controller;
- repository implementati;
- database;
- API;
- file;
- storage;
- workflow applicativi;
- notifiche;
- servizi esterni.

La regola architetturale è:

- il dominio contiene le regole fondamentali;
- l'application layer orchestra i casi d'uso;
- l'infrastructure layer collega il sistema al mondo esterno.

## 9. Eccezioni di dominio

Le eccezioni custom sono ora parte della regola ordinaria del dominio.

Sono state introdotte gradualmente, dominio per dominio, e devono essere preferite alle eccezioni standard Java nei punti di validazione e invarianti.

La struttura base prevista è:

- `DomainException`;
- `DomainValidationException`;
- `InvariantViolationException`.

Le eccezioni specifiche devono stare nel package `exceptions` del rispettivo dominio.

Strategia consigliata:

1. definire eccezioni base condivise;
2. introdurre eccezioni specifiche per dominio;
3. sostituire gradualmente `IllegalArgumentException` e `IllegalStateException`;
4. aggiornare i test;
5. verificare con `mvn clean test`.

## 10. Struttura eccezioni prevista

```text
it.gabriele.truckflow.domain.shared.exceptions
├── DomainException
├── DomainValidationException
└── InvariantViolationException

it.gabriele.truckflow.domain.users.exceptions
└── InvalidUserException

it.gabriele.truckflow.domain.qualifications.exceptions
└── InvalidQualificationException

it.gabriele.truckflow.domain.operational.exceptions
├── InvalidDriverException
├── InvalidMechanicException
├── InvalidWarehouseOperatorException
├── InvalidDispatcherException
└── InvalidManagerException

it.gabriele.truckflow.domain.vehicles.exceptions
├── InvalidVehicleException
└── InvalidVehicleCombinationException

it.gabriele.truckflow.domain.cargo.exceptions
└── InvalidCargoException

it.gabriele.truckflow.domain.locations.exceptions
└── InvalidLocationException

it.gabriele.truckflow.domain.triptemplates.exceptions
├── InvalidTripTemplateException
└── InvalidTripTemplateSegmentException

it.gabriele.truckflow.domain.shipments.exceptions
├── InvalidShipmentException
├── InvalidShipmentItemException
└── InvalidShipmentLegException

it.gabriele.truckflow.domain.documents.exceptions
└── InvalidDocumentException

it.gabriele.truckflow.domain.compliance.exceptions
└── InvalidComplianceRequirementException
```

`ComplianceViolationException` non viene introdotta in questa fase, perché una violazione di compliance è un risultato concreto di un controllo. Il dominio attuale modella solo requisiti astratti tramite `ComplianceRequirement`.

## 11. Nomenclatura

Ogni dominio deve usare un vocabolario coerente.

I nomi devono essere:

- chiari;
- parlanti;
- specifici;
- non ambigui;
- coerenti con il contesto in cui vengono usati.

Esempi di nomi corretti:

- `Shipment`;
- `CargoUnit`;
- `TripTemplate`;
- `VehicleUnit`;
- `VehicleCombination`;
- `Document`;
- `ComplianceRequirement`.

## 12. Dipendenze

Il dominio puro non deve dipendere da:

- Spring;
- JPA;
- Hibernate;
- Lombok;
- application;
- infrastructure;
- controller;
- repository implementati;
- database;
- file system;
- API esterne.

Sono ammessi:

- Java standard library;
- ID di altri domini, quando necessario;
- value object stabili;
- concetti astratti.


## 12.1 File locali, IDE e artefatti generati

Il repository non deve contenere file locali dell'IDE o artefatti generati dalla build.

Devono rimanere fuori dal versionamento:

- `.idea/`;
- `target/`;
- `.DS_Store`;
- `__MACOSX/`;
- file `.patch`;
- script locali `.sh` generati per attività temporanee.

Questi file dipendono dall'ambiente locale dello sviluppatore o da operazioni temporanee e non rappresentano codice sorgente, documentazione ufficiale o regole di dominio.

La regola operativa è: il repository deve contenere solo codice sorgente, test, configurazioni condivise realmente necessarie e documentazione ufficiale.

## 12.2 Primitive obsession e Value Object leggeri

Non ogni `String` è sbagliata. Campi descrittivi come `name`, `description`, `notes`, `title`, `statement` o `expectedCondition` possono restare testo libero.

Una `String` diventa problematica quando rappresenta un concetto di dominio con regole proprie, come paese, scope, lingua, tema, targa, VIN o codice operativo.

In questi casi il dominio deve preferire value object o enum dedicati.

La logica di configurazione o interfaccia utente non deve entrare nel dominio puro. Per esempio, la scelta futura "Europa -> lista nazioni -> Italia come paese predefinito" appartiene ad application layer, configurazione aziendale o UI, non a `ComplianceJurisdiction`.

## 13. Checklist finale

Prima di chiudere la review del dominio puro verificare:

- nessuna classe temporanea;
- nessuna classe vuota;
- nessun TODO lasciato senza motivo;
- nessuna dipendenza da framework;
- nessuna logica infrastrutturale nel dominio;
- nessuna logica applicativa nel dominio;
- aggregate root corretti;
- value object coerenti;
- invarianti chiari;
- eccezioni pulite;
- nomenclatura coerente;
- test verdi;
- test architetturali sui confini tra domini verdi;
- test contrattuali dei value object principali verdi;
- documentazione aggiornata.

## 14. Roadmap successiva

La prima review correttiva del dominio puro è stata completata con otto interventi mirati, documentati in [`14-domain-review-patches.md`](14-domain-review-patches.md). La revisione finale della test suite del dominio puro è documentata in [`15-domain-test-suite-review.md`](15-domain-test-suite-review.md).

La roadmap successiva consigliata è:

1. mantenere verdi formattazione e test con `mvn spotless:apply` e `mvn clean test`;
2. evitare nuove eccezioni standard Java nei punti di validazione del dominio;
3. aggiornare `docs/13-domain-rules.md` quando cambiano regole architetturali;
4. aggiornare `docs/14-domain-review-patches.md` quando vengono eseguiti nuovi interventi correttivi rilevanti;
5. aggiornare `docs/15-domain-test-suite-review.md` quando cambiano copertura, confini o strategia dei test del dominio puro;
6. iniziare il livello `application` con use case piccoli, porte in ingresso e porte repository in uscita;
7. introdurre `infrastructure.memory` solo come adattatore tecnico, senza riportare logica applicativa o infrastrutturale nel dominio;
8. rimandare API REST, database e integrazioni esterne finché application layer e use case principali non sono stabili.
