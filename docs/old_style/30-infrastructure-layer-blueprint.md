# Archivio storico — 30-infrastructure-layer-blueprint

> Questo documento fa parte dell'archivio storico `docs/old_style/`.
> Mantiene il percorso step-by-step del progetto, ma la documentazione principale aggiornata si trova in:
> `docs/simple/`, `docs/professional/` e `docs/digital/`.

---

# Punto 7A — Infrastructure Layer Blueprint

Questo documento apre ufficialmente il **Punto 7 — Infrastructure Layer** di TruckFlow Manager.

> Aggiornamento 7B: il blueprint è stato trasformato in foundation tecnica leggera con package, eccezioni e convenzioni infrastrutturali documentate in `31-infrastructure-foundation.md`.
>
> Aggiornamento 7C: Spring è stato introdotto solo come wiring tecnico non web, documentato in `32-spring-wiring-foundation.md`, senza contaminare domain/application e senza introdurre REST, JPA, Spring Data o security.

Il Punto 7A è una fase **solo documentale e architetturale**: non introduce codice Java operativo, non crea repository reali, non configura database, non attiva controller REST e non cambia il comportamento dell'application layer.

Il suo scopo è fissare in modo chiaro, professionale e definitivo i confini del nuovo layer infrastrutturale prima di iniziare gli step implementativi successivi.

## Stato da cui partiamo

TruckFlow Manager ha già completato:

- il dominio puro, modellato, pulito, testato e documentato;
- il Punto 6 — Application Layer, completato da 6A a 6M;
- i primi use case applicativi per Locations, Cargo, Shipments, Documents, Vehicles, Operational Roles e Compliance base;
- repository in memory usati come adapter tecnici leggeri per test e sviluppo locale;
- documentazione Markdown e digitale allineata fino al freeze dell'application layer.

Dopo il Punto 6M il sistema ha una base pulita:

```text
Domain Layer
Application Layer
In-Memory Infrastructure Adapter
```

Il Punto 7 prepara il passaggio verso:

```text
Domain Layer
Application Layer
Infrastructure Layer reale
```

senza introdurre ancora API REST, controller, JSON, sicurezza HTTP o frontend.

## Obiettivo del Punto 7

Il Punto 7 ha un obiettivo preciso:

> dare all'application layer implementazioni tecniche reali dei suoi port.out, mantenendo domain e application layer indipendenti da database, framework, file system, servizi esterni e dettagli di configurazione.

In altre parole, l'infrastructure layer deve occuparsi del **con cosa** il sistema lavora:

- database;
- file system;
- storage documentale;
- servizi esterni;
- configurazioni;
- logging tecnico;
- mapping verso modelli persistenti;
- wiring con Spring;
- adapter tecnici.

Il dominio e l'application layer continuano invece a occuparsi del **cosa** e del **come applicativo**.

## Filosofia ufficiale del Punto 7

Il Punto 7 segue questi principi:

1. il dominio resta puro;
2. l'application layer resta indipendente da Spring, database, JPA, controller e file system;
3. l'infrastructure layer implementa i port.out definiti dall'application layer;
4. Spring è un dettaglio tecnico di wiring e configurazione;
5. i repository in memory non vengono rimossi;
6. l'infrastruttura reale viene introdotta a piccoli passi;
7. ogni adapter deve rispettare i contratti applicativi già definiti;
8. ogni errore tecnico deve restare fuori dal dominio;
9. REST API e controller appartengono al Punto 8, non al Punto 7;
10. ogni sotto-step del Punto 7 deve aggiornare documentazione e test coerenti.

## Architettura esagonale nel Punto 7

Il progetto segue una logica di **ports and adapters**.

Il centro è composto da:

```text
Domain
Application
```

L'esterno è composto da:

```text
Infrastructure
Web/API future
External systems future
```

Nel Punto 7 l'infrastructure layer diventa il primo vero layer esterno stabile.

### Direzione delle dipendenze

Le dipendenze devono sempre puntare verso il centro:

```text
infrastructure -> application -> domain
```

Sono vietate dipendenze inverse come:

```text
domain -> application
domain -> infrastructure
application -> infrastructure
application -> web
application -> JPA
application -> Spring controller
```

L'infrastructure layer può conoscere:

- i port.out dell'application layer;
- i modelli di dominio necessari a implementare quei port;
- librerie tecniche;
- Spring come wiring;
- modelli persistenti propri dell'infrastruttura.

