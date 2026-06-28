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
    private final VehicleBodyType bodyType;
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

        if (notes == null) {
            throw new IllegalArgumentException("Le note del veicolo sono obbligatorie.");
        }

        validateBodyType(type, bodyType);
        validateCargoData(type, bodyType, maxPayload, cargoSpaceDimension);
        validateTemperatureData(type, bodyType, temperatureRange);

        this.type = type;
        this.bodyType = bodyType;
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

        if (!type.supportsTemperatureControl() && bodyType.supportsTemperatureControl()) {
            throw new IllegalArgumentException("Un allestimento refrigerato richiede un tipo veicolo refrigerato.");
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
        return type.canCarryCargo();
    }

    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }

    public boolean canBeAssigned() {
        return status.canBeAssigned();
    }

    public boolean supportsTemperatureControl() {
        return bodyType.supportsTemperatureControl();
    }

    public boolean isPoweredUnit() {
        return type.isPoweredUnit();
    }

    public boolean isTrailer() {
        return type.isTrailer();
    }

    public boolean hasTankBody() {
        return bodyType.isTank();
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

        return cargoSpaceDimension.calculateVolume();
    }

    public boolean canCarryWeight(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Il peso da verificare è obbligatorio.");
        }

        return canCarryCargo() && weight.isLessThanOrEqualTo(maxPayload);
    }

    public boolean canFitDimension(Dimension dimension) {
        if (dimension == null) {
            throw new IllegalArgumentException("Le dimensioni da verificare sono obbligatorie.");
        }

        return canCarryCargo() && dimension.fitsInside(cargoSpaceDimension);
    }

    public boolean canSupportTemperatureRange(TemperatureRange requiredTemperatureRange) {
        if (requiredTemperatureRange == null) {
            throw new IllegalArgumentException("L'intervallo di temperatura richiesto è obbligatorio.");
        }

        return supportsTemperatureControl()
                && requiredTemperatureRange.isCoveredBy(temperatureRange);
    }

    public String formatSingleLine() {
        return fleetNumber + " - " + licensePlate + " - " + chassisNumber + " - " + type + " - " + bodyType + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return fleetNumber.equals(vehicle.fleetNumber)
                && licensePlate.equals(vehicle.licensePlate)
                && chassisNumber.equals(vehicle.chassisNumber)
                && type == vehicle.type
                && bodyType == vehicle.bodyType
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
                bodyType,
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
