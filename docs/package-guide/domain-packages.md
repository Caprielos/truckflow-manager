# Package domain

Questa pagina elenca i package sotto `domain` e il loro scopo.

## `domain/audit`

Traccia modifiche e azioni importanti: chi ha fatto cosa, quando e con che gravità.

Classi principali:

- `AuditActionType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `AuditActorType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `AuditEvent` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `AuditRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `AuditSeverity` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `AuditTrail` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/availability`

Disponibilità di risorse: veicoli, driver, rimorchi, strutture o altre risorse operative.

Classi principali:

- `AvailabilityResourceType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `AvailabilityRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `AvailabilityStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ResourceAvailability` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/billing`

Fatture cliente, pagamenti e stato incassi.

Classi principali:

- `BillingRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `Invoice` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `InvoiceStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PaymentMethod` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PaymentRecord` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/cargo`

Merce trasportata: categoria, peso, volume, temperatura, ADR e regole operative cargo.

Classi principali:

- `AdrClass` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `CargoCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `CargoItem` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CargoLoad` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CargoLoadRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `CargoOperationalRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `DangerousGoodsProfile` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `HazardLabel` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PackingGroup` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/claim`

Danni, reclami, incidenti e ispezioni danni.

Classi principali:

- `ClaimRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `ClaimSeverity` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ClaimStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ClaimType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DamageInspection` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DamageInspectionItem` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TransportClaim` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/company`

Azienda di trasporto, licenze aziendali e autorizzazioni operative.

Classi principali:

- `CompanyComplianceRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `CompanyLicense` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CompanyLicenseType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TransportCompany` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/compliance`

Regole generali di conformità tra cargo, driver, veicolo e documenti.

Classi principali:

- `ComplianceRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/configuration`

Configurazioni di sistema, parametri modificabili e valori di configurazione.

Classi principali:

- `ConfigurationCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ConfigurationRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `ConfigurationScope` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ConfigurationValue` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ConfigurationValueType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `SystemConfiguration` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/contract`

Contratti cliente, listini, tariffe e regole prezzo commerciali.

Classi principali:

- `ChargeUnit` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ContractRateCard` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CustomerContract` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CustomerContractRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `TariffRule` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TariffRuleType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/customer`

Clienti, account cliente e contatti operativi/amministrativi.

Classi principali:

- `Customer` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CustomerAccount` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CustomerContact` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `CustomerContactRole` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `CustomerStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `CustomerType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/dataimport`

Import da fonti esterne: carte carburante, pedaggi, telematica, banca, paghe e fatture.

Classi principali:

- `ExternalDataSourceType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ImportBatch` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ImportRecord` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ImportRecordStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ImportRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/dispatch`

Ufficio traffico: candidati di assegnazione, controlli readiness e piani di dispatch.

Classi principali:

- `DispatchAssignmentCandidate` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DispatchCheckResult` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DispatchCheckType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DispatchPlan` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DispatchReadinessStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DispatchRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/document`

Documenti di trasporto: bolla/DDT, CMR, POD, fascicoli documentali.

Classi principali:

- `DeliveryNote` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DeliveryNoteLine` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DocumentRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `DocumentStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ShipmentDocumentBundle` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TransportDocument` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TransportDocumentType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/driver`

Autisti, patenti, certificati, qualifiche operative e regole abilitative.

Classi principali:

- `Driver` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverAdrCertificateType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverCertificate` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverCertificateType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverLicenseCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverOperationalQualification` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverProfessionalQualification` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `DriverStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/drivetime`

Regole ore guida, riposo e limiti operativi.

Classi principali:

- `DriverTimeRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/economics`

Costi, ricavi, IVA, acquisti flotta, fatture fornitori, utile/perdita e cassa/debito.

Classi principali:

- `CustomerRevenueInvoice` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `EconomicsRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `FinancialBalance` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `FinancingAgreement` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `FleetAssetAcquisition` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `FleetAssetCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FleetAssetCostComponent` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `FleetAssetCostComponentType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FleetAssetPurchase` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `FleetEconomicLedger` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `FleetFinancialStatement` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `InsurancePolicy` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `MissionCostLine` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `MissionCostType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `MissionEconomics` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `MissionRevenueLine` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `MissionRevenueType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ProfitabilityResult` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ProfitabilityStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PurchaseCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PurchaseLine` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `RecurringExpense` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `RecurringExpenseCategory` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `SupplierInvoice` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `SupplierInvoiceStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TaxableRevenueLine` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `VatBreakdown` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `VatRate` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `VatTreatment` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/facility`

Strutture aziendali: deposito, sede, magazzino, piazzale, proprietà/affitto e spese.

Classi principali:

- `Facility` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `FacilityCostFrequency` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FacilityCostLine` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `FacilityCostType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FacilityFinancialProfile` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.
- `FacilityOwnershipType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FacilityType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/fleet`

Mezzi, rimorchi, convogli, schede tecniche, assi, allestimenti e certificati veicolo.

Classi principali:

