# Walkthrough - AssignParkingSpotUseCase

## Interfaccia

```java
public interface AssignParkingSpotUseCase {
    ParkingAssignment handle(Command command);
}
```

Significa:

```text
questa azione riceve un command e restituisce una assegnazione parcheggio.
```

## Command

Il command contiene:

- `assignmentCode`: codice assegnazione;
- `parkingSpotId`: id posto;
- `parkedResource`: cosa parcheggio;
- `startedAt`: quando inizia l’occupazione;
- `notes`: note.

## Implementazione

`DefaultAssignParkingSpotUseCase` fa:

1. controlla che il command non sia null;
2. carica il posto parcheggio;
3. crea `ParkingAssignment.active(...)`;
4. salva l’assegnazione;
5. restituisce il domain object creato.

## Perché è utile

Così il futuro controller REST non dovrà conoscere la logica del parcheggio. Dovrà solo costruire un command e chiamare lo use case.
