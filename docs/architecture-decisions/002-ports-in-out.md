# ADR 002 - Separazione port/in e port/out

## Decisione

Nel package application sono state separate le porte di ingresso e uscita.

## Motivazione

- `port/in`: azioni che il sistema offre.
- `port/out`: dati o servizi che il sistema richiede.

## Conseguenze

Sarà più semplice aggiungere REST API e database senza riscrivere gli use case.
