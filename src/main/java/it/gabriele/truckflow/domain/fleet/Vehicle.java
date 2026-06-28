package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;

import java.util.Objects;

/**
 * Rappresenta un veicolo fisico della flotta.
 * Può essere un furgone, camion, trattore stradale o rimorchio.
 */
public final class Vehicle {

    private static final int MAX_FLEET_NUMBER_LENGTH = 50;
    private static final int MAX_LICENSE_PLATE_LENGTH = 20;
    private static final int VIN_LENGTH = 17;

    private final String fleetNumber;
    private final String licensePlate;
    private final String chassisNumber;
    private final VehicleType type;
    private final VehicleUnitType unitType;
    private final VehicleBodyType bodyType;
    private final VehicleTechnicalSpecification technicalSpecification;
    private final VehicleStatus status;
    private final TireSpecification tireSpecification;
    private final Weight maxPayload;
    private final Dimension cargoSpaceDimension;
    private final TemperatureRange temperatureRange;
    private final Notes notes;

    private Vehicle(
            String fleetNumber,
            String licensePlate,
            String chassisNumber,
            VehicleType type,
            VehicleBodyType bodyType,
            VehicleStatus status,
            TireSpecification tireSpecification,
            Weight maxPayload,
            Dimension cargoSpaceDimension,
            TemperatureRange temperatureRange,
            Notes notes
    ) {
        this(fleetNumber, licensePlate, chassisNumber, type, type == null ? null : type.getUnitType(), bodyType, null,
                status, tireSpecification, maxPayload, cargoSpaceDimension, temperatureRange, notes);
    }

    private Vehicle(
            String fleetNumber,
            String licensePlate,
            String chassisNumber,
            VehicleType type,
            VehicleUnitType unitType,
            VehicleBodyType bodyType,
            VehicleTechnicalSpecification technicalSpecification,
            VehicleStatus status,
            TireSpecification tireSpecification,
            Weight maxPayload,
            Dimension cargoSpaceDimension,
            TemperatureRange temperatureRange,
            Notes notes
    ) {
        this.fleetNumber = validateFleetNumber(fleetNumber);
        this.licensePlate = validateLicensePlate(licensePlate);
        this.chassisNumber = validateChassisNumber(chassisNumber);

        if (type == null) {
            throw new IllegalArgumentException("Il tipo veicolo è obbligatorio.");
        }

        if (bodyType == null) {
            throw new IllegalArgumentException("L'allestimento del veicolo è obbligatorio.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato veicolo è obbligatorio.");
        }

        if (tireSpecification == null) {
            throw new IllegalArgumentException("Le specifiche degli pneumatici sono obbligatorie.");
        }

        if (unitType == null) {
            throw new IllegalArgumentException("Il tipo unità veicolo è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note del veicolo sono obbligatorie.");
        }

        if (technicalSpecification == null) {
            validateBodyType(type, bodyType);
            validateCargoData(type, bodyType, maxPayload, cargoSpaceDimension);
            validateTemperatureData(type, bodyType, temperatureRange);
        } else {
            validateTechnicalSpecification(unitType, technicalSpecification);
        }

        this.type = type;
        this.unitType = unitType;
        this.bodyType = bodyType;
        this.technicalSpecification = technicalSpecification;
        this.status = status;
        this.tireSpecification = tireSpecification;
        this.maxPayload = maxPayload;
        this.cargoSpaceDimension = cargoSpaceDimension;
        this.temperatureRange = temperatureRange;
        this.notes = notes;
    }

    public static Vehicle cargoVehicle(
            String fleetNumber,
            String licensePlate,
            String chassisNumber,
            VehicleType type,
            VehicleBodyType bodyType,
            VehicleStatus status,
            TireSpecification tireSpecification,
            Weight maxPayload,
            Dimension cargoSpaceDimension,
            TemperatureRange temperatureRange,
            Notes notes
    ) {
        return new Vehicle(
                fleetNumber,
                licensePlate,
                chassisNumber,
                type,
                bodyType,
                status,
                tireSpecification,
                maxPayload,
                cargoSpaceDimension,
                temperatureRange,
                notes
        );
    }

    public static Vehicle nonCargoVehicle(
            String fleetNumber,
            String licensePlate,
            String chassisNumber,
            VehicleType type,
            VehicleStatus status,
            TireSpecification tireSpecification,
            Notes notes
    ) {
        return new Vehicle(
                fleetNumber,
                licensePlate,
                chassisNumber,
                type,
                VehicleBodyType.NONE,
                status,
                tireSpecification,
                null,
                null,
                null,
                notes
        );
    }

