# Architettura

## Architettura attuale

Il progetto è costruito attorno al **domain layer**.

```text
src/main/java/it/gabriele/truckflow
├── Main.java
└── domain/
    ├── shared/
    ├── cargo/
    ├── fleet/
    ├── driver/
    ├── shipment/
    ├── operation/
    └── ...
```

Il dominio non usa:

- Spring;
- JPA;
- database;
- REST controller;
- filesystem;
- API esterne;
- mapper JSON;
- frontend.

Questa scelta è voluta. Le classi domain devono essere riutilizzabili, testabili e indipendenti.

## Layer previsti

Il progetto può evolvere così:

```text
domain
  regole pure, entità, value object, enum

application
  casi d'uso: crea ordine, pianifica missione, assegna driver, verifica compliance

infrastructure
  repository DB, file, provider mappe, fuel card, GPS, email

web
  REST API, controller, DTO, sicurezza, frontend
```

## Dipendenze corrette

La dipendenza deve andare dall'esterno verso il dominio, mai il contrario.

```text
web → application → domain
infrastructure → application/domain ports
```

Il dominio non deve conoscere controller, database o framework.

## Package boundary

Ogni package rappresenta un sotto-dominio.

- `fleet` non contiene logica carburante dettagliata: per quello c'è `fuel`.
- `fleet` non contiene storico pneumatici: per quello c'è `tire`.
- `shipment` non contiene assegnazione driver/mezzo: per quello c'è `operation`.
- `identity` non sostituisce `driver` o `customer`: rappresenta utenti software.
- `tracking` non sostituisce `audit`: tracking riguarda il viaggio, audit riguarda il sistema.

## Perché il vecchio shipment è stato rimosso

Il vecchio package `it.gabriele.truckflow.shipment` era fuori dal domain layer. Era utile all'inizio come esercizio semplice, ma nella versione attuale avrebbe creato duplicazione.

La versione corretta è:

```text
it.gabriele.truckflow.domain.shipment
```

Questa scelta mantiene l'architettura pulita.
