package it.gabriele.truckflow.domain.sustainability;

/**
 * Classe emissiva del veicolo.
 */
public enum EmissionStandard {

    EURO_0(0, false),
    EURO_1(1, false),
    EURO_2(2, false),
    EURO_3(3, false),
    EURO_4(4, false),
    EURO_5(5, true),
    EURO_6(6, true),
    ZERO_EMISSION(7, true),
    UNKNOWN(-1, false);

    private final int level;
    private final boolean lowEmissionStandard;

    EmissionStandard(int level, boolean lowEmissionStandard) {
        this.level = level;
        this.lowEmissionStandard = lowEmissionStandard;
    }

    public int getLevel() {
        return level;
    }

    public boolean isLowEmissionStandard() {
        return lowEmissionStandard;
    }

    public boolean isAtLeast(EmissionStandard other) {
        if (other == null) {
            throw new IllegalArgumentException("La classe emissiva da confrontare è obbligatoria.");
        }

        return level >= other.level;
    }
}
