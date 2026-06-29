# Testing guide

## Comando principale

```bash
mvn clean test
```

## Stato test rilevato nello zip

```text
816 test, 0 failure, 0 errori, 0 skipped
```

## Cosa testano i test

I test sono il modo principale per capire il comportamento domain. Ogni package ha test mirati su:

- validazione costruttori/factory;
- stati e transizioni;
- calcoli economici;
- regole di compatibilità;
- regole di disponibilità;
- regole documentali;
- regole di payroll;
- regole magazzino/parcheggio.

## Come leggere un errore

Quando Maven fallisce, guarda la parte finale:

```text
[ERROR] Failures:
[ERROR]   NomeTest.nomeMetodo:linea expected ... but was ...
```

Poi apri il test indicato. Spesso l'errore dice se:

- la logica domain è sbagliata;
- il test si aspetta una cosa vecchia;
- manca una validazione;
- una patch non è stata applicata correttamente.

## Regola prima del commit

Prima di ogni commit importante:

```bash
mvn clean test
git status
```
