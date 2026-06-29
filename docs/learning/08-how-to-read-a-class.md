# 08 - Come leggere una classe Java del progetto

Prendiamo questa idea:

```java
public final class DefaultAssignParkingSpotUseCase implements AssignParkingSpotUseCase
```

Si legge così:

- `public`: visibile da altri package.
- `final`: non può essere estesa.
- `class`: è una classe concreta.
- `DefaultAssignParkingSpotUseCase`: nome della classe.
- `implements AssignParkingSpotUseCase`: rispetta il contratto dello use case.

## Campi

```java
private final ParkingSpotRepository parkingSpotRepository;
```

Significa che la classe ha bisogno di un repository per cercare posti parcheggio.

## Costruttore

Il costruttore riceve le dipendenze.

```java
public DefaultAssignParkingSpotUseCase(ParkingSpotRepository parkingSpotRepository, ...)
```

## Metodo handle

È il metodo che esegue davvero l’azione.

```java
public ParkingAssignment handle(Command command)
```

## Regola mentale

- campi = cosa serve alla classe;
- costruttore = come glielo dai;
- metodo pubblico = cosa fa la classe.
