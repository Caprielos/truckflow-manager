# 00 — Software Engineering Overview — TruckFlow Manager

> Documento ufficiale di Ingegneria del Software per il progetto **TruckFlow Manager**.
> Riferimenti metodologici: **ISO/IEC 25010** per la qualità del prodotto software e **ISO/IEC 12207** per i processi del ciclo di vita del software.
> Nota: il presente documento è strutturato secondo tali riferimenti, ma non costituisce certificazione ISO formale, che richiederebbe audit esterno indipendente.

---

## 1. Titolo del progetto

**TruckFlow Manager**
Sistema Java enterprise-oriented per la gestione progressiva di concetti logistici, trasporto merci, ruoli operativi, veicoli, carichi, sedi, spedizioni, documenti, requisiti di compliance e futura esposizione API.

### Identificazione tecnica

| Voce | Valore |
|---|---|
| Nome progetto | TruckFlow Manager |
| GroupId Maven | `it.gabriele.truckflow` |
| ArtifactId Maven | `truckflow-manager` |
| Versione | `0.1.0-SNAPSHOT` |
| Linguaggio | Java 21 |
| Build tool | Maven |
| Root package | `it.gabriele.truckflow` |
| Main Java files analizzati | 505 |
| Test Java files analizzati | 43 |
| Package Java reali analizzati | 103 |
| Stato roadmap | Punto 7 completato; Punto 8A API Layer Blueprint formalizzato; prossimo Punto 8B |

---

## 2. Executive Summary

TruckFlow Manager è un progetto software costruito con approccio incrementale, orientato alla qualità architetturale e alla separazione rigorosa delle responsabilità. Il progetto modella un dominio logistico articolato, evitando di partire da un semplice CRUD o da una struttura database-first.

La direzione architetturale è coerente con Clean Architecture e Hexagonal Architecture:

```text
API Layer futuro
        ↓
Application Layer
        ↓
Domain Layer

Infrastructure Layer implementa dettagli tecnici e adapter,
senza contaminare domain e application.
```

Lo stato attuale è maturo per quanto riguarda la base interna del sistema:

- **Domain Layer completato**: bounded context, entità, value object, invarianti, eccezioni e test di dominio.
- **Application Layer completato**: command, result, inbound port, outbound repository port e use case service.
- **Infrastructure Layer completato**: Spring wiring non-web, repository in-memory, mapping persistence, repository file-backed selezionati e test infrastrutturali.
- **API Layer non ancora implementato come delivery runtime**: il Punto 8A formalizza blueprint, versionamento, regole architetturali e test future-proof; controller, DTO, endpoint reali, error handling runtime e OpenAPI restano nei punti successivi.

Il progetto non include ancora database relazionale, JPA, Spring Data, security HTTP/JWT, frontend, workflow, dashboard, audit trail o integrazioni esterne. Tali esclusioni sono intenzionali e risultano coerenti con una roadmap che privilegia stabilità, testabilità e riduzione della complessità prematura.

---

## 3. Problema reale che il sistema risolve

Le organizzazioni di trasporto e logistica devono gestire informazioni eterogenee, interdipendenti e spesso soggette a vincoli normativi o operativi:

- veicoli e combinazioni veicolari;
- ruoli operativi, conducenti, dispatcher, meccanici, manager e operatori di magazzino;
- carichi, requisiti di trasporto, caratteristiche tecniche e compatibilità;
- sedi, hub, punti di carico/scarico e indirizzi;
- spedizioni, tratte, item, metriche e requisiti;
- documenti tecnici, operativi, amministrativi e di compliance;
- requisiti normativi, giurisdizioni, scadenze e controlli;
- futura esposizione API verso sistemi esterni, UI o integrazioni enterprise.

Un sistema costruito senza separazione architetturale rischia di mescolare regole di business, persistenza, controller, DTO, sicurezza e configurazioni tecniche. TruckFlow Manager risolve questo problema costruendo prima un nucleo stabile e framework-independent, per poi aggiungere progressivamente i layer tecnici.

---

## 4. Obiettivi del sistema

Gli obiettivi tecnici e funzionali del sistema sono:

1. Modellare il dominio logistico in modo realistico e non banale.
2. Separare regole di business, orchestrazione applicativa e dettagli infrastrutturali.
3. Mantenere il Domain Layer indipendente da Spring, JPA, REST, database e frontend.
4. Esporre use case applicativi tramite port in e command/result.
5. Definire repository port outbound indipendenti dalle implementazioni tecniche.
6. Fornire adapter in-memory per test e runtime locale.
7. Fornire repository file-backed limitati ai contesti già sufficientemente stabili.
8. Preparare il futuro API Layer senza anticipare controller, DTO o sicurezza; il Punto 8A introduce solo blueprint e test architetturale.
9. Mantenere documentazione storica, semplice, professionale e digitale allineata al codice.
10. Proteggere i confini architetturali tramite test e freeze progressivi.

---

## 5. Contesto applicativo e scenario d’uso

TruckFlow Manager è pensato come base enterprise per una piattaforma di gestione logistica e trasporto merci.

### Scenario attuale

Nello stato corrente, il sistema opera principalmente come fondazione backend non-web:

- modella concetti di dominio;
- espone casi d’uso applicativi;
- configura repository in-memory;
- supporta repository file-backed per contesti selezionati;
- usa Spring come meccanismo tecnico di wiring;
- mantiene runtime non web tramite `spring.main.web-application-type: none`.

### Scenario futuro

Con il Punto 8, il sistema potrà diventare esponibile tramite API REST versionate:

```text
/api/v1/locations
/api/v1/documents
/api/v1/cargo-units
/api/v1/compliance-requirements
```

Il primo candidato API previsto è **Locations**, perché è un bounded context stabile, con regole chiare, basso rischio workflow e repository file-backed già validato.

---

## 6. Attori e stakeholder

| Attore / Stakeholder | Interesse principale | Stato nel progetto |
|---|---|---|
| CTO / Revisore architetturale | Valutare qualità strutturale, manutenibilità, testabilità e roadmap tecnica | Supportato dalla documentazione e dai test architetturali |
| Manager logistico | Monitorare sedi, veicoli, ruoli, carichi, spedizioni e compliance | Modellato a livello di dominio; UI/API future |
| Dispatcher | Coordinare operazioni, risorse e spedizioni | Ruolo modellato nel dominio e nei use case operational |
| Driver | Figura operativa soggetta a qualifiche, stato e requisiti | Modellata nel dominio operational |
| Mechanic | Figura tecnica per manutenzione e operatività futura | Modellata nel dominio operational |
| Warehouse Operator | Figura operativa per magazzino, carico/scarico e sedi | Modellata nel dominio operational |
| Compliance Manager | Gestire requisiti normativi, documenti e giurisdizioni | Modellato tramite compliance e documents |
| Sviluppatore / Maintainer | Evolvere il sistema senza violare i layer | Supportato da package structure, test e docs |
| Cliente corporate | Valutare capacità evolutiva e solidità tecnica | Supportato dal documento enterprise e roadmap |

---

