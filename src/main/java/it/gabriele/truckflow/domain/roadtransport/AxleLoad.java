package it.gabriele.truckflow.domain.roadtransport;

/** Carico previsto o misurato su un asse, con limite legale e tecnico. */
public record AxleLoad(
    int axleNumber,
    double plannedKilograms,
    double legalLimitKilograms,
    double technicalLimitKilograms) {

  public AxleLoad {
    if (axleNumber < 1) {
      throw new IllegalArgumentException("Il numero asse deve partire da 1.");
    }
    validateNonNegative(plannedKilograms, "Il carico asse");
    validatePositive(legalLimitKilograms, "Il limite legale asse");
    validatePositive(technicalLimitKilograms, "Il limite tecnico asse");
    if (legalLimitKilograms > technicalLimitKilograms) {
      throw new IllegalArgumentException(
          "Il limite legale asse non può superare il limite tecnico.");
    }
  }

  public boolean isWithinLegalLimit() {
    return plannedKilograms <= legalLimitKilograms;
  }

  public boolean isWithinTechnicalLimit() {
    return plannedKilograms <= technicalLimitKilograms;
  }

  public double legalOverloadKilograms() {
    return Math.max(0, plannedKilograms - legalLimitKilograms);
  }

  private static void validatePositive(double value, String fieldName) {
    if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
      throw new IllegalArgumentException(fieldName + " deve essere maggiore di zero.");
    }
  }

  private static void validateNonNegative(double value, String fieldName) {
    if (Double.isNaN(value) || Double.isInfinite(value) || value < 0) {
      throw new IllegalArgumentException(fieldName + " non può essere negativo.");
    }
  }
}
