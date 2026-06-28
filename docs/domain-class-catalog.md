# TruckFlow Manager — Domain Class Catalog

## Scopo

Questo documento descrive il catalogo delle classi previste nel dominio.

Per ogni classe vengono indicati:

- nome;
- tipo: Entity, Value Object, Enum o Domain Service;
- responsabilità;
- attributi concettuali;
- relazioni/note.

Questo documento non è codice Java definitivo.  
Serve come guida per implementare il dominio in modo graduale.

---

## Legenda

### Entity

Oggetto con identità propria.

Esempi:

- `Customer`;
- `Driver`;
- `VehicleCombination`;
- `Shipment`;
- `TransportMission`.

### Value Object

Oggetto senza identità propria. Conta per il valore.

Esempi:

- `Money`;
- `Weight`;
- `Address`;
- `Dimension`.

### Enum

Insieme chiuso di valori.

Esempi:

- `ShipmentStatus`;
- `DriverStatus`;
- `VehicleStatus`.

### Domain Service

Servizio di dominio per una regola che coinvolge più oggetti.

Esempi:

- `WeightComplianceCheck`;
- `DriverVehicleEligibilityCheck`.

---

# organization

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Organization` | Entity | Azienda che usa il gestionale | id, legalName, tradeName, vatNumber, taxCode, headquartersAddress, contactInfo, status, createdAt, updatedAt | ha Branch, Department, Employee |
| `Branch` | Entity | Sede o filiale | id, organizationId, name, address, contactInfo, branchType, active, createdAt | appartiene a Organization |
| `Department` | Entity | Reparto interno | id, organizationId, name, description, active | appartiene a Organization |
| `Employee` | Entity | Dipendente interno non autista | id, firstName, lastName, email, phone, departmentId, jobTitle, status, hiredAt, active | può collegarsi a UserAccount |

# customer

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Customer` | Entity | Cliente persona fisica o azienda | id, customerType, firstName, lastName, companyName, vatNumber, taxCode, mainAddress, contactInfo, status, notes, createdAt, updatedAt | ha ordini, spedizioni, contratti |
| `CustomerType` | Enum | Tipo cliente | PERSON, COMPANY | usato da Customer |
| `CustomerStatus` | Enum | Stato cliente | ACTIVE, SUSPENDED, INACTIVE | usato da Customer |
| `ContactInfo` | Value Object | Contatti | email, phoneNumber, mobilePhoneNumber, pec, contactPersonName, contactPersonRole, notes | usato da Customer, Branch, Facility |
| `CustomerContract` | Entity | Contratto cliente | id, customerId, contractNumber, validFrom, validTo, paymentTerms, serviceLevelAgreement, defaultDiscount, notes, active | collegato a Customer |
| `PaymentTerms` | Value Object | Condizioni pagamento | paymentMethod, dueDays, requiresAdvancePayment, invoiceEmail, notes | usato da CustomerContract |
| `ServiceLevelAgreement` | Value Object | Accordi di servizio | priorityLevel, requiresTracking, requiresProofOfDelivery, maxAllowedDelay, defaultDeliveryTimeWindow, notes | usato da CustomerContract |

# order

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `TransportOrder` | Entity | Richiesta iniziale del cliente | id, orderNumber, customerId, requestedCargo, pickupLocation, deliveryLocation, requestedPickupWindow, requestedDeliveryWindow, priority, serviceType, status, customerNotes, createdAt, updatedAt, acceptedAt, cancelledAt | genera Quote e Shipment |
| `TransportOrderStatus` | Enum | Stato ordine | REQUESTED, QUOTED, ACCEPTED, REJECTED, PLANNED, CANCELLED | usato da TransportOrder |
| `TransportOrderPriority` | Enum | Priorità ordine | LOW, NORMAL, HIGH, URGENT | usato da TransportOrder |
| `ServiceType` | Enum | Tipo servizio | STANDARD, EXPRESS, REFRIGERATED, HAZARDOUS_GOODS, FRAGILE_GOODS, INTERNATIONAL | usato da TransportOrder |

# pricing

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `TransportQuote` | Entity | Preventivo cliente | id, quoteNumber, transportOrderId, customerId, totalPrice, costBreakdown, status, validFrom, validUntil, createdAt, sentAt, acceptedAt, rejectedAt, notes | collegato a TransportOrder |
| `QuoteStatus` | Enum | Stato preventivo | DRAFT, SENT, ACCEPTED, REJECTED, EXPIRED | usato da TransportQuote |
| `CostBreakdown` | Value Object | Dettaglio costi | items, subtotal, discounts, additionalCharges, total, currency | usato da TransportQuote |
| `CostItem` | Value Object | Voce costo | costType, description, amount, quantity, unitPrice, notes | parte di CostBreakdown |
| `CostType` | Enum | Tipo costo | FUEL, TOLL, DRIVER, MAINTENANCE_ESTIMATE, REFRIGERATION, HAZARDOUS_GOODS_EXTRA, URGENCY_EXTRA, WAITING_TIME, COMPANY_MARGIN, DISCOUNT, OTHER | usato da CostItem |
| `RouteCostEstimate` | Value Object | Stima costo tratta | estimatedDistance, estimatedTravelTime, estimatedFuelCost, estimatedTollCost, totalEstimatedRouteCost | usato nel pricing |
| `TollCostEstimate` | Value Object | Stima pedaggi | routeId, estimatedAmount, currency, provider, calculatedAt, confidenceLevel | integrazione futura |
| `FuelCostEstimate` | Value Object | Stima carburante | estimatedLiters, fuelPricePerLiter, totalFuelCost, consumptionUsed, calculatedAt | integrazione futura |
| `DriverCostEstimate` | Value Object | Stima costo autista | estimatedDrivingHours, estimatedWorkingHours, hourlyCost, totalDriverCost, notes | usato nel pricing |
| `AdditionalCharge` | Value Object | Maggiorazione | reason, amount, taxable, notes | usato da CostBreakdown |
| `Discount` | Value Object | Sconto | reason, percentage, amount, notes | usato da CostBreakdown |

