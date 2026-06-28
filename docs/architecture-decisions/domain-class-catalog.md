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


# Domain Class Catalog

## Scopo

Catalogo delle classi attuali del domain MVP.

Legenda:

- **Entity**: oggetto con identità propria.
- **Value Object**: oggetto senza identità, valido per i suoi valori.
- **Enum**: insieme chiuso di valori.
- **Domain Service**: classe di regole che coinvolge più oggetti.
- **Aggregate**: radice/aggregato concettuale.


## shared

Value Object condivisi.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| Weight | Value Object | Peso in kg; factory da kg/tonnellate; non negativo. |
| Distance | Value Object | Distanza in km; factory da km/metri; non negativa. |
| Volume | Value Object | Volume in m³; factory da m³/litri; non negativo. |
| Dimension | Value Object | Lunghezza, larghezza, altezza in metri; calcola volume; verifica fitsInside senza rotazione. |
| Money | Value Object | Importo con valuta; BigDecimal; add/subtract/compare solo stessa valuta. |
| Percentage | Value Object | Percentuale 0-100; conversione in moltiplicatore. |
| TemperatureRange | Value Object | Range temperatura Celsius; contains e isCoveredBy. |
| TimeWindow | Value Object | Finestra oraria LocalTime; start prima di end; overlap end-touch escluso. |
| DateRange | Value Object | Intervallo LocalDate; start <= end; overlap inclusivo; giorni inclusivi. |
| Notes | Value Object | Note testuali trim; max 2000; vuoto consentito; contains case-insensitive. |

## cargo

Carichi, merci e ADR.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| CargoCategory | Enum | Categorie merce: general, food, refrigerated, pharma, fragile, ADR, oversized, liquid, electronics, construction. |
| CargoItem | Value Object | Singolo item di carico con peso, dimensioni, categoria, temperatura opzionale, dangerous goods opzionale. |
| CargoLoad | Value Object | Lista immutabile di CargoItem; calcola peso/volume; rileva temperatura e ADR. |
| CargoLoadRules | Domain Service | Regole peso, volume, fit dimensionale, temperatura, ADR, fragile e oversized. |
| AdrClass | Enum | Classi ADR da 1 a 9, inclusi esplosivi, gas, liquidi infiammabili, radioattivi. |
| PackingGroup | Enum | Gruppo di imballaggio ADR I, II, III. |
| HazardLabel | Enum | Etichette di pericolo ADR. |
| DangerousGoodsProfile | Value Object | UN number, proper shipping name, ADR class, labels, tunnel code, transport category, tank flag. |

## location

Indirizzi, coordinate e luoghi.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| GeoCoordinates | Value Object | Latitudine/longitudine valide. |
| Address | Value Object | Indirizzo con countryCode ISO-like a 2 lettere e coordinate opzionali. |
| Location | Value Object | Nome, Address e ZoneId. |

## facility

Punti operativi.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| FacilityType | Enum | Warehouse, depot, customer site, supplier site, terminal, port, airport, maintenance center. |
| Facility | Entity | Codice, tipo, location, orari, stato attivo, note. |

## customer

Clienti e contatti.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| CustomerType | Enum | Individual, company, public authority, internal. |
| CustomerStatus | Enum | Active, inactive, suspended. |
| Customer | Entity | Cliente commerciale con codice, ragione sociale, tipo, stato, sede principale. |
| CustomerContactRole | Enum | Logistics, administration, billing, operations, sales, management, other. |
| CustomerContact | Value Object | Contatto cliente con email/phone validati e flag primary. |
| CustomerAccount | Aggregate | Customer + contatti; richiede almeno un contatto e uno solo primary. |

## order

Richieste commerciali.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| TransportOrderStatus | Enum | Draft, submitted, accepted, rejected, cancelled. |
| TransportServiceType | Enum | Standard, express, refrigerated, hazardous, oversized. |
| TransportOrder | Entity | Richiesta cliente con cargo, pickup, delivery, finestre orarie, servizio, prezzo, stato. |

## shipment

Spedizioni.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| ShipmentStatus | Enum | Created, planned, dispatched, in_transit, delivered, cancelled. |
| Shipment | Entity | Spedizione generata da TransportOrder accepted; transizioni plan/dispatch/inTransit/deliver/cancel. |
| ShipmentRules | Domain Service | Regole transizioni, terminalità, completed, special handling. |

## route

Piani percorso.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| RouteStopType | Enum | Start, pickup, delivery, rest break, fuel stop, end. |
| RouteStop | Value Object | Tappa con sequence, type, facility, time window. |
| RoutePlan | Value Object | Route con stop ordinati, distanza stimata e regole start/end. |
| RoutePlanRules | Domain Service | Cargo operations, max distance, facilities attive, internazionale, usable. |

