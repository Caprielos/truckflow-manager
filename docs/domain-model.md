# TruckFlow Manager — Domain Model

## Scopo

Questo documento descrive la struttura completa del dominio di TruckFlow Manager.

Il dominio è progettato per essere realistico, ma verrà implementato gradualmente.  
L’obiettivo non è scrivere subito tutte le classi, ma avere una mappa chiara per non dover cambiare direzione in futuro.

---

## Struttura completa

```text
domain
├── organization
├── customer
├── order
├── pricing
├── billing
├── driver
├── fleet
├── maintenance
├── cargo
├── route
├── shipment
├── operation
├── planning
├── tracking
├── document
├── regulation
├── compliance
├── facility
├── carrier
├── availability
├── notification
├── claim
├── audit
├── sustainability
├── security
├── identity
├── configuration
├── reporting
├── location
└── shared
```

---

## organization

```text
organization
├── Organization
├── Branch
├── Department
└── Employee
```

Gestisce l’azienda che usa il gestionale.

Serve per rappresentare:

- azienda proprietaria;
- sedi operative;
- filiali;
- reparti;
- dipendenti interni.

---

## customer

```text
customer
├── Customer
├── CustomerType
├── CustomerStatus
├── ContactInfo
├── CustomerContract
├── PaymentTerms
└── ServiceLevelAgreement
```

Gestisce clienti persone fisiche o aziende.

---

## order

```text
order
├── TransportOrder
├── TransportOrderStatus
├── TransportOrderPriority
└── ServiceType
```

Gestisce la richiesta iniziale del cliente.

---

## pricing

```text
pricing
├── TransportQuote
├── QuoteStatus
├── CostBreakdown
├── CostItem
├── CostType
├── RouteCostEstimate
├── TollCostEstimate
├── FuelCostEstimate
├── DriverCostEstimate
├── AdditionalCharge
└── Discount
```

Gestisce preventivi e costi stimati.

---

## billing

```text
billing
├── Invoice
├── InvoiceStatus
├── Payment
├── PaymentStatus
└── BillingDocument
```

Gestisce fatture e pagamenti futuri.  
È separato da `pricing`.

---

## driver

```text
driver
├── Driver
├── DriverStatus
├── DriverLicense
├── LicenseCategory
├── ProfessionalQualification
├── ProfessionalQualificationType
├── DriverQualification
├── DriverAvailability
├── DriverMedicalCheck
├── TachographCard
└── DriverWorkProfile
```

Gestisce autisti, patenti, CQC, ADR, carta tachigrafica e disponibilità.

---

## fleet

```text
fleet
├── Vehicle
├── MotorVehicle
├── Van
├── Truck
├── Trailer
├── VehicleCombination
├── VehicleLegalCategory
├── MotorVehicleType
├── TrailerType
├── BodyType
├── VehicleCombinationType
├── VehicleStatus
├── CapacityProfile
├── CargoSpace
├── TruckWeightProfile
├── TrailerWeightProfile
├── CombinedWeightProfile
├── AxleConfiguration
├── CouplingCompatibility
├── FuelConsumption
├── VehicleAssignment
├── OdometerReading
└── InsurancePolicy
```

Gestisce furgoni, camion, rimorchi, semirimorchi e combinazioni.

Regola fondamentale:

```text
Shipment → VehicleCombination
```

non:

```text
Shipment → Truck
```

---

## maintenance

```text
maintenance
├── MaintenanceRecord
├── MaintenanceType
├── MaintenanceSchedule
├── InspectionRecord
└── MaintenanceStatus
```

Gestisce manutenzioni, revisioni e controlli.

---

## cargo

```text
cargo
├── Cargo
├── CargoItem
├── CargoType
├── PackagingType
├── CargoRequirement
├── HandlingInstruction
├── HazardousMaterialInfo
├── LoadUnit
└── CargoDimension
```

Gestisce il carico, con peso, volume, dimensioni e requisiti.

---

## route

```text
route
├── Route
├── RouteLeg
├── RouteStop
├── StopType
├── DistanceEstimate
└── TravelTimeEstimate
```

Gestisce percorsi, tratte e fermate.

---

## shipment

```text
shipment
├── Shipment
├── ShipmentStatus
├── ShipmentAssignment
├── ShipmentSchedule
├── PickupSiteRequirement
├── DeliverySiteRequirement
├── LoadingResponsibility
├── ShipmentCancellationReason
└── ProofRequiredPolicy
```

Gestisce la spedizione richiesta dal cliente.

---

## operation

```text
operation
├── TransportMission
├── MissionStatus
├── MissionStop
├── MissionStopType
├── LoadPlan
└── LoadPlanItem
```

Gestisce il viaggio operativo reale del mezzo.

Una missione può contenere più spedizioni.

---

## planning

