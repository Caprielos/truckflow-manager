# ADR — Economics separato

## Decisione

Costi, ricavi, IVA, asset e marginalità sono separati da pricing e billing per non confondere prezzo cliente con costo interno.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
