# ADR — Domain indipendente da framework

## Decisione

Il domain layer non deve dipendere da Spring, JPA, database, REST, file system o API esterne. Questa scelta rende i test più semplici e le regole più riutilizzabili.

## Motivazione

La scelta mantiene il progetto più pulito, più testabile e più vicino a un software reale.

## Conseguenze

- Il dominio resta indipendente.
- Le regole possono essere testate senza infrastruttura.
- Il progetto può crescere verso application, infrastructure e web senza riscrivere il cuore del sistema.
