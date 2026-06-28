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


# Domain Model

## Scopo

Questo documento descrive la struttura attuale del domain implementato.

## Package tree

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


# Package opzionali futuri

Questi package erano nella visione iniziale ma non fanno parte del domain MVP attuale.

## organization

Per modellare l'azienda che usa il gestionale: legal name, VAT number, sedi, reparti, dipendenti.

## carrier

Per modellare vettori esterni/subcontractor.

## planning avanzato

Per algoritmi di suggerimento: candidate driver, candidate vehicle, planning score, optimization.

## regulation avanzato

Per normative configurabili reali: divieti, permessi, ZTL, LEZ, gallerie ADR. Da implementare solo con fonti ufficiali aggiornate.

## security avanzato

La parte di accesso reale, password, token, JWT e Spring Security non deve entrare nel domain. Starà in infrastructure/application.
