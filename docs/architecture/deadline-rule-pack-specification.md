# Specifica deadline-rule-pack.yml

## Obiettivo

`deadline-rule-pack.yml` è il file unico e versionato che contiene le regole di scadenza, controllo, monitoraggio e blocco operativo usate dal `compliance-deadline-service`.

Il file deve poter contenere anche slot vuoti configurabili da interfaccia grafica. Uno slot vuoto non viene ignorato: produce stato `CONFIGURATION_MISSING` quando è applicabile.

## Struttura generale

```yaml
rulePack:
  id: "truckflow-eu-it-default"
  version: "2026.1"
  status: "DRAFT"
  tenantId: "COMPANY-001"
  defaultCountry: "IT"
  createdBy: "system"
  createdAt: "2026-01-01T00:00:00Z"

managedElements:
  - elementCode: "VEHICLE_ENGINE_OIL"
    category: "VEHICLE_TECHNICAL_COMPONENT"
    ownerTypes: ["VEHICLE"]
    requiresAtLeastOneRuleSlot: true

rules:
  - ruleId: "IVECO_SWAY_ENGINE_OIL"
    elementCode: "VEHICLE_ENGINE_OIL"
    sourceType: "MANUFACTURER"
    status: "ACTIVE"
    fillableFromUi: false
    manufacturerScope:
      manufacturer: "IVECO"
      model: "S-WAY"
    interval:
      type: "DATE_OR_DISTANCE"
      months: 12
      km: 90000
    warning:
      daysBefore: 30
      kmBefore: 5000
    blocking:
      blocksMission: false
      blocksAssignment: false
      blocksDeparture: false
      requiresManualReview: false
    workflow:
      workflowType: "MAINTENANCE_PLANNING"
    evidence:
      - title: "Manuale manutenzione Iveco S-Way"
        documentId: "PDF-IVECO-SWAY-001"

  - ruleId: "GENERIC_TRAILER_REFRIGERATION_UNIT_EMPTY"
    elementCode: "TRAILER_REFRIGERATION_UNIT"
    sourceType: "CONFIGURABLE_TECHNICAL"
    status: "EMPTY_SLOT"
    fillableFromUi: true
    manufacturerScope:
      manufacturer: "*"
      model: "*"
    interval:
      type: "DATE_OR_HOURS"
      months: null
      hours: null
```

## Metadata del rule pack

| Campo | Significato |
|---|---|
| `id` | Identificativo del pacchetto regole |
| `version` | Versione leggibile e auditabile |
| `status` | `DRAFT`, `ACTIVE`, `ARCHIVED` |
| `tenantId` | Azienda o cliente proprietario della configurazione |
| `defaultCountry` | Stato predefinito per le regole nazionali |
| `createdBy` | Utente o sistema che ha creato il pack |
| `createdAt` | Data creazione |

## Tipi di fonte

```text
LEGAL
MANUFACTURER
CONFIGURABLE_TECHNICAL
OPERATIONAL
CONTINUOUS_MONITORING
COMPANY_POLICY
CUSTOMER_CONTRACT
SECURITY_POLICY
```

## Stati regola

```text
DRAFT
ACTIVE
DISABLED
ARCHIVED
EMPTY_SLOT
```

`EMPTY_SLOT` significa che il sistema conosce l'elemento, ma la regola deve essere compilata.

## Tipi intervallo

```text
DATE_BASED
DISTANCE_BASED
HOURS_BASED
DATE_OR_DISTANCE
DATE_OR_HOURS
EVENT_BASED
CONDITION_BASED
MANUAL_CHECK
```

## Blocking policy

Ogni regola può indicare se blocca:

- assegnazione mezzo;
- assegnazione autista;
- partenza missione;
- carico;
- scarico;
- magazzino;
- consegna;
- POD;
- uso rimorchio.

Esempio:

```yaml
blocking:
  blocksMission: true
  blocksAssignment: true
  blocksDeparture: true
  requiresManualReview: true
  severity: "CRITICAL"
```

## Evidence

Ogni regola può collegarsi a fonti documentali:

```yaml
evidence:
  - title: "Direttiva 2014/45/UE"
    url: "https://eur-lex.europa.eu/..."
    documentId: null
    evidenceType: "LEGAL_REFERENCE"

  - title: "Manuale tecnico veicolo"
    url: null
    documentId: "PDF-VEH-001"
    evidenceType: "MANUFACTURER_MANUAL"
```

Tipi evidence:

```text
LEGAL_REFERENCE
MANUFACTURER_MANUAL
TECHNICAL_DATASHEET
MAINTENANCE_CONTRACT
CUSTOMER_CONTRACT
COMPANY_POLICY
AUDIT_DOCUMENT
```

## Conditions

Le condizioni decidono quando una regola si applica.

Esempi:

```yaml
conditions:
  - field: "country"
    operator: "EQUALS"
    value: "IT"
  - field: "vehicleCategory"
    operator: "IN"
    values: ["N3", "O4"]
  - field: "cargo.adr"
    operator: "EQUALS"
    value: true
```

Operatori minimi:

```text
EQUALS
NOT_EQUALS
IN
NOT_IN
GREATER_THAN
GREATER_THAN_OR_EQUAL
LESS_THAN
LESS_THAN_OR_EQUAL
EXISTS
NOT_EXISTS
```

## RuleDependency

Alcune regole dipendono da altre.

Esempio:

```yaml
dependencies:
  - requiredElementCode: "DRIVER_ADR_CERTIFICATE"
    failureStatus: "BLOCKING"
  - requiredElementCode: "VEHICLE_ADR_CERTIFICATION"
    failureStatus: "BLOCKING"
```

## Override

Gli override non modificano la regola. Autorizzano temporaneamente una eccezione.

Esempio:

```yaml
overridePolicy:
  allowed: true
  maxDurationDays: 1
  requiresReason: true
  requiresApprover: true
  auditRequired: true
```

## Regola di copertura

Ogni elemento del `ManagedElementCatalog` deve avere almeno uno slot di regola nel rule pack.

Se manca, il test di copertura deve fallire.

Se lo slot esiste ma non è compilato, il sistema restituisce:

```text
CONFIGURATION_MISSING
```
