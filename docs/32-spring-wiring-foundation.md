> Aggiornamento 7E: il wiring Spring resta ancora sul profilo `memory`. Il primo repository reale prototipale per Locations viene introdotto come adapter separato e non sostituisce automaticamente gli in-memory repository.

# Punto 7C — Spring Wiring Foundation


> Aggiornamento 7D: il wiring Spring del Punto 7C resta invariato. Il Punto 7D aggiunge solo blueprint di mapping nel package `infrastructure.mapping` e non modifica i bean, i profili Spring o il runtime non web.

Questo documento descrive il **Punto 7C — Spring Wiring Foundation** di TruckFlow Manager.

Il Punto 7C introduce Spring in modo **controllato, tecnico e non web**. Lo scopo non è creare API, controller, database o sicurezza HTTP. Lo scopo è solo permettere al progetto di comporre repository in memory e use case applicativi tramite configurazioni Spring esplicite, lasciando intatti i confini già stabiliti da dominio, application layer e infrastructure foundation.

## Stato da cui partiamo

Prima del Punto 7C il progetto contiene già:

- dominio puro completato, testato e documentato;
- application layer completato e congelato con il Punto 6M;
- repository port definiti nell'application layer;
- repository in memory già presenti sotto `infrastructure.memory`;
- Punto 7A documentato come blueprint dell'Infrastructure Layer;
- Punto 7B implementato come foundation infrastrutturale leggera;
- nessun controller REST;
- nessuna entity JPA;
- nessun database reale;
- nessuna Spring Data repository;
- nessuna security HTTP.

Il Punto 7C non cambia questi confini. Aggiunge solo il primo livello di **wiring tecnico Spring**.

## Obiettivo del Punto 7C

L'obiettivo del Punto 7C è rispondere a questa domanda:

> Come possiamo avviare TruckFlow Manager con Spring, componendo i use case esistenti e gli adapter in memory, senza contaminare domain e application layer?

La risposta è:

- un entry point Spring Boot tecnico;
- un file `application.yml` minimale;
- un profilo Spring `memory`;
- configurazioni bean esplicite nel package `infrastructure.config.spring`;
- nessuna annotazione Spring nel dominio;
- nessuna annotazione Spring nell'application layer;
- nessun web server operativo;
- nessun repository reale;
- nessuna persistenza JPA.

## Struttura introdotta

Il Punto 7C introduce la seguente struttura:

```text
src/main/java/it/gabriele/truckflow
├── TruckFlowApplication.java
└── infrastructure/config/spring
    ├── SpringProfileNames.java
    ├── TruckFlowSpringWiringConfiguration.java
    ├── InMemoryRepositorySpringConfiguration.java
    ├── LocationUseCaseSpringConfiguration.java
    ├── CargoUseCaseSpringConfiguration.java
    ├── ShipmentUseCaseSpringConfiguration.java
    ├── DocumentUseCaseSpringConfiguration.java
    ├── VehicleUseCaseSpringConfiguration.java
    ├── OperationalUseCaseSpringConfiguration.java
    ├── ComplianceUseCaseSpringConfiguration.java
    └── package-info.java
```

Introduce anche:

```text
src/main/resources/application.yml
src/test/java/it/gabriele/truckflow/infrastructure/config/spring/SpringWiringFoundationTest.java
```

Questa struttura è volutamente esplicita. Non usa component scan su application service o repository in memory. Questo rende chiaro dove Spring entra nel progetto e impedisce al framework di diventare invisibile nei layer centrali.

## `TruckFlowApplication`

`TruckFlowApplication` è l'entry point tecnico Spring Boot del progetto.

La classe appartiene al package root `it.gabriele.truckflow`, ma scansiona solo la configurazione infrastrutturale Spring:

```text
it.gabriele.truckflow.infrastructure.config.spring
```

Questo è importante perché:

- evita di cercare componenti Spring nel dominio;
- evita di cercare componenti Spring nell'application layer;
- impedisce di trasformare i service applicativi in `@Service`;
- mantiene Spring come dettaglio dell'infrastructure layer.

