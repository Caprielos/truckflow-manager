# 01 - Struttura del progetto spiegata semplice

Un progetto Java Maven ha spesso questa struttura:

```text
src/main/java
src/test/java
pom.xml
```

## src/main/java

Contiene il codice principale dell’applicazione.

Nel progetto:

```text
it.gabriele.truckflow.domain
it.gabriele.truckflow.application
it.gabriele.truckflow.infrastructure.memory
```

## src/test/java

Contiene i test.

Qui trovi test unitari e test di scenario.

## pom.xml

È il file Maven. Dice a Maven come compilare il progetto e quali librerie usare.

## package

Un package è una cartella logica. Serve per organizzare classi con responsabilità simili.

Esempio:

```text
domain/parking
```

contiene le classi legate al parcheggio.
