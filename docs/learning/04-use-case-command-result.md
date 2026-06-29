# Use case, Command e ApplicationResult

## Use case

Uno use case è un'azione reale del sistema.

Esempi:

```text
assegna un posto parcheggio
pianifica una missione
calcola economics
registra movimento magazzino
```

Nel codice:

```java
public interface AssignParkingSpotUseCase
```

## Command

Il `Command` contiene i dati in ingresso.

Esempio:

```java
record Command(
    String assignmentCode,
    String parkingSpotId,
    ParkedResource parkedResource,
    LocalDateTime startedAt,
    Notes notes
) { }
```

Invece di passare cinque parametri separati, li metti dentro un oggetto con un nome.

## `handle`

Il metodo `handle` significa: gestisci questa richiesta.

```java
ParkingAssignment handle(Command command);
```

Tradotto:

```text
ricevo i dati per parcheggiare qualcosa e restituisco l'assegnazione creata
```

## ApplicationResult<T>

`ApplicationResult<T>` serve quando vuoi rappresentare:

```text
successo con valore
oppure fallimento con errori
```

Esempio:

```java
ApplicationResult<TransportMission>
```

può contenere:

```text
missione creata
```

oppure:

```text
lista errori
```

## Perché alcuni use case restituiscono direttamente l'oggetto?

Alcuni use case oggi restituiscono direttamente `ParkingAssignment`, `TransportMission`, ecc. È una scelta semplice per i primi scenari.

Più avanti, soprattutto nel web layer, si potrà usare più spesso `ApplicationResult<T>` per risposte API pulite.