## Cosa entra nel Punto 7

Il Punto 7 può introdurre progressivamente:

- package infrastrutturali ordinati;
- eccezioni infrastrutturali;
- configurazioni tecniche;
- wiring Spring controllato;
- mapper domain ↔ persistence;
- modelli persistenti;
- repository reali che implementano port.out;
- adapter verso database o storage;
- test tecnici di repository, mapper e configurazione;
- documentazione dei confini infrastrutturali.

Questi elementi devono sempre rimanere separati dal dominio e dall'application layer.

## Cosa NON entra nel Punto 7

Il Punto 7 non deve introdurre:

- controller REST;
- DTO web;
- endpoint HTTP;
- mapping JSON di API;
- Swagger/OpenAPI operativo;
- security HTTP;
- JWT;
- frontend;
- ruoli utente web;
- autorizzazioni applicative esposte via API;
- dashboard;
- tracking operativo;
- planning;
- dispatching reale;
- workflow enterprise completi.

Questi elementi appartengono a punti successivi, soprattutto al **Punto 8 — API Layer** e ai moduli enterprise futuri.

## Repository in memory: regola importante

I repository in memory introdotti nel Punto 6 non devono essere considerati provvisori da eliminare.

Restano utili per:

- test applicativi;
- scenari locali;
- prototipazione;
- dimostrazione dei use case;
- sviluppo senza database;
- verifica dei contratti port.out.

Il Punto 7 non sostituisce automaticamente gli adapter in memory. Li affianca con adapter infrastrutturali più realistici.

La regola corretta è:

> l'application layer vede solo i port.out; quale adapter venga usato è una scelta infrastrutturale.

## Spring nel Punto 7

Spring può entrare nel Punto 7, ma solo come tecnologia di wiring.

Spring può servire per:

- creare bean;
- collegare use case e repository;
- gestire profili;
- caricare configurazioni;
- separare adapter in memory e adapter reali;
- preparare test di integrazione tecnica.

Spring non deve entrare in:

- dominio;
- value object;
- entity di dominio;
- application command;
- application result;
- port in;
- port out;
- application service come requisito architetturale.

Se un use case funziona solo perché conosce Spring, il confine è stato violato.

## Database e persistenza

Il Punto 7 può preparare la persistenza reale, ma non deve farlo in modo improvvisato.

Prima di introdurre database completi bisogna definire:

- quali aggregate persistiamo per primi;
- quale modello persistente usiamo;
- come mappiamo value object e primitive;
- come gestiamo gli enum;
- come rappresentiamo gli stati;
- come evitiamo di deformare il dominio per adattarlo al database;
- quali test garantiscono la correttezza del mapping.

Il database non deve comandare il dominio. Il database è un dettaglio tecnico.

## Mapping domain ↔ persistence

Il mapping è una responsabilità infrastrutturale.

Il dominio contiene oggetti espressivi:

- entity;
- aggregate;
- value object;
- enum;
- stati;
- invarianti.

La persistenza contiene strutture tecniche:

- tabelle;
- colonne;
- chiavi;
- record;
- documenti persistenti;
- tipi primitivi.

Il mapper traduce tra questi due mondi.

Regole:

- il mapper non deve contenere regole business nuove;
- il mapper non deve bypassare invarianti del dominio;
- il mapper non deve trasformare il dominio in DTO anemici;
- il mapper deve essere testato;
- il mapper deve appartenere all'infrastructure layer.

## Eccezioni infrastrutturali

Gli errori tecnici non sono errori di dominio.

Esempi di errori infrastrutturali:

- database non disponibile;
- configurazione mancante;
- errore di connessione;
- mapping fallito;
- file non leggibile;
- servizio esterno non raggiungibile;
- timeout;
- credenziali mancanti.

Questi errori devono essere rappresentati con eccezioni infrastrutturali, per esempio:

- `InfrastructureException`;
- `RepositoryException`;
- `ExternalServiceException`.

Il dominio non deve mai conoscere queste eccezioni.

## Struttura concettuale futura

Il Punto 7 potrà evolvere verso una struttura simile:

```text
src/main/java/it/gabriele/truckflow/infrastructure
├── config
├── repository
├── adapter
├── mapping
├── service
├── exception
└── memory
```

La cartella `memory` può continuare a contenere gli adapter in memory esistenti.

