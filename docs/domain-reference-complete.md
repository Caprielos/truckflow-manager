# Domain reference complete

Catalogo completo dei package e delle classi principali rilevate dal progetto.

## `audit` — Tracciamento audit

Registra azioni importanti, attori, severità e trail degli eventi. Serve per sapere chi ha fatto cosa e con quale impatto.

Classi:

- `AuditActionType`
- `AuditActorType`
- `AuditEvent`
- `AuditRules`
- `AuditSeverity`
- `AuditTrail`

Test:
- `AuditEventTest`
- `AuditRulesTest`
- `AuditTrailTest`

## `availability` — Disponibilità risorse

Gestisce disponibilità e indisponibilità di autisti, veicoli, rimorchi, strutture o altre risorse operative.

Classi:

- `AvailabilityResourceType`
- `AvailabilityRules`
- `AvailabilityStatus`
- `ResourceAvailability`

Test:
- `AvailabilityRulesTest`
- `ResourceAvailabilityTest`

## `billing` — Fatturazione cliente

Rappresenta fatture emesse, stato fattura, pagamenti e regole base di incasso.

Classi:

- `BillingRules`
- `Invoice`
- `InvoiceStatus`
- `PaymentMethod`
- `PaymentRecord`

Test:
- `BillingRulesTest`
- `InvoiceTest`
- `PaymentRecordTest`

## `cargo` — Merce e requisiti di carico

Descrive il carico, categorie merce, ADR, temperatura, rifiuti, animali vivi, alimentare e regole operative richieste dalla merce.

Classi:

- `AdrClass`
- `CargoCategory`
- `CargoItem`
- `CargoLoad`
- `CargoLoadRules`
- `CargoOperationalRules`
- `DangerousGoodsProfile`
- `HazardLabel`
- `PackingGroup`

Test:
- `CargoItemTest`
- `CargoLoadRulesTest`
- `CargoLoadTest`
- `CargoOperationalRulesTest`
- `DangerousCargoTest`
- `DangerousGoodsProfileTest`

## `claim` — Danni, sinistri e reclami

Gestisce danni, ispezioni, reclami cliente, severità e stato delle pratiche.

Classi:

- `ClaimRules`
- `ClaimSeverity`
- `ClaimStatus`
- `ClaimType`
- `DamageInspection`
- `DamageInspectionItem`
- `TransportClaim`

Test:
- `ClaimRulesTest`
- `TransportClaimTest`

## `company` — Azienda e licenze operative

Modella l’impresa di trasporto, le licenze aziendali e le verifiche di conformità aziendale.

Classi:

- `CompanyComplianceRules`
- `CompanyLicense`
- `CompanyLicenseType`
- `TransportCompany`

## `compliance` — Conformità generale

Contiene regole trasversali per verificare requisiti di spedizione, autista, mezzo, cargo, documenti e missione.

Classi:

- `ComplianceRules`

Test:
- `ComplianceRulesTest`

## `configuration` — Configurazioni dominio

Permette di rappresentare valori configurabili per regole aziendali, soglie, parametri e impostazioni.

Classi:

- `ConfigurationCategory`
- `ConfigurationRules`
- `ConfigurationScope`
- `ConfigurationValue`
- `ConfigurationValueType`
- `SystemConfiguration`

Test:
- `ConfigurationRulesTest`

## `contract` — Contratti cliente e listini

Gestisce contratti, rate card, tariff rules, supplementi e logiche tariffarie realistiche.

Classi:

- `ChargeUnit`
- `ContractRateCard`
- `CustomerContract`
- `CustomerContractRules`
- `TariffRule`
- `TariffRuleType`

Test:
- `CustomerContractModelTest`

## `customer` — Clienti e contatti

Gestisce cliente, account cliente, contatti, ruoli di contatto, stato e tipologia cliente.

Classi:

- `Customer`
- `CustomerAccount`
- `CustomerContact`
- `CustomerContactRole`
- `CustomerStatus`
- `CustomerType`

Test:
- `CustomerAccountTest`
- `CustomerContactTest`
- `CustomerTest`

## `dataimport` — Import dati esterni

Rappresenta batch e record importati da carburante, pedaggi, telematica, banca, paghe o fatture fornitore.

Classi:

- `ExternalDataSourceType`
- `ImportBatch`
- `ImportRecord`
- `ImportRecordStatus`
- `ImportRules`

Test:
- `DataImportModelTest`

## `dispatch` — Ufficio traffico / pianificazione

Valuta candidati di assegnazione autista/mezzo/convoglio, readiness e scelta del candidato migliore.

Classi:

- `DispatchAssignmentCandidate`
- `DispatchCheckResult`
- `DispatchCheckType`
- `DispatchPlan`
- `DispatchReadinessStatus`
- `DispatchRules`

Test:
- `DispatchPlanningTest`

## `document` — Documenti trasporto

Gestisce documenti richiesti, bolla/DDT strutturata, bundle documentale spedizione e stati documentali.

Classi:

