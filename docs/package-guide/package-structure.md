# Struttura dei package TruckFlow Manager

Questo documento riassume la struttura dei package Java dopo la riorganizzazione in fasi.

## Regola generale

TruckFlow usa una struttura ispirata a DDD e architettura esagonale:

```text
domain                -> modello business puro
application.port.in   -> contratti dei casi d’uso invocati dall’esterno
application.usecase   -> implementazioni dei casi d’uso
application.port.out  -> repository e servizi richiesti dagli use case
infrastructure.memory -> adapter in memoria per test, demo e fase pre-database
web                   -> controller REST, DTO e configurazione Spring
```

## Perché usare sottopackage per area

Il progetto è diventato enterprise e contiene molte aree: flotta, driver, spedizioni, ADR, ATP, rifiuti,
warehouse, POD, SLA, KPI, finance, sicurezza, tachigrafo e così via. Mettere tutti i file nello stesso
package renderebbe difficile capire dove cercare una classe.

Per questo ogni livello è organizzato per area:

```text
application.usecase.sla
application.port.in.sla
application.port.out.sla
infrastructure.memory.sla
```

La stessa logica vale per le altre aree.

## package-info.java

Ogni package Java deve avere un `package-info.java` in italiano. Questo file serve solo a documentare
il contenuto del package. Non deve contenere regole business, metodi o configurazioni operative.

Usiamo `package-info.java` invece di file `.txt` perché è lo standard Java e viene letto anche da Javadoc.

## Cosa non deve succedere

- Il `domain` non deve dipendere da Spring, database, controller o repository concreti.
- Gli `usecase` non devono conoscere controller REST o DTO web.
- Le interfacce `port.out` non devono contenere implementazioni tecniche.
- `infrastructure.memory` deve contenere solo adapter temporanei in memoria.
- Il package base di ogni livello deve contenere solo componenti comuni o documentazione di package.

## Prossime estensioni

Quando arriveranno database, scheduler, security reale e integrazioni esterne, dovranno nascere nuovi
adapter infrastrutturali separati, per esempio:

```text
infrastructure.jpa
infrastructure.scheduler
infrastructure.security
infrastructure.integration
```

Non bisogna mischiare questi dettagli dentro il `domain` o dentro gli `usecase`.
