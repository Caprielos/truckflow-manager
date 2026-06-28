# ADR 003 — Realistic Fleet Model

## Decisione

Il mezzo non viene più modellato come una macro-categoria unica.

La flotta viene modellata con concetti separati:

```text
VehicleUnitType
VehicleWeightClass
VehicleBodyBaseType
VehicleBodyConfiguration
VehicleTechnicalSpecification
VehicleCertificate
VehicleCombination
```

## Motivazione

Nel mondo reale un camion non è solo “frigo” o “cisterna”.

Esempio:

```text
RIGID_TRUCK + REFRIGERATED_BOX + ATP
TRACTOR_UNIT + SEMI_TRAILER + TANK + ADR
RIGID_TRUCK + FIXED_OPEN_BOX + HYDRAULIC_CRANE
```

## Conseguenze

La compatibilità merce/mezzo viene calcolata con regole specifiche, non con booleani dentro una sola enum.
