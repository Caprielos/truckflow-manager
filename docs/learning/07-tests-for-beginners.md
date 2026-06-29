# Test per principianti

## Cosa è un test?

Un test è codice che controlla automaticamente se un altro codice funziona.

Esempio mentale:

```text
Dato un posto parcheggio libero
Quando parcheggio un furgone
Allora viene creata una assegnazione attiva
```

## Perché servono i test?

Perché ogni volta che modifichi il progetto puoi lanciare:

```bash
mvn clean test
```

Se è verde, sai che non hai rotto le regole già testate.

## JUnit

Il progetto usa JUnit 5. I test hanno metodi con:

```java
@Test
```

## Struttura Arrange Act Assert

Un test dovrebbe avere tre parti:

### Arrange

Preparo i dati.

```text
creo driver, mezzo, parcheggio, shipment
```

### Act

Eseguo l'azione.

```text
chiamo lo use case
```

### Assert

Controllo il risultato.

```text
verifico che sia stato creato quello che mi aspettavo
```

## Test unitario

Prova una classe o una regola piccola.

Esempio:

```text
MoneyTest
WeightTest
ParkingAssignmentTest
```

## Test di scenario

Prova più pezzi insieme.

Esempio:

```text
TruckFlowApplicationScenarioTest
```

Questo è più vicino a un flusso reale.
