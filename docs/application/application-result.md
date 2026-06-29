# ApplicationResult spiegato

La classe:

```java
public final class ApplicationResult<T>
```

serve per rappresentare il risultato di un'operazione applicativa.

## Cosa significa `<T>`

`T` è un tipo generico.

Esempi:

```java
ApplicationResult<Shipment>
ApplicationResult<TransportMission>
ApplicationResult<MissionEconomics>
ApplicationResult<ParkingAssignment>
```

Si legge così:

```text
ApplicationResult<Shipment>
→ risultato applicativo che, se va bene, contiene uno Shipment
```

## Perché serve

Uno use case può finire in due modi:

```text
successo
→ ritorna un valore

fallimento
→ ritorna errori
```

`ApplicationResult<T>` evita di creare una classe diversa per ogni risultato.

## Metodi principali

```java
ApplicationResult.success(value)
```

crea un risultato positivo.

```java
ApplicationResult.failure(error)
```

crea un risultato fallito.

```java
isSuccess()
isFailure()
getValue()
getValueOrThrow()
getErrors()
```

servono per leggere il risultato.

## Nota importante

Nella versione attuale molti use case restituiscono direttamente il domain object, per esempio:

```java
ParkingAssignment handle(Command command)
```

`ApplicationResult<T>` è già pronto per controller web/CLI futuri, quando vorrai gestire gli errori senza far uscire eccezioni crude.
