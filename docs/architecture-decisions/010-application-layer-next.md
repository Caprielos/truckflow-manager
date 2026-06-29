# ADR — Application layer come prossimo passo

## Decisione

Dopo il domain serve orchestrare use case e repository ports prima di database e API.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
