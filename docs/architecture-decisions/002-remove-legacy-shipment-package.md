# ADR — Rimozione shipment legacy

## Decisione

Il vecchio package fuori da domain è stato eliminato per avere una sola fonte di verità: domain/shipment.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
