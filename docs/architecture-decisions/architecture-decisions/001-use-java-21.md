# ADR 001 — Uso di Java 21

## Stato

Accettata

## Contesto

TruckFlow Manager è un progetto backend Java pensato per diventare un gestionale realistico per aziende di trasporto merci su strada.

Il progetto deve essere:

- professionale;
- mantenibile;
- scalabile;
- adatto a un portfolio serio;
- sviluppato gradualmente.

## Decisione

TruckFlow Manager usa Java 21 LTS come versione principale.

Il `pom.xml` deve compilare con release 21.

## Motivazioni

Java 21 è una versione LTS moderna e stabile.

È adatta a:

- Maven;
- JUnit;
- Spring Boot futuro;
- PostgreSQL futuro;
- Docker futuro;
- GitHub Actions;
- IntelliJ IDEA.

## Conseguenze

- Il progetto richiede JDK compatibile con Java 21.
- Le dipendenze future devono essere compatibili con Java 21.
- Il domain resta Java puro.

## Alternative considerate

### Java 17

Molto stabile, ma meno recente.

### Versioni non LTS

Non scelte perché meno adatte a un progetto portfolio/backend stabile.

## Decisione finale

Java 21 LTS.
