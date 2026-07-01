# 18 — Application Repository Ports

Questo documento descrive il **Punto 6C — Repository Ports** di TruckFlow Manager.

Il Punto 6A ha definito il blueprint dell'application layer. Il Punto 6B ha creato la foundation applicativa: package, contratti base, eccezioni applicative e test architetturali. Il Punto 6C introduce le prime **porte repository** specifiche, cioè i contratti astratti che i futuri use case useranno per salvare e recuperare aggregate del dominio senza conoscere database, file system, API esterne o implementazioni concrete.

---

## 1. Obiettivo del Punto 6C

L'obiettivo del Punto 6C è creare il primo collegamento pulito tra application layer e dominio persistibile.

Questa fase introduce:

- il contratto marker `RepositoryPort`;
- `LocationRepository` per il dominio locations;
- `CargoUnitRepository` per il dominio cargo;
- `ShipmentRepository` per il dominio shipments;
- test contrattuali sulle porte repository;
- documentazione aggiornata sul nuovo step.

Il risultato non è ancora un sistema con database. Le repository port sono solo interfacce applicative.

---

## 2. Perché servono le repository port

Il dominio puro sa costruire, validare e modificare aggregate root e value object, ma non deve sapere dove questi oggetti vengono salvati.

L'application layer, invece, deve poter orchestrare casi d'uso come:

- registrare una location;
- registrare un cargo;
- creare una shipment;
- aggiungere un cargo a una shipment;
- aggiungere una tratta a una shipment;
- confermare una shipment;
- recuperare oggetti esistenti.

Per fare questo, i futuri use case dovranno caricare e salvare aggregate. Non devono però dipendere da una classe tecnica concreta.

La regola è:

```text
Use case -> Repository port -> implementazione futura
```

Non deve essere:

```text
Use case -> database concreto
Use case -> file system concreto
Use case -> repository JPA concreto
Use case -> repository in memory concreto
```

---

## 3. Cosa è stato aggiunto

### 3.1 `RepositoryPort`

È stato aggiunto il marker:

```text
application.port.out.RepositoryPort
```

Questo contratto identifica le porte repository in uscita dell'application layer.

Non contiene logica e non rappresenta un database. Serve a rendere esplicito che una repository port è un contratto outbound dell'application layer.

---

### 3.2 `LocationRepository`

È stata aggiunta la porta:

```text
application.port.out.locations.LocationRepository
```

Questa porta permette ai futuri use case sulle location di:

- salvare una `Location`;
- cercare una `Location` tramite `LocationId`;
- cercare una `Location` tramite `LocationCode`;
- verificare l'esistenza per ID;
- verificare l'esistenza per codice.

Questa porta sarà usata in futuro da use case come:

- `RegisterLocationUseCase`;
- `FindLocationUseCase`;
- `UpdateLocationUseCase`;
- `ArchiveLocationUseCase`.

---

### 3.3 `CargoUnitRepository`

È stata aggiunta la porta:

```text
application.port.out.cargo.CargoUnitRepository
```

Questa porta permette ai futuri use case cargo di:

- salvare una `CargoUnit`;
- cercare una `CargoUnit` tramite `CargoId`;
- cercare una `CargoUnit` tramite `CargoCode`;
- verificare l'esistenza per ID;
- verificare l'esistenza per codice.

Questa porta sarà usata in futuro da use case come:

- `RegisterCargoUnitUseCase`;
- `FindCargoUnitUseCase`;
- `UpdateCargoUnitUseCase`;
- `ArchiveCargoUnitUseCase`.

---

### 3.4 `ShipmentRepository`

È stata aggiunta la porta:

```text
application.port.out.shipments.ShipmentRepository
```

Questa porta permette ai futuri use case shipment di:

- salvare una `Shipment`;
- cercare una `Shipment` tramite `ShipmentId`;
- cercare una `Shipment` tramite `ShipmentCode`;
- verificare l'esistenza per ID;
- verificare l'esistenza per codice.

Questa porta sarà usata in futuro da use case come:

- `CreateShipmentUseCase`;
- `AddShipmentItemUseCase`;
- `AddShipmentLegUseCase`;
- `ConfirmShipmentUseCase`;
- `CancelShipmentUseCase`;
- `FindShipmentUseCase`.

---

## 4. Perché partiamo da Locations, Cargo e Shipments

Il primo blocco applicativo consigliato rimane:

```text
Locations + Cargo + Shipments
```

Questa scelta è intenzionale.

Le location rappresentano i luoghi logistici.

Il cargo rappresenta la merce.

La shipment rappresenta la richiesta di spedizione che collega cargo e location attraverso item e tratte logiche.

Questi tre domini permettono di costruire il primo flusso applicativo reale senza introdurre ancora planning, dispatching, tracking, veicoli assegnati, autisti assegnati, disponibilità, database o REST API.

Il primo scenario futuro sarà:

```text
registro una location di partenza
registro una location di destinazione
registro un cargo
creo una shipment
aggiungo il cargo alla shipment
aggiungo la tratta alla shipment
confermo la shipment
```

---

## 5. Cosa le repository port NON fanno

Le repository port introdotte nel Punto 6C non fanno ancora:

