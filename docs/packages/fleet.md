# Package `fleet` — Flotta, mezzi e convogli

## Scopo

Anagrafica e regole tecniche per veicoli, rimorchi, allestimenti, certificati e convogli.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `AxleSteeringType` | Enum | Valori controllati usati dalle regole di dominio. |
| `BrakeSafetySystem` | Enum | Valori controllati usati dalle regole di dominio. |
| `BrakeType` | Enum | Valori controllati usati dalle regole di dominio. |
| `CouplingType` | Enum | Valori controllati usati dalle regole di dominio. |
| `DeadlineStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `KingpinDiameter` | Enum | Valori controllati usati dalle regole di dominio. |
| `RetarderType` | Enum | Valori controllati usati dalle regole di dominio. |
| `SuspensionType` | Enum | Valori controllati usati dalle regole di dominio. |
| `TireSpecification` | Classe | Classe di dominio del package. |
| `TransmissionType` | Enum | Valori controllati usati dalle regole di dominio. |
| `Vehicle` | Classe | Entity del singolo mezzo fisico della flotta. |
| `VehicleAxle` | Classe | Singolo asse del mezzo. |
| `VehicleAxleSpecification` | Classe | Configurazione assi, sospensioni e freni. |
| `VehicleBodyBaseType` | Enum | Allestimento base, cioè il contenitore sopra il telaio. |
| `VehicleBodyCompatibilityRules` | Classe | Regole che collegano categoria merce e allestimento compatibile. |
| `VehicleBodyConfiguration` | Classe | Composizione dell’allestimento: base, accessori e feature tecniche. |
| `VehicleBodyType` | Enum | Enum legacy/operativa per compatibilità con il modello attuale. |
| `VehicleCertificate` | Classe | Certificato o scadenza tecnica del veicolo. |
| `VehicleCertificateType` | Enum | Tipi di certificati: revisione, ATP, ADR, XL, tachigrafo e altri. |
| `VehicleCombination` | Classe | Entity del convoglio operativo: singolo mezzo, autotreno o bilico. |
| `VehicleCombinationLegalLimitProfile` | Classe | Profilo limiti legali configurabile per peso e dimensioni. |
| `VehicleCombinationRules` | Classe | Classe di regole di business del package. |
| `VehicleCombinationTechnicalRules` | Classe | Calcoli tecnici del convoglio: masse, tara, portata e limiti. |
| `VehicleCombinationType` | Enum | Valori controllati usati dalle regole di dominio. |
| `VehicleCouplingSpecification` | Classe | Dati di ralla, timone, occhione o gancio. |
| `VehicleDimensionSpecification` | Classe | Dimensioni esterne/interne, volume e capacità pallet. |
| `VehicleEquipmentPosition` | Enum | Valori controllati usati dalle regole di dominio. |
| `VehicleLoadingEquipment` | Classe | Classe di dominio del package. |
| `VehicleLoadingEquipmentType` | Enum | Valori controllati usati dalle regole di dominio. |
| `VehicleMassSpecification` | Classe | Masse del mezzo: P.B.C., tara, traino e carico su ralla. |
| `VehicleStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `VehicleTechnicalFeature` | Enum | Valori controllati usati dalle regole di dominio. |
| `VehicleTechnicalSpecification` | Classe | Aggrega masse, dimensioni, assi, aggancio, allestimento e certificati. |
| `VehicleType` | Enum | Enum legacy mantenuta per compatibilità; oggi rappresenta il tipo fisico unità. |
| `VehicleUnitType` | Enum | Tipo fisico reale: furgone, autocarro, trattore, rimorchio o semirimorchio. |
| `VehicleWeightClass` | Enum | Classe di peso usata per ragionare su patente e regole operative. |
| `WheelConfiguration` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `AxleSteeringType`

Valori: `FIXED`, `STEERING`, `SELF_STEERING`.

### `BrakeSafetySystem`

