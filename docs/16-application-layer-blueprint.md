# 16 — Application Layer Blueprint

Questo documento definisce il **Punto 6A — Application Layer Blueprint** di TruckFlow Manager.

> Stato: il blueprint del Punto 6A è stato completato come guida architetturale. Il progetto ha completato il Punto 6B con la foundation applicativa e ha avviato il Punto 6C con le prime repository port descritte in `docs/18-application-repository-ports.md`.


Il dominio puro è stato modellato, rafforzato, testato e documentato. Il passo successivo non è introdurre subito REST API, database o Spring, ma progettare il livello applicativo che userà il dominio attraverso casi d'uso chiari.

L'application layer è il ponte tra il modello di dominio e i futuri ingressi tecnici del sistema, come API REST, interfacce grafiche, job automatici, import/export, workflow e integrazioni esterne.

---

## 1. Obiettivo del Punto 6A

Il Punto 6A ha un obiettivo preciso:

> definire struttura, responsabilità, convenzioni e roadmap dell'application layer prima di scrivere use case e repository.

Questa fase serve a evitare che il progetto cresca in modo caotico.

L'application layer dovrà:

- esporre le azioni che TruckFlow Manager può eseguire;
- orchestrare aggregate root e value object del dominio;
- usare repository astratti tramite porte in uscita;
- restituire risultati applicativi leggibili;
- tradurre problemi applicativi in eccezioni applicative coerenti;
- preparare il futuro collegamento con web, database, API e integrazioni;
- mantenere il dominio puro indipendente da framework e infrastruttura.

Questa fase non aggiunge ancora funzionalità operative enterprise come planning, dispatching, tracking, dashboard, workflow o availability. Quelle arriveranno in moduli successivi.

---

## 2. Perché serve un application layer

Il dominio contiene le regole di business.

Esempi:

- una shipment può essere confermata solo se rispetta le sue invarianti;
- un cargo non può avere pesi o dimensioni incoerenti;
- un veicolo stradale deve rispettare le regole di identificazione;
- un documento deve avere metadati validi;
- un requisito di compliance deve avere target, regola, fonte e giurisdizione.

Il dominio però non deve sapere:

- chi ha richiesto l'operazione;
- da quale controller arriva la richiesta;
- dove l'oggetto verrà salvato;
- se il dato arriva da REST, UI, file, job o integrazione esterna;
- come recuperare una shipment esistente;
- come verificare se un codice è già presente in un repository.

Queste responsabilità appartengono all'application layer.

L'application layer risponde alla domanda:

> quali azioni può eseguire il sistema usando il dominio?

Esempi di azioni applicative:

- registrare una location;
- registrare un cargo;
- creare una shipment;
- aggiungere un cargo a una shipment;
- aggiungere una tratta a una shipment;
- confermare una shipment;
- registrare un documento;
- registrare un requisito di compliance;
- recuperare oggetti esistenti tramite repository.

---

## 3. Cosa contiene l'application layer

L'application layer conterrà principalmente:

- **use case**: azioni applicative eseguibili;
- **command**: input dei casi d'uso;
- **result**: output dei casi d'uso;
- **port in**: interfacce dei casi d'uso esposte verso l'esterno;
- **port out**: interfacce verso repository e servizi esterni astratti;
- **application service**: implementazioni dei casi d'uso;
- **application exception**: errori applicativi;
- **test applicativi**: verifica dell'orchestrazione tra domini e repository.

L'application layer non contiene regole fondamentali di business che appartengono al dominio.

---

## 4. Cosa non contiene l'application layer

Nel Punto 6 non devono entrare ancora:

- controller REST;
- DTO web;
- Spring Boot annotations;
- JPA;
- Hibernate;
- database reali;
- JWT;
- sicurezza avanzata;
- Swagger/OpenAPI;
- frontend;
- tracking GPS;
- telematica;
- planning operativo completo;
- dispatching reale;
- workflow documentali;
- dashboard e analytics;
- audit trail enterprise;
- multi-tenant;
- integrazioni esterne reali.

