# Domain Package Map

| Package | Responsabilità | Classi principali |
|---|---|---|
| `audit` | Audit trail. Registra eventi di audit per modifiche, login, permessi, verifiche documentali e azioni sensibili. | `AuditActionType, AuditActorType, AuditEvent, AuditRules, AuditSeverity, AuditTrail` |
| `availability` | Disponibilità risorse. Rappresenta disponibilità, assegnazione, manutenzione, ferie e indisponibilità di driver, veicoli, convogli e facility. | `AvailabilityResourceType, AvailabilityRules, AvailabilityStatus, ResourceAvailability` |
| `billing` | Fatturazione e pagamenti. Gestisce fatture, stato fattura, pagamenti e regole economiche base. | `BillingRules, Invoice, InvoiceStatus, PaymentMethod, PaymentRecord` |
| `cargo` | Merci e carichi. Modella merce, colli, carichi, categorie cargo, ADR e regole operative derivate dal tipo di merce. | `AdrClass, CargoCategory, CargoItem, CargoLoad, CargoLoadRules, CargoOperationalRules…` |
| `claim` | Danni e reclami. Gestisce reclami, danni merce, ritardi, dispute, sinistri, damage inspection e severità. | `ClaimRules, ClaimSeverity, ClaimStatus, ClaimType, DamageInspection, DamageInspectionItem…` |
| `company` | Azienda e licenze. Modella l’impresa di trasporto e le licenze operative necessarie: albo, REN, licenza comunitaria, albo gestori ambientali. | `CompanyComplianceRules, CompanyLicense, CompanyLicenseType, TransportCompany` |
| `compliance` | Compliance trasversale. Contiene regole che incrociano cargo, driver, veicolo e documenti. | `ComplianceRules` |
| `configuration` | Configurazioni dominio. Modella configurazioni tipizzate, categorie e scope applicativi senza dipendere da un database. | `ConfigurationCategory, ConfigurationRules, ConfigurationScope, ConfigurationValue, ConfigurationValueType, SystemConfiguration` |
| `customer` | Clienti e account. Gestisce cliente, stato commerciale, account operativo e contatti per logistica, amministrazione e fatturazione. | `Customer, CustomerAccount, CustomerContact, CustomerContactRole, CustomerStatus, CustomerType` |
| `document` | Documenti di trasporto. Gestisce documenti richiesti, ricevuti, verificati, scaduti o rifiutati: CMR, POD, FIR, ADR, ATP, HACCP, veterinari. | `DocumentRules, DocumentStatus, TransportDocument, TransportDocumentType` |
| `driver` | Autisti e qualifiche. Gestisce autisti, patenti, CQC, ADR, qualifiche operative e certificati con scadenze reali. | `Driver, DriverAdrCertificateType, DriverCertificate, DriverCertificateType, DriverLicenseCategory, DriverOperationalQualification…` |
| `drivetime` | Tempi guida/riposo. Contiene regole pure per limiti di guida, pause e riposi, preparate per una futura pianificazione più realistica. | `DriverTimeRules` |
| `facility` | Sedi operative. Rappresenta magazzini, depositi, clienti, terminal, porti, aeroporti e centri manutenzione. | `Facility, FacilityType` |
| `fleet` | Flotta e schede tecniche. Gestisce veicoli, rimorchi, trattori, convogli, assi, masse, allestimenti, equipaggiamenti, certificati e compatibilità tecnica. | `AxleSteeringType, BrakeSafetySystem, BrakeType, CouplingType, DeadlineStatus, KingpinDiameter…` |
| `fuel` | Carburante e consumi. Gestisce transazioni carburante, fuel card provider e regole di consumo/anomalia tra rifornimenti. | `FuelCardProvider, FuelConsumptionRules, FuelTransaction` |
| `identity` | Utenti, ruoli e permessi. Gestisce account applicativi separati da driver/customer, ruoli e permessi. | `IdentityRules, UserAccount, UserAccountStatus, UserPermission, UserRole` |
| `loadsecurity` | Fissaggio carico. Modella checklist e dotazioni di fissaggio: cinghie, barre, antiscivolo, protezioni e reti. | `LoadSecuringChecklist, LoadSecuringEquipment, LoadSecuringEquipmentType, LoadSecuringRules` |
| `location` | Indirizzi e coordinate. Modella coordinate geografiche, indirizzi e punti logistici geolocalizzabili. | `Address, GeoCoordinates, Location` |
| `maintenance` | Manutenzione e downtime. Gestisce work order, scadenze manutentive, ticket autista, fermo mezzo e tipologie intervento. | `DriverDefectTicket, MaintenanceRules, MaintenanceStatus, MaintenanceType, MaintenanceWorkOrder, VehicleDowntime` |
| `notification` | Notifiche. Rappresenta messaggi, canali, destinatari, priorità e stati di invio. | `NotificationChannel, NotificationMessage, NotificationPriority, NotificationRecipientType, NotificationRules, NotificationStatus…` |
| `operation` | Missioni operative. Rappresenta l’esecuzione reale: spedizione + route plan + convoglio + autista + stato operativo. | `TransportMission, TransportMissionRules, TransportMissionStatus` |
| `order` | Ordini di trasporto. Rappresenta la richiesta commerciale accettabile o rifiutabile prima di diventare spedizione. | `TransportOrder, TransportOrderStatus, TransportServiceType` |
| `pricing` | Preventivazione costi. Modella stima costi rotta, breakdown prezzo e righe economiche: base freight, fuel surcharge, pedaggi, ADR, temperatura. | `CostEstimationSource, PriceBreakdown, PricingLine, PricingLineType, PricingRules, RouteCostEstimate` |
| `reporting` | Reportistica. Definisce report, metriche, formato e stato di generazione per viste operative, economiche e compliance. | `GeneratedReport, ReportDefinition, ReportFormat, ReportMetric, ReportMetricType, ReportStatus…` |
| `route` | Piani di viaggio. Modella soste, pickup, delivery, pause, carburante e sequenza di viaggio. | `RoutePlan, RoutePlanRules, RouteStop, RouteStopType` |
| `shared` | Value Object comuni. Contiene tipi immutabili e validati usati da tutto il dominio: peso, denaro, distanze, volumi, dimensioni, finestre temporali, range date, temperatura e note. | `DateRange, Dimension, Distance, Money, Notes, Percentage…` |
| `shipment` | Spedizioni. Rappresenta la spedizione generata da un ordine accettato. È il collegamento commerciale/logistico tra ordine e missione operativa. | `Shipment, ShipmentRules, ShipmentStatus` |
| `sustainability` | Emissioni e sostenibilità. Calcola emissioni stimate, rating, fuel type e standard emissivi Euro/zero emission. | `EmissionEstimate, EmissionRating, EmissionStandard, FuelType, SustainabilityRules` |
| `telematics` | Telematica e CAN bus. Modella snapshot GPS/CAN-bus e anomalie di guida: velocità, fuel drop, frenate brusche, idling. | `DrivingBehaviorEvent, DrivingBehaviorEventType, TelematicsRules, TelematicsSnapshot` |
| `tire` | Pneumatici. Traccia singole gomme fisiche, RFID, posizione ruota, installazioni, rotazioni, stato e soglia battistrada. | `Tire, TireInstallation, TireRotationEvent, TireRules, TireStatus, WheelPosition…` |
| `tracking` | Timeline eventi. Registra eventi di viaggio: partenza, arrivo, pickup, delivery, ritardi, incidenti, snapshot telematici. | `TrackingEvent, TrackingEventType, TrackingRules, TrackingTimeline` |
