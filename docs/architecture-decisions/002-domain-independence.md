# ADR 002 — Domain indipendente da framework e database

## Decisione

Il package `domain` non dipende da Spring, JPA, database, API REST o filesystem.

## Motivazione

Il domain deve contenere regole di business pure e testabili.

## Conseguenze

- Repository e persistenza stanno fuori dal domain.
- Le API esterne stanno in infrastructure.
- I test del domain sono veloci e non richiedono servizi esterni.
