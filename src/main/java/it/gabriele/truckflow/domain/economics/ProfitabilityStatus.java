package it.gabriele.truckflow.domain.economics;

/** Stato economico di una missione o di un periodo aziendale. */
public enum ProfitabilityStatus {
  PROFIT,
  BREAK_EVEN,
  LOSS,
  NO_REVENUE;

  public boolean isCritical() {
    return this == LOSS || this == NO_REVENUE;
  }
}