## 7. Requisiti funzionali completi

I requisiti funzionali sotto riportati sono derivati dai package Java reali, dalle port applicative, dai use case presenti e dalla documentazione di progetto.

| ID | Area | Requisito funzionale | Stato |
|---|---|---|---|
| RF-01 | Locations | Supportare i casi d’uso applicativi: FindLocation, RegisterLocation. | Implementato nell’Application Layer |
| RF-02 | Cargo | Supportare i casi d’uso applicativi: FindCargoUnit, RegisterCargoUnit. | Implementato nell’Application Layer |
| RF-03 | Documents | Supportare i casi d’uso applicativi: ActivateDocument, ArchiveDocument, FindDocument, RegisterDocument. | Implementato nell’Application Layer |
| RF-04 | Compliance | Supportare i casi d’uso applicativi: ActivateComplianceRequirement, ArchiveComplianceRequirement, DiscontinueComplianceRequirement, FindComplianceRequirement, RegisterComplianceRequirement, SuspendComplianceRequirement. | Implementato nell’Application Layer |
| RF-05 | Operational Roles | Supportare i casi d’uso applicativi: ActivateDispatcher, ActivateDriver, ActivateManager, ActivateMechanic, ActivateWarehouseOperator, FindDispatcher, FindDriver, FindManager, FindMechanic, FindWarehouseOperator, MarkNotEligibleDispatcher, MarkNotEligibleDriver, MarkNotEligibleManager, MarkNotEligibleMechanic, MarkNotEligibleWarehouseOperator, RegisterDispatcher, RegisterDriver, RegisterManager, RegisterMechanic, RegisterWarehouseOperator, SuspendDispatcher, SuspendDriver, SuspendManager, SuspendMechanic, SuspendWarehouseOperator. | Implementato nell’Application Layer |
| RF-06 | Vehicles | Supportare i casi d’uso applicativi: ActivateVehicleUnit, DismissVehicleUnit, FindVehicleCombination, FindVehicleUnit, MarkVehicleUnitOutOfService, RegisterVehicleCombination, RegisterVehicleUnit, SuspendVehicleUnit. | Implementato nell’Application Layer |
| RF-07 | Shipments | Supportare i casi d’uso applicativi: AddShipmentItem, AddShipmentLeg, CancelShipment, ConfirmShipment, CreateShipment, FindShipment. | Implementato nell’Application Layer |
| RF-08 | Users | modellare utenti, profili, contatti, preferenze, ruoli, permessi e stato utente. | Implementato nel Domain Layer; use case dedicati non ancora esposti |
| RF-09 | Qualifications | modellare catalogo, categoria e definizione delle qualifiche professionali. | Implementato nel Domain Layer; use case dedicati non ancora esposti |
| RF-10 | TripTemplates | modellare template di viaggio, segmenti, distanza, tipo rotta e stato del template. | Implementato nel Domain Layer; use case dedicati non ancora esposti |
| RF-11 | Repository ports | Esporre contratti repository outbound per Cargo, Compliance, Documents, Locations, Operational, Shipments e Vehicles. | Implementato |
| RF-12 | Repository in-memory | Fornire adapter in-memory per test, sviluppo locale e wiring non web. | Implementato |
| RF-13 | Repository file-backed | Fornire persistenza file-backed controllata per Locations, Cargo, Documents e Compliance. | Implementato in scope limitato |
| RF-14 | API REST | Esporre endpoint HTTP versionati `/api/v1` tramite futuro API Layer. | Pianificato da 8C in poi |
| RF-15 | Error handling API | Definire mapping errore applicativo/dominio verso risposte HTTP strutturate. | Pianificato 8E |

### Repository outbound implementati come contratti applicativi

| Area | Repository port |
|---|---|
| Cargo | `CargoUnitRepository` |
| Compliance | `ComplianceRequirementRepository` |
| Documents | `DocumentRepository` |
| Locations | `LocationRepository` |
| Operational | `DriverRepository`, `DispatcherRepository`, `ManagerRepository`, `MechanicRepository`, `WarehouseOperatorRepository` |
| Shipments | `ShipmentRepository` |
| Vehicles | `VehicleUnitRepository`, `VehicleCombinationRepository` |

### Repository concreti disponibili

| Categoria | Implementazioni |
|---|---|
| In-memory | Cargo, Compliance, Documents, Locations, Operational Roles, Shipments, Vehicles |
| File-backed | Locations, Cargo, Documents, Compliance |
| Non ancora file-backed | Shipments, Vehicles, Operational Roles |

---

## 8. Requisiti non funzionali conformi a ISO/IEC 25010

La seguente analisi usa le caratteristiche di qualità del software come riferimento metodologico. Lo stato indicato rappresenta il grado di copertura attuale nel progetto.

### 8.1 Affidabilità

| Aspetto | Valutazione |
|---|---|
| Maturità | Il progetto contiene test di dominio, application, infrastructure, repository e freeze. |
| Disponibilità | Non valutabile in produzione perché il sistema non è ancora distribuito come servizio web. |
| Tolleranza ai guasti | Presente a livello di eccezioni di dominio, application e infrastructure; assente gestione runtime enterprise. |
| Recuperabilità | Limitata ai repository file-backed; non esistono ancora DB transaction, backup o recovery strategy. |

**Conclusione**: affidabilità strutturale buona per la fase attuale; affidabilità operativa da progettare con API, persistenza e deployment.

### 8.2 Sicurezza

| Aspetto | Valutazione |
|---|---|
| Autenticazione | Non ancora implementata. |
| Autorizzazione | Concetti domain-level come ruoli e permessi esistono, ma non esiste security HTTP. |
| Protezione dati | Non ancora implementata a livello storage/trasporto. |
| Auditability | Pianificata, non implementata. |
| Isolamento layer | Buono: domain/application non dipendono da web/security. |

**Conclusione**: sicurezza applicativa HTTP non presente per scelta di roadmap; modello predisposto, ma Punto 8 e cicli successivi dovranno introdurre security formalizzata.

### 8.3 Manutenibilità

| Aspetto | Valutazione |
|---|---|
| Modularità | Alta: 103 package reali organizzati per layer e bounded context. |
| Analizzabilità | Buona: documentazione simple/professional/old_style/digital presente. |
| Modificabilità | Buona: use case, port e repository sono separati. |
| Testabilità | Buona: 43 file test e test architetturali. |
| Riusabilità | Buona: value object, command, result, port e repository adapter favoriscono evoluzione controllata. |

**Conclusione**: la manutenibilità è il punto di qualità più forte del progetto.

### 8.4 Scalabilità

| Aspetto | Valutazione |
|---|---|
| Scalabilità architetturale | Buona: i layer permettono crescita verso API, DB e integrazioni. |
| Scalabilità runtime | Non ancora valutabile: assenti API, deployment e database. |
| Scalabilità funzionale | Buona: bounded context separati e roadmap progressiva. |
| Scalabilità organizzativa | Buona: package e documentazione permettono onboarding tecnico. |

**Conclusione**: scalabilità architetturale buona; scalabilità operativa futura da validare.