Gli altri package verranno introdotti solo quando lo step specifico li richiederà.

## Sotto-step ufficiali del Punto 7

Il Punto 7 sarà composto da otto sotto-step progressivi.

Non sono tutti solo teorici: alcuni sono documentali, altri sono implementativi, ma tutti devono essere piccoli, controllabili e coerenti.

| Punto | Nome | Natura | Obiettivo |
|---|---|---|---|
| 7A | Infrastructure Blueprint | Documentale | Definire principi, confini, roadmap e regole del layer infrastrutturale |
| 7B | Infrastructure Foundation | Implementativa leggera | Creare struttura package, eccezioni e convenzioni infrastrutturali |
| 7C | Spring Wiring Foundation | Implementativa controllata | Introdurre wiring Spring senza controller, REST, JPA o security |
| 7D | Persistence Mapping Blueprint | Documentale + struttura | Definire strategia di mapping domain ↔ persistence |
| 7E | Real Repository Prototype | Implementativa pilota | Implementare il primo repository reale prototipale su Locations |
| 7F | Repository Expansion | Implementativa progressiva | Estendere il pattern agli altri domini prioritari |
| 7G | Infrastructure Testing | Tecnica | Consolidare test di mapper, repository, config e adapter |
| 7H | Infrastructure Review & Freeze | Review | Congelare il Punto 7 prima del Punto 8 |

## 7A — Infrastructure Blueprint

Il Punto 7A è lo step corrente.

Introduce solo documentazione e decisioni architetturali.

Produce:

- roadmap ufficiale del Punto 7;
- confini del layer infrastrutturale;
- lista di cosa entra e cosa resta fuori;
- regole di dipendenza;
- ruolo dei port.out;
- ruolo degli adapter;
- ruolo di Spring;
- ruolo dei repository in memory;
- criteri di accettazione per i prossimi step.

Non produce:

- repository reali;
- entity JPA;
- configurazione database;
- controller;
- endpoint;
- DTO web;
- security;
- workflow;
- integrazioni esterne.

## 7B — Infrastructure Foundation

Il Punto 7B crea la base strutturale del layer infrastrutturale.

Contenuti applicati:

- package `infrastructure.config`;
- package `infrastructure.repository`;
- package `infrastructure.adapter`;
- package `infrastructure.mapping`;
- package `infrastructure.service`;
- package `infrastructure.exception`;
- eccezioni infrastrutturali base;
- marker e convenzioni tecniche;
- profili infrastrutturali;
- contratto generico di mapping;
- test architetturale minimo.

Il 7B non introduce database reale completo, JPA, Spring Data, REST API, controller o security.

Il 7B è documentato in `31-infrastructure-foundation.md`.

## 7C — Spring Wiring Foundation

Il Punto 7C introdurrà Spring come motore tecnico di wiring.

Possibili contenuti:

- configurazioni bean;
- profili tecnici;
- `application.yml` minimale;
- wiring tra port.out e adapter;
- test di contesto tecnico.

Vincoli:

- nessun controller;
- nessuna REST API;
- nessun endpoint;
- nessuna security;
- nessun DTO web.

## 7D — Persistence Mapping Blueprint

Il Punto 7D definirà la strategia di mapping.

Possibili contenuti:

- convenzioni per mapper;
- regole per value object;
- regole per enum;
- regole per stati;
- documentazione dei mapping prioritari;
- struttura dei package mapper.

Il 7D può preparare classi o interfacce leggere, ma non deve forzare subito tutti i domini dentro un database.

## 7E — Real Repository Prototype

Il Punto 7E implementa il primo repository reale pilota, scegliendo Locations come dominio sicuro e usando un adapter file-backed separato dal profilo `memory`.

Il dominio consigliato è **Locations**, perché è più semplice e riduce il rischio architetturale.

Alternative possibili:

- Documents, se si vuole preparare presto storage e document management;
- Cargo, se si vuole validare un aggregate più ricco.

La scelta consigliata resta Locations.

Il risultato atteso è un pattern chiaro e replicabile.

## 7F — Repository Expansion

Il Punto 7F estenderà il pattern ai domini prioritari.

Possibili candidati:

- Cargo;
- Documents;
- Vehicles;
- Shipments;
- Operational Roles;
- Compliance base.

L'espansione deve essere progressiva. Non è obbligatorio portare tutti i domini a repository reale in un singolo step.

