package it.gabriele.truckflow.domain.compliance;

import java.util.Optional;

public record ComplianceJurisdiction(
    Optional<CountryCode> country,
    Optional<JurisdictionRegion> region,
    ComplianceJurisdictionScope scope,
    String notes) {

  public ComplianceJurisdiction {
    country = country == null ? Optional.empty() : country;
    region = region == null ? Optional.empty() : region;
    scope = ComplianceValidation.requireNonNull(scope, "scope");
    notes = ComplianceValidation.normalize(notes);
  }

  public ComplianceJurisdiction(String country, String region, String scope, String notes) {
    this(
        CountryCode.optional(country),
        JurisdictionRegion.optional(region),
        ComplianceJurisdictionScope.from(scope),
        notes);
  }

  public ComplianceJurisdiction(
      CountryCode country,
      JurisdictionRegion region,
      ComplianceJurisdictionScope scope,
      String notes) {
    this(Optional.ofNullable(country), Optional.ofNullable(region), scope, notes);
  }

  public static ComplianceJurisdiction italy() {
    return new ComplianceJurisdiction(
        CountryCode.of("IT"), null, ComplianceJurisdictionScope.NATIONAL, "");
  }

  public static ComplianceJurisdiction europeanUnion() {
    return new ComplianceJurisdiction(
        null, JurisdictionRegion.of("EU"), ComplianceJurisdictionScope.EUROPEAN_UNION, "");
  }

  public static ComplianceJurisdiction companyInternal() {
    return new ComplianceJurisdiction(
        Optional.empty(), Optional.empty(), ComplianceJurisdictionScope.COMPANY_INTERNAL, "");
  }

  public String countryValue() {
    return country.map(CountryCode::value).orElse("");
  }

  public String regionValue() {
    return region.map(JurisdictionRegion::value).orElse("");
  }
}
