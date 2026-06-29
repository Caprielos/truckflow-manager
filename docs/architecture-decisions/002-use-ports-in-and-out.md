# ADR 002 - Usare port/in e port/out

## Decisione

L'application layer separa porte di ingresso e porte di uscita.

## port/in

Use case chiamati da web, CLI, test o altri adapter.

## port/out

Repository e servizi richiesti dall'application per recuperare o salvare dati.

## Beneficio

La direzione delle dipendenze diventa leggibile.
