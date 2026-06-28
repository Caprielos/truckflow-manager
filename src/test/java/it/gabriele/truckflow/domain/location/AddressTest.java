package it.gabriele.truckflow.domain.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Address.
 */
class AddressTest {

    @Test
    void shouldCreateAddress() {
        Address address = Address.of(
                "Via Roma 10",
                "Milano",
                "20100",
                "IT"
        );

        assertEquals("Via Roma 10", address.getStreet());
        assertEquals("Milano", address.getCity());
        assertEquals("20100", address.getPostalCode());
        assertEquals("IT", address.getCountryCode());
        assertFalse(address.hasCoordinates());
    }

    @Test
    void shouldCreateAddressWithCoordinates() {
        GeoCoordinates coordinates = GeoCoordinates.of(45.4642, 9.1900);

        Address address = Address.withCoordinates(
                "Via Roma 10",
                "Milano",
                "20100",
                "IT",
                coordinates
        );

        assertTrue(address.hasCoordinates());
        assertEquals(coordinates, address.getCoordinates());
    }

    @Test
    void shouldTrimTextFieldsAndNormalizeCountryCode() {
        Address address = Address.of(
                "  Via Roma 10  ",
                "  Milano  ",
                "  20100  ",
                " it "
        );

        assertEquals("Via Roma 10", address.getStreet());
        assertEquals("Milano", address.getCity());
        assertEquals("20100", address.getPostalCode());
        assertEquals("IT", address.getCountryCode());
    }

    @Test
    void shouldNotAllowNullOrBlankRequiredFields() {
        assertThrows(IllegalArgumentException.class, () -> Address.of(null, "Milano", "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", null, "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", null, "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "20100", null));

        assertThrows(IllegalArgumentException.class, () -> Address.of("   ", "Milano", "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "   ", "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "   ", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "20100", "   "));
    }

    @Test
    void shouldNotAllowTooLongFields() {
        String tooLongText = "a".repeat(151);

        assertThrows(IllegalArgumentException.class, () -> Address.of(tooLongText, "Milano", "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", tooLongText, "20100", "IT"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", tooLongText, "IT"));
    }

    @Test
    void shouldNotAllowInvalidCountryCode() {
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "20100", "ITA"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "20100", "I"));
        assertThrows(IllegalArgumentException.class, () -> Address.of("Via Roma 10", "Milano", "20100", "1T"));
    }

    @Test
    void shouldNotCreateAddressWithNullCoordinatesUsingWithCoordinatesFactory() {
        assertThrows(IllegalArgumentException.class, () -> Address.withCoordinates(
                "Via Roma 10",
                "Milano",
                "20100",
                "IT",
                null
        ));
    }

    @Test
    void shouldCheckCountry() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertTrue(address.isInCountry("IT"));
        assertTrue(address.isInCountry(" it "));
        assertFalse(address.isInCountry("FR"));
    }

    @Test
    void shouldNotCheckNullCountry() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertThrows(IllegalArgumentException.class, () -> address.isInCountry(null));
    }

    @Test
    void shouldFormatSingleLine() {
        Address address = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertEquals("Via Roma 10, 20100 Milano, IT", address.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentAddressesEqual() {
        Address first = Address.of("  Via Roma 10  ", "Milano", "20100", "it");
        Address second = Address.of("Via Roma 10", "Milano", "20100", "IT");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
