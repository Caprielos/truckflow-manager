package it.gabriele.truckflow.domain.kpi;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Risultato KPI calcolato per periodo, cliente, mezzo, autista o processo. */
public final class KpiResult {

  private static final int MAX_CODE_LENGTH = 50;

  private final String resultCode;
  private final KpiMetric metric;
  private final String subjectCode;
  private final DateRange period;
  private final double value;
  private final String unit;
  private final Notes notes;

  private KpiResult(
      String resultCode,
      KpiMetric metric,
      String subjectCode,
      DateRange period,
      double value,
      String unit,
      Notes notes) {
    this.resultCode = validateCode(resultCode, "Il codice KPI è obbligatorio.");

    if (metric == null) {
      throw new IllegalArgumentException("La metrica KPI è obbligatoria.");
    }

    this.subjectCode = validateCode(subjectCode, "Il soggetto KPI è obbligatorio.");

    if (period == null) {
      throw new IllegalArgumentException("Il periodo KPI è obbligatorio.");
    }

    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException("Il valore KPI deve essere valido.");
    }

    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("L'unità KPI è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note KPI sono obbligatorie.");
    }

    this.metric = metric;
    this.period = period;
    this.value = value;
    this.unit = unit.trim();
    this.notes = notes;
  }

  public static KpiResult of(
      String resultCode,
      KpiMetric metric,
      String subjectCode,
      DateRange period,
      double value,
      String unit,
      Notes notes) {
    return new KpiResult(resultCode, metric, subjectCode, period, value, unit, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getResultCode() {
    return resultCode;
  }

  public KpiMetric getMetric() {
    return metric;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  public DateRange getPeriod() {
    return period;
  }

  public double getValue() {
    return value;
  }

  public String getUnit() {
    return unit;
  }

  public Notes getNotes() {
    return notes;
  }

  public KpiCategory getCategory() {
    return metric.getCategory();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof KpiResult kpiResult)) return false;
    return Double.compare(value, kpiResult.value) == 0
        && resultCode.equals(kpiResult.resultCode)
        && metric == kpiResult.metric
        && subjectCode.equals(kpiResult.subjectCode)
        && period.equals(kpiResult.period)
        && unit.equals(kpiResult.unit)
        && notes.equals(kpiResult.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resultCode, metric, subjectCode, period, value, unit, notes);
  }
}
