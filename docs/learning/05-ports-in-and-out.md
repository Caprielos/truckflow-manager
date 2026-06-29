# 05 - Port in e port out

## port/in

Sono le porte di ingresso. Rappresentano cosa il sistema offre.

Esempio:

```text
AssignParkingSpotUseCase
PlanTransportMissionUseCase
```

Significa:

```text
qualcuno dall’esterno può chiedere queste azioni.
```

## port/out

Sono le porte di uscita. Rappresentano ciò che l’application chiede all’esterno.

Esempio:

```text
ParkingSpotRepository
DriverRepository
ShipmentRepository
```

Significa:

```text
lo use case deve leggere o salvare dati.
```

## Schema mentale

```text
controller futuro → port/in → usecase → port/out → repository reale
```

Oggi il repository reale è in memoria. Domani sarà database.
