# Domain `fleet` spiegato

Mezzi, rimorchi, convogli, schede tecniche, assi, allestimenti e certificati veicolo.

## Classi principali

### `AxleSteeringType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `BrakeSafetySystem`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `BrakeType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `CouplingType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `DeadlineStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `KingpinDiameter`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `RetarderType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `SuspensionType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `TireSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_BRAND_LENGTH`
- `MAX_MODEL_LENGTH`
- `MAX_SIZE_LENGTH`
- `MAX_SPEED_RATING_LENGTH`
- `brand`
- `model`
- `size`
- `loadIndex`
- `speedRating`

Metodi pubblici principali:

- `of()`
- `getBrand()`
- `getModel()`
- `getSize()`
- `getLoadIndex()`
- `getSpeedRating()`
- `formatSingleLine()`
- `equals()`
- `hashCode()`
- `toString()`

### `TransmissionType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `Vehicle`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_FLEET_NUMBER_LENGTH`
- `MAX_LICENSE_PLATE_LENGTH`
- `VIN_LENGTH`
- `fleetNumber`
- `licensePlate`
- `chassisNumber`
- `type`
- `unitType`
- `bodyType`
- `technicalSpecification`
- `status`
- `tireSpecification`

Metodi pubblici principali:

- `cargoVehicle()`
- `nonCargoVehicle()`
- `technicalVehicle()`
- `getFleetNumber()`
- `getLicensePlate()`
- `getChassisNumber()`
- `getType()`
- `getUnitType()`
- `getTechnicalSpecification()`
- `hasTechnicalSpecification()`
- `getBodyType()`
- `getStatus()`

### `VehicleAxle`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `axleNumber`
- `wheelConfiguration`
- `liftable`
- `steeringType`

Metodi pubblici principali:

- `of()`
- `getAxleNumber()`
- `getWheelConfiguration()`
- `isLiftable()`
- `getSteeringType()`
- `isSteering()`
- `equals()`
- `hashCode()`

### `VehicleAxleSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `axles`
- `suspensionType`
- `brakeType`
- `brakeSafetySystems`
- `axleBrand`

Metodi pubblici principali:

- `of()`
- `getAxles()`
- `getAxleCount()`
- `countLiftableAxles()`
- `countSteeringAxles()`
- `getSuspensionType()`
- `getBrakeType()`
- `getBrakeSafetySystems()`
- `getAxleBrand()`

### `VehicleBodyBaseType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `cargoBody`
- `temperatureControlled`
- `tank`
- `liquidTankCompatible`
- `bulkBody`
- `openBody`

Metodi pubblici principali:

- `isCargoBody()`
- `supportsTemperatureControl()`
- `isTank()`
- `isLiquidTankCompatible()`
- `isBulkBody()`
- `isOpenBody()`

### `VehicleBodyCompatibilityRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Campi principali:

- `category`
- `bodyType`

Metodi pubblici principali:

- `isBodyCompatibleWithCargoLoad()`
- `supportsCargoCategories()`
- `supportsTemperatureRequirements()`
- `supportsAdrTankRequirements()`

### `VehicleBodyConfiguration`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `baseType`
- `loadingEquipment`
- `technicalFeatures`

Metodi pubblici principali:

- `of()`
- `baseOnly()`
- `none()`
- `getBaseType()`
- `getLoadingEquipment()`
- `getTechnicalFeatures()`
- `hasEquipment()`
- `hasFeature()`
- `supportsTemperatureControl()`
- `isCargoBody()`
- `equals()`
- `hashCode()`

### `VehicleBodyType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `cargoBody`
- `temperatureControlled`
- `tank`
- `liquidTank`
- `bulkDryBody`
- `openBody`

Metodi pubblici principali:

- `isCargoBody()`
- `supportsTemperatureControl()`
- `isTank()`
- `isLiquidTank()`
- `isGasTank()`
- `isFuelTank()`
- `isBulkDryBody()`
- `isOpenBody()`

### `VehicleCertificate`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `type`
- `validFrom`
- `expiresAt`
- `notes`

Metodi pubblici principali:

- `of()`
- `getType()`
- `getValidFrom()`
- `getExpiresAt()`
- `getNotes()`
- `isValidOn()`
- `calculateStatus()`
- `equals()`
- `hashCode()`

### `VehicleCertificateType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `VehicleCombination`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `MAX_COMBINATION_NUMBER_LENGTH`
- `combinationNumber`
- `poweredUnit`
- `trailer`
- `notes`

Metodi pubblici principali:

