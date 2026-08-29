# TruckFlow Manager

Fondazione backend per un sistema di gestione della logistica e del trasporto merci su strada, sviluppata con Java 21 e Spring Boot.

TruckFlow Manager modella i principali concetti operativi di un'azienda di trasporti — tra cui veicoli, spedizioni, carichi, ruoli operativi, sedi, documenti e requisiti di conformità — applicando principi di Domain-Driven Design, Clean Architecture e Architettura Esagonale.

> **Stato del progetto: in sviluppo.** I layer Domain, Application e la prima fondazione del layer Infrastructure sono implementati e testati. API REST, database relazionale, sicurezza e frontend non sono ancora implementati.

## Obiettivo del progetto

Il progetto esplora come costruire un backend logistico manutenibile partendo dalle regole di business, anziché da controller e tabelle di database.

Gli obiettivi principali sono:

- modellare un dominio logistico realistico attraverso invarianti esplicite;
- mantenere la logica di business indipendente da framework e persistenza;
- esporre le operazioni applicative attraverso casi d'uso e porte;
- rendere sostituibili gli adapter infrastrutturali;
- proteggere i confini architetturali con test automatici;
- evolvere progressivamente verso un'applicazione basata su API.

## Funzionalità attuali

### Modello di dominio

Il dominio attuale comprende:

- unità di carico e requisiti di trasporto;
- requisiti di conformità e relativi stati del ciclo di vita;
- ruoli operativi: autisti, dispatcher, manager, meccanici e operatori di magazzino;
- sedi, indirizzi e coordinate geografiche;
- spedizioni, tratte, articoli, metriche, note e riferimenti;
- modelli di viaggio;
- utenti, ruoli, permessi e qualifiche;
- unità e combinazioni veicolari, carrozzerie, agganci e stati operativi;
- documenti operativi e di conformità.

### Application Layer

L'Application Layer fornisce:

- command e result come contratti applicativi espliciti;
- porte in ingresso per i casi d'uso supportati;
- porte repository in uscita;
- servizi applicativi per carichi, conformità, documenti, sedi, ruoli operativi, spedizioni e veicoli;
- gestione delle eccezioni applicative;
- indipendenza da Spring e dalle implementazioni di persistenza.

### Infrastructure Layer

L'infrastruttura attuale comprende:

- configurazione delle dipendenze tramite Spring;
- repository in-memory per sviluppo locale e test;
- repository basati su file per sedi, carichi, documenti e conformità;
- record di persistenza e mapper espliciti tra dominio e persistenza;
- un profilo di esecuzione non-web;
- test di integrazione e dei confini architetturali.

## Architettura

La direzione delle dipendenze segue i principi di Clean Architecture e Architettura Esagonale:

```text
                     API REST futura
                           |
                           v
                  Application Layer
              casi d'uso, command e porte
                           |
                           v
                      Domain Layer
          entità, value object e regole di business

Infrastructure Layer ---- implementa ----> porte in uscita
Configurazione Spring, adapter in-memory e basati su file
```

Il Domain Layer non contiene dipendenze da Spring, JPA o infrastruttura. L'Application Layer coordina il comportamento del dominio attraverso le porte, mentre i dettagli tecnici rimangono confinati nell'Infrastructure Layer.

## Tecnologie

- Java 21
- Spring Boot 3.5
- Maven
- JUnit 5
- AssertJ e Spring Test, forniti da `spring-boot-starter-test`
- Spotless con Google Java Format
- dipendenza Springdoc OpenAPI, riservata al futuro API Layer

## Struttura del progetto

```text
src/main/java/it/gabriele/truckflow/
├── domain/          # Modello di business, invarianti ed eccezioni di dominio
├── application/     # Command, result, porte e casi d'uso
├── infrastructure/  # Adapter repository, mapping e configurazione Spring
├── Main.java
└── TruckFlowApplication.java

src/test/java/it/gabriele/truckflow/
├── domain/
├── application/
├── infrastructure/
└── documentation/

docs/
├── simple/          # Documentazione introduttiva e accessibile
├── professional/    # Documentazione tecnica e architetturale
├── old_style/       # Archivio storico dello sviluppo passo per passo
└── digital/         # Lettore statico della documentazione
```

## Requisiti

Per compilare il progetto in locale sono necessari:

- JDK 21;
- Maven 3.9 o successivo;
- Git.

È possibile verificare le versioni installate con:

```bash
java -version
mvn -version
git --version
```

## Compilazione e test

Clonare la repository:

