package it.gabriele.truckflow.domain.configuration;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Valore configurativo tipizzato.
 */
public final class ConfigurationValue {

    private static final int MAX_TEXT_LENGTH = 500;

    private final ConfigurationValueType type;
    private final String rawValue;

    private ConfigurationValue(
            ConfigurationValueType type,
            String rawValue
    ) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo valore configurazione è obbligatorio.");
        }

        if (rawValue == null) {
            throw new IllegalArgumentException("Il valore configurazione è obbligatorio.");
        }

        this.type = type;
        this.rawValue = validateRawValue(type, rawValue);
    }

    public static ConfigurationValue ofText(String value) {
        return new ConfigurationValue(ConfigurationValueType.TEXT, value);
    }

    public static ConfigurationValue ofBoolean(boolean value) {
        return new ConfigurationValue(ConfigurationValueType.BOOLEAN, Boolean.toString(value));
    }

    public static ConfigurationValue ofInteger(int value) {
        return new ConfigurationValue(ConfigurationValueType.INTEGER, Integer.toString(value));
    }

    public static ConfigurationValue ofDecimal(String value) {
        return new ConfigurationValue(ConfigurationValueType.DECIMAL, value);
    }

    public static ConfigurationValue ofPercentage(String value) {
        return new ConfigurationValue(ConfigurationValueType.PERCENTAGE, value);
    }

    public static ConfigurationValue ofDurationMinutes(int minutes) {
        return new ConfigurationValue(ConfigurationValueType.DURATION_MINUTES, Integer.toString(minutes));
    }

    private static String validateRawValue(
            ConfigurationValueType type,
            String rawValue
    ) {
        String normalizedValue = rawValue.trim();

        if (normalizedValue.isEmpty()) {
            throw new IllegalArgumentException("Il valore configurazione non può essere vuoto.");
        }

        return switch (type) {
            case TEXT -> validateText(normalizedValue);
            case BOOLEAN -> validateBoolean(normalizedValue);
            case INTEGER -> validateInteger(normalizedValue);
            case DECIMAL -> validateDecimal(normalizedValue);
            case PERCENTAGE -> validatePercentage(normalizedValue);
            case DURATION_MINUTES -> validateDurationMinutes(normalizedValue);
        };
    }

    private static String validateText(String value) {
        if (value.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException("Il valore testuale configurazione non può superare "
                    + MAX_TEXT_LENGTH + " caratteri.");
        }

        return value;
    }

    private static String validateBoolean(String value) {
        String normalized = value.toLowerCase();

        if (!normalized.equals("true") && !normalized.equals("false")) {
            throw new IllegalArgumentException("Il valore booleano configurazione deve essere true o false.");
        }

        return normalized;
    }

    private static String validateInteger(String value) {
        try {
            Integer.parseInt(value);
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Il valore intero configurazione non è valido.", exception);
        }
    }

    private static String validateDecimal(String value) {
        try {
            return new BigDecimal(value).stripTrailingZeros().toPlainString();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Il valore decimale configurazione non è valido.", exception);
        }
    }

    private static String validatePercentage(String value) {
        BigDecimal percentage;

        try {
            percentage = new BigDecimal(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Il valore percentuale configurazione non è valido.", exception);
        }

        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Il valore percentuale configurazione deve essere tra 0 e 100.");
        }

        return percentage.stripTrailingZeros().toPlainString();
    }

    private static String validateDurationMinutes(String value) {
        int minutes;

        try {
            minutes = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("La durata in minuti configurazione non è valida.", exception);
        }

        if (minutes < 0) {
            throw new IllegalArgumentException("La durata in minuti configurazione non può essere negativa.");
        }

        return Integer.toString(minutes);
    }

    public ConfigurationValueType getType() {
        return type;
    }

    public String getRawValue() {
        return rawValue;
    }

    public String asText() {
        ensureType(ConfigurationValueType.TEXT);
        return rawValue;
    }

    public boolean asBoolean() {
        ensureType(ConfigurationValueType.BOOLEAN);
        return Boolean.parseBoolean(rawValue);
    }

    public int asInteger() {
        ensureType(ConfigurationValueType.INTEGER);
        return Integer.parseInt(rawValue);
    }

    public BigDecimal asDecimal() {
        ensureType(ConfigurationValueType.DECIMAL);
        return new BigDecimal(rawValue);
    }

    public BigDecimal asPercentage() {
        ensureType(ConfigurationValueType.PERCENTAGE);
        return new BigDecimal(rawValue);
    }

    public int asDurationMinutes() {
        ensureType(ConfigurationValueType.DURATION_MINUTES);
        return Integer.parseInt(rawValue);
    }

    public boolean isNumeric() {
        return type.isNumeric();
    }

    private void ensureType(ConfigurationValueType expectedType) {
        if (type != expectedType) {
            throw new IllegalStateException("Il valore configurazione non è di tipo " + expectedType + ".");
        }
    }

    public String formatSingleLine() {
        return type + ": " + rawValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigurationValue that)) return false;
        return type == that.type && rawValue.equals(that.rawValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rawValue);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
