# ADR — Contratto prima del pricing

## Decisione

Il prezzo dovrebbe nascere da contratti/listini e poi diventare pricing/fattura.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
