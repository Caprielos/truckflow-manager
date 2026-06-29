# Note versione corrente

Questa versione include il passaggio fondamentale da solo domain model a progetto con application layer e repository in memoria.

## Aggiunte principali

```text
src/main/java/it/gabriele/truckflow/application
src/main/java/it/gabriele/truckflow/infrastructure/memory
src/test/java/it/gabriele/truckflow/application/scenario
```

## Cosa significa

Prima il progetto aveva regole e oggetti realistici, ma non aveva ancora un modo ordinato per usarli insieme.

Ora esistono use case che fanno azioni reali:

- creare spedizione da ordine accettato;
- pianificare missione;
- chiudere missione;
- assegnare parcheggio;
- calcolare economics;
- calcolare payroll autista;
- registrare movimenti di magazzino;
- registrare acquisti flotta, contratti, import e fatture fornitori;
- generare bundle documentale spedizione.

## Test scenario

Il test principale di scenario è:

```text
TruckFlowApplicationScenarioTest
```

Questo test verifica che application + infrastructure memory + domain lavorino insieme.
