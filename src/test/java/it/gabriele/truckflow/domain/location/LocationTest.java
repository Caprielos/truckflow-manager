package it.gabriele.truckflow.domain.location;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Location.
 */
class LocationTest {

    @Test
    void shouldCreateLocationUsingZoneId() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        Location location = Location.of(
                "Magazzino Milano",
                address,
                ZoneId.of("Europe/Rome")
        );

        assertEquals("Magazzino Milano", location.getName());
        assertEquals(address, location.getAddress());
        assertEquals(ZoneId.of("Europe/Rome"), location.getZoneId());
    }

    @Test
    void shouldCreateLocationUsingZoneIdString() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        Location location = Location.of(
                "Magazzino Milano",
                address,
                "Europe/Rome"
        );

        assertEquals(ZoneId.of("Europe/Rome"), location.getZoneId());
    }

    @Test
    void shouldTrimNameAndZoneIdString() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        Location location = Location.of(
                "   Magazzino Milano   ",
                address,
                "   Europe/Rome   "
        );

        assertEquals("Magazzino Milano", location.getName());
        assertEquals(ZoneId.of("Europe/Rome"), location.getZoneId());
    }

    @Test
    void shouldNotAllowNullOrBlankName() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertThrows(IllegalArgumentException.class,
                () -> Location.of(null, address, ZoneId.of("Europe/Rome")));

        assertThrows(IllegalArgumentException.class,
                () -> Location.of("   ", address, ZoneId.of("Europe/Rome")));
    }

    @Test
    void shouldNotAllowTooLongName() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");
        String tooLongName = "a".repeat(151);

        assertThrows(IllegalArgumentException.class,
                () -> Location.of(tooLongName, address, ZoneId.of("Europe/Rome")));
    }

    @Test
    void shouldNotAllowNullAddress() {
        assertThrows(IllegalArgumentException.class,
                () -> Location.of("Magazzino Milano", null, ZoneId.of("Europe/Rome")));
    }

    @Test
    void shouldNotAllowNullZoneId() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertThrows(IllegalArgumentException.class,
                () -> Location.of("Magazzino Milano", address, (ZoneId) null));
    }

    @Test
    void shouldNotAllowNullOrBlankZoneIdString() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertThrows(IllegalArgumentException.class,
                () -> Location.of("Magazzino Milano", address, (String) null));

        assertThrows(IllegalArgumentException.class,
                () -> Location.of("Magazzino Milano", address, "   "));
    }

    @Test
    void shouldExposeAddressCoordinates() {
        GeoCoordinates coordinates = GeoCoordinates.of(45.4642, 9.1900);

        Address address = Address.withCoordinates(
                "Via Roma 10",
                "Milano",
                "20100",
                "IT",
                coordinates
        );

        Location location = Location.of("Magazzino Milano", address, "Europe/Rome");

        assertTrue(location.hasCoordinates());
        assertEquals(coordinates, location.getCoordinates());
    }

    @Test
    void shouldDetectCountry() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");
        Location location = Location.of("Magazzino Milano", address, "Europe/Rome");

        assertTrue(location.isInCountry("IT"));
        assertTrue(location.isInCountry(" it "));
        assertFalse(location.isInCountry("FR"));
    }

    @Test
    void shouldDetectSameTimeZone() {
        Location milanWarehouse = Location.of(
                "Magazzino Milano",
                Address.of("Via Roma 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );

        Location romeWarehouse = Location.of(
                "Magazzino Roma",
                Address.of("Via Appia 20", "Roma", "00100", "IT"),
                "Europe/Rome"
        );

        Location londonWarehouse = Location.of(
                "Warehouse London",
                Address.of("Baker Street 1", "London", "NW1", "GB"),
                "Europe/London"
        );

        assertTrue(milanWarehouse.isInSameTimeZone(romeWarehouse));
        assertFalse(milanWarehouse.isInSameTimeZone(londonWarehouse));
    }

    @Test
    void shouldNotCheckSameTimeZoneWithNullLocation() {
        Location location = Location.of(
                "Magazzino Milano",
                Address.of("Via Roma 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );

        assertThrows(IllegalArgumentException.class,
                () -> location.isInSameTimeZone(null));
    }

    @Test
    void shouldFormatSingleLine() {
        Location location = Location.of(
                "Magazzino Milano",
                Address.of("Via Roma 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );

        assertEquals(
                "Magazzino Milano - Via Roma 10, 20100 Milano, IT [Europe/Rome]",
                location.formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentLocationsEqual() {
        Location first = Location.of(
                "  Magazzino Milano  ",
                Address.of("Via Roma 10", "Milano", "20100", "it"),
                "Europe/Rome"
        );

        Location second = Location.of(
                "Magazzino Milano",
                Address.of("Via Roma 10", "Milano", "20100", "IT"),
                ZoneId.of("Europe/Rome")
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
