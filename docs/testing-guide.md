# Testing guide

Il progetto usa Maven e JUnit 5.

## Eseguire tutti i test

```bash
mvn clean test
```

## Strategia test

I test del domain verificano:

- validazioni dei value object;
- transizioni di stato;
- compatibilità carico/mezzo;
- requisiti autista;
- documenti richiesti;
- calcoli di pesi, volumi e costi;
- regole di disponibilità, manutenzione e tracking.

## Convenzione

Ogni package importante ha almeno un test dedicato.

Esempi:

```text
VehicleTest
VehicleCombinationRulesTest
RealisticFleetModelTest
CargoOperationalRulesTest
DriverRulesTest
FuelTransactionTest
TireManagementTest
LoadSecuringChecklistTest
```

## Prima di committare

Eseguire sempre:

```bash
mvn clean test
git status
```