`TruckFlowApplication` non rappresenta business logic. È solo bootstrap tecnico.

## `application.yml`

Il Punto 7C aggiunge una configurazione minimale:

```yaml
spring:
  application:
    name: truckflow-manager
  main:
    web-application-type: none
  profiles:
    active: memory

truckflow:
  infrastructure:
    profile: memory
```

La scelta più importante è:

```yaml
web-application-type: none
```

Questo significa che Spring Boot può avviare il contesto applicativo, ma non avvia un server web.

Quindi nel Punto 7C restano esclusi:

- controller REST;
- endpoint HTTP;
- DTO web;
- JSON API;
- Swagger operativo;
- security HTTP;
- autenticazione;
- autorizzazione.

Il profilo `memory` collega i port.out ai repository in memory già esistenti.

## Package `infrastructure.config.spring`

Il package `infrastructure.config.spring` è l'unico punto in cui Spring viene usato per comporre il sistema.

Questo package può dipendere da:

- application port in;
- application port out;
- application service concreti;
- repository in memory;
- Spring configuration API.

Questo package non deve contenere:

- regole di dominio;
- flussi applicativi nuovi;
- controller;
- entity JPA;
- mapper persistence concreti;
- integrazioni esterne;
- security configuration.

## `SpringProfileNames`

`SpringProfileNames` centralizza i nomi dei profili Spring.

Nel Punto 7C viene formalizzato il profilo:

```text
memory
```

Questo profilo indica che l'infrastruttura attiva usa gli adapter in memory. In futuro potranno essere aggiunti profili come `local`, `test` o `production`, ma non vengono ancora configurati in questa fase.

## `TruckFlowSpringWiringConfiguration`

`TruckFlowSpringWiringConfiguration` è la configurazione root del wiring Spring.

Il suo compito è importare le configurazioni specifiche:

- repository in memory;
- use case Locations;
- use case Cargo;
- use case Shipments;
- use case Documents;
- use case Vehicles;
- use case Operational Roles;
- use case Compliance.

Questa classe non costruisce business logic. Si limita a comporre bean.

## `InMemoryRepositorySpringConfiguration`

`InMemoryRepositorySpringConfiguration` collega i port.out dell'application layer agli adapter in memory.

Esempi concettuali:

- `LocationRepository` → `InMemoryLocationRepository`;
- `CargoUnitRepository` → `InMemoryCargoUnitRepository`;
- `ShipmentRepository` → `InMemoryShipmentRepository`;
- `DocumentRepository` → `InMemoryDocumentRepository`;
- `VehicleUnitRepository` → `InMemoryVehicleUnitRepository`;
- `DriverRepository` → `InMemoryDriverRepository`;
- `ComplianceRequirementRepository` → `InMemoryComplianceRequirementRepository`.

Questa configurazione è attiva solo con il profilo:

```text
memory
```

Questo mantiene chiaro che l'infrastruttura reale non è ancora stata introdotta. I repository in memory restano validi per test, sviluppo locale e bootstrap tecnico controllato.

## Configurazioni use case

Il Punto 7C aggiunge configurazioni distinte per contesto applicativo:

- `LocationUseCaseSpringConfiguration`;
- `CargoUseCaseSpringConfiguration`;
- `ShipmentUseCaseSpringConfiguration`;
- `DocumentUseCaseSpringConfiguration`;
- `VehicleUseCaseSpringConfiguration`;
- `OperationalUseCaseSpringConfiguration`;
- `ComplianceUseCaseSpringConfiguration`.

Queste configurazioni creano bean per le port.in già esistenti.

Esempio concettuale:

```text
RegisterLocationUseCase -> RegisterLocationService
FindLocationUseCase     -> FindLocationService
```

La regola è sempre la stessa:

- il bean esposto è la port.in;
- l'implementazione concreta è il service applicativo;
- le dipendenze sono port.out;
- Spring vive solo nella configurazione infrastrutturale.

## Perché non usare `@Service` negli application service

Una scelta importante del Punto 7C è non annotare i service applicativi con `@Service`.

