# 12. Dominio `domain.compliance`

## Indice

1. Obiettivo del dominio compliance
2. Principio fondamentale
3. Struttura del package
4. Aggregate root `ComplianceRequirement`
5. Identità, codice e stato
6. Categoria, tipo, obbligatorietà e severità
7. Target astratto della regola
8. Regola descrittiva
9. Fonte del requisito
10. Giurisdizione
11. Invarianti principali
12. Relazione con gli altri domini
13. Cosa non appartiene al dominio compliance puro
14. Esempio concettuale
15. Sintesi finale

---

## 1. Obiettivo del dominio compliance

Il dominio `domain.compliance` rappresenta i requisiti di conformità che governano i concetti principali di TruckFlow.

Serve a descrivere:

- quali requisiti esistono;
- a quale tipo di dominio si applicano;
- quale categoria riguardano;
- che tipo di requisito sono;
- quanto sono obbligatori;
- quanto sono severi;
- quale regola descrivono;
- da quale fonte derivano;
- in quale ambito geografico, normativo o aziendale valgono.

Il dominio compliance completa la prima grande fondazione del dominio puro di TruckFlow, insieme a users, qualifications, operational, vehicles, cargo, locations, triptemplates, shipments e documents.

Non completa “tutto il progetto per sempre”: in futuro potranno nascere altri bounded context enterprise come customers, contracts, pricing, maintenance, incidents, claims, alerts o costing. Tuttavia, per la prima fase del dominio puro, compliance è il pezzo che chiude il modello base.

---

## 2. Principio fondamentale

Nel dominio puro, compliance significa **requisito astratto**, non controllo operativo.

Quindi `domain.compliance` non deve dire se oggi una spedizione è conforme, se un documento è scaduto o se un veicolo può partire.

Deve solo descrivere che un requisito esiste.

Esempi:

- il cargo ADR richiede requisiti ADR;
- una spedizione internazionale può richiedere un documento CMR;
- un veicolo può richiedere una certificazione tecnica;
- una persona operativa può richiedere una qualificazione;
- una regola interna aziendale può richiedere una procedura di sicurezza.

Il controllo concreto arriverà più avanti in application layer, planning, dispatching, audit o moduli di compliance check.

---

## 3. Struttura del package

La struttura scelta è piatta e leggibile:

```text
it.gabriele.truckflow.domain.compliance
├── ComplianceRequirement.java
├── ComplianceRequirementId.java
├── ComplianceRequirementCode.java
├── ComplianceRequirementStatus.java
│
├── ComplianceCategory.java
├── ComplianceRequirementType.java
├── ComplianceObligationLevel.java
├── ComplianceSeverity.java
│
├── ComplianceRule.java
├── ComplianceTarget.java
├── ComplianceTargetType.java
│
├── ComplianceSource.java
├── ComplianceSourceType.java
├── ComplianceJurisdiction.java
├── CountryCode.java
├── JurisdictionRegion.java
├── ComplianceJurisdictionScope.java
└── ComplianceValidation.java
```

Questa struttura non introduce sottopackage perché il dominio è ancora compatto. Se in futuro crescerà molto, potrà essere diviso per concetti, come già fatto per `domain.vehicles` e `domain.shipments`.

---

## 4. Aggregate root `ComplianceRequirement`

`ComplianceRequirement` è l'aggregate root del dominio compliance.

Rappresenta un requisito astratto di conformità.

La struttura concettuale è:

```text
ComplianceRequirement
├─ ComplianceRequirementId id
├─ ComplianceRequirementCode code
├─ name
├─ description
├─ ComplianceRequirementStatus status
├─ ComplianceCategory category
├─ ComplianceRequirementType type
├─ ComplianceObligationLevel obligationLevel
├─ ComplianceSeverity severity
├─ ComplianceTarget target
├─ ComplianceRule rule
├─ ComplianceSource source
├─ ComplianceJurisdiction jurisdiction
└─ notes
```

Il requisito non contiene risultati di verifica, non contiene violazioni, non contiene scadenze e non contiene riferimenti concreti a specifiche entità del sistema.

---

## 5. Identità, codice e stato

### `ComplianceRequirementId`

È l'identificatore tecnico del requisito.

Serve al sistema e non all'utente.

### `ComplianceRequirementCode`

È il codice aziendale leggibile del requisito.

Esempi:

```text
CMP-ADR-001
CMP-VEH-REV-001
CMP-DOC-CMR-001
CMP-ATP-001
```

Questa scelta è coerente con `CargoCode`, `FleetCode`, `ShipmentCode`, `TripTemplateCode`, `LocationCode`, `OperationalCode` e `DocumentCode`.

### `ComplianceRequirementStatus`

Lo stato è anagrafico, non operativo:

```text
ACTIVE
SUSPENDED
ARCHIVED
DISCONTINUED
```

Non sono stati del requisito puro:

```text
PASSED
FAILED
VIOLATED
EXPIRED
PENDING_REVIEW
```