### 8.5 Efficienza delle prestazioni

| Aspetto | Valutazione |
|---|---|
| Performance runtime | Non misurata in ambiente produttivo. |
| Overhead architetturale | Controllato: layer espliciti ma senza infrastruttura pesante. |
| Persistenza | File-backed limitato; nessuna ottimizzazione DB ancora richiesta. |
| Benchmark | Non presenti. |

**Conclusione**: prestazioni non ancora oggetto di misurazione formale; accettabile per fase foundation.

### 8.6 Usabilità

| Aspetto | Valutazione |
|---|---|
| Usabilità utente finale | Non valutabile: manca frontend/API user-facing. |
| Usabilità sviluppatore | Buona: docs, package explorer, comandi Maven e test. |
| Usabilità documentale | Buona e in miglioramento: Digital Reader statico con documenti renderizzati. |

**Conclusione**: usabilità tecnica buona; usabilità prodotto finale futura.

### 8.7 Compatibilità

| Aspetto | Valutazione |
|---|---|
| Coesistenza | Buona: runtime non-web e file-backed non richiedono servizi esterni. |
| Interoperabilità | Pianificata tramite API Layer e future integrazioni. |
| Contratti esterni | Non ancora presenti. |

**Conclusione**: compatibilità interna buona; interoperabilità esterna pianificata.

### 8.8 Portabilità

| Aspetto | Valutazione |
|---|---|
| Portabilità codice | Buona: Java 21 e Maven. |
| Portabilità ambiente | Buona: nessun DB obbligatorio nello stato attuale. |
| Installabilità | Maven-based; richiede JDK 21. |
| Sostituibilità infrastruttura | Buona: repository port permettono sostituzione adapter. |

**Conclusione**: portabilità buona nella fase attuale.

---

## 9. Processi di sviluppo conformi a ISO/IEC 12207

### 9.1 Analisi dei requisiti

Il progetto mostra una progressiva identificazione dei requisiti attraverso:

- definizione dei bounded context;
- documentazione storica dei punti progettuali;
- separazione tra requisiti implementati e requisiti rimandati;
- formalizzazione della roadmap;
- documenti simple/professional/digital.

### 9.2 Progettazione

La progettazione è basata su:

- Domain-Driven Design;
- Clean/Hexagonal Architecture;
- port e adapter;
- separazione command/result/use case;
- repository port outbound;
- mapping persistence separato;
- freeze per layer.

### 9.3 Implementazione

L’implementazione è incrementale:

- prima dominio;
- poi application;
- poi infrastructure;
- poi futura API.

Questo riduce la probabilità di accoppiamento prematuro tra modello business e tecnologia.

### 9.4 Verifica

La verifica è supportata da test dedicati:

- `src/test/java/it/gabriele/truckflow/application/ApplicationArchitectureTest.java`
- `src/test/java/it/gabriele/truckflow/application/ApplicationFoundationTest.java`
- `src/test/java/it/gabriele/truckflow/application/ApplicationLayerFinalFreezeTest.java`
- `src/test/java/it/gabriele/truckflow/application/port/out/ApplicationComplianceRepositoryPortTest.java`
- `src/test/java/it/gabriele/truckflow/application/port/out/ApplicationOperationalRepositoryPortTest.java`
- `src/test/java/it/gabriele/truckflow/application/port/out/ApplicationRepositoryPortTest.java`
- `src/test/java/it/gabriele/truckflow/application/port/out/ApplicationVehicleRepositoryPortTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationComplianceUseCaseExpansionTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationOperationalUseCaseExpansionTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationOperationalUseCaseHardeningTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationUseCaseExpansionTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationUseCaseHardeningTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationUseCaseReviewTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/ApplicationVehicleUseCaseExpansionTest.java`
- `src/test/java/it/gabriele/truckflow/application/usecase/FirstApplicationUseCaseTest.java`
- `src/test/java/it/gabriele/truckflow/documentation/DocumentationRestructureTest.java`
- `src/test/java/it/gabriele/truckflow/domain/DomainArchitectureTest.java`
- `src/test/java/it/gabriele/truckflow/domain/DomainValueObjectContractTest.java`
- `src/test/java/it/gabriele/truckflow/domain/cargo/CargoDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/compliance/ComplianceDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/documents/DocumentDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/locations/LocationDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/operational/OperationalDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/qualifications/QualificationCatalogTest.java`
- `src/test/java/it/gabriele/truckflow/domain/shared/exceptions/DomainExceptionTest.java`
- `src/test/java/it/gabriele/truckflow/domain/shipments/ShipmentDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/triptemplates/TripTemplateDomainTest.java`
- `src/test/java/it/gabriele/truckflow/domain/users/UserTest.java`
- `src/test/java/it/gabriele/truckflow/domain/vehicles/VehicleDomainTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/InfrastructureFoundationTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/InfrastructureLayerFinalFreezeTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/InfrastructureTechnicalBoundaryTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/config/spring/SpringWiringFoundationTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/mapping/PersistenceMappingBlueprintTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/memory/InMemoryArchitectureTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/memory/InMemoryComplianceRepositoryTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/memory/InMemoryOperationalRepositoryTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/memory/InMemoryRepositoryTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/memory/InMemoryVehicleRepositoryTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/repository/FileRepositoryExpansionTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/repository/InfrastructureRepositoryUseCaseIntegrationTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/repository/file/FileRepositoryStorageTest.java`
- `src/test/java/it/gabriele/truckflow/infrastructure/repository/locations/FileLocationRepositoryPrototypeTest.java`

### 9.5 Validazione

La validazione attuale è tecnica e architetturale, non ancora business/utente finale. Sono validati:

- confini dei layer;
- presenza dei package attesi;
- assenza di layer prematuri nel codice main;
- repository e mapping file-backed selezionati;
- coerenza della documentazione.

La validazione con stakeholder finali sarà appropriata dopo l’introduzione di API e interfacce applicative.

### 9.6 Manutenzione

La manutenzione è favorita da:

- struttura package stabile;
- naming coerente;
- documentazione aggiornata;
- freeze test;
- roadmap esplicita;
- separazione tra scelte implementate e future.

---

## 10. Processi di supporto ISO/IEC 12207

### 10.1 Documentazione

La documentazione è organizzata in:

| Area | Contenuto | Quantità rilevata |
|---|---|---:|
| `docs/simple` | Guide didattiche e spiegazioni semplici | 14 file Markdown |
| `docs/professional` | Documentazione tecnica ufficiale | 9 file Markdown |
| `docs/old_style` | Archivio storico e roadmap step-by-step | 38 file Markdown |
| `docs/digital` | Reader statico e documenti renderizzati | 61 file HTML renderizzati |

### 10.2 Gestione della configurazione

La gestione della configurazione è supportata da:

- Maven;
- `pom.xml`;
- configurazione Java 21;
- Spotless / Google Java Format;
- `application.yml`;
- Spring profile `memory`;
- repository Git;
- patch documentali e tecniche applicabili.

### 10.3 Gestione della qualità

La qualità è gestita tramite:

- test di dominio;
- test application;
- test infrastructure;
- architecture/freeze test;
- documentazione di review;
- controllo formattazione tramite Maven/Spotless.

### 10.4 Audit tecnico

Il progetto include elementi auditabili:

- roadmap storica;
- freeze documentati;
- test che verificano assenza di layer prematuri;
- documentazione dei repository file-backed inclusi/esclusi;
- package explorer statico;
- separazione tra implementato e pianificato.

---

## 11. Processi organizzativi ISO/IEC 12207

### 11.1 Gestione del progetto

La gestione del progetto è organizzata per milestone:

- Punti 1 → 5: Domain Layer;
- Punto 6A → 6M: Application Layer;
- Punto 7A → 7H: Infrastructure Layer;
- Punto 8A: API Layer Blueprint formalizzato;
- Punto 8B → 8H: API Layer runtime ancora pianificato.

Ogni ciclo ha obiettivi, output e freeze.

### 11.2 Gestione dei rischi

I rischi principali sono:

- introdurre API prima della stabilizzazione;
- mescolare domain/application/infrastructure;
- trasformare repository file-backed in persistenza complessa non controllata;
- introdurre security, JPA o database troppo presto;
- produrre documentazione non allineata al codice.

Le mitigazioni sono descritte nella sezione 21.

### 11.3 Pianificazione

La pianificazione è incrementale e conservativa. Ogni fase abilita la successiva:

```text
Domain stabile
  → Application stabile
    → Infrastructure stabile
      → API controllata
        → Persistence enterprise / Security / Frontend / Integrations
```

---

## 12. Modello di dominio DDD

Il progetto segue una modellazione DDD pragmatica. I bounded context sono separati per dominio e non per tecnologia.

| Bounded context | Entità/Aggregati principali | Value Object / Enumerazioni principali | Invarianti e regole principali |
|---|---|---|---|
| Users | `User` | `UserId`, `Username`, `UserProfile`, `UserContact`, `UserAddress`, `UserRole`, `UserStatus`, `UserPermission`, `UserPreferences`, `LanguageCode`, `UserTheme` | Identità utente obbligatoria, username coerente, stato esplicito, separazione tra profilo, contatti, permessi e preferenze. |
| Qualifications | `Qualification`, `QualificationCatalog` | `QualificationCategory` | Catalogo qualifiche separato dal ruolo operativo; qualifiche modellate come concetti di dominio e non come semplici stringhe. |
| Operational | `Driver`, `Dispatcher`, `Manager`, `Mechanic`, `WarehouseOperator` | `DriverId`, `DispatcherId`, `ManagerId`, `MechanicId`, `WarehouseOperatorId`, `OperationalProfile`, `OperationalStatus`, `OperationalQualification`, `OperationalScope` | Ogni ruolo operativo ha identità propria, profilo coerente, stato operativo controllato e qualifiche/scope espliciti. |
| Vehicles | `VehicleUnit`, `VehicleCombination` | `VehicleUnitId`, `VehicleCombinationId`, `LicensePlate`, `FleetCode`, `VehicleIdentificationNumber`, `VehicleUnitType`, `VehicleCombinationType`, profili body, coupling, specification e weights | Separazione tra unità veicolare e combinazione; plate/VIN/fleet code come value object; stato veicolo e compatibilità tecnica non ridotti a primitive. |
| Cargo | `CargoUnit` | `CargoId`, `CargoCode`, `CargoType`, `CargoCategory`, `CargoWeights`, `CargoDimensions`, `CargoPackaging`, `CargoTemperature`, `CargoTransportRequirement`, `CargoCompatibilityRequirement`, `CargoRegulatory`, `CargoStatus` | Cargo come concetto centrale per requisiti di trasporto, compatibilità, peso, dimensione, packaging e regolamentazione. |
| Locations | `Location` | `LocationId`, `LocationCode`, `LocationAddress`, `GeoCoordinates`, `LocationType`, `LocationStatus` | Location identificata da ID/code, indirizzo e stato; candidata naturale per primo controller API perché stabile e poco workflow-dependent. |
| TripTemplates | `TripTemplate`, `TripTemplateSegment` | `TripTemplateId`, `TripTemplateCode`, `TripTemplateSegmentId`, `Distance`, `DistanceUnit`, `RouteSpecification`, `RouteRoadType`, `TripTemplateStatus`, `TripTemplateType` | Template e segmenti separati; distanza e specifica strada modellate esplicitamente; pianificazione avanzata rimandata. |
| Shipments | `Shipment`, `ShipmentItem`, `ShipmentLeg` | `ShipmentId`, `ShipmentCode`, `ShipmentStatus`, `ShipmentPriority`, `ShipmentServiceLevel`, `ShipmentWeight`, `ShipmentVolume`, `ShipmentTemperature`, `ShipmentRequirementSet`, `ShipmentReferences`, `ShipmentNotes` | Shipment separa core, item, legs, metriche, requisiti e riferimenti; workflow complesso non anticipato oltre i casi d’uso base. |
| Documents | `Document` | `DocumentId`, `DocumentCode`, `DocumentType`, `DocumentCategory`, `DocumentContent`, `DocumentMetadata`, `DocumentReference`, `DocumentReferenceType`, `DocumentStatus` | Documento modellato come concetto generico riutilizzabile, indipendente da file upload, PDF handling, workflow e permessi. |
| Compliance | `ComplianceRequirement` | `ComplianceRequirementId`, `ComplianceRequirementCode`, `ComplianceCategory`, `ComplianceJurisdiction`, `CountryCode`, `ComplianceTarget`, `ComplianceRule`, `ComplianceSeverity`, `ComplianceSource`, `ComplianceRequirementStatus` | Requisiti di compliance separati da implementazioni tecniche; giurisdizione, target, source e severity espliciti. |
| Shared Exceptions | `DomainException`, `DomainValidationException`, `InvariantViolationException` | Eccezioni specifiche per bounded context | Gerarchia comune per distinguere violazioni di dominio, validazione e invarianti. |
|

### Regole DDD osservate

- I bounded context non dipendono da Spring, REST, JPA o database.
- Gli ID sono modellati come value object o classi dedicate.
- Le eccezioni di dominio sono esplicite.
- Le regole di validazione sono contenute nel dominio o nei use case, non in controller.
- I concetti con significato diverso non vengono unificati solo perché strutturalmente simili.
- Le relazioni cross-domain devono preferire ID, value object stabili o concetti astratti, non aggregate root completi.

---

## 13. Architettura software

### 13.1 Layer attuali e futuri

| Layer | Stato | Responsabilità |
|---|---|---|
| Domain | Completato | Modello business, value object, entità, invarianti, eccezioni. |
| Application | Completato | Use case, command, result, inbound port, outbound port. |
| Infrastructure | Completato per il primo ciclo | Adapter tecnici, repository memory/file-backed, mapping, Spring wiring non-web. |
| API | Pianificato | Controller REST, DTO, mapping API, error handling HTTP, OpenAPI. |

### 13.2 Motivazioni formali

La scelta architetturale consente:

- indipendenza del dominio;
- testabilità senza web/database;
- possibilità di sostituire repository e adapter;
- evoluzione controllata verso REST API;
- isolamento delle scelte future come JPA, security e frontend.

### 13.3 Alternative considerate e scartate

| Alternativa | Motivo dello scarto/rinvio |
|---|---|
| CRUD monolitico controller-service-repository | Avrebbe accoppiato business, web e persistenza troppo presto. |
| Database-first / JPA-first | Avrebbe orientato il modello al dato invece che al dominio. |
| API-first immediato | Avrebbe imposto DTO e controller prima della stabilità del dominio. |
| Spring Data immediato | Avrebbe anticipato tecnologia non necessaria nella fase foundation. |
| Frontend immediato | Avrebbe spostato il focus sulla UI invece che sulla correttezza architetturale. |

---

## 14. Motivazione delle scelte tecniche

| Scelta | Motivazione |
|---|---|
| Java 21 | Linguaggio stabile, moderno e adatto a sistemi enterprise. |
| Maven | Build riproducibile e standard nel mondo Java. |
| JUnit | Verifica automatizzata dei layer e dei comportamenti principali. |
| Spring Boot | Usato come wiring tecnico, non come driver architetturale del dominio. |
| Runtime non-web | Evita di introdurre delivery layer prima del Punto 8. |
| Port/Adapter | Isola le dipendenze tecniche dai layer interni. |
| Repository in-memory | Permette test e sviluppo senza database. |
| Repository file-backed limitati | Introduce persistenza reale solo dove il modello è stabile. |
| Markdown/HTML statico | Documentazione versionabile, leggibile e indipendente dal runtime applicativo. |

---

## 15. Tecnologie adottate e alternative scartate

### Tecnologie adottate

- Spring Boot `3.5.15`
- Java `21`
- Maven
- JUnit / Spring Boot Test
- Spotless Maven Plugin / Google Java Format
- Springdoc OpenAPI dependency già presente nel `pom.xml`, ma API runtime non ancora implementata
- Spring Boot Web/Validation dependencies presenti, con runtime configurato come non-web tramite `spring.main.web-application-type: none`

### Alternative scartate o rimandate

| Tecnologia / approccio | Stato | Motivazione |
|---|---|---|
| JPA / Hibernate | Rimandato | Richiede schema, entity mapping e transaction strategy. |
| Spring Data | Rimandato | Prematuro senza decisione DB e modello persistence definitivo. |
| Security HTTP / JWT | Rimandato | Ha senso dopo API Layer. |
| Frontend | Rimandato | Richiede API stabili. |
| Database relazionale | Rimandato | Persistenza enterprise successiva al primo ciclo infrastructure. |
| Telematics/Tachograph/ERP integrations | Rimandato | Richiedono port esterne e adapter dedicati futuri. |

---

## 16. Stato attuale del progetto

### Stato sintetico

| Area | Stato |
|---|---|
| Domain Layer | Completato |
| Application Layer | Completato |
| Infrastructure Layer | Completato per il primo ciclo |
| API Layer Blueprint | Formalizzato in Punto 8A |
| Database/JPA | Non presente |
| Security HTTP/JWT | Non presente |
| Frontend | Non presente |
| External integrations | Non presenti |
| Documentazione digitale | Presente e rifinita |

### Evidenze tecniche

- `src/main/java`: 505 file Java.
- `src/test/java`: 43 file Java di test.
- Package Java rilevati: 103.
- Runtime Spring non-web configurato in `application.yml`.
- Repository file-backed limitati a Locations, Cargo, Documents e Compliance.
- Nessun package `api` presente nel codice main.
- Nessun controller REST, entity JPA o Spring Data repository presente nel codice main.

---

## 17. Funzionalità implementate

Sono implementate:

1. Modello dominio per utenti, qualifiche, ruoli operativi, veicoli, cargo, locations, triptemplates, shipments, documents e compliance.
2. Eccezioni di dominio condivise e specifiche per bounded context.
3. Command applicativi.
4. Result applicativi.
5. Inbound port.
6. Outbound repository port.
7. Use case service per locations, cargo, documents, compliance, operational roles, vehicles e shipments.
8. Repository in-memory per tutti i contesti applicativi principali.
9. Repository file-backed per Locations, Cargo, Documents e Compliance.
10. Mapping persistence e record persistence per repository file-backed selezionati.
11. Spring wiring non-web.
12. Test di dominio, application, infrastructure e freeze.
13. Documentazione simple, professional, old_style e digital.
14. Digital Documentation Reader statico.

---

## 18. Funzionalità mancanti

Sono mancanti o intenzionalmente rimandate:

1. API REST.
2. Controller.
3. DTO web.
4. Mapping API request/response.
5. Error handling HTTP.
6. OpenAPI/Swagger review completa.
7. Security HTTP/JWT.
8. Database relazionale.
9. JPA / Hibernate.
10. Spring Data.
11. Frontend.
12. Audit trail applicativo.
13. Workflow configurabile.
14. Dashboard e KPI.
15. File upload reale.
16. Integrazioni esterne.
17. Telematics/digital tachograph.
18. Persistenza enterprise per shipments, vehicles e operational roles.
19. Validazione utente finale.
20. Deployment e osservabilità runtime.

---


### Documento Punto 8A

Il blueprint ufficiale del Punto 8A è documentato in:

```text
docs/professional/38-api-layer-blueprint.md
```

Il documento definisce regole API, versionamento `/api/v1`, primo contesto REST Locations, endpoint futuri e test `ApiLayerArchitectureTest`.

## 19. Roadmap tecnica completa

