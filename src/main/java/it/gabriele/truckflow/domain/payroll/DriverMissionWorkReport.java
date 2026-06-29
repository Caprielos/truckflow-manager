package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.driver.DriverAdrCertificateType;
import it.gabriele.truckflow.domain.driver.DriverLicenseCategory;
import it.gabriele.truckflow.domain.driver.DriverOperationalQualification;
import it.gabriele.truckflow.domain.driver.DriverProfessionalQualification;
import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.fleet.VehicleCombinationType;
import it.gabriele.truckflow.domain.fleet.VehicleLoadingEquipmentType;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;

/**
 * Riepilogo lavorativo della missione: ore guida/lavoro, indennità, merce, convoglio,
 * allestimento e qualifiche realmente impiegate. È la base per calcolare il costo autista.
 */
public final class DriverMissionWorkReport {

    private static final int MAX_CODE_LENGTH = 50;

    private final String reportCode;
    private final String missionNumber;
    private final Driver driver;
    private final Duration drivingTime;
    private final Duration otherWorkTime;
    private final Duration waitingTime;
    private final Duration loadingUnloadingTime;
    private final Duration overtime;
    private final Duration nightWorkTime;
    private final Duration sundayWorkTime;
    private final Duration holidayWorkTime;
    private final int domesticAllowanceDays;
    private final int internationalAllowanceDays;
    private final int overnightDays;
    private final Set<CargoCategory> cargoCategories;
    private final VehicleCombinationType combinationType;
    private final VehicleBodyBaseType bodyBaseType;
    private final Set<VehicleLoadingEquipmentType> loadingEquipmentTypes;
    private final Notes notes;

    private DriverMissionWorkReport(Builder builder) {
        this.reportCode = validateCode(builder.reportCode, "Il codice report lavoro autista è obbligatorio.");
        this.missionNumber = validateCode(builder.missionNumber, "Il numero missione è obbligatorio.");
        if (builder.driver == null) {
            throw new IllegalArgumentException("L'autista del report lavoro è obbligatorio.");
        }
        this.driver = builder.driver;
        this.drivingTime = validateDuration(builder.drivingTime, "Le ore guida sono obbligatorie.");
        this.otherWorkTime = validateDuration(builder.otherWorkTime, "Le ore altro lavoro sono obbligatorie.");
        this.waitingTime = validateDuration(builder.waitingTime, "Le ore attesa sono obbligatorie.");
        this.loadingUnloadingTime = validateDuration(builder.loadingUnloadingTime, "Le ore carico/scarico sono obbligatorie.");
        this.overtime = validateDuration(builder.overtime, "Le ore straordinario sono obbligatorie.");
        this.nightWorkTime = validateDuration(builder.nightWorkTime, "Le ore notturne sono obbligatorie.");
        this.sundayWorkTime = validateDuration(builder.sundayWorkTime, "Le ore domenicali sono obbligatorie.");
        this.holidayWorkTime = validateDuration(builder.holidayWorkTime, "Le ore festive sono obbligatorie.");
        this.domesticAllowanceDays = validateNonNegative(builder.domesticAllowanceDays, "I giorni diaria nazionale non possono essere negativi.");
        this.internationalAllowanceDays = validateNonNegative(builder.internationalAllowanceDays, "I giorni diaria estera non possono essere negativi.");
        this.overnightDays = validateNonNegative(builder.overnightDays, "I pernottamenti non possono essere negativi.");
        this.cargoCategories = validateSet(builder.cargoCategories, "Le categorie merce non possono contenere null.");
        if (builder.combinationType == null) {
            throw new IllegalArgumentException("Il tipo convoglio è obbligatorio.");
        }
        if (builder.bodyBaseType == null) {
            throw new IllegalArgumentException("Il tipo allestimento è obbligatorio.");
        }
        this.combinationType = builder.combinationType;
        this.bodyBaseType = builder.bodyBaseType;
        this.loadingEquipmentTypes = validateSet(builder.loadingEquipmentTypes, "Le attrezzature di carico non possono contenere null.");
        if (builder.notes == null) {
            throw new IllegalArgumentException("Le note report lavoro autista sono obbligatorie.");
        }
        this.notes = builder.notes;
    }

    public static Builder builder(String reportCode, String missionNumber, Driver driver) {
        return new Builder(reportCode, missionNumber, driver);
    }

    public BigDecimal getDrivingHours() {
        return durationToHours(drivingTime);
    }

    public BigDecimal getOtherWorkHours() {
        return durationToHours(otherWorkTime);
    }

    public BigDecimal getWaitingHours() {
        return durationToHours(waitingTime);
    }

