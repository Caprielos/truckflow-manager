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


# Domain Overview

## Obiettivo

TruckFlow Manager è una web application gestionale per aziende di trasporto merci su strada.

Copre l'intero ciclo:

```text
Customer
    ↓
TransportOrder
    ↓ accepted
Shipment
    ↓ planned with RoutePlan
TransportMission
    ↓ assigned Driver + VehicleCombination
Tracking
    ↓ completed
Documents
    ↓
Invoice + Payment
    ↓
Reporting / Audit / Notifications
```

## Distinzioni fondamentali

### TransportOrder

Richiesta commerciale del cliente.

### Shipment

Spedizione nata da un ordine accettato.

### TransportMission

Viaggio operativo reale.

### VehicleCombination

Unità assegnabile composta da mezzo singolo o powered unit + trailer.

### UserAccount

Account applicativo separato da Driver e Customer.

## Package attuali

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

## Flusso commerciale

```text
CustomerAccount ACTIVE
    ↓
TransportOrder DRAFT
    ↓ submit
SUBMITTED
    ↓ accept
ACCEPTED
    ↓
Shipment.fromAcceptedOrder()
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

Alternative:

```text
REQUESTED/RECEIVED → REJECTED
VERIFIED con scadenza → EXPIRED
```

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

Alternative:

```text
UNDER_REVIEW → REJECTED
OPEN/UNDER_REVIEW/ACCEPTED → CANCELLED
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