- `singleVehicle()`
- `withTrailer()`
- `getCombinationNumber()`
- `getPoweredUnit()`
- `getTrailer()`
- `getNotes()`
- `hasTrailer()`
- `getCombinationType()`
- `calculateTotalAxleCount()`
- `calculateGrossCombinationWeight()`
- `findNextCertificateDeadline()`
- `getCargoUnit()`

### `VehicleCombinationLegalLimitProfile`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `maximumGrossCombinationWeight`
- `maximumExternalDimension`

Metodi pubblici principali:

- `italianStandardAutotreno()`
- `italianStandardRefrigeratedAutotreno()`
- `getMaximumGrossCombinationWeight()`
- `getMaximumExternalDimension()`

### `VehicleCombinationRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `hasEnoughWeightCapacity()`
- `hasEnoughVolumeCapacity()`
- `hasEnoughSpaceForEveryItem()`
- `supportsRequiredTemperature()`
- `canPhysicallyCarry()`
- `canBeAssignedToCargoLoad()`
- `canBeAssignedToShipment()`

### `VehicleCombinationTechnicalRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `calculateGrossCombinationWeight()`
- `calculateTotalTareWeight()`
- `calculateTotalPayload()`
- `isWithinGrossWeightLimit()`
- `canTow()`
- `isWithinExternalDimensionLimit()`

### `VehicleCombinationType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `hasTowedUnit()`
- `requiresTrailerLicense()`

### `VehicleCouplingSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `couplingType`
- `fifthWheelHeightMeters`
- `kingpinDiameter`
- `drawbarLengthMeters`
- `drawbarEyeType`

Metodi pubblici principali:

- `none()`
- `fifthWheel()`
- `drawbar()`
- `getCouplingType()`
- `getFifthWheelHeightMeters()`
- `getKingpinDiameter()`
- `getDrawbarLengthMeters()`
- `getDrawbarEyeType()`
- `hasFifthWheelData()`
- `hasDrawbarData()`
- `equals()`
- `hashCode()`

### `VehicleDimensionSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `externalDimension`
- `cargoSpaceDimension`
- `loadFloorHeightMeters`
- `epalCapacity`

Metodi pubblici principali:

- `of()`
- `getExternalDimension()`
- `getCargoSpaceDimension()`
- `getLoadFloorHeightMeters()`
- `getEpalCapacity()`
- `hasCargoSpace()`
- `calculateCargoVolume()`
- `estimateEpalCapacity()`
- `equals()`
- `hashCode()`

### `VehicleEquipmentPosition`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `VehicleLoadingEquipment`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `type`
- `position`
- `capacity`
- `notes`

Metodi pubblici principali:

- `of()`
- `getType()`
- `getPosition()`
- `getCapacity()`
- `getNotes()`
- `isType()`
- `equals()`
- `hashCode()`

### `VehicleLoadingEquipmentType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `requiresOperatorQualification()`

### `VehicleMassSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `grossVehicleWeight`
- `tareWeight`
- `maxTowableWeight`
- `maximumFifthWheelLoad`

Metodi pubblici principali:

- `of()`
- `getGrossVehicleWeight()`
- `getTareWeight()`
- `getMaxTowableWeight()`
- `getMaximumFifthWheelLoad()`
- `calculateNetPayload()`
- `calculateWeightClass()`
- `canTow()`
- `equals()`
- `hashCode()`

### `VehicleStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `assignable`

Metodi pubblici principali:

- `canBeAssigned()`

### `VehicleTechnicalFeature`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `VehicleTechnicalSpecification`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `massSpecification`
- `dimensionSpecification`
- `axleSpecification`
- `couplingSpecification`
- `bodyConfiguration`
- `certificates`

Metodi pubblici principali:

- `of()`
- `getMassSpecification()`
- `getDimensionSpecification()`
- `getAxleSpecification()`
- `getCouplingSpecification()`
- `getBodyConfiguration()`
- `getCertificates()`
- `hasValidCertificate()`

### `VehicleType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `unitType`
- `legacyTemperatureControlledType`

Metodi pubblici principali:

- `getUnitType()`
- `canCarryCargo()`
- `supportsTemperatureControl()`
- `isPoweredUnit()`
- `isTrailer()`
- `isSemiTrailer()`
- `isDrawbarTrailer()`

### `VehicleUnitType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `poweredUnit`
- `cargoCapable`
- `towedUnit`

Metodi pubblici principali:

- `isPoweredUnit()`
- `canCarryCargo()`
- `isTowedUnit()`
- `isTrailer()`
- `isSemiTrailer()`

### `VehicleWeightClass`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `minExclusiveKg`
- `maxInclusiveKg`

Metodi pubblici principali:

- `fromGrossWeight()`
- `requiresHeavyGoodsLicense()`

### `WheelConfiguration`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
