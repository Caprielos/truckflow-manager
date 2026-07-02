# Punto 7E — Real Repository Prototype

Questo documento descrive il **Punto 7E — Real Repository Prototype** di TruckFlow Manager.

Il Punto 7E è il primo passo in cui l'infrastructure layer non è più solo foundation, wiring o blueprint: introduce un **repository reale prototipale** per un dominio pilota, mantenendo però un confine molto prudente.

Il dominio scelto è **Locations**.

La scelta è intenzionale: `Location` è un aggregate stabile, semplice, con identità, codice business, stato, indirizzo e coordinate. Permette di validare il pattern completo senza introdurre subito la complessità di Shipments, Vehicles, Operational Roles o Compliance.

## Stato da cui partiamo

Prima del Punto 7E il progetto contiene già:

- domain layer puro, completato, testato e documentato;
- application layer completato e congelato con il Punto 6M;
- repository port definiti nell'application layer;
- repository in memory ancora validi per test e profilo `memory`;
- Punto 7A come blueprint generale dell'infrastructure layer;
- Punto 7B come foundation tecnica dell'infrastructure layer;
- Punto 7C come Spring wiring tecnico non web;
- Punto 7D come blueprint del mapping domain ↔ persistence;
- nessun controller REST;
- nessun database;
- nessun JPA;
- nessun Spring Data;
- nessuna security HTTP.

Il Punto 7E mantiene questi confini.

## Obiettivo del Punto 7E

L'obiettivo è validare il primo pattern completo:

```text
Application port.out
        ↓
Infrastructure repository adapter
        ↓
Persistence mapper
        ↓
Technical persistence model
        ↓
Technical storage mechanism
```

Per il 7E questo pattern viene applicato solo a `LocationRepository`.

Il risultato è un repository tecnico reale, ma volutamente piccolo e controllato.

## Perché Locations come dominio pilota

Locations è il dominio più adatto per il prototipo perché:

- ha un aggregate root chiaro: `Location`;
- ha un identificatore stabile: `LocationId`;
- ha un codice business stabile: `LocationCode`;
- ha uno stato semplice: `LocationStatus`;
- ha value object facili da mappare: `LocationAddress` e `GeoCoordinates`;
- non richiede collection figlie complesse;
- non richiede workflow;
- non richiede assegnazioni operative;
- non richiede calcoli di compatibilità;
- non richiede integrazioni esterne.

Questo permette di testare la forma infrastrutturale senza rischiare di sporcare il dominio o l'application layer.

## Cosa introduce il Punto 7E

Il Punto 7E introduce il package:

```text
src/main/java/it/gabriele/truckflow/infrastructure/repository/locations
```

Dentro questo package vengono aggiunti:

```text
FileLocationRepository.java
LocationPersistenceMapper.java
LocationPersistenceRecord.java
package-info.java
```

Viene inoltre aggiunto il test:

```text
src/test/java/it/gabriele/truckflow/infrastructure/repository/locations/FileLocationRepositoryPrototypeTest.java
```

## `LocationPersistenceRecord`

`LocationPersistenceRecord` è il primo modello tecnico persistente del progetto.

Non è:

- una entity JPA;
- una tabella SQL;
- un DTO web;
- un oggetto di dominio;
- un command applicativo;
- un result applicativo.

È un record tecnico usato solo dal prototipo file-backed.

Rappresenta in forma persistibile i dati necessari a ricostruire una `Location`:

- `id`;
- `code`;
- `name`;
- `type`;
- `status`;
- campi dell'indirizzo;
- latitudine;
- longitudine;
- note.

## `LocationPersistenceMapper`

`LocationPersistenceMapper` implementa il contratto generico:

```text
PersistenceMapper<Location, LocationPersistenceRecord>
```

Il mapper ha due responsabilità:

- trasformare una `Location` valida in `LocationPersistenceRecord`;
- ricostruire una `Location` valida da `LocationPersistenceRecord`.

Il mapper non deve introdurre regole business nuove.

Le regole restano nel dominio:

- validazione del `LocationCode`;
- validazione del nome;
- validazione delle coordinate;
- validazione dei value object.

Se la ricostruzione tecnica fallisce, l'errore viene espresso come `MappingException`.

## `FileLocationRepository`

`FileLocationRepository` è il primo repository reale prototipale.

Implementa:

```text
LocationRepository
InfrastructureRepositoryAdapter
```

Quindi è contemporaneamente:

- un adapter infrastrutturale;
- un'implementazione concreta della port.out applicativa `LocationRepository`.

Supporta le operazioni già previste dal contratto applicativo:

- `save`;
- `findById`;
- `findByCode`;
- `existsById`;
- `existsByCode`.

Il repository salva i record su un file locale tecnico.

