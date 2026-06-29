# Testing guide

## Comando principale

```bash
mvn clean test
```

## Test presenti

- Unit test domain.
- Test application use case.
- Test di scenario application.

## Report incluso nello zip

```text
823 test
0 failure
0 errori
0 skipped
```

## Test di scenario più importante

```text
src/test/java/it/gabriele/truckflow/application/scenario/TruckFlowApplicationScenarioTest.java
```

Questo test verifica il funzionamento combinato di:

```text
domain
application
infrastructure/memory
```
