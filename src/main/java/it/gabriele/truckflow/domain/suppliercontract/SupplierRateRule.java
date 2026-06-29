package it.gabriele.truckflow.domain.suppliercontract;

import it.gabriele.truckflow.domain.shared.Money;

/** Regola tariffaria del fornitore/sub-vettore. */
public record SupplierRateRule(
    String ruleCode,
    SupplierRateType rateType,
    Money amount,
    String zoneCode,
    boolean requiresPreApproval) {

  public SupplierRateRule {
    ruleCode = normalize(ruleCode, "Il codice regola tariffaria è obbligatorio.");
    if (rateType == null) {
      throw new IllegalArgumentException("Il tipo tariffa è obbligatorio.");
    }
    if (amount == null) {
      throw new IllegalArgumentException("L'importo tariffa è obbligatorio.");
    }
    zoneCode = zoneCode == null ? "" : zoneCode.trim().toUpperCase();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
