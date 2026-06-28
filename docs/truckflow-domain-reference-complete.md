# TruckFlow Manager - Domain Reference Completa

Versione: domain MVP dopo `reporting`  
Package root: `it.gabriele.truckflow.domain`

Questo documento è il riferimento completo del domain model di TruckFlow Manager. Serve per ricordare cosa abbiamo costruito, perché lo abbiamo fatto, quali regole di business esistono, cosa deve restare fuori dal domain e quali sono i prossimi step.

---

# 1. Obiettivo del progetto

TruckFlow Manager è un gestionale per trasporti e logistica. Il domain model rappresenta il cuore del sistema: ordini di trasporto, spedizioni, missioni operative, autisti, mezzi, carichi, documenti, fatture, pagamenti, reclami, tracking, manutenzione, notifiche, audit, sostenibilità, configurazioni e report.

Il domain deve restare Java puro. Nel domain NON devono esserci Spring, REST controller, database, JPA annotations, repository, email reali, chiamate HTTP, Google Maps, ViaMichelin, HERE, PTV, filesystem, autenticazione reale, frontend, DTO JSON o codice di infrastruttura.

Architettura futura prevista:

```text
presentation / web / REST
        ↓
application / use cases
        ↓
domain
        ↓
infrastructure
```

Il domain risponde alla domanda: **quali sono le regole del business?**  
L'application layer risponderà alla domanda: **quali azioni può fare il sistema usando quelle regole?**

---

# 2. Concetti fondamentali

## 2.1 TransportOrder

`TransportOrder` è la richiesta commerciale del cliente. Esempio: il cliente chiede di trasportare 5 pallet da Milano a Roma. Non è ancora il viaggio reale.

## 2.2 Shipment

`Shipment` è la spedizione nata da un ordine accettato. Esempio: l'ordine `ORD-001` viene accettato e genera `SHP-001`.

## 2.3 RoutePlan

`RoutePlan` è il piano del percorso, composto da stop ordinati: start, pickup, delivery, rest break, fuel stop, end.

## 2.4 VehicleCombination

La spedizione non viene assegnata a un camion generico, ma a una `VehicleCombination`, perché nella realtà possiamo avere van singolo, camion rigido, trattore + semirimorchio, motrice + trailer, mezzo refrigerato, trailer ADR o cisterna.

## 2.5 TransportMission

`TransportMission` è il viaggio operativo reale. Mette insieme `Shipment`, `Driver`, `VehicleCombination` e `RoutePlan`.

## 2.6 UserAccount separato da Driver e Customer

`UserAccount` è l'account applicativo. `Driver` è l'autista operativo. `Customer` è il cliente commerciale. Sono separati perché un autista può esistere senza account, un cliente può avere più contatti e un admin interno non è né driver né customer.

---

# 3. Stato attuale del domain MVP

