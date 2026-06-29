# Application layer spiegato classe per classe

## `application/common`

- `ApplicationError`: rappresenta un errore applicativo leggibile.
- `ApplicationResult<T>`: risultato generico che può contenere un valore o una lista errori.
- `ResourceNotFoundException`: eccezione quando un use case cerca una risorsa obbligatoria e non la trova.

## `application/port/in`

Contiene gli use case. Ogni interfaccia rappresenta un'azione reale.

Esempi:

- `AssignParkingSpotUseCase`: assegnare una risorsa a un posto parcheggio.
- `PlanTransportMissionUseCase`: pianificare una missione.
- `CalculateMissionEconomicsUseCase`: calcolare ricavi, costi e margine.
- `CalculateDriverMissionPayrollUseCase`: calcolare costo autista.
- `RecordInventoryStockMovementUseCase`: registrare un movimento magazzino.

## `application/port/out`

Contiene repository port. Sono interfacce, non database.

Esempio:

```text
ParkingSpotRepository
→ l'application può chiedere un ParkingSpot
```

## `application/usecase`

Contiene implementazioni concrete.

La convenzione è:

```text
UseCase interface: AssignParkingSpotUseCase
Implementation: DefaultAssignParkingSpotUseCase
```

La parola `Default` significa: implementazione standard attuale.
