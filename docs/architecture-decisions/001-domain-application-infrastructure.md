# ADR 001 - Separare domain, application e infrastructure

## Decisione

Il progetto usa tre livelli principali:

```text
domain
application
infrastructure
```

## Motivazione

Le regole di business devono restare indipendenti da database, API e tecnologia.

## Conseguenze

- Il domain è più testabile.
- Gli use case sono più chiari.
- La persistenza può cambiare senza riscrivere il business.
