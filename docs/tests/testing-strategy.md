# Strategia di test

Il progetto usa JUnit 5 e Maven Surefire.

Nel progetto zippato risultano:

```text
Test totali: 823
Failure: 0
Errori: 0
Skipped: 0
```

## Tipi di test

### Unit test domain

Testano regole e value object isolati.

Esempi:

```text
MoneyTest
VehicleTest
CargoLoadRulesTest
MissionEconomicsTest
```

### Use case test

Testano application service specifici.

Esempi:

```text
ApplicationResultTest
AssignParkingSpotUseCaseTest
```

### Scenario test

Testano flussi realistici con più componenti.

Esempio:

```text
TruckFlowApplicationScenarioTest
```

## Comando

```bash
mvn clean test
```

## Quando lanciare i test

- Dopo ogni patch.
- Prima di ogni commit.
- Dopo merge su main.
- Dopo modifiche al pom.xml.
- Dopo aggiornamento Java/Maven.
