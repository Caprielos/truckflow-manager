# `domain/fleet`

Mezzi, rimorchi, convogli, schede tecniche, assi, allestimenti e certificati veicolo.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `AxleSteeringType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `BrakeSafetySystem` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `BrakeType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `CouplingType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `DeadlineStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `KingpinDiameter` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `RetarderType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `SuspensionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `TireSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | MAX_BRAND_LENGTH, MAX_MODEL_LENGTH, MAX_SIZE_LENGTH, MAX_SPEED_RATING_LENGTH, brand, model, size, loadIndex | of, getBrand, getModel, getSize, getLoadIndex, getSpeedRating, formatSingleLine, equals |
| `TransmissionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `Vehicle` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | MAX_FLEET_NUMBER_LENGTH, MAX_LICENSE_PLATE_LENGTH, VIN_LENGTH, fleetNumber, licensePlate, chassisNumber, type, unitType | cargoVehicle, nonCargoVehicle, technicalVehicle, getFleetNumber, getLicensePlate, getChassisNumber, getType, getUnitType |
| `VehicleAxle` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | axleNumber, wheelConfiguration, liftable, steeringType | of, getAxleNumber, getWheelConfiguration, isLiftable, getSteeringType, isSteering, equals, hashCode |
| `VehicleAxleSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | axles, suspensionType, brakeType, brakeSafetySystems, axleBrand | of, getAxles, getAxleCount, countLiftableAxles, countSteeringAxles, getSuspensionType, getBrakeType, getBrakeSafetySystems |
| `VehicleBodyBaseType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | cargoBody, temperatureControlled, tank, liquidTankCompatible, bulkBody, openBody | isCargoBody, supportsTemperatureControl, isTank, isLiquidTankCompatible, isBulkBody, isOpenBody |
| `VehicleBodyCompatibilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | category, bodyType | isBodyCompatibleWithCargoLoad, supportsCargoCategories, supportsTemperatureRequirements, supportsAdrTankRequirements |
| `VehicleBodyConfiguration` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | baseType, loadingEquipment, technicalFeatures | of, baseOnly, none, getBaseType, getLoadingEquipment, getTechnicalFeatures, hasEquipment, hasFeature |
| `VehicleBodyType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | cargoBody, temperatureControlled, tank, liquidTank, bulkDryBody, openBody | isCargoBody, supportsTemperatureControl, isTank, isLiquidTank, isGasTank, isFuelTank, isBulkDryBody, isOpenBody |
| `VehicleCertificate` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | type, validFrom, expiresAt, notes | of, getType, getValidFrom, getExpiresAt, getNotes, isValidOn, calculateStatus, equals |
| `VehicleCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `VehicleCombination` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | MAX_COMBINATION_NUMBER_LENGTH, combinationNumber, poweredUnit, trailer, notes | singleVehicle, withTrailer, getCombinationNumber, getPoweredUnit, getTrailer, getNotes, hasTrailer, getCombinationType |
| `VehicleCombinationLegalLimitProfile` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | maximumGrossCombinationWeight, maximumExternalDimension | italianStandardAutotreno, italianStandardRefrigeratedAutotreno, getMaximumGrossCombinationWeight, getMaximumExternalDimension |
| `VehicleCombinationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | hasEnoughWeightCapacity, hasEnoughVolumeCapacity, hasEnoughSpaceForEveryItem, supportsRequiredTemperature, canPhysicallyCarry, canBeAssignedToCargoLoad, canBeAssignedToShipment |
| `VehicleCombinationTechnicalRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | calculateGrossCombinationWeight, calculateTotalTareWeight, calculateTotalPayload, isWithinGrossWeightLimit, canTow, isWithinExternalDimensionLimit |
| `VehicleCombinationType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | hasTowedUnit, requiresTrailerLicense |
| `VehicleCouplingSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | couplingType, fifthWheelHeightMeters, kingpinDiameter, drawbarLengthMeters, drawbarEyeType | none, fifthWheel, drawbar, getCouplingType, getFifthWheelHeightMeters, getKingpinDiameter, getDrawbarLengthMeters, getDrawbarEyeType |
| `VehicleDimensionSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | externalDimension, cargoSpaceDimension, loadFloorHeightMeters, epalCapacity | of, getExternalDimension, getCargoSpaceDimension, getLoadFloorHeightMeters, getEpalCapacity, hasCargoSpace, calculateCargoVolume, estimateEpalCapacity |
| `VehicleEquipmentPosition` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `VehicleLoadingEquipment` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | type, position, capacity, notes | of, getType, getPosition, getCapacity, getNotes, isType, equals, hashCode |
| `VehicleLoadingEquipmentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | requiresOperatorQualification |
| `VehicleMassSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | grossVehicleWeight, tareWeight, maxTowableWeight, maximumFifthWheelLoad | of, getGrossVehicleWeight, getTareWeight, getMaxTowableWeight, getMaximumFifthWheelLoad, calculateNetPayload, calculateWeightClass, canTow |
| `VehicleStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | assignable | canBeAssigned |
| `VehicleTechnicalFeature` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `VehicleTechnicalSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | massSpecification, dimensionSpecification, axleSpecification, couplingSpecification, bodyConfiguration, certificates | of, getMassSpecification, getDimensionSpecification, getAxleSpecification, getCouplingSpecification, getBodyConfiguration, getCertificates, hasValidCertificate |
| `VehicleType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | unitType, legacyTemperatureControlledType | getUnitType, canCarryCargo, supportsTemperatureControl, isPoweredUnit, isTrailer, isSemiTrailer, isDrawbarTrailer |
| `VehicleUnitType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | poweredUnit, cargoCapable, towedUnit | isPoweredUnit, canCarryCargo, isTowedUnit, isTrailer, isSemiTrailer |
| `VehicleWeightClass` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | minExclusiveKg, maxInclusiveKg | fromGrossWeight, requiresHeavyGoodsLicense |
| `WheelConfiguration` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
