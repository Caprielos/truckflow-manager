# Domain Reference Complete

Catalogo tecnico delle classi domain presenti nella versione documentata.

## `audit` — Audit trail

Registra eventi di audit per modifiche, login, permessi, verifiche documentali e azioni sensibili.

- `AuditActionType`: enum. Valori principali: `CREATED`, `UPDATED`, `STATUS_CHANGED`, `ASSIGNED`, `CANCELLED`, `DELETED`, `DOCUMENT_VERIFIED`, `PAYMENT_REGISTERED`, `CLAIM_SETTLED`, `EXTERNAL_ESTIMATE_IMPORTED`, `LOGIN`, `LOGIN_FAILED`, `PERMISSION_DENIED`.
- `AuditActorType`: enum. Valori principali: `USER`, `SYSTEM`, `INTEGRATION`.
- `AuditEvent`: entity/value object/domain model.
- `AuditRules`: rules class.
- `AuditSeverity`: enum. Valori principali: `INFO`, `WARNING`, `ERROR`, `CRITICAL`.
- `AuditTrail`: entity/value object/domain model.

## `availability` — Disponibilità risorse

Rappresenta disponibilità, assegnazione, manutenzione, ferie e indisponibilità di driver, veicoli, convogli e facility.

- `AvailabilityResourceType`: enum. Valori principali: `DRIVER`, `VEHICLE`, `VEHICLE_COMBINATION`, `TRAILER`, `FACILITY`.
- `AvailabilityRules`: rules class.
- `AvailabilityStatus`: enum. Valori principali: `AVAILABLE`, `RESERVED`, `ASSIGNED`, `UNAVAILABLE`, `MAINTENANCE`, `ON_LEAVE`.
- `ResourceAvailability`: entity/value object/domain model.

## `billing` — Fatturazione e pagamenti

Gestisce fatture, stato fattura, pagamenti e regole economiche base.

- `BillingRules`: rules class.
- `Invoice`: entity/value object/domain model.
- `InvoiceStatus`: enum. Valori principali: `DRAFT`, `ISSUED`, `PAID`, `CANCELLED`.
- `PaymentMethod`: enum. Valori principali: `BANK_TRANSFER`, `CARD`, `CASH`, `DIRECT_DEBIT`, `CREDIT_NOTE`, `OTHER`.
- `PaymentRecord`: entity/value object/domain model.

## `cargo` — Merci e carichi

Modella merce, colli, carichi, categorie cargo, ADR e regole operative derivate dal tipo di merce.

