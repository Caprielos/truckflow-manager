package it.gabriele.truckflow.domain.location;

import java.util.Objects;

/**
 * Rappresenta un indirizzo fisico.
 * Serve per clienti, magazzini, ritiri e consegne.
 */
public final class Address {

    private static final int MAX_FIELD_LENGTH = 150;
    private static final int COUNTRY_CODE_LENGTH = 2;

    private final String street;
    private final String city;
    private final String postalCode;
    private final String countryCode;
    private final GeoCoordinates coordinates;

    private Address(
            String street,
            String city,
            String postalCode,
            String countryCode,
            GeoCoordinates coordinates
    ) {
        this.street = validateRequiredText(street, "La via");
        this.city = validateRequiredText(city, "La città");
        this.postalCode = validateRequiredText(postalCode, "Il codice postale");
        this.countryCode = validateCountryCode(countryCode);
        this.coordinates = coordinates;
    }

    public static Address of(
            String street,
            String city,
            String postalCode,
            String countryCode
    ) {
        return new Address(street, city, postalCode, countryCode, null);
    }

    public static Address withCoordinates(
            String street,
            String city,
            String postalCode,
            String countryCode,
            GeoCoordinates coordinates
    ) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Le coordinate sono obbligatorie.");
        }

        return new Address(street, city, postalCode, countryCode, coordinates);
    }

    private static String validateRequiredText(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " è obbligatoria.");
        }

        String normalizedValue = value.trim();

        if (normalizedValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " non può essere vuota.");
        }

        if (normalizedValue.length() > MAX_FIELD_LENGTH) {
            throw new IllegalArgumentException(fieldName + " non può superare " + MAX_FIELD_LENGTH + " caratteri.");
        }

        return normalizedValue;
    }

    private static String validateCountryCode(String countryCode) {
        String normalizedCountryCode = validateRequiredText(countryCode, "Il codice paese").toUpperCase();

        if (normalizedCountryCode.length() != COUNTRY_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice paese deve essere composto da 2 lettere.");
        }

        if (!normalizedCountryCode.matches("[A-Z]{2}")) {
            throw new IllegalArgumentException("Il codice paese deve contenere solo lettere.");
        }

        return normalizedCountryCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public GeoCoordinates getCoordinates() {
        return coordinates;
    }

    public boolean hasCoordinates() {
        return coordinates != null;
    }

    public boolean isInCountry(String countryCode) {
        if (countryCode == null) {
            throw new IllegalArgumentException("Il codice paese da confrontare è obbligatorio.");
        }

        return this.countryCode.equals(countryCode.trim().toUpperCase());
    }

    public String formatSingleLine() {
        return street + ", " + postalCode + " " + city + ", " + countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return street.equals(address.street)
                && city.equals(address.city)
                && postalCode.equals(address.postalCode)
                && countryCode.equals(address.countryCode)
                && Objects.equals(coordinates, address.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode, countryCode, coordinates);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