Valori: `ABS`, `EBS`, `ESP`, `RSP`.

### `BrakeType`

Valori: `DISC`, `DRUM`.

### `CouplingType`

Valori: `NONE`, `FIFTH_WHEEL`, `DRAWBAR_EYE`, `CENTER_AXLE_DRAWBAR`, `TOW_HOOK`.

### `DeadlineStatus`

Valori: `VALID`, `EXPIRING_SOON`, `EXPIRED`.

### `KingpinDiameter`

Valori: `TWO_INCHES`, `THREE_AND_HALF_INCHES`.

### `RetarderType`

Valori: `NONE`, `HYDRAULIC_RETARDER`, `INTARDER`, `ENHANCED_ENGINE_BRAKE`.

### `SuspensionType`

Valori: `MECHANICAL`, `PNEUMATIC`, `HYDRAULIC`, `LEAF_SPRING`.

### `TransmissionType`

Valori: `MANUAL`, `AUTOMATED`, `AUTOMATIC`.

### `VehicleBodyBaseType`

Valori: `NONE`, `FIXED_OPEN_BOX`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `CURTAIN_SIDE`, `DRY_BOX`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `TANK`, `SILO`, `FLATBED`, `LOW_LOADER`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `WALKING_FLOOR`, `CAR_TRANSPORTER`, `COIL_CARRIER`, `LIVESTOCK_BODY`, `CONCRETE_MIXER`, `CRANE_PLATFORM`.

### `VehicleBodyType`

Valori: `NONE`, `VAN_BODY`, `BOX`, `DRY_BOX`, `CURTAIN_SIDE`, `ISOTHERMAL_BOX`, `REFRIGERATED_BOX`, `FIXED_OPEN_BOX`, `FLATBED`, `FLATBED_WITH_RAMPS`, `LOW_LOADER`, `EXTENDABLE_FLATBED`, `CONTAINER_CHASSIS`, `SWAP_BODY_CARRIER`, `HOOKLIFT_CHASSIS`, `TIPPER`, `REAR_TIPPER`, `THREE_WAY_TIPPER`, `WALKING_FLOOR`, `SILO`, `TANK_LIQUID`, `TANK_FUEL`, `TANK_GAS`, `FOOD_GRADE_TANK`, `CAR_TRANSPORTER`, `LIVESTOCK`, `COIL_CARRIER`, `CONCRETE_MIXER`.

### `VehicleCertificateType`

Valori: `ROADWORTHINESS_INSPECTION`, `TACHOGRAPH_CALIBRATION`, `ATP`, `ADR_VEHICLE_APPROVAL`, `XL_CODE`, `TANK_PERIODIC_INSPECTION`, `CRANE_PERIODIC_INSPECTION`, `TAIL_LIFT_PERIODIC_INSPECTION`, `INSURANCE`, `ROAD_TAX`.

### `VehicleCombinationType`

Valori: `SINGLE_VEHICLE`, `TRUCK_AND_TRAILER`, `ARTICULATED_VEHICLE`.

### `VehicleEquipmentPosition`

Valori: `BEHIND_CAB`, `REAR`, `REAR_PLATFORM`, `CHASSIS`, `ROOF`, `SIDE`, `NOT_APPLICABLE`.

### `VehicleLoadingEquipmentType`

Valori: `HYDRAULIC_CRANE`, `TAIL_LIFT`, `HYDRAULIC_RAMP`, `MANUAL_RAMP`, `HYDRAULIC_WINCH`, `ELECTRIC_WINCH`, `POLYP_GRAPPLE_LOADER`, `REFRIGERATION_UNIT`, `TWIST_LOCK`.

### `VehicleStatus`

Valori: `AVAILABLE`, `ASSIGNED`, `IN_MAINTENANCE`, `OUT_OF_SERVICE`, `RETIRED`.

### `VehicleTechnicalFeature`

