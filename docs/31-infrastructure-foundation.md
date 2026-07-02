# Punto 7B — Infrastructure Foundation

Questo documento descrive il **Punto 7B — Infrastructure Foundation** di TruckFlow Manager.

> Aggiornamento 7C: la foundation del 7B è ora usata dal wiring Spring controllato documentato in `32-spring-wiring-foundation.md`. Il 7C aggiunge configurazioni bean e bootstrap tecnico, ma non introduce repository reali, database, REST API, JPA o security.

Il Punto 7B è il primo passo implementativo leggero del nuovo ciclo infrastrutturale aperto con il Punto 7A. Non introduce ancora database, JPA, Spring Data, controller REST, security o repository reali completi. Il suo obiettivo è creare una base tecnica ordinata in cui gli step successivi del Punto 7 possano inserire wiring Spring, mapping, adapter e repository reali senza rompere i confini costruiti nel dominio e nell'application layer.

## Stato da cui partiamo

Prima del Punto 7B il progetto contiene già:

- dominio puro completato e documentato;
- application layer completato e congelato con il Punto 6M;
- roadmap finale allineata dopo il Punto 6M;
- Punto 7A documentato con il blueprint dell'Infrastructure Layer;
- repository in memory esistenti sotto `infrastructure.memory`;
- nessun controller REST operativo;
- nessun database reale;
- nessuna entity JPA;
- nessuna security HTTP.

Il Punto 7B non cambia questa scelta. Aggiunge solo la **fondazione tecnica** del layer infrastrutturale.

## Obiettivo del Punto 7B

L'obiettivo è creare una struttura infrastrutturale chiara e stabile:

```text
src/main/java/it/gabriele/truckflow/infrastructure
├── adapter
├── config
├── exception
├── mapping
├── repository
├── service
└── memory
```

Questa struttura serve a separare responsabilità tecniche diverse:

- `adapter` contiene convenzioni per adapter tecnici;
- `config` contiene concetti di configurazione infrastrutturale;
- `exception` contiene eccezioni tecniche;
- `mapping` contiene contratti per tradurre domain ↔ persistence;
- `repository` contiene convenzioni per futuri repository reali;
- `service` contiene convenzioni per servizi tecnici infrastrutturali;
- `memory` resta il package degli adapter in memory già esistenti.

## Cosa è stato introdotto

Il Punto 7B introduce:

- package infrastrutturali documentati con `package-info.java`;
- un contratto marker `InfrastructureAdapter`;
- un enum tecnico `InfrastructureProfile`;
- una gerarchia base di eccezioni infrastrutturali;
- un contratto generico `PersistenceMapper`;
- un marker per repository adapter infrastrutturali;
- un marker per servizi tecnici infrastrutturali;
- un test architetturale dedicato alla foundation infrastrutturale.

Questi elementi sono volutamente piccoli. Il loro scopo è fissare linguaggio, convenzioni e confini, non implementare subito persistenza reale.

## Cosa NON è stato introdotto

Il Punto 7B non introduce:

- database;
- JPA;
- Hibernate;
- Spring Data;
- repository reali completi;
- entity persistenti;
- controller REST;
- DTO web;
- endpoint HTTP;
- security HTTP;
- JWT;
- application service nuovi;
- use case nuovi;
- mapping concreto di un dominio;
- integrazioni esterne;
- file storage reale;
- email service operativo;
- audit trail operativo.

Queste scelte sono intenzionali. Il Punto 7B deve essere una foundation, non un salto diretto a una infrastruttura completa.

## Package `infrastructure.adapter`

Il package `infrastructure.adapter` definisce il concetto tecnico di adapter.

Un adapter è una classe infrastrutturale che collega un contratto astratto del core a un dettaglio tecnico esterno o interno.

Esempi futuri:

- repository database adapter;
- storage adapter;
- email adapter;
- document generation adapter;
- external service adapter;
- technical logging adapter.

Il contratto `InfrastructureAdapter` espone solo un nome tecnico stabile tramite `adapterName()`.

Questo non è business logic. Serve solo a rendere più chiari diagnostica, documentazione e convenzioni future.

