# Code style TruckFlow Manager

TruckFlow Manager usa **Google Java Style** per il codice Java.

Lo stile viene controllato automaticamente da Maven tramite Spotless.

## Comandi principali

Formatta automaticamente il codice Java:

```bash
mvn spotless:apply
```

Controlla soltanto lo stile, senza modificare i file:

```bash
mvn spotless:check
```

Esegue build e test. La fase `validate` controlla anche lo stile:

```bash
mvn clean test
```

## Workflow consigliato

Dopo aver modificato codice Java:

```bash
mvn spotless:apply
mvn clean test
```

Poi controlla Git:

```bash
git status
```

Se Spotless cambia molti file, è normale la prima volta: significa che sta uniformando il progetto allo standard.

## Perché Spotless

Spotless è stato scelto perché:

- formatta automaticamente il codice;
- usa `google-java-format`, cioè il formatter collegato a Google Java Style;
- può bloccare la build se il codice non è formattato;
- funziona bene con Maven;
- è più pratico per questo progetto rispetto a Checkstyle, che segnala errori ma di solito non formatta automaticamente.

## Cosa è una scelta nostra

Google Java Style decide la formattazione del codice Java.

La scelta di usare Spotless dentro Maven è invece una decisione del progetto TruckFlow Manager.