Package realizzati:

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
└── reporting
```

Il domain MVP è circa al 90-95%. Mancano soprattutto: eventuale package `organization`, eventuale package `carrier`, planning avanzato, application layer, infrastructure layer, API REST e frontend.

---

# 4. `domain.shared`

Package con value object riutilizzabili. Serve per evitare primitive sparse come `String`, `double`, `BigDecimal` senza significato.

## Weight

Peso in chilogrammi. Regole: valore non negativo, no NaN, no infinito, factory da chilogrammi e tonnellate, confronto tra pesi. Usato da cargo, vehicle e regole di capacità.

## Distance

Distanza in chilometri. Regole: valore non negativo, no NaN, no infinito, factory da chilometri e metri. Usato da route, pricing ed emissioni.

## Volume

Volume in metri cubi. Regole: valore non negativo, factory da metri cubi e litri. Usato per carichi e capacità.

## Dimension

Dimensioni fisiche: lunghezza, larghezza, altezza in metri. Regole: valori positivi, calcolo volume, verifica `fitsInside`. Nota: non ruota automaticamente il carico.

## Money

Denaro con `BigDecimal amount` e `Currency`. Regole: importo non negativo, stessa valuta per addizione/sottrazione/confronto, sottrazione non negativa. Usato in order, pricing, billing e claim.

## Percentage

Percentuale da 0 a 100, con conversione in moltiplicatore.

## TemperatureRange

Range temperatura in Celsius. Regole: min <= max, contains, isCoveredBy. Usato per carichi e mezzi refrigerati.

## TimeWindow

Finestra oraria. Regole: start prima di end, contains, overlaps. Due finestre che si toccano solo sull'end/start non sono overlap.

## DateRange

Intervallo di date. Regole: start <= end, contains, overlap inclusivo, giorni inclusivi. Usato in availability, maintenance e reporting.

## Notes

Note testuali. Regole: non null, trim, max 2000 caratteri, vuoto consentito, `hasText`, ricerca case-insensitive.

---

# 5. `domain.cargo`

Gestisce merci, carichi, ADR e compatibilità base del carico.

## CargoCategory

Categorie: `GENERAL`, `FOOD`, `REFRIGERATED_FOOD`, `PHARMACEUTICAL`, `FRAGILE`, `HAZARDOUS_MATERIAL`, `OVERSIZED`, `LIQUID`, `ELECTRONICS`, `CONSTRUCTION_MATERIAL`. Alcune richiedono temperatura controllata.

## CargoItem

Singola merce. Campi: descrizione, categoria, peso, dimensioni, eventuale temperatura richiesta, eventuale `DangerousGoodsProfile`, note. Factory: `of`, `temperatureControlled`, `dangerousGoods`, `temperatureControlledDangerousGoods`. Regole: campi obbligatori, calcolo volume, rilevazione temperatura, dangerous goods, ADR, ADR tank e ADR class.

## CargoLoad

Carico composto da più `CargoItem`. Regole: lista obbligatoria, non vuota, nessun item nullo, lista immutabile, calcolo peso e volume totali, rilevazione temperatura, ADR, ADR tank, categorie e dimensioni.

## CargoLoadRules

Regole statiche: peso entro capacità, volume entro capacità, dimensioni compatibili, temperatura coperta, presenza ADR, presenza ADR tank, classi ADR specifiche, fragile, oversized.

## ADR

Classi modellate tramite `AdrClass`, `PackingGroup`, `HazardLabel`, `DangerousGoodsProfile`. Il profilo ADR contiene UN number, proper shipping name, classe ADR, classification code, packing group, hazard labels, tunnel restriction code, transport category e flag tank transport.

---

# 6. `domain.location`

## GeoCoordinates

Coordinate geografiche. Regole: latitudine tra -90 e 90, longitudine tra -180 e 180, no NaN, no infinito.

## Address

Indirizzo con street, city, postalCode, countryCode e coordinate opzionali. Country code di 2 lettere uppercase.

## Location

Luogo operativo con nome, address e `ZoneId`. Serve per facility e timezone.

---

# 7. `domain.facility`

## FacilityType

Tipi: warehouse, depot, customer site, supplier site, cross dock, terminal, port, airport, maintenance center.

## Facility

Punto operativo. Campi: code, type, location, operating hours, active, notes. Regole: codice normalizzato, può essere attiva/inattiva, verifica apertura a una certa ora, verifica paese e coordinate.

---

# 8. `domain.customer`

## CustomerType e CustomerStatus

Tipi cliente: individual, company, public authority, internal. Stati: active, inactive, suspended. Solo active può richiedere nuovi trasporti.

## Customer

Cliente commerciale con code, legalName, type, status, primaryLocation, notes.

## CustomerContact

Contatto cliente con fullName, role, email, phone, primaryContact, notes. Email e telefono validati.

## CustomerAccount

Aggrega `Customer` e lista contatti. Regole: almeno un contatto, nessun null, esattamente un primary contact, lista immutabile, verifica contatto billing e possibilità di richiedere ordini.

---

# 9. `domain.order`

## TransportOrderStatus

Stati: `DRAFT`, `SUBMITTED`, `ACCEPTED`, `REJECTED`, `CANCELLED`. Terminali: rejected e cancelled.

## TransportServiceType

Tipi servizio: standard, express, refrigerated, hazardous, oversized. Ogni tipo dichiara cosa supporta.

## TransportOrder

Richiesta commerciale cliente. Campi: orderNumber, customerAccount, cargoLoad, pickupFacility, deliveryFacility, pickupTimeWindow, deliveryTimeWindow, serviceType, quotedPrice, status, notes. Regole: cliente attivo, pickup/delivery diverse, servizio compatibile col carico, transizioni submit/accept/reject/cancel.

---

# 10. `domain.shipment`

## ShipmentStatus

Stati: `CREATED`, `PLANNED`, `DISPATCHED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`. Terminali: delivered e cancelled.

## Shipment

Spedizione generata da ordine accettato tramite `fromAcceptedOrder`. Non può nascere da ordine draft/submitted/rejected/cancelled. Transizioni: plan, dispatch, markInTransit, deliver, cancel.

## ShipmentRules

Regole: canBePlanned, canBeDispatched, canBeMarkedInTransit, canBeDelivered, canBeCancelled, isTerminal, isCompleted, requiresSpecialHandling. Special handling per ADR, temperatura, internazionale, oversized.

---

# 11. `domain.route`

## RouteStopType

Tipi: start, pickup, delivery, rest break, fuel stop, end. Pickup e delivery sono cargo operation.

## RouteStop

Tappa del percorso con sequenceNumber, type, facility, plannedTimeWindow, notes. Sequence positiva.

## RoutePlan

Piano route con routeNumber, stop ordinati, estimatedDistance, notes. Regole: almeno due stop, primo START, ultimo END, esattamente uno START e uno END, sequence progressive, lista immutabile.

## RoutePlanRules

Regole: hasCargoOperations, hasPickupAndDelivery, isWithinMaxDistance, startsAndEndsAtDifferentFacilities, usesOnlyActiveFacilities, isInternational, isOperationallyUsable.

---

# 12. `domain.fleet`

## VehicleType

Tipi: van, rigid truck, refrigerated truck, tractor unit, semi trailer, refrigerated trailer. Distingue powered unit, trailer, cargo carrying e temperature controlled.

## VehicleStatus

Stati: available, assigned, in maintenance, out of service, retired. Solo available è assegnabile.

## VehicleBodyType

Allestimenti: van body, box, curtain side, refrigerated box, isothermal box, flatbed, low loader, container chassis, tipper, walking floor, silo, tank liquid, tank fuel, tank gas, car transporter, livestock. Alcuni sono tank, alcuni supportano temperatura, alcuni bulk, alcuni open body, alcuni ADR.

## TireSpecification

Specifica pneumatici: brand, model, size, loadIndex, speedRating.

## Vehicle

Mezzo o trailer. Campi: fleetNumber, licensePlate, chassis/VIN, type, bodyType, status, tireSpecification, maxPayload, cargoSpaceDimension, temperatureRange, notes. Regole: plate normalizzata, chassis obbligatorio, cargo vehicle richiede cargo space, non-cargo vehicle non deve averlo, refrigerato richiede temperatureRange, bodyType compatibile col type.

## VehicleCombination

Combinazione assegnabile. Può essere single vehicle oppure powered unit + trailer. Regole: powered unit obbligatoria, trailer opzionale ma deve essere trailer, cargo unit è trailer se presente e cargo, altrimenti powered unit.

## VehicleCombinationRules

Regole: capacità peso, capacità volume, dimensioni carico, supporto temperatura, availability, assegnabilità a cargo load e shipment.

## VehicleBodyCompatibilityRules

Regole carico/allestimento: refrigerato richiede body refrigerato/isotermico, liquidi richiedono tank, fuel richiede tank fuel, gas tank gas, oversized flatbed/low loader, construction material flatbed/tipper/curtain side, livestock body dedicato, car transporter body dedicato.

---

# 13. `domain.driver`

## Patenti e qualifiche

`DriverLicenseCategory`: B, C, E. Scelta: non usiamo C1/C1E; E è estensione rimorchio. Camion rigido richiede C; combinazione con trailer/semitrailer richiede C+E.

`DriverProfessionalQualification`: CQC_GOODS.

`DriverAdrCertificateType`: ADR_BASIC, ADR_TANK, ADR_CLASS_1_EXPLOSIVES, ADR_CLASS_7_RADIOACTIVE.

`DriverOperationalQualification`: temperatura, internazionale, high value, oversized.

## Driver

Campi: driverCode, fullName, status, licenseCategories, professionalQualifications, adrCertificates, operationalQualifications, notes. Set immutabili e helper per patente, CQC e ADR.

## DriverRules

Regole: driver assegnabile, patente necessaria per vehicle combination, CQC merci, ADR basic, ADR tank, ADR class 1, ADR class 7, qualifica temperatura, internazionale e oversized.

---

# 14. `domain.compliance`

## ComplianceRules

Package trasversale. Una missione è compliant solo se driver, vehicle combination, route e shipment sono compatibili. Tiene insieme `DriverRules`, `VehicleCombinationRules`, `VehicleBodyCompatibilityRules`, `RoutePlanRules` e `ShipmentRules`.

---

# 15. `domain.operation`

## TransportMissionStatus

Stati: planned, dispatched, in progress, completed, cancelled. Terminali: completed e cancelled.

## TransportMission

Viaggio operativo reale. Campi: missionNumber, shipment, driver, vehicleCombination, routePlan, status, notes. Creazione solo se assignment compliant. Transizioni: dispatch, start, complete, cancel.

## TransportMissionRules

Regole: canBeDispatched, canBeStarted, canBeCompleted, canBeCancelled, isCompliant, requiresSpecialHandling.

---

# 16. `domain.availability`

## ResourceAvailability

Disponibilità di una risorsa. Tipi risorsa: driver, vehicle, vehicle combination, trailer, facility. Stati: available, reserved, assigned, unavailable, maintenance, on leave. Campi: resourceType, resourceCode, dateRange, timeWindow, status, notes.

## AvailabilityRules

Regole: risorsa disponibile se non ha record bloccanti sovrapposti, verifica disponibilità in periodo, filtra per risorsa, impedisce null in lista.

---

# 17. `domain.tracking`

## TrackingEventType

Tipi: position recorded, departed, arrived, pickup completed, delivery completed, delay reported, incident reported, mission completed.

## TrackingEvent

Evento tracking con eventCode, missionNumber, shipmentNumber, type, occurredAt, coordinate opzionali, notes. POSITION_RECORDED richiede coordinate.

## TrackingTimeline

Lista ordinata di eventi per stessa missione e shipment. Regole: non vuota, stesso missionNumber, stesso shipmentNumber, eventCode unici, ordinata temporalmente.

## TrackingRules

Regole: canAppendEvent, requiresOperationalReview, hasPickupCompleted, hasDeliveryCompleted, isMissionCompleted, hasExceptionEvents.

---

# 18. `domain.maintenance`

## MaintenanceType e Status

Tipi: routine service, safety inspection, tire replacement, repair, refrigeration unit service, ADR tank inspection, breakdown. Stati: open, scheduled, in progress, completed, cancelled.

## MaintenanceWorkOrder

Ordine manutenzione con workOrderNumber, vehicle, type, status, plannedDateRange, notes. Scheduled e in progress richiedono date range. Alcuni tipi bloccano disponibilità mezzo.

## MaintenanceRules

Regole: canBeScheduled, canBeStarted, canBeCompleted, canBeCancelled, blocksVehicleAvailability, shouldMakeVehicleUnavailable, requiresAdrSpecialist, requiresTireService.

---

# 19. `domain.pricing`

## Scelta ViaMichelin/API esterne

Il domain NON chiama ViaMichelin, HERE, PTV o Google. Il domain può rappresentare una stima arrivata da un provider esterno, ma non deve sapere come viene chiamato il servizio.

Architettura corretta futura:

```text
application port: RouteCostEstimator
infrastructure adapter: ViaMichelinRouteCostEstimator
domain object: RouteCostEstimate
```

## CostEstimationSource

Fonti: manual, internal model, ViaMichelin, HERE Maps, PTV, Google Maps, other external provider.

## RouteCostEstimate

Stima costi percorso con estimateCode, source, distance, fuelCost, tollCost, vehicleWearCost, notes. Regole: stessa valuta, total route cost, helper external/manual.

## PricingLineType

Tipi: base freight, distance charge, fuel surcharge, toll charge, vehicle wear charge, ADR surcharge, temperature control surcharge, waiting time charge, handling charge, discount.

## PricingLine

Riga prezzo. Factory per base freight, surcharge, discount e righe derivate da route cost estimate.

## PriceBreakdown

Dettaglio prezzo. Regole: almeno una riga, codici unici, stessa valuta, almeno una riga positiva, sconti sottratti, totale non negativo.

## PricingRules

Regole: presenza base freight, fuel surcharge, toll charge, vehicle wear charge, ADR surcharge, temperature surcharge, international surcharge, discounts.

---

# 20. `domain.billing`

## InvoiceStatus e PaymentMethod

Invoice: draft, issued, paid, cancelled. Paid e cancelled sono terminali. Metodi pagamento: bank transfer, card, cash, direct debit, credit note, other.

## Invoice

Fattura con invoiceNumber, customerCode, shipmentNumber, priceBreakdown, issueDate, dueDate, status, notes. Regole: dueDate >= issueDate, draft -> issued, issued -> paid, draft/issued cancellabili.

## PaymentRecord

Pagamento con paymentNumber, invoiceNumber, amount, method, receivedDate, notes.

## BillingRules

Regole: canBeIssued, canBeMarkedPaid, canBeCancelled, hasReceivableAmount, isOverdue, payment matching invoice, canRegisterPayment, paymentsCoverInvoice.

---

# 21. `domain.document`

## TransportDocumentType

Tipi: CMR waybill, proof of delivery, delivery note, ADR transport document, temperature log, invoice copy, insurance certificate, vehicle registration, driver license copy.

## DocumentStatus

Stati: draft, requested, received, verified, rejected, expired. Verified è usable for operation. Rejected ed expired sono terminali.

## TransportDocument

Documento collegato a una reference. Campi: documentNumber, type, referenceNumber, issueDate, expirationDate, status, notes. Regole: expirable received/verified richiede expirationDate, expirationDate >= issueDate. Transizioni: request, receive, verify, reject, expire.

## DocumentRules

Regole: canBeRequested, canBeReceived, canBeVerified, canBeRejected, canBeExpired, isExpiredOn, isValidForOperation, requiresExpirationDate, containsAdrDocument, containsProofOfDelivery, allDocumentsValidForOperation.

---

# 22. `domain.claim`

## ClaimType, Severity, Status

Tipi: cargo damage, cargo loss, delay, temperature excursion, document dispute, billing dispute, other. Gravità: low, medium, high, critical. Stati: open, under review, accepted, settled, rejected, cancelled.

## TransportClaim

Reclamo con claimNumber, shipmentNumber, customerCode, type, severity, status, requestedCompensation, acceptedCompensation, openedDate, closedDate, notes. Regole: accepted/settled richiedono acceptedCompensation, importo accettato <= richiesto, terminale richiede closedDate, closedDate >= openedDate. Transizioni: startReview, accept, settle, reject, cancel.

## ClaimRules

Regole: canBeReviewed, canBeAccepted, canBeRejected, canBeSettled, canBeCancelled, isOpenForAction, isResolved, requiresUrgentReview, accepted compensation valida.

---

# 23. `domain.audit`

## AuditEvent

Evento audit: eventId, aggregateType, aggregateId, actorType, actorId, actionType, severity, occurredAt, notes. Risponde a: chi ha fatto cosa, quando, su quale oggetto.

Actor type: user, system, integration. Severity: info, warning, error, critical. Action type: created, updated, status changed, assigned, cancelled, deleted, document verified, payment registered, claim settled, external estimate imported, login, login failed, permission denied.

## AuditTrail

Lista eventi per stesso aggregate. Regole: almeno un evento, stesso aggregateType e aggregateId, eventId unici, ordinamento temporale.

## AuditRules

Regole: canAppendEvent, requiresReview, containsSecuritySensitiveEvents, containsFinancialImpactEvents, isChronological.

---

# 24. `domain.notification`

## NotificationMessage

Messaggio notifica con notificationNumber, type, channel, recipientType, recipientReference, priority, subject, body, scheduledAt, sentAt, status, notes.

Tipi: shipment planned/delayed, pickup completed, delivery completed, document requested/verified, invoice issued, payment received, claim updated, maintenance alert, security alert, system alert.

Canali: email, SMS, push, in-app, webhook. Destinatari: customer contact, driver, dispatcher, admin, integration, system. Priorità: low, normal, high, urgent. Stati: draft, scheduled, sent, failed, cancelled.

Regole: scheduled richiede scheduledAt, sent richiede sentAt, sentAt non prima di scheduledAt. Transizioni: schedule, send, fail, cancel.

## NotificationRules

Regole: canBeScheduled, canBeSent, canBeFailed, canBeCancelled, isTerminal, requiresImmediateAttention, shouldNotifyCustomer, isOperationalNotification, isFinancialNotification, isSecurityNotification, usesExternalChannel.

---

# 25. `domain.sustainability`

## FuelType, EmissionStandard, EmissionRating

Fuel: diesel, HVO, LNG, CNG, electric, hydrogen, unknown. Emission standard: Euro 0-6, zero emission, unknown. Rating: low, medium, high, very high.

## EmissionEstimate

Stima emissioni con estimateNumber, shipmentNumber, routeNumber, distance, fuelType, emissionStandard, estimatedEnergyAmount, estimatedCo2Kg, rating, notes. Regole: valori numerici non negativi, zero tailpipe emission, low emission vehicle, high impact.

Nota: fattori emissione e calcoli certificati non stanno nel domain; saranno servizi esterni o application/infrastructure.

## SustainabilityRules

Regole: isLowEmissionTransport, isHighImpactTransport, requiresSustainabilityReview, isZeroTailpipeEmission, hasBetterEmissionStandard, calculateTotalCo2Kg, containsHighImpactEstimate, allEstimatesAreLowEmission.

---

# 26. `domain.identity`

## UserAccount

Account applicativo con accountId, email, displayName, status, roles, permissions, notes. Stati: invited, active, locked, disabled, deleted. Solo active può fare login. Deleted è terminale.

Ruoli: admin, dispatcher, planner, accounting, maintenance, driver, customer, viewer. Permessi: view/manage shipments, operations, fleet, drivers, billing, documents, claims, reports, audit, users, configuration.

Regole: accountId normalizzato, email lowercase valida, almeno un ruolo, ruoli e permessi immutabili. Transizioni: activate, lock, disable, delete.

## IdentityRules

Regole: canLogin, canBeActivated, canBeLocked, canBeDisabled, canBeDeleted, canManageUsers, canViewAudit, canManageConfiguration, canAccessBackOffice, canAccessDriverPortal, canAccessCustomerPortal, canPerformSensitiveAction, requiresStrongAuthentication.

---

# 27. `domain.configuration`

## SystemConfiguration

Configurazione applicativa con configurationKey, category, scope, scopeReference, value, description, active, notes.

Categorie: operation, pricing, notification, document, security, sustainability, reporting, integration. Security e integration sono sensitive. Scope: global, organization, customer, facility, user. Global non richiede reference.

`ConfigurationValue` può essere text, boolean, integer, decimal, percentage, duration minutes. Regole: text max 500, boolean true/false, percentage 0-100, duration >= 0.

Regole config: key uppercase, scopeReference obbligatorio per non-global, sensitive se category sensitive o key contiene PASSWORD/SECRET/TOKEN/API_KEY. Transizioni: activate, deactivate, changeValue.

## ConfigurationRules

Regole: canBeApplied, canOverride, isApplicableTo, isSensitiveConfiguration, requiresRestrictedAccess, isNumericConfiguration, isPricingConfiguration, isSecurityConfiguration.

---

# 28. `domain.reporting`

## ReportDefinition e GeneratedReport

Report types: operations, financial, fleet, driver, customer, sustainability, compliance, claims, documents. Financial e compliance richiedono restricted access.

Formati: PDF, CSV, XLSX, JSON, HTML. Stati: draft, generated, published, archived, failed.

Metriche: shipment count, completed shipment count, delay count, claim count, document expiration count, maintenance count, total distance km, total revenue, total cost, total CO2 kg, vehicle utilization percentage, on-time delivery percentage.

`ReportMetric` contiene metricCode, type, label, value, unit, notes. Value non negativo.

`ReportDefinition` contiene reportCode, type, format, period, requestedByAccountId, notes.

`GeneratedReport` contiene reportNumber, definition, metrics, status, generatedAt, notes. Regole: draft/failed senza metriche, generated/published/archived con metriche e generatedAt, metricCode unici. Transizioni: generate, publish, archive, fail.

## ReportingRules

Regole: canBeGenerated, canBePublished, canBeArchived, canBeFailed, isReadyForPublication, containsFinancialMetrics, containsSustainabilityMetrics, requiresRestrictedAccess, containsMetricType, calculateMetricTotal.

---

# 29. Flussi principali

## Flusso commerciale

```text
CustomerAccount attivo
    ↓
