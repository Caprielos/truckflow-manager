package it.gabriele.truckflow.domain.kpi;

import java.util.Objects;

/** Soglia KPI usata da dashboard e alert. */
public final class KpiThreshold {

  private final KpiMetric metric;
  private final double warningValue;
  private final double criticalValue;
  private final boolean lowerIsBetter;

  private KpiThreshold(
      KpiMetric metric, double warningValue, double criticalValue, boolean lowerIsBetter) {
    if (metric == null) {
      throw new IllegalArgumentException("La metrica soglia KPI è obbligatoria.");
    }

    if (Double.isNaN(warningValue)
        || Double.isInfinite(warningValue)
        || Double.isNaN(criticalValue)
        || Double.isInfinite(criticalValue)) {
      throw new IllegalArgumentException("Le soglie KPI devono essere valide.");
    }

    this.metric = metric;
    this.warningValue = warningValue;
    this.criticalValue = criticalValue;
    this.lowerIsBetter = lowerIsBetter;
  }

  public static KpiThreshold of(
      KpiMetric metric, double warningValue, double criticalValue, boolean lowerIsBetter) {
    return new KpiThreshold(metric, warningValue, criticalValue, lowerIsBetter);
  }

  public KpiMetric getMetric() {
    return metric;
  }

  public double getWarningValue() {
    return warningValue;
  }

  public double getCriticalValue() {
    return criticalValue;
  }

  public boolean isLowerIsBetter() {
    return lowerIsBetter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof KpiThreshold that)) return false;
    return Double.compare(warningValue, that.warningValue) == 0
        && Double.compare(criticalValue, that.criticalValue) == 0
        && lowerIsBetter == that.lowerIsBetter
        && metric == that.metric;
  }

  @Override
  public int hashCode() {
    return Objects.hash(metric, warningValue, criticalValue, lowerIsBetter);
  }
}
