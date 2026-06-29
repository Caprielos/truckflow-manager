# Port in e port out

Dentro application ci sono due direzioni:

```text
port/in
→ entra una richiesta nel sistema

port/out
→ l'application chiede dati al mondo esterno
```

## port/in

`port/in` contiene gli use case, cioè cosa puoi chiedere al sistema.

Esempi:

```text
AssignParkingSpotUseCase
PlanTransportMissionUseCase
CalculateMissionEconomicsUseCase
```

Questi sono ingressi del sistema.

## port/out

`port/out` contiene repository port, cioè interfacce per salvare e caricare dati.

Esempi:

```text
ParkingSpotRepository
DriverRepository
ShipmentRepository
TransportMissionRepository
```

Queste sono uscite dell'application verso infrastruttura.

## Flusso completo

```text
controller futuro / test scenario
        ↓
port/in: AssignParkingSpotUseCase
        ↓
usecase: DefaultAssignParkingSpotUseCase
        ↓
port/out: ParkingSpotRepository
        ↓
infrastructure: InMemoryParkingSpotRepository
```

## Perché separarli

Perché le due direzioni hanno significati diversi:

```text
port/in
→ cosa il sistema offre

port/out
→ cosa serve al sistema per lavorare
```
