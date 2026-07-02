# Punto 7F — Repository Expansion

Il Punto 7F estende il pattern validato nel Punto 7E.

Nel Punto 7E il progetto ha introdotto un primo repository reale prototipale per `Locations`, usando un file locale come meccanismo tecnico di persistenza. Il Punto 7F non cambia direzione: continua a usare repository file-backed piccoli, testabili e indipendenti da database, JPA, Spring Data e REST API.

L'obiettivo non è ancora costruire una persistence enterprise completa. L'obiettivo è validare che il pattern repository + persistence record + mapper + test tecnici possa essere riutilizzato su più contesti applicativi senza sporcare dominio e application layer.

---

## Obiettivo dello step

Il Punto 7F introduce repository reali file-backed per tre contesti sicuri e catalog-like:

- `CargoUnit`;
- `Document`;
- `ComplianceRequirement`.

Questi tre contesti sono adatti alla prima espansione perché hanno:

- identità stabile;
- codice business stabile;
- stato di ciclo vita;
- modello ricostruibile attraverso costruttori di dominio;
- basso rischio rispetto a Shipments, Vehicles e Operational Roles completi.

Il repository pilota `FileLocationRepository` resta valido e non viene rimosso.

---

## Cosa è stato aggiunto

### Supporto file-backed condiviso

È stato introdotto il package:

```text
it.gabriele.truckflow.infrastructure.repository.file
```

Contiene:

- `FileRecordCodec`;
- `FileRepositoryStorage`;
- `FileRepositoryText`.

Questi componenti centralizzano la logica tecnica comune:

- lettura file;
- scrittura file;
- codifica sicura dei campi testuali;
- gestione di file mancanti come repository vuoti;
- trasformazione dei record in righe testuali;
- gestione di errori tecnici tramite `RepositoryException`.

Questa logica è infrastrutturale. Non entra nel dominio e non entra nell'application layer.

---

## Cargo repository expansion

Per Cargo sono stati introdotti:

```text
FileCargoUnitRepository
CargoUnitPersistenceRecord
CargoUnitPersistenceMapper
CargoUnitFileRecordCodec
```

`FileCargoUnitRepository` implementa il port applicativo:

```text
CargoUnitRepository
```

Supporta:

- `save`;
- `findById`;
- `findByCode`;
- `existsById`;
- `existsByCode`.

Il mapper conserva i principali value object del cargo:

- categorie;
- dimensioni;
- pesi;
- packaging;
- temperatura;
- hazard;
- requisiti regolatori;
- proprietà;
- requisiti di compatibilità.

Non introduce controlli di compatibilità infrastrutturali. Le regole restano nel dominio.

---

## Documents repository expansion

Per Documents sono stati introdotti:

```text
FileDocumentRepository
DocumentPersistenceRecord
DocumentPersistenceMapper
DocumentFileRecordCodec
```

`FileDocumentRepository` implementa il port applicativo:

```text
DocumentRepository
```

Supporta:

- `save`;
- `findById`;
- `findByCode`;
- `existsById`;
- `existsByCode`.

Il mapper conserva:

- classificazione documento;
- stato logico;
- metadata;
- contenuto logico;
- references;
- note.

Questo repository resta volutamente logico. Non introduce file upload, binary storage, PDF generation, firma digitale, versioning documentale o workflow.

---

## Compliance repository expansion

Per Compliance sono stati introdotti:

```text
FileComplianceRequirementRepository
ComplianceRequirementPersistenceRecord
ComplianceRequirementPersistenceMapper
ComplianceRequirementFileRecordCodec
```

`FileComplianceRequirementRepository` implementa il port applicativo:

```text
ComplianceRequirementRepository
```

Supporta:

- `save`;
- `findById`;
- `findByCode`;
- `existsById`;
- `existsByCode`.

Il mapper conserva:

- categoria compliance;
- tipo requisito;
- livello di obbligo;
- severità;
- target;
- rule;
- source;
- jurisdiction;
- stato.

Questo repository non calcola scadenze, violazioni, audit trail, notifiche o country engine operativo.

---

## Perché non espandere tutto subito

Il Punto 7F non crea ancora repository file-backed completi per:

- Shipments;
- Vehicles;
- Operational Roles;
- TripTemplates.

La scelta è intenzionale.

Questi contesti sono più complessi:

- Shipments contiene item, legs, metriche, riferimenti e state machine;
- Vehicles contiene specifiche tecniche, body profile, coupling, capability e combination;
- Operational Roles contiene profili, qualificazioni, scope e relazioni con User;
- TripTemplates non ha ancora use case applicativi attivi nel Punto 6.

Forzarli ora significherebbe creare mapping troppo grandi prima di aver rafforzato i test infrastrutturali generali. Verranno trattati dopo aver consolidato il pattern e la strategia di testing del Punto 7G.

---

## Test introdotti

È stato aggiunto:

```text
FileRepositoryExpansionTest
```

Il test verifica:

- implementazione dei port applicativi;
- implementazione di `InfrastructureRepositoryAdapter`;
- salvataggio e ricaricamento da nuova istanza repository;
- rifiuto dei duplicati business code;
- file mancanti trattati come repository vuoti;
- input nulli rifiutati prima del lavoro tecnico;
- round-trip dei mapper domain ↔ persistence record.

Questi sono test tecnici infrastrutturali, non test di business.

---

## Confini rispettati

Il Punto 7F non introduce:

- database;
- JPA;
- Hibernate;
- Spring Data;
- schema SQL;
- REST API;
- controller;
- DTO web;
- security;
- JWT;
- servizi esterni;
- file upload;
- storage binario documentale;
- workflow documentali;
- audit trail;
- dashboard;
- planning;
- tracking.

Spring resta limitato al wiring già introdotto nel Punto 7C.

---

## Stato finale del Punto 7F

Dopo questo step, il progetto ha repository reali file-backed per:

- Locations, introdotto nel Punto 7E;
- Cargo, introdotto nel Punto 7F;
- Documents, introdotto nel Punto 7F;
- Compliance, introdotto nel Punto 7F.

I repository in-memory restano disponibili e non vengono sostituiti.

Il Punto 7F completa la prima espansione controllata dei repository reali e prepara il progetto al Punto 7G.

---

## Prossimo step

Il prossimo step consigliato è:

```text
Punto 7G — Infrastructure Testing
```

Il 7G dovrà rafforzare la copertura tecnica su:

- repository file-backed;
- mapper;
- supporto file condiviso;
- error handling infrastrutturale;
- confini architetturali;
- assenza di layer prematuri.
