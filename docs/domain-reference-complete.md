# Domain reference completo

Elenco automatico dei package domain e delle classi presenti.

## `audit`

Audit trail, attori, azioni, severità e regole di tracciabilità.

- `AuditActionType`
- `AuditActorType`
- `AuditEvent`
- `AuditRules`
- `AuditSeverity`
- `AuditTrail`

## `availability`

Disponibilità di risorse come autisti, mezzi e strutture.

- `AvailabilityResourceType`
- `AvailabilityRules`
- `AvailabilityStatus`
- `ResourceAvailability`

## `billing`

Fatture cliente, pagamenti, stato fattura e regole di billing.

- `BillingRules`
- `Invoice`
- `InvoiceStatus`
- `PaymentMethod`
- `PaymentRecord`

## `cargo`

Merce, carichi, ADR, temperatura, rifiuti, alimentare, animali vivi e regole operative.

- `AdrClass`
- `CargoCategory`
- `CargoItem`
- `CargoLoad`
- `CargoLoadRules`
- `CargoOperationalRules`
- `DangerousGoodsProfile`
- `HazardLabel`
- `PackingGroup`

## `claim`

Danni, sinistri, claim di trasporto e ispezioni danni.

- `ClaimRules`
- `ClaimSeverity`
- `ClaimStatus`
- `ClaimType`
- `DamageInspection`
- `DamageInspectionItem`
- `TransportClaim`

## `company`

Azienda di trasporto, licenze aziendali e compliance aziendale.

- `CompanyComplianceRules`
- `CompanyLicense`
- `CompanyLicenseType`
- `TransportCompany`

## `compliance`

Regole trasversali di conformità.

- `ComplianceRules`

## `configuration`

Configurazioni del sistema e valori configurabili.

- `ConfigurationCategory`
- `ConfigurationRules`
- `ConfigurationScope`
- `ConfigurationValue`
- `ConfigurationValueType`
- `SystemConfiguration`

## `contract`

Contratti cliente, rate card, tariff rules e supplementi.

- `ChargeUnit`
- `ContractRateCard`
- `CustomerContract`
- `CustomerContractRules`
- `TariffRule`
- `TariffRuleType`

## `customer`

Clienti, account cliente e contatti logistici.

- `Customer`
- `CustomerAccount`
- `CustomerContact`
- `CustomerContactRole`
- `CustomerStatus`
- `CustomerType`

## `dataimport`

Import da fonti esterne: fuel card, pedaggi, telematica, paghe, fatture.

- `ExternalDataSourceType`
- `ImportBatch`
- `ImportRecord`
- `ImportRecordStatus`
- `ImportRules`

## `dispatch`

Ufficio traffico, candidati assegnazione e readiness check.

- `DispatchAssignmentCandidate`
- `DispatchCheckResult`
- `DispatchCheckType`
- `DispatchPlan`
- `DispatchReadinessStatus`
- `DispatchRules`

## `document`

Documenti di trasporto, bolla/DDT, bundle documentale spedizione.

- `DeliveryNote`
- `DeliveryNoteLine`
- `DocumentRules`
- `DocumentStatus`
- `ShipmentDocumentBundle`
- `TransportDocument`
- `TransportDocumentType`

## `driver`

Autisti, patenti, CQC, ADR, qualifiche operative e regole.

- `Driver`
- `DriverAdrCertificateType`
- `DriverCertificate`
- `DriverCertificateType`
- `DriverLicenseCategory`
- `DriverOperationalQualification`
- `DriverProfessionalQualification`
- `DriverRules`
- `DriverStatus`

## `drivetime`

Regole di guida, lavoro e riposo.

- `DriverTimeRules`

## `economics`

Acquisti, IVA, ricavi, costi, assicurazioni, finanziamenti, utile/perdita e debito.

- `CustomerRevenueInvoice`
- `EconomicsRules`
- `FinancialBalance`
- `FinancingAgreement`
- `FleetAssetAcquisition`
- `FleetAssetCategory`
- `FleetAssetCostComponent`
- `FleetAssetCostComponentType`
- `FleetAssetPurchase`
- `FleetEconomicLedger`
- `FleetFinancialStatement`
- `InsurancePolicy`
- `MissionCostLine`
- `MissionCostType`
- `MissionEconomics`
- `MissionRevenueLine`
- `MissionRevenueType`
- `ProfitabilityResult`
- `ProfitabilityStatus`
- `PurchaseCategory`
- `PurchaseLine`
- `RecurringExpense`
- `RecurringExpenseCategory`
- `SupplierInvoice`
- `SupplierInvoiceStatus`
- `TaxableRevenueLine`
- `VatBreakdown`
- `VatRate`
- `VatTreatment`

## `facility`

Depositi, magazzini, piazzali, proprietà/affitto e costi struttura.

- `Facility`
- `FacilityCostFrequency`
- `FacilityCostLine`
- `FacilityCostType`
- `FacilityFinancialProfile`
- `FacilityOwnershipType`
- `FacilityType`

## `fleet`

Veicoli, rimorchi, convogli, allestimenti, assi, certificati e schede tecniche.