    public static Vehicle technicalVehicle(
            String fleetNumber,
            String licensePlate,
            String chassisNumber,
            VehicleUnitType unitType,
            VehicleStatus status,
            TireSpecification tireSpecification,
            VehicleTechnicalSpecification technicalSpecification,
            Notes notes
    ) {
        if (unitType == null) {
            throw new IllegalArgumentException("Il tipo unità veicolo è obbligatorio.");
        }
        if (technicalSpecification == null) {
            throw new IllegalArgumentException("La scheda tecnica veicolo è obbligatoria.");
        }
        VehicleType legacyType = legacyTypeFrom(unitType, technicalSpecification.getBodyConfiguration().supportsTemperatureControl());
        VehicleBodyType legacyBodyType = legacyBodyTypeFrom(technicalSpecification.getBodyConfiguration().getBaseType());
        Weight payload = technicalSpecification.getMassSpecification().calculateNetPayload();
        Dimension cargoDimension = technicalSpecification.getDimensionSpecification().getCargoSpaceDimension();
        TemperatureRange temperatureRange = null;

        return new Vehicle(
                fleetNumber,
                licensePlate,
                chassisNumber,
                legacyType,
                unitType,
                legacyBodyType,
                technicalSpecification,
                status,
                tireSpecification,
                payload,
                cargoDimension,
                temperatureRange,
                notes
        );
    }

    private static String validateFleetNumber(String fleetNumber) {
        if (fleetNumber == null) {
            throw new IllegalArgumentException("Il numero flotta è obbligatorio.");
        }

        String normalizedFleetNumber = fleetNumber.trim().toUpperCase();

        if (normalizedFleetNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero flotta non può essere vuoto.");
        }

        if (normalizedFleetNumber.length() > MAX_FLEET_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero flotta non può superare " + MAX_FLEET_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedFleetNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero flotta può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedFleetNumber;
    }

    private static String validateLicensePlate(String licensePlate) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("La targa è obbligatoria.");
        }

        String normalizedLicensePlate = licensePlate.trim().toUpperCase();

        if (normalizedLicensePlate.isEmpty()) {
            throw new IllegalArgumentException("La targa non può essere vuota.");
        }

        if (normalizedLicensePlate.length() > MAX_LICENSE_PLATE_LENGTH) {
            throw new IllegalArgumentException("La targa non può superare " + MAX_LICENSE_PLATE_LENGTH + " caratteri.");
        }

        if (!normalizedLicensePlate.matches("[A-Z0-9 -]+")) {
            throw new IllegalArgumentException("La targa può contenere solo lettere, numeri, spazi e trattini.");
        }

