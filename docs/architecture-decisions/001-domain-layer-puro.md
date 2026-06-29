# ADR — Domain layer puro

## Decisione

Il dominio non dipende da framework, database, API o UI. Questa scelta mantiene il cuore business testabile e riusabile.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