| Milestone | Stato | Descrizione formale |
|---|---|---|
| Punto 1 | Completato | Avvio della fondazione di dominio e definizione della visione iniziale del progetto. |
| Punto 2 | Completato | Modellazione dei primi bounded context e consolidamento di utenti, qualifiche, ruoli operativi e veicoli. |
| Punto 3 | Completato | Espansione del dominio verso cargo, locations, trip templates, shipments, documents e compliance. |
| Punto 4 | Completato | Revisione delle regole di dominio, correzioni architetturali e protezione delle invarianti. |
| Punto 5 | Completato | Review finale del Domain Layer e consolidamento della suite di test di dominio. |
| Punto 6A | Completato | Application Layer Blueprint: definizione struttura, obiettivi e confini dell’application layer. |
| Punto 6B | Completato | Application Foundation: introduzione di command, result, port e convenzioni applicative. |
| Punto 6C | Completato | Application Repository Ports: definizione dei contratti outbound verso repository. |
| Punto 6D | Completato | In-Memory Repositories: introduzione di adapter in-memory per test e runtime locale. |
| Punto 6E | Completato | First Use Cases: implementazione iniziale dei casi d’uso applicativi. |
| Punto 6F | Completato | Use Case Hardening: rafforzamento dei casi d’uso e delle validazioni applicative. |
| Punto 6G | Completato | Use Case Expansion: estensione controllata verso ulteriori bounded context. |
| Punto 6H | Completato | Expansion Review & Documentation Alignment: revisione e allineamento documentale. |
| Punto 6I | Completato | Vehicles Use Cases Expansion: espansione dei casi d’uso veicoli. |
| Punto 6J | Completato | Operational Roles Use Cases Expansion: espansione dei casi d’uso dei ruoli operativi. |
| Punto 6K | Completato | Operational Use Case Hardening: rafforzamento dei casi d’uso operational. |
| Punto 6L | Completato | Compliance Base Use Cases: introduzione dei casi d’uso compliance. |
| Punto 6M | Completato | Application Layer Final Review & Freeze: chiusura e congelamento dell’application layer. |
| Punto 7A | Completato | Infrastructure Layer Blueprint: definizione del layer tecnico e dei vincoli. |
| Punto 7B | Completato | Infrastructure Foundation: package tecnici di base, marker, eccezioni e adapter. |
| Punto 7C | Completato | Spring Wiring Foundation: wiring Spring non-web confinato in infrastructure. |
| Punto 7D | Completato | Persistence Mapping Blueprint: formalizzazione del mapping domain/persistence. |
| Punto 7E | Completato | Real Repository Prototype: repository file-backed prototipale per Locations. |
| Punto 7F | Completato | Repository Expansion: espansione file-backed verso Cargo, Documents e Compliance. |
| Punto 7G | Completato | Infrastructure Testing: test tecnici su repository, mapping e confini. |
| Punto 7H | Completato | Infrastructure Review & Freeze: review finale e chiusura Punto 7. |
| Punto 8A | Completato | API Layer Blueprint: definizione formale del layer API, versionamento `/api/v1`, primo contesto Locations e test architetturale future-proof. |
| Punto 8B | Prossimo | API Layer Foundation: package e convenzioni API. |
| Punto 8C | Pianificato | Locations Controller Prototype: primo controller REST su contesto stabile. |
| Punto 8D | Pianificato | API DTO & Mapping Conventions: request/response DTO e mapping verso command/result. |
| Punto 8E | Pianificato | API Error Handling: modello errore HTTP strutturato. |
| Punto 8F | Pianificato | API Controller Tests: test web/controller. |
| Punto 8G | Pianificato | OpenAPI & Swagger Review: documentazione API e revisione contratto. |
| Punto 8H | Pianificato | API Layer Freeze: review e chiusura controllata del primo ciclo API. |

### Punti completati

- Punto 1 → 5: Domain Layer.
- Punto 6A → 6M: Application Layer.
- Punto 7A → 7H: Infrastructure Layer.
- Punto 8A: API Layer Blueprint.

### Punti mancanti

- Punto 8B → 8H: API Layer runtime, controller, DTO, error handling, test web, OpenAPI e freeze.
- Cicli successivi non ancora formalizzati: security, database/JPA, frontend, audit, workflow, integrazioni e dashboard.

---

## 20. Regole architetturali, vincoli e linee guida

### Regole di dipendenza

```text
Domain          → nessuna dipendenza verso application, infrastructure, api, Spring, JPA, REST
Application     → può dipendere dal domain, non da infrastructure/api
Infrastructure  → può implementare port application e usare domain/application
API futura      → deve dipendere da application, non da repository concreti
```

### Vincoli attuali

- Nessun controller REST nel codice main.
- Nessun package API runtime prima del Punto 8B.
- Nessuna entity JPA.
- Nessun Spring Data repository.
- Nessuna configurazione security HTTP.
- Nessun database obbligatorio.
- Nessun frontend.
- Nessuna integrazione esterna.

### Linee guida per Punto 8

- Primo controller: `LocationController`.
- Prima base endpoint: `/api/v1/locations`.
- Controller deve chiamare use case, non repository.
- DTO devono restare nel layer API.
- Error handling HTTP deve mappare eccezioni applicative/domain in risposta strutturata.
- API Layer deve avere test dedicati.

---

## 21. Rischi tecnici, criticità e strategie di mitigazione

| Rischio / Criticità | Impatto | Mitigazione |
|---|---|---|
| Introduzione prematura di API runtime | Alto | Il Punto 8A introduce solo blueprint e test architetturali; controller e DTO restano nei punti successivi. |
| Controller che chiamano repository concreti | Alto | Regola: API → use case application. |
| Accoppiamento domain/Spring | Alto | Test architetturali e divieto import Spring nel dominio. |
| JPA introdotto senza strategia | Medio/Alto | Rimandare DB/JPA a ciclo dedicato. |
| Repository file-backed estesi a contesti complessi | Medio | Limitare file-backed a contesti stabili; progettare mapping dedicato per shipments/vehicles/operational. |
| Security rimandata troppo a lungo | Medio | Inserire security dopo API foundation, non prima. |
| Documentazione non allineata al codice | Medio | Aggiornare docs e package explorer a ogni ciclo. |
| Crescita del dominio senza governance | Medio | Continuare con freeze, review e bounded context espliciti. |
| Mancanza di performance baseline | Medio | Introdurre benchmark dopo API e persistenza enterprise. |
| Mancanza di validazione utente finale | Medio | Pianificare validazione funzionale dopo primi endpoint/API. |

---

## 22. Glossario tecnico completo

| Termine | Definizione |
|---|---|
| API Layer | Futuro layer di delivery HTTP con controller, DTO, error handling e OpenAPI. |
| Application Layer | Layer che contiene use case, command, result e port. |
| Architecture Freeze | Punto di stabilizzazione che impedisce derive premature. |
| Adapter | Implementazione tecnica di una port. |
| Aggregate | Oggetto di dominio che protegge un insieme coerente di invarianti. |
| Bounded Context | Confine semantico di un sotto-dominio. |
| Clean Architecture | Stile architetturale in cui le dipendenze puntano verso il nucleo business. |
| Command | Oggetto applicativo che rappresenta una richiesta di esecuzione use case. |
| Compliance Requirement | Requisito normativo/operativo modellato nel dominio compliance. |
| Domain Layer | Layer contenente business rules, entità, value object, invarianti ed eccezioni. |
| DTO | Oggetto di trasferimento dati previsto per il futuro API Layer. |
| Entity | Oggetto di dominio con identità persistente nel tempo. |
| File-backed Repository | Repository concreto basato su storage testuale/file. |
| Hexagonal Architecture | Stile port/adapter che separa core applicativo e dettagli esterni. |
| Infrastructure Layer | Layer tecnico con adapter, repository, mapping e configurazioni. |
| In-memory Repository | Repository tecnico in RAM per test e sviluppo locale. |
| Invariant | Regola che deve restare vera per mantenere valido un oggetto di dominio. |
| JPA | Tecnologia di persistenza ORM rimandata a ciclo futuro. |
| Maven | Build tool Java usato dal progetto. |
| OpenAPI | Specifica futura per documentare endpoint REST. |
| Port | Interfaccia che definisce ingresso o uscita del layer application. |
| Repository | Astrazione per recupero/salvataggio dati. |
| Result | Oggetto applicativo restituito da un use case. |
| Spring Wiring | Uso di Spring per collegare bean e implementazioni, confinato in infrastructure. |
| Use Case | Azione applicativa eseguibile che orchestra dominio e repository port. |
| Value Object | Oggetto immutabile o semanticamente identificato dal valore, non dall’identità. |

