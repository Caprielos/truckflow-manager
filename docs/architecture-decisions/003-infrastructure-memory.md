# ADR 003 - Repository in memoria

## Decisione

È stata aggiunta `infrastructure/memory`.

## Motivazione

Permette di provare gli use case senza database reale.

## Conseguenze

I test di scenario possono simulare flussi completi. In futuro i repository in memoria potranno essere sostituiti da repository database.
