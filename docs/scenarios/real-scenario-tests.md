# Test di scenario reali

I test di scenario servono per vedere se più parti del progetto lavorano insieme.

Il file principale è:

```text
src/test/java/it/gabriele/truckflow/application/scenario/TruckFlowApplicationScenarioTest.java
```

## Scenario 1: parcheggio convoglio pronto

Il test crea:

- un deposito;
- un posto parcheggio `A12`;
- un convoglio articolato `trattore + semirimorchio`;
- un'assegnazione parcheggio.

Poi verifica:

```text
parksCombination = true
readyForMission = true
assegnazione salvata nel repository
```

## Scenario 2: ordine → spedizione → missione → chiusura

Il test crea:

- ordine accettato;
- spedizione da ordine;
- autista;
- convoglio;
- route plan;
- missione pianificata;
- missione completata.

Questo mostra che application + domain + repository memory funzionano insieme.

## Scenario 3: economics + payroll

Il test crea:

- missione;
- report lavoro autista;
- policy paga;
- payroll missione;
- revenue line;
- cost line;
- economics missione.

Poi verifica che la missione sia profittevole e che il costo autista venga calcolato.

## Scenario 4: magazzino

Il test crea:

- articolo magazzino;
- ubicazione magazzino;
- movimento di acquisto;
- movimento di consumo manutenzione;
- bilancio giacenza.

Poi verifica:

```text
giaceza iniziale: 6
consumo: 3
giaceza finale: 3
serve riordino: sì
non posso riservare 4 pezzi
```

## Come lanciare

```bash
mvn clean test
```
