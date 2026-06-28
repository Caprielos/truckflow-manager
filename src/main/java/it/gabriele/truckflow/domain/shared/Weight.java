package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/**
 * Rappresenta un peso del dominio.
 * Internamente il peso viene sempre salvato in chilogrammi.
 */
public final class Weight {

    private static final double KILOGRAMS_PER_TON = 1000.0;

    private final double kilograms;

    private Weight(double kilograms) {
        if (kilograms < 0) {
            throw new IllegalArgumentException("Il peso non può essere negativo.");
        }

        if (Double.isNaN(kilograms) || Double.isInfinite(kilograms)) {
            throw new IllegalArgumentException("Il peso deve essere un numero valido.");
        }

        this.kilograms = kilograms;
    }

    /**
     * Crea un peso partendo dai chilogrammi.
     */
    public static Weight ofKilograms(double kilograms) {
        return new Weight(kilograms);
    }

    /**
     * Crea un peso partendo dalle tonnellate,
     * convertendole subito in chilogrammi.
     */
    public static Weight ofTons(double tons) {
        return new Weight(tons * KILOGRAMS_PER_TON);
    }

    public double getKilograms() {
        return kilograms;
    }

    /**
     * Verifica se questo peso è maggiore di un altro peso.
     */
    public boolean isGreaterThan(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Il peso da confrontare è obbligatorio.");
        }

        return this.kilograms > other.kilograms;
    }

    /**
     * Verifica se questo peso è minore o uguale a un altro peso.
     */
    public boolean isLessThanOrEqualTo(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Il peso da confrontare è obbligatorio.");
        }

        return this.kilograms <= other.kilograms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weight weight)) return false;
        return Double.compare(kilograms, weight.kilograms) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kilograms);
    }

    @Override
    public String toString() {
        return kilograms + " kg";
    }
}