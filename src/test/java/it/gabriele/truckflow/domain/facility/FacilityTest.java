package it.gabriele.truckflow.domain.facility;

import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.GeoCoordinates;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Facility.
 */
class FacilityTest {

    @Test
    void shouldCreateActiveFacility() {
        Location location = milanLocation();

        Facility facility = Facility.active(
                "mil-warehouse-01",
                FacilityType.WAREHOUSE,
                location,
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertEquals("MIL-WAREHOUSE-01", facility.getCode());
        assertEquals(FacilityType.WAREHOUSE, facility.getType());
        assertEquals(location, facility.getLocation());
        assertEquals(TimeWindow.of("08:00", "18:00"), facility.getOperatingHours());
        assertEquals(Notes.empty(), facility.getNotes());
        assertTrue(facility.isActive());
    }

    @Test
    void shouldCreateInactiveFacility() {
        Facility facility = Facility.inactive(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertFalse(facility.isActive());
    }

    @Test
    void shouldNormalizeCode() {
        Facility facility = Facility.active(
                "  mil_warehouse_01  ",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertEquals("MIL_WAREHOUSE_01", facility.getCode());
    }

    @Test
    void shouldNotAllowInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                null,
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "   ",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "MIL WAREHOUSE 01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongCode() {
        String tooLongCode = "A".repeat(51);

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                tooLongCode,
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "MIL-WAREHOUSE-01",
                null,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                null,
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                null
        ));
    }

    @Test
    void shouldCheckIfActiveFacilityIsOpenAtGivenTime() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertTrue(facility.isOpenAt(LocalTime.of(8, 0)));
        assertTrue(facility.isOpenAt(LocalTime.of(12, 0)));
        assertTrue(facility.isOpenAt(LocalTime.of(18, 0)));
        assertFalse(facility.isOpenAt(LocalTime.of(19, 0)));
    }

    @Test
    void shouldConsiderInactiveFacilityClosed() {
        Facility facility = Facility.inactive(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertFalse(facility.isOpenAt(LocalTime.of(12, 0)));
    }

    @Test
    void shouldNotCheckOpeningWithNullTime() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> facility.isOpenAt(null));
    }

    @Test
    void shouldDetectCountry() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertTrue(facility.isInCountry("IT"));
        assertFalse(facility.isInCountry("FR"));
    }

    @Test
    void shouldExposeCoordinates() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertTrue(facility.hasCoordinates());
    }

    @Test
    void shouldDetectNotes() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.of("Accesso da cancello nord")
        );

        assertTrue(facility.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        Facility facility = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertEquals(
                "MIL-WAREHOUSE-01 - WAREHOUSE - Magazzino Milano - Via Roma 10, 20100 Milano, IT [Europe/Rome]",
                facility.formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentFacilitiesEqual() {
        Facility first = Facility.active(
                "  mil-warehouse-01  ",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        Facility second = Facility.active(
                "MIL-WAREHOUSE-01",
                FacilityType.WAREHOUSE,
                milanLocation(),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Location milanLocation() {
        return Location.of(
                "Magazzino Milano",
                Address.withCoordinates(
                        "Via Roma 10",
                        "Milano",
                        "20100",
                        "IT",
                        GeoCoordinates.of(45.4642, 9.1900)
                ),
                "Europe/Rome"
        );
    }
}