# billing

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Invoice` | Entity | Fattura | id, invoiceNumber, customerId, shipmentId, issueDate, dueDate, totalAmount, status, billingDocument, notes | collegata a Customer e Shipment |
| `InvoiceStatus` | Enum | Stato fattura | DRAFT, ISSUED, PAID, PARTIALLY_PAID, OVERDUE, CANCELLED | usato da Invoice |
| `Payment` | Entity | Pagamento | id, invoiceId, amount, paidAt, paymentMethod, transactionReference, status, notes | collegato a Invoice |
| `PaymentStatus` | Enum | Stato pagamento | PENDING, COMPLETED, FAILED, REFUNDED, CANCELLED | usato da Payment |
| `BillingDocument` | Value Object | Documento contabile | documentNumber, documentDate, attachmentReference, notes | usato da Invoice |

# driver

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Driver` | Entity | Autista professionale | id, firstName, lastName, birthDate, phoneNumber, email, license, professionalQualifications, status, availability, medicalChecks, tachographCard, workProfile, hiredAt, active, notes | assegnato a Shipment e Mission |
| `DriverStatus` | Enum | Stato autista | AVAILABLE, ASSIGNED, ON_DELIVERY, UNAVAILABLE, ON_LEAVE, SUSPENDED | usato da Driver |
| `DriverLicense` | Value Object | Patente | licenseNumber, categories, issuedAt, expiresAt, issuingCountry, restrictions, notes | usato da Driver |
| `LicenseCategory` | Enum | Categoria patente | B, BE, C1, C1E, C, CE | usato da DriverLicense |
| `ProfessionalQualification` | Entity/Value Object | Qualifica professionale | id, type, certificateNumber, issuedAt, expiresAt, issuingCountry, status, notes | CQC, ADR, ATP |
| `ProfessionalQualificationType` | Enum | Tipo qualifica | CQC_GOODS, ADR_BASIC, ADR_TANK, ADR_EXPLOSIVES, ADR_RADIOACTIVE, ATP_REFRIGERATED_TRANSPORT | usato da ProfessionalQualification |
| `DriverQualification` | Enum | Competenza operativa | HAZARDOUS_GOODS, REFRIGERATED_TRANSPORT, HEAVY_TRUCK, INTERNATIONAL_TRANSPORT, FRAGILE_GOODS_HANDLING | usato da Driver |
| `DriverAvailability` | Entity | Disponibilità autista | id, driverId, availableFrom, availableTo, reason, notes | collegata a Driver |
| `DriverMedicalCheck` | Entity | Controllo medico | id, driverId, checkDate, expiresAt, result, notes, attachmentReference | collegato a Driver |
| `TachographCard` | Value Object | Carta tachigrafica | cardNumber, issuedAt, expiresAt, issuingCountry, status | usata da Driver |
| `DriverWorkProfile` | Value Object | Preferenze lavoro autista | preferredWorkingArea, acceptsInternationalRoutes, acceptsNightDriving, maxPreferredDailyHours, notes | usato da Driver |

