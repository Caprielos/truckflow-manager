package it.gabriele.truckflow.domain.kpi;

/** Regole di dominio per soglie KPI e dashboard. */
public final class KpiRules {

  private KpiRules() {}

  public static boolean isWarning(KpiResult result, KpiThreshold threshold) {
    validateSameMetric(result, threshold);

    if (threshold.isLowerIsBetter()) {
      return result.getValue() >= threshold.getWarningValue();
    }

    return result.getValue() <= threshold.getWarningValue();
  }

  public static boolean isCritical(KpiResult result, KpiThreshold threshold) {
    validateSameMetric(result, threshold);

    if (threshold.isLowerIsBetter()) {
      return result.getValue() >= threshold.getCriticalValue();
    }

    return result.getValue() <= threshold.getCriticalValue();
  }

  private static void validateSameMetric(KpiResult result, KpiThreshold threshold) {
    if (result == null) {
      throw new IllegalArgumentException("Il risultato KPI è obbligatorio.");
    }

    if (threshold == null) {
      throw new IllegalArgumentException("La soglia KPI è obbligatoria.");
    }

    if (result.getMetric() != threshold.getMetric()) {
      throw new IllegalArgumentException("Risultato e soglia devono usare la stessa metrica KPI.");
    }
  }
}