- `AdrClass`: enum. Valori principali: `CLASS_1_EXPLOSIVES`, `CLASS_2_GASES`, `CLASS_3_FLAMMABLE_LIQUIDS`, `CLASS_4_1_FLAMMABLE_SOLIDS`, `CLASS_4_2_SPONTANEOUS_COMBUSTION`, `CLASS_4_3_WATER_REACTIVE`, `CLASS_5_1_OXIDIZING_SUBSTANCES`, `CLASS_5_2_ORGANIC_PEROXIDES`, `CLASS_6_1_TOXIC_SUBSTANCES`, `CLASS_6_2_INFECTIOUS_SUBSTANCES`, `CLASS_7_RADIOACTIVE_MATERIAL`, `CLASS_8_CORROSIVE_SUBSTANCES`, `CLASS_9_MISCELLANEOUS`.
- `CargoCategory`: enum. Valori principali: `GENERAL`, `PALLETIZED_DRY_GOODS`, `FOOD`, `REFRIGERATED_FOOD`, `PHARMACEUTICAL`, `TEMPERATURE_CONTROLLED_GOODS`, `FRAGILE`, `ELECTRONICS`, `HIGH_VALUE_GOODS`, `HAZARDOUS_MATERIAL`, `DANGEROUS_GOODS`, `OVERSIZED`, `MACHINERY`, `VEHICLES`, `CONTAINERIZED_GOODS`, `LIQUID`, `FOOD_GRADE_LIQUID`, `FUEL`, `GAS`, `CONSTRUCTION_MATERIAL`….
- `CargoItem`: entity/value object/domain model.
- `CargoLoad`: entity/value object/domain model.
- `CargoLoadRules`: rules class.
- `CargoOperationalRules`: rules class.
- `DangerousGoodsProfile`: entity/value object/domain model.
- `HazardLabel`: enum. Valori principali: `LABEL_1_EXPLOSIVES`, `LABEL_2_1_FLAMMABLE_GAS`, `LABEL_2_2_NON_FLAMMABLE_GAS`, `LABEL_2_3_TOXIC_GAS`, `LABEL_3_FLAMMABLE_LIQUID`, `LABEL_4_1_FLAMMABLE_SOLID`, `LABEL_4_2_SPONTANEOUS_COMBUSTION`, `LABEL_4_3_DANGEROUS_WHEN_WET`, `LABEL_5_1_OXIDIZER`, `LABEL_5_2_ORGANIC_PEROXIDE`, `LABEL_6_1_TOXIC`, `LABEL_6_2_INFECTIOUS`, `LABEL_7_RADIOACTIVE`, `LABEL_8_CORROSIVE`, `LABEL_9_MISCELLANEOUS`.
- `PackingGroup`: enum. Valori principali: `I`, `II`, `III`.

## `claim` — Danni e reclami

Gestisce reclami, danni merce, ritardi, dispute, sinistri, damage inspection e severità.

- `ClaimRules`: rules class.
- `ClaimSeverity`: enum. Valori principali: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.
- `ClaimStatus`: enum. Valori principali: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `SETTLED`, `REJECTED`, `CANCELLED`.
- `ClaimType`: enum. Valori principali: `CARGO_DAMAGE`, `CARGO_LOSS`, `DELAY`, `TEMPERATURE_EXCURSION`, `DOCUMENT_DISPUTE`, `BILLING_DISPUTE`, `VEHICLE_DAMAGE`, `ACCIDENT`, `INSURANCE_CLAIM`, `OTHER`.
- `DamageInspection`: entity/value object/domain model.
- `DamageInspectionItem`: entity/value object/domain model.
- `TransportClaim`: entity/value object/domain model.

## `company` — Azienda e licenze

Modella l’impresa di trasporto e le licenze operative necessarie: albo, REN, licenza comunitaria, albo gestori ambientali.

- `CompanyComplianceRules`: rules class.
- `CompanyLicense`: entity/value object/domain model.
- `CompanyLicenseType`: enum. Valori principali: `ROAD_HAULAGE_REGISTER`, `REN`, `COMMUNITY_LICENSE`, `OWN_ACCOUNT_LICENSE`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_2_BIS`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4`, `ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_5`.
- `TransportCompany`: entity/value object/domain model.

## `compliance` — Compliance trasversale

Contiene regole che incrociano cargo, driver, veicolo e documenti.

- `ComplianceRules`: rules class.

## `configuration` — Configurazioni dominio

Modella configurazioni tipizzate, categorie e scope applicativi senza dipendere da un database.

- `ConfigurationCategory`: enum. Valori principali: `OPERATION`, `PRICING`, `NOTIFICATION`, `DOCUMENT`, `SECURITY`, `SUSTAINABILITY`, `REPORTING`, `INTEGRATION`.
- `ConfigurationRules`: rules class.
- `ConfigurationScope`: enum. Valori principali: `GLOBAL`, `ORGANIZATION`, `CUSTOMER`, `FACILITY`, `USER`.
- `ConfigurationValue`: entity/value object/domain model.
- `ConfigurationValueType`: enum. Valori principali: `TEXT`, `BOOLEAN`, `INTEGER`, `DECIMAL`, `PERCENTAGE`, `DURATION_MINUTES`.
- `SystemConfiguration`: entity/value object/domain model.