- `DeliveryNote`
- `DeliveryNoteLine`
- `DocumentRules`
- `DocumentStatus`
- `ShipmentDocumentBundle`
- `TransportDocument`
- `TransportDocumentType`

Test:
- `DeliveryNoteAndDocumentBundleTest`
- `DocumentRulesTest`

## `driver` — Autisti e abilitazioni

Modella autisti, patenti, CQC, ADR, qualifiche operative, certificati con validità e stato autista.

Classi:

- `Driver`
- `DriverAdrCertificateType`
- `DriverCertificate`
- `DriverCertificateType`
- `DriverLicenseCategory`
- `DriverOperationalQualification`
- `DriverProfessionalQualification`
- `DriverRules`
- `DriverStatus`

Test:
- `DriverRulesTest`
- `DriverTest`

## `drivetime` — Ore guida e riposo

Contiene regole per limiti di guida, pausa, riposo e compatibilità temporale del lavoro autista.

Classi:

- `DriverTimeRules`

Test:
- `DriverTimeRulesTest`

## `economics` — Economia, costi, IVA e marginalità

Centro economico: acquisti asset, IVA, fatture fornitore, costi missione, ricavi, ledger, utile/perdita e debito/cassa negativa.

Classi:

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

Test:
- `FleetAssetAcquisitionTest`
- `FleetAssetPurchaseTest`
- `FleetEconomicLedgerTest`
- `FleetFinancialStatementTest`
- `MissionEconomicsTest`
- `SupplierInvoiceTest`
- `TaxedSupplierInvoiceTest`
- `VatBreakdownTest`

## `facility` — Strutture aziendali e costi immobiliari

Gestisce depositi, piazzali, magazzini, proprietà/affitto/leasing e spese di struttura.

Classi:

- `Facility`
- `FacilityCostFrequency`
- `FacilityCostLine`
- `FacilityCostType`
- `FacilityFinancialProfile`
- `FacilityOwnershipType`
- `FacilityType`

Test:
- `FacilityFinancialProfileTest`
- `FacilityTest`

## `fleet` — Flotta e mezzi

Modella veicoli, unità, allestimenti, assi, masse, dimensioni, certificati, combinazioni e compatibilità tecnica.

Classi:

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

Test:
- `RealisticFleetModelTest`
- `TireSpecificationTest`
- `VehicleCombinationRulesTest`
- `VehicleCombinationTest`
- `VehicleTest`

## `fuel` — Carburante e consumi

Gestisce rifornimenti, provider carte carburante e regole di consumo.

Classi:

- `FuelCardProvider`
- `FuelConsumptionRules`
- `FuelTransaction`

Test:
- `FuelTransactionTest`

## `identity` — Utenti, ruoli e permessi

Gestisce account utente, ruoli e permessi per distinguere accesso operativo, amministrativo ed economico.

Classi:

- `IdentityRules`
- `UserAccount`
- `UserAccountStatus`
- `UserPermission`
- `UserRole`

Test:
- `ExpandedIdentityPermissionsTest`
- `IdentityRulesTest`
- `UserAccountTest`

## `inventory` — Magazzino ricambi e materiali

Gestisce articoli, giacenze, ubicazioni, movimenti stock, scorte minime e reorder signal.

Classi:

- `InventoryBalance`
- `InventoryItem`
- `InventoryItemType`
- `InventoryRules`
- `InventoryStockMovement`
- `StockMovementType`
- `WarehouseLocation`

Test:
- `InventoryManagementTest`

## `loadsecurity` — Fissaggio carico

Gestisce attrezzature e checklist per fissaggio carico, cinghie, barre, tappeti antiscivolo e controlli.

Classi:

- `LoadSecuringChecklist`
- `LoadSecuringEquipment`
- `LoadSecuringEquipmentType`
- `LoadSecuringRules`

Test:
- `LoadSecuringChecklistTest`

## `location` — Luoghi e coordinate

Rappresenta indirizzi, coordinate geografiche e location operative.

Classi:

- `Address`
- `GeoCoordinates`
- `Location`

Test:
- `AddressTest`
- `GeoCoordinatesTest`
- `LocationTest`

## `maintenance` — Manutenzione e fermi mezzo

Gestisce work order, difetti segnalati dall’autista, fermi mezzo, stati e tipi manutenzione.

Classi:

- `DriverDefectTicket`
- `MaintenanceRules`
- `MaintenanceStatus`
- `MaintenanceType`
- `MaintenanceWorkOrder`
- `VehicleDowntime`

Test:
- `MaintenanceRulesTest`
- `MaintenanceWorkOrderTest`

## `notification` — Notifiche

Modella messaggi, canali, priorità, destinatari e stato notifica.

Classi:

- `NotificationChannel`
- `NotificationMessage`
- `NotificationPriority`
- `NotificationRecipientType`
- `NotificationRules`
- `NotificationStatus`
- `NotificationType`

Test:
- `NotificationMessageTest`
- `NotificationRulesTest`

## `operation` — Missione operativa