Motivo:

- gli application service appartengono all'application layer;
- l'application layer deve restare indipendente dal framework;
- Spring è un dettaglio tecnico esterno;
- il wiring deve vivere nell'infrastructure layer.

Quindi invece di scrivere:

```text
@Service
class RegisterLocationService
```

si mantiene il service puro e lo si compone da infrastructure:

```text
@Bean
RegisterLocationUseCase registerLocationUseCase(...)
```

Questa scelta mantiene l'architettura pulita.

## Cosa è stato collegato

Il Punto 7C collega tramite Spring:

- repository in memory attuali;
- use case Locations;
- use case Cargo;
- use case Shipments;
- use case Documents;
- use case Vehicles;
- use case Operational Roles;
- use case Compliance.

Questo significa che il progetto può avviare un application context tecnico e ottenere i use case tramite Spring, ma senza introdurre delivery layer.

## Cosa NON è stato introdotto

Il Punto 7C non introduce:

- REST API;
- controller;
- DTO web;
- endpoint HTTP;
- JSON API;
- database;
- JPA;
- Hibernate;
- Spring Data;
- entity persistence;
- repository reali;
- transazioni;
- security HTTP;
- JWT;
- audit trail operativo;
- workflow;
- servizi esterni;
- email service;
- document generation;
- file storage reale;
- nuovi use case business.

Queste esclusioni sono intenzionali. Il Punto 7C deve essere solo wiring, non infrastruttura completa.

## Test del Punto 7C

Il test `SpringWiringFoundationTest` verifica che:

- `TruckFlowApplication` esista;
- `application.yml` configuri runtime non web;
- il profilo `memory` sia esplicito;
- il contesto Spring possa essere avviato con `TruckFlowSpringWiringConfiguration`;
- i repository in memory siano disponibili come port.out;
- i use case principali siano disponibili come port.in;
- domain e application layer non contengano dipendenze Spring;
- l'infrastructure layer non abbia introdotto controller, JPA, Spring Data o security;
- la documentazione del Punto 7C sia presente.

Il test è architetturale e tecnico. Non ridefinisce le regole business già coperte dai test domain/application.

## Relazione con il Punto 7B

Il Punto 7B ha creato la foundation infrastrutturale:

- package;
- eccezioni;
- marker;
- profili tecnici;
- contratto generico di mapping.

Il Punto 7C usa quella foundation per introdurre il wiring Spring.

Il passaggio è coerente:

```text
7B = struttura e linguaggio infrastrutturale
7C = composizione tecnica con Spring
```

## Relazione con il Punto 7D

Dopo il Punto 7C, il Punto 7D introduce:

```text
7D — Persistence Mapping Blueprint
```

Il Punto 7D non parte con JPA o database completi. Definisce invece in modo ordinato:

- regole di mapping domain ↔ persistence;
- blueprint dei modelli persistenti futuri;
- catalogo dei contesti applicativi attivi;
- vincoli per non rompere il dominio;
- strategia per un futuro repository reale pilota.

Il Punto 7C prepara il terreno perché i futuri adapter possano essere collegati tramite Spring senza cambiare application layer. Il Punto 7D usa questa base per descrivere cosa dovrà essere mappato, prima di scegliere una tecnologia concreta di persistenza.

## Stato finale del Punto 7C

Alla fine del Punto 7C il progetto ha:

- Spring Boot come bootstrap tecnico;
- runtime non web;
- profilo `memory` attivo;
- repository in memory cablati tramite Spring;
- use case applicativi cablati tramite Spring;
- domain layer ancora puro;
- application layer ancora framework-free;
- nessun database;
- nessun controller;
- nessuna API REST;
- nessuna security.

## Sintesi

Il Punto 7C introduce Spring nel modo più prudente possibile:

> Spring non entra nel dominio, non entra nell'application layer e non apre API.
> Spring viene usato solo per comporre tecnicamente ciò che esiste già.

Questa scelta permette di avanzare verso un'infrastruttura reale senza rompere l'architettura costruita fino al Punto 6M.
