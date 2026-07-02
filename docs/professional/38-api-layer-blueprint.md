# 38. API Layer Blueprint — Punto 8A

## 1. Scopo del documento

Questo documento formalizza il **Punto 8A — API Layer Blueprint** di TruckFlow Manager.

Il Punto 8A introduce la progettazione del futuro **API Layer** senza creare ancora controller, DTO, endpoint reali, mapper API, sicurezza HTTP, database, JPA o frontend.

L’obiettivo è definire in modo stabile il boundary esterno del sistema prima dell’implementazione runtime.

## 2. Obiettivo generale del Punto 8

Il **Punto 8** introduce il layer API del progetto:

```text
it.gabriele.truckflow.api
```

Questo layer rappresenta il boundary esterno ufficiale del sistema, cioè il punto attraverso cui client, interfacce web, strumenti esterni o futuri sistemi integrati potranno comunicare con TruckFlow Manager.

L’API Layer deve essere progettato con criteri di:

- stabilità;
- versionamento;
- chiarezza contrattuale;
- separazione dei layer;
- backward compatibility;
- testabilità;
- documentabilità;
- assenza di dipendenze tecniche premature.

La regola architetturale fondamentale è:

```text
API Layer → Application Layer → Domain Layer
```

Il layer API deve comunicare con il sistema tramite i **use case applicativi**, non tramite repository concreti o dettagli infrastrutturali.

## 3. Stato attuale prima dell’API Layer

TruckFlow Manager ha completato:

| Ciclo | Stato | Descrizione |
| --- | --- | --- |
| Punto 1 → 5 | Completato | Domain Layer, bounded context, value object, invarianti e regole di dominio. |
| Punto 6A → 6M | Completato | Application Layer, use case, command, result e port. |
| Punto 7A → 7H | Completato | Infrastructure Layer, wiring Spring non-web, repository in-memory e repository file-backed selezionati. |
| Punto 8A | Formalizzato da questo documento | API Layer Blueprint e test architetturale future-proof. |

Il progetto è Spring-ready, ma non espone ancora REST API reali.

## 4. Roadmap completa del Punto 8

| Punto | Stato dopo questo documento | Descrizione |
| --- | --- | --- |
| 8A — API Layer Blueprint | Completato | Definizione formale del layer API, versionamento, primo contesto REST e test architetturale. |
| 8B — API Layer Foundation | Prossimo | Creazione controllata dei package API e delle prime convenzioni comuni. |
| 8C — Locations Controller Prototype | Pianificato | Primo controller REST su contesto Locations. |
| 8D — API DTO & Mapping Conventions | Pianificato | Request DTO, response DTO e mapper API verso command/result applicativi. |
| 8E — API Error Handling | Pianificato | Modello errore HTTP stabile e mapping errori application/domain verso API. |
| 8F — API Controller Tests | Pianificato | Test controller con `@WebMvcTest` e use case mockati. |
| 8G — OpenAPI & Swagger Review | Pianificato | Documentazione del contratto API realmente implementato. |
| 8H — API Layer Freeze | Pianificato | Review finale e congelamento del primo ciclo API. |

## 5. Regola principale di dipendenza

La dipendenza ammessa è:

```text
api → application → domain
```

Il layer API **non deve** dipendere direttamente da:

- `it.gabriele.truckflow.infrastructure`;
- repository concreti;
- repository file-backed;
- mapper di persistenza;
- storage file;
- dettagli tecnici interni;
- package `domain` usati come scorciatoia per bypassare i use case.

La comunicazione corretta avviene tramite Application Layer:

```text
HTTP request
      ↓
API request DTO
      ↓
Application command
      ↓
Application use case
      ↓
Application result
      ↓
API response DTO
      ↓
HTTP response
```

## 6. Package API futuro

Il package radice futuro sarà:

```text
it.gabriele.truckflow.api
```

La struttura iniziale prevista è:

```text
it.gabriele.truckflow.api
└── v1
    ├── common
    └── locations
```

Significato:

| Package | Responsabilità |
| --- | --- |
| `api` | Radice del delivery layer HTTP. |
| `api.v1` | Prima major version del contratto HTTP pubblico. |
| `api.v1.common` | Componenti comuni API: error response, eventuali costanti, convenzioni comuni. |
| `api.v1.locations` | Primo contesto REST esposto in modo controllato. |

Nel Punto 8A questi package **non vengono ancora creati**. La loro creazione appartiene al Punto 8B.

## 7. Versionamento API

Il versionamento pubblico dell’API avverrà tramite path versionato:

```text
/api/v1
```

Questa strategia è **API versioning by URI path**.

Il principio seguito è la stabilità del contratto HTTP pubblico:

- `/api/v1` resta stabile per i client compatibili con la prima versione pubblica;
- modifiche compatibili possono restare nella stessa major version;
- breaking change richiedono una nuova major API version, ad esempio `/api/v2`.

Il versionamento del path API non deve essere confuso con il Semantic Versioning del software, espresso da versioni come `1.0.0`, `1.1.0` o `2.0.0`.

## 8. Primo contesto REST

Il primo contesto REST sarà:

```text
Locations
```

Motivazioni:

- è già presente nel Domain Layer;
- è già presente nell’Application Layer;
- è già supportato dall’Infrastructure Layer;
- è già supportato da repository file-backed;
- è concettualmente più semplice di shipments, vehicles o operational roles;
- permette di validare la pipeline API senza introdurre workflow complessi.

## 9. Endpoint futuri iniziali

Endpoint previsto per il primo prototipo:

```text
POST /api/v1/locations
```

Endpoint successivo previsto:

```text
GET /api/v1/locations/{id}
```

Nel Punto 8A questi endpoint sono solo documentati. Non devono essere ancora implementati.

## 10. Convenzioni future per DTO e mapping

DTO request futuro:

```text
RegisterLocationRequest
```

DTO response futuro:

```text
LocationResponse
```

Mapper API futuro:

```text
LocationApiMapper
```

Regola fondamentale:

```text
I DTO non sono oggetti di dominio.
I DTO non contengono logica business.
I DTO servono solo per comunicare con l’esterno.
```

Esempio request futura:

```json
{
  "name": "Milano Hub",
  "address": {
    "street": "Via Roma 10",
    "city": "Milano",
    "country": "IT"
  }
}
```

Esempio response futura:

```json
{
  "id": "LOC-001",
  "name": "Milano Hub",
  "address": {
    "street": "Via Roma 10",
    "city": "Milano",
    "country": "IT"
  }
}
```

Gli ID esposti via API saranno stringhe, perché il dominio usa identificativi forti e value object.

## 11. Error handling futuro

Il modello errore API dovrà essere stabile e non dovrà esporre dettagli Java interni.

Formato previsto:

```json
{
  "code": "LOCATION_NOT_FOUND",
  "category": "NOT_FOUND",
  "message": "Location not found"
}
```

Categorie iniziali previste:

- `VALIDATION_ERROR`;
- `NOT_FOUND`;
- `CONFLICT`;
- `INTERNAL_ERROR`.

L’API non dovrà esporre:

- stack trace;
- nomi di classi interne;
- path locali;
- dettagli file-backed;
- dettagli infrastructure;
- messaggi tecnici non filtrati.

## 12. Test architetturale del Punto 8A

Il Punto 8A introduce:

```text
ApiLayerArchitectureTest
```

Percorso:

```text
src/test/java/it/gabriele/truckflow/architecture/ApiLayerArchitectureTest.java
```

Il test è future-proof: deve passare anche se `it.gabriele.truckflow.api` non esiste ancora.

Quando il package API verrà creato, il test dovrà proteggere queste regole:

- API può dipendere dall’Application Layer;
- API non può dipendere dall’Infrastructure Layer;
- API non può usare repository concreti;
- API non deve bypassare l’Application Layer usando direttamente il Domain Layer;
- Domain non può dipendere da API;
- Application non può dipendere da API.

## 13. Cosa è ammesso nel Punto 8A

Il Punto 8A ammette:

- documentazione del blueprint API;
- definizione del versionamento;
- definizione del primo contesto REST;
- definizione degli endpoint futuri;
- definizione delle regole architetturali;
- test architetturale future-proof;
- aggiornamento della documentazione generale.

## 14. Cosa non è ammesso nel Punto 8A

Il Punto 8A non deve introdurre:

- controller REST;
- DTO API;
- endpoint reali;
- mapper API;
- security;
- JWT;
- database;
- JPA;
- Spring Data;
- frontend;
- workflow;
- audit trail;
- dipendenze API verso Infrastructure;
- dipendenze Domain/Application verso API.

## 15. Motivazione architetturale

La scelta di iniziare con un blueprint è coerente con l’approccio adottato nei cicli precedenti:

```text
prima si progetta
poi si crea la fondazione
poi si introduce un prototipo controllato
poi si consolidano convenzioni
poi si testa
poi si documenta
poi si congela
```

Questo riduce il rischio di creare un delivery layer disordinato, accoppiato ai repository o contaminato da logica business.

## 16. Alternative escluse

| Alternativa | Motivo dell’esclusione |
| --- | --- |
| Creare subito tutti i controller | Rischio di API superficiale e accoppiata ai dettagli interni. |
| Esporre direttamente repository | Violazione della Clean Architecture e del principio API → Application. |
| Creare DTO per tutti i bounded context | Espansione prematura e alto rischio di incoerenza. |
| Partire da Shipments | Contesto più complesso, legato a workflow e pianificazione futura. |
| Introdurre security subito | Security HTTP ha senso dopo un primo contratto API reale. |
| Introdurre database/JPA | La persistenza relazionale è un ciclo futuro separato. |

## 17. Primo risultato atteso

Dopo il Punto 8A il progetto deve avere:

- direzione API formalizzata;
- documento blueprint ufficiale;
- roadmap API esplicita;
- regole architetturali testate;
- nessun controller prematuro;
- nessun DTO prematuro;
- nessun endpoint prematuro;
- nessuna contaminazione di Domain, Application o Infrastructure.

## 18. Prossimo step

Il prossimo step dopo questo blueprint è:

```text
Punto 8B — API Layer Foundation
```

Il Punto 8B potrà introdurre i package base del layer API, ma dovrà continuare a evitare implementazioni REST eccessive o non motivate.
