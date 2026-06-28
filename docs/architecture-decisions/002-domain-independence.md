# ADR 002 — Domain Independence

## Decisione

Il domain non dipende da Spring, JPA, database, REST API, filesystem o provider esterni.

## Motivazione

Le regole di business devono essere testabili e leggibili senza infrastruttura.

## Conseguenze

- Le entity domain non sono annotate con JPA.
- I repository concreti verranno creati in infrastructure.
- I casi d’uso verranno creati in application.
- Le API esterne saranno nascoste dietro port.