- persistenza su database;
- implementazioni in memory reali;
- query avanzate;
- paginazione;
- filtri complessi;
- transazioni;
- locking;
- audit trail;
- versioning;
- integrazioni esterne;
- salvataggio su file;
- mapping JPA;
- esposizione REST.

Queste responsabilità arriveranno più avanti in infrastructure, web, database e moduli enterprise.

Le porte repository devono rimanere semplici perché il loro scopo attuale è preparare i primi use case.

---

## 6. Regole architetturali

Le repository port appartengono all'application layer.

Possono importare aggregate root, ID e code del dominio perché devono esprimere il contratto applicativo di persistenza.

Non possono dipendere da:

- Spring;
- JPA;
- Hibernate;
- Lombok;
- controller REST;
- implementazioni infrastructure;
- classi database;
- classi web;
- adapter concreti.

La direzione corretta delle dipendenze è:

```text
application -> domain
infrastructure -> application
web -> application
```

La direzione vietata è:

```text
domain -> application
application -> infrastructure
application -> web
```

---

## 7. Cosa è stato testato

È stato aggiunto un test dedicato alle repository port applicative.

Il test verifica che:

- `LocationRepository` salvi e recuperi location per ID e codice;
- `CargoUnitRepository` salvi e recuperi cargo unit per ID e codice;
- `ShipmentRepository` salvi e recuperi shipment per ID e codice;
- le repository port siano interfacce;
- le repository port estendano `RepositoryPort`;
- i contratti possano essere implementati senza dipendere da tecnologia esterna.

I test usano implementazioni in-memory private interne al test. Queste non sono ancora la infrastructure ufficiale del progetto. Servono solo per dimostrare il contratto.

---

## 8. Cosa manca ancora

Dopo il Punto 6C mancano ancora:

- repository in-memory ufficiali in `infrastructure.memory`;
- command specifici;
- result specifici;
- use case specifici;
- application service;
- test applicativi completi;
- primo flusso Locations + Cargo + Shipments;
- controller REST;
- database;
- mapping persistence;
- API pubbliche;
- sicurezza;
- workflow;
- moduli enterprise.

Queste parti non sono state introdotte ora perché il Punto 6C deve rimanere focalizzato sui contratti repository.

---

## 9. Prossimo step

Il prossimo passo consigliato è:

```text
Punto 6D — In-Memory Repositories
```

In quella fase verranno create le prime implementazioni concrete temporanee:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`.

Queste implementazioni permetteranno di testare i futuri use case senza database.

Dopo il Punto 6D sarà possibile iniziare il Punto 6E, dedicato ai primi use case reali.

## Evoluzione nel Punto 6D

Il Punto 6D implementa le repository port introdotte in questo documento tramite adapter in memory.

La relazione è questa:

- `LocationRepository` viene implementata da `InMemoryLocationRepository`;
- `CargoUnitRepository` viene implementata da `InMemoryCargoUnitRepository`;
- `ShipmentRepository` viene implementata da `InMemoryShipmentRepository`.

Queste implementazioni dimostrano che i contratti definiti nel Punto 6C sono semplici, testabili e indipendenti da tecnologie esterne.

Le repository in memory non sostituiscono il futuro database. Servono per test, prototipi locali e primi use case applicativi.

## Aggiornamento dopo il Punto 6E

Le repository port introdotte nel Punto 6C sono ora usate dai primi application service.

- `LocationRepository` viene usata dai casi d'uso di registrazione, ricerca e validazione delle location nelle shipment leg.
- `CargoUnitRepository` viene usata dai casi d'uso cargo e per verificare che un cargo esista prima di aggiungerlo a una shipment.
- `ShipmentRepository` viene usata per creare, aggiornare, confermare e recuperare le shipment.

Le porte hanno quindi iniziato a svolgere il loro ruolo reale: permettere agli use case di orchestrare il dominio senza conoscere implementazioni concrete.

## Collegamento con il Punto 6F

Il Punto 6F usa le repository port definite in questo step per verificare scenari applicativi più robusti.

`ShipmentRepository` viene usata anche dal nuovo `CancelShipmentUseCase`, mentre `LocationRepository` e `CargoUnitRepository` continuano a proteggere i lookup necessari ai primi flussi. La logica rimane astratta: nessun service applicativo conosce database, JPA, file system o implementazioni concrete.

## Aggiornamento dopo il Punto 6G — DocumentRepository

Il Punto 6G estende il set di repository port aggiungendo:

```text
application.port.out.documents.DocumentRepository
```

Questa porta permette ai use case documentali di:

- salvare un `Document`;
- cercare un `Document` tramite `DocumentId`;
- cercare un `Document` tramite `DocumentCode`;
- verificare l'esistenza per ID;
- verificare l'esistenza per codice.

`DocumentRepository` resta una porta outbound dell'application layer. Non è una repository JPA, non usa Spring Data, non conosce database e non gestisce file fisici.

La porta viene usata dai primi use case documentali del Punto 6G:

- `RegisterDocumentUseCase`;
- `FindDocumentUseCase`;
- `ActivateDocumentUseCase`;
- `ArchiveDocumentUseCase`.

I test contrattuali delle repository port sono stati aggiornati per includere anche `DocumentRepository`.