# fleet

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Vehicle` | Entity astratta | Mezzo generico | id, licensePlate, brand, model, year, legalCategory, status, insurancePolicy, active, notes | base concettuale |
| `MotorVehicle` | Entity astratta | Mezzo con motore | id, licensePlate, brand, model, year, currentKilometers, motorVehicleType, fuelConsumption, status, couplingCompatibility, notes | base per Van/Truck |
| `Van` | Entity | Furgone tipo Ducato/Daily/Sprinter | id, licensePlate, brand, model, year, legalCategory, bodyType, capacityProfile, cargoSpace, fuelConsumption, status, currentKilometers | può stare in VehicleCombination |
| `Truck` | Entity | Camion/motrice/trattore | id, licensePlate, brand, model, year, legalCategory, motorVehicleType, bodyType, weightProfile, axleConfiguration, capacityProfile, cargoSpace, couplingCompatibility, fuelConsumption, status, currentKilometers | può stare in VehicleCombination |
| `Trailer` | Entity | Rimorchio/semirimorchio | id, licensePlate, brand, model, year, legalCategory, trailerType, bodyType, capacityProfile, cargoSpace, weightProfile, axleConfiguration, couplingCompatibility, status, notes | può stare in VehicleCombination |
| `VehicleCombination` | Entity | Combinazione usata per spedizione/missione | id, type, motorVehicle, trailer, combinedCapacityProfile, combinedWeightProfile, status, createdAt, notes | assegnata a Shipment/Mission |
| `VehicleLegalCategory` | Enum | Categoria legale veicolo | N1, N2, N3, O1, O2, O3, O4 | usata da Vehicle |
| `MotorVehicleType` | Enum | Tipo mezzo motore | VAN, LIGHT_TRUCK, RIGID_TRUCK, TRACTOR_UNIT, ROAD_TRACTOR | usato da MotorVehicle |
| `TrailerType` | Enum | Tipo rimorchio | SEMI_TRAILER, FULL_TRAILER, CENTRE_AXLE_TRAILER, DRAWBAR_TRAILER | usato da Trailer |
| `BodyType` | Enum | Allestimento | PANEL_VAN, BOX_BODY, CURTAIN_SIDE, REFRIGERATED, INSULATED, TANK, FLATBED, TIPPER, CONTAINER_CHASSIS, LOW_LOADER, CAR_TRANSPORTER, WALKING_FLOOR, SILO, LIVESTOCK, OPEN_BODY | usato da Van/Truck/Trailer |
| `VehicleCombinationType` | Enum | Tipo combinazione | VAN_ONLY, RIGID_TRUCK_ONLY, RIGID_TRUCK_WITH_TRAILER, TRACTOR_WITH_SEMI_TRAILER, TRUCK_AND_DRAWBAR_TRAILER, ROAD_TRAIN | usato da VehicleCombination |
| `VehicleStatus` | Enum | Stato mezzo | AVAILABLE, ASSIGNED, IN_MAINTENANCE, OUT_OF_SERVICE, RESERVED | usato da veicoli |
| `CapacityProfile` | Value Object | Capacità operativa | maxPayloadWeight, maxGrossWeight, usableVolume, maxPalletPositions, usableLength, usableWidth, usableHeight | usato da veicoli e combinazione |
| `CargoSpace` | Value Object | Vano di carico | internalLength, internalWidth, internalHeight, usableVolume, palletCapacity, bodyType, temperatureControlled, temperatureRange | usato da veicoli |
| `TruckWeightProfile` | Value Object | Profilo pesi camion | emptyWeight, maxGrossWeight, maxPayload, maxTowableWeight, maxCombinationWeight | usato da Truck |
| `TrailerWeightProfile` | Value Object | Profilo pesi rimorchio | emptyWeight, maxGrossWeight, maxPayload, maxAxleLoad | usato da Trailer |
| `CombinedWeightProfile` | Value Object | Profilo pesi combinato | totalEmptyWeight, totalMaxGrossWeight, totalPayload, totalAxleLimitSummary | usato da VehicleCombination |
| `AxleConfiguration` | Value Object | Configurazione assi | numberOfAxles, maxWeightPerAxle, axleDistances, notes | usato da Truck/Trailer |
| `CouplingCompatibility` | Value Object | Compatibilità aggancio | couplingType, maxTowableWeight, compatibleTrailerTypes, notes | usato da Truck/Trailer |
| `FuelConsumption` | Value Object | Consumo carburante | litersPer100KmEmpty, litersPer100KmLoaded, fuelType, notes | usato da MotorVehicle |
| `VehicleAssignment` | Entity | Storico assegnazione mezzo | id, vehicleId, driverId, assignedFrom, assignedTo, reason, notes | storico |
| `OdometerReading` | Entity | Lettura chilometri | id, vehicleId, kilometers, recordedAt, source, notes | storico |
| `InsurancePolicy` | Entity | Polizza assicurativa | id, policyNumber, provider, validFrom, validTo, coverageType, notes | collegata a veicolo |

# maintenance

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `MaintenanceRecord` | Entity | Manutenzione | id, vehicleId, maintenanceDate, kilometersAtMaintenance, type, description, cost, workshopName, nextDueDate, nextDueKilometers, status | collegata a Vehicle |
| `MaintenanceType` | Enum | Tipo manutenzione | OIL_CHANGE, TYRE_REPLACEMENT, BRAKE_CHECK, ENGINE_REPAIR, INSPECTION, GENERAL_SERVICE, REFRIGERATION_SYSTEM_CHECK, TACHOGRAPH_CHECK | usato da MaintenanceRecord |
| `MaintenanceSchedule` | Entity | Programmazione manutenzione | id, vehicleId, plannedDate, plannedKilometers, type, status, notes | collegata a Vehicle |
| `InspectionRecord` | Entity | Revisione/ispezione | id, vehicleId, inspectionDate, expiresAt, result, notes, attachmentReference | collegata a Vehicle |
| `MaintenanceStatus` | Enum | Stato manutenzione | PLANNED, COMPLETED, CANCELLED, OVERDUE | usato da MaintenanceRecord |

# cargo

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Cargo` | Entity | Carico trasportato | id, description, cargoType, totalWeight, totalVolume, dimensions, palletCount, packageCount, packagingType, declaredValue, requirements, items, notes | collegato a Shipment |
| `CargoItem` | Entity/Value Object | Elemento di carico | id, description, quantity, singleItemWeight, singleItemVolume, singleItemDimension, packagingType, stackable, notes | parte di Cargo |
| `CargoType` | Enum | Tipo merce | GENERAL_GOODS, FOOD, REFRIGERATED_GOODS, HAZARDOUS_GOODS, FRAGILE_GOODS, LIQUID, CONSTRUCTION_MATERIAL, MACHINERY, PHARMACEUTICAL, HIGH_VALUE_GOODS, BULK_GOODS | usato da Cargo |
| `PackagingType` | Enum | Tipo imballaggio | PALLET, BOX, CONTAINER, BAG, BARREL, LOOSE, CRATE | usato da Cargo |
| `CargoRequirement` | Value Object | Requisiti carico | requiresRefrigeration, temperatureRange, requiresAdr, requiresCoveredVehicle, requiresTailLiftAtSite, fragile, requiresSecureParking, requiresTank, requiresFlatbed, requiresCurtainSide, documentsRequired | usato da Cargo |
| `HandlingInstruction` | Value Object | Istruzioni movimentazione | stackable, keepUpright, protectFromSun, forkliftRequired, notes | usato da Cargo |
| `HazardousMaterialInfo` | Value Object | Dati merce pericolosa | unNumber, hazardClass, packingGroup, riskDescription, safetyInstructions, documentsRequired | usato da Cargo |
| `LoadUnit` | Value Object | Unità logistica | unitType, quantity, weight, volume, dimension | parte di Cargo |
| `CargoDimension` | Value Object | Dimensioni carico | length, width, height, unit | usato da CargoItem/Cargo |