Questi appartengono a verifiche, audit, scadenze o workflow futuri.

---

## 6. Categoria, tipo, obbligatorietà e severità

### `ComplianceCategory`

Classifica l'area del requisito:

```text
GENERIC
VEHICLE
OPERATIONAL
CARGO
SHIPMENT
LOCATION
TRIP_TEMPLATE
DOCUMENT
COMPLIANCE
SAFETY
FINANCIAL
LEGAL
```

`DOCUMENT` è presente perché TruckFlow ora contiene anche `domain.documents`.

### `ComplianceRequirementType`

Descrive il tipo di requisito:

```text
DOCUMENT_REQUIRED
QUALIFICATION_REQUIRED
VEHICLE_CAPABILITY_REQUIRED
VEHICLE_CERTIFICATION_REQUIRED
CARGO_REGULATORY_REQUIRED
TEMPERATURE_CONTROL_REQUIRED
SAFETY_REQUIREMENT
LEGAL_REQUIREMENT
INTERNAL_POLICY
CUSTOMER_REQUIREMENT
```

### `ComplianceObligationLevel`

Descrive il livello di obbligatorietà:

```text
MANDATORY
REQUIRED
RECOMMENDED
OPTIONAL
```

La distinzione tra `MANDATORY` e `REQUIRED` è intenzionale:

- `MANDATORY` indica un obbligo forte, legale, normativo o aziendale non derogabile;
- `REQUIRED` indica un requisito richiesto dal processo, dal cliente o dal contratto;
- `RECOMMENDED` indica un requisito consigliato;
- `OPTIONAL` indica un requisito opzionale.

### `ComplianceSeverity`

Descrive la severità del mancato rispetto:

```text
LOW
MEDIUM
HIGH
CRITICAL
```

Obbligatorietà e severità sono volutamente separati.

Esempio:

```text
obligationLevel = MANDATORY
severity = CRITICAL
```

---

## 7. Target astratto della regola

`ComplianceTarget` dice a quale tipo di dominio si applica il requisito.

È astratto e non punta a un caso concreto.

```text
ComplianceTarget
├─ ComplianceTargetType targetType
└─ notes
```

I target principali sono:

```text
VEHICLE
OPERATIONAL
CARGO
SHIPMENT
LOCATION
TRIP_TEMPLATE
DOCUMENT
GENERIC
OTHER
```

Non viene introdotto un `ComplianceReference` con `referencedId`, perché quello rappresenterebbe un collegamento a una specifica istanza, per esempio un veicolo concreto o una shipment concreta. Questo tipo di relazione appartiene a futuri moduli di verifica o assessment, non al dominio puro dei requisiti.

---

## 8. Regola descrittiva

`ComplianceRule` descrive la regola da rispettare.

È importante chiarire che non è codice eseguibile.

Non contiene:

- `check()`;
- predicati;
- validator operativi;
- logica di confronto con veicoli, cargo, shipment o documenti.

Contiene solo una descrizione chiara del requisito.

Concettualmente:

```text
ComplianceRule
├─ title
├─ statement
├─ expectedCondition
└─ notes
```

Esempio:

```text
Title: ADR cargo rule
Statement: Cargo marked as ADR must require ADR-compatible transport.
Expected condition: ADR transport requirement must be declared.
```

---

## 9. Fonte del requisito

Ogni requisito di compliance deve avere una fonte.

`ComplianceSource` rappresenta da dove nasce il requisito.

```text
ComplianceSource
├─ sourceName
├─ ComplianceSourceType sourceType
├─ referenceCode
├─ description
└─ notes
```

Esempi di fonte:

```text
EU regulation
Italian road regulation
Internal company policy
Customer contract requirement
Safety policy
```

Tipi supportati:

```text
LEGAL_REGULATION
EU_REGULATION
NATIONAL_REGULATION
INTERNAL_POLICY
CUSTOMER_REQUIREMENT
CONTRACTUAL_REQUIREMENT
SAFETY_STANDARD
OTHER
```

Questa scelta rende il dominio più enterprise, perché un requisito non è solo una regola isolata: ha sempre un'origine normativa, contrattuale, aziendale o di sicurezza.

---

## 10. Giurisdizione

`ComplianceJurisdiction` descrive l'ambito di validità concettuale del requisito.

```text
ComplianceJurisdiction
├─ Optional<CountryCode> country
├─ Optional<JurisdictionRegion> region
├─ ComplianceJurisdictionScope scope
└─ notes
```

Esempi:

```text
country = IT
scope = NATIONAL
```

```text
region = EU
scope = EUROPEAN_UNION
```

```text
scope = COMPANY_INTERNAL
```

La giurisdizione non calcola se una norma vale oggi, non gestisce date di validità e non applica automaticamente regole diverse per Stato.

Descrive solo l'ambito concettuale del requisito.

`CountryCode` rappresenta un paese specifico, per esempio `IT`, `FR`, `DE` o `ES`.

`ComplianceJurisdictionScope` rappresenta il livello della giurisdizione, per esempio:

```text
NATIONAL
EUROPEAN_UNION
INTERNATIONAL
COMPANY_INTERNAL
CUSTOMER_SPECIFIC
REGIONAL
OTHER
```

`JurisdictionRegion` rappresenta un'area geografica o normativa ampia, per esempio `EU`, `EMEA`, `LOMBARDY` o `NORTH_ITALY`. È un value object leggero e non contiene una lista rigida di nazioni.

La logica applicativa del tipo "scelgo Europa, vedo le nazioni europee e il paese predefinito è Italia" non appartiene al dominio puro. Quella logica verrà gestita più avanti da application layer, configurazione aziendale o interfaccia utente.

---

## 11. Invarianti principali

Le invarianti principali sono:

- `ComplianceRequirementId` obbligatorio, generato automaticamente se assente;
- `ComplianceRequirementCode` obbligatorio e normalizzato in maiuscolo;
- `name` obbligatorio;
- `status` obbligatorio;
- `category` obbligatoria;
- `type` obbligatorio;
- `obligationLevel` obbligatorio;
- `severity` obbligatoria;
- `target` obbligatorio;
- `rule` obbligatoria;
- `source` obbligatoria;
- `jurisdiction` obbligatoria;
- note normalizzate.

Inoltre:

- `ComplianceRule` deve avere titolo e statement;
- `ComplianceSource` deve avere nome e tipo;
- `ComplianceJurisdiction` deve avere uno scope;
- `CountryCode`, quando presente, deve usare un codice paese valido;
- `JurisdictionRegion`, quando presente, deve essere normalizzata;
- `ComplianceTarget` deve avere un target type.

---

## 12. Relazione con gli altri domini

`domain.compliance` non importa classi degli altri domini.

Non importa:

- `VehicleUnitId`;
- `CargoId`;
- `ShipmentId`;
- `DocumentId`;
- `LocationId`;
- `TripTemplateId`;
- `DriverId`.

La relazione è concettuale tramite `ComplianceTargetType`.

Esempio:

```text
ComplianceRequirement
├─ category: CARGO
├─ target: CARGO
├─ type: CARGO_REGULATORY_REQUIRED
└─ rule: Cargo marked as ADR must declare ADR-compatible transport.
```

Il collegamento a un caso concreto, per esempio `Shipment SHP-001`, verrà gestito in futuro da moduli di compliance check, planning o dispatching.

---

## 13. Cosa non appartiene al dominio compliance puro

Non fanno parte di `domain.compliance` in questa fase:

- `ComplianceCheck`;
- `ComplianceResult`;
- `ComplianceViolation`;
- `ComplianceAudit`;
- `ComplianceDeadline`;
- `ComplianceApproval`;
- `ComplianceAlert`;
- scadenze;
- workflow;
- approvazioni;
- notifiche;
- controlli automatici;
- validazioni tecniche su casi concreti.

Questi concetti sono importanti, ma arriveranno più avanti in application layer o in moduli dedicati.

---

## 14. Esempio concettuale

Esempio di requisito ADR per cargo pericoloso:

```text
ComplianceRequirement
├─ code: CMP-ADR-001
├─ name: ADR requirement for dangerous cargo
├─ status: ACTIVE
├─ category: CARGO
├─ type: CARGO_REGULATORY_REQUIRED
├─ obligationLevel: MANDATORY
├─ severity: CRITICAL
├─ target:
│  └─ targetType: CARGO
├─ rule:
│  ├─ title: ADR cargo rule
│  ├─ statement: Cargo marked as ADR must require ADR-compatible transport.
│  └─ expectedCondition: ADR transport requirement must be declared.
├─ source:
│  ├─ sourceName: European ADR framework
│  ├─ sourceType: EU_REGULATION
│  └─ referenceCode: ADR
└─ jurisdiction:
   ├─ region: EU
   └─ scope: EUROPEAN_UNION
```

Questo requisito non controlla ancora nessun cargo reale. Descrive solo la regola.

---

## 15. Sintesi finale

`domain.compliance` rappresenta il catalogo dei requisiti astratti di conformità di TruckFlow.

La scelta finale è:

```text
ComplianceRequirement = aggregate root del requisito astratto
ComplianceRule = descrizione della regola
ComplianceTarget = tipo di dominio a cui si applica
ComplianceSource = origine del requisito
ComplianceJurisdiction = ambito di validità concettuale
CountryCode = paese specifico della giurisdizione
JurisdictionRegion = area geografica o normativa ampia
ComplianceJurisdictionScope = livello della giurisdizione
ComplianceObligationLevel = obbligatorietà
ComplianceSeverity = severità
```

Il dominio resta puro perché non contiene verifiche concrete, workflow, audit, scadenze, notifiche, repository o dipendenze da altri bounded context.

Questa scelta completa la prima grande fondazione del dominio puro enterprise di TruckFlow e prepara il progetto ai futuri moduli applicativi di planning, dispatching e compliance check.