        return normalizedLicensePlate;
    }

    private static String validateChassisNumber(String chassisNumber) {
        if (chassisNumber == null) {
            throw new IllegalArgumentException("Il numero telaio è obbligatorio.");
        }

        String normalizedChassisNumber = chassisNumber.trim().toUpperCase();

        if (normalizedChassisNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero telaio non può essere vuoto.");
        }

        if (normalizedChassisNumber.length() != VIN_LENGTH) {
            throw new IllegalArgumentException("Il numero telaio deve avere " + VIN_LENGTH + " caratteri.");
        }

        if (!normalizedChassisNumber.matches("[A-HJ-NPR-Z0-9]+")) {
            throw new IllegalArgumentException("Il numero telaio può contenere solo lettere e numeri, esclusi I, O e Q.");
        }

        return normalizedChassisNumber;
    }

    private static void validateBodyType(VehicleType type, VehicleBodyType bodyType) {
        if (!type.canCarryCargo() && bodyType != VehicleBodyType.NONE) {
            throw new IllegalArgumentException("Un veicolo non cargo deve avere allestimento NONE.");
        }

        if (type.canCarryCargo() && bodyType == VehicleBodyType.NONE) {
            throw new IllegalArgumentException("Un veicolo cargo deve avere un allestimento cargo.");
        }

        if (type.canCarryCargo() && !bodyType.isCargoBody()) {
            throw new IllegalArgumentException("L'allestimento non è compatibile con un veicolo cargo.");
        }

        if (type.supportsTemperatureControl() && !bodyType.supportsTemperatureControl()) {
            throw new IllegalArgumentException("Un veicolo refrigerato deve avere un allestimento refrigerato.");
        }

    }

    private static void validateCargoData(
            VehicleType type,
            VehicleBodyType bodyType,
            Weight maxPayload,
            Dimension cargoSpaceDimension
    ) {
        if (type.canCarryCargo()) {
            if (maxPayload == null) {
                throw new IllegalArgumentException("La portata massima è obbligatoria per un veicolo cargo.");
            }

            if (cargoSpaceDimension == null) {
                throw new IllegalArgumentException("Le dimensioni dello spazio di carico sono obbligatorie per un veicolo cargo.");
            }

            return;
        }

        if (maxPayload != null || cargoSpaceDimension != null) {
            throw new IllegalArgumentException("Un veicolo non cargo non può avere dati di carico.");
        }

        if (bodyType.isCargoBody()) {
            throw new IllegalArgumentException("Un veicolo non cargo non può avere allestimento cargo.");
        }
    }

    private static void validateTemperatureData(
            VehicleType type,
            VehicleBodyType bodyType,
            TemperatureRange temperatureRange
    ) {
        boolean requiresTemperatureRange = type.supportsTemperatureControl()
                || bodyType.supportsTemperatureControl();

        if (requiresTemperatureRange && temperatureRange == null) {
            throw new IllegalArgumentException("Un veicolo a temperatura controllata deve avere un intervallo di temperatura.");
        }

        if (!requiresTemperatureRange && temperatureRange != null) {
            throw new IllegalArgumentException("Un veicolo non refrigerato non può avere un intervallo di temperatura.");
        }
    }

    private static void validateTechnicalSpecification(
            VehicleUnitType unitType,
            VehicleTechnicalSpecification technicalSpecification
    ) {
        VehicleBodyConfiguration bodyConfiguration = technicalSpecification.getBodyConfiguration();

        if (!unitType.canCarryCargo() && bodyConfiguration.isCargoBody()) {
            throw new IllegalArgumentException("Un'unità non cargo può avere accessori, ma non un allestimento base cargo.");
        }

        if (unitType.canCarryCargo() && !bodyConfiguration.isCargoBody()) {
            throw new IllegalArgumentException("Un'unità cargo deve avere una configurazione allestimento cargo.");
        }

        if (unitType == VehicleUnitType.TRACTOR_UNIT
                && technicalSpecification.getCouplingSpecification().getCouplingType() != CouplingType.FIFTH_WHEEL) {
            throw new IllegalArgumentException("Un trattore stradale deve avere dati ralla/quinta ruota.");
        }

        if (unitType == VehicleUnitType.SEMI_TRAILER
                && !technicalSpecification.getCouplingSpecification().hasFifthWheelData()) {
            throw new IllegalArgumentException("Un semirimorchio deve avere dati kingpin/ralla.");
        }

        if ((unitType == VehicleUnitType.DRAWBAR_TRAILER || unitType == VehicleUnitType.CENTER_AXLE_TRAILER)
                && !technicalSpecification.getCouplingSpecification().hasDrawbarData()) {
            throw new IllegalArgumentException("Un rimorchio deve avere dati timone/occhione.");
        }
    }

    private static VehicleType legacyTypeFrom(VehicleUnitType unitType, boolean temperatureControlled) {
        return switch (unitType) {
            case VAN -> VehicleType.VAN;
            case RIGID_TRUCK -> temperatureControlled ? VehicleType.REFRIGERATED_TRUCK : VehicleType.RIGID_TRUCK;
            case TRACTOR_UNIT -> VehicleType.TRACTOR_UNIT;
            case DRAWBAR_TRAILER -> VehicleType.DRAWBAR_TRAILER;
            case CENTER_AXLE_TRAILER -> VehicleType.CENTER_AXLE_TRAILER;
            case SEMI_TRAILER -> temperatureControlled ? VehicleType.REFRIGERATED_TRAILER : VehicleType.SEMI_TRAILER;
        };
    }

    private static VehicleBodyType legacyBodyTypeFrom(VehicleBodyBaseType baseType) {
        return switch (baseType) {
            case NONE -> VehicleBodyType.NONE;
            case FIXED_OPEN_BOX -> VehicleBodyType.FIXED_OPEN_BOX;
            case REAR_TIPPER -> VehicleBodyType.REAR_TIPPER;
            case THREE_WAY_TIPPER -> VehicleBodyType.THREE_WAY_TIPPER;
            case CURTAIN_SIDE -> VehicleBodyType.CURTAIN_SIDE;
            case DRY_BOX -> VehicleBodyType.DRY_BOX;
            case ISOTHERMAL_BOX -> VehicleBodyType.ISOTHERMAL_BOX;
            case REFRIGERATED_BOX -> VehicleBodyType.REFRIGERATED_BOX;
            case TANK -> VehicleBodyType.TANK_LIQUID;
            case SILO -> VehicleBodyType.SILO;
            case FLATBED, CRANE_PLATFORM -> VehicleBodyType.FLATBED;
            case LOW_LOADER -> VehicleBodyType.LOW_LOADER;
            case CONTAINER_CHASSIS -> VehicleBodyType.CONTAINER_CHASSIS;
            case SWAP_BODY_CARRIER -> VehicleBodyType.SWAP_BODY_CARRIER;
            case HOOKLIFT_CHASSIS -> VehicleBodyType.HOOKLIFT_CHASSIS;
            case WALKING_FLOOR -> VehicleBodyType.WALKING_FLOOR;
            case CAR_TRANSPORTER -> VehicleBodyType.CAR_TRANSPORTER;
            case COIL_CARRIER -> VehicleBodyType.COIL_CARRIER;
            case LIVESTOCK_BODY -> VehicleBodyType.LIVESTOCK;
            case CONCRETE_MIXER -> VehicleBodyType.CONCRETE_MIXER;
        };
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleUnitType getUnitType() {
        return unitType;
    }

    public VehicleTechnicalSpecification getTechnicalSpecification() {
        return technicalSpecification;
    }

    public boolean hasTechnicalSpecification() {
        return technicalSpecification != null;
    }

    public VehicleBodyType getBodyType() {
        return bodyType;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public TireSpecification getTireSpecification() {
        return tireSpecification;
    }

    public Weight getMaxPayload() {
        return maxPayload;
    }

    public Dimension getCargoSpaceDimension() {
        return cargoSpaceDimension;
    }

    public TemperatureRange getTemperatureRange() {
        return temperatureRange;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean canCarryCargo() {
        return unitType.canCarryCargo();
    }

    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }

    public boolean canBeAssigned() {
        return status.canBeAssigned();
    }

    public boolean supportsTemperatureControl() {
        return technicalSpecification != null
                ? technicalSpecification.getBodyConfiguration().supportsTemperatureControl()
                : bodyType.supportsTemperatureControl();
    }

    public boolean isPoweredUnit() {
        return unitType.isPoweredUnit();
    }

    public boolean isTrailer() {
        return unitType.isTowedUnit();
    }

    public boolean hasTankBody() {
        return technicalSpecification != null
                ? technicalSpecification.getBodyConfiguration().getBaseType().isTank()
                : bodyType.isTank();
    }

    public boolean hasFuelTankBody() {
        return bodyType.isFuelTank();
    }

    public boolean hasGasTankBody() {
        return bodyType.isGasTank();
    }

    public boolean hasOpenBody() {
        return bodyType.isOpenBody();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public Volume calculateCargoSpaceVolume() {
        if (!canCarryCargo()) {
            throw new IllegalStateException("Un veicolo non cargo non ha volume di carico.");
        }
        if (technicalSpecification != null) {
            return technicalSpecification.getDimensionSpecification().calculateCargoVolume();
        }
        return cargoSpaceDimension.calculateVolume();
    }

    public boolean canCarryWeight(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Il peso da verificare è obbligatorio.");
        }

        Weight availablePayload = technicalSpecification != null
                ? technicalSpecification.getMassSpecification().calculateNetPayload()
                : maxPayload;
        return canCarryCargo() && weight.isLessThanOrEqualTo(availablePayload);
    }

    public boolean canFitDimension(Dimension dimension) {
        if (dimension == null) {
            throw new IllegalArgumentException("Le dimensioni da verificare sono obbligatorie.");
        }

        Dimension availableCargoSpace = technicalSpecification != null
                ? technicalSpecification.getDimensionSpecification().getCargoSpaceDimension()
                : cargoSpaceDimension;
        return canCarryCargo() && availableCargoSpace != null && dimension.fitsInside(availableCargoSpace);
    }

    public boolean canSupportTemperatureRange(TemperatureRange requiredTemperatureRange) {
        if (requiredTemperatureRange == null) {
            throw new IllegalArgumentException("L'intervallo di temperatura richiesto è obbligatorio.");
        }

        return supportsTemperatureControl()
                && requiredTemperatureRange.isCoveredBy(temperatureRange);
    }

    public String formatSingleLine() {
        return fleetNumber + " - " + licensePlate + " - " + chassisNumber + " - " + unitType + " - " + bodyType + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return fleetNumber.equals(vehicle.fleetNumber)
                && licensePlate.equals(vehicle.licensePlate)
                && chassisNumber.equals(vehicle.chassisNumber)
                && type == vehicle.type
                && unitType == vehicle.unitType
                && bodyType == vehicle.bodyType
                && Objects.equals(technicalSpecification, vehicle.technicalSpecification)
                && status == vehicle.status
                && tireSpecification.equals(vehicle.tireSpecification)
                && Objects.equals(maxPayload, vehicle.maxPayload)
                && Objects.equals(cargoSpaceDimension, vehicle.cargoSpaceDimension)
                && Objects.equals(temperatureRange, vehicle.temperatureRange)
                && notes.equals(vehicle.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                fleetNumber,
                licensePlate,
                chassisNumber,
                type,
                unitType,
                bodyType,
                technicalSpecification,
                status,
                tireSpecification,
                maxPayload,
                cargoSpaceDimension,
                temperatureRange,
                notes
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
