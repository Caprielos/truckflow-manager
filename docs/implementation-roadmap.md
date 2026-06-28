# TruckFlow Manager — Implementation Roadmap

## Scopo

Questo documento definisce l’ordine di implementazione consigliato.

Il dominio è grande, ma verrà implementato a piccoli passi.

---

# Fase 0 — Documentazione

Obiettivo:

- fissare la visione del dominio;
- scrivere overview;
- scrivere domain model;
- scrivere catalogo classi;
- scrivere regole;
- scrivere glossario;
- aggiornare README.

File coinvolti:

```text
docs/domain-overview.md
docs/domain-model.md
docs/domain-class-catalog.md
docs/domain-rules.md
docs/glossary.md
docs/implementation-roadmap.md
```

---

# Fase 1 — Shared Kernel

Implementare i value object riutilizzabili.

Package:

```text
shared
```

Classi:

```text
Money
Weight
Volume
Distance
Dimension
TemperatureRange
DateRange
TimeWindow
Percentage
Notes
```

Obiettivo:

- avere oggetti solidi per importi, pesi, dimensioni, volumi e finestre temporali;
- evitare primitive sparse nel codice.

---

# Fase 2 — Driver

Package:

```text
driver
```

Classi iniziali:

```text
Driver
DriverStatus
DriverLicense
LicenseCategory
ProfessionalQualification
ProfessionalQualificationType
TachographCard
```

Obiettivo:

- rappresentare autista;
- rappresentare patente;
- rappresentare CQC/ADR;
- iniziare controlli base.

---

# Fase 3 — Fleet

Package:

```text
fleet
```

Classi iniziali:

```text
Van
Truck
Trailer
VehicleCombination
VehicleLegalCategory
MotorVehicleType
TrailerType
BodyType
VehicleCombinationType
VehicleStatus
CapacityProfile
CargoSpace
TruckWeightProfile
TrailerWeightProfile
AxleConfiguration
CouplingCompatibility
FuelConsumption
```

Obiettivo:

- modellare mezzi e rimorchi;
- introdurre `VehicleCombination`;
- evitare relazione diretta `Shipment → Truck`.

---

# Fase 4 — Cargo

Package:

```text
cargo
```

Classi iniziali:

```text
Cargo
CargoItem
CargoType
PackagingType
CargoRequirement
CargoDimension
HazardousMaterialInfo
```

Obiettivo:

- rappresentare carichi;
- gestire peso, volume e dimensioni;
- gestire requisiti speciali.

---

# Fase 5 — Shipment

Package:

```text
shipment
```

Classi iniziali:

```text
Shipment
ShipmentStatus
ShipmentAssignment
ShipmentSchedule
PickupSiteRequirement
DeliverySiteRequirement
LoadingResponsibility
ShipmentCancellationReason
ProofRequiredPolicy
```

Obiettivo:

- creare il cuore operativo del sistema;
- gestire ciclo di vita spedizione;
- collegare autista, carico e VehicleCombination.

---

# Fase 6 — Compliance base

Package:

```text
compliance
```

Classi iniziali:

```text
ComplianceResult
ComplianceViolation
WeightComplianceCheck
VolumeComplianceCheck
DimensionComplianceCheck
DriverVehicleEligibilityCheck
CargoVehicleCompatibilityCheck
```

Obiettivo:

- verificare compatibilità base;
- iniziare test di business reali;
- impedire assegnazioni non valide.

---

# Fase 7 — Order e Pricing

Package:

```text
order
pricing
```

Obiettivo:

- creare flusso ordine → preventivo → spedizione.

---

# Fase 8 — Route, Operation e Planning

Package:

```text
route
operation
planning
```

Obiettivo:

- distinguere spedizione e missione;
- gestire tratte;
- pianificare soste e pause;
- preparare tracking futuro.

---

# Fase 9 — Document, Tracking e Maintenance

Package:

```text
document
tracking
maintenance
```

Obiettivo:

- documenti;
- prova di consegna;
- eventi;
- incidenti;
- manutenzioni;
- scadenze.

---

# Fase 10 — Identity, Audit e Security

Package:

```text
identity
audit
security
```

Obiettivo:

- account;
- ruoli;
- permessi;
- audit;
- base per login futuro.

Nota:

- il login reale con Spring Security arriverà più avanti;
- il dominio deve solo prevedere concetti e relazioni.

---

# Fase 11 — Spring Boot e API REST

Obiettivo:

- introdurre application layer;
- introdurre controller REST;
- introdurre DTO;
- collegare use case;
- mantenere il dominio indipendente.

---

# Fase 12 — Database

Obiettivo:

- PostgreSQL;
- repository;
- mapping;
- migrazioni;
- persistenza dati.

Nota:

- le entità di dominio non devono essere progettate intorno al database.

---

# Fase 13 — Frontend Web

Obiettivo:

- dashboard;
- gestione clienti;
- gestione ordini;
- gestione spedizioni;
- gestione flotta;
- gestione autisti;
- gestione documenti;
- vista tracking.

---

# Fase 14 — Integrazioni esterne

Possibili integrazioni:

```text
Google Maps
servizio geocoding
simulatore tracking
stima pedaggi
prezzo carburante
email
notifiche
storage documenti
```

Tutte queste integrazioni devono stare fuori dal dominio.

---

# Regola finale

Il progetto va costruito così:

```text
Documentazione
    ↓
Domain puro
    ↓
Test
    ↓
Application layer
    ↓
Spring Boot
    ↓
Database
    ↓
Frontend
    ↓
Integrazioni
```
