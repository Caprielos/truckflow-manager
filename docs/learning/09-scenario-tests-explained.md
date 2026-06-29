# Test di scenario spiegati

I test di scenario servono per simulare una situazione reale dell'app.

File principale:

```text
src/test/java/it/gabriele/truckflow/application/scenario/TruckFlowApplicationScenarioTest.java
```

## Perché è importante

I test unitari controllano piccoli pezzi. Il test di scenario controlla che i pezzi lavorino insieme.

## Cosa simula

Il test di scenario può fare cose come:

```text
1. creo repository in memoria
2. salvo dati iniziali
3. chiamo use case application
4. creo spedizione
5. pianifico missione
6. parcheggio convoglio
7. calcolo economics
8. calcolo payroll
9. registro magazzino
10. verifico risultati
```

## Cosa devi guardare

Quando apri un test di scenario, cerca:

```text
setup dei repository
creazione dati finti ma coerenti
chiamata agli use case
assert finali
```

## I valori tipo DRV-001 sono reali?

Sono dati finti di test, ma rappresentano codici reali aziendali.

Esempio:

```text
DRV-001 = codice autista
RTE-001 = codice route plan
COMBO-CURTAIN = codice convoglio
MIS-APP-001 = codice missione
```

Servono per collegare oggetti tra loro.
