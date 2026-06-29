# 02 - Parole Java usate nel progetto

## class

Una classe descrive un concetto o un servizio.

```java
public final class Vehicle { }
```

## interface

Una interface è un contratto: dice cosa deve saper fare una classe, ma non come.

```java
public interface AssignParkingSpotUseCase { }
```

## enum

Un enum è una lista chiusa di valori.

```java
enum VehicleStatus { AVAILABLE, MAINTENANCE }
```

## record

Un record è una classe compatta e immutabile per trasportare dati.

```java
record Command(String parkingSpotId) { }
```

## final

`final` su una classe significa che non può essere estesa.

## private final

Un campo `private final` è interno alla classe e viene assegnato una volta sola.

## Optional

`Optional<T>` significa: potrebbe esserci un valore oppure no.

## Generics `<T>`

`T` è un segnaposto per un tipo.

```java
ApplicationResult<Shipment>
```

significa risultato applicativo che contiene una spedizione.
