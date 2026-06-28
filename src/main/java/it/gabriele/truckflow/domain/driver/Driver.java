package it.gabriele.truckflow.domain.driver;

import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Rappresenta un autista dell'azienda.
 * L'autista è una risorsa operativa, non un account utente.
 */
public final class Driver {

    private static final int MAX_DRIVER_CODE_LENGTH = 50;
    private static final int MAX_FULL_NAME_LENGTH = 150;

    private final String driverCode;
    private final String fullName;
    private final DriverStatus status;
    private final Set<DriverLicenseCategory> licenseCategories;
    private final Set<DriverProfessionalQualification> professionalQualifications;
    private final Set<DriverAdrCertificateType> adrCertificates;
    private final Set<DriverOperationalQualification> operationalQualifications;
    private final List<DriverCertificate> certificates;
    private final Notes notes;

    private Driver(
            String driverCode,
            String fullName,
            DriverStatus status,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        this(driverCode, fullName, status, licenseCategories, professionalQualifications, adrCertificates, operationalQualifications, notes, List.of());
    }

    private Driver(
            String driverCode,
            String fullName,
            DriverStatus status,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes,
            List<DriverCertificate> certificates
    ) {
        this.driverCode = validateDriverCode(driverCode);
        this.fullName = validateFullName(fullName);

        if (status == null) {
            throw new IllegalArgumentException("Lo stato dell'autista è obbligatorio.");
        }

        this.licenseCategories = validateRequiredSet(
                licenseCategories,
                "Le patenti dell'autista sono obbligatorie.",
                "L'autista deve avere almeno una patente.",
                "Le patenti dell'autista non possono contenere valori nulli."
        );

        this.professionalQualifications = validateOptionalSet(
                professionalQualifications,
                "Le qualifiche professionali dell'autista sono obbligatorie.",
                "Le qualifiche professionali dell'autista non possono contenere valori nulli."
        );

        this.adrCertificates = validateOptionalSet(
                adrCertificates,
                "Le abilitazioni ADR dell'autista sono obbligatorie.",
                "Le abilitazioni ADR dell'autista non possono contenere valori nulli."
        );

        this.operationalQualifications = validateOptionalSet(
                operationalQualifications,
                "Le qualifiche operative dell'autista sono obbligatorie.",
                "Le qualifiche operative dell'autista non possono contenere valori nulli."
        );

        if (notes == null) {
            throw new IllegalArgumentException("Le note dell'autista sono obbligatorie.");
        }

        if (certificates == null) {
            throw new IllegalArgumentException("I certificati dell'autista sono obbligatori.");
        }
        if (certificates.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I certificati dell'autista non possono contenere valori nulli.");
        }

        this.status = status;
        this.certificates = List.copyOf(certificates);
        this.notes = notes;
    }

    public static Driver available(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.AVAILABLE,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }


    public static Driver availableWithCertificates(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes,
            List<DriverCertificate> certificates
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.AVAILABLE,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes,
                certificates
        );
    }

    public static Driver assigned(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.ASSIGNED,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }

    public static Driver onLeave(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.ON_LEAVE,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }

    public static Driver suspended(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.SUSPENDED,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }

    public static Driver inactive(
            String driverCode,
            String fullName,
            Set<DriverLicenseCategory> licenseCategories,
            Set<DriverProfessionalQualification> professionalQualifications,
            Set<DriverAdrCertificateType> adrCertificates,
            Set<DriverOperationalQualification> operationalQualifications,
            Notes notes
    ) {
        return new Driver(
                driverCode,
                fullName,
                DriverStatus.INACTIVE,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }

    private static String validateDriverCode(String driverCode) {
        if (driverCode == null) {
            throw new IllegalArgumentException("Il codice autista è obbligatorio.");
        }

        String normalizedDriverCode = driverCode.trim().toUpperCase();

        if (normalizedDriverCode.isEmpty()) {
            throw new IllegalArgumentException("Il codice autista non può essere vuoto.");
        }

        if (normalizedDriverCode.length() > MAX_DRIVER_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice autista non può superare " + MAX_DRIVER_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedDriverCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice autista può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedDriverCode;
    }

    private static String validateFullName(String fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("Il nome completo dell'autista è obbligatorio.");
        }

        String normalizedFullName = fullName.trim();

        if (normalizedFullName.isEmpty()) {
            throw new IllegalArgumentException("Il nome completo dell'autista non può essere vuoto.");
        }

        if (normalizedFullName.length() > MAX_FULL_NAME_LENGTH) {
            throw new IllegalArgumentException("Il nome completo dell'autista non può superare " + MAX_FULL_NAME_LENGTH + " caratteri.");
        }

        return normalizedFullName;
    }

    private static <T> Set<T> validateRequiredSet(
            Set<T> values,
            String nullMessage,
            String emptyMessage,
            String nullElementMessage
    ) {
        if (values == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (values.isEmpty()) {
            throw new IllegalArgumentException(emptyMessage);
        }

        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(nullElementMessage);
        }

        return Set.copyOf(values);
    }

    private static <T> Set<T> validateOptionalSet(
            Set<T> values,
            String nullMessage,
            String nullElementMessage
    ) {
        if (values == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(nullElementMessage);
        }

        return Set.copyOf(values);
    }

    public String getDriverCode() {
        return driverCode;
    }

    public String getFullName() {
        return fullName;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public Set<DriverLicenseCategory> getLicenseCategories() {
        return licenseCategories;
    }

    public Set<DriverProfessionalQualification> getProfessionalQualifications() {
        return professionalQualifications;
    }

    public Set<DriverAdrCertificateType> getAdrCertificates() {
        return adrCertificates;
    }

    public Set<DriverOperationalQualification> getOperationalQualifications() {
        return operationalQualifications;
    }

    public List<DriverCertificate> getCertificates() {
        return certificates;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isAvailable() {
        return status == DriverStatus.AVAILABLE;
    }

    public boolean isAssigned() {
        return status == DriverStatus.ASSIGNED;
    }

    public boolean canBeAssigned() {
        return status.canBeAssigned();
    }

    public boolean hasLicenseCategory(DriverLicenseCategory licenseCategory) {
        if (licenseCategory == null) {
            throw new IllegalArgumentException("La categoria di patente da verificare è obbligatoria.");
        }

        return licenseCategories.contains(licenseCategory);
    }

    public boolean hasAllLicenseCategories(Set<DriverLicenseCategory> requiredLicenseCategories) {
        validateRequiredCheckSet(
                requiredLicenseCategories,
                "Le categorie di patente richieste sono obbligatorie.",
                "Le categorie di patente richieste non possono contenere valori nulli."
        );

        return licenseCategories.containsAll(requiredLicenseCategories);
    }

    public boolean hasProfessionalQualification(DriverProfessionalQualification professionalQualification) {
        if (professionalQualification == null) {
            throw new IllegalArgumentException("La qualifica professionale da verificare è obbligatoria.");
        }

        return professionalQualifications.contains(professionalQualification);
    }

    public boolean hasAdrCertificate(DriverAdrCertificateType adrCertificateType) {
        if (adrCertificateType == null) {
            throw new IllegalArgumentException("L'abilitazione ADR da verificare è obbligatoria.");
        }

        return adrCertificates.contains(adrCertificateType);
    }

    public boolean hasOperationalQualification(DriverOperationalQualification operationalQualification) {
        if (operationalQualification == null) {
            throw new IllegalArgumentException("La qualifica operativa da verificare è obbligatoria.");
        }

        return operationalQualifications.contains(operationalQualification);
    }

    public boolean hasCertificate(DriverCertificateType certificateType) {
        if (certificateType == null) {
            throw new IllegalArgumentException("Il tipo certificato da verificare è obbligatorio.");
        }
        return certificates.stream().anyMatch(certificate -> certificate.getType() == certificateType);
    }

    public boolean hasValidCertificate(DriverCertificateType certificateType, LocalDate date) {
        if (certificateType == null) {
            throw new IllegalArgumentException("Il tipo certificato da verificare è obbligatorio.");
        }
        if (date == null) {
            throw new IllegalArgumentException("La data verifica certificato è obbligatoria.");
        }
        return certificates.stream().anyMatch(certificate -> certificate.getType() == certificateType && certificate.isValidOn(date));
    }

    public boolean hasExpiringCertificateWithin(LocalDate date, int warningDays) {
        if (date == null) {
            throw new IllegalArgumentException("La data verifica certificato è obbligatoria.");
        }
        if (warningDays < 0) {
            throw new IllegalArgumentException("I giorni di preavviso non possono essere negativi.");
        }
        return certificates.stream().anyMatch(certificate -> certificate.expiresWithin(date, warningDays));
    }

    public boolean canDriveLightVehicle() {
        return hasLicenseCategory(DriverLicenseCategory.B)
                || hasLicenseCategory(DriverLicenseCategory.C1)
                || hasLicenseCategory(DriverLicenseCategory.C)
                || hasLicenseCategory(DriverLicenseCategory.CE);
    }

    public boolean canDriveRigidTruck() {
        return hasLicenseCategory(DriverLicenseCategory.C1)
                || hasLicenseCategory(DriverLicenseCategory.C)
                || hasLicenseCategory(DriverLicenseCategory.CE);
    }

    public boolean canDriveVehicleCombinationWithTrailer() {
        return hasLicenseCategory(DriverLicenseCategory.CE)
                || hasLicenseCategory(DriverLicenseCategory.C1E)
                || hasLicenseCategory(DriverLicenseCategory.BE)
                || (hasLicenseCategory(DriverLicenseCategory.C)
                && hasLicenseCategory(DriverLicenseCategory.E));
    }

    public boolean hasGoodsCqc() {
        return hasProfessionalQualification(DriverProfessionalQualification.CQC_GOODS);
    }

    public boolean hasAdrBasicCertificate() {
        return hasAdrCertificate(DriverAdrCertificateType.ADR_BASIC);
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    private static <T> void validateRequiredCheckSet(
            Set<T> values,
            String nullMessage,
            String nullElementMessage
    ) {
        if (values == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(nullElementMessage);
        }
    }

    public String formatSingleLine() {
        return driverCode + " - " + fullName + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver driver)) return false;
        return driverCode.equals(driver.driverCode)
                && fullName.equals(driver.fullName)
                && status == driver.status
                && licenseCategories.equals(driver.licenseCategories)
                && professionalQualifications.equals(driver.professionalQualifications)
                && adrCertificates.equals(driver.adrCertificates)
                && operationalQualifications.equals(driver.operationalQualifications)
                && certificates.equals(driver.certificates)
                && notes.equals(driver.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                driverCode,
                fullName,
                status,
                licenseCategories,
                professionalQualifications,
                adrCertificates,
                operationalQualifications,
                notes
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
