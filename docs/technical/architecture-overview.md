# Architettura tecnica

TruckFlow Manager usa una struttura vicina a Clean Architecture / Hexagonal Architecture.

```text
src/main/java/it/gabriele/truckflow
├── domain
├── application
├── infrastructure/memory
└── Main.java
```

## Layer

- `domain`: regole pure e concetti aziendali.
- `application`: casi d’uso e orchestrazione.
- `infrastructure.memory`: repository in memoria che implementano le porte di uscita.
- futuro `web`: controller REST.
- futuro `infrastructure.persistence`: database.

## Dipendenze desiderate

```text
web futuro → application → domain
infrastructure → application port/out + domain
```

Il domain non deve dipendere da application, database, REST o framework.