## `customer` — Clienti e account

Gestisce cliente, stato commerciale, account operativo e contatti per logistica, amministrazione e fatturazione.

- `Customer`: entity/value object/domain model.
- `CustomerAccount`: entity/value object/domain model.
- `CustomerContact`: entity/value object/domain model.
- `CustomerContactRole`: enum. Valori principali: `LOGISTICS`, `ADMINISTRATION`, `BILLING`, `OPERATIONS`, `SALES`, `MANAGEMENT`, `OTHER`.
- `CustomerStatus`: enum. Valori principali: `ACTIVE`, `INACTIVE`, `SUSPENDED`.
- `CustomerType`: enum. Valori principali: `INDIVIDUAL`, `COMPANY`, `PUBLIC_AUTHORITY`, `INTERNAL`.

## `document` — Documenti di trasporto

Gestisce documenti richiesti, ricevuti, verificati, scaduti o rifiutati: CMR, POD, FIR, ADR, ATP, HACCP, veterinari.

- `DocumentRules`: rules class.
- `DocumentStatus`: enum. Valori principali: `DRAFT`, `REQUESTED`, `RECEIVED`, `VERIFIED`, `REJECTED`, `EXPIRED`.
- `TransportDocument`: entity/value object/domain model.
- `TransportDocumentType`: enum. Valori principali: `CMR_WAYBILL`, `PROOF_OF_DELIVERY`, `DELIVERY_NOTE`, `ADR_TRANSPORT_DOCUMENT`, `TEMPERATURE_LOG`, `INVOICE_COPY`, `INSURANCE_CERTIFICATE`, `VEHICLE_REGISTRATION`, `DRIVER_LICENSE_COPY`, `WASTE_IDENTIFICATION_FORM`, `SAFETY_DATA_SHEET`, `ADR_WRITTEN_INSTRUCTIONS`, `HACCP_SANITATION_DOCUMENT`, `VETERINARY_DOCUMENT`, `OVERSIZED_TRANSPORT_AUTHORIZATION`.

## `driver` — Autisti e qualifiche

Gestisce autisti, patenti, CQC, ADR, qualifiche operative e certificati con scadenze reali.

- `Driver`: entity/value object/domain model.
- `DriverAdrCertificateType`: enum. Valori principali: `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`.
- `DriverCertificate`: entity/value object/domain model.
- `DriverCertificateType`: enum. Valori principali: `LICENSE_B`, `LICENSE_C1`, `LICENSE_C`, `LICENSE_BE`, `LICENSE_C1E`, `LICENSE_CE`, `CQC_GOODS`, `ADR_BASIC`, `ADR_TANK`, `ADR_CLASS_1_EXPLOSIVES`, `ADR_CLASS_7_RADIOACTIVE`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `TEMPERATURE_CONTROLLED_TRANSPORT`, `OVERSIZED_TRANSPORT`, `HIGH_VALUE_CARGO`, `INTERNATIONAL_TRANSPORT`.
- `DriverLicenseCategory`: enum. Valori principali: `B`, `C1`, `C`, `BE`, `C1E`, `CE`, `E`.
- `DriverOperationalQualification`: enum. Valori principali: `TEMPERATURE_CONTROLLED_TRANSPORT`, `INTERNATIONAL_TRANSPORT`, `HIGH_VALUE_CARGO`, `OVERSIZED_CARGO`, `TRUCK_MOUNTED_CRANE`, `AERIAL_PLATFORM`, `FORKLIFT`, `EARTH_MOVING_MACHINES`, `LIVE_ANIMAL_TRANSPORT`, `VEHICLE_RECOVERY_OPERATION`, `BULK_TRANSPORT`, `WASTE_TRANSPORT`.
- `DriverProfessionalQualification`: enum. Valori principali: `CQC_GOODS`.
- `DriverRules`: rules class.
- `DriverStatus`: enum. Valori principali: `AVAILABLE`, `ASSIGNED`, `ON_LEAVE`, `SUSPENDED`, `INACTIVE`.

