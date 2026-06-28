package it.gabriele.truckflow.domain.fuel;

/**
 * Regole di consumo reale carburante.
 */
public final class FuelConsumptionRules {

    private FuelConsumptionRules() {
    }

    public static double calculateKilometersPerLiter(FuelTransaction previous, FuelTransaction current) {
        validate(previous, current);
        return current.calculateKilometersPerLiter(previous);
    }

    public static boolean isConsumptionAnomaly(
            FuelTransaction previous,
            FuelTransaction current,
            double expectedKilometersPerLiter,
            double tolerancePercentage
    ) {
        if (expectedKilometersPerLiter <= 0 || Double.isNaN(expectedKilometersPerLiter) || Double.isInfinite(expectedKilometersPerLiter)) {
            throw new IllegalArgumentException("Il consumo atteso deve essere positivo.");
        }
        if (tolerancePercentage < 0 || tolerancePercentage > 100) {
            throw new IllegalArgumentException("La tolleranza deve essere tra 0 e 100.");
        }
        double actual = calculateKilometersPerLiter(previous, current);
        double minimumAccepted = expectedKilometersPerLiter * (1 - tolerancePercentage / 100.0);
        return actual < minimumAccepted;
    }

    private static void validate(FuelTransaction previous, FuelTransaction current) {
        if (previous == null) {
            throw new IllegalArgumentException("Il rifornimento precedente è obbligatorio.");
        }
        if (current == null) {
            throw new IllegalArgumentException("Il rifornimento corrente è obbligatorio.");
        }
    }
}
