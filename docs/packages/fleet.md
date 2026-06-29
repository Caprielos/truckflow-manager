# Package `fleet` — Flotta e mezzi

Modella veicoli, unità, allestimenti, assi, masse, dimensioni, certificati, combinazioni e compatibilità tecnica.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/fleet
```

## Classi

- `AxleSteeringType`
- `BrakeSafetySystem`
- `BrakeType`
- `CouplingType`
- `DeadlineStatus`
- `KingpinDiameter`
- `RetarderType`
- `SuspensionType`
- `TireSpecification`
- `TransmissionType`
- `Vehicle`
- `VehicleAxle`
- `VehicleAxleSpecification`
- `VehicleBodyBaseType`
- `VehicleBodyCompatibilityRules`
- `VehicleBodyConfiguration`
- `VehicleBodyType`
- `VehicleCertificate`
- `VehicleCertificateType`
- `VehicleCombination`
- `VehicleCombinationLegalLimitProfile`
- `VehicleCombinationRules`
- `VehicleCombinationTechnicalRules`
- `VehicleCombinationType`
- `VehicleCouplingSpecification`
- `VehicleDimensionSpecification`
- `VehicleEquipmentPosition`
- `VehicleLoadingEquipment`
- `VehicleLoadingEquipmentType`
- `VehicleMassSpecification`
- `VehicleStatus`
- `VehicleTechnicalFeature`
- `VehicleTechnicalSpecification`
- `VehicleType`
- `VehicleUnitType`
- `VehicleWeightClass`
- `WheelConfiguration`

## Test collegati

- `RealisticFleetModelTest`
- `TireSpecificationTest`
- `VehicleCombinationRulesTest`
- `VehicleCombinationTest`
- `VehicleTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