## `drivetime` — Tempi guida/riposo

Contiene regole pure per limiti di guida, pause e riposi, preparate per una futura pianificazione più realistica.

- `DriverTimeRules`: rules class.

## `facility` — Sedi operative

Rappresenta magazzini, depositi, clienti, terminal, porti, aeroporti e centri manutenzione.

- `Facility`: entity/value object/domain model.
- `FacilityType`: enum. Valori principali: `WAREHOUSE`, `DEPOT`, `CUSTOMER_SITE`, `SUPPLIER_SITE`, `CROSS_DOCK`, `TERMINAL`, `PORT`, `AIRPORT`, `MAINTENANCE_CENTER`.

## `fleet` — Flotta e schede tecniche

Gestisce veicoli, rimorchi, trattori, convogli, assi, masse, allestimenti, equipaggiamenti, certificati e compatibilità tecnica.

- `AxleSteeringType`: enum. Valori principali: `FIXED`, `STEERING`, `SELF_STEERING`.
- `BrakeSafetySystem`: enum. Valori principali: `ABS`, `EBS`, `ESP`, `RSP`.
- `BrakeType`: enum. Valori principali: `DISC`, `DRUM`.
- `CouplingType`: enum. Valori principali: `NONE`, `FIFTH_WHEEL`, `DRAWBAR_EYE`, `CENTER_AXLE_DRAWBAR`, `TOW_HOOK`.
- `DeadlineStatus`: enum. Valori principali: `VALID`, `EXPIRING_SOON`, `EXPIRED`.
- `KingpinDiameter`: enum. Valori principali: `TWO_INCHES`, `THREE_AND_HALF_INCHES`.
- `RetarderType`: enum. Valori principali: `NONE`, `HYDRAULIC_RETARDER`, `INTARDER`, `ENHANCED_ENGINE_BRAKE`.
- `SuspensionType`: enum. Valori principali: `MECHANICAL`, `PNEUMATIC`, `HYDRAULIC`, `LEAF_SPRING`.
- `TireSpecification`: entity/value object/domain model.
- `TransmissionType`: enum. Valori principali: `MANUAL`, `AUTOMATED`, `AUTOMATIC`.
- `Vehicle`: entity/value object/domain model.
- `VehicleAxle`: entity/value object/domain model.
- `VehicleAxleSpecification`: entity/value object/domain model.
- `VehicleBodyBaseType`: enum. Valori principali: `NONE`, `FIXED_OPEN_BOX`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `CURTAIN_SIDE`, `DRY_BOX`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `TANK`, `SILO`, `FLATBED`, `LOW_LOADER`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `WALKING_FLOOR`, `CAR_TRANSPORTER`, `COIL_CARRIER`, `LIVESTOCK_BODY`, `CONCRETE_MIXER`….
- `VehicleBodyCompatibilityRules`: rules class.
- `VehicleBodyConfiguration`: entity/value object/domain model.
- `VehicleBodyType`: enum. Valori principali: `NONE`, `VAN_BODY`, `BOX`, `DRY_BOX`, `CURTAIN_SIDE`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `FIXED_OPEN_BOX`, `FLATBED`, `FLATBED_WITH_RAMPS`, `LOW_LOADER`, `EXTENDABLE_FLATBED`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `TIPPER`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `WALKING_FLOOR`, `SILO`….
- `VehicleCertificate`: entity/value object/domain model.
- `VehicleCertificateType`: enum. Valori principali: `ROADWORTHINESS_INSPECTION`, `TACHOGRAPH_CALIBRATION`, `ATP`, `ADR_VEHICLE_APPROVAL`, `XL_CODE`, `TANK_PERIODIC_INSPECTION`, `CRANE_PERIODIC_INSPECTION`, `TAIL_LIFT_PERIODIC_INSPECTION`, `INSURANCE`, `ROAD_TAX`.
- `VehicleCombination`: entity/value object/domain model.
- `VehicleCombinationLegalLimitProfile`: entity/value object/domain model.
- `VehicleCombinationRules`: rules class.
- `VehicleCombinationTechnicalRules`: rules class.
- `VehicleCombinationType`: enum. Valori principali: `SINGLE_VEHICLE`, `TRUCK_AND_TRAILER`, `ARTICULATED_VEHICLE`.
- `VehicleCouplingSpecification`: entity/value object/domain model.
- `VehicleDimensionSpecification`: entity/value object/domain model.
- `VehicleEquipmentPosition`: enum. Valori principali: `BEHIND_CAB`, `REAR`, `REAR_PLATFORM`, `CHASSIS`, `ROOF`, `SIDE`, `NOT_APPLICABLE`.
- `VehicleLoadingEquipment`: entity/value object/domain model.
- `VehicleLoadingEquipmentType`: enum. Valori principali: `HYDRAULIC_CRANE`, `TAIL_LIFT`, `HYDRAULIC_RAMP`, `MANUAL_RAMP`, `HYDRAULIC_WINCH`, `ELECTRIC_WINCH`, `POLYP_GRAPPLE_LOADER`, `REFRIGERATION_UNIT`, `TWIST_LOCK`.
- `VehicleMassSpecification`: entity/value object/domain model.
- `VehicleStatus`: enum. Valori principali: `AVAILABLE`, `ASSIGNED`, `IN_MAINTENANCE`, `OUT_OF_SERVICE`, `RETIRED`.
- `VehicleTechnicalFeature`: enum. Valori principali: `SELF_STEERING_AXLE`, `STEERING_AXLE`, `LIFTABLE_AXLE`, `DOUBLE_DECK`, `MEGA_VOLUME`, `LOW_DECK`, `AIR_SUSPENSION`, `EXTENDABLE_CHASSIS`, `GOOSENECK`, `LOW_BED_CRADLE`, `TWIST_LOCKS`, `ATP_CERTIFIED`, `XL_CERTIFIED`, `ADR_APPROVED`, `ACTIVE_REFRIGERATION`, `TEMPERATURE_RECORDER`, `HAY_RACKS`, `AGRICULTURAL_APPROVAL`, `STAINLESS_STEEL_TANK`, `FOOD_GRADE_TANK`.
- `VehicleTechnicalSpecification`: entity/value object/domain model.
- `VehicleType`: enum. Valori principali: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`, `REFRIGERATED_TRUCK`, `REFRIGERATED_TRAILER`.
- `VehicleUnitType`: enum. Valori principali: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`.
- `VehicleWeightClass`: enum. Valori principali: `LIGHT_UNDER_3_5T`, `MEDIUM_UP_TO_12T`, `HEAVY_OVER_12T`.
- `WheelConfiguration`: enum. Valori principali: `SINGLE`, `TWIN`.