## 7G — Infrastructure Testing

Il Punto 7G consoliderà i test tecnici.

Aree di test:

- mapper;
- repository reali;
- configurazioni;
- adapter;
- wiring Spring;
- profili;
- fallback verso repository in memory.

Questi test sono tecnici. Non devono duplicare i test di business già presenti in domain e application.

## 7H — Infrastructure Review & Freeze

Il Punto 7H chiuderà il ciclo infrastrutturale.

Verificherà:

- confini rispettati;
- repository reali coerenti con port.out;
- mapper testati;
- configurazione chiara;
- nessun controller REST anticipato;
- nessuna security HTTP anticipata;
- documentazione aggiornata;
- readiness per il Punto 8.

Dopo il 7H sarà possibile aprire il Punto 8 — API Layer.

## Decisioni ufficiali del Punto 7A

Le decisioni ufficiali sono:

1. il Punto 7 si chiama **Infrastructure Layer**;
2. il Punto 7A è solo documentale;
3. il Punto 7 non introduce REST API;
4. il Punto 7 non introduce controller;
5. il Punto 7 non introduce DTO web;
6. il Punto 7 non introduce security HTTP;
7. Spring potrà essere usato solo come wiring tecnico;
8. i repository in memory restano nel progetto;
9. i repository reali implementeranno port.out dell'application layer;
10. il primo repository reale consigliato è Locations;
11. mapping, persistence model e adapter devono stare in infrastructure;
12. il Punto 8 sarà il momento corretto per esporre API.

## Criteri di accettazione del Punto 7A

Il Punto 7A è completo quando:

- esiste un documento ufficiale del blueprint infrastrutturale;
- la roadmap del Punto 7 è esplicitata da 7A a 7H;
- la documentazione principale cita l'apertura del Punto 7;
- la documentazione digitale include il nuovo documento;
- la documentazione enterprise chiarisce lo stato futuro dell'infrastructure layer;
- non è stato modificato codice Java operativo;
- non sono stati aggiunti controller, repository reali, JPA, database o security.

## Relazione con il Punto 8

Il Punto 8 sarà il layer API.

Il Punto 8 potrà introdurre:

- controller REST;
- DTO web;
- mapping API ↔ application;
- error handling HTTP;
- validazione request/response;
- Swagger/OpenAPI operativo;
- security HTTP.

Il Punto 7 deve preparare una base infrastrutturale stabile affinché il Punto 8 possa esporre use case applicativi senza accedere direttamente al dominio e senza improvvisare repository o persistence.

## Sintesi finale

Il Punto 7A apre l'Infrastructure Layer in modo prudente e professionale. I successivi punti 7B, 7C, 7D e 7E hanno iniziato ad applicare la roadmap: foundation tecnica, wiring Spring non web, mapping blueprint e primo repository reale prototipale su Locations.

Non implementa subito database o Spring operativo. Prima definisce:

- principi;
- confini;
- roadmap;
- dipendenze consentite;
- dipendenze vietate;
- ruolo degli adapter;
- ruolo dei repository reali;
- ruolo dei repository in memory;
- rapporto tra Punto 7 e Punto 8.

Questa fase ha reso il progetto pronto a passare agli step successivi del Punto 7 senza perdere la pulizia architetturale costruita fino al Punto 6M.

---

## Allineamento Punto 7F

Il Punto 7F — Repository Expansion estende il pattern file-backed validato dal prototipo Locations.

La prima espansione controllata aggiunge repository file-backed per Cargo, Documents e Compliance base, mantenendo fuori database, JPA, Hibernate, Spring Data, schema SQL, REST API, controller, security, servizi esterni, workflow e audit trail.

I repository in-memory restano validi e non vengono sostituiti.


---

## Allineamento Punto 7G

Il Punto 7G conferma questa impostazione: prima di procedere al freeze finale, l'Infrastructure Layer viene rafforzato con test tecnici su storage, adapter file-backed, integrazione con use case applicativi e confini architetturali.


## Allineamento Punto 7H

Il Punto 7H conferma e chiude la roadmap 7A → 7H definita in questo blueprint. L'Infrastructure Layer resta tecnico, progressivo e separato dal futuro API Layer. Il freeze finale non introduce REST, controller, database, JPA, Spring Data o security; aggiunge invece un test finale e una documentazione di chiusura.
