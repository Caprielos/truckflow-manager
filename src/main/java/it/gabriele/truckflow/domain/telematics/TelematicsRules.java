package it.gabriele.truckflow.domain.telematics;

public final class TelematicsRules {

  private TelematicsRules() {}

  public static boolean isFuelDropAnomaly(
      double previousFuelPercent, double currentFuelPercent, double thresholdPercent) {
    validatePercent(previousFuelPercent, "Il livello carburante precedente");
    validatePercent(currentFuelPercent, "Il livello carburante corrente");
    if (thresholdPercent < 0 || thresholdPercent > 100) {
      throw new IllegalArgumentException("La soglia deve essere tra 0 e 100.");
    }
    return previousFuelPercent - currentFuelPercent >= thresholdPercent;
  }

  public static boolean isSpeeding(double speedKmh, double legalLimitKmh, double toleranceKmh) {
    if (speedKmh < 0 || legalLimitKmh < 0 || toleranceKmh < 0) {
      throw new IllegalArgumentException("Velocità e tolleranza non possono essere negative.");
    }
    return speedKmh > legalLimitKmh + toleranceKmh;
  }

  private static void validatePercent(double value, String label) {
    if (value < 0 || value > 100 || Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException(label + " deve essere tra 0 e 100.");
    }
  }
}