Rappresenta il viaggio reale: missione, stato, regole operative, assegnazioni e chiusura.

Classi:

- `TransportMission`
- `TransportMissionRules`
- `TransportMissionStatus`

Test:
- `TransportMissionRulesTest`
- `TransportMissionTest`

## `order` — Ordini di trasporto

Rappresenta richiesta commerciale cliente prima che diventi spedizione pianificata.

Classi:

- `TransportOrder`
- `TransportOrderStatus`
- `TransportServiceType`

Test:
- `TransportOrderTest`

## `parking` — Parcheggi e posti numerati

Gestisce posti, risorse parcheggiate, furgoni, rimorchi, trattori, convogli agganciati e readiness.

Classi:

- `ParkedResource`
- `ParkingAssignment`
- `ParkingResourceType`
- `ParkingRules`
- `ParkingSpot`
- `ParkingSpotStatus`
- `ParkingSpotType`

Test:
- `ParkingAssignmentTest`

## `payroll` — Costo autista e stipendio missione

Calcola voci paga in base a ore, patenti, ADR, rimorchio, trasporto speciale, straordinari, trasferte e costo aziendale.

Classi:

- `DriverMissionPayLine`
- `DriverMissionPayroll`
- `DriverMissionWorkReport`
- `DriverPayComponentType`
- `DriverPayRule`
- `DriverPayUnit`
- `DriverPayrollPolicy`
- `DriverPayrollRules`
- `MissionPayrollProjection`

Test:
- `DriverPayrollRulesTest`

## `pricing` — Preventivi e prezzo cliente

Gestisce prezzo da proporre/fatturare al cliente, voci prezzo e breakdown commerciale.

Classi:

- `CostEstimationSource`
- `PriceBreakdown`
- `PricingLine`
- `PricingLineType`
- `PricingRules`
- `RouteCostEstimate`

Test:
- `PriceBreakdownTest`
- `PricingLineTest`
- `PricingRulesTest`
- `RouteCostEstimateTest`

## `reporting` — Reportistica domain

Modella definizioni report, metriche, formato, stato e regole di generazione logica.

Classi:

- `GeneratedReport`
- `ReportDefinition`
- `ReportFormat`
- `ReportMetric`
- `ReportMetricType`
- `ReportStatus`
- `ReportType`
- `ReportingRules`

Test:
- `GeneratedReportTest`
- `ReportingRulesTest`

## `route` — Percorsi e soste

Gestisce route plan, stop, carico/scarico, sequenza e regole di coerenza della rotta.

Classi:

- `RoutePlan`
- `RoutePlanRules`
- `RouteStop`
- `RouteStopType`

Test:
- `RoutePlanRulesTest`
- `RoutePlanTest`
- `RouteStopTest`

## `shared` — Value object condivisi

Contiene Money, Weight, Distance, Dimension, Volume, TemperatureRange, TimeWindow, DateRange, Notes e Percentage.

Classi:

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

Test:
- `DateRangeTest`
- `DimensionTest`
- `DistanceTest`
- `MoneyTest`
- `NotesTest`
- `PercentageTest`
- `TemperatureRangeTest`
- `TimeWindowTest`
- `VolumeTest`
- `WeightTest`

## `shipment` — Spedizione

Rappresenta la spedizione nata da un ordine accettato, con stato e regole; non contiene direttamente driver e mezzo.

Classi:

- `Shipment`
- `ShipmentRules`
- `ShipmentStatus`

Test:
- `ShipmentRulesTest`
- `ShipmentTest`

## `sustainability` — Emissioni e sostenibilità

Modella stime emissioni, standard, rating e regole di sostenibilità.

Classi:

- `EmissionEstimate`
- `EmissionRating`
- `EmissionStandard`
- `FuelType`
- `SustainabilityRules`

Test:
- `EmissionEstimateTest`
- `SustainabilityRulesTest`

## `telematics` — Telematica e comportamento guida

Gestisce snapshot GPS/CAN-bus e eventi comportamento guida come frenate, accelerazioni, consumo e odometro.

Classi:

- `DrivingBehaviorEvent`
- `DrivingBehaviorEventType`
- `TelematicsRules`
- `TelematicsSnapshot`

Test:
- `TelematicsSnapshotTest`

## `tire` — Pneumatici

Gestisce gomma fisica, installazioni, rotazioni, stato, posizioni ruota e regole usura/sicurezza.

Classi:

- `Tire`
- `TireInstallation`
- `TireRotationEvent`
- `TireRules`
- `TireStatus`
- `WheelPosition`
- `WheelSide`
- `WheelSlot`

Test:
- `TireManagementTest`

## `tracking` — Tracking spedizione/missione

Gestisce eventi tracking e timeline di avanzamento operativo.

Classi:

- `TrackingEvent`
- `TrackingEventType`
- `TrackingRules`
- `TrackingTimeline`

Test:
- `TrackingEventTest`
- `TrackingRulesTest`
- `TrackingTimelineTest`
