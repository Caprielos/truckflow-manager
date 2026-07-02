# Punto 7G — Infrastructure Testing

Il Punto 7G rafforza la parte tecnica dell'Infrastructure Layer senza aggiungere nuovi repository business, nuovi adapter esterni o nuove funzionalità applicative.

Dopo il Punto 7E e il Punto 7F, il progetto dispone di repository file-backed per:

- `Locations`;
- `Cargo`;
- `Documents`;
- `Compliance`.

Il Punto 7G verifica che questi adapter siano davvero affidabili dal punto di vista tecnico, che possano essere usati dai use case applicativi tramite i port.out e che i confini architetturali restino puliti.

---

## Obiettivo dello step

L'obiettivo del Punto 7G è costruire una suite di test infrastrutturali più forte.

Questi test non sono test di business. Le regole di business restano nel dominio e nell'application layer.

I test del Punto 7G controllano invece:

- comportamento tecnico dello storage file-backed;
- codifica e decodifica sicura dei record;
- gestione dei file mancanti;
- gestione dei record malformati;
- uso dei repository file-backed tramite use case applicativi;
- assenza di dipendenze vietate tra layer;
- assenza di framework prematuri come JPA, Spring Data, REST controller e security.

---

## Cosa è stato aggiunto

Sono stati aggiunti tre nuovi test principali:

```text
FileRepositoryStorageTest
InfrastructureRepositoryUseCaseIntegrationTest
InfrastructureTechnicalBoundaryTest
```

Questi test completano la base già introdotta da:

```text
FileLocationRepositoryPrototypeTest
FileRepositoryExpansionTest
PersistenceMappingBlueprintTest
SpringWiringFoundationTest
InfrastructureFoundationTest
```

Il Punto 7G non modifica i repository esistenti. Li verifica meglio.

---

## FileRepositoryStorageTest

`FileRepositoryStorageTest` controlla il supporto tecnico condiviso usato dai repository file-backed introdotti nel Punto 7F.

Verifica che `FileRepositoryStorage`:

- tratti un file mancante come repository vuoto;
- scriva e rilegga record in modo stabile;
- crei automaticamente le directory parent necessarie;
- mantenga sicuri campi con tab, newline e caratteri Unicode;
- segnali un field count non valido tramite `RepositoryException`;
- segnali campi non decodificabili tramite `RepositoryException`;
- rifiuti input infrastrutturali nulli o non validi.

Questo test è importante perché `FileRepositoryStorage` è il punto tecnico comune usato dai repository file-backed di Cargo, Documents e Compliance.

---

## InfrastructureRepositoryUseCaseIntegrationTest

`InfrastructureRepositoryUseCaseIntegrationTest` verifica che un repository file-backed possa essere usato da veri use case applicativi attraverso il port.out corretto.

Il test usa il dominio pilota `Locations`:

- `RegisterLocationService`;
- `FindLocationService`;
- `FileLocationRepository`.

Verifica che:

- un use case applicativo possa registrare una Location usando il repository file-backed;
- un altro use case possa ritrovarla per ID;
- i dati restino disponibili anche ricreando una nuova istanza del repository;
- la protezione contro codici business duplicati resti valida anche con repository file-backed.

Questo conferma che l'infrastruttura reale resta compatibile con l'application layer, senza far conoscere file system o dettagli tecnici ai use case.

---

## InfrastructureTechnicalBoundaryTest

`InfrastructureTechnicalBoundaryTest` controlla i confini architetturali dopo l'espansione repository.

Verifica che:

- `domain` e `application` non importino `infrastructure`;
- il codice main non contenga controller REST;
- il codice main non contenga security HTTP;
- il codice main non contenga JPA o Spring Data;
- non siano stati creati package prematuri come `infrastructure.persistence`, `infrastructure.database`, `infrastructure.security` o `infrastructure.web`.

Questo test protegge il progetto da scorciatoie tecniche premature.

---

## Cosa il Punto 7G NON introduce

Il Punto 7G non introduce:

- database;
- JPA;
- Hibernate;
- Spring Data;
- schema SQL;
- repository reali per Shipments, Vehicles o Operational Roles;
- REST API;
- controller;
- DTO web;
- security;
- JWT;
- servizi esterni;
- email;
- audit trail;
- workflow;
- file upload;
- storage binario documentale;
- tracking;
- planning;
- dashboard.

Il Punto 7G è uno step di test e consolidamento tecnico.

---

## Perché non espandere ancora tutti i repository

Dopo il Punto 7F il progetto ha già repository file-backed per contesti catalog-like.

Non vengono ancora aggiunti repository reali completi per:

- `Shipments`;
- `Vehicles`;
- `Operational Roles`;
- `TripTemplates`.

La scelta resta intenzionale.

Questi contesti hanno strutture più complesse, relazioni più delicate e mapping più profondi. Prima di espanderli serve avere una suite di test infrastrutturali solida. Il Punto 7G serve proprio a creare questa protezione.

---

## Confini architetturali confermati

Dopo il Punto 7G la direzione resta la stessa:

```text
domain      -> puro, senza infrastruttura
application -> usa port.out astratti
infrastructure -> implementa adapter tecnici
```

I repository file-backed sono adapter infrastructure.

I use case applicativi continuano a vedere solo i port.out.

Spring resta wiring tecnico non web.

I repository in-memory restano validi per test, sviluppo locale e scenari leggeri.

---

## Stato finale del Punto 7G

Alla fine del Punto 7G il progetto ha:

- Infrastructure Layer foundation presente;
- Spring wiring non web presente;
- blueprint mapping presente;
- prototipo repository reale Locations presente;
- repository file-backed per Cargo, Documents e Compliance presenti;
- test tecnici più forti su storage, use case integration e confini architetturali;
- nessuna API REST;
- nessun database;
- nessun JPA;
- nessuna security.

Il progetto è pronto per il Punto 7H — Infrastructure Review & Freeze.


---

## Allineamento Punto 7H

Il Punto 7H usa la suite tecnica introdotta dal Punto 7G come base del freeze finale. La fase successiva non aggiunge nuovi repository o servizi, ma verifica la presenza dei test infrastrutturali, dei documenti 7A → 7H, del runtime Spring non web e dell'assenza di layer prematuri.

Dopo il Punto 7H, il Punto 7 è considerato chiuso e il progetto può preparare il futuro Punto 8 — API Layer.
