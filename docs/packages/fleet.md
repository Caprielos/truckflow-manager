# Package `fleet` — Flotta e schede tecniche

Gestisce veicoli, rimorchi, trattori, convogli, assi, masse, allestimenti, equipaggiamenti, certificati e compatibilità tecnica.

## Responsabilità

- Vehicle può usare modello tecnico realistico con VehicleUnitType e VehicleTechnicalSpecification.
- VehicleCombination costruisce singolo mezzo, autotreno o articolato.
- Fleet interagisce con tire, maintenance, fuel, telematics, loadsecurity e compliance.

## Classi

- `AxleSteeringType` — enum con valori: `FIXED`, `STEERING`, `SELF_STEERING`.
- `BrakeSafetySystem` — enum con valori: `ABS`, `EBS`, `ESP`, `RSP`.
- `BrakeType` — enum con valori: `DISC`, `DRUM`.
- `CouplingType` — enum con valori: `NONE`, `FIFTH_WHEEL`, `DRAWBAR_EYE`, `CENTER_AXLE_DRAWBAR`, `TOW_HOOK`.
- `DeadlineStatus` — enum con valori: `VALID`, `EXPIRING_SOON`, `EXPIRED`.
- `KingpinDiameter` — enum con valori: `TWO_INCHES`, `THREE_AND_HALF_INCHES`.
- `RetarderType` — enum con valori: `NONE`, `HYDRAULIC_RETARDER`, `INTARDER`, `ENHANCED_ENGINE_BRAKE`.
- `SuspensionType` — enum con valori: `MECHANICAL`, `PNEUMATIC`, `HYDRAULIC`, `LEAF_SPRING`.
- `TireSpecification` — modello/domain object del package.
- `TransmissionType` — enum con valori: `MANUAL`, `AUTOMATED`, `AUTOMATIC`.
- `Vehicle` — modello/domain object del package.
- `VehicleAxle` — modello/domain object del package.
- `VehicleAxleSpecification` — modello/domain object del package.
- `VehicleBodyBaseType` — enum con valori: `NONE`, `FIXED_OPEN_BOX`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `CURTAIN_SIDE`, `DRY_BOX`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `TANK`, `SILO`, `FLATBED`, `LOW_LOADER`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`….
- `VehicleBodyCompatibilityRules` — classe di regole pure del package.
- `VehicleBodyConfiguration` — modello/domain object del package.
- `VehicleBodyType` — enum con valori: `NONE`, `VAN_BODY`, `BOX`, `DRY_BOX`, `CURTAIN_SIDE`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `FIXED_OPEN_BOX`, `FLATBED`, `FLATBED_WITH_RAMPS`, `LOW_LOADER`, `EXTENDABLE_FLATBED`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`….
- `VehicleCertificate` — modello/domain object del package.
- `VehicleCertificateType` — enum con valori: `ROADWORTHINESS_INSPECTION`, `TACHOGRAPH_CALIBRATION`, `ATP`, `ADR_VEHICLE_APPROVAL`, `XL_CODE`, `TANK_PERIODIC_INSPECTION`, `CRANE_PERIODIC_INSPECTION`, `TAIL_LIFT_PERIODIC_INSPECTION`, `INSURANCE`, `ROAD_TAX`.
- `VehicleCombination` — modello/domain object del package.
- `VehicleCombinationLegalLimitProfile` — modello/domain object del package.
- `VehicleCombinationRules` — classe di regole pure del package.
- `VehicleCombinationTechnicalRules` — classe di regole pure del package.
- `VehicleCombinationType` — enum con valori: `SINGLE_VEHICLE`, `TRUCK_AND_TRAILER`, `ARTICULATED_VEHICLE`.
- `VehicleCouplingSpecification` — modello/domain object del package.
- `VehicleDimensionSpecification` — modello/domain object del package.
- `VehicleEquipmentPosition` — enum con valori: `BEHIND_CAB`, `REAR`, `REAR_PLATFORM`, `CHASSIS`, `ROOF`, `SIDE`, `NOT_APPLICABLE`.
- `VehicleLoadingEquipment` — modello/domain object del package.
- `VehicleLoadingEquipmentType` — enum con valori: `HYDRAULIC_CRANE`, `TAIL_LIFT`, `HYDRAULIC_RAMP`, `MANUAL_RAMP`, `HYDRAULIC_WINCH`, `ELECTRIC_WINCH`, `POLYP_GRAPPLE_LOADER`, `REFRIGERATION_UNIT`, `TWIST_LOCK`.
- `VehicleMassSpecification` — modello/domain object del package.
- `VehicleStatus` — enum con valori: `AVAILABLE`, `ASSIGNED`, `IN_MAINTENANCE`, `OUT_OF_SERVICE`, `RETIRED`.
- `VehicleTechnicalFeature` — enum con valori: `SELF_STEERING_AXLE`, `STEERING_AXLE`, `LIFTABLE_AXLE`, `DOUBLE_DECK`, `MEGA_VOLUME`, `LOW_DECK`, `AIR_SUSPENSION`, `EXTENDABLE_CHASSIS`, `GOOSENECK`, `LOW_BED_CRADLE`, `TWIST_LOCKS`, `ATP_CERTIFIED`, `XL_CERTIFIED`, `ADR_APPROVED`, `ACTIVE_REFRIGERATION`….
- `VehicleTechnicalSpecification` — modello/domain object del package.
- `VehicleType` — enum con valori: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`, `REFRIGERATED_TRUCK`, `REFRIGERATED_TRAILER`.
- `VehicleUnitType` — enum con valori: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`.
- `VehicleWeightClass` — enum con valori: `LIGHT_UNDER_3_5T`, `MEDIUM_UP_TO_12T`, `HEAVY_OVER_12T`.
- `WheelConfiguration` — enum con valori: `SINGLE`, `TWIN`.

## Regole importanti

- Il modello realistico distingue unità veicolo, allestimento, massa, dimensione, assi, aggancio e certificati.
- Il refrigerato è un allestimento/certificazione, non un tipo veicolo separato.
- Le combinazioni calcolano tipo, assi totali, massa complessiva e scadenze certificate.

## Collegamenti

- Vehicle può usare modello tecnico realistico con VehicleUnitType e VehicleTechnicalSpecification.
- VehicleCombination costruisce singolo mezzo, autotreno o articolato.
- Fleet interagisce con tire, maintenance, fuel, telematics, loadsecurity e compliance.