TransportOrder DRAFT
    ↓ submit
SUBMITTED
    ↓ accept
ACCEPTED
    ↓
Shipment.fromAcceptedOrder
```

## Flusso operativo

```text
Shipment CREATED
    ↓ plan
PLANNED
    ↓
TransportMission PLANNED
    ↓ dispatch
DISPATCHED
    ↓ start
IN_PROGRESS
    ↓ complete
COMPLETED
```

## Flusso documentale

```text
TransportDocument DRAFT
    ↓ request
REQUESTED
    ↓ receive
RECEIVED
    ↓ verify
VERIFIED
```

Oppure rejected/expired.

## Flusso billing

```text
PriceBreakdown
    ↓
Invoice DRAFT
    ↓ issue
ISSUED
    ↓ payment
PaymentRecord
    ↓ markPaid
PAID
```

## Flusso claim

```text
TransportClaim OPEN
    ↓ startReview
UNDER_REVIEW
    ↓ accept
ACCEPTED
    ↓ settle
SETTLED
```

Oppure rejected/cancelled.

## Flusso audit

Ogni azione importante può generare un `AuditEvent`: ordine creato, shipment planned, mission dispatched, documento verificato, pagamento registrato, claim settled, login fallito, permission denied, stima costi esterna importata.

## Flusso notification

```text
DRAFT → SCHEDULED → SENT
DRAFT/SCHEDULED → FAILED
DRAFT/SCHEDULED → CANCELLED
```

## Flusso reporting

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

---

# 30. Regole trasversali importanti

## Immutabilità

Molte classi sono immutable. Le transizioni non modificano l'oggetto esistente, ma restituiscono una nuova istanza. Questo riduce bug e rende più semplice testare.

## Oggetti sempre validi

Gli oggetti domain non devono esistere in stato invalido. Esempi: non esiste invoice con dueDate prima di issueDate, non esiste vehicle cargo senza cargo space, non esiste report generated senza metriche.

## Liste e set immutabili

Liste e set interni vengono copiati con `List.copyOf`, `Set.copyOf` o stream `.toList()`.

## Codici normalizzati

Molti codici vengono normalizzati uppercase: `shp-001` diventa `SHP-001`.

## Rules statiche

Ogni package importante ha una classe `Rules` per centralizzare regole che coinvolgono più oggetti.

---

# 31. Cosa NON aggiungere nel domain

Non aggiungere nel domain:

```text
@Autowired
@Service
@Repository
@Entity
@Table
@Column
RestController
HttpClient
File
Path
InputStream
JSON parser
database query
API key
password hash reale
JWT
email sender
Google Maps client
ViaMichelin client
```

---

# 32. Prossimi step consigliati

## 32.1 Application layer

Creare package:

```text
it.gabriele.truckflow.application
```

Use case possibili:

```text
CreateTransportOrderUseCase
SubmitTransportOrderUseCase
AcceptTransportOrderUseCase
CreateShipmentFromOrderUseCase
PlanShipmentUseCase
CreateTransportMissionUseCase
DispatchMissionUseCase
CompleteMissionUseCase
GenerateInvoiceUseCase
RegisterPaymentUseCase
GenerateReportUseCase
```

## 32.2 Porte application

Esempi:

```java
public interface TransportOrderRepository {
    TransportOrder save(TransportOrder order);
    Optional<TransportOrder> findByOrderNumber(String orderNumber);
}
```

```java
public interface RouteCostEstimator {
    RouteCostEstimate estimate(RoutePlan routePlan, VehicleCombination combination);
}
```

```java
public interface NotificationSender {
    void send(NotificationMessage message);
}
```

## 32.3 Infrastructure layer

Package futuro:

```text
it.gabriele.truckflow.infrastructure
```

Conterrà repository in memoria, repository JPA, adapter ViaMichelin/HERE/PTV/Google, adapter email, adapter file storage e adapter security.

## 32.4 Web/API layer

Package futuro:

```text
it.gabriele.truckflow.web
```

Controller futuri: TransportOrderController, ShipmentController, MissionController, DriverController, VehicleController, InvoiceController, ReportController.

---

# 33. Package opzionali futuri

## organization

Rappresenta l'azienda che usa il gestionale: company profile, VAT number, headquarters, branches, departments.

## carrier

Rappresenta vettori esterni/subcontractor: carrier company, carrier contract, carrier compliance, subcontracted mission.

## planning avanzato

Rappresenta algoritmi o suggerimenti: driver candidates, vehicle candidates, optimization score, planning recommendation.

---

# 34. Checklist finale domain MVP

Fatto:

- shared value objects;
- cargo e ADR;
- location;
- facility;
- customer;
- order;
- shipment;
- route;
- fleet;
- driver;
- compliance;
- operation;
- availability;
- tracking;
- maintenance;
- pricing;
- billing;
- document;
- claim;
- audit;
- notification;
- sustainability;
- identity;
- configuration;
- reporting.

Da fare:

- salvare questa documentazione in `docs/domain-reference.md` oppure `docs/domain-reference-complete.md`;
- eseguire `mvn clean test`;
- committare la documentazione;
- iniziare application layer.

Comandi:

```bash
mvn clean test
git status
git add docs/
git commit -m "Document domain model"
git push origin main
```

---

# 35. Riassunto finale

TruckFlow Manager ora ha un domain model molto ampio e realistico. Copre la parte commerciale, operativa, mezzi/autisti, ADR, documentale, economica, audit/security, reporting, sostenibilità e configurazione.

Il prossimo grande passo non è aggiungere domain a caso, ma iniziare a usare questo domain tramite application layer e use case.