## Package `infrastructure.config`

Il package `infrastructure.config` prepara il terreno per la configurazione tecnica.

Nel Punto 7B viene introdotto `InfrastructureProfile` con quattro profili:

- `MEMORY`;
- `LOCAL`;
- `TEST`;
- `PRODUCTION`.

Questi profili non attivano ancora Spring. Servono a stabilire una nomenclatura tecnica che sarà utile nel Punto 7C, quando Spring verrà introdotto come motore di wiring.

Il significato è:

- `MEMORY`: adapter in memory per test e scenari locali;
- `LOCAL`: configurazione locale per sviluppo;
- `TEST`: configurazione tecnica per test automatizzati;
- `PRODUCTION`: configurazione futura per ambiente reale.

## Package `infrastructure.exception`

Gli errori tecnici non devono finire nel dominio.

Per questo il Punto 7B introduce una gerarchia base:

```text
InfrastructureException
├── RepositoryException
├── ExternalServiceException
├── InfrastructureConfigurationException
└── MappingException
```

### `InfrastructureException`

È la radice delle eccezioni tecniche infrastrutturali.

Rappresenta problemi del mondo tecnico, non violazioni di regole business.

### `RepositoryException`

Rappresenta errori tecnici di persistenza o repository adapter.

Esempi futuri:

- database non raggiungibile;
- errore durante una query;
- timeout di persistenza;
- vincolo tecnico non gestibile a livello domain.

### `ExternalServiceException`

Rappresenta errori nel dialogo con servizi esterni.

Esempi futuri:

- servizio esterno non disponibile;
- risposta non valida;
- timeout;
- credenziali tecniche rifiutate.

### `InfrastructureConfigurationException`

Rappresenta errori di configurazione.

Esempi futuri:

- property mancante;
- profilo non valido;
- endpoint tecnico non configurato;
- credenziale mancante.

### `MappingException`

Rappresenta errori di traduzione tra domain model e modello tecnico.

Esempi futuri:

- enum persistito non riconosciuto;
- valore tecnico impossibile da ricostruire come value object;
- record persistente incompleto.

## Package `infrastructure.mapping`

Il package `infrastructure.mapping` prepara il concetto di mapping domain ↔ persistence.

Il contratto `PersistenceMapper<D, P>` definisce due direzioni:

- `toPersistence(D domainModel)`;
- `toDomain(P persistenceModel)`.

Il mapper è infrastrutturale perché traduce tra:

- un modello di dominio espressivo;
- un modello tecnico persistibile.

Regole fondamentali:

- il mapper non deve inventare regole business;
- il mapper non deve bypassare invarianti del dominio;
- il mapper non deve costringere il dominio ad adattarsi al database;
- il mapper deve usare API pubbliche del dominio;
- il mapper dovrà essere testato quando arriveranno i mapping concreti.

Nel Punto 7B non viene ancora creato un mapper concreto per Locations, Shipments, Vehicles o altri domini. Questo arriverà più avanti.

## Package `infrastructure.repository`

Il package `infrastructure.repository` ospiterà i futuri repository reali.

Il Punto 7B introduce solo il marker `InfrastructureRepositoryAdapter`, che rappresenta un adapter repository infrastrutturale.

Un repository adapter reale dovrà:

- implementare un port.out dell'application layer;
- usare mapping infrastrutturale quando serve;
- nascondere il dettaglio tecnico all'application layer;
- tradurre errori tecnici in eccezioni infrastrutturali coerenti;
- non contenere regole business nuove.

I repository in memory esistenti restano in `infrastructure.memory`. Non vengono spostati e non vengono sostituiti.

## Package `infrastructure.service`

Il package `infrastructure.service` ospiterà futuri servizi tecnici.

Esempi futuri:

- document storage;
- document generation;
- email notification;
- technical logging;
- audit adapter;
- external integration service.

Nel Punto 7B viene introdotto solo il marker `InfrastructureService`.

Non viene implementato nessun servizio operativo.

## Rapporto con `infrastructure.memory`

`infrastructure.memory` resta valido.

Non è un package da cancellare.

