# Termini Java spiegati in modo semplice

## `package`

Esempio:

```java
package it.gabriele.truckflow.domain.parking;
```

Significa: questa classe appartiene alla cartella logica `domain.parking`.

Serve per organizzare il codice.

## `import`

Esempio:

```java
import java.time.LocalDateTime;
```

Significa: voglio usare una classe che sta in un altro package.

## `public`

Significa: questa classe, metodo o costruttore può essere usato anche da altri package.

## `private`

Significa: questa cosa può essere usata solo dentro la classe stessa.

Esempio:

```java
private final String missionNumber;
```

Solo la classe può modificare/leggere direttamente quel campo. Da fuori si passa da metodi pubblici.

## `final`

Può significare due cose.

Su una classe:

```java
public final class ApplicationResult<T>
```

vuol dire: nessuna classe può fare `extends ApplicationResult`.

Su un campo:

```java
private final String assignmentCode;
```

vuol dire: dopo il costruttore quel campo non cambia più.

## `class`

È una definizione di oggetto.

Esempio:

```java
public final class ParkingAssignment
```

Significa: creo il tipo `ParkingAssignment`.

## `interface`

È un contratto.

Esempio:

```java
public interface AssignParkingSpotUseCase
```

Significa: chi implementa questa interfaccia deve offrire quel metodo.

## `implements`

Esempio:

```java
public final class DefaultAssignParkingSpotUseCase implements AssignParkingSpotUseCase
```

Significa: questa classe promette di rispettare il contratto `AssignParkingSpotUseCase`.

## `record`

È una classe compatta e immutabile per portare dati.

Esempio:

```java
record Command(String parkingSpotId) { }
```

Java crea automaticamente costruttore, metodi di lettura, equals, hashCode e toString.

## `enum`

È un elenco chiuso di valori.

Esempio:

```java
enum ParkingSpotStatus {
    AVAILABLE,
    OCCUPIED,
    OUT_OF_SERVICE
}
```

Serve per evitare stringhe libere come `"available"`, `"availble"`, `"occupato"`.

## `static`

Significa: appartiene alla classe, non a un singolo oggetto.

Esempio:

```java
Money.ofEuros(100)
```

Non devi fare `new Money(...)`; usi un metodo factory statico.

## `new`

Crea un nuovo oggetto.

```java
new Command(...)
```

## `return`

Restituisce un valore da un metodo.

## `null`

Significa assenza di valore. Nel progetto si cerca di evitarlo il più possibile, usando validazioni e `Optional`.

## `Optional<T>`

Significa: forse c'è un valore, forse no.

Esempio:

```java
Optional<ParkingSpot> findById(String id)
```

Il posto può esistere oppure no.

## `List<T>`

Lista ordinata di elementi.

## `Set<T>`

Insieme senza duplicati.

## `Map<K,V>`

Collezione chiave-valore.

Esempio mentale:

```text
"SPOT-100" → ParkingSpot
"DRV-001" → Driver
```

## `T` nei generics

In `ApplicationResult<T>`, `T` è un segnaposto per il tipo.

```java
ApplicationResult<Shipment>
```

significa: risultato che contiene uno `Shipment`.
