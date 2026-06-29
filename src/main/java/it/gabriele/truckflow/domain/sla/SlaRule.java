package it.gabriele.truckflow.domain.sla;

import java.util.Objects;

/** Regola SLA misurabile, ad esempio consegna entro X minuti o POD entro X ore. */
public final class SlaRule {

  private static final int MAX_CODE_LENGTH = 50;

  private final String ruleCode;
  private final SlaMetric metric;
  private final int allowedMinutes;
  private final double requiredPercentage;
  private final boolean critical;

  private SlaRule(
      String ruleCode,
      SlaMetric metric,
      int allowedMinutes,
      double requiredPercentage,
      boolean critical) {
    this.ruleCode = validateCode(ruleCode);

    if (metric == null) {
      throw new IllegalArgumentException("La metrica SLA è obbligatoria.");
    }

    if (allowedMinutes < 0) {
      throw new IllegalArgumentException("I minuti ammessi SLA non possono essere negativi.");
    }

    if (Double.isNaN(requiredPercentage)
        || Double.isInfinite(requiredPercentage)
        || requiredPercentage < 0
        || requiredPercentage > 100) {
      throw new IllegalArgumentException("La percentuale SLA deve essere tra 0 e 100.");
    }

    this.metric = metric;
    this.allowedMinutes = allowedMinutes;
    this.requiredPercentage = requiredPercentage;
    this.critical = critical;
  }

  public static SlaRule of(
      String ruleCode,
      SlaMetric metric,
      int allowedMinutes,
      double requiredPercentage,
      boolean critical) {
    return new SlaRule(ruleCode, metric, allowedMinutes, requiredPercentage, critical);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice regola SLA è obbligatorio.");
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice regola SLA non può essere vuoto.");
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice regola SLA non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice regola SLA può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getRuleCode() {
    return ruleCode;
  }

  public SlaMetric getMetric() {
    return metric;
  }

  public int getAllowedMinutes() {
    return allowedMinutes;
  }

  public double getRequiredPercentage() {
    return requiredPercentage;
  }

  public boolean isCritical() {
    return critical;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SlaRule slaRule)) return false;
    return allowedMinutes == slaRule.allowedMinutes
        && Double.compare(requiredPercentage, slaRule.requiredPercentage) == 0
        && critical == slaRule.critical
        && ruleCode.equals(slaRule.ruleCode)
        && metric == slaRule.metric;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleCode, metric, allowedMinutes, requiredPercentage, critical);
  }
}
