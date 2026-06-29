# Scenario tests

I test di scenario dimostrano che più package lavorano insieme.

Il file principale è:

```text
src/test/java/it/gabriele/truckflow/application/scenario/TruckFlowApplicationScenarioTest.java
```

## Cosa prova

1. Parcheggiare un convoglio già pronto.
2. Creare spedizione da ordine accettato.
3. Pianificare e chiudere una missione.
4. Calcolare economics missione e payroll autista.
5. Registrare movimenti magazzino e controllare riordino.

## Perché è diverso da un test unitario

Un test unitario controlla una classe singola. Un test di scenario controlla un flusso aziendale.

Esempio:

```text
ordine → spedizione → missione → economics → payroll
```
