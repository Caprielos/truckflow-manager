# Punto 6L — Application Compliance Base Use Cases

Il Punto 6L introduce una piccola espansione controllata dell'application layer verso il dominio **Compliance**.

Questa fase aggiunge i primi use case applicativi per gestire un catalogo base di `ComplianceRequirement`, cioè requisiti astratti di conformità già modellati nel dominio puro.

Il Punto 6L rimane rigorosamente dentro i confini dell'application layer e degli adapter in memory. Non introduce controlli legali reali, motori di validazione, workflow, audit trail, dashboard, REST API, controller Spring, database, JPA, Spring Data, security, tracking o planning.

## Obiettivo dello step

L'obiettivo è rendere il dominio `compliance` consumabile dai futuri casi d'uso applicativi senza trasformarlo ancora in un motore di compliance operativo.

Il sistema ora può:

- registrare un requisito astratto di compliance;
- trovare un requisito tramite ID;
- attivare un requisito;
- sospendere un requisito;
- archiviare un requisito;
- dismettere un requisito tramite stato `DISCONTINUED`;
- proteggere i codici duplicati;
- usare una repository port astratta;
- usare un adapter in memory per test e sviluppo locale.

## Cosa è stato aggiunto

Sono stati aggiunti i package applicativi dedicati a compliance:

```text
application.command.compliance
application.result.compliance
application.port.in.compliance
application.port.out.compliance
application.usecase.compliance
infrastructure.memory.compliance
```

Questa struttura segue la stessa organizzazione già usata per Locations, Cargo, Shipments, Documents, Vehicles e Operational Roles.

## Command applicativi

Sono stati aggiunti i command:

```text
RegisterComplianceRequirementCommand
FindComplianceRequirementCommand
ActivateComplianceRequirementCommand
SuspendComplianceRequirementCommand
ArchiveComplianceRequirementCommand
DiscontinueComplianceRequirementCommand
```

I command controllano input obbligatori e normalizzano i testi applicativi semplici dove necessario.

`RegisterComplianceRequirementCommand` richiede:

- `ComplianceRequirementCode`;
- nome;
- stato;
- categoria;
- tipo;
- obligation level;
- severity;
- target;
- rule;
- source;
- jurisdiction.

Il command non esegue controlli legali reali. Prepara soltanto l'input per creare un aggregate di dominio valido.

## Result applicativo

È stato aggiunto:

```text
ComplianceRequirementResult
```

Il result espone solo informazioni utili al livello applicativo:

- ID;
- code;
- name;
- status;
- category;
- type;
- obligation level;
- severity;
- target type;
- jurisdiction scope;
- country;
- region;
- flag `active`;
- flag `mandatory`;
- flag `critical`.

Il result non espone l'intero aggregate e rifiuta domain object nulli con `UseCaseValidationException`.

## Repository port

È stata aggiunta la port:

```text
ComplianceRequirementRepository
```

La port consente:

- `save`;
- `findById`;
- `findByCode`;
- `existsById`;
- `existsByCode`.

È una porta astratta dell'application layer. Non conosce database, JPA, query SQL, Spring Data o repository concrete.

## Repository in memory

È stato aggiunto l'adapter:

```text
InMemoryComplianceRequirementRepository
```

L'adapter gestisce due indici:

- requisiti per ID;
- ID per code.

Rifiuta input nulli con `UseCaseValidationException` e codici duplicati con `DuplicateResourceException`.

Questo adapter è utile per test e sviluppo locale, ma non rappresenta una persistenza enterprise definitiva.

## Service applicativi

Sono stati aggiunti i service:

```text
RegisterComplianceRequirementService
FindComplianceRequirementService
ActivateComplianceRequirementService
SuspendComplianceRequirementService
ArchiveComplianceRequirementService
DiscontinueComplianceRequirementService
```

I service orchestrano il dominio:

