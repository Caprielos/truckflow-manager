# Come leggere errori Maven

Il comando principale è:

```bash
mvn clean test
```

## Errore di compilazione

Se vedi:

```text
COMPILATION ERROR
```

vuol dire che Java non riesce a compilare.

Cause comuni:

- import mancante;
- metodo chiamato con parametri sbagliati;
- classe non trovata;
- nome file/classe non coerente.

## Errore di test

Se vedi:

```text
Failures: 1
```

vuol dire che il codice compila, ma un test si aspettava un risultato diverso.

## Error vs Failure

```text
Failure = assert fallito
Error = eccezione non prevista
```

## Dove guardare

Maven di solito indica:

```text
ClasseTest.metodoTest:riga
```

Esempio:

```text
InventoryManagementTest.shouldCalculateWarehouseStockAndReorderSignal:54
```

Apri quel file e quella riga.

## Non correggere a caso

Prima chiediti:

```text
è sbagliato il codice o è sbagliata l'aspettativa del test?
```

Nel progetto è già successo: la logica del magazzino era giusta, ma il test si aspettava il contrario.