```bash
git clone https://github.com/Caprielos/truckflow-manager.git
cd truckflow-manager
```

Eseguire l'intera suite di test:

```bash
mvn clean test
```

Creare il JAR eseguibile:

```bash
mvn clean package
```

Allo stato attuale, la build esegue **266 test automatici** distribuiti tra Domain, Application e Infrastructure Layer.

Spotless è collegato alla fase `validate` di Maven, quindi la build verifica anche il rispetto delle regole di formattazione Java.

## Strategia di test

La suite attuale verifica:

- invarianti di dominio e transizioni del ciclo di vita;
- contratti di validazione dei value object;
- casi d'uso applicativi e scenari di errore;
- contratti delle porte in ingresso e in uscita;
- comportamento dei repository in-memory;
- persistenza su file e mapping;
- integrazione tra repository e casi d'uso;
- configurazione delle dipendenze Spring;
- confini architetturali tra i layer;
- coerenza di alcune parti della documentazione.

Risultato attualmente verificato:

```text
Test eseguiti: 266
Fallimenti: 0
Errori: 0
Ignorati: 0
```

## Documentazione

Il punto di ingresso principale della documentazione è [TRUCKFLOW_PROJECT_DOCUMENTATION.md](TRUCKFLOW_PROJECT_DOCUMENTATION.md).

Letture consigliate:

- [Panoramica di Ingegneria del Software](docs/professional/00-software-engineering-overview.md)
- [Documentazione professionale](docs/professional/README.md)
- [Guida semplice](docs/simple/README.md)
- [Stato attuale e prossimi passi](docs/professional/06-current-status-and-next-steps.md)
- [Lettore digitale della documentazione](docs/digital/index.html)

La panoramica professionale utilizza ISO/IEC 25010 e ISO/IEC 12207 come riferimenti metodologici. Non dichiara una certificazione ISO formale.

## Limitazioni attuali

TruckFlow Manager non è ancora un'applicazione completa pronta per la produzione. I seguenti componenti sono intenzionalmente esclusi dallo stato attuale:

- controller REST ed endpoint HTTP pubblici;
- DTO per richieste e risposte web;
- mapping degli errori HTTP;
- autenticazione e autorizzazione;
- database relazionale e migrazioni;
- JPA e Spring Data;
- configurazione per il deployment in produzione;
- frontend o dashboard per l'utente finale;
- monitoraggio, audit e strategie di backup per la produzione.

Gli attuali repository basati su file sono prototipi per contesti selezionati e sufficientemente stabili. Non sono destinati a sostituire un database transazionale in produzione.

## Roadmap

Il prossimo traguardo è l'API Layer, iniziando da una vertical slice completa per le sedi:

1. definire convenzioni e versionamento delle API;
2. introdurre DTO di richiesta e risposta;
3. esporre il primo controller REST;
4. aggiungere una gestione centralizzata degli errori API;
5. aggiungere validazione e test di integrazione con MockMvc;
6. pubblicare la documentazione OpenAPI/Swagger;
7. introdurre la Continuous Integration;
8. valutare persistenza relazionale e sicurezza nei cicli successivi.

## Decisioni progettuali

Alcune scelte sono intenzionalmente conservative:

- le classi di dominio non utilizzano annotazioni Spring o di persistenza;
- i casi d'uso applicativi dipendono da interfacce repository, non dagli adapter;
- soltanto alcuni contesti stabili dispongono di repository basati su file;
- il runtime Spring attuale è non-web;
- API, database e sicurezza vengono introdotti solo dopo averne definito i confini;
- la documentazione descrive sia lo stato attuale sia il percorso progettuale storico.

Queste decisioni privilegiano testabilità ed evoluzione controllata. I prossimi traguardi si concentreranno sulla realizzazione di funzionalità osservabili end-to-end.

## Maturità del progetto

La repository è adatta allo studio e alla discussione di:

- modellazione del dominio;
- Clean Architecture e Architettura Esagonale;
- inversione delle dipendenze;
- progettazione dei casi d'uso applicativi;
- adapter repository;
- test architetturali;
- sviluppo incrementale di un backend.

Nello stato attuale deve essere considerata una **fondazione architetturale backend**, non un prodotto completo per la gestione dei trasporti.

## Autore

Sviluppato da [Caprielos](https://github.com/Caprielos).

## Licenza

Non è ancora stata scelta una licenza per il progetto. Fino all'aggiunta di una licenza, il codice sorgente rimane pubblicamente visibile ma non viene distribuito con una licenza open source.