---

## 23. Package Explorer reale

Il seguente Package Explorer rispecchia i package Java reali rilevati in `src/main/java`.

### Criteri di classificazione documentale

- **Documentato**: package o area direttamente trattata nella documentazione ufficiale o storica.
- **Parzialmente documentato**: package coperto dal documento del layer o bounded context padre, ma senza documento specifico dedicato.
- **Non documentato**: package privo di copertura diretta o indiretta rilevante nella documentazione disponibile.

### Sintesi classificazione

- Documentato: 31
- Parzialmente documentato: 72
- Non documentato: 0

| # | Package Java reale | Layer | File Java | Stato documentazione |
|---:|---|---|---:|---|
| 1 | `it.gabriele.truckflow` | Bootstrap | 2 | Documentato |
| 2 | `it.gabriele.truckflow.application` | Application | 1 | Documentato |
| 3 | `it.gabriele.truckflow.application.command` | Application | 2 | Documentato |
| 4 | `it.gabriele.truckflow.application.command.cargo` | Application | 3 | Parzialmente documentato |
| 5 | `it.gabriele.truckflow.application.command.compliance` | Application | 7 | Parzialmente documentato |
| 6 | `it.gabriele.truckflow.application.command.documents` | Application | 5 | Parzialmente documentato |
| 7 | `it.gabriele.truckflow.application.command.locations` | Application | 3 | Parzialmente documentato |
| 8 | `it.gabriele.truckflow.application.command.operational` | Application | 26 | Parzialmente documentato |
| 9 | `it.gabriele.truckflow.application.command.shipments` | Application | 7 | Parzialmente documentato |
| 10 | `it.gabriele.truckflow.application.command.vehicles` | Application | 9 | Parzialmente documentato |
| 11 | `it.gabriele.truckflow.application.exception` | Application | 5 | Documentato |
| 12 | `it.gabriele.truckflow.application.port.in` | Application | 2 | Documentato |
| 13 | `it.gabriele.truckflow.application.port.in.cargo` | Application | 3 | Parzialmente documentato |
| 14 | `it.gabriele.truckflow.application.port.in.compliance` | Application | 7 | Parzialmente documentato |
| 15 | `it.gabriele.truckflow.application.port.in.documents` | Application | 5 | Parzialmente documentato |
| 16 | `it.gabriele.truckflow.application.port.in.locations` | Application | 3 | Parzialmente documentato |
| 17 | `it.gabriele.truckflow.application.port.in.operational` | Application | 26 | Parzialmente documentato |
| 18 | `it.gabriele.truckflow.application.port.in.shipments` | Application | 7 | Parzialmente documentato |
| 19 | `it.gabriele.truckflow.application.port.in.vehicles` | Application | 9 | Parzialmente documentato |
| 20 | `it.gabriele.truckflow.application.port.out` | Application | 2 | Documentato |
| 21 | `it.gabriele.truckflow.application.port.out.cargo` | Application | 2 | Parzialmente documentato |
| 22 | `it.gabriele.truckflow.application.port.out.compliance` | Application | 2 | Parzialmente documentato |
| 23 | `it.gabriele.truckflow.application.port.out.documents` | Application | 2 | Parzialmente documentato |
| 24 | `it.gabriele.truckflow.application.port.out.locations` | Application | 2 | Parzialmente documentato |
| 25 | `it.gabriele.truckflow.application.port.out.operational` | Application | 6 | Parzialmente documentato |
| 26 | `it.gabriele.truckflow.application.port.out.shipments` | Application | 2 | Parzialmente documentato |
| 27 | `it.gabriele.truckflow.application.port.out.vehicles` | Application | 3 | Parzialmente documentato |
| 28 | `it.gabriele.truckflow.application.result` | Application | 2 | Documentato |
| 29 | `it.gabriele.truckflow.application.result.cargo` | Application | 2 | Parzialmente documentato |
| 30 | `it.gabriele.truckflow.application.result.compliance` | Application | 2 | Parzialmente documentato |
| 31 | `it.gabriele.truckflow.application.result.documents` | Application | 2 | Parzialmente documentato |
| 32 | `it.gabriele.truckflow.application.result.locations` | Application | 2 | Parzialmente documentato |
| 33 | `it.gabriele.truckflow.application.result.operational` | Application | 6 | Parzialmente documentato |
| 34 | `it.gabriele.truckflow.application.result.shipments` | Application | 2 | Parzialmente documentato |
| 35 | `it.gabriele.truckflow.application.result.vehicles` | Application | 3 | Parzialmente documentato |
| 36 | `it.gabriele.truckflow.application.usecase` | Application | 1 | Documentato |
| 37 | `it.gabriele.truckflow.application.usecase.cargo` | Application | 3 | Parzialmente documentato |
| 38 | `it.gabriele.truckflow.application.usecase.compliance` | Application | 8 | Parzialmente documentato |
| 39 | `it.gabriele.truckflow.application.usecase.documents` | Application | 6 | Parzialmente documentato |
| 40 | `it.gabriele.truckflow.application.usecase.locations` | Application | 3 | Parzialmente documentato |
| 41 | `it.gabriele.truckflow.application.usecase.operational` | Application | 31 | Parzialmente documentato |
| 42 | `it.gabriele.truckflow.application.usecase.shipments` | Application | 8 | Parzialmente documentato |
| 43 | `it.gabriele.truckflow.application.usecase.vehicles` | Application | 11 | Parzialmente documentato |
| 44 | `it.gabriele.truckflow.domain.cargo` | Domain | 17 | Documentato |
| 45 | `it.gabriele.truckflow.domain.cargo.exceptions` | Domain | 1 | Documentato |
| 46 | `it.gabriele.truckflow.domain.compliance` | Domain | 18 | Documentato |
| 47 | `it.gabriele.truckflow.domain.compliance.exceptions` | Domain | 1 | Documentato |
| 48 | `it.gabriele.truckflow.domain.documents` | Domain | 11 | Documentato |
| 49 | `it.gabriele.truckflow.domain.documents.exceptions` | Domain | 1 | Documentato |
| 50 | `it.gabriele.truckflow.domain.locations` | Domain | 8 | Documentato |
| 51 | `it.gabriele.truckflow.domain.locations.exceptions` | Domain | 1 | Documentato |
| 52 | `it.gabriele.truckflow.domain.operational.common` | Domain | 7 | Parzialmente documentato |
| 53 | `it.gabriele.truckflow.domain.operational.dispatcher` | Domain | 2 | Parzialmente documentato |
| 54 | `it.gabriele.truckflow.domain.operational.driver` | Domain | 2 | Parzialmente documentato |
| 55 | `it.gabriele.truckflow.domain.operational.exceptions` | Domain | 5 | Documentato |
| 56 | `it.gabriele.truckflow.domain.operational.manager` | Domain | 2 | Parzialmente documentato |
| 57 | `it.gabriele.truckflow.domain.operational.mechanic` | Domain | 2 | Parzialmente documentato |
| 58 | `it.gabriele.truckflow.domain.operational.warehouse` | Domain | 2 | Parzialmente documentato |
| 59 | `it.gabriele.truckflow.domain.qualifications` | Domain | 3 | Documentato |
| 60 | `it.gabriele.truckflow.domain.qualifications.exceptions` | Domain | 1 | Documentato |
| 61 | `it.gabriele.truckflow.domain.shared.exceptions` | Domain | 3 | Documentato |
| 62 | `it.gabriele.truckflow.domain.shipments.core` | Domain | 7 | Parzialmente documentato |
| 63 | `it.gabriele.truckflow.domain.shipments.exceptions` | Domain | 3 | Documentato |
| 64 | `it.gabriele.truckflow.domain.shipments.items` | Domain | 3 | Parzialmente documentato |
| 65 | `it.gabriele.truckflow.domain.shipments.legs` | Domain | 3 | Parzialmente documentato |
| 66 | `it.gabriele.truckflow.domain.shipments.metrics` | Domain | 5 | Parzialmente documentato |
| 67 | `it.gabriele.truckflow.domain.shipments.notes` | Domain | 1 | Parzialmente documentato |
| 68 | `it.gabriele.truckflow.domain.shipments.properties` | Domain | 2 | Parzialmente documentato |
| 69 | `it.gabriele.truckflow.domain.shipments.references` | Domain | 1 | Parzialmente documentato |
| 70 | `it.gabriele.truckflow.domain.shipments.requirements` | Domain | 2 | Parzialmente documentato |
| 71 | `it.gabriele.truckflow.domain.triptemplates` | Domain | 13 | Documentato |
| 72 | `it.gabriele.truckflow.domain.triptemplates.exceptions` | Domain | 2 | Documentato |
| 73 | `it.gabriele.truckflow.domain.users` | Domain | 15 | Documentato |
| 74 | `it.gabriele.truckflow.domain.users.exceptions` | Domain | 1 | Documentato |
| 75 | `it.gabriele.truckflow.domain.vehicles.body` | Domain | 11 | Parzialmente documentato |
| 76 | `it.gabriele.truckflow.domain.vehicles.combination` | Domain | 3 | Parzialmente documentato |
| 77 | `it.gabriele.truckflow.domain.vehicles.common` | Domain | 1 | Parzialmente documentato |
| 78 | `it.gabriele.truckflow.domain.vehicles.coupling` | Domain | 2 | Parzialmente documentato |
| 79 | `it.gabriele.truckflow.domain.vehicles.exceptions` | Domain | 2 | Documentato |
| 80 | `it.gabriele.truckflow.domain.vehicles.operation` | Domain | 2 | Parzialmente documentato |
| 81 | `it.gabriele.truckflow.domain.vehicles.specification` | Domain | 12 | Parzialmente documentato |
| 82 | `it.gabriele.truckflow.domain.vehicles.unit` | Domain | 8 | Parzialmente documentato |
| 83 | `it.gabriele.truckflow.infrastructure` | Infrastructure | 1 | Documentato |
| 84 | `it.gabriele.truckflow.infrastructure.adapter` | Infrastructure | 2 | Parzialmente documentato |
| 85 | `it.gabriele.truckflow.infrastructure.config` | Infrastructure | 2 | Documentato |
| 86 | `it.gabriele.truckflow.infrastructure.config.spring` | Infrastructure | 11 | Documentato |
| 87 | `it.gabriele.truckflow.infrastructure.exception` | Infrastructure | 6 | Parzialmente documentato |
| 88 | `it.gabriele.truckflow.infrastructure.mapping` | Infrastructure | 6 | Parzialmente documentato |
| 89 | `it.gabriele.truckflow.infrastructure.memory` | Infrastructure | 1 | Parzialmente documentato |
| 90 | `it.gabriele.truckflow.infrastructure.memory.cargo` | Infrastructure | 2 | Parzialmente documentato |
| 91 | `it.gabriele.truckflow.infrastructure.memory.compliance` | Infrastructure | 2 | Parzialmente documentato |
| 92 | `it.gabriele.truckflow.infrastructure.memory.documents` | Infrastructure | 1 | Parzialmente documentato |
| 93 | `it.gabriele.truckflow.infrastructure.memory.locations` | Infrastructure | 2 | Parzialmente documentato |
| 94 | `it.gabriele.truckflow.infrastructure.memory.operational` | Infrastructure | 6 | Parzialmente documentato |
| 95 | `it.gabriele.truckflow.infrastructure.memory.shipments` | Infrastructure | 2 | Parzialmente documentato |
| 96 | `it.gabriele.truckflow.infrastructure.memory.vehicles` | Infrastructure | 3 | Parzialmente documentato |
| 97 | `it.gabriele.truckflow.infrastructure.repository` | Infrastructure | 2 | Documentato |
| 98 | `it.gabriele.truckflow.infrastructure.repository.cargo` | Infrastructure | 5 | Parzialmente documentato |
| 99 | `it.gabriele.truckflow.infrastructure.repository.compliance` | Infrastructure | 5 | Parzialmente documentato |
| 100 | `it.gabriele.truckflow.infrastructure.repository.documents` | Infrastructure | 5 | Parzialmente documentato |
| 101 | `it.gabriele.truckflow.infrastructure.repository.file` | Infrastructure | 4 | Documentato |
| 102 | `it.gabriele.truckflow.infrastructure.repository.locations` | Infrastructure | 4 | Parzialmente documentato |
| 103 | `it.gabriele.truckflow.infrastructure.service` | Infrastructure | 2 | Parzialmente documentato |

