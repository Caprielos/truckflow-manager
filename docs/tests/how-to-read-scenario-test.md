# Come leggere un test di scenario

Un test di scenario può sembrare lungo, ma ha una struttura semplice.

## 1. Setup

Crea repository in memoria e use case.

```text
InMemoryParkingSpotRepository
InMemoryShipmentRepository
DefaultAssignParkingSpotUseCase
```

## 2. Dati iniziali

Crea oggetti realistici.

```text
driver
vehicle combination
route plan
parking spot
order
shipment
```

## 3. Azione

Chiama uno use case.

```text
planMission.handle(command)
assignParking.handle(command)
```

## 4. Verifica

Usa assert.

```text
assertEquals
assertTrue
assertNotNull
```

## Perché i codici sembrano inventati?

Perché sono dati di test, ma rappresentano codici reali aziendali.

```text
DRV-001 → autista
RTE-001 → rotta
COMBO-CURTAIN → convoglio
```