## `fuel` — Carburante e consumi

Gestisce transazioni carburante, fuel card provider e regole di consumo/anomalia tra rifornimenti.

- `FuelCardProvider`: enum. Valori principali: `DKV`, `UTA`, `ENI`, `SHELL`, `OTHER`.
- `FuelConsumptionRules`: rules class.
- `FuelTransaction`: entity/value object/domain model.

## `identity` — Utenti, ruoli e permessi

Gestisce account applicativi separati da driver/customer, ruoli e permessi.

- `IdentityRules`: rules class.
- `UserAccount`: entity/value object/domain model.
- `UserAccountStatus`: enum. Valori principali: `INVITED`, `ACTIVE`, `LOCKED`, `DISABLED`, `DELETED`.
- `UserPermission`: enum. Valori principali: `VIEW_SHIPMENTS`, `MANAGE_SHIPMENTS`, `VIEW_OPERATIONS`, `MANAGE_OPERATIONS`, `MANAGE_FLEET`, `MANAGE_DRIVERS`, `MANAGE_BILLING`, `MANAGE_DOCUMENTS`, `MANAGE_CLAIMS`, `VIEW_REPORTS`, `VIEW_AUDIT`, `MANAGE_USERS`, `MANAGE_CONFIGURATION`.
- `UserRole`: enum. Valori principali: `ADMIN`, `DISPATCHER`, `PLANNER`, `ACCOUNTING`, `MAINTENANCE`, `DRIVER`, `CUSTOMER`, `VIEWER`.