# route

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Route` | Value Object/Entity | Percorso | id, origin, destination, stops, estimatedDistance, estimatedDuration, restrictions, notes | usato da Shipment/Mission |
| `RouteLeg` | Value Object | Tratto percorso | from, to, distance, estimatedDuration, restrictions, notes | parte di Route |
| `RouteStop` | Value Object | Fermata | location, stopOrder, stopType, plannedArrival, plannedDeparture, expectedDuration, notes | parte di Route |
| `StopType` | Enum | Tipo fermata | PICKUP, DELIVERY, INTERMEDIATE_STOP, FUEL_STOP, REST_STOP, LOADING_WAIT, UNLOADING_WAIT, BORDER_STOP | usato da RouteStop |
| `DistanceEstimate` | Value Object | Stima distanza | distance, calculatedAt, source, confidenceLevel | usato da Route |
| `TravelTimeEstimate` | Value Object | Stima durata | duration, calculatedAt, source, confidenceLevel | usato da Route |

# shipment

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Shipment` | Entity | Spedizione cliente | id, shipmentNumber, transportOrderId, customerId, cargo, route, assignedDriverId, assignedVehicleCombinationId, status, schedule, price, notes, createdAt, updatedAt | centro operativo |
| `ShipmentStatus` | Enum | Stato spedizione | CREATED, ASSIGNED, PLANNED, IN_TRANSIT, DELIVERED, CANCELLED, FAILED_DELIVERY, RETURNED | usato da Shipment |
| `ShipmentAssignment` | Entity | Assegnazione autista/mezzo | id, shipmentId, driverId, vehicleCombinationId, assignedAt, assignedBy, notes | collegata a Shipment |
| `ShipmentSchedule` | Value Object | Pianificazione spedizione | plannedPickupWindow, plannedDeliveryWindow, actualPickupTime, actualDeliveryTime, estimatedArrivalTime | usato da Shipment |
| `PickupSiteRequirement` | Value Object | Requisiti sito ritiro | appointmentRequired, hasForklift, hasLoadingDock, accessRestrictions, contactPerson, estimatedWaitingTime, notes | usato da Shipment |
| `DeliverySiteRequirement` | Value Object | Requisiti sito consegna | appointmentRequired, hasForklift, hasLoadingDock, accessRestrictions, contactPerson, estimatedWaitingTime, notes | usato da Shipment |
| `LoadingResponsibility` | Enum | Responsabilità carico/scarico | CUSTOMER, RECEIVER, WAREHOUSE_OPERATOR, THIRD_PARTY, NOT_PROVIDED_BY_CARRIER | usato da Shipment |
| `ShipmentCancellationReason` | Enum | Motivo annullamento | CUSTOMER_REQUEST, NO_AVAILABLE_TRUCK, NO_AVAILABLE_DRIVER, CARGO_NOT_READY, ROUTE_NOT_FEASIBLE, PRICE_REJECTED, OTHER | usato da Shipment |
| `ProofRequiredPolicy` | Value Object | Requisiti prova consegna | signatureRequired, photoRequired, receiverNameRequired, documentAttachmentRequired, notes | usato da Shipment |

# operation

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `TransportMission` | Entity | Viaggio operativo reale | id, missionNumber, shipments, driverId, vehicleCombinationId, stops, loadPlan, status, plannedStart, plannedEnd, actualStart, actualEnd, notes | può contenere più Shipment |
| `MissionStatus` | Enum | Stato missione | CREATED, PLANNED, IN_PROGRESS, COMPLETED, CANCELLED, BLOCKED | usato da Mission |
| `MissionStop` | Entity/Value Object | Fermata missione | id, missionId, stopOrder, stopType, location, plannedArrival, plannedDeparture, actualArrival, actualDeparture, relatedShipmentId, notes | parte di Mission |
| `MissionStopType` | Enum | Tipo fermata missione | DEPOT_START, PICKUP, DELIVERY, REST, FUEL, BORDER, DEPOT_RETURN, OTHER | usato da MissionStop |
| `LoadPlan` | Entity | Piano di carico | id, missionId, items, totalWeight, totalVolume, notes | collegato a Mission |
| `LoadPlanItem` | Value Object | Elemento piano carico | cargoId, vehicleSection, position, weight, volume, unloadOrder, notes | parte di LoadPlan |