Queste parti arriveranno dopo, quando i casi d'uso saranno stabili.

La regola è:

> prima si definisce cosa il sistema può fare, poi si decide come esporlo e dove salvarlo.

---

## 5. Struttura package consigliata

La struttura iniziale consigliata è:

```text
src/main/java/it/gabriele/truckflow/application
├── command
├── result
├── port
│   ├── in
│   └── out
├── usecase
└── exception
```

Questa struttura mantiene il livello applicativo leggibile e separato dal dominio.

### 5.1 `application.command`

Contiene gli input dei casi d'uso.

Un command rappresenta una richiesta applicativa, non un DTO REST.

Esempi futuri:

- `RegisterLocationCommand`;
- `RegisterCargoUnitCommand`;
- `CreateShipmentCommand`;
- `AddShipmentItemCommand`;
- `AddShipmentLegCommand`;
- `ConfirmShipmentCommand`.

Un command deve essere semplice, esplicito e orientato all'azione.

### 5.2 `application.result`

Contiene gli output restituiti dai casi d'uso.

Esempi futuri:

- `LocationResult`;
- `CargoUnitResult`;
- `ShipmentResult`;
- `DocumentResult`;
- `ComplianceRequirementResult`.

Il result serve a non esporre necessariamente l'aggregate root completo verso l'esterno.

### 5.3 `application.port.in`

Contiene le interfacce dei casi d'uso.

Esempi futuri:

- `RegisterLocationUseCase`;
- `RegisterCargoUnitUseCase`;
- `CreateShipmentUseCase`;
- `ConfirmShipmentUseCase`.

Queste interfacce sono ciò che, in futuro, i controller REST, i job o altri ingressi tecnici potranno chiamare.

### 5.4 `application.port.out`

Contiene le porte verso l'esterno.

La prima categoria di port out sarà quella dei repository astratti.

Esempi futuri:

- `LocationRepository`;
- `CargoUnitRepository`;
- `ShipmentRepository`;

Queste tre porte sono state introdotte nel Punto 6C come primi contratti outbound concreti dell’application layer. Non sono implementazioni database e non appartengono all’infrastructure.
- `DocumentRepository`;
- `ComplianceRequirementRepository`.

Questi repository non sono database. Sono contratti.

### 5.5 `application.usecase`

Contiene le implementazioni dei casi d'uso.

Convenzione consigliata:

- interfaccia: `CreateShipmentUseCase`;
- implementazione: `CreateShipmentService`.

La parola `Service` qui indica un application service, non un domain service.

### 5.6 `application.exception`

Contiene errori applicativi.

Esempi futuri:

- `ApplicationException`;
- `ResourceNotFoundException`;
- `DuplicateResourceException`;
- `UseCaseValidationException`.

Le eccezioni applicative non sostituiscono quelle di dominio. Hanno responsabilità diversa.

---

## 6. Differenza tra eccezione di dominio ed eccezione applicativa

Le eccezioni di dominio indicano che una regola di business è stata violata.

Esempi:

- una shipment incompleta non può essere confermata;
- un cargo ha temperature incoerenti;
- un documento ha metadati non validi;
- un veicolo ha dati tecnici non accettabili.

Le eccezioni applicative indicano problemi nell'esecuzione del caso d'uso.

Esempi:

- la shipment richiesta non esiste;
- esiste già una location con lo stesso codice;
- il command ricevuto è nullo;
- il cargo da aggiungere alla shipment non è stato trovato;
- il repository non può completare l'operazione richiesta.

Regola:

> il dominio decide se un oggetto è valido; l'application layer decide come caricare, salvare e coordinare quell'oggetto.

---

## 7. Repository port

I repository dell'application layer saranno interfacce.

Servono a separare i casi d'uso dalla tecnologia di persistenza.

Un repository port deve esprimere intenzioni applicative semplici:

- salvare un aggregate;
- trovare un aggregate per ID;
- verificare se un aggregate esiste;
- verificare duplicati per codice quando necessario;
- recuperare un aggregate per codice quando ha senso.