## fleet

Flotta e combinazioni.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| VehicleType | Enum | Van, rigid truck, refrigerated truck, tractor unit, semi trailer, refrigerated trailer. |
| VehicleStatus | Enum | Available, assigned, in maintenance, out of service, retired. |
| VehicleBodyType | Enum | Box, curtain side, refrigerated, flatbed, low-loader, tanks, silo, tipper, livestock, car transporter. |
| TireSpecification | Value Object | Brand, model, size, load index, speed rating. |
| Vehicle | Entity | Mezzo/trailer con plate, VIN, type, body, status, tires, payload, dimensions, temperature. |
| VehicleCombination | Entity | Single vehicle o powered unit + trailer; unità assegnabile a shipment/mission. |
| VehicleCombinationRules | Domain Service | Peso, volume, dimensioni, temperatura, assegnabilità. |
| VehicleBodyCompatibilityRules | Domain Service | Compatibilità tra categoria carico/ADR e allestimento mezzo. |

## driver

Autisti, patenti e qualifiche.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| DriverLicenseCategory | Enum | B, C, E. E è estensione rimorchio; C+E per combinazioni con trailer/semitrailer. |
| DriverProfessionalQualification | Enum | CQC_GOODS / code 95. |
| DriverAdrCertificateType | Enum | ADR basic, tank, class 1 explosives, class 7 radioactive. |
| DriverOperationalQualification | Enum | Temperature, international, high-value, oversized. |
| DriverStatus | Enum | Available, assigned, on leave, suspended, inactive. |
| Driver | Entity | Autista con codici, stato, patenti, CQC, ADR, qualifiche operative. |
| DriverRules | Domain Service | Patente, CQC, ADR, tank, class 1, class 7, temperatura, internazionale, oversized. |

## compliance

Conformità trasversale.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| ComplianceRules | Domain Service | Verifica compatibilità driver + vehicle combination + route + shipment. |

## operation

Missioni operative.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| TransportMissionStatus | Enum | Planned, dispatched, in progress, completed, cancelled. |
| TransportMission | Entity | Viaggio operativo reale con shipment, driver, vehicle combination e route. |
| TransportMissionRules | Domain Service | Dispatch/start/complete/cancel e compliance missione. |

## availability

Disponibilità risorse.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| AvailabilityResourceType | Enum | Driver, vehicle, vehicle combination, trailer, facility. |
| AvailabilityStatus | Enum | Available, reserved, assigned, unavailable, maintenance, on leave. |
| ResourceAvailability | Value Object | Disponibilità risorsa su DateRange + TimeWindow. |
| AvailabilityRules | Domain Service | Disponibilità e conflitti tra slot/record. |

## tracking

Eventi e tracking.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| TrackingEventType | Enum | Position, departed, arrived, pickup/delivery completed, delay, incident, mission completed. |
| TrackingEvent | Entity | Evento tracking con mission, shipment, type, occurredAt, coordinate opzionali. |
| TrackingTimeline | Value Object | Lista ordinata eventi stessa missione/spedizione, eventCode unici. |
| TrackingRules | Domain Service | Append, review, pickup/delivery completed, exception events. |

## maintenance

Manutenzione.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| MaintenanceType | Enum | Routine, safety inspection, tires, repair, refrigeration, ADR tank inspection, breakdown. |
| MaintenanceStatus | Enum | Open, scheduled, in progress, completed, cancelled. |
| MaintenanceWorkOrder | Entity | Ordine manutenzione su veicolo, con date range quando richiesto. |
| MaintenanceRules | Domain Service | Schedule/start/complete/cancel e blocco disponibilità. |

## pricing

Prezzi e stime costo.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| CostEstimationSource | Enum | Manual, internal model, ViaMichelin, HERE, PTV, Google, other. |
| PricingLineType | Enum | Base freight, distance, fuel, tolls, wear, ADR, temperature, waiting, handling, discount. |
| RouteCostEstimate | Value Object | Distanza, fuel cost, toll cost, vehicle wear cost, source. |
| PricingLine | Value Object | Riga prezzo positiva o discount; factory da route cost estimate. |
| PriceBreakdown | Value Object | Righe prezzo, stessa valuta, codici unici, totale finale. |
| PricingRules | Domain Service | Presenza surcharge/discount e regole ADR/temperatura/internazionale. |

## billing

Fatture e pagamenti.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| InvoiceStatus | Enum | Draft, issued, paid, cancelled. |
| PaymentMethod | Enum | Bank transfer, card, cash, direct debit, credit note, other. |
| Invoice | Entity | Fattura legata a customer, shipment e PriceBreakdown. |
| PaymentRecord | Entity | Pagamento su invoice con amount, method, receivedDate. |
| BillingRules | Domain Service | Issue, paid, cancel, overdue, matching payment, cover invoice. |

