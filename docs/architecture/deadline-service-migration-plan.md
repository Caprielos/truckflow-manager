# Piano di migrazione verso compliance-deadline-service

## Obiettivo

Migrare la logica delle scadenze fuori dal dominio principale e renderla responsabilità esclusiva del `compliance-deadline-service`.

La migrazione deve essere graduale, testabile e reversibile per fasi.

## Stato attuale

Nel monolite TruckFlow Manager esistono già domini e use case legati a:

- deadline;
- deadlinepolicy;
- regulation;
- ADR;
- ATP;
- tachograph;
- driver documents;
- vehicle compliance;
- workflow;
- alerting;
- audit.

Questi elementi sono utili, ma nel nuovo disegno devono essere ricondotti a un servizio autonomo.

## Stato finale desiderato

Il dominio principale gestisce gli oggetti reali:

```text
Truck
Trailer
Driver
Cargo
Shipment
Warehouse
Trip
TelemetryReading
Document
```

Il dominio principale non calcola più scadenze.

Quando deve prendere una decisione operativa, chiama:

```text
DeadlineGateway
```

Il gateway parla con:

```text
compliance-deadline-service
```

## Fase 0 - Documentazione architetturale

Aggiungere documenti di progetto:

```text
docs/architecture/compliance-deadline-service.md
docs/architecture/deadline-rule-pack-specification.md
docs/architecture/managed-element-catalog.md
docs/architecture/deadline-service-migration-plan.md
```

Nessuna logica Java viene modificata.

## Fase 1 - Catalogo elementi in codice

Creare un catalogo Java interno al futuro servizio:

```text
src/main/java/it/gabriele/truckflow/deadlineservice/domain/ManagedElementCode.java
src/main/java/it/gabriele/truckflow/deadlineservice/domain/ManagedElementCategory.java
src/main/java/it/gabriele/truckflow/deadlineservice/domain/ManagedElementCatalog.java
```

Obiettivo: avere tutti gli elementi censiti.

## Fase 2 - Rule pack iniziale

Creare:

```text
src/main/resources/deadline-rule-pack.yml
```

Il file deve contenere:

- regole già note;
- slot vuoti configurabili;
- riferimenti evidence;
- tenant;
- Paese;
- versione.

## Fase 3 - Modello core del microservizio

Creare il modello interno:

```text
DeadlineRule
DeadlineRulePack
DeadlineSubject
DeadlineEvaluation
DeadlineState
RuleCondition
RuleDependency
BlockingPolicy
DeadlineOverride
DeadlineEvidence
DeadlineAuditEntry
```

Non collegare ancora API esterne.

## Fase 4 - Engine di calcolo

Creare:

```text
DeadlineCalculationEngine
DeadlineRuleMatcher
DeadlineStatusCalculator
DeadlineExplanationBuilder
```

Il motore deve:

- trovare regole applicabili;
- calcolare prossima scadenza;
- gestire regole vuote;
- applicare priorità;
- produrre spiegazione.

## Fase 5 - Use case applicativi

Creare:

```text
EvaluateDeadlineUseCase
EvaluateDeadlineBatchUseCase
ImportDeadlineRulePackUseCase
ActivateDeadlineRulePackUseCase
ApplyDeadlineOverrideUseCase
```

## Fase 6 - API REST

Creare endpoint:

```text
POST /v1/deadline-evaluations
POST /v1/deadline-evaluations/batch
GET  /v1/deadline-states/{objectType}/{objectId}
GET  /v1/rules/effective
POST /v1/rule-packs/import
POST /v1/rule-packs/{id}/activate
POST /v1/deadline-overrides
```

## Fase 7 - Gateway nel dominio principale

Nel monolite principale aggiungere una porta:

```text
application.port.out.deadline.DeadlineGateway
```

Implementazioni possibili:

```text
infrastructure.memory.deadline.InMemoryDeadlineGateway
infrastructure.client.deadline.HttpDeadlineClient
```

All'inizio si può usare una implementazione in memoria per non dipendere da rete o database.

## Fase 8 - Integrazione operativa

I principali use case devono chiamare `DeadlineGateway` quando serve una decisione:

- assegnazione mezzo;
- assegnazione rimorchio;
- assegnazione autista;
- partenza viaggio;
- readiness magazzino;
- gestione carico;
- tachograph readiness;
- POD;
- controllo telemetria.

## Fase 9 - Alert, workflow e audit

Il microservizio deve pubblicare o richiedere:

```text
deadline.alert.created
deadline.workflow.requested
deadline.audit.recorded
deadline.status.changed
deadline.configuration.missing
```

Nel monolite iniziale questi eventi possono essere simulati con repository in memoria.

## Fase 10 - Deprecazione vecchia logica

Deprecare progressivamente:

```text
domain.deadlinepolicy
application.usecase.deadlinepolicy
```

Non cancellare subito: prima garantire equivalenza tramite test.

## Regola di sicurezza della migrazione

Ogni fase deve rispettare:

```text
mvn spotless:apply
mvn clean test
```

Nessuna fase deve essere fusa su `main` finché la migrazione non è stabile.
