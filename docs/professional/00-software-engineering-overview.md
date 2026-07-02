# Software Engineering Overview — TruckFlow Manager

## 1. Executive Summary

TruckFlow Manager è un progetto Java 21 orientato alla gestione professionale di processi logistici e di trasporto merci. L'obiettivo è costruire una piattaforma enterprise capace di modellare in modo realistico veicoli, autisti, carichi, spedizioni, documenti, requisiti di compliance, sedi operative e futuri processi API.

Il progetto non nasce come semplice CRUD. La direzione architetturale scelta è costruire prima un dominio pulito e stabile, poi un application layer con casi d'uso, poi un infrastructure layer con adapter tecnici e repository concreti, e solo successivamente un API layer REST.

Lo stato attuale è:

```text
Punto 1 → 5    Domain Layer completato
Punto 6A → 6M  Application Layer completato
Punto 7A → 7H  Infrastructure Layer completato
Punto 8A → 8H  API Layer pianificato
```

TruckFlow Manager contiene già un modello ampio, use case applicativi, porte in ingresso/uscita, repository in-memory, repository file-backed limitati e documentazione organizzata. Non contiene ancora REST API reali, controller, DTO web, database relazionale, JPA, Spring Data, sicurezza HTTP o frontend.

## 2. Problema reale che il sistema risolve

Le aziende di trasporto e logistica devono coordinare molte informazioni eterogenee: veicoli, conducenti, carichi, sedi, spedizioni, documenti, scadenze, requisiti normativi, disponibilità operative e qualità del servizio.

Un sistema debole tende a mescolare queste responsabilità in controller, tabelle o servizi generici. TruckFlow Manager separa invece il problema in layer e bounded context, in modo che le regole di business restino leggibili, testabili e indipendenti dalla tecnologia.

## 3. Obiettivi del sistema

- Modellare il dominio logistico in modo realistico e incrementale.
- Separare regole di business, casi d'uso e dettagli tecnici.
- Evitare dipendenze premature da database, REST API, security o frontend.
- Preparare un'evoluzione enterprise verso API, workflow, dashboard, audit, compliance avanzata e integrazioni esterne.
- Mantenere documentazione e codice coerenti a ogni ciclo di sviluppo.

## 4. Attori e stakeholder

- Manager logistico: monitora stato operativo, scadenze, qualità e performance.
- Dispatcher: assegna risorse e coordina operazioni future.
- Driver: figura operativa soggetta a qualifiche, documenti e requisiti.
- Warehouse operator: gestisce punti di carico/scarico e flussi operativi futuri.
- Compliance manager: controlla requisiti normativi e documentali.
- Sviluppatore/manutentore: evolve il sistema rispettando i confini architetturali.

## 5. Requisiti funzionali principali

| ID | Requisito | Stato | Layer coinvolti |
| --- | --- | --- | --- |
| RF-01 | Gestire concetti di utenti e ruoli operativi | Presente nel dominio | Domain |
| RF-02 | Modellare qualifiche, certificazioni e requisiti | Presente nel dominio | Domain |
| RF-03 | Modellare veicoli e concetti tecnici | Presente nel dominio | Domain |
| RF-04 | Modellare cargo, location, shipments, documents e compliance | Presente nel dominio | Domain |
| RF-05 | Eseguire use case applicativi tramite port in | Presente | Application |
| RF-06 | Usare repository port indipendenti dall'infrastructure | Presente | Application |
| RF-07 | Salvare contesti selezionati su file-backed repositories | Presente in modo limitato | Infrastructure |
| RF-08 | Esporre REST API versionate | Pianificato | API futura |
| RF-09 | Introdurre error handling API e DTO web | Pianificato | API futura |
| RF-10 | Introdurre DB/JPA/Spring Data/security | Futuro, non ancora presente | Infrastructure/API futura |

## 6. Requisiti non funzionali

