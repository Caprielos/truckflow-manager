# ADR 001 — Use Java 21

## Decisione

Il progetto usa Java 21 come baseline.

## Motivazione

Java 21 è una versione moderna e stabile, adatta a un progetto portfolio serio. Permette di usare una sintassi aggiornata mantenendo compatibilità con Maven e JUnit 5.

## Conseguenze

Il `pom.xml` deve compilare con release 21.

Prima di ogni commit:

```bash
mvn clean test
```
