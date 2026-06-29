# ADR — Facility, parking e inventory separati

## Decisione

Struttura fisica, posto parcheggio e magazzino sono concetti collegati ma diversi.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