1. validano il command;
2. usano la repository port;
3. recuperano o creano l'aggregate;
4. invocano il metodo di dominio corretto;
5. salvano tramite repository port;
6. restituiscono un result applicativo.

Le regole business rimangono nel dominio `compliance`.

## Copy-on-write sulle mutazioni

Per i cambi di stato è stato aggiunto:

```text
ComplianceRequirementMutationSupport
```

Le mutazioni lavorano su una copia dell'aggregate prima del salvataggio.

Questo mantiene coerente la scelta già adottata per Shipments, Documents, Vehicles e Operational Roles: evitare che un adapter in memory conservi mutazioni parziali in caso di errore futuro.

## Test aggiunti

Sono stati aggiunti:

```text
ApplicationComplianceUseCaseExpansionTest
ApplicationComplianceRepositoryPortTest
InMemoryComplianceRepositoryTest
```

I test verificano:

- registrazione di un requisito compliance;
- ricerca tramite ID;
- passaggi di stato `ACTIVE`, `SUSPENDED`, `ARCHIVED` e `DISCONTINUED`;
- comportamento copy-on-write sui cambi di stato;
- rifiuto di codici duplicati;
- rifiuto di risorse mancanti;
- rifiuto di command nulli;
- rifiuto di dependency repository nulle;
- normalizzazione di nome, descrizione e note nel command;
- contratto della repository port;
- comportamento dell'adapter in memory.

È stato inoltre aggiornato `ApplicationUseCaseReviewTest` per includere i nuovi inbound port, service e result compliance.

## Cosa non viene ancora introdotto

Il Punto 6L non introduce:

- controlli automatici su ADR, ATP, tachigrafo, CQC, visite mediche o revisioni;
- calcolo violazioni;
- scadenze obbligatorie concrete;
- motore regole;
- audit trail;
- workflow approvativi;
- notifiche;
- dashboard compliance;
- document requirement enforcement;
- country engine concreto;
- REST API;
- controller Spring;
- database;
- JPA;
- Spring Data;
- security.

Queste funzionalità appartengono a moduli futuri.

## Confini architetturali rispettati

Il Punto 6L mantiene i confini già stabiliti:

- il dominio `compliance` contiene le invarianti;
- l'application layer orchestra i casi d'uso;
- l'infrastructure memory implementa solo adapter tecnici;
- l'application layer dipende da port astratte, non da implementazioni concrete;
- il dominio non importa application o infrastructure;
- non vengono introdotti framework.

## Documentazione aggiornata

Il Punto 6L aggiorna:

- questo documento `docs/27-application-compliance-base-use-cases.md`;
- `docs/README.md`;
- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`;
- i documenti applicativi collegati;
- `docs/12-domain-compliance.md`;
- `digitalDocs/index.html`;
- `digitalDocs/styles.css`;
- `digitalDocs/README.md`;
- `digitalDocs/truckflow-manager-enterprise-documentation.html`;
- `command_basic.md`.

La documentazione Markdown rimane la fonte principale. La documentazione digitale HTML + CSS rimane il mirror navigabile.

## Stato finale dopo il Punto 6L

Dopo il Punto 6L, l'application layer copre i primi blocchi fondamentali:

- Locations;
- Cargo;
- Shipments;
- Documents;
- Vehicles;
- Operational Roles;
- Compliance base requirements.

Il prossimo step consigliato è il **Punto 6M — Application Layer Final Review & Freeze**, cioè la chiusura finale del Punto 6 con revisione complessiva, documentazione finale e verifica dei confini architetturali.

## Allineamento Punto 6M

Il Punto 6M chiude il primo ciclo dell'application layer con una review/freeze finale. Da questo momento i contenuti documentati nei punti 6A-6L sono considerati fondazione applicativa stabile: eventuali evoluzioni future dovranno essere introdotte in nuovi punti roadmap, mantenendo ancora fuori REST API, controller, database, JPA, Spring Data, security, tracking, planning, dashboard, workflow e integrazioni esterne.
