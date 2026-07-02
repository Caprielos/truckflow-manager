# Punto 7D — Persistence Mapping Blueprint

Questo documento descrive il **Punto 7D — Persistence Mapping Blueprint** di TruckFlow Manager.

Il Punto 7D definisce il modo in cui, nei prossimi step, gli aggregate e i value object del dominio potranno essere tradotti in modelli tecnici persistenti. La fase è volutamente prudente: introduce blueprint, convenzioni e test architetturali, ma non introduce ancora database, JPA, Spring Data, repository reali o schema fisico.

## Stato da cui partiamo

Prima del Punto 7D il progetto contiene già:

- dominio puro completato, testato e documentato;
- application layer completato e congelato con il Punto 6M;
- repository port nell'application layer;
- repository in memory ancora validi per test, sviluppo locale e profilo `memory`;
- Punto 7A come blueprint generale dell'Infrastructure Layer;
- Punto 7B come foundation tecnica dell'infrastructure layer;
- Punto 7C come wiring Spring tecnico non web;
- nessun controller REST;
- nessun database reale;
- nessuna entity JPA;
- nessuna Spring Data repository;
- nessuna security HTTP.

Il Punto 7D non cambia questi confini. Aggiunge solo il primo linguaggio ufficiale per parlare di mapping domain ↔ persistence.

## Obiettivo del Punto 7D

L'obiettivo è rispondere a questa domanda:

> Come descriviamo il passaggio da domain model a persistence model senza introdurre subito una tecnologia di persistenza concreta?

La risposta è:

- un catalogo di blueprint di mapping;
- una descrizione tecnica dei campi da persistire;
- una classificazione dei tipi di mapping;
- regole su identity, business code, state, value object, enum, collection e reference;
- test che proteggono il progetto da JPA/database introdotti troppo presto.

## Struttura introdotta

Il Punto 7D rafforza il package già creato nel Punto 7B:

```text
src/main/java/it/gabriele/truckflow/infrastructure/mapping
├── PersistenceMapper.java
├── PersistenceMappingKind.java
├── PersistenceFieldMapping.java
├── PersistenceMappingBlueprint.java
├── PersistenceMappingBlueprintCatalog.java
└── package-info.java
```

Introduce anche il test:

```text
src/test/java/it/gabriele/truckflow/infrastructure/mapping/PersistenceMappingBlueprintTest.java
```

Questa struttura non è ancora una persistenza reale. È una mappa tecnica e documentale che guiderà il futuro Punto 7E.

## Concetto di persistence mapping blueprint

Un **persistence mapping blueprint** descrive come un tipo di dominio dovrebbe essere rappresentato da un futuro modello tecnico persistente.

Esempio concettuale:

```text
Location -> LocationPersistenceModel
Shipment -> ShipmentPersistenceModel
VehicleUnit -> VehicleUnitPersistenceModel
ComplianceRequirement -> ComplianceRequirementPersistenceModel
```

La parola `PersistenceModel` indica un modello futuro, tecnico, neutro rispetto alla tecnologia. Non significa JPA entity, tabella SQL, documento Mongo o record fisico già implementato.

## Cosa è stato introdotto nel codice

### `PersistenceMappingKind`

Classifica la natura del mapping:

- `AGGREGATE`;
- `VALUE_OBJECT`;
- `ENUMERATION`;
- `STATE`;
- `COLLECTION`;
- `REFERENCE`.

Questa classificazione serve a rendere esplicito cosa stiamo traducendo:

- un value object può diventare una stringa o più colonne future;
- un enum può diventare un valore testuale stabile;
- uno stato deve essere persistito senza alterare la state machine;
- una collection può diventare un insieme di record figli futuri;
- una reference verso un altro aggregate deve rimanere basata su ID, non su aggregate completi.

### `PersistenceFieldMapping`

Descrive un singolo campo del mapping:

- nome lato dominio;
- nome lato persistence model;
- tipo di mapping;
- obbligatorietà;
- note tecniche.

È un record validato. Rifiuta nomi vuoti, tipo nullo e note vuote.

### `PersistenceMappingBlueprint`

Descrive il blueprint completo di un tipo:

- context name;
- domain type;
- future persistence model name;
- elenco dei campi;
- note tecniche.

Anche questo record è validato. Un blueprint senza campi non è ammesso.

### `PersistenceMappingBlueprintCatalog`

È il catalogo ufficiale dei blueprint introdotti nel Punto 7D.

Copre i contesti applicativi già attivi:

- `locations`;
- `cargo`;
- `shipments`;
- `documents`;
- `vehicles.unit`;
- `vehicles.combination`;
- `operational.driver`;
- `operational.mechanic`;
- `operational.warehouse`;
- `operational.dispatcher`;
- `operational.manager`;
- `compliance`.

Il catalogo non salva nulla. Non legge nulla. Non implementa repository. Serve solo a rendere ufficiale la direzione del mapping.

## Regola su ID, codice e stato

Per gli aggregate attualmente coperti dal layer applicativo, il blueprint richiede sempre:

- un identificatore stabile (`id`);
- un codice business stabile (`code` o `fleetCode`);
- uno stato di lifecycle (`status`).

Questa regola è importante perché i repository futuri dovranno supportare le stesse operazioni già definite dalle repository port:

- salvataggio;
- ricerca per ID;
- ricerca per codice;
- verifica esistenza per ID;
- verifica esistenza per codice.

## Mapping dei value object

I value object del dominio non devono essere eliminati o bypassati.

