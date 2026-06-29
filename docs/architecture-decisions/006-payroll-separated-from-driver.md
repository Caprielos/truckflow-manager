# ADR — Payroll separato da Driver

## Decisione

Driver descrive persona e abilitazioni; payroll descrive costo lavoro in base alla missione.

## Motivazione

La decisione rende il progetto più vicino a un gestionale reale e mantiene confini chiari tra responsabilità diverse.

## Conseguenze

- Il domain resta più pulito.
- I test rimangono leggibili.
- L'application layer potrà orchestrare i moduli senza duplicare regole.