```text
planning
├── TripPlan
├── DrivingTimeRule
├── DrivingSession
├── DrivingBreak
├── BreakType
├── DailyRest
├── WeeklyRest
├── DriverDutyPeriod
├── TachographRecord
├── BreakPlan
├── RestStop
├── StopRecommendation
├── VehiclePosition
├── RouteProgress
└── EstimatedArrival
```

Gestisce pianificazione viaggio, pause, riposi e avanzamento.

---

## tracking

```text
tracking
├── ShipmentEvent
├── ShipmentEventType
├── IncidentReport
├── IncidentType
├── DelayReport
└── DelayReason
```

Gestisce eventi, ritardi e incidenti.

---

## document

```text
document
├── TransportDocument
├── DocumentType
├── ProofOfDelivery
├── Signature
├── AttachmentReference
└── DamageReport
```

Gestisce documenti e prova di consegna.

---

## regulation

```text
regulation
├── RegulatoryProfile
├── CountryRegulation
├── RoadRestriction
├── DrivingBanCalendar
├── DrivingBanPeriod
├── DrivingBanException
├── TransportPermit
├── PermitType
├── RoadAccessRestriction
├── LowEmissionZoneRule
├── TunnelRestriction
├── BorderCrossingRule
├── Rule
├── RuleSet
├── RuleSeverity
└── RuleApplicability
```

Gestisce normative, divieti e permessi.

Le normative precise non devono essere hardcodate.  
Quando si implementeranno regole reali, dovranno essere verificate su fonti ufficiali aggiornate.

---

## compliance

```text
compliance
├── ComplianceCheck
├── ComplianceResult
├── ComplianceViolation
├── DriverVehicleEligibilityCheck
├── DriverCargoEligibilityCheck
├── LicenseComplianceCheck
├── CqcComplianceCheck
├── AdrComplianceCheck
├── AtpComplianceCheck
├── DrivingTimeComplianceCheck
├── RoadRestrictionComplianceCheck
├── WeightComplianceCheck
├── VolumeComplianceCheck
├── DimensionComplianceCheck
├── LoadCompatibilityCheck
├── VehicleCombinationCompatibilityCheck
├── CargoVehicleCompatibilityCheck
├── DocumentComplianceCheck
└── PermitComplianceCheck
```

Gestisce controlli di compatibilità e conformità.

---

## facility

```text
facility
├── Depot
├── Yard
├── Warehouse
├── ParkingSlot
└── FacilityStatus
```

Gestisce depositi, piazzali, magazzini e parcheggi.

---

## carrier

```text
carrier
├── Carrier
├── CarrierType
├── CarrierContract
├── SubcontractedMission
└── CarrierRating
```

Gestisce vettori esterni e subappalti.

---

## availability

```text
availability
├── AvailabilityCalendar
├── AvailabilitySlot
├── UnavailabilityReason
└── ResourceType
```

Gestisce disponibilità di autisti, veicoli, rimorchi, depositi e vettori.

---

## notification

```text
notification
├── Alert
├── AlertType
├── AlertSeverity
└── AlertStatus
```

Gestisce alert concettuali.  
L’invio reale delle notifiche sarà fuori dal dominio.

---

## claim

```text
claim
├── Claim
├── ClaimType
├── ClaimStatus
└── ClaimResolution
```

Gestisce reclami.

---

## audit

```text
audit
├── AuditLog
├── UserAction
├── Actor
└── ActionType
```

Gestisce storico azioni.

---

## sustainability

```text
sustainability
├── EmissionEstimate
├── FuelUsageEstimate
└── EmissionStandard
```

Gestisce emissioni e consumi stimati.

---

## security

```text
security
├── SecurityRequirement
├── SecurityLevel
├── SecureParkingRequirement
├── Seal
├── AccessPolicy
└── SecurityEvent
```

Gestisce sicurezza del carico e policy applicative concettuali.

---

## identity

```text
identity
├── UserAccount
├── UserRole
├── Permission
├── UserStatus
├── LoginCredential
├── PasswordResetToken
└── AccountLink
```

Gestisce utenti, account, ruoli e permessi.

---

## configuration

```text
configuration
├── CompanySetting
├── RuleConfiguration
├── CatalogItem
└── SystemParameter
```

Gestisce configurazioni e cataloghi.

---

## reporting

```text
reporting
├── ShipmentReport
├── FleetReport
├── DriverReport
├── CostReport
└── PerformanceMetric
```

Gestisce report e metriche future.

---

## location

```text
location
├── Address
├── Coordinates
├── Facility
├── FacilityType
└── LocationContact
```

Gestisce indirizzi, coordinate e luoghi operativi.

---

## shared

```text
shared
├── Money
├── Weight
├── Volume
├── Distance
├── Dimension
├── TemperatureRange
├── DateRange
├── TimeWindow
├── Percentage
└── Notes
```

Contiene value object riutilizzabili.