## `loadsecurity` — Fissaggio carico

Modella checklist e dotazioni di fissaggio: cinghie, barre, antiscivolo, protezioni e reti.

- `LoadSecuringChecklist`: entity/value object/domain model.
- `LoadSecuringEquipment`: entity/value object/domain model.
- `LoadSecuringEquipmentType`: enum. Valori principali: `RATCHET_STRAP`, `LOAD_BAR`, `ANTI_SLIP_MAT`, `CONTAINMENT_NET`, `EDGE_PROTECTOR`.
- `LoadSecuringRules`: rules class.

## `location` — Indirizzi e coordinate

Modella coordinate geografiche, indirizzi e punti logistici geolocalizzabili.

- `Address`: entity/value object/domain model.
- `GeoCoordinates`: entity/value object/domain model.
- `Location`: entity/value object/domain model.

## `maintenance` — Manutenzione e downtime

Gestisce work order, scadenze manutentive, ticket autista, fermo mezzo e tipologie intervento.

- `DriverDefectTicket`: entity/value object/domain model.
- `MaintenanceRules`: rules class.
- `MaintenanceStatus`: enum. Valori principali: `OPEN`, `SCHEDULED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.
- `MaintenanceType`: enum. Valori principali: `ROUTINE_SERVICE`, `SAFETY_INSPECTION`, `TIRE_REPLACEMENT`, `REPAIR`, `REFRIGERATION_UNIT_SERVICE`, `ADR_TANK_INSPECTION`, `BREAKDOWN`, `ENGINE_SERVICE`, `AIR_DRYER_FILTER_REPLACEMENT`, `BRAKE_WEAR_CHECK`, `TIRE_ROTATION`, `DRIVER_DEFECT_TICKET`, `DOWNTIME`.
- `MaintenanceWorkOrder`: entity/value object/domain model.
- `VehicleDowntime`: entity/value object/domain model.

## `notification` — Notifiche

Rappresenta messaggi, canali, destinatari, priorità e stati di invio.

- `NotificationChannel`: enum. Valori principali: `EMAIL`, `SMS`, `PUSH`, `IN_APP`, `WEBHOOK`.
- `NotificationMessage`: entity/value object/domain model.
- `NotificationPriority`: enum. Valori principali: `LOW`, `NORMAL`, `HIGH`, `URGENT`.
- `NotificationRecipientType`: enum. Valori principali: `CUSTOMER_CONTACT`, `DRIVER`, `DISPATCHER`, `ADMIN`, `INTEGRATION`, `SYSTEM`.
- `NotificationRules`: rules class.
- `NotificationStatus`: enum. Valori principali: `DRAFT`, `SCHEDULED`, `SENT`, `FAILED`, `CANCELLED`.
- `NotificationType`: enum. Valori principali: `SHIPMENT_PLANNED`, `SHIPMENT_DELAYED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DOCUMENT_REQUESTED`, `DOCUMENT_VERIFIED`, `INVOICE_ISSUED`, `PAYMENT_RECEIVED`, `CLAIM_UPDATED`, `MAINTENANCE_ALERT`, `SECURITY_ALERT`, `SYSTEM_ALERT`.

## `operation` — Missioni operative

Rappresenta l’esecuzione reale: spedizione + route plan + convoglio + autista + stato operativo.

- `TransportMission`: entity/value object/domain model.
- `TransportMissionRules`: rules class.
- `TransportMissionStatus`: enum. Valori principali: `PLANNED`, `DISPATCHED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.

