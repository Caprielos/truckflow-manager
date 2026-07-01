package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;
import java.util.Locale;

public enum ComplianceJurisdictionScope {
  NATIONAL,
  EUROPEAN_UNION,
  INTERNATIONAL,
  COMPANY_INTERNAL,
  CUSTOMER_SPECIFIC,
  REGIONAL,
  OTHER;

  public static ComplianceJurisdictionScope from(String value) {
    String normalized =
        ComplianceValidation.requireText(value, "scope")
            .replace('-', '_')
            .replace(' ', '_')
            .toUpperCase(Locale.ROOT);

    try {
      return ComplianceJurisdictionScope.valueOf(normalized);
    } catch (IllegalArgumentException exception) {
      throw new InvalidComplianceRequirementException(
          "Compliance jurisdiction scope is not supported: " + normalized + ".");
    }
  }
}
