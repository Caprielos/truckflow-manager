package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/**
 * Rappresenta un intervallo di temperatura espresso in gradi Celsius.
 * Serve per merce refrigerata e veicoli a temperatura controllata.
 */
public final class TemperatureRange {

    private final double minCelsius;
    private final double maxCelsius;

    private TemperatureRange(double minCelsius, double maxCelsius) {
        validateTemperature(minCelsius, "La temperatura minima");
        validateTemperature(maxCelsius, "La temperatura massima");

        if (minCelsius > maxCelsius) {
            throw new IllegalArgumentException("La temperatura minima non può essere maggiore della massima.");
        }

        this.minCelsius = minCelsius;
        this.maxCelsius = maxCelsius;
    }

    /**
     * Crea un intervallo di temperatura in gradi Celsius.
     */
    public static TemperatureRange ofCelsius(double minCelsius, double maxCelsius) {
        return new TemperatureRange(minCelsius, maxCelsius);
    }

    private static void validateTemperature(double value, String fieldName) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(fieldName + " deve essere un numero valido.");
        }
    }

    public double getMinCelsius() {
        return minCelsius;
    }

    public double getMaxCelsius() {
        return maxCelsius;
    }

    /**
     * Verifica se una temperatura è compresa nell'intervallo.
     */
    public boolean contains(double celsius) {
        validateTemperature(celsius, "La temperatura da verificare");

        return celsius >= minCelsius && celsius <= maxCelsius;
    }

    /**
     * Verifica se questo intervallo è coperto da un altro intervallo.
     * Esempio: 2-8°C è coperto da 0-10°C.
     */
    public boolean isCoveredBy(TemperatureRange availableRange) {
        if (availableRange == null) {
            throw new IllegalArgumentException("L'intervallo disponibile è obbligatorio.");
        }

        return this.minCelsius >= availableRange.minCelsius
                && this.maxCelsius <= availableRange.maxCelsius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemperatureRange that)) return false;
        return Double.compare(minCelsius, that.minCelsius) == 0
                && Double.compare(maxCelsius, that.maxCelsius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minCelsius, maxCelsius);
    }

    @Override
    public String toString() {
        return minCelsius + "°C / " + maxCelsius + "°C";
    }
}
