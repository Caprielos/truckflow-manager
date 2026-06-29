# 04 - Use case, Command e Result

## Use case

Uno use case è un’azione reale.

Esempio:

```java
AssignParkingSpotUseCase
```

significa “assegna una risorsa a un posto parcheggio”.

## Command

Il command contiene i dati in ingresso.

Esempio:

```java
record Command(
    String assignmentCode,
    String parkingSpotId,
    ParkedResource parkedResource,
    LocalDateTime startedAt,
    Notes notes
) {}
```

Si legge così:

```text
Per assegnare un parcheggio mi servono codice assegnazione, posto, risorsa, ora inizio e note.
```

## handle

Il metodo `handle` esegue lo use case.

```java
ParkingAssignment handle(Command command);
```

Si legge:

```text
ricevo un Command e restituisco un ParkingAssignment.
```

## ApplicationResult<T>

`ApplicationResult<T>` è una classe generica che può contenere un successo o errori.

Esempi:

```java
ApplicationResult<Shipment>
ApplicationResult<TransportMission>
ApplicationResult<ParkingAssignment>
```
