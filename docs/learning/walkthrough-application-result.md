# Walkthrough — ApplicationResult<T>

`ApplicationResult<T>` è una scatola generica per il risultato di uno use case.

## La riga

```java
public final class ApplicationResult<T>
```

si legge:

```text
classe pubblica, non estendibile, che lavora con un tipo generico T
```

## Perché T?

Perché un use case può restituire cose diverse:

```java
ApplicationResult<Shipment>
ApplicationResult<TransportMission>
ApplicationResult<ParkingAssignment>
ApplicationResult<MissionEconomics>
```

## Campi

```java
private final T value;
private final List<ApplicationError> errors;
```

Può avere un valore oppure errori.

## Successo

```java
ApplicationResult.success(value)
```

Crea un risultato riuscito.

## Fallimento

```java
ApplicationResult.failure(error)
```

Crea un risultato fallito.

## Lettura

```java
if (result.isSuccess()) {
    var value = result.getValueOrThrow();
}
```

## Perché è utile

Perché evita risposte confuse e aiuta il futuro web layer a restituire errori chiari.
