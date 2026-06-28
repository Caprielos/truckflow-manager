# Package `fleet` — Fleet

## Scopo

È il package più importante per mezzi, allestimenti, schede tecniche, convogli, certificati e compatibilità con il carico.

## Concetti principali

- `Vehicle`
- `VehicleCombination`
- `VehicleUnitType`
- `VehicleType`
- `VehicleWeightClass`
- `VehicleBodyBaseType`
- `VehicleBodyConfiguration`
- `VehicleLoadingEquipment`
- `VehicleTechnicalSpecification`
- `VehicleCertificate`
- `VehicleCombinationTechnicalRules`
- `VehicleBodyCompatibilityRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `AxleSteeringType` | enum | Enum di classificazione/valori ammessi. |
| `BrakeSafetySystem` | enum | Enum di classificazione/valori ammessi. |
| `BrakeType` | enum | Enum di classificazione/valori ammessi. |
| `CouplingType` | enum | Enum di classificazione/valori ammessi. |
| `DeadlineStatus` | enum | Enum di classificazione/valori ammessi. |
| `KingpinDiameter` | enum | Enum di classificazione/valori ammessi. |
| `RetarderType` | enum | Enum di classificazione/valori ammessi. |
| `SuspensionType` | enum | Enum di classificazione/valori ammessi. |
| `TireSpecification` | final class | Value object tecnico con dati strutturati. |
| `TransmissionType` | enum | Enum di classificazione/valori ammessi. |
| `Vehicle` | final class | Entity principale del mezzo singolo. |
| `VehicleAxle` | final class | Entity o value object del package. |
| `VehicleAxleSpecification` | final class | Value object tecnico con dati strutturati. |
| `VehicleBodyBaseType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleBodyCompatibilityRules` | final class | Regole di compatibilità tra merce e allestimento. |
| `VehicleBodyConfiguration` | final class | Composizione dell’allestimento: base, accessori e feature tecniche. |
| `VehicleBodyType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleCertificate` | final class | Abilitazione/certificazione con validità. |
| `VehicleCertificateType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleCombination` | final class | Entity del convoglio operativo composto da motrice e opzionale unità trainata. |
| `VehicleCombinationLegalLimitProfile` | final class | Entity o value object del package. |
| `VehicleCombinationRules` | final class | Regole operative per assegnare un convoglio a spedizioni/carichi. |
| `VehicleCombinationTechnicalRules` | final class | Calcoli tecnici del convoglio: massa, tara, portata, traino e limiti. |
| `VehicleCombinationType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleCouplingSpecification` | final class | Value object tecnico con dati strutturati. |
| `VehicleDimensionSpecification` | final class | Value object tecnico con dati strutturati. |
| `VehicleEquipmentPosition` | enum | Enum di classificazione/valori ammessi. |
| `VehicleLoadingEquipment` | final class | Entity o value object del package. |
| `VehicleLoadingEquipmentType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleMassSpecification` | final class | Value object tecnico con dati strutturati. |
| `VehicleStatus` | enum | Enum di classificazione/valori ammessi. |
| `VehicleTechnicalFeature` | enum | Enum di classificazione/valori ammessi. |
| `VehicleTechnicalSpecification` | final class | Scheda tecnica aggregata del mezzo. |
| `VehicleType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleUnitType` | enum | Enum di classificazione/valori ammessi. |
| `VehicleWeightClass` | enum | Enum di classificazione/valori ammessi. |
| `WheelConfiguration` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `AxleSteeringType`: `FIXED`, `STEERING`, `SELF_STEERING`
- `BrakeSafetySystem`: `ABS`, `EBS`, `ESP`, `RSP`
- `BrakeType`: `DISC`, `DRUM`
- `CouplingType`: `NONE`, `FIFTH_WHEEL`, `DRAWBAR_EYE`, `CENTER_AXLE_DRAWBAR`, `TOW_HOOK`
- `DeadlineStatus`: `VALID`, `EXPIRING_SOON`, `EXPIRED`
- `KingpinDiameter`: `TWO_INCHES`, `THREE_AND_HALF_INCHES`
- `RetarderType`: `NONE`, `HYDRAULIC_RETARDER`, `INTARDER`, `ENHANCED_ENGINE_BRAKE`
- `SuspensionType`: `MECHANICAL`, `PNEUMATIC`, `HYDRAULIC`, `LEAF_SPRING`
- `TransmissionType`: `MANUAL`, `AUTOMATED`, `AUTOMATIC`
- `VehicleBodyBaseType`: `NONE`, `FIXED_OPEN_BOX`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `CURTAIN_SIDE`, `DRY_BOX`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `TANK`, `SILO`, `FLATBED`, `LOW_LOADER`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `WALKING_FLOOR`, `CAR_TRANSPORTER`, `COIL_CARRIER`, `LIVESTOCK_BODY`, `CONCRETE_MIXER`, `CRANE_PLATFORM`
- `VehicleBodyType`: `NONE`, `VAN_BODY`, `BOX`, `DRY_BOX`, `CURTAIN_SIDE`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `FIXED_OPEN_BOX`, `FLATBED`, `FLATBED_WITH_RAMPS`, `LOW_LOADER`, `EXTENDABLE_FLATBED`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `TIPPER`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `WALKING_FLOOR`, `SILO`, `TANK_LIQUID`, `TANK_FUEL`, `TANK_GAS`, `FOOD_GRADE_TANK`, `CAR_TRANSPORTER`, `LIVESTOCK`, `COIL_CARRIER`, `CONCRETE_MIXER`
- `VehicleCertificateType`: `ROADWORTHINESS_INSPECTION`, `TACHOGRAPH_CALIBRATION`, `ATP`, `ADR_VEHICLE_APPROVAL`, `XL_CODE`, `TANK_PERIODIC_INSPECTION`, `CRANE_PERIODIC_INSPECTION`, `TAIL_LIFT_PERIODIC_INSPECTION`, `INSURANCE`, `ROAD_TAX`
- `VehicleCombinationType`: `SINGLE_VEHICLE`, `TRUCK_AND_TRAILER`, `ARTICULATED_VEHICLE`
- `VehicleEquipmentPosition`: `BEHIND_CAB`, `REAR`, `REAR_PLATFORM`, `CHASSIS`, `ROOF`, `SIDE`, `NOT_APPLICABLE`
- `VehicleLoadingEquipmentType`: `HYDRAULIC_CRANE`, `TAIL_LIFT`, `HYDRAULIC_RAMP`, `MANUAL_RAMP`, `HYDRAULIC_WINCH`, `ELECTRIC_WINCH`, `POLYP_GRAPPLE_LOADER`, `REFRIGERATION_UNIT`, `TWIST_LOCK`
- `VehicleStatus`: `AVAILABLE`, `ASSIGNED`, `IN_MAINTENANCE`, `OUT_OF_SERVICE`, `RETIRED`
- `VehicleTechnicalFeature`: `SELF_STEERING_AXLE`, `STEERING_AXLE`, `LIFTABLE_AXLE`, `DOUBLE_DECK`, `MEGA_VOLUME`, `LOW_DECK`, `AIR_SUSPENSION`, `EXTENDABLE_CHASSIS`, `GOOSENECK`, `LOW_BED_CRADLE`, `TWIST_LOCKS`, `ATP_CERTIFIED`, `XL_CERTIFIED`, `ADR_APPROVED`, `ACTIVE_REFRIGERATION`, `TEMPERATURE_RECORDER`, `HAY_RACKS`, `AGRICULTURAL_APPROVAL`, `STAINLESS_STEEL_TANK`, `FOOD_GRADE_TANK`
- `VehicleType`: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`
- `VehicleUnitType`: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`
- `VehicleWeightClass`: `LIGHT_UNDER_3_5T`, `MEDIUM_UP_TO_12T`, `HEAVY_OVER_12T`
- `WheelConfiguration`: `SINGLE`, `TWIN`

## Regole di business

- Il mezzo non è più descritto da una macro-enum unica: si compone di unità fisica, allestimento, accessori, feature tecniche e specifiche.
- I convogli distinguono veicolo singolo, autotreno e autoarticolato/bilico.
- Le regole tecniche calcolano massa, tara, portata, assi, volume, pallet e limiti.
- Le certificazioni mezzo come ATP, ADR, XL, revisione e tachigrafo sono controllabili tramite scadenza.

## Collegamenti con altri package

- cargo per compatibilità
- driver per patente e abilitazioni
- maintenance/tire/fuel/telematics per gestione vita mezzo
- pricing per costi e pedaggi
- operation per assegnazione missione

## Test collegati

- `RealisticFleetModelTest.java`
- `TireSpecificationTest.java`
- `VehicleCombinationRulesTest.java`
- `VehicleCombinationTest.java`
- `VehicleTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
