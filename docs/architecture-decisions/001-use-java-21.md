# ADR 001 — Uso di Java 21

## Stato

Accettata

## Data

2026-06-28

## Contesto

TruckFlow Manager è un progetto backend Java pensato per diventare un gestionale realistico per aziende di trasporto merci su strada.

Il progetto deve essere:

- professionale;
- mantenibile;
- scalabile;
- adatto a un portfolio serio;
- sviluppato gradualmente nel tempo.

All’inizio il progetto partirà dal dominio puro in Java.  
In futuro potrà evolvere in una web application con:

- Spring Boot;
- PostgreSQL;
- API REST;
- frontend web;
- autenticazione e autorizzazione;
- gestione documenti;
- simulazione tracking;
- possibile integrazione con Google Maps;
- possibili servizi esterni per pedaggi, carburante e notifiche.

Dato che il progetto crescerà nel tempo, è importante scegliere una versione di Java stabile, moderna e adatta allo sviluppo backend professionale.

---

## Decisione

TruckFlow Manager userà Java 21 LTS come versione principale di Java.

Il progetto userà Eclipse Temurin JDK 21.

---

## Motivazioni

Java 21 è una versione LTS, cioè Long-Term Support.

Questo significa che è una versione stabile, supportata nel tempo e adatta a progetti professionali.

Java 21 permette di usare una versione moderna del linguaggio, mantenendo allo stesso tempo compatibilità con strumenti molto usati nel mondo backend.

Java 21 è adatto per lavorare con:

- Maven;
- JUnit;
- Spring Boot;
- PostgreSQL;
- Docker;
- GitHub Actions;
- IntelliJ IDEA;
- strumenti di sviluppo moderni.

Questa scelta ci permette di costruire un progetto moderno senza usare una versione troppo sperimentale.

---

## Conseguenze

Tutti gli sviluppatori che lavorano al progetto dovranno usare Java 21.

Il file `pom.xml` dovrà essere configurato per usare Java 21.

La documentazione del progetto dovrà indicare Java 21 come requisito tecnico.

Le dipendenze future dovranno essere compatibili con Java 21.

---

## Alternative considerate

### Java 17

Java 17 è una versione LTS molto usata in produzione.

Sarebbe stata una scelta sicura, ma Java 21 è più recente, sempre LTS e più adatta a un nuovo progetto che vuole essere moderno.

### Versioni Java più recenti non LTS

Versioni più recenti di Java potrebbero offrire funzionalità aggiuntive.

Tuttavia, non sono la scelta migliore per questo progetto perché non garantiscono la stessa stabilità a lungo termine di una versione LTS.

---

## Decisione finale

TruckFlow Manager userà Java 21 LTS.

Questa scelta supporta l’obiettivo di costruire un backend Java moderno, stabile e professionale.