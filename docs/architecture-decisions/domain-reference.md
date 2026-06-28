# TruckFlow Manager

> Documentazione aggiornata e allineata al domain MVP implementato.

Package root del domain:

```text
it.gabriele.truckflow.domain
```

Regola principale:

```text
Il domain rappresenta il business.
La tecnologia serve solo a farlo funzionare.
```


# Domain Reference Completa

## 1. Obiettivo

TruckFlow Manager è un gestionale per trasporti e logistica.

Il domain model rappresenta il cuore del sistema:

- clienti;
- ordini;
- spedizioni;
- missioni;
- route;
- autisti;
- flotta;
- carichi;
- ADR;
- documenti;
- pricing;
- billing;
- claim;
- audit;
- notification;
- sustainability;
- identity;
- configuration;
- reporting.

## 2. Regola principale

Il domain deve restare Java puro.

Nel domain NON devono esserci:

```text
Spring
JPA
REST controller
database
repository tecnici
email reali
HTTP client
filesystem
Google Maps
ViaMichelin
HERE
PTV
JWT
password hash reali
frontend
DTO JSON
```

## 3. Architettura

```text
Frontend/Web
    ↓
REST API
    ↓
Application Layer
    ↓
Domain
    ↓
Infrastructure
```

## 4. Concetti chiave

### TransportOrder

Richiesta commerciale del cliente.

### Shipment

Spedizione nata da ordine accettato.

### TransportMission

Viaggio operativo reale.

### VehicleCombination

Unità assegnabile alla spedizione/missione.

### Driver

Autista operativo.

### UserAccount

Account applicativo. Separato da Driver e Customer.

## 5. Package attuali

```text
domain
├── shared
├── cargo
├── location
├── facility
├── customer
├── order
├── shipment
├── route
├── fleet
├── driver
├── compliance
├── operation
├── availability
├── tracking
├── maintenance
├── pricing
├── billing
├── document
├── claim
├── audit
├── notification
├── sustainability
├── identity
├── configuration
├── reporting
```

## shared

Value Object condivisi.

```text
shared
├── Weight
├── Distance
├── Volume
├── Dimension
├── Money
├── Percentage
├── TemperatureRange
├── TimeWindow
├── DateRange
├── Notes
```

## cargo

Carichi, merci e ADR.

```text
cargo
├── CargoCategory
├── CargoItem
├── CargoLoad
├── CargoLoadRules
├── AdrClass
├── PackingGroup
├── HazardLabel
├── DangerousGoodsProfile
```

## location

Indirizzi, coordinate e luoghi.

```text
location
├── GeoCoordinates
├── Address
├── Location
```

## facility

Punti operativi.

```text
facility
├── FacilityType
├── Facility
```

## customer

Clienti e contatti.

```text
customer
├── CustomerType
├── CustomerStatus
├── Customer
├── CustomerContactRole
├── CustomerContact
├── CustomerAccount
```

## order

Richieste commerciali.

```text
order
├── TransportOrderStatus
├── TransportServiceType
├── TransportOrder
```

## shipment

Spedizioni.

```text
shipment
├── ShipmentStatus
├── Shipment
├── ShipmentRules
```

## route

Piani percorso.

```text
route
├── RouteStopType
├── RouteStop
├── RoutePlan
├── RoutePlanRules
```

## fleet

Flotta e combinazioni.

```text
fleet
├── VehicleType
├── VehicleStatus
├── VehicleBodyType
├── TireSpecification
├── Vehicle
├── VehicleCombination
├── VehicleCombinationRules
├── VehicleBodyCompatibilityRules
```

## driver

Autisti, patenti e qualifiche.

```text
driver
├── DriverLicenseCategory
├── DriverProfessionalQualification
├── DriverAdrCertificateType
├── DriverOperationalQualification
├── DriverStatus
├── Driver
├── DriverRules
```

## compliance

Conformità trasversale.

```text
compliance
├── ComplianceRules
```

## operation

Missioni operative.

```text
operation
├── TransportMissionStatus
├── TransportMission
├── TransportMissionRules
```

## availability

Disponibilità risorse.

```text
availability
├── AvailabilityResourceType
├── AvailabilityStatus
├── ResourceAvailability
├── AvailabilityRules
```

## tracking

Eventi e tracking.

