# Roadmap finale e allineamento documentale

Questo documento chiude la pulizia documentale successiva al **Punto 6M — Application Layer Final Review & Freeze**.

Serve a rendere esplicita la roadmap reale del progetto, correggere alcune descrizioni storiche ormai superate e chiarire lo stato attuale prima dell'apertura del Punto 7. Dopo questa pulizia, il Punto 7A apre ufficialmente il ciclo Infrastructure Layer con un blueprint solo documentale.

Questo step è **solo documentale**: non modifica codice Java, test, package, use case o dipendenze Maven.

## Stato ufficiale attuale

TruckFlow Manager ha completato il primo grande ciclo tecnico del progetto:

- il **domain layer puro** è stato modellato, pulito, testato e documentato;
- il **Punto 6 — Application Layer** è stato completato da **6A** a **6M**;
- l'application layer contiene use case applicativi per Locations, Cargo, Shipments, Documents, Vehicles, Operational Roles e Compliance base;
- i repository concreti disponibili sono ancora solo repository **in memory**;
- non sono ancora stati introdotti database reali, JPA, Spring Data, REST API, controller, security, tracking, planning, dashboard, workflow o integrazioni esterne.

Lo stato corretto non è più "siamo al 6H". Lo stato corretto è:

**Punto 6M completato — primo ciclo dell'application layer chiuso.**

## Roadmap ufficiale completata

| Punto | Stato | Descrizione reale |
|---|---|---|
| 1 | completato | Domain Review Finale |
| 2 | completato | Review concreta dominio per dominio |
| 3 | completato | Eccezioni custom di dominio |
| 4 | completato | Aggiornamento delle regole e della documentazione del dominio |
| 5 | completato | Pulizia finale del dominio puro |
| 6A | completato | Application Layer Blueprint |
| 6B | completato | Application Foundation |
| 6C | completato | Application Repository Ports |
| 6D | completato | In-Memory Repositories |
| 6E | completato | First Use Cases: Locations, Cargo, Shipments |
| 6F | completato | Application Use Case Review & Hardening |
| 6G | completato | Application Use Cases Expansion: Documents |
| 6H | completato | Application Use Case Expansion Review & Documentation Alignment |
| 6I | completato | Application Use Cases Expansion II: Vehicles |
| 6J | completato | Application Use Cases Expansion III: Operational Roles |
| 6K | completato | Application Operational Use Case Review & Hardening |
| 6L | completato | Application Compliance Base Use Cases |
| 6M | completato | Application Layer Final Review & Freeze |

Dopo il Punto 6M, il Punto 6 deve essere considerato **chiuso**. Le prossime evoluzioni non devono essere aggiunte come altri sotto-step 6N, 6O o simili, ma devono aprire un nuovo punto roadmap.

## Correzione importante sul Punto 6G Documents

Una descrizione precedente indicava il Punto 6G Documents come:

- register;
- update;
- attach;
- generate;
- find.

Questa descrizione non rappresenta lo stato reale del progetto.

Il Punto 6G reale ha introdotto solo i primi use case applicativi logici del dominio Documents:

- register;
- find;
- activate;
- archive.

Non sono stati introdotti:

- update documentale completo;
- attach di file fisici;
- generazione PDF;
- upload;
- storage;
- firma digitale;
- versioning documentale;
- workflow documentali.

Questa scelta è corretta perché il Punto 6 doveva restare nel perimetro dell'application layer puro, senza anticipare infrastructure, file system, document management avanzato o processi enterprise.

## Allineamento reale dei punti 6I, 6J, 6K, 6L e 6M

La roadmap iniziale lasciava aperta la scelta dei domini successivi. Il progetto ha poi seguito questa sequenza reale:

| Punto | Scelta reale applicata |
|---|---|
| 6I | Vehicles use cases |
| 6J | Operational Roles use cases |
| 6K | Review & hardening degli Operational Roles |
| 6L | Compliance base use cases |
| 6M | Final review & freeze dell'application layer |

Questa sequenza è coerente perché:

- Vehicles prepara il registro applicativo della flotta logica;
- Operational Roles prepara il registro applicativo delle figure operative;
- il 6K stabilizza Operational Roles prima di procedere;
- Compliance base aggiunge il catalogo applicativo dei requisiti astratti;
- il 6M chiude il ciclo con un controllo finale di coerenza.

## Stato finale dell'application layer

Alla chiusura del Punto 6M, l'application layer contiene:

- command applicativi;
- result applicativi;
- port in;
- port out;
- application service;
- eccezioni applicative;
- repository port;
- repository in memory;
- test applicativi;
- test architetturali;
- test di hardening;
- test finale di freeze.

I contesti applicativi attivi sono:

- Locations;
- Cargo;
- Shipments;
- Documents;
- Vehicles;
- Operational Roles;
- Compliance base.

## Cosa resta intenzionalmente fuori

Il progetto non deve ancora introdurre:

- REST API;
- controller Spring;
- DTO web;
- database;
- JPA;
- Hibernate;
- Spring Data;
- security;
- JWT;
- tracking;
- planning;
- dispatching reale;
- dashboard;
- audit trail enterprise;
- workflow;
- file upload;
- file storage;
- generazione PDF;
- document versioning;
- country engine operativo;
- controlli concreti di violazione compliance;
- notifiche;
- integrazioni esterne.

Questi elementi appartengono a punti futuri della roadmap.

## Nota sulle dipendenze Spring nel `pom.xml`

Nel `pom.xml` possono essere presenti dipendenze Spring Boot, Web, Validation o OpenAPI già preparate in precedenza.

Questa presenza non significa che il Punto 6 abbia introdotto REST API, controller o infrastruttura Spring operativa.

La regola corretta è:

- le dipendenze possono essere considerate preparatorie o storiche;
- il codice dell'application layer non deve dipendere da Spring;
- il dominio non deve dipendere da Spring;
- non devono esistere controller, entity JPA, repository Spring Data o security configuration finché non verrà aperto un punto roadmap dedicato.

Il Punto 6M protegge questa regola tramite controlli architetturali e documentazione.

## Guided Links e documentazione digitale

La documentazione digitale in `digitalDocs/index.html` è il mirror navigabile della documentazione Markdown.

Il concetto di **Guided Links** indica la navigazione guidata tra i documenti principali:

- indice laterale;
- pannelli HTML separati;
- collegamenti ai file Markdown ufficiali;
- tooltip CSS per spiegazioni italiane sui termini tecnici;
- lettura ordinata dal dominio fino all'application layer.

La fonte ufficiale rimane la documentazione Markdown nella cartella `docs`, ma la documentazione digitale rende più semplice consultare il progetto in modo guidato.

## Regola per il prossimo punto roadmap

Dopo il Punto 6M, il prossimo lavoro dovrà iniziare come **Punto 7**.

La direzione più naturale è:

**Punto 7 — Infrastructure Layer**

Il Punto 7 viene aperto con il **Punto 7A — Infrastructure Layer Blueprint**, una fase solo documentale che definisce confini, roadmap, dipendenze consentite e dipendenze vietate senza introdurre subito controller REST, database, JPA, repository reali o security.

Il Punto 7 potrà preparare:

- infrastructure layer blueprint;
- adapter reali futuri;
- mapping domain/application verso persistence;
- strategia database;
- strategia Spring controllata;
- separazione tra repository port e implementazioni concrete;
- roadmap progressiva 7A → 7H;
- criteri per non confondere Infrastructure Layer e futuro API Layer.

Le REST API dovrebbero restare un punto successivo, perché devono esporre use case stabili e non accedere direttamente al dominio. Il documento ufficiale di apertura è [`docs/30-infrastructure-layer-blueprint.md`](30-infrastructure-layer-blueprint.md).

## Conclusione

La documentazione ufficiale deve ora dire chiaramente che:

- il dominio puro è completato come fondazione;
- il Punto 6 è completato fino al 6M;
- il 6G Documents non include file upload, attach o generate;
- Vehicles, Operational Roles e Compliance base sono già stati applicati;
- il Punto 6 non ha introdotto REST API, database o security;
- il prossimo lavoro deve aprire un nuovo punto roadmap.

Questa è la base documentale corretta prima di procedere con il Punto 7.

## Allineamento successivo — Punto 7B

Dopo l'apertura documentale del Punto 7A, il progetto procede con il **Punto 7B — Infrastructure Foundation**.

Il 7B rende concreta la foundation infrastrutturale senza cambiare la roadmap finale del Punto 6. La sequenza corretta diventa:

1. chiusura Punto 6M;
2. pulizia roadmap finale;
3. Punto 7A — Infrastructure Layer Blueprint;
4. Punto 7B — Infrastructure Foundation;
5. prossimo step: Punto 7C — Spring Wiring Foundation.

Il Punto 7B non introduce API REST, controller, JSON, database, JPA, Spring Data o security. Introduce solo struttura tecnica e convenzioni per proseguire in modo ordinato.
