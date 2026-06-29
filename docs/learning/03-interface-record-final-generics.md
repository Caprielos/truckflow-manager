# Interface, record, final e generics

## Interface

Una interface è un contratto.

```java
public interface AssignParkingSpotUseCase {
    ParkingAssignment handle(Command command);
}
```

Significa:

```text
chiunque implementa questa interfaccia deve avere un metodo handle che riceve Command e restituisce ParkingAssignment
```

Nel progetto usiamo le interface per separare:

```text
cosa si può fare
```

da:

```text
come viene fatto
```

## Implementazione

```java
public final class DefaultAssignParkingSpotUseCase implements AssignParkingSpotUseCase
```

Questa è la classe che esegue davvero il caso d'uso.

## Record

Un record è una classe compatta per dati.

```java
record Command(String assignmentCode, String parkingSpotId) { }
```

Serve per portare input allo use case.

Con un record Java crea da solo:

- costruttore;
- metodi di lettura;
- equals;
- hashCode;
- toString.

## Final

`final` serve per bloccare cambiamenti non desiderati.

Su una classe:

```java
public final class ApplicationResult<T>
```

nessuno può estenderla.

Su un campo:

```java
private final List<ApplicationError> errors;
```

il riferimento non cambia dopo il costruttore.

## Generics

```java
ApplicationResult<T>
```

`T` è un tipo variabile.

Esempi:

```java
ApplicationResult<Shipment>
ApplicationResult<TransportMission>
ApplicationResult<ParkingAssignment>
```

La classe è una sola, ma può contenere valori diversi.

## Perché non usare Object?

Con `Object`, Java non sa cosa contiene il risultato. Con `<T>`, Java protegge il tipo.

```java
ApplicationResult<Shipment> result
```

Qui Java sa che, se c'è un valore, sarà uno `Shipment`.
