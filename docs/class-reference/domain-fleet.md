# Package `domain.fleet`

Flotta: veicoli, rimorchi, convogli, allestimenti, specifiche tecniche, certificati e limiti.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| AxleSteeringType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| BrakeSafetySystem | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| BrakeType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| CouplingType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| DeadlineStatus | enum | Enum di stato del ciclo di vita. | - |
| KingpinDiameter | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| RetarderType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| SuspensionType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| TireSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getBrand, getModel, getSize, getLoadIndex, getSpeedRating, formatSingleLine, equals, hashCode, toString |
| TransmissionType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| Vehicle | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | cargoVehicle, nonCargoVehicle, technicalVehicle, getFleetNumber, getLicensePlate, getChassisNumber, getType, getUnitType, getTechnicalSpecification, hasTechnicalSpecification |
| VehicleAxle | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getAxleNumber, getWheelConfiguration, isLiftable, getSteeringType, isSteering, equals, hashCode |
| VehicleAxleSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getAxles, getAxleCount, countLiftableAxles, countSteeringAxles, getSuspensionType, getBrakeType, getBrakeSafetySystems, getAxleBrand |
| VehicleBodyBaseType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCargoBody, supportsTemperatureControl, isTank, isLiquidTankCompatible, isBulkBody, isOpenBody |
| VehicleBodyCompatibilityRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.fleet. | isBodyCompatibleWithCargoLoad, supportsCargoCategories, supportsTemperatureRequirements, supportsAdrTankRequirements |
| VehicleBodyConfiguration | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, baseOnly, none, getBaseType, getLoadingEquipment, getTechnicalFeatures, hasEquipment, hasFeature, supportsTemperatureControl, isCargoBody |
| VehicleBodyType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCargoBody, supportsTemperatureControl, isTank, isLiquidTank, isGasTank, isFuelTank, isBulkDryBody, isOpenBody |
| VehicleCertificate | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getType, getValidFrom, getExpiresAt, getNotes, isValidOn, calculateStatus, equals, hashCode |
| VehicleCertificateType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| VehicleCombination | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | singleVehicle, withTrailer, getCombinationNumber, getPoweredUnit, getTrailer, getNotes, hasTrailer, getCombinationType, calculateTotalAxleCount, calculateGrossCombinationWeight |
| VehicleCombinationLegalLimitProfile | class | Profilo che raggruppa informazioni tecniche, operative o economiche. | italianStandardAutotreno, italianStandardRefrigeratedAutotreno, getMaximumGrossCombinationWeight, getMaximumExternalDimension |
| VehicleCombinationRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.fleet. | hasEnoughWeightCapacity, hasEnoughVolumeCapacity, hasEnoughSpaceForEveryItem, supportsRequiredTemperature, canPhysicallyCarry, canBeAssignedToCargoLoad, canBeAssignedToShipment |
| VehicleCombinationTechnicalRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.fleet. | calculateGrossCombinationWeight, calculateTotalTareWeight, calculateTotalPayload, isWithinGrossWeightLimit, canTow, isWithinExternalDimensionLimit |
| VehicleCombinationType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | hasTowedUnit, requiresTrailerLicense |
| VehicleCouplingSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | none, fifthWheel, drawbar, getCouplingType, getFifthWheelHeightMeters, getKingpinDiameter, getDrawbarLengthMeters, getDrawbarEyeType, hasFifthWheelData, hasDrawbarData |
| VehicleDimensionSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getExternalDimension, getCargoSpaceDimension, getLoadFloorHeightMeters, getEpalCapacity, hasCargoSpace, calculateCargoVolume, estimateEpalCapacity, equals, hashCode |
| VehicleEquipmentPosition | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| VehicleLoadingEquipment | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getType, getPosition, getCapacity, getNotes, isType, equals, hashCode |
| VehicleLoadingEquipmentType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | requiresOperatorQualification |
| VehicleMassSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getGrossVehicleWeight, getTareWeight, getMaxTowableWeight, getMaximumFifthWheelLoad, calculateNetPayload, calculateWeightClass, canTow, equals, hashCode |
| VehicleStatus | enum | Enum di stato del ciclo di vita. | canBeAssigned |
| VehicleTechnicalFeature | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| VehicleTechnicalSpecification | class | Classe del package domain.fleet; rappresenta un concetto del modello TruckFlow. | of, getMassSpecification, getDimensionSpecification, getAxleSpecification, getCouplingSpecification, getBodyConfiguration, getCertificates, hasValidCertificate |
| VehicleType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | getUnitType, canCarryCargo, supportsTemperatureControl, isPoweredUnit, isTrailer, isSemiTrailer, isDrawbarTrailer |
| VehicleUnitType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isPoweredUnit, canCarryCargo, isTowedUnit, isTrailer, isSemiTrailer |
| VehicleWeightClass | enum | Enum: insieme chiuso di valori ammessi dal dominio. | fromGrossWeight, requiresHeavyGoodsLicense |
| WheelConfiguration | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