# planning

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `TripPlan` | Entity | Piano viaggio | id, missionId, route, plannedDepartureTime, estimatedArrivalTime, plannedStops, breakPlan, notes | collegato a Mission |
| `DrivingTimeRule` | Value Object | Regole tempi guida | maxContinuousDrivingTime, mandatoryBreakDuration, maxDailyDrivingTime, maxWeeklyDrivingTime, maxFortnightlyDrivingTime, dailyRestRequirement, weeklyRestRequirement, regulatoryArea | usato da Compliance |
| `DrivingSession` | Entity | Sessione guida | id, driverId, startTime, endTime, duration, routeLeg, notes | usato da Planning |
| `DrivingBreak` | Entity/Value Object | Pausa | id, startTime, endTime, duration, breakType, location, reason, notes | parte di BreakPlan |
| `BreakType` | Enum | Tipo pausa | MANDATORY_BREAK, DAILY_REST, OPTIONAL_STOP, FUEL_STOP, LOADING_WAIT, UNLOADING_WAIT | usato da DrivingBreak |
| `DailyRest` | Value Object | Riposo giornaliero | startTime, endTime, duration, compliant | usato da Planning |
| `WeeklyRest` | Value Object | Riposo settimanale | startTime, endTime, duration, compliant | usato da Planning |
| `DriverDutyPeriod` | Entity | Periodo attività conducente | id, driverId, startTime, endTime, dutyType, duration, notes | guida/lavoro/attesa/riposo |
| `TachographRecord` | Entity | Registrazione tachigrafo | id, driverId, vehicleId, startTime, endTime, activityType, source, notes | storico |
| `BreakPlan` | Value Object | Piano pause | drivingBreaks, dailyRests, weeklyRests, notes | usato da TripPlan |
| `RestStop` | Entity/Value Object | Area sosta | id, name, location, availableForTrucks, secureParking, fuelStation, restaurant, showers, refrigeratedArea, notes | usato da StopRecommendation |
| `StopRecommendation` | Value Object | Sosta consigliata | restStop, recommendedArrivalTime, recommendedDepartureTime, reason, distanceFromRoute, priority | usato da Planning |
| `VehiclePosition` | Value Object | Posizione veicolo | vehicleCombinationId, coordinates, recordedAt, speed, heading, missionId | usato da tracking |
| `RouteProgress` | Value Object | Avanzamento tratta | missionId, currentPosition, distanceTravelled, remainingDistance, estimatedRemainingTime, status | usato da tracking |
| `EstimatedArrival` | Value Object | Arrivo stimato | estimatedTime, calculatedAt, confidenceLevel, reason | usato da Planning |

# tracking

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `ShipmentEvent` | Entity | Evento spedizione | id, shipmentId, missionId, eventType, occurredAt, location, description, recordedBy | storico |
| `ShipmentEventType` | Enum | Tipo evento | CREATED, ASSIGNED, PLANNED, DEPARTED, ARRIVED_AT_PICKUP, LOADED, BREAK_STARTED, BREAK_ENDED, DELAY_REPORTED, INCIDENT_REPORTED, ARRIVED_AT_DESTINATION, UNLOADED, DELIVERED, CANCELLED | usato da ShipmentEvent |
| `IncidentReport` | Entity | Segnalazione incidente/problema | id, shipmentId, missionId, incidentType, occurredAt, location, description, severity, resolved, notes | collegata a Shipment/Mission |
| `IncidentType` | Enum | Tipo incidente | VEHICLE_BREAKDOWN, ROAD_ACCIDENT, DAMAGED_CARGO, MISSING_CARGO, CUSTOMER_UNAVAILABLE, WRONG_ADDRESS, SECURITY_ISSUE, OTHER | usato da IncidentReport |
| `DelayReport` | Entity | Segnalazione ritardo | id, shipmentId, missionId, reason, estimatedDelay, reportedAt, notes | collegata a Shipment/Mission |
| `DelayReason` | Enum | Motivo ritardo | TRAFFIC, WEATHER, VEHICLE_PROBLEM, LOADING_DELAY, UNLOADING_DELAY, DRIVER_BREAK, BORDER_CONTROL, ROAD_RESTRICTION, OTHER | usato da DelayReport |

# document

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `TransportDocument` | Entity | Documento trasporto | id, documentType, shipmentId, orderId, documentNumber, issuedAt, attachmentReference, notes | collegato a Shipment/Order |
| `DocumentType` | Enum | Tipo documento | DDT, CMR, DELIVERY_NOTE, PROOF_OF_DELIVERY, INVOICE_REFERENCE, INSURANCE_DOCUMENT, HAZARDOUS_GOODS_DOCUMENT, OTHER | usato da TransportDocument |
| `ProofOfDelivery` | Entity | Prova consegna | id, shipmentId, deliveredAt, receiverName, signature, notes, damageReported, attachments, deliveryLocation | chiude spedizione |
| `Signature` | Value Object | Firma | signedBy, signedAt, signatureDataReference | usato da ProofOfDelivery |
| `AttachmentReference` | Value Object | Riferimento file | fileName, contentType, storageKey, uploadedAt, uploadedBy | non contiene file fisico |
| `DamageReport` | Entity | Danni merce | id, shipmentId, description, severity, photos, reportedAt, notes | collegato a ProofOfDelivery/Claim |