- `AxleSteeringType`
- `BrakeSafetySystem`
- `BrakeType`
- `CouplingType`
- `DeadlineStatus`
- `KingpinDiameter`
- `RetarderType`
- `SuspensionType`
- `TireSpecification`
- `TransmissionType`
- `Vehicle`
- `VehicleAxle`
- `VehicleAxleSpecification`
- `VehicleBodyBaseType`
- `VehicleBodyCompatibilityRules`
- `VehicleBodyConfiguration`
- `VehicleBodyType`
- `VehicleCertificate`
- `VehicleCertificateType`
- `VehicleCombination`
- `VehicleCombinationLegalLimitProfile`
- `VehicleCombinationRules`
- `VehicleCombinationTechnicalRules`
- `VehicleCombinationType`
- `VehicleCouplingSpecification`
- `VehicleDimensionSpecification`
- `VehicleEquipmentPosition`
- `VehicleLoadingEquipment`
- `VehicleLoadingEquipmentType`
- `VehicleMassSpecification`
- `VehicleStatus`
- `VehicleTechnicalFeature`
- `VehicleTechnicalSpecification`
- `VehicleType`
- `VehicleUnitType`
- `VehicleWeightClass`
- `WheelConfiguration`

## `fuel`

Rifornimenti, fuel card e consumo carburante.

- `FuelCardProvider`
- `FuelConsumptionRules`
- `FuelTransaction`

## `identity`

Utenti, ruoli e permessi.

- `IdentityRules`
- `UserAccount`
- `UserAccountStatus`
- `UserPermission`
- `UserRole`

## `inventory`

Magazzino ricambi/materiali, giacenze, movimenti e riordino.

- `InventoryBalance`
- `InventoryItem`
- `InventoryItemType`
- `InventoryRules`
- `InventoryStockMovement`
- `StockMovementType`
- `WarehouseLocation`

## `loadsecurity`

Attrezzature e checklist di fissaggio carico.

- `LoadSecuringChecklist`
- `LoadSecuringEquipment`
- `LoadSecuringEquipmentType`
- `LoadSecuringRules`

## `location`

Indirizzi, coordinate e luoghi.

- `Address`
- `GeoCoordinates`
- `Location`

## `maintenance`

Manutenzione, ticket difetti autista e downtime.

- `DriverDefectTicket`
- `MaintenanceRules`
- `MaintenanceStatus`
- `MaintenanceType`
- `MaintenanceWorkOrder`
- `VehicleDowntime`

## `notification`

Messaggi e notifiche operative.

- `NotificationChannel`
- `NotificationMessage`
- `NotificationPriority`
- `NotificationRecipientType`
- `NotificationRules`
- `NotificationStatus`
- `NotificationType`

## `operation`

TransportMission: missione operativa reale.

- `TransportMission`
- `TransportMissionRules`
- `TransportMissionStatus`

## `order`

Ordine/richiesta commerciale cliente.

- `TransportOrder`
- `TransportOrderStatus`
- `TransportServiceType`

## `parking`

Posti parcheggio, risorse parcheggiate e convogli pronti.

- `ParkedResource`
- `ParkingAssignment`
- `ParkingResourceType`
- `ParkingRules`
- `ParkingSpot`
- `ParkingSpotStatus`
- `ParkingSpotType`

## `payroll`

Ore lavoro, premi, patenti, ADR, rimorchi e costo aziendale autista.

- `DriverMissionPayLine`
- `DriverMissionPayroll`
- `DriverMissionWorkReport`
- `DriverPayComponentType`
- `DriverPayRule`
- `DriverPayUnit`
- `DriverPayrollPolicy`
- `DriverPayrollRules`
- `MissionPayrollProjection`

## `pricing`

Preventivo/prezzo cliente e breakdown tariffario.

- `CostEstimationSource`
- `PriceBreakdown`
- `PricingLine`
- `PricingLineType`
- `PricingRules`
- `RouteCostEstimate`

## `reporting`

Report e metriche.

- `GeneratedReport`
- `ReportDefinition`
- `ReportFormat`
- `ReportMetric`
- `ReportMetricType`
- `ReportStatus`
- `ReportType`
- `ReportingRules`

## `route`

Route plan, stop e regole tratta.

- `RoutePlan`
- `RoutePlanRules`
- `RouteStop`
- `RouteStopType`

## `shared`

Value object comuni: Money, Weight, Distance, Notes, TimeWindow, ecc.

- `DateRange`
- `Dimension`
- `Distance`
- `Money`
- `Notes`
- `Percentage`
- `TemperatureRange`
- `TimeWindow`
- `Volume`
- `Weight`

## `shipment`

Spedizione nata da ordine accettato.

- `Shipment`
- `ShipmentRules`
- `ShipmentStatus`

## `sustainability`

Emissioni, fuel type e rating sostenibilità.

- `EmissionEstimate`
- `EmissionRating`
- `EmissionStandard`
- `FuelType`
- `SustainabilityRules`

## `telematics`

Snapshot telematici e comportamento di guida.

- `DrivingBehaviorEvent`
- `DrivingBehaviorEventType`
- `TelematicsRules`
- `TelematicsSnapshot`

## `tire`

Gomme fisiche, installazioni, rotazioni, posizioni ruota e regole.

- `Tire`
- `TireInstallation`
- `TireRotationEvent`
- `TireRules`
- `TireStatus`
- `WheelPosition`
- `WheelSide`
- `WheelSlot`

## `tracking`

Eventi tracking e timeline spedizione/missione.

- `TrackingEvent`
- `TrackingEventType`
- `TrackingRules`
- `TrackingTimeline`
