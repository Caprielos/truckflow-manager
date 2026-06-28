# Domain Overview

## Mappa mentale del dominio

Il dominio segue un flusso realistico:

```text
Customer
  ↓
TransportOrder
  ↓ ordine accettato
Shipment
  ↓ pianificazione operativa
TransportMission
  ↓ usa
RoutePlan + VehicleCombination + Driver
  ↓ controlla
Cargo + Documents + Company + Compliance + Availability
  ↓ durante il viaggio
Tracking + Telematics + Fuel
  ↓ dopo/durante
Billing + Claim + Reporting + Audit
```

## Differenza tra concetti simili

### TransportOrder

È la richiesta commerciale: cliente, merce, pickup, delivery, prezzo e tipo servizio.

### Shipment

È la spedizione nata da un ordine accettato. Tiene lo stato logistico della spedizione.

### TransportMission

È il viaggio reale pianificato/eseguito. Qui entrano driver, mezzo/convoglio e rotta.

### Vehicle

È il mezzo fisico: furgone, motrice, trattore, rimorchio, semirimorchio.

### VehicleCombination

È la combinazione operativa: mezzo singolo, autotreno, articolato.

### Tire

È una gomma fisica tracciabile. Non è un semplice campo del veicolo.

### Driver

È la persona/autista. Non è l'account software.

### UserAccount

È l'utente applicativo che accede al sistema.

## Modello realistico

Il progetto ora evita semplificazioni troppo finte.

Esempio: un mezzo refrigerato non è un tipo separato assoluto. È una combinazione di:

```text
VehicleUnitType
VehicleBodyConfiguration
VehicleCertificate ATP
VehicleTechnicalFeature ACTIVE_REFRIGERATION
TemperatureRange / cargo requirement
```

Questa modellazione è più vicina alla realtà.
