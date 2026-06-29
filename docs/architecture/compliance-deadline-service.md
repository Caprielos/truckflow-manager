# Compliance Deadline Service

## Obiettivo

Il `compliance-deadline-service` è il microservizio autonomo responsabile di tutte le scadenze e conformità dell'ecosistema TruckFlow Manager.

Diventa l'unica fonte di verità per:

- scadenze legali nazionali ed europee;
- scadenze tecniche definite da costruttori, manuali, contratti di manutenzione o configurazioni aziendali;
- scadenze e controlli operativi interni;
- monitoraggi continui legati a telemetria, sicurezza, qualità e viaggio;
- alert, workflow, audit, blocchi operativi e override autorizzati.

Il dominio principale non calcola più scadenze. I servizi principali descrivono solo gli oggetti e i fatti disponibili. Il microservizio delle scadenze decide se l'oggetto è soggetto a regole, da quali fonti derivano e quale stato operativo produce.

## Principio architetturale

Ogni servizio che legge o usa un oggetto deve poter inviare al microservizio un descrittore generico:

```text
oggetto reale + fatti disponibili + contesto operativo
```

Il microservizio risponde con:

```text
scadenze applicabili + stato + spiegazione + blocchi + alert/workflow richiesti
```

Il servizio chiamante non deve conoscere la formula di calcolo.

## Nome del servizio

Nome tecnico consigliato:

```text
compliance-deadline-service
```

Nome breve accettabile:

```text
deadline-service
```

`compliance-deadline-service` è preferibile perché il servizio non gestisce solo date, ma anche conformità, obblighi, blocchi, alert, workflow, audit e monitoraggio.

## Responsabilità incluse

Il microservizio deve gestire:

1. Regole legali.
2. Regole tecniche del costruttore.
3. Regole operative interne.
4. Regole di monitoraggio continuo.
5. Regole di blocco operativo.
6. Override autorizzati.
7. Audit completo.
8. Spiegazione del risultato.
9. Versionamento dei rule pack.
10. Slot configurabili anche vuoti.

## Responsabilità escluse

Il microservizio non deve gestire direttamente:

- anagrafica completa dei camion;
- anagrafica completa dei rimorchi;
- anagrafica completa degli autisti;
- pianificazione completa dei viaggi;
- magazzino completo;
- telemetria grezza completa;
- document management completo;
- manutenzione operativa come ordine di lavoro completo.

Questi domini restano nei rispettivi servizi. Il microservizio riceve solo i dati necessari per valutare regole e scadenze.

## Struttura logica

```text
compliance-deadline-service
 ├─ api
 │   ├─ REST API
 │   ├─ future gRPC API
 │   ├─ evaluate single subject
 │   ├─ evaluate batch
 │   ├─ query deadline state
 │   └─ admin rule pack API
 │
 ├─ application
 │   ├─ EvaluateDeadlineUseCase
 │   ├─ EvaluateDeadlineBatchUseCase
 │   ├─ ImportDeadlineRulePackUseCase
 │   ├─ ActivateDeadlineRulePackUseCase
 │   ├─ GenerateDeadlineAlertsUseCase
 │   ├─ RequestWorkflowUseCase
 │   └─ ApplyDeadlineOverrideUseCase
 │
 ├─ domain
 │   ├─ DeadlineRule
 │   ├─ DeadlineRulePack
 │   ├─ ManagedElementCatalog
 │   ├─ DeadlineSubject
 │   ├─ DeadlineEvaluation
 │   ├─ DeadlineState
 │   ├─ DeadlineRuleSource
 │   ├─ RuleCondition
 │   ├─ RuleDependency
 │   ├─ BlockingPolicy
 │   ├─ DeadlineOverride
 │   ├─ DeadlineEvidence
 │   ├─ DeadlineAuditEntry
 │   └─ DeadlineCalculationEngine
 │
 ├─ config
 │   ├─ deadline-rule-pack.yml
 │   ├─ schema validation
 │   ├─ versioning
 │   └─ tenant-specific packs
 │
 ├─ infrastructure
 │   ├─ deadline state repository
 │   ├─ rule pack repository
 │   ├─ audit repository
 │   ├─ object storage / PDF evidence links
 │   ├─ event publisher
 │   └─ tenant-aware config store
 │
 └─ integration
     ├─ alert-service client
     ├─ workflow-service client
     ├─ audit-service client
     ├─ fleet-service client
     ├─ driver-service client
     ├─ trailer-service client
     ├─ cargo-service client
     ├─ warehouse-service client
     ├─ trip-service client
     ├─ telematics-service client
     └─ document-service client
```

## Regole gestite

### Regole legali

Derivano da normative europee, nazionali o locali. Dipendono dallo Stato configurato, dal tipo di oggetto e dal contesto.

Esempi:

- revisione mezzo;
- assicurazione mezzo;
- tachigrafo;
- carta tachigrafica;
- ore guida e riposo;
- ATP;
- ADR;
- patente;
- CQC;
- visite mediche;
- corsi obbligatori.

Queste regole devono essere versionate e collegate a riferimenti documentali o normativi.

### Regole tecniche del costruttore

Derivano da manuali, schede tecniche, costruttore, modello, anno, allestimento o contratto manutentivo.

Esempi camion:

- olio motore;
- filtri;
- freni;
- liquido refrigerante;
- AdBlue;
- cinghie;
- batteria;
- sospensioni;
- luci;
- diagnostica motore.

Esempi rimorchio:

- impianto frenante rimorchio;
- impianto elettrico rimorchio;
- impianto refrigerante;
- pianale;
- porte;
- ralla;
- sponde idrauliche;
- piedini e attacchi.

Queste regole non devono essere hardcodate nel codice. Devono stare nel rule pack configurabile.

### Regole operative interne

Derivano da procedure aziendali, contratti cliente, checklist operative, SLA o policy interne.

Esempi:

- controllo sigilli;
- controllo etichette;
- controllo peso reale vs dichiarato;
- celle frigo;
- attrezzature magazzino;
- impianti di sicurezza;
- controlli pre-partenza;
- controlli carico e scarico;
- POD obbligatorio.

Queste regole possono produrre workflow e blocchi anche se non hanno una data classica di scadenza.

### Monitoraggi continui

Derivano da eventi e sensori.

Esempi:

- CANBUS;
- errori motore DTC;
- temperatura;
- pressione pneumatici TPMS;
- consumi reali;
- stato porte;
- aperture non autorizzate;
- allarmi;
- sensori intrusione;
- telecamere di bordo.

Qui il concetto non è solo `prossima data`, ma anche `evento rilevante`, `anomalia`, `stato cambiato`, `workflow richiesto`.

## Concetti centrali

### DeadlineRule

La regola configurata. Descrive quando una scadenza o un controllo si applica.

### DeadlineRulePack

Insieme versionato di regole. Può essere globale, per Paese, per tenant, per cliente o per flotta.

### ManagedElementCatalog

Catalogo completo degli elementi che il sistema può valutare. Serve per non dimenticare nulla.

### DeadlineSubject

Descrittore generico dell'oggetto valutato. Non contiene classi Java del dominio principale.

### DeadlineEvaluation

Risultato del calcolo in un momento specifico.

### DeadlineState

Stato persistito nel tempo. Serve per sapere se lo stato è cambiato e se generare alert o workflow.

### DeadlineEvidence

Riferimenti a PDF, manuali, normative, contratti, schede tecniche e documentazione.

### DeadlineAuditEntry

Tracciamento di calcoli, modifiche regola, override, attivazioni di rule pack e cambi di stato.

## Stati standard

```text
NOT_APPLICABLE
CONFIGURATION_MISSING
OK
DUE_SOON
DUE_NOW
OVERDUE
BLOCKING
SUSPENDED
MANUAL_REVIEW_REQUIRED
```

## Regole di priorità

Se più regole si applicano allo stesso elemento, il motore deve scegliere la più restrittiva o quella che porta alla scadenza più vicina.

Esempio:

```text
olio motore
regola costruttore: 90.000 km
regola interna aziendale: 75.000 km
risultato: vince 75.000 km
```

## Spiegabilità obbligatoria

Ogni valutazione deve spiegare:

- quale oggetto è stato valutato;
- quale elemento è stato valutato;
- quale regola è stata applicata;
- quale fonte ha prodotto la regola;
- quali dati sono stati usati;
- quale versione del rule pack è stata usata;
- perché lo stato finale è stato assegnato;
- se ci sono blocchi, alert, workflow o override.

## Multi-tenancy

Ogni richiesta deve portare almeno:

```text
tenantId
objectRef
country
facts
```

Il servizio deve poter usare rule pack diversi per aziende diverse.

## Statelesness

Il calcolo deve essere stateless: a parità di input e rule pack, produce lo stesso risultato.

Lo stato persistito serve per:

- storico;
- confronto con stato precedente;
- alert una tantum;
- audit;
- dashboard;
- workflow.

## Collegamento con gli altri servizi

Gli altri servizi non devono importare classi interne del microservizio. Devono comunicare tramite:

- API REST sincrone per decisioni immediate;
- eventi asincroni per aggiornamenti e scalabilità;
- DTO stabili e versionati.

## Decisione architetturale

La logica delle scadenze deve essere progressivamente rimossa da:

```text
domain.deadlinepolicy
domain.deadline
application.usecase.deadlinepolicy
application.usecase.deadline
```

Nel monolite principale deve restare solo una porta di comunicazione:

```text
DeadlineGateway
```

In futuro il gateway sarà implementato da un client HTTP/event-driven verso il microservizio.
