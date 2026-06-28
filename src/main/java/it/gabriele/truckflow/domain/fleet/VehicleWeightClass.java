package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Weight;

/**
 * Classe di peso utile per patenti, compliance e pianificazione.
 */
public enum VehicleWeightClass {

    LIGHT_UNDER_3_5T(0, 3500),
    MEDIUM_UP_TO_12T(3500, 12000),
    HEAVY_OVER_12T(12000, Double.MAX_VALUE);

    private final double minExclusiveKg;
    private final double maxInclusiveKg;

    VehicleWeightClass(double minExclusiveKg, double maxInclusiveKg) {
        this.minExclusiveKg = minExclusiveKg;
        this.maxInclusiveKg = maxInclusiveKg;
    }

    public static VehicleWeightClass fromGrossWeight(Weight grossWeight) {
        if (grossWeight == null) {
            throw new IllegalArgumentException("La massa complessiva è obbligatoria.");
        }

        double kilograms = grossWeight.getKilograms();

        if (kilograms <= 3500) {
            return LIGHT_UNDER_3_5T;
        }

        if (kilograms <= 12000) {
            return MEDIUM_UP_TO_12T;
        }

        return HEAVY_OVER_12T;
    }

    public boolean requiresHeavyGoodsLicense() {
        return this != LIGHT_UNDER_3_5T;
    }
}