Gli adapter in memory restano utili per:

- test applicativi;
- sviluppo locale;
- prototipi;
- verifica dei port.out;
- scenari senza database.

Il Punto 7B affianca nuovi package infrastrutturali al package memory, ma non lo sostituisce.

La regola resta:

> l'application layer conosce solo i port.out; la scelta dell'adapter è responsabilità dell'infrastructure layer.

## Confini architetturali dopo il 7B

Dopo il Punto 7B, la direzione delle dipendenze resta:

```text
infrastructure -> application -> domain
```

Sono ancora vietate:

```text
domain -> application
domain -> infrastructure
application -> infrastructure
application -> web
application -> JPA
application -> Spring controller
```

L'infrastructure layer può dipendere da application e domain, ma in questo step i nuovi contratti sono volutamente neutrali e non introducono ancora implementazioni collegate a port.out specifici.

## Test introdotti

Il Punto 7B introduce `InfrastructureFoundationTest`.

Il test verifica:

- presenza dei package infrastrutturali fondamentali;
- presenza dei `package-info.java`;
- gerarchia delle eccezioni infrastrutturali;
- profili tecnici dichiarati;
- assenza di controller, JPA, Spring Data e security nel foundation step;
- assenza di dipendenze da domain/application verso infrastructure;
- presenza della documentazione del Punto 7A e 7B;
- assenza di package prematuri `infrastructure.jpa` e `infrastructure.database`.

Questo test non sostituisce i test futuri di repository reali o mapping concreti. Serve solo a proteggere la foundation.

## Decisioni ufficiali del Punto 7B

Le decisioni ufficiali sono:

1. il package `infrastructure.memory` resta nel progetto;
2. i nuovi package infrastrutturali sono foundation, non implementazioni database;
3. le eccezioni tecniche partono da `InfrastructureException`;
4. gli errori tecnici non sono errori di dominio;
5. il mapping domain ↔ persistence è responsabilità infrastrutturale;
6. i futuri repository reali dovranno implementare port.out applicativi;
7. Spring non è ancora stato attivato come wiring operativo;
8. REST API e controller restano fuori dal Punto 7B;
9. JPA, Hibernate e Spring Data restano fuori dal Punto 7B;
10. il prossimo step corretto è il Punto 7C — Spring Wiring Foundation.

## Criteri di accettazione del Punto 7B

Il Punto 7B è completo quando:

- esistono i package infrastrutturali base;
- ogni package base è documentato;
- esiste una gerarchia di eccezioni infrastrutturali;
- esistono contratti minimi per adapter, mapping, repository adapter e servizi tecnici;
- i repository in memory restano intatti;
- nessun controller REST è stato introdotto;
- nessun database reale è stato introdotto;
- nessuna entity JPA è stata introdotta;
- nessun repository Spring Data è stato introdotto;
- la documentazione Markdown è aggiornata;
- la documentazione digitale è aggiornata;
- i test architetturali verificano i confini principali.

## Relazione con il Punto 7C

Il Punto 7C sarà **Spring Wiring Foundation**.

Dopo il 7B, il progetto ha package e convenzioni pronte per accogliere Spring come motore tecnico di wiring.

Il 7C potrà introdurre:

- configurazioni Spring controllate;
- eventuali classi `@Configuration`;
- bean per adapter in memory;
- profili tecnici;
- file di configurazione minimale;
- test di wiring tecnico.

Anche nel 7C resteranno esclusi:

- controller REST;
- endpoint HTTP;
- DTO web;
- security HTTP;
- JPA completa;
- repository reali complessi.

## Sintesi finale

Il Punto 7B trasforma il blueprint del Punto 7A in una base tecnica concreta ma prudente.

Non costruisce ancora l'infrastruttura reale completa. Prepara invece il terreno con:

- package chiari;
- eccezioni tecniche;
- marker di adapter;
- profili tecnici;
- contratto generico di mapping;
- convenzioni per repository adapter e servizi tecnici;
- test architetturali.

Questa foundation permette di passare al Punto 7C senza improvvisare e senza perdere la pulizia architetturale raggiunta fino al Punto 6M.