# regulation

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `RegulatoryProfile` | Entity/Value Object | Profilo normativo | id, country, area, ruleSets, validFrom, validTo, notes | usato da compliance |
| `CountryRegulation` | Entity | Normativa paese | id, country, year, drivingBanCalendar, roadRestrictions, notes | contiene regole paese |
| `RoadRestriction` | Entity/Value Object | Restrizione strada | id, area, restrictionType, affectedVehicleTypes, affectedCargoTypes, validFrom, validTo, notes | usato da route compliance |
| `DrivingBanCalendar` | Entity | Calendario divieti | id, country, year, banPeriods, exceptions, notes | usato da compliance |
| `DrivingBanPeriod` | Value Object | Periodo divieto | startDateTime, endDateTime, affectedVehicleMass, affectedVehicleTypes, affectedRoadTypes, reason | parte calendario |
| `DrivingBanException` | Value Object | Eccezione divieto | exceptionType, description, requiredPermit, notes | parte calendario |
| `TransportPermit` | Entity | Permesso trasporto | id, permitType, holderId, validFrom, validTo, area, documentReference, notes | usato da compliance |
| `PermitType` | Enum | Tipo permesso | HOLIDAY_BAN_EXEMPTION, ZTL_ACCESS, ADR_ROUTE_PERMISSION, EXCEPTIONAL_TRANSPORT, PORT_ACCESS, BORDER_CROSSING | usato da TransportPermit |
| `RoadAccessRestriction` | Value Object | Restrizione accesso | area, vehicleTypes, timeWindow, reason, notes | usato da RoadRestriction |
| `LowEmissionZoneRule` | Value Object | Regola LEZ | area, requiredEmissionStandard, validFrom, validTo, notes | usato da compliance |
| `TunnelRestriction` | Value Object | Restrizione galleria | tunnelName, forbiddenCargoTypes, forbiddenVehicleTypes, notes | usato da compliance |
| `BorderCrossingRule` | Value Object | Regola confine | countryFrom, countryTo, requiredDocuments, requiredPermits, notes | usato da international routes |
| `Rule` | Entity/Value Object | Regola configurabile | id, name, description, applicability, severity, active | parte di RuleSet |
| `RuleSet` | Entity | Insieme regole | id, name, version, rules, validFrom, validTo, notes | usato da compliance |
| `RuleSeverity` | Enum | Severità regola | BLOCKING, WARNING, INFO | usato da Rule |
| `RuleApplicability` | Value Object | Applicabilità regola | country, area, vehicleTypes, cargoTypes, dateRange, notes | usato da Rule |

# compliance

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `ComplianceCheck` | Interface/Service domain | Controllo generico | name, description | produce ComplianceResult |
| `ComplianceResult` | Value Object | Risultato controllo | status, violations, warnings, checkedAt, notes | ritornato dai check |
| `ComplianceViolation` | Value Object | Violazione | code, message, severity, relatedObjectId, blocking, notes | parte di result |
| `DriverVehicleEligibilityCheck` | Domain Service | Autista può guidare mezzo | driver, vehicleCombination, date | controlla patente/CQC |
| `DriverCargoEligibilityCheck` | Domain Service | Autista può trasportare carico | driver, cargo, date | controlla ADR/qualifiche |
| `LicenseComplianceCheck` | Domain Service | Patente valida | driverLicense, requiredProfile, date | controlla categorie e scadenza |
| `CqcComplianceCheck` | Domain Service | CQC valida | driver, transportType, date | controlla qualifica |
| `AdrComplianceCheck` | Domain Service | ADR valida | driver, cargo, date | controlla merci pericolose |
| `AtpComplianceCheck` | Domain Service | ATP/frigo compatibile | vehicleCombination, cargo, date | controlla requisiti refrigerati |
| `DrivingTimeComplianceCheck` | Domain Service | Tempi guida e pause | tripPlan, drivingTimeRule | controlla piano |
| `RoadRestrictionComplianceCheck` | Domain Service | Restrizioni strada | route, vehicleCombination, cargo, date | controlla divieti/permessi |
| `WeightComplianceCheck` | Domain Service | Peso conforme | cargo, vehicleCombination | controlla portata e massa |
| `VolumeComplianceCheck` | Domain Service | Volume conforme | cargo, vehicleCombination | controlla volume utile |
| `DimensionComplianceCheck` | Domain Service | Dimensioni conformi | cargo, vehicleCombination | controlla lung/larg/alt |
| `LoadCompatibilityCheck` | Domain Service | Compatibilità carico totale | cargo, vehicleCombination, loadPlan | controlla peso/volume/dimensioni/requisiti |
| `VehicleCombinationCompatibilityCheck` | Domain Service | Motrice/rimorchio compatibili | vehicleCombination | controlla aggancio e limiti |
| `CargoVehicleCompatibilityCheck` | Domain Service | Mezzo adatto al carico | cargo, vehicleCombination | controlla bodyType/requisiti |
| `DocumentComplianceCheck` | Domain Service | Documenti presenti | shipment, cargo, regulation | controlla documenti richiesti |
| `PermitComplianceCheck` | Domain Service | Permessi presenti | shipment, route, vehicleCombination, cargo | controlla permessi |