Valori: `SELF_STEERING_AXLE`, `STEERING_AXLE`, `LIFTABLE_AXLE`, `DOUBLE_DECK`, `MEGA_VOLUME`, `LOW_DECK`, `AIR_SUSPENSION`, `EXTENDABLE_CHASSIS`, `GOOSENECK`, `LOW_BED_CRADLE`, `TWIST_LOCKS`, `ATP_CERTIFIED`, `XL_CERTIFIED`, `ADR_APPROVED`, `ACTIVE_REFRIGERATION`, `TEMPERATURE_RECORDER`, `HAY_RACKS`, `AGRICULTURAL_APPROVAL`, `STAINLESS_STEEL_TANK`, `FOOD_GRADE_TANK`.

### `VehicleType`

Valori: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`.

### `VehicleUnitType`

Valori: `VAN`, `RIGID_TRUCK`, `TRACTOR_UNIT`, `DRAWBAR_TRAILER`, `CENTER_AXLE_TRAILER`, `SEMI_TRAILER`.

### `VehicleWeightClass`

Valori: `LIGHT_UNDER_3_5T`, `MEDIUM_UP_TO_12T`, `HEAVY_OVER_12T`.

### `WheelConfiguration`

Valori: `SINGLE`, `TWIN`.


## Ragionamento del modello flotta

Il modello flotta non usa una singola enum per descrivere tutto. Un mezzo reale viene composto da più concetti:

```text
VehicleUnitType           -> che unità fisica è
VehicleBodyBaseType       -> che allestimento base ha
VehicleLoadingEquipment   -> quali accessori/moduli ha
VehicleTechnicalFeature   -> quali caratteristiche tecniche possiede
VehicleTechnicalSpecification -> masse, assi, dimensioni, agganci, certificati
VehicleCombinationType    -> come viene usato nel convoglio
```

## VehicleType e VehicleUnitType

`VehicleType` è mantenuto per compatibilità con il codice già esistente. Il significato corretto è però `VehicleUnitType`.

Per i nuovi sviluppi, il ragionamento deve partire da:

```text
VAN
RIGID_TRUCK
TRACTOR_UNIT
DRAWBAR_TRAILER
CENTER_AXLE_TRAILER
SEMI_TRAILER
```

## Allestimento base e configurazione

Gli allestimenti non sono solo categorie fisse. Un mezzo può avere:

```text
base: CURTAIN_SIDE
equipment: TAIL_LIFT
feature: XL_CERTIFIED
```

oppure:

```text
base: REAR_TIPPER
equipment: HYDRAULIC_CRANE at BEHIND_CAB
feature: LIFTABLE_AXLE
```

Questa struttura permette di modellare:

- centinato standard, mega o double deck;
- furgonato secco, isotermico o frigo;
- cassone fisso o ribaltabile;
- cisterna, silo, pianale, portacontainer;
- scarrabile, walking floor, bisarca, porta coils;
- gru, sponda, rampe, verricello, polipo.

## Convogli

`VehicleCombination` rappresenta il mezzo operativo:

```text
SINGLE_VEHICLE        -> furgone/autocarro singolo
TRUCK_AND_TRAILER     -> autotreno
ARTICULATED_VEHICLE   -> bilico / autoarticolato
```

I calcoli tecnici del convoglio stanno in `VehicleCombinationTechnicalRules`.

## Certificati veicolo

I certificati sono modellati con `VehicleCertificate` e `VehicleCertificateType`.

Esempi:

- revisione ministeriale;
- ATP;
- ADR veicolo;
- codice XL;
- taratura tachigrafo;
- verifica cisterna;
- verifica gru/sponda;
- assicurazione e bollo.

## Cose da ricordare

- Peso, assi, dimensioni e certificati non devono stare dentro enum giganti.
- I limiti legali devono essere configurabili tramite profili.
- Gli allestimenti devono rimanere componibili.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/fleet
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
