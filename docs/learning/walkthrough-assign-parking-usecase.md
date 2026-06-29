# Walkthrough — AssignParkingSpotUseCase

Questo use case risponde alla domanda:

```text
Posso assegnare questa risorsa a questo posto parcheggio?
```

## Interfaccia

```java
public interface AssignParkingSpotUseCase {
    ParkingAssignment handle(Command command);
}
```

Significa:

```text
chi usa questo use case passa un Command e riceve un ParkingAssignment
```

## Command

```java
record Command(
    String assignmentCode,
    String parkingSpotId,
    ParkedResource parkedResource,
    LocalDateTime startedAt,
    Notes notes
) { }
```

Il comando contiene:

- codice assegnazione;
- id posto parcheggio;
- risorsa da parcheggiare;
- data/ora inizio;
- note.

## Implementazione

`DefaultAssignParkingSpotUseCase` fa questi passaggi:

```text
1. controlla che il command non sia null
2. cerca il ParkingSpot nel repository
3. crea ParkingAssignment.active(...)
4. salva l'assegnazione
5. restituisce l'assegnazione
```

## Perché è realistico

Perché nella vita reale il parcheggio non è un campo dentro Vehicle. È un'assegnazione temporanea tra:

```text
posto fisico + risorsa parcheggiata + data inizio
```

La risorsa può essere anche:

```text
trattore + semirimorchio già agganciati
autotreno pronto
furgone
rimorchio singolo
```
