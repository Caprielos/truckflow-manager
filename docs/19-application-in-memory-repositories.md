# Punto 6D — In-Memory Repositories

Il **Punto 6D** introduce le prime implementazioni concrete, ma ancora leggere e temporanee, delle repository port create nel Punto 6C.

Questa fase non introduce database, JPA, Spring, file system, API esterne o persistenza definitiva. L’obiettivo è avere repository utilizzabili da test e futuri use case applicativi, mantenendo separati dominio, application layer e infrastruttura.

## Obiettivo dello step

Il Punto 6D serve a trasformare i contratti astratti dell’application layer in adapter concreti in memoria.

Nel Punto 6C sono state introdotte le porte:

- `LocationRepository`;
- `CargoUnitRepository`;
- `ShipmentRepository`.

Con il Punto 6D vengono introdotte le prime implementazioni:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`.

Queste classi permettono di salvare, cercare e verificare aggregate tramite ID e codice senza dipendere da un database reale.

## Perché servono repository in memory

Le repository in memory sono utili perché permettono di:

- testare i primi use case senza introdurre subito un database;
- verificare che le repository port siano realmente implementabili;
- preparare scenari applicativi completi per Locations, Cargo e Shipments;
- mantenere il dominio completamente indipendente dall’infrastruttura;
- evitare di introdurre JPA, Spring Data o SQL prima che l’application layer sia stabile.

Questa scelta è coerente con l’architettura del progetto: prima si costruiscono dominio e application layer, poi si introducono adapter tecnici più avanzati.

## Package introdotti

La nuova infrastruttura leggera è organizzata in:

- `infrastructure`;
- `infrastructure.memory`;
- `infrastructure.memory.locations`;
- `infrastructure.memory.cargo`;
- `infrastructure.memory.shipments`.

Il package `infrastructure.memory` è un adapter tecnico. Implementa porte dell’application layer, ma non modifica e non sporca il domain layer.

## Repository implementate

### InMemoryLocationRepository

Implementa `LocationRepository` e gestisce aggregate `Location`.

Responsabilità principali:

- salvare una location;
- cercare una location tramite `LocationId`;
- cercare una location tramite `LocationCode`;
- verificare esistenza per ID;
- verificare esistenza per codice;
- impedire codici duplicati su location diverse.

### InMemoryCargoUnitRepository

Implementa `CargoUnitRepository` e gestisce aggregate `CargoUnit`.

Responsabilità principali:

- salvare una cargo unit;
- cercare una cargo unit tramite `CargoId`;
- cercare una cargo unit tramite `CargoCode`;
- verificare esistenza per ID;
- verificare esistenza per codice;
- impedire codici duplicati su cargo unit diverse.

### InMemoryShipmentRepository

Implementa `ShipmentRepository` e gestisce aggregate `Shipment`.

Responsabilità principali:

- salvare una shipment;
- cercare una shipment tramite `ShipmentId`;
- cercare una shipment tramite `ShipmentCode`;
- verificare esistenza per ID;
- verificare esistenza per codice;
- impedire codici duplicati su shipment diverse.

## Regole applicative protette

Le repository in memory non duplicano le regole di dominio.

Non decidono, per esempio:

- quando una shipment può essere confermata;
- se un cargo ADR è coerente;
- se una temperatura è valida;
- se una location è anagraficamente corretta.

Queste regole restano nel dominio.

Le repository in memory proteggono invece regole applicative e tecniche minime:

- non accettare aggregate nulli;
- non cercare tramite ID nullo;
- non cercare tramite codice nullo;
- non salvare due aggregate diversi con lo stesso codice;
- mantenere un indice per ID e uno per codice.

## Eccezioni usate

Per input applicativi nulli viene usata:

- `UseCaseValidationException`.

Per duplicati di codice viene usata:

- `DuplicateResourceException`.

Queste sono eccezioni applicative, non eccezioni di dominio.

La differenza è importante:

- il dominio segnala violazioni di regole business interne agli aggregate;
- l’application/infrastructure segnala errori di orchestrazione, input, lookup o persistenza temporanea.

## Cosa è stato testato

Sono stati aggiunti test dedicati per verificare che le repository in memory:

- implementino le repository port dell’application layer;
- salvino aggregate validi;
- recuperino aggregate per ID;
- recuperino aggregate per codice;
- riconoscano l’esistenza per ID e codice;
- restituiscano `Optional.empty()` quando una risorsa non esiste;
- rifiutino input nulli con `UseCaseValidationException`;
- rifiutino codici duplicati con `DuplicateResourceException`;
- restino prive di dipendenze da Spring, JPA, Lombok e web adapter.

## Cosa non è stato fatto

Il Punto 6D non introduce ancora:

- database reale;
- JPA;
- Spring Data;
- transazioni;
- query avanzate;
- paginazione;
- ordinamenti;
- filtri complessi;
- locking concorrente;
- caching enterprise;
- audit trail;
- repository per tutti i domini;
- use case applicativi specifici;
- controller REST.

Queste funzionalità non appartengono a questa fase.

## Perché non introdurre subito un database

Introdurre subito un database renderebbe il progetto più complesso prima di avere use case stabili.

Per ora è più corretto avere repository in memory perché:

- i test sono più semplici e veloci;
- l’application layer può evolvere senza vincoli di mapping JPA;
- gli aggregate restano liberi da annotazioni tecniche;
- si possono cambiare i contratti prima di stabilizzare la persistenza definitiva;
- si riduce il rischio di progettare il database prima del comportamento applicativo.

## Relazione con il Punto 6E

Il Punto 6D prepara direttamente il prossimo step:

**Punto 6E — First Use Cases**.

Ora che esistono repository port e repository in memory, i primi use case potranno:

- ricevere command applicativi;
- creare o caricare aggregate;
- usare il dominio;
- salvare tramite repository port;
- essere testati usando repository in memory.

Il primo flusso applicativo consigliato rimane:

1. registrare una location di partenza;
2. registrare una location di destinazione;
3. registrare un cargo;
4. creare una shipment;
5. aggiungere item e leg;
6. confermare la shipment.

## Stato dopo il Punto 6D

Dopo questo step, il progetto contiene:

- domain layer puro e testato;
- application foundation;
- repository port applicative;
- repository in memory concrete per Locations, Cargo e Shipments;
- test infrastrutturali leggeri;
- documentazione aggiornata.

Il progetto è quindi pronto per iniziare i primi use case applicativi senza introdurre ancora web, database o framework.

## Aggiornamento dopo il Punto 6E

Le repository in memory introdotte nel Punto 6D sono ora usate nel primo scenario applicativo completo.

Il test applicativo del Punto 6E usa `InMemoryLocationRepository`, `InMemoryCargoUnitRepository` e `InMemoryShipmentRepository` per simulare un flusso reale senza database:

1. registrazione delle location;
2. registrazione del cargo;
3. creazione della shipment;
4. salvataggio delle mutazioni successive;
5. recupero della shipment finale.

Questo conferma che gli adapter in memory sono sufficienti per testare l'orchestrazione applicativa prima di introdurre persistenza enterprise.

## Collegamento con il Punto 6F

Le repository in memory introdotte nel Punto 6D vengono usate nel Punto 6F per testare scenari applicativi più severi.

I test verificano che una shipment cancellata venga salvata correttamente, che un cargo duplicato venga rifiutato, che le risorse mancanti producano errori applicativi e che una mutazione fallita non lasci stato parziale nell'aggregate salvato.

## Aggiornamento dopo il Punto 6G — InMemoryDocumentRepository

Il Punto 6G estende l'infrastructure memory aggiungendo:

```text
infrastructure.memory.documents.InMemoryDocumentRepository
```

Questa classe implementa `DocumentRepository` e gestisce aggregate `Document` in memoria.

Responsabilità principali:

- salvare un documento;
- cercare un documento tramite `DocumentId`;
- cercare un documento tramite `DocumentCode`;
- verificare esistenza per ID;
- verificare esistenza per codice;
- impedire codici duplicati su documenti diversi;
- rifiutare input nulli con `UseCaseValidationException`.

La repository resta un adapter temporaneo e leggero. Non introduce database, JPA, Spring Data, storage fisico, upload, download, versioning documentale reale o workflow approvativi.

`InMemoryRepositoryTest` è stato aggiornato per verificare anche il comportamento di `InMemoryDocumentRepository`.
