# ADR 003 — Modello flotta realistico e componibile

## Decisione

Il modello flotta non usa una sola enum gigante per rappresentare camion, rimorchi e allestimenti.

Il mezzo viene composto con:

```text
VehicleUnitType
VehicleBodyBaseType
VehicleBodyConfiguration
VehicleLoadingEquipment
VehicleTechnicalFeature
VehicleTechnicalSpecification
VehicleCertificate
```

## Motivazione

Nel mondo reale un mezzo può essere:

```text
semirimorchio centinato mega
furgonato frigo con sponda
scarrabile con gru retrocabina
pianale con rampe e verricello
cisterna ADR
trattore stradale con gru
```

Una enum unica diventerebbe ingestibile.

## Conseguenze

- Gli allestimenti sono componibili.
- Peso, assi e certificati stanno in specifiche tecniche.
- `VehicleType` resta solo per compatibilità con il codice esistente.