# facility

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Depot` | Entity | Deposito | id, name, address, coordinates, capacity, status, notes | punto partenza/rientro |
| `Yard` | Entity | Piazzale | id, name, address, parkingSlots, status, notes | parcheggio mezzi |
| `Warehouse` | Entity | Magazzino | id, name, address, contactInfo, operatingHours, status, notes | luogo operativo |
| `ParkingSlot` | Entity | Posto parcheggio | id, yardId, slotCode, suitableFor, occupied, notes | per veicoli/rimorchi |
| `FacilityStatus` | Enum | Stato struttura | ACTIVE, INACTIVE, FULL, MAINTENANCE | usato da facility |

# carrier

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Carrier` | Entity | Vettore esterno | id, name, carrierType, vatNumber, contactInfo, contract, rating, status, notes | usato per subappalto |
| `CarrierType` | Enum | Tipo vettore | COMPANY, OWNER_OPERATOR, PARTNER, TEMPORARY_SUBCONTRACTOR | usato da Carrier |
| `CarrierContract` | Entity | Contratto vettore | id, carrierId, validFrom, validTo, terms, rates, notes | collegato a Carrier |
| `SubcontractedMission` | Entity | Missione subappaltata | id, missionId, carrierId, agreedPrice, status, notes | collegata a Mission |
| `CarrierRating` | Value Object | Valutazione vettore | score, punctualityScore, qualityScore, notes | usato da Carrier |

# availability

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `AvailabilityCalendar` | Entity | Calendario disponibilità | id, resourceType, resourceId, slots, notes | per driver/vehicle/depot |
| `AvailabilitySlot` | Value Object | Slot disponibilità | start, end, available, reason, notes | parte calendario |
| `UnavailabilityReason` | Enum | Motivo indisponibilità | MAINTENANCE, VACATION, SICKNESS, ASSIGNED, OUT_OF_SERVICE, RESERVED, OTHER | usato da slot |
| `ResourceType` | Enum | Tipo risorsa | DRIVER, VAN, TRUCK, TRAILER, VEHICLE_COMBINATION, CARRIER, DEPOT, YARD | usato da calendar |

# notification

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Alert` | Entity | Avviso | id, alertType, severity, status, relatedObjectId, message, createdAt, resolvedAt, notes | non invia email |
| `AlertType` | Enum | Tipo avviso | LICENSE_EXPIRING, CQC_EXPIRING, ADR_EXPIRING, INSURANCE_EXPIRING, MAINTENANCE_OVERDUE, DELAY_ESTIMATED, DOCUMENT_MISSING, COMPLIANCE_FAILED, BREAK_NOT_PLANNED | usato da Alert |
| `AlertSeverity` | Enum | Severità avviso | LOW, MEDIUM, HIGH, CRITICAL | usato da Alert |
| `AlertStatus` | Enum | Stato avviso | OPEN, ACKNOWLEDGED, RESOLVED, DISMISSED | usato da Alert |

# claim

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Claim` | Entity | Reclamo | id, shipmentId, customerId, claimType, status, description, openedAt, closedAt, resolution, notes | collegato a Shipment |
| `ClaimType` | Enum | Tipo reclamo | DAMAGED_CARGO, MISSING_CARGO, DELAY_DISPUTE, NON_COMPLIANT_DELIVERY, MISSING_DOCUMENT, INVOICE_DISPUTE | usato da Claim |
| `ClaimStatus` | Enum | Stato reclamo | OPEN, UNDER_REVIEW, ACCEPTED, REJECTED, RESOLVED, CLOSED | usato da Claim |
| `ClaimResolution` | Value Object | Risoluzione reclamo | resolutionType, compensationAmount, description, resolvedAt, notes | usato da Claim |

# audit

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `AuditLog` | Entity | Log audit | id, actor, actionType, targetType, targetId, occurredAt, description, metadata | storico azioni |
| `UserAction` | Entity/Value Object | Azione utente | actor, actionType, target, occurredAt, notes | parte di AuditLog |
| `Actor` | Value Object | Chi ha agito | actorId, actorType, displayName, roles | collega UserAccount o sistema |
| `ActionType` | Enum | Tipo azione | CREATE_ORDER, CREATE_SHIPMENT, ASSIGN_DRIVER, ASSIGN_VEHICLE, UPDATE_PRICE, CANCEL_SHIPMENT, CLOSE_SHIPMENT, UPLOAD_DOCUMENT, LOGIN, LOGOUT | usato da AuditLog |

# sustainability

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `EmissionEstimate` | Value Object | Stima emissioni | co2Amount, calculationMethod, vehicleId, routeId, calculatedAt, notes | report futuro |
| `FuelUsageEstimate` | Value Object | Stima carburante | estimatedLiters, distance, consumption, calculatedAt, notes | pricing/report |
| `EmissionStandard` | Enum/Value Object | Classe ambientale | standardCode, description, country, notes | usato da Vehicle |