```text
tracking
├── TrackingEventType
├── TrackingEvent
├── TrackingTimeline
├── TrackingRules
```

## maintenance

Manutenzione.

```text
maintenance
├── MaintenanceType
├── MaintenanceStatus
├── MaintenanceWorkOrder
├── MaintenanceRules
```

## pricing

Prezzi e stime costo.

```text
pricing
├── CostEstimationSource
├── PricingLineType
├── RouteCostEstimate
├── PricingLine
├── PriceBreakdown
├── PricingRules
```

## billing

Fatture e pagamenti.

```text
billing
├── InvoiceStatus
├── PaymentMethod
├── Invoice
├── PaymentRecord
├── BillingRules
```

## document

Documenti trasporto.

```text
document
├── TransportDocumentType
├── DocumentStatus
├── TransportDocument
├── DocumentRules
```

## claim

Reclami.

```text
claim
├── ClaimType
├── ClaimSeverity
├── ClaimStatus
├── TransportClaim
├── ClaimRules
```

## audit

Audit trail.

```text
audit
├── AuditActorType
├── AuditSeverity
├── AuditActionType
├── AuditEvent
├── AuditTrail
├── AuditRules
```

## notification

Notifiche.

```text
notification
├── NotificationType
├── NotificationChannel
├── NotificationRecipientType
├── NotificationPriority
├── NotificationStatus
├── NotificationMessage
├── NotificationRules
```

## sustainability

Emissioni e sostenibilità.

```text
sustainability
├── FuelType
├── EmissionStandard
├── EmissionRating
├── EmissionEstimate
├── SustainabilityRules
```

## identity

Account, ruoli e permessi.

```text
identity
├── UserAccountStatus
├── UserRole
├── UserPermission
├── UserAccount
├── IdentityRules
```

## configuration

Configurazioni applicative.

```text
configuration
├── ConfigurationCategory
├── ConfigurationScope
├── ConfigurationValueType
├── ConfigurationValue
├── SystemConfiguration
├── ConfigurationRules
```

## reporting

Report e metriche.

```text
reporting
├── ReportType
├── ReportFormat
├── ReportStatus
├── ReportMetricType
├── ReportMetric
├── ReportDefinition
├── GeneratedReport
├── ReportingRules
```


# 6. Flussi principali

## Commerciale

```text
CustomerAccount
    ↓
TransportOrder
    ↓ accepted
Shipment
```

## Operativo

```text
Shipment
    ↓
RoutePlan
    ↓
Driver + VehicleCombination
    ↓
TransportMission
    ↓
TrackingTimeline
```

## Economico

```text
RouteCostEstimate
    ↓
PricingLine
    ↓
PriceBreakdown
    ↓
Invoice
    ↓
PaymentRecord
```

## Documentale

```text
TransportDocument DRAFT
    ↓ REQUESTED
    ↓ RECEIVED
    ↓ VERIFIED
```

## Claim

```text
OPEN → UNDER_REVIEW → ACCEPTED → SETTLED
OPEN/UNDER_REVIEW → REJECTED
OPEN/UNDER_REVIEW/ACCEPTED → CANCELLED
```

## Audit

```text
AuditEvent
    ↓
AuditTrail
```

## Reporting

```text
ReportDefinition
    ↓
GeneratedReport DRAFT
    ↓ generate(metrics)
GENERATED
    ↓ publish
PUBLISHED
    ↓ archive
ARCHIVED
```

# 7. Scelte importanti

## Shipment usa VehicleCombination

Corretto:

```text
Shipment → VehicleCombination
```

Non corretto:

```text
Shipment → Truck
```

## Pricing separato da Billing

```text
pricing = preventivo / stima / breakdown
billing = fattura / pagamento
```

## Domain non chiama provider esterni

`RouteCostEstimate` può indicare `VIAMICHELIN` come source, ma il client ViaMichelin sarà un adapter infrastructure futuro.

## Identity separata dal mondo operativo

`UserAccount`, `Driver`, `Customer` e `CustomerContact` restano concetti separati.

# 8. Prossimi step

1. Eseguire sempre `mvn clean test`.
2. Committare documentazione.
3. Iniziare application layer.
4. Creare use case.
5. Creare port repository.
6. Solo dopo introdurre infrastructure e web/API.
