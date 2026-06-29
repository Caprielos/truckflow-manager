# Application layer

L’application layer contiene gli use case: azioni reali richieste al sistema.

## Struttura

```text
application/common
application/port/in
application/port/out
application/usecase
```

## port/in

Le porte di ingresso sono interfacce che descrivono cosa si può chiedere all’applicazione.

Esempio:

```java
AssignParkingSpotUseCase
PlanTransportMissionUseCase
CalculateMissionEconomicsUseCase
```

## port/out

Le porte di uscita sono repository. Lo use case sa che deve salvare/caricare dati, ma non sa se questi dati vengono da RAM, database o API.

Esempio:

```java
ParkingSpotRepository
TransportMissionRepository
DriverRepository
```

## usecase

Le classi `Default...UseCase` sono implementazioni concrete.

Esempio:

```java
DefaultAssignParkingSpotUseCase
```

fa:

1. riceve un command;
2. carica il posto parcheggio dal repository;
3. crea un oggetto domain `ParkingAssignment`;
4. salva l’assegnazione;
5. restituisce il risultato.