# security

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `SecurityRequirement` | Value Object | Requisito sicurezza carico | securityLevel, requiresSecureParking, requiresSeal, requiresTracking, allowedStopsOnly, notes | usato da Cargo/Shipment |
| `SecurityLevel` | Enum | Livello sicurezza | LOW, NORMAL, HIGH, CRITICAL | usato da SecurityRequirement |
| `SecureParkingRequirement` | Value Object | Richiesta parcheggio sicuro | required, minimumSecurityLevel, maxDistanceFromRoute, notes | usato in planning |
| `Seal` | Entity/Value Object | Sigillo | sealNumber, appliedAt, removedAt, appliedBy, notes | usato su cargo/trailer |
| `AccessPolicy` | Value Object | Policy accesso | role, permissions, notes | usato da identity/security |
| `SecurityEvent` | Entity | Evento sicurezza | id, eventType, actor, occurredAt, description, severity, notes | audit/security |

# identity

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `UserAccount` | Entity | Account utente | id, username, email, passwordHash, roles, status, createdAt, lastLoginAt, linkedAccount, notes | accesso sistema |
| `UserRole` | Enum | Ruolo utente | ADMIN, OPERATIONS_MANAGER, DISPATCHER, FLEET_MANAGER, DRIVER, CUSTOMER, ACCOUNTING, VIEWER | usato da UserAccount |
| `Permission` | Enum | Permesso applicativo | MANAGE_USERS, VIEW_CUSTOMERS, MANAGE_CUSTOMERS, CREATE_ORDER, CREATE_QUOTE, APPROVE_QUOTE, CREATE_SHIPMENT, ASSIGN_DRIVER, ASSIGN_VEHICLE, VIEW_TRACKING, MANAGE_FLEET, MANAGE_MAINTENANCE, VIEW_PRICING, MANAGE_DOCUMENTS, CLOSE_SHIPMENT, VIEW_REPORTS | usato da AccessPolicy |
| `UserStatus` | Enum | Stato account | ACTIVE, DISABLED, LOCKED, PENDING_ACTIVATION, PASSWORD_RESET_REQUIRED | usato da UserAccount |
| `LoginCredential` | Value Object | Credenziali login | usernameOrEmail, passwordHash, lastPasswordChangeAt, passwordResetRequired | hashing tecnico in infrastructure |
| `PasswordResetToken` | Entity | Token reset password | id, userAccountId, tokenHash, expiresAt, usedAt, createdAt | security futuro |
| `AccountLink` | Value Object | Collegamento account | targetType, targetId, displayName | collega Driver/Customer/Employee/Carrier |

# configuration

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `CompanySetting` | Entity/Value Object | Impostazione aziendale | key, value, description, active | configurazione |
| `RuleConfiguration` | Entity | Configurazione regola | id, ruleSetId, parameters, active, validFrom, validTo | regole configurabili |
| `CatalogItem` | Entity | Elemento catalogo | id, catalogType, code, label, description, active | cataloghi configurabili |
| `SystemParameter` | Entity/Value Object | Parametro sistema | key, value, scope, description | config generica |

# reporting

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `ShipmentReport` | Value Object | Report spedizioni | period, totalShipments, deliveredShipments, cancelledShipments, delayedShipments | report futuro |
| `FleetReport` | Value Object | Report flotta | period, vehicleUtilization, maintenanceDue, outOfServiceVehicles | report futuro |
| `DriverReport` | Value Object | Report autisti | period, drivingHours, missionsCompleted, delays, incidents | report futuro |
| `CostReport` | Value Object | Report costi | period, fuelCost, tollCost, driverCost, margin | report futuro |
| `PerformanceMetric` | Value Object | Metrica performance | name, value, period, unit, notes | dashboard futuro |

# location

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Address` | Value Object | Indirizzo | street, streetNumber, city, province, postalCode, country | usato ovunque |
| `Coordinates` | Value Object | Coordinate | latitude, longitude | usato da route/tracking |
| `Facility` | Entity | Luogo operativo generico | id, name, facilityType, address, coordinates, contact, notes | sedi cliente/hub |
| `FacilityType` | Enum | Tipo luogo | CUSTOMER_SITE, WAREHOUSE, LOGISTICS_HUB, REST_AREA, FUEL_STATION, PORT, BORDER_POINT, OTHER | usato da Facility |
| `LocationContact` | Value Object | Referente luogo | name, phone, email, role, notes | usato da Facility |

# shared

| Classe | Tipo | Responsabilità | Attributi concettuali | Relazioni / Note |
|---|---|---|---|---|
| `Money` | Value Object | Denaro | amount, currency | usato in pricing/billing |
| `Weight` | Value Object | Peso | value, unit | preferire kg internamente |
| `Volume` | Value Object | Volume | value, unit | preferire m3 internamente |
| `Distance` | Value Object | Distanza | value, unit | preferire km internamente |
| `Dimension` | Value Object | Dimensioni | length, width, height, unit | usato da cargo/fleet |
| `TemperatureRange` | Value Object | Intervallo temperatura | minTemperature, maxTemperature, unit | usato da frigo/cargo |
| `DateRange` | Value Object | Periodo date | startDate, endDate | usato in validità |
| `TimeWindow` | Value Object | Finestra oraria | startTime, endTime | usato in pickup/delivery |
| `Percentage` | Value Object | Percentuale | value | usato in sconti/margini |
| `Notes` | Value Object | Note controllate | text | opzionale |