## `order` — Ordini di trasporto

Rappresenta la richiesta commerciale accettabile o rifiutabile prima di diventare spedizione.

- `TransportOrder`: entity/value object/domain model.
- `TransportOrderStatus`: enum. Valori principali: `DRAFT`, `SUBMITTED`, `ACCEPTED`, `REJECTED`, `CANCELLED`.
- `TransportServiceType`: enum. Valori principali: `STANDARD`, `EXPRESS`, `REFRIGERATED`, `HAZARDOUS`, `OVERSIZED`.

## `pricing` — Preventivazione costi

Modella stima costi rotta, breakdown prezzo e righe economiche: base freight, fuel surcharge, pedaggi, ADR, temperatura.

- `CostEstimationSource`: enum. Valori principali: `MANUAL`, `INTERNAL_MODEL`, `VIAMICHELIN`, `HERE_MAPS`, `PTV`, `GOOGLE_MAPS`, `OTHER_EXTERNAL_PROVIDER`.
- `PriceBreakdown`: entity/value object/domain model.
- `PricingLine`: entity/value object/domain model.
- `PricingLineType`: enum. Valori principali: `BASE_FREIGHT`, `DISTANCE_CHARGE`, `FUEL_SURCHARGE`, `TOLL_CHARGE`, `VEHICLE_WEAR_CHARGE`, `ADR_SURCHARGE`, `TEMPERATURE_CONTROL_SURCHARGE`, `WAITING_TIME_CHARGE`, `HANDLING_CHARGE`, `DISCOUNT`.
- `PricingRules`: rules class.
- `RouteCostEstimate`: entity/value object/domain model.

## `reporting` — Reportistica

Definisce report, metriche, formato e stato di generazione per viste operative, economiche e compliance.

- `GeneratedReport`: entity/value object/domain model.
- `ReportDefinition`: entity/value object/domain model.
- `ReportFormat`: enum. Valori principali: `PDF`, `CSV`, `XLSX`, `JSON`, `HTML`.
- `ReportMetric`: entity/value object/domain model.
- `ReportMetricType`: enum. Valori principali: `SHIPMENT_COUNT`, `COMPLETED_SHIPMENT_COUNT`, `DELAY_COUNT`, `CLAIM_COUNT`, `DOCUMENT_EXPIRATION_COUNT`, `MAINTENANCE_COUNT`, `TOTAL_DISTANCE_KM`, `TOTAL_REVENUE`, `TOTAL_COST`, `TOTAL_CO2_KG`, `VEHICLE_UTILIZATION_PERCENTAGE`, `ON_TIME_DELIVERY_PERCENTAGE`.
- `ReportStatus`: enum. Valori principali: `DRAFT`, `GENERATED`, `PUBLISHED`, `ARCHIVED`, `FAILED`.
- `ReportType`: enum. Valori principali: `OPERATIONS`, `FINANCIAL`, `FLEET`, `DRIVER`, `CUSTOMER`, `SUSTAINABILITY`, `COMPLIANCE`, `CLAIMS`, `DOCUMENTS`.
- `ReportingRules`: rules class.

## `route` — Piani di viaggio

Modella soste, pickup, delivery, pause, carburante e sequenza di viaggio.

- `RoutePlan`: entity/value object/domain model.
- `RoutePlanRules`: rules class.
- `RouteStop`: entity/value object/domain model.
- `RouteStopType`: enum. Valori principali: `START`, `PICKUP`, `DELIVERY`, `REST_BREAK`, `FUEL_STOP`, `END`.

## `shared` — Value Object comuni