Nel futuro mapping reale:

- lato persistence potranno essere rappresentati con primitive tecniche;
- lato domain dovranno essere ricostruiti tramite API pubbliche del dominio;
- le validazioni del dominio dovranno continuare a vivere nel dominio;
- l'infrastructure non deve inventare regole business parallele.

Esempi:

```text
LocationId       -> String tecnico futuro
LocationCode     -> String tecnico futuro
ShipmentStatus   -> String tecnico futuro
VehicleUnitId    -> String tecnico futuro
CountryCode      -> String tecnico futuro
```

La persistenza può semplificare la forma tecnica, ma non deve semplificare il significato del dominio.

## Mapping degli enum e degli stati

Gli enum e gli stati devono essere persistiti come valori stabili.

La regola consigliata è usare nomi testuali espliciti, non ordinali numerici fragili.

Quindi concettualmente:

```text
ShipmentStatus.CONFIRMED -> "CONFIRMED"
VehicleStatus.ACTIVE     -> "ACTIVE"
DocumentStatus.ARCHIVED  -> "ARCHIVED"
```

Questo rende più leggibili dati, migrazioni future, log tecnici e test.

## Mapping delle collection

Alcuni aggregate contengono collection:

- item di shipment;
- leg di shipment;
- requisiti di cargo;
- scope operativi;
- riferimenti tecnici.

Nel Punto 7D non decidiamo ancora se queste collection diventeranno:

- tabelle figlie;
- record embedded;
- strutture JSON interne;
- modelli tecnici separati.

Decidiamo solo il principio:

> Le collection devono essere persistite come parte del modello tecnico dell'aggregate, senza spostare regole business nell'infrastructure layer.

## Mapping delle reference

Quando un aggregate fa riferimento a un altro aggregate, l'infrastructure deve usare identificatori stabili.

Non deve salvare aggregate completi dentro altri aggregate.

Esempio concettuale:

```text
VehicleCombination -> vehicle_unit_ids
ShipmentLeg        -> location_id/reference tecnica
DocumentReference  -> referenced_object_id + reference_type
```

Questa scelta mantiene il confine tra bounded context e impedisce accoppiamenti impropri.

## Cosa NON è stato introdotto

Il Punto 7D non introduce:

- package `infrastructure.jpa`;
- package `infrastructure.database`;
- package `infrastructure.persistence`;
- entity JPA;
- annotazioni `@Entity`, `@Table`, `@Column`;
- `JpaRepository`;
- `CrudRepository`;
- `JdbcTemplate`;
- `EntityManager`;
- database reale;
- schema SQL;
- migration Flyway/Liquibase;
- repository reali;
- query reali;
- transazioni reali;
- controller REST;
- API pubbliche;
- security HTTP.

Questo è fondamentale: il Punto 7D prepara il mapping, ma non implementa ancora la persistenza.

## Test introdotto

Il test `PersistenceMappingBlueprintTest` verifica che:

- il catalogo copra tutti i contesti applicativi attivi;
- ogni blueprint abbia campi non vuoti;
- ogni blueprint abbia ID, codice business e stato;
- i record di blueprint rifiutino input invalidi;
- il lookup per contesto sia esplicito e null-safe;
- il package mapping non introduca JPA, Spring Data o framework di persistenza;
- domain e application layer non dipendano da `infrastructure.mapping`;
- la documentazione del Punto 7D sia presente.

## Relazione con il Punto 7E

Il Punto 7E sarà il primo momento in cui potremo scegliere un dominio pilota e costruire un repository reale prototipale.

Il candidato più sicuro resta:

```text
Locations
```

Motivo:

- è semplice;
- ha ID e codice chiari;
- non richiede collection complesse;
- non richiede workflow;
- permette di validare il pattern senza rischiare di sporcare domini più delicati.

Il Punto 7E introduce ora, in modo controllato:

- un persistence model reale per `Location`;
- un mapper concreto `LocationPersistenceMapper`;
- un repository adapter reale prototipale;
- test tecnici del mapping e del repository.

Queste cose sono state lasciate fuori dal Punto 7D e diventano il prototipo controllato del Punto 7E.

## Regola finale del Punto 7D

Il Punto 7D formalizza questa regola:

> Prima di scegliere una tecnologia di persistenza, TruckFlow Manager deve sapere cosa deve mappare, perché lo mappa e quali confini non deve superare.

Per questo il Punto 7D è un blueprint tecnico, non una persistence implementation.

## Stato dopo il Punto 7D

Dopo questo step il progetto ha:

- domain layer puro ancora isolato;
- application layer ancora indipendente dall'infrastructure;
- Spring wiring tecnico non web già presente dal Punto 7C;
- mapping blueprint ufficiale per i contesti applicativi attivi;
- test architetturali dedicati al mapping;
- documentazione aggiornata;
- nessun database reale;
- nessun JPA;
- nessun repository reale dentro il Punto 7D;
- nessun controller REST.

Lo step successivo è stato:

```text
Punto 7E — Real Repository Prototype
```

---

## Allineamento Punto 7F

Il Punto 7F — Repository Expansion estende il pattern file-backed validato dal prototipo Locations.

La prima espansione controllata aggiunge repository file-backed per Cargo, Documents e Compliance base, mantenendo fuori database, JPA, Hibernate, Spring Data, schema SQL, REST API, controller, security, servizi esterni, workflow e audit trail.

I repository in-memory restano validi e non vengono sostituiti.
