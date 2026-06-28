package it.gabriele.truckflow.domain.driver;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Driver con patenti, CQC, ADR e qualifiche operative separate.
 */
class DriverTest {

    @Test
    void shouldCreateAvailableDriver() {
        Driver driver = standardAvailableDriver();

        assertEquals("DRV-001", driver.getDriverCode());
        assertEquals("Mario Rossi", driver.getFullName());
        assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
        assertEquals(standardLicenses(), driver.getLicenseCategories());
        assertEquals(standardProfessionalQualifications(), driver.getProfessionalQualifications());
        assertEquals(standardAdrCertificates(), driver.getAdrCertificates());
        assertEquals(standardOperationalQualifications(), driver.getOperationalQualifications());
        assertEquals(Notes.empty(), driver.getNotes());
        assertTrue(driver.isAvailable());
        assertTrue(driver.canBeAssigned());
    }

    @Test
    void shouldCreateDriverWithoutOptionalQualifications() {
        Driver driver = Driver.available(
                "DRV-002",
                "Luigi Bianchi",
                Set.of(DriverLicenseCategory.B),
                Set.of(),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        assertEquals(Set.of(DriverLicenseCategory.B), driver.getLicenseCategories());
        assertTrue(driver.getProfessionalQualifications().isEmpty());
        assertTrue(driver.getAdrCertificates().isEmpty());
        assertTrue(driver.getOperationalQualifications().isEmpty());
    }

    @Test
    void shouldCreateAssignedDriver() {
        Driver driver = Driver.assigned(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        assertEquals(DriverStatus.ASSIGNED, driver.getStatus());
        assertTrue(driver.isAssigned());
        assertFalse(driver.canBeAssigned());
    }

    @Test
    void shouldCreateUnavailableDrivers() {
        Driver onLeave = Driver.onLeave(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        Driver suspended = Driver.suspended(
                "DRV-002",
                "Luigi Bianchi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        Driver inactive = Driver.inactive(
                "DRV-003",
                "Giuseppe Verdi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        assertFalse(onLeave.canBeAssigned());
        assertFalse(suspended.canBeAssigned());
        assertFalse(inactive.canBeAssigned());
    }

    @Test
    void shouldNormalizeDriverCodeAndFullName() {
        Driver driver = Driver.available(
                "  drv_001  ",
                "  Mario Rossi  ",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        assertEquals("DRV_001", driver.getDriverCode());
        assertEquals("Mario Rossi", driver.getFullName());
    }

    @Test
    void shouldNotAllowInvalidDriverCode() {
        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                null,
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "   ",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV 001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongDriverCode() {
        String tooLongCode = "A".repeat(51);

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                tooLongCode,
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowInvalidFullName() {
        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                null,
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "   ",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongFullName() {
        String tooLongName = "A".repeat(151);

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                tooLongName,
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullOrEmptyLicenseCategories() {
        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                null,
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullLicenseInsideSet() {
        Set<DriverLicenseCategory> licenses = new HashSet<>(Arrays.asList(
                DriverLicenseCategory.C,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                licenses,
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullOptionalSets() {
        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                null,
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                null,
                standardOperationalQualifications(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullValuesInsideOptionalSets() {
        Set<DriverProfessionalQualification> professionalQualifications = new HashSet<>(Arrays.asList(
                DriverProfessionalQualification.CQC_GOODS,
                null
        ));

        Set<DriverAdrCertificateType> adrCertificates = new HashSet<>(Arrays.asList(
                DriverAdrCertificateType.ADR_BASIC,
                null
        ));

        Set<DriverOperationalQualification> operationalQualifications = new HashSet<>(Arrays.asList(
                DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                professionalQualifications,
                Set.of(),
                Set.of(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                Set.of(),
                adrCertificates,
                Set.of(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                Set.of(),
                Set.of(),
                operationalQualifications,
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullNotes() {
        assertThrows(IllegalArgumentException.class, () -> Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                null
        ));
    }

    @Test
    void shouldReturnUnmodifiableSets() {
        Driver driver = standardAvailableDriver();

        assertThrows(UnsupportedOperationException.class,
                () -> driver.getLicenseCategories().add(DriverLicenseCategory.B));

        assertThrows(UnsupportedOperationException.class,
                () -> driver.getProfessionalQualifications().add(DriverProfessionalQualification.CQC_GOODS));

        assertThrows(UnsupportedOperationException.class,
                () -> driver.getAdrCertificates().add(DriverAdrCertificateType.ADR_TANK));

        assertThrows(UnsupportedOperationException.class,
                () -> driver.getOperationalQualifications().add(DriverOperationalQualification.HIGH_VALUE_CARGO));
    }

    @Test
    void shouldCheckLicenseCategories() {
        Driver driver = standardAvailableDriver();

        assertTrue(driver.hasLicenseCategory(DriverLicenseCategory.C));
        assertTrue(driver.hasLicenseCategory(DriverLicenseCategory.E));
        assertFalse(driver.hasLicenseCategory(DriverLicenseCategory.B));
    }

    @Test
    void shouldCheckAllRequiredLicenseCategories() {
        Driver driver = standardAvailableDriver();

        assertTrue(driver.hasAllLicenseCategories(Set.of(
                DriverLicenseCategory.C,
                DriverLicenseCategory.E
        )));

        assertFalse(driver.hasAllLicenseCategories(Set.of(
                DriverLicenseCategory.B,
                DriverLicenseCategory.C
        )));
    }

    @Test
    void shouldNotCheckInvalidLicenseCategories() {
        Driver driver = standardAvailableDriver();

        assertThrows(IllegalArgumentException.class, () -> driver.hasLicenseCategory(null));
        assertThrows(IllegalArgumentException.class, () -> driver.hasAllLicenseCategories(null));

        Set<DriverLicenseCategory> licensesWithNull = new HashSet<>(Arrays.asList(
                DriverLicenseCategory.C,
                null
        ));

        assertThrows(IllegalArgumentException.class,
                () -> driver.hasAllLicenseCategories(licensesWithNull));
    }

    @Test
    void shouldCheckDrivingCapabilities() {
        Driver vanDriver = Driver.available(
                "DRV-002",
                "Luigi Bianchi",
                Set.of(DriverLicenseCategory.B),
                Set.of(),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        Driver rigidTruckDriver = Driver.available(
                "DRV-003",
                "Giuseppe Verdi",
                Set.of(DriverLicenseCategory.C),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(),
                Set.of(),
                Notes.empty()
        );

        Driver articulatedTruckDriver = standardAvailableDriver();

        assertTrue(vanDriver.canDriveLightVehicle());
        assertFalse(vanDriver.canDriveRigidTruck());
        assertFalse(vanDriver.canDriveVehicleCombinationWithTrailer());

        assertTrue(rigidTruckDriver.canDriveRigidTruck());
        assertFalse(rigidTruckDriver.canDriveVehicleCombinationWithTrailer());

        assertTrue(articulatedTruckDriver.canDriveRigidTruck());
        assertTrue(articulatedTruckDriver.canDriveVehicleCombinationWithTrailer());
    }

    @Test
    void shouldCheckProfessionalQualifications() {
        Driver driver = standardAvailableDriver();

        assertTrue(driver.hasProfessionalQualification(DriverProfessionalQualification.CQC_GOODS));
        assertTrue(driver.hasGoodsCqc());
        assertEquals("95", DriverProfessionalQualification.CQC_GOODS.getHarmonizedCode());
    }

    @Test
    void shouldNotCheckNullProfessionalQualification() {
        Driver driver = standardAvailableDriver();

        assertThrows(IllegalArgumentException.class,
                () -> driver.hasProfessionalQualification(null));
    }

    @Test
    void shouldCheckAdrCertificates() {
        Driver driver = standardAvailableDriver();

        assertTrue(driver.hasAdrCertificate(DriverAdrCertificateType.ADR_BASIC));
        assertTrue(driver.hasAdrCertificate(DriverAdrCertificateType.ADR_TANK));
        assertTrue(driver.hasAdrBasicCertificate());
        assertFalse(driver.hasAdrCertificate(DriverAdrCertificateType.ADR_CLASS_7_RADIOACTIVE));
    }

    @Test
    void shouldNotCheckNullAdrCertificate() {
        Driver driver = standardAvailableDriver();

        assertThrows(IllegalArgumentException.class,
                () -> driver.hasAdrCertificate(null));
    }

    @Test
    void shouldCheckOperationalQualifications() {
        Driver driver = standardAvailableDriver();

        assertTrue(driver.hasOperationalQualification(DriverOperationalQualification.INTERNATIONAL_TRANSPORT));
        assertTrue(driver.hasOperationalQualification(DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT));
        assertFalse(driver.hasOperationalQualification(DriverOperationalQualification.HIGH_VALUE_CARGO));
    }

    @Test
    void shouldNotCheckNullOperationalQualification() {
        Driver driver = standardAvailableDriver();

        assertThrows(IllegalArgumentException.class,
                () -> driver.hasOperationalQualification(null));
    }

    @Test
    void shouldDetectNotes() {
        Driver driver = Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.of("Preferisce tratte nazionali")
        );

        assertTrue(driver.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        Driver driver = standardAvailableDriver();

        assertEquals("DRV-001 - Mario Rossi - AVAILABLE", driver.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentDriversEqual() {
        Driver first = Driver.available(
                "  drv-001  ",
                "  Mario Rossi  ",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        Driver second = Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Driver standardAvailableDriver() {
        return Driver.available(
                "DRV-001",
                "Mario Rossi",
                standardLicenses(),
                standardProfessionalQualifications(),
                standardAdrCertificates(),
                standardOperationalQualifications(),
                Notes.empty()
        );
    }

    private static Set<DriverLicenseCategory> standardLicenses() {
        return Set.of(
                DriverLicenseCategory.C,
                DriverLicenseCategory.E
        );
    }

    private static Set<DriverProfessionalQualification> standardProfessionalQualifications() {
        return Set.of(
                DriverProfessionalQualification.CQC_GOODS
        );
    }

    private static Set<DriverAdrCertificateType> standardAdrCertificates() {
        return Set.of(
                DriverAdrCertificateType.ADR_BASIC,
                DriverAdrCertificateType.ADR_TANK
        );
    }

    private static Set<DriverOperationalQualification> standardOperationalQualifications() {
        return Set.of(
                DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
        );
    }
}