Contiene tipi immutabili e validati usati da tutto il dominio: peso, denaro, distanze, volumi, dimensioni, finestre temporali, range date, temperatura e note.

- `DateRange`: entity/value object/domain model.
- `Dimension`: entity/value object/domain model.
- `Distance`: entity/value object/domain model.
- `Money`: entity/value object/domain model.
- `Notes`: entity/value object/domain model.
- `Percentage`: entity/value object/domain model.
- `TemperatureRange`: entity/value object/domain model.
- `TimeWindow`: entity/value object/domain model.
- `Volume`: entity/value object/domain model.
- `Weight`: entity/value object/domain model.

## `shipment` — Spedizioni

Rappresenta la spedizione generata da un ordine accettato. È il collegamento commerciale/logistico tra ordine e missione operativa.

- `Shipment`: entity/value object/domain model.
- `ShipmentRules`: rules class.
- `ShipmentStatus`: enum. Valori principali: `CREATED`, `PLANNED`, `DISPATCHED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`.

## `sustainability` — Emissioni e sostenibilità

Calcola emissioni stimate, rating, fuel type e standard emissivi Euro/zero emission.

- `EmissionEstimate`: entity/value object/domain model.
- `EmissionRating`: enum. Valori principali: `LOW`, `MEDIUM`, `HIGH`, `VERY_HIGH`.
- `EmissionStandard`: enum. Valori principali: `EURO_0`, `EURO_1`, `EURO_2`, `EURO_3`, `EURO_4`, `EURO_5`, `EURO_6`, `ZERO_EMISSION`, `UNKNOWN`.
- `FuelType`: enum. Valori principali: `DIESEL`, `HVO`, `LNG`, `CNG`, `ELECTRIC`, `HYDROGEN`, `UNKNOWN`.
- `SustainabilityRules`: rules class.

## `telematics` — Telematica e CAN bus

Modella snapshot GPS/CAN-bus e anomalie di guida: velocità, fuel drop, frenate brusche, idling.

- `DrivingBehaviorEvent`: entity/value object/domain model.
- `DrivingBehaviorEventType`: enum. Valori principali: `HARSH_BRAKING`, `HARSH_ACCELERATION`, `SPEEDING`, `IDLING_TOO_LONG`, `LOW_FUEL_LEVEL`, `POSSIBLE_FUEL_THEFT`, `ENGINE_FAULT`.
- `TelematicsRules`: rules class.
- `TelematicsSnapshot`: entity/value object/domain model.

## `tire` — Pneumatici

Traccia singole gomme fisiche, RFID, posizione ruota, installazioni, rotazioni, stato e soglia battistrada.

- `Tire`: entity/value object/domain model.
- `TireInstallation`: entity/value object/domain model.
- `TireRotationEvent`: entity/value object/domain model.
- `TireRules`: rules class.
- `TireStatus`: enum. Valori principali: `NEW`, `RETREADED`, `REGROOVED`, `IN_USE`, `STORED`, `DISPOSED`.
- `WheelPosition`: entity/value object/domain model.
- `WheelSide`: enum. Valori principali: `LEFT`, `RIGHT`, `CENTER`.
- `WheelSlot`: enum. Valori principali: `SINGLE`, `INNER`, `OUTER`.

## `tracking` — Timeline eventi

Registra eventi di viaggio: partenza, arrivo, pickup, delivery, ritardi, incidenti, snapshot telematici.

- `TrackingEvent`: entity/value object/domain model.
- `TrackingEventType`: enum. Valori principali: `POSITION_RECORDED`, `DEPARTED`, `ARRIVED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DELAY_REPORTED`, `INCIDENT_REPORTED`, `MISSION_COMPLETED`, `CAN_BUS_SNAPSHOT`, `HARSH_BRAKING`, `SPEEDING`, `FUEL_LEVEL_RECORDED`.
- `TrackingRules`: rules class.
- `TrackingTimeline`: entity/value object/domain model.