I repository port non devono contenere dettagli tecnici come query SQL, entity JPA, sessioni, connessioni o annotazioni framework.

---

## 8. Infrastructure memory

Dopo le port repository verrà introdotta una prima infrastruttura in memoria.

La struttura consigliata è:

```text
src/main/java/it/gabriele/truckflow/infrastructure/memory
├── cargo
├── locations
└── shipments
```

Nel primo blocco non serve coprire tutti i domini.

Le prime implementazioni previste sono:

- `InMemoryLocationRepository`;
- `InMemoryCargoUnitRepository`;
- `InMemoryShipmentRepository`.

Queste implementazioni serviranno per:

- testare i casi d'uso senza database;
- costruire scenari applicativi realistici;
- preparare il futuro passaggio a repository persistenti;
- mantenere il dominio indipendente dall'infrastruttura.

La memoria non è il database finale. È una prima implementazione tecnica utile per sviluppo e test.

---

## 9. Primo blocco funzionale consigliato

Il primo blocco applicativo consigliato è:

> Locations + Cargo + Shipments.

Questa scelta è naturale perché una shipment usa location e cargo tramite ID.

Il primo flusso applicativo reale sarà:

1. registrare una location di partenza;
2. registrare una location di destinazione;
3. registrare un cargo;
4. creare una shipment;
5. aggiungere il cargo alla shipment;
6. aggiungere una tratta alla shipment;
7. confermare la shipment.

Questo flusso dimostra che l'application layer sa orchestrare più domini senza sporcare il dominio stesso.

---

## 10. Use case iniziali previsti

### 10.1 Locations

Use case iniziali:

- `RegisterLocationUseCase`;
- `FindLocationUseCase`.

Responsabilità:

- creare location valide usando il dominio;
- evitare duplicati applicativi per codice;
- salvare e recuperare location tramite repository port;
- restituire un result leggibile.

### 10.2 Cargo

Use case iniziali:

- `RegisterCargoUnitUseCase`;
- `FindCargoUnitUseCase`.

Responsabilità:

- creare cargo validi usando il dominio;
- verificare duplicati applicativi per codice;
- salvare e recuperare cargo tramite repository port;
- mantenere hazard, regulatory, packaging, temperature, weights e dimensions dentro le regole del dominio.

### 10.3 Shipments

Use case iniziali:

- `CreateShipmentUseCase`;
- `AddShipmentItemUseCase`;
- `AddShipmentLegUseCase`;
- `ConfirmShipmentUseCase`;
- `CancelShipmentUseCase`;
- `FindShipmentUseCase`.

Responsabilità:

- creare una shipment come richiesta di spedizione;
- aggiungere item cargo usando `CargoId`;
- aggiungere tratte logiche usando `LocationId`;
- confermare una shipment chiamando le regole del dominio;
- salvare ogni modifica in modo coerente;
- restituire errori applicativi quando shipment, cargo o location non esistono.

---

## 11. Come deve comportarsi un use case

Ogni use case deve seguire una logica semplice:

1. riceve un command;
2. valida che il command sia presente e minimamente completo;
3. carica dal repository gli aggregate necessari;
4. se una risorsa non esiste, genera un errore applicativo;
5. chiama i metodi del dominio;
6. salva l'aggregate aggiornato;
7. restituisce un result.

Il caso d'uso non deve duplicare le regole del dominio.

Esempio concettuale:

- `ConfirmShipmentUseCase` carica la shipment, chiama `confirm`, salva la shipment e restituisce il risultato;
- non riscrive nel service la regola secondo cui una shipment confermata deve avere item e tratte.

---

## 12. Test strategy dell'application layer

I test del dominio verificano invarianti e regole interne.

I test dell'application layer dovranno verificare orchestrazione e comportamento dei casi d'uso.

Esempi di test applicativi futuri:

- registrare una location valida salva la location nel repository;
- registrare una location con codice duplicato produce errore applicativo;
- registrare un cargo valido salva il cargo;
- creare una shipment salva una shipment in stato iniziale coerente;
- aggiungere un cargo a una shipment fallisce se la shipment non esiste;
- aggiungere un cargo a una shipment fallisce se il cargo non esiste;
- confermare una shipment completa aggiorna lo stato;
- confermare una shipment incompleta propaga l'errore di dominio;
- il flusso completo location + cargo + shipment funziona senza database.

Questi test non devono verificare di nuovo tutti gli invarianti del dominio. Devono verificare che l'application layer usi correttamente il dominio.

---

## 13. Regole architetturali da rispettare

L'application layer può dipendere dal domain layer.

Il domain layer non può dipendere dall'application layer.

L'infrastructure può dipendere dall'application layer e dal domain layer per implementare le porte.

Il futuro web layer dovrà dipendere dall'application layer, non accedere direttamente al dominio per modificare lo stato.

Schema concettuale:

```text
web / api / jobs
        ↓
application
        ↓
domain
        ↑
infrastructure implementa le port out definite dall'application layer
```

Regola fondamentale:

> il flusso delle dipendenze deve proteggere il dominio.

---

## 14. Cosa non va anticipato nel primo ciclo applicativo

Nel primo ciclo application non bisogna introdurre:

- compatibilità cargo-veicolo reale;
- assegnazione autista;
- assegnazione veicolo;
- disponibilità giornaliera;
- calendario operativo;
- tracking spedizioni;
- stati come `IN_TRANSIT` o `DELIVERED`;
- manutenzione flotta;
- workflow documentali;
- verifica compliance concreta;
- notifiche;
- dashboard;
- SLA e penali;
- multi-tenant.

Questi concetti sono corretti per la roadmap enterprise, ma non per il primo application layer.

Il primo application layer deve essere piccolo, testabile e stabile.

---

## 15. Roadmap del Punto 6

Il Punto 6 può essere diviso in fasi.

### Punto 6A — Application Layer Blueprint

Stato: documentato da questo file.

Obiettivo:

- definire struttura e regole dell'application layer;
- decidere convenzioni di package, use case, command, result e port;
- chiarire cosa entra e cosa resta fuori.

### Punto 6B — Application Foundation

Obiettivo:

- creare package application;
- creare eccezioni applicative base;
- definire convenzioni concrete per command e result;
- preparare i primi contratti di use case.

### Punto 6C — Repository Ports

Obiettivo:

- creare repository astratti per location, cargo e shipment;
- definire operazioni minime di salvataggio e ricerca;
- evitare qualsiasi dipendenza tecnica.

### Punto 6D — In-Memory Repositories

Obiettivo:

- implementare repository in memory per location, cargo e shipment;
- supportare i test applicativi;
- preparare il passaggio futuro a repository persistenti.

### Punto 6E — First Use Cases

Obiettivo:

- implementare i primi use case applicativi;
- partire da locations, cargo e shipments;
- mantenere servizi piccoli e leggibili.

### Punto 6F — Application Tests

Obiettivo:

- testare use case singoli;
- testare errori applicativi;
- testare il primo flusso completo senza database.

---

## 16. Documentazione da mantenere aggiornata

Quando inizierà il codice applicativo, dovranno essere aggiornati:

- `docs/README.md`;
- `docs/16-application-layer-blueprint.md`;
- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`;
- `digitalDocs/index.html`;
- `digitalDocs/truckflow-manager-enterprise-documentation.html`;
- eventuali nuovi documenti specifici sui repository o sugli use case.

La documentazione deve continuare a distinguere chiaramente:

- ciò che è già implementato;
- ciò che è solo pianificato;
- ciò che appartiene a layer futuri;
- ciò che non deve entrare nel dominio.

---

## 17. Decisione ufficiale

La decisione ufficiale del Punto 6A è:

> TruckFlow Manager inizierà l'application layer con un blueprint documentato, poi introdurrà gradualmente struttura applicativa, repository port, repository in memory, use case e test applicativi, partendo dal flusso Locations + Cargo + Shipments.

Questo consente di crescere in modo ordinato senza confondere dominio, applicazione, infrastruttura e web.
