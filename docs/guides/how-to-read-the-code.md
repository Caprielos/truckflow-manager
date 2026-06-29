# Come leggere il codice nuovo

Questa guida spiega i pezzi Java che possono confondere all'inizio.

## `interface`

Esempio:

```java
public interface AssignParkingSpotUseCase
```

Significa: definisco un contratto. Chi implementa questa interfaccia deve fornire quel comportamento.

## `record`

Esempio:

```java
record Command(String parkingSpotId, Notes notes) {}
```

Un record è una piccola classe immutabile usata per trasportare dati.

I valori si leggono così:

```java
command.parkingSpotId()
```

non così:

```java
command.getParkingSpotId()
```

## `Command`

Il `Command` è l'input dello use case.

Esempio:

```text
AssignParkingSpotUseCase.Command
```

contiene tutti i dati necessari per assegnare un parcheggio.

## `Default...UseCase`

Esempio:

```java
DefaultAssignParkingSpotUseCase
```

È l'implementazione concreta dello use case.

## `Repository`

Un repository serve per salvare/caricare domain object.

Esempio:

```java
parkingSpotRepository.findById("A12")
parkingAssignmentRepository.save(assignment)
```

## `Optional<T>`

`Optional<T>` significa: forse c'è un valore, forse no.

Esempio:

```java
Optional<ParkingSpot>
```

vuol dire: forse ho trovato il posto parcheggio, forse no.

## `T`

`T` è un tipo generico.

Esempio:

```java
ApplicationResult<T>
```

può diventare:

```java
ApplicationResult<Shipment>
ApplicationResult<ParkingAssignment>
ApplicationResult<MissionEconomics>
```
