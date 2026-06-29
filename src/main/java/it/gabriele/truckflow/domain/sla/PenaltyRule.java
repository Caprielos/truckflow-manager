package it.gabriele.truckflow.domain.sla;

import it.gabriele.truckflow.domain.shared.Money;
import java.util.Objects;

/** Regola economica per calcolare una penale da violazione SLA. */
public final class PenaltyRule {

  private final SlaMetric metric;
  private final Money fixedAmount;
  private final boolean waivable;

  private PenaltyRule(SlaMetric metric, Money fixedAmount, boolean waivable) {
    if (metric == null) {
      throw new IllegalArgumentException("La metrica penale è obbligatoria.");
    }

    if (fixedAmount == null) {
      throw new IllegalArgumentException("L'importo penale è obbligatorio.");
    }

    this.metric = metric;
    this.fixedAmount = fixedAmount;
    this.waivable = waivable;
  }

  public static PenaltyRule fixed(SlaMetric metric, Money fixedAmount, boolean waivable) {
    return new PenaltyRule(metric, fixedAmount, waivable);
  }

  public SlaMetric getMetric() {
    return metric;
  }

  public Money getFixedAmount() {
    return fixedAmount;
  }

  public boolean isWaivable() {
    return waivable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PenaltyRule that)) return false;
    return waivable == that.waivable
        && metric == that.metric
        && fixedAmount.equals(that.fixedAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metric, fixedAmount, waivable);
  }
}
