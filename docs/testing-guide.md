# Testing Guide

## Comando principale

```bash
mvn clean test
```

## Stato dai report presenti nello zip

Nei report Surefire inclusi nello zip risultano:

```text
Test class: 74
Test totali: 788
Failure: 0
Error: 0
Skipped: 0
```

Nel mio ambiente Maven non è installato, quindi questi numeri vengono dai report già presenti nel progetto zippato. Prima di fare commit o push, eseguire sempre localmente:

```bash
mvn clean test
```

## Tipi di test presenti

I test coprono:

- value object (`Weight`, `Money`, `Dimension`, `TimeWindow`, ecc.);
- entity e stati (`Shipment`, `TransportOrder`, `TransportMission`, `Invoice`, ecc.);
- rules class (`ShipmentRules`, `DriverRules`, `ComplianceRules`, ecc.);
- modelli realistici (`RealisticFleetModelTest`, `SeriousDomainIntegrationTest`);
- moduli operativi (`FuelTransactionTest`, `TireManagementTest`, `TelematicsSnapshotTest`, ecc.).

## Regola pratica

Ogni nuovo comportamento domain deve avere almeno un test.

Quando si aggiunge una classe nuova:

1. testare validazioni costruttore/factory;
2. testare almeno un caso valido;
3. testare almeno un caso non valido;
4. testare transizioni di stato se presenti;
5. testare regole pure in una classe `*RulesTest`.
