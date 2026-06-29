# Walkthrough - ApplicationResult<T>

`ApplicationResult<T>` è una classe generica.

## Perché esiste

Uno use case può:

- riuscire e restituire un valore;
- fallire e restituire errori.

`T` indica il tipo del valore.

Esempi:

```java
ApplicationResult<Shipment>
ApplicationResult<TransportMission>
ApplicationResult<ParkingAssignment>
```

## Metodi principali

```java
success(T value)
```

Crea un risultato riuscito.

```java
failure(ApplicationError error)
```

Crea un risultato fallito.

```java
isSuccess()
isFailure()
```

Permettono di sapere se l’operazione è andata bene.

```java
getValue()
```

Restituisce un `Optional<T>`.

```java
getValueOrThrow()
```

Restituisce il valore oppure lancia errore se il risultato è fallito.
