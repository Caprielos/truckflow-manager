package it.gabriele.truckflow.domain.configuration;

/**
 * Tipo del valore configurato.
 */
public enum ConfigurationValueType {

    TEXT,
    BOOLEAN,
    INTEGER,
    DECIMAL,
    PERCENTAGE,
    DURATION_MINUTES;

    public boolean isNumeric() {
        return this == INTEGER
                || this == DECIMAL
                || this == PERCENTAGE
                || this == DURATION_MINUTES;
    }
}
