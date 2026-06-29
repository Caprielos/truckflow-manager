# Architettura

## Architettura attuale

Il progetto è organizzato principalmente così:

```text
src/main/java/it/gabriele/truckflow
├── Main.java
└── domain/
    ├── shared/
    ├── customer/
    ├── order/
    ├── shipment/
    ├── operation/
    ├── fleet/
    ├── driver/
    ├── economics/
    ├── payroll/
    ├── facility/
    ├── parking/
    ├── inventory/
    └── ...
```

## Regola principale

Il domain layer non deve sapere nulla di:

- database;
- API REST;
- frontend;
- file system;
- Spring/JPA;
- servizi esterni;
- controller;
- repository concreti.

Il domain deve contenere solo concetti e regole di business.

## Layer futuri

La prossima evoluzione naturale è:

```text
domain
→ modelli e regole pure

application
→ use case: pianifica missione, calcola margine, chiudi missione, genera fattura

application/ports
→ interfacce repository e servizi esterni

infrastructure/memory
→ implementazioni in memoria per test e demo

infrastructure/persistence
→ database/JPA più avanti

web
→ REST API
```

## Dipendenze consigliate

La direzione delle dipendenze deve essere:

```text
web → application → domain
infrastructure → application/domain
```

Il domain non deve dipendere da application, web o infrastructure.