---

## 24. Valutazione tecnica finale del progetto

TruckFlow Manager presenta una fondazione tecnica solida, coerente e ben separata. Il progetto dimostra una forte attenzione a manutenibilità, testabilità, modularità e controllo della complessità. La scelta di procedere per layer e freeze riduce il rischio di accumulare debito architetturale precoce.

### Punti di forza

- Dominio ampio e realistico.
- Layer ben separati.
- Use case applicativi espliciti.
- Repository port indipendenti dalle implementazioni.
- Infrastruttura controllata e non invasiva.
- Test architetturali e freeze.
- Documentazione estesa e organizzata.
- Roadmap coerente verso API Layer.

### Limiti attuali

- Sistema non ancora esposto via API.
- Assenza di sicurezza HTTP.
- Assenza di persistenza enterprise/database.
- Assenza di frontend.
- Assenza di audit trail, workflow e dashboard.
- Prestazioni e deployment non ancora valutati.

### Giudizio complessivo

Il progetto è **architetturalmente maturo per la fase foundation** e ha formalizzato il Punto 8A API Layer Blueprint. Non è ancora un prodotto enterprise operativo, ma possiede una base progettuale adeguata per evolvere verso una piattaforma enterprise reale.

La priorità successiva dovrebbe essere:

```text
Punto 8B — API Layer Foundation
```

seguito da un primo controller su Locations, mantenendo le stesse regole di disciplina già adottate nei cicli Domain, Application e Infrastructure.
