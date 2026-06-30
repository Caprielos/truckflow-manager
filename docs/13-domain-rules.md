# 13 – Domain Rules

## 1. Overview

Questo documento definisce le regole architetturali del dominio puro di TruckFlow.

Le regole qui descritte servono a mantenere il dominio coerente, pulito, disaccoppiato e pronto per essere utilizzato dal livello application.

Questo documento rappresenta la base della **TruckFlow Domain Foundation v1.0**: la fondazione del dominio puro è stata definita, mentre la review concreta dominio per dominio e il refactoring graduale delle eccezioni custom rimangono attività successive.

## 2. Stato della Domain Review Finale

La **Domain Review Finale** è avviata e la roadmap è approvata.

Non va considerata ancora completata, perché la review dominio per dominio deve essere eseguita concretamente su classi, aggregate root, value object, invarianti, nomenclatura, dipendenze e test.

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

Esempi:

- `CargoWeights`;
- `ShipmentWeight`;
- `VehicleWeights`;
- `Distance`.

Questi concetti possono restare separati perché rappresentano peso o distanza in contesti diversi.

La regola corretta è: un value object va condiviso solo se rappresenta davvero lo stesso concetto aziendale in più domini.

## 6. Invarianti

Gli invarianti devono essere chiari, espliciti e localizzati nel dominio.

Devono essere protetti nel costruttore o nei metodi di modifica dell'aggregate root, dell'entità o del value object che possiede la regola.

A regime, gli invarianti devono lanciare eccezioni di dominio dedicate.

Durante la fase MVP sono accettabili eccezioni standard Java, come `IllegalArgumentException` e `IllegalStateException`, purché siano usate in modo coerente e localizzato.

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

Le eccezioni custom sono un miglioramento enterprise, ma non sono un obbligo immediato.

Devono essere introdotte gradualmente, dominio per dominio, senza modificare tutto insieme.

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
- documentazione aggiornata.

## 14. Roadmap successiva

La roadmap consigliata è:

1. approvazione roadmap Domain Review;
2. review concreta dominio per dominio;
3. introduzione graduale eccezioni custom;
4. aggiornamento di `docs/13-domain-rules.md` quando cambiano le regole;
5. pulizia finale del dominio puro;
6. inizio del livello application.

Ordine consigliato per la review:

1. `domain.users`;
2. `domain.qualifications`;
3. `domain.operational`;
4. `domain.vehicles`;
5. `domain.cargo`;
6. `domain.locations`;
7. `domain.triptemplates`;
8. `domain.shipments`;
9. `domain.documents`;
10. `domain.compliance`.
