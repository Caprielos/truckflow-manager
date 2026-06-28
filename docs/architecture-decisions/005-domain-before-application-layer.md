# ADR 005 — Prima il domain, poi application layer

## Decisione

Il progetto costruisce prima un domain ampio e coerente, poi aggiunge application layer e infrastructure.

## Motivazione

Le regole di autotrasporto sono complesse. È meglio stabilizzare prima concetti e regole, poi scrivere use case e repository.

## Conseguenze

Il prossimo lavoro sarà creare use case come:

```text
CreateTransportOrderUseCase
CreateVehicleCombinationUseCase
AssignDriverToMissionUseCase
RegisterFuelTransactionUseCase
```
