# Testing Guide

## Obiettivo dei test

I test proteggono le regole di dominio.

Ogni package principale ha test dedicati in:

```text
src/test/java/it/gabriele/truckflow/domain
```

## Comando principale

```bash
mvn clean test
```

Va eseguito prima di ogni commit.

## Cosa testare

Per ogni nuova regola:

1. caso valido;
2. caso non valido;
3. boundary case;
4. eccezioni attese;
5. comportamento di calcolo.

## Test di integrazione domain

Il package `domain.integration` contiene test che verificano l’integrazione trasversale dei nuovi concetti realistici.

Esempi:

- cargo → requisiti documentali;
- vehicle → technical specification;
- driver → certificati;
- company → licenze;
- mission readiness.

## Regola importante

Quando una regola di business cambia, non bisogna forzare il codice a rispettare un test vecchio.

Esempio: il frigo non è più un `VehicleType`, ma un allestimento. Quindi un autocarro rigido con `REFRIGERATED_BOX` è valido se ha dati coerenti.