- `AxleSteeringType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `BrakeSafetySystem` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `BrakeType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `CouplingType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DeadlineStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `KingpinDiameter` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `RetarderType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `SuspensionType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TireSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `TransmissionType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `Vehicle` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleAxle` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleAxleSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleBodyBaseType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleBodyCompatibilityRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `VehicleBodyConfiguration` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleBodyType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleCertificate` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleCertificateType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleCombination` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleCombinationLegalLimitProfile` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleCombinationRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `VehicleCombinationTechnicalRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `VehicleCombinationType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleCouplingSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleDimensionSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleEquipmentPosition` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleLoadingEquipment` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleLoadingEquipmentType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleMassSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleTechnicalFeature` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleTechnicalSpecification` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `VehicleType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleUnitType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `VehicleWeightClass` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `WheelConfiguration` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/fuel`

Rifornimenti carburante, carta carburante e transazioni fuel.

Classi principali:

- `FuelCardProvider` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FuelConsumptionRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `FuelTransaction` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/identity`

Account utente, ruoli e permessi applicativi.

Classi principali:

- `IdentityRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `UserAccount` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `UserAccountStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `UserPermission` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `UserRole` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/inventory`

Magazzino: ricambi, DPI, gomme, AdBlue, scorte, movimenti e riordino.

Classi principali:

- `InventoryBalance` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `InventoryItem` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `InventoryItemType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `InventoryRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `InventoryStockMovement` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `StockMovementType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `WarehouseLocation` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/loadsecurity`

Fissaggio carico, attrezzature e checklist sicurezza carico.

Classi principali:

- `LoadSecuringChecklist` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `LoadSecuringEquipment` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `LoadSecuringEquipmentType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `LoadSecuringRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/location`

Indirizzi, coordinate geografiche e luoghi fisici.

Classi principali:

- `Address` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `GeoCoordinates` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Location` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/maintenance`

Manutenzione veicoli, ordini lavoro, scadenze e ticket difetti driver.

Classi principali:

- `DriverDefectTicket` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `MaintenanceRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `MaintenanceStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `MaintenanceType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `MaintenanceWorkOrder` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `VehicleDowntime` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

## `domain/notification`

Messaggi, notifiche, canali e regole di invio.

Classi principali:

- `NotificationChannel` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `NotificationMessage` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `NotificationPriority` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `NotificationRecipientType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `NotificationRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `NotificationStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `NotificationType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/operation`

Missione operativa reale: autista, convoglio, rotta e stati missione.

Classi principali:

- `TransportMission` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TransportMissionRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `TransportMissionStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/order`

Ordini di trasporto commerciali prima della spedizione.

Classi principali:

- `TransportOrder` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TransportOrderStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TransportServiceType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/parking`

Posti parcheggio numerati e risorse parcheggiate, inclusi convogli già agganciati.

Classi principali:

- `ParkedResource` — Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.
- `ParkingAssignment` — Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.
- `ParkingResourceType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ParkingRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `ParkingSpot` — Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.
- `ParkingSpotStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ParkingSpotType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/payroll`

Costo autista: ore, straordinari, trasferte, ADR, CE, notturno, festivo e supplementi.

Classi principali:

- `DriverMissionPayLine` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverMissionPayroll` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverMissionWorkReport` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverPayComponentType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverPayRule` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverPayUnit` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `DriverPayrollPolicy` — Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.
- `DriverPayrollRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `MissionPayrollProjection` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/pricing`

Preventivi e breakdown prezzo cliente.

Classi principali:

- `CostEstimationSource` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PriceBreakdown` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `PricingLine` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `PricingLineType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `PricingRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `RouteCostEstimate` — Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

## `domain/reporting`

Report generati, metriche e regole reporting.

Classi principali:

- `GeneratedReport` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ReportDefinition` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ReportFormat` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ReportMetric` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ReportMetricType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ReportStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ReportType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `ReportingRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/route`

Tappe, pianificazione route e regole di percorso.

Classi principali:

- `RoutePlan` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `RoutePlanRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `RouteStop` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `RouteStopType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/shared`

Value object riutilizzabili: Money, Weight, Distance, Notes, ecc.

Classi principali:

- `DateRange` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Dimension` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Distance` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Money` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Notes` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Percentage` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TemperatureRange` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TimeWindow` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Volume` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `Weight` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/shipment`

Spedizione nata da ordine accettato: cosa deve essere trasportato e stato logistico.

Classi principali:

- `Shipment` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ShipmentRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `ShipmentStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/sustainability`

Emissioni e sostenibilità del trasporto.

Classi principali:

- `EmissionEstimate` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `EmissionRating` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `EmissionStandard` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `FuelType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `SustainabilityRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

## `domain/telematics`

Snapshot GPS/CAN-bus, comportamento guida e dati telematici.

Classi principali:

- `DrivingBehaviorEvent` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `DrivingBehaviorEventType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TelematicsRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `TelematicsSnapshot` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `domain/tire`

Gomme fisiche tracciabili, posizioni ruota, installazioni e stato pneumatico.

Classi principali:

- `Tire` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `TireInstallation` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `TireRotationEvent` — Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.
- `TireRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `TireStatus` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `WheelPosition` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `WheelSide` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `WheelSlot` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

## `domain/tracking`

Timeline eventi tracking della spedizione/missione.

Classi principali:

- `TrackingEvent` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `TrackingEventType` — Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
- `TrackingRules` — Classe di regole: contiene controlli e decisioni di business, senza salvare dati.
- `TrackingTimeline` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
