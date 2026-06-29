package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Risultato economico: ricavi, costi, saldo e margine. */
public final class ProfitabilityResult {

  private final Money totalRevenue;
  private final Money totalCosts;
  private final FinancialBalance netResult;
  private final Percentage marginPercentage;
  private final ProfitabilityStatus status;

  private ProfitabilityResult(Money totalRevenue, Money totalCosts) {
    if (totalRevenue == null) {
      throw new IllegalArgumentException("Il totale ricavi è obbligatorio.");
    }
    if (totalCosts == null) {
      throw new IllegalArgumentException("Il totale costi è obbligatorio.");
    }
    totalRevenue.add(totalCosts);
    this.totalRevenue = totalRevenue;
    this.totalCosts = totalCosts;
    this.netResult = FinancialBalance.from(totalRevenue).subtract(totalCosts);
    this.marginPercentage = calculateMargin(totalRevenue, netResult);
    this.status = calculateStatus(totalRevenue, netResult);
  }

  public static ProfitabilityResult of(Money totalRevenue, Money totalCosts) {
    return new ProfitabilityResult(totalRevenue, totalCosts);
  }

  private static Percentage calculateMargin(Money revenue, FinancialBalance netResult) {
    if (revenue.getAmount().signum() == 0 || netResult.isNegative()) {
      return Percentage.of(BigDecimal.ZERO);
    }
    BigDecimal margin =
        netResult
            .getAmount()
            .multiply(BigDecimal.valueOf(100))
            .divide(revenue.getAmount(), 2, RoundingMode.HALF_UP);
    if (margin.compareTo(BigDecimal.valueOf(100)) > 0) {
      margin = BigDecimal.valueOf(100);
    }
    return Percentage.of(margin);
  }

  private static ProfitabilityStatus calculateStatus(Money revenue, FinancialBalance netResult) {
    if (revenue.getAmount().signum() == 0) {
      return ProfitabilityStatus.NO_REVENUE;
    }
    if (netResult.isPositive()) {
      return ProfitabilityStatus.PROFIT;
    }
    if (netResult.isZero()) {
      return ProfitabilityStatus.BREAK_EVEN;
    }
    return ProfitabilityStatus.LOSS;
  }

  public Money getTotalRevenue() {
    return totalRevenue;
  }

  public Money getTotalCosts() {
    return totalCosts;
  }

  public FinancialBalance getNetResult() {
    return netResult;
  }

  public Percentage getMarginPercentage() {
    return marginPercentage;
  }

  public ProfitabilityStatus getStatus() {
    return status;
  }

  public boolean isProfitable() {
    return status == ProfitabilityStatus.PROFIT;
  }

  public boolean isLossMaking() {
    return status == ProfitabilityStatus.LOSS;
  }

  public boolean isInDebt() {
    return netResult.isNegative();
  }

  public Money getDebtAmount() {
    if (!isInDebt()) {
      return Money.of(BigDecimal.ZERO, totalRevenue.getCurrency());
    }
    return netResult.absoluteMoney();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProfitabilityResult that)) return false;
    return totalRevenue.equals(that.totalRevenue)
        && totalCosts.equals(that.totalCosts)
        && netResult.equals(that.netResult)
        && marginPercentage.equals(that.marginPercentage)
        && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalRevenue, totalCosts, netResult, marginPercentage, status);
  }
}