Questa scelta è intenzionale: serve a validare la persistenza reale senza introdurre ancora un database.

## Perché file-backed e non database

Nel Punto 7E non vogliamo ancora scegliere una tecnologia definitiva.

Per questo il prototipo usa un file locale:

- è reale, perché i dati sopravvivono a una nuova istanza del repository;
- è semplice, perché non richiede JPA, SQL, schema o migrazioni;
- è tecnico, perché vive interamente nell'infrastructure layer;
- è reversibile, perché potrà essere sostituito da un adapter database futuro;
- è utile per testare mapper, repository e gestione errori.

Il Punto 7E non è il database definitivo del progetto.

È il primo prototipo del pattern.

## Formato tecnico del file

Il file repository usa una rappresentazione tecnica semplice:

- una location per riga;
- campi separati da tabulazione;
- ogni campo codificato in Base64 URL-safe;
- lettura e scrittura tramite `java.nio.file`.

Questa scelta evita di introdurre JSON, librerie esterne, database, JPA o Spring Data.

Il formato non è pensato come formato enterprise definitivo.

È un formato tecnico locale per validare il comportamento del repository reale.

## Error handling

Il Punto 7E mantiene separati gli errori:

- input applicativi nulli → `UseCaseValidationException`;
- codice location duplicato → `DuplicateResourceException`;
- problemi di lettura/scrittura file → `RepositoryException`;
- problemi di ricostruzione domain ↔ persistence → `MappingException`.

Questo conferma la regola generale:

> L'infrastructure può fallire per motivi tecnici, ma non deve trasformare quegli errori in regole di dominio false.

## Cosa viene testato

`FileLocationRepositoryPrototypeTest` verifica che:

- il repository implementi `LocationRepository`;
- il repository implementi `InfrastructureRepositoryAdapter`;
- una location salvata possa essere ritrovata per ID;
- una location salvata possa essere ritrovata per codice;
- una nuova istanza del repository possa rileggere i dati dal file;
- un codice business duplicato venga rifiutato;
- un file mancante venga trattato come repository vuoto;
- input nulli vengano rifiutati;
- un file malformato produca un errore tecnico;
- il mapper faccia round-trip tra domain e persistence record.

Questi sono test tecnici, non test di business.

Il business resta coperto dai test di dominio e application layer.

## Cosa NON introduce il Punto 7E

Il Punto 7E non introduce:

- database;
- JPA;
- Hibernate;
- Spring Data;
- repository reali per tutti i domini;
- schema SQL;
- migration tool;
- transazioni enterprise;
- controller REST;
- DTO web;
- JSON API;
- security;
- servizi esterni;
- storage documentale enterprise;
- audit trail;
- workflow.

## Relazione con i repository in memory

I repository in memory non vengono rimossi.

Restano validi per:

- test applicativi veloci;
- sviluppo locale leggero;
- profilo Spring `memory`;
- scenari didattici;
- verifica dei use case senza persistenza tecnica.

`FileLocationRepository` non sostituisce automaticamente `InMemoryLocationRepository`.

È un adapter prototipale separato.

Il wiring Spring resta sul profilo `memory` fino a una scelta esplicita futura.

## Relazione con il Punto 7F

Il Punto 7E valida il pattern su Locations.

Il Punto 7F potrà decidere se:

- estendere lo stesso approccio file-backed ad altri domini;
- introdurre un adapter database più serio;
- creare una seconda configurazione Spring per scegliere repository reali;
- iniziare da Cargo o Documents come secondo dominio.

La cosa importante è che il Punto 7F non dovrà inventare il pattern da zero: potrà riusare la struttura validata dal 7E.

## Stato dopo il Punto 7E

Dopo questo step il progetto ha:

- il primo repository reale prototipale;
- il primo persistence record concreto;
- il primo mapper concreto domain ↔ persistence;
- test tecnici dedicati al prototipo;
- documentazione aggiornata;
- domain layer ancora puro;
- application layer ancora indipendente dall'infrastructure;
- Spring ancora non web;
- nessun database;
- nessun JPA;
- nessun controller REST.

Il prossimo step naturale è:

```text
Punto 7F — Repository Expansion
```

---

## Allineamento Punto 7F

Il Punto 7F — Repository Expansion estende il pattern file-backed validato dal prototipo Locations.

La prima espansione controllata aggiunge repository file-backed per Cargo, Documents e Compliance base, mantenendo fuori database, JPA, Hibernate, Spring Data, schema SQL, REST API, controller, security, servizi esterni, workflow e audit trail.

I repository in-memory restano validi e non vengono sostituiti.


---

## Allineamento Punto 7G

Il Punto 7G conferma il prototipo Locations usando `FileLocationRepository` direttamente attraverso i use case applicativi `RegisterLocationService` e `FindLocationService`.
