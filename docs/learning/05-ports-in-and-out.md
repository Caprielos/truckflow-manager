# Port/in e port/out spiegati semplice

## Idea generale

L'application layer è come il centro operativo.

Ha due tipi di porte:

```text
port/in  → richieste che entrano nel sistema
port/out → richieste che l'application fa verso fuori
```

## port/in

Contiene gli use case.

Esempi:

```text
AssignParkingSpotUseCase
PlanTransportMissionUseCase
CalculateMissionEconomicsUseCase
```

Sono azioni che qualcuno può chiedere al sistema.

## port/out

Contiene repository e servizi richiesti dall'application.

Esempi:

```text
ParkingSpotRepository
DriverRepository
TransportMissionRepository
```

Sono cose che l'application usa per salvare o caricare dati.

## Esempio parcheggio

```text
Utente futuro / test
        ↓
port/in: AssignParkingSpotUseCase
        ↓
DefaultAssignParkingSpotUseCase
        ↓
port/out: ParkingSpotRepository
        ↓
InMemoryParkingSpotRepository
```

## Frase da ricordare

```text
port/in = cosa puoi chiedere all'app
port/out = cosa l'app deve chiedere fuori per lavorare
```
