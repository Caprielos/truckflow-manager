package it.gabriele.truckflow.domain.compliance;

public record ComplianceJurisdiction(String country, String region, String scope, String notes) {

  public ComplianceJurisdiction {
    country = ComplianceValidation.normalize(country).toUpperCase();
    region = ComplianceValidation.normalize(region).toUpperCase();
    scope = ComplianceValidation.requireText(scope, "scope").toUpperCase();
    notes = ComplianceValidation.normalize(notes);
  }

  public static ComplianceJurisdiction italy() {
    return new ComplianceJurisdiction("IT", "", "NATIONAL", "");
  }

  public static ComplianceJurisdiction europeanUnion() {
    return new ComplianceJurisdiction("", "EU", "EUROPEAN_UNION", "");
  }

  public static ComplianceJurisdiction companyInternal() {
    return new ComplianceJurisdiction("", "", "COMPANY_INTERNAL", "");
  }
}