    public BigDecimal getLoadingUnloadingHours() {
        return durationToHours(loadingUnloadingTime);
    }

    public BigDecimal getOvertimeHours() {
        return durationToHours(overtime);
    }

    public BigDecimal getNightWorkHours() {
        return durationToHours(nightWorkTime);
    }

    public BigDecimal getSundayWorkHours() {
        return durationToHours(sundayWorkTime);
    }

    public BigDecimal getHolidayWorkHours() {
        return durationToHours(holidayWorkTime);
    }

    public boolean hasCargoCategory(CargoCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("La categoria merce da verificare è obbligatoria.");
        }
        return cargoCategories.contains(category);
    }

    public boolean transportsAdrCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::requiresAdrData);
    }

    public boolean transportsWaste() {
        return cargoCategories.stream().anyMatch(CargoCategory::isWaste);
    }

    public boolean transportsDangerousWaste() {
        return cargoCategories.contains(CargoCategory.WASTE_DANGEROUS);
    }

    public boolean transportsTemperatureControlledCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::requiresTemperatureControl)
                || bodyBaseType.supportsTemperatureControl();
    }

    public boolean transportsPharmaceuticals() {
        return cargoCategories.contains(CargoCategory.PHARMACEUTICAL);
    }

    public boolean transportsFoodGradeCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::requiresSanitaryOrVeterinaryDocuments);
    }

    public boolean transportsLivestock() {
        return cargoCategories.contains(CargoCategory.LIVESTOCK);
    }

    public boolean transportsHighValueCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::isHighValueOrVehicle);
    }

    public boolean transportsOversizedCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::isOversized)
                || bodyBaseType == VehicleBodyBaseType.LOW_LOADER;
    }

    public boolean transportsBulkCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::isBulk) || bodyBaseType.isBulkBody();
    }

    public boolean transportsLiquidOrTankCargo() {
        return cargoCategories.stream().anyMatch(CargoCategory::isLiquid)
                || bodyBaseType.isTank()
                || bodyBaseType.isLiquidTankCompatible();
    }

    public boolean usesCrane() {
        return loadingEquipmentTypes.contains(VehicleLoadingEquipmentType.HYDRAULIC_CRANE)
                || bodyBaseType == VehicleBodyBaseType.CRANE_PLATFORM;
    }

    public boolean usesTailLift() {
        return loadingEquipmentTypes.contains(VehicleLoadingEquipmentType.TAIL_LIFT);
    }

    public boolean usesArticulatedVehicle() {
        return combinationType == VehicleCombinationType.ARTICULATED_VEHICLE;
    }

    public boolean usesTruckAndTrailer() {
        return combinationType == VehicleCombinationType.TRUCK_AND_TRAILER;
    }

    public boolean usesTrailer() {
        return combinationType.hasTowedUnit();
    }

    public boolean usesRefrigeratedBody() {
        return bodyBaseType == VehicleBodyBaseType.REFRIGERATED_BOX;
    }

    public boolean usesTipperBody() {
        return bodyBaseType == VehicleBodyBaseType.REAR_TIPPER || bodyBaseType == VehicleBodyBaseType.THREE_WAY_TIPPER;
    }

    public boolean usesLowLoaderBody() {
        return bodyBaseType == VehicleBodyBaseType.LOW_LOADER;
    }

    public boolean driverHasLicense(DriverLicenseCategory category) {
        return driver.hasLicenseCategory(category);
    }

    public boolean driverHasProfessionalQualification(DriverProfessionalQualification qualification) {
        return driver.hasProfessionalQualification(qualification);
    }

    public boolean driverHasAdrCertificate(DriverAdrCertificateType adrCertificateType) {
        return driver.hasAdrCertificate(adrCertificateType);
    }

    public boolean driverHasOperationalQualification(DriverOperationalQualification qualification) {
        return driver.hasOperationalQualification(qualification);
    }

    private static BigDecimal durationToHours(Duration duration) {
        return BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }

    private static Duration validateDuration(Duration duration, String nullMessage) {
        if (duration == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (duration.isNegative()) {
            throw new IllegalArgumentException("Le durate del report lavoro non possono essere negative.");
        }
        return duration;
    }

    private static int validateNonNegative(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private static <T> Set<T> validateSet(Set<T> values, String nullElementMessage) {
        if (values == null) {
            throw new IllegalArgumentException("L'insieme del report lavoro è obbligatorio.");
        }
        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(nullElementMessage);
        }
        return Set.copyOf(values);
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getReportCode() {
        return reportCode;
    }

    public String getMissionNumber() {
        return missionNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public Duration getDrivingTime() {
        return drivingTime;
    }

    public Duration getOtherWorkTime() {
        return otherWorkTime;
    }

    public Duration getWaitingTime() {
        return waitingTime;
    }

    public Duration getLoadingUnloadingTime() {
        return loadingUnloadingTime;
    }

    public Duration getOvertime() {
        return overtime;
    }

    public Duration getNightWorkTime() {
        return nightWorkTime;
    }

    public Duration getSundayWorkTime() {
        return sundayWorkTime;
    }

    public Duration getHolidayWorkTime() {
        return holidayWorkTime;
    }

    public int getDomesticAllowanceDays() {
        return domesticAllowanceDays;
    }

    public int getInternationalAllowanceDays() {
        return internationalAllowanceDays;
    }

    public int getOvernightDays() {
        return overnightDays;
    }

    public Set<CargoCategory> getCargoCategories() {
        return cargoCategories;
    }

    public VehicleCombinationType getCombinationType() {
        return combinationType;
    }

    public VehicleBodyBaseType getBodyBaseType() {
        return bodyBaseType;
    }

    public Set<VehicleLoadingEquipmentType> getLoadingEquipmentTypes() {
        return loadingEquipmentTypes;
    }

    public Notes getNotes() {
        return notes;
    }

    public static final class Builder {
        private final String reportCode;
        private final String missionNumber;
        private final Driver driver;
        private Duration drivingTime = Duration.ZERO;
        private Duration otherWorkTime = Duration.ZERO;
        private Duration waitingTime = Duration.ZERO;
        private Duration loadingUnloadingTime = Duration.ZERO;
        private Duration overtime = Duration.ZERO;
        private Duration nightWorkTime = Duration.ZERO;
        private Duration sundayWorkTime = Duration.ZERO;
        private Duration holidayWorkTime = Duration.ZERO;
        private int domesticAllowanceDays;
        private int internationalAllowanceDays;
        private int overnightDays;
        private Set<CargoCategory> cargoCategories = Set.of();
        private VehicleCombinationType combinationType = VehicleCombinationType.SINGLE_VEHICLE;
        private VehicleBodyBaseType bodyBaseType = VehicleBodyBaseType.DRY_BOX;
        private Set<VehicleLoadingEquipmentType> loadingEquipmentTypes = Set.of();
        private Notes notes = Notes.empty();

        private Builder(String reportCode, String missionNumber, Driver driver) {
            this.reportCode = reportCode;
            this.missionNumber = missionNumber;
            this.driver = driver;
        }

        public Builder drivingTime(Duration drivingTime) {
            this.drivingTime = drivingTime;
            return this;
        }

        public Builder otherWorkTime(Duration otherWorkTime) {
            this.otherWorkTime = otherWorkTime;
            return this;
        }

        public Builder waitingTime(Duration waitingTime) {
            this.waitingTime = waitingTime;
            return this;
        }

        public Builder loadingUnloadingTime(Duration loadingUnloadingTime) {
            this.loadingUnloadingTime = loadingUnloadingTime;
            return this;
        }

        public Builder overtime(Duration overtime) {
            this.overtime = overtime;
            return this;
        }

        public Builder nightWorkTime(Duration nightWorkTime) {
            this.nightWorkTime = nightWorkTime;
            return this;
        }

        public Builder sundayWorkTime(Duration sundayWorkTime) {
            this.sundayWorkTime = sundayWorkTime;
            return this;
        }

        public Builder holidayWorkTime(Duration holidayWorkTime) {
            this.holidayWorkTime = holidayWorkTime;
            return this;
        }

        public Builder domesticAllowanceDays(int domesticAllowanceDays) {
            this.domesticAllowanceDays = domesticAllowanceDays;
            return this;
        }

        public Builder internationalAllowanceDays(int internationalAllowanceDays) {
            this.internationalAllowanceDays = internationalAllowanceDays;
            return this;
        }

        public Builder overnightDays(int overnightDays) {
            this.overnightDays = overnightDays;
            return this;
        }

        public Builder cargoCategories(Set<CargoCategory> cargoCategories) {
            this.cargoCategories = cargoCategories;
            return this;
        }

        public Builder vehicleContext(VehicleCombinationType combinationType, VehicleBodyBaseType bodyBaseType) {
            this.combinationType = combinationType;
            this.bodyBaseType = bodyBaseType;
            return this;
        }

        public Builder loadingEquipmentTypes(Set<VehicleLoadingEquipmentType> loadingEquipmentTypes) {
            this.loadingEquipmentTypes = loadingEquipmentTypes;
            return this;
        }

        public Builder notes(Notes notes) {
            this.notes = notes;
            return this;
        }

        public DriverMissionWorkReport build() {
            return new DriverMissionWorkReport(this);
        }
    }
}