## document

Documenti trasporto.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| TransportDocumentType | Enum | CMR, POD, delivery note, ADR doc, temperature log, invoice copy, insurance, registration, driver license copy. |
| DocumentStatus | Enum | Draft, requested, received, verified, rejected, expired. |
| TransportDocument | Entity | Documento con referenceNumber, issue/expiration date e stato. |
| DocumentRules | Domain Service | Request/receive/verify/reject/expire e validità operativa. |

## claim

Reclami.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| ClaimType | Enum | Damage, loss, delay, temperature, document dispute, billing dispute, other. |
| ClaimSeverity | Enum | Low, medium, high, critical. |
| ClaimStatus | Enum | Open, under review, accepted, settled, rejected, cancelled. |
| TransportClaim | Entity | Reclamo con importo richiesto/accettato e date apertura/chiusura. |
| ClaimRules | Domain Service | Review, accept, reject, settle, cancel, urgent review. |

## audit

Audit trail.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| AuditActorType | Enum | User, system, integration. |
| AuditSeverity | Enum | Info, warning, error, critical. |
| AuditActionType | Enum | Created, updated, status changed, document verified, payment, claim, login, denied. |
| AuditEvent | Entity | Chi ha fatto cosa, quando e su quale aggregate. |
| AuditTrail | Value Object | Eventi ordinati dello stesso aggregate. |
| AuditRules | Domain Service | Append, review, security/financial impact, chronological. |

## notification

Notifiche.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| NotificationType | Enum | Shipment, document, invoice, payment, claim, maintenance, security, system. |
| NotificationChannel | Enum | Email, SMS, push, in-app, webhook. |
| NotificationRecipientType | Enum | Customer contact, driver, dispatcher, admin, integration, system. |
| NotificationPriority | Enum | Low, normal, high, urgent. |
| NotificationStatus | Enum | Draft, scheduled, sent, failed, cancelled. |
| NotificationMessage | Entity | Messaggio con canale, destinatario, subject/body e stato. |
| NotificationRules | Domain Service | Schedule/send/fail/cancel e categorie notifica. |

## sustainability

Emissioni e sostenibilità.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| FuelType | Enum | Diesel, HVO, LNG, CNG, electric, hydrogen, unknown. |
| EmissionStandard | Enum | Euro 0-6, zero emission, unknown. |
| EmissionRating | Enum | Low, medium, high, very high. |
| EmissionEstimate | Value Object | Stima CO2/energia su shipment + route. |
| SustainabilityRules | Domain Service | Low emission, high impact, review, zero tailpipe, totale CO2. |

## identity

Account, ruoli e permessi.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| UserAccountStatus | Enum | Invited, active, locked, disabled, deleted. |
| UserRole | Enum | Admin, dispatcher, planner, accounting, maintenance, driver, customer, viewer. |
| UserPermission | Enum | Permessi view/manage su shipments, operations, fleet, billing, docs, claims, audit, users, config. |
| UserAccount | Entity | Account applicativo separato da Driver/Customer. |
| IdentityRules | Domain Service | Login, lifecycle account, accessi portali, azioni sensibili. |

## configuration

Configurazioni applicative.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| ConfigurationCategory | Enum | Operation, pricing, notification, document, security, sustainability, reporting, integration. |
| ConfigurationScope | Enum | Global, organization, customer, facility, user. |
| ConfigurationValueType | Enum | Text, boolean, integer, decimal, percentage, duration minutes. |
| ConfigurationValue | Value Object | Valore tipizzato e validato. |
| SystemConfiguration | Entity | Configurazione con key, category, scope, value, active. |
| ConfigurationRules | Domain Service | Apply, override, scope, sensitive/restricted access. |

## reporting

Report e metriche.

| Classe | Tipo | Responsabilità |
| --- | --- | --- |
| ReportType | Enum | Operations, financial, fleet, driver, customer, sustainability, compliance, claims, documents. |
| ReportFormat | Enum | PDF, CSV, XLSX, JSON, HTML. |
| ReportStatus | Enum | Draft, generated, published, archived, failed. |
| ReportMetricType | Enum | Shipment count, revenue, cost, CO2, utilization, on-time delivery, ecc. |
| ReportMetric | Value Object | Metrica numerica non negativa con unità. |
| ReportDefinition | Value Object | Definizione report con type, format, period, requester. |
| GeneratedReport | Entity | Report generato con metriche, stato e generatedAt. |
| ReportingRules | Domain Service | Generate, publish, archive, fail, totals, restricted access. |