| Qualità | Descrizione | Stato attuale |
| --- | --- | --- |
| Manutenibilità | Codice diviso per layer e bounded context | Prioritaria e già applicata |
| Testabilità | Domain e application testabili senza web/database | Già applicata |
| Scalabilità architetturale | API, DB, security e integrazioni saranno aggiunti per layer | Pianificata |
| Osservabilità | Dashboard, KPI e audit non sono ancora implementati | Futuro |
| Sicurezza | Nessuna security HTTP/JWT ancora introdotta | Futuro |
| Portabilità | Persistenza file-backed limitata, senza DB obbligatorio | Presente |
| Chiarezza documentale | Documentazione simple, professional, old_style e digital | In consolidamento |

## 7. Architettura software

La direzione architetturale è:

```text
API Layer futuro
        ↓
Application Layer
        ↓
Domain Layer

Infrastructure Layer implementa le port dell'application
e fornisce dettagli tecnici senza contaminare i layer interni.
```

Regole principali:

- `domain` non dipende da Spring, JPA, REST o infrastructure.
- `application` orchestra use case e dipende dal dominio.
- `infrastructure` implementa dettagli tecnici e adapter.
- `api` sarà un layer separato e userà solo use case applicativi.
- I controller futuri non chiameranno repository concreti.

## 8. Bounded context principali

- `domain.users`
- `domain.qualifications`
- `domain.operational`
- `domain.vehicles`
- `domain.cargo`
- `domain.locations`
- `domain.triptemplates`
- `domain.shipments`
- `domain.documents`
- `domain.compliance`

I package application e infrastructure collegano questi contesti a casi d'uso, port, repository memory, mapping e repository file-backed selezionati.

## 9. Tecnologie adottate

- Java 21
- Maven
- JUnit
- Spring Boot come wiring tecnico non-web
- Repository in-memory
- Repository file-backed per contesti selezionati
- Documentazione Markdown/HTML statica

Tecnologie rimandate:

- REST API reali
- Spring MVC controller
- JPA / Spring Data
- database relazionale
- security HTTP / JWT
- frontend
- workflow e audit trail

## 10. Stato attuale del progetto

Sono completati:

- Domain Layer: Punti 1 → 5
- Application Layer: Punto 6A → 6M
- Infrastructure Layer: Punto 7A → 7H
- Digital Documentation Reader: in fase di refinement

Repository file-backed già validati:

- Locations
- Cargo
- Documents
- Compliance

## 11. Cosa manca

Mancano ancora:

- API Layer
- controller REST
- DTO web
- mapper API
- error handling API
- OpenAPI/Swagger review
- security
- database/JPA/Spring Data
- frontend
- audit trail
- workflow
- integrazioni esterne

Queste mancanze non sono errori: sono scelte di roadmap per evitare complessità prematura.

## 12. Roadmap

| Ciclo | Stato | Contenuto |
| --- | --- | --- |
| Punto 1 → 5 | Completato | Domain Layer |
| Punto 6A → 6M | Completato | Application Layer |
| Punto 7A → 7H | Completato | Infrastructure Layer |
| Punto 8A → 8H | Pianificato | API Layer |

Roadmap API futura:

```text
8A — API Layer Blueprint
8B — API Layer Foundation
8C — Locations Controller Prototype
8D — API DTO & Mapping Conventions
8E — API Error Handling
8F — API Controller Tests
8G — OpenAPI & Swagger Review
8H — API Layer Freeze
```

## 13. Rischi tecnici e mitigazioni

| Rischio | Mitigazione |
| --- | --- |
| Introdurre REST/API troppo presto | Avviare Punto 8 solo dopo il consolidamento documentale |
| Mescolare infrastructure e application | Test architetturali e regole di dipendenza |
| Documentazione non allineata al codice | Digital Reader con catalogo statico aggiornato |
| Package Explorer troppo grande | Albero gerarchico con dettaglio selezionato |
| Crescita incontrollata dei bounded context | Roadmap e freeze per layer |

## 14. Glossario essenziale

- Domain Layer: layer con regole e concetti di business.
- Application Layer: layer dei casi d'uso e delle port.
- Infrastructure Layer: layer tecnico con adapter e repository concreti.
- API Layer: futuro layer REST versionato.
- Port: interfaccia che definisce un ingresso o un'uscita applicativa.
- Adapter: implementazione tecnica di una port.
- Repository: astrazione per salvare o recuperare dati.
- Freeze: punto di stabilizzazione che blocca derive premature.
