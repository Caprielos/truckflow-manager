package it.gabriele.truckflow.domain.compliance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ComplianceDomainTest {

  @Test
  void createsMandatoryCriticalAdrCargoRequirement() {
    var requirement = adrCargoRequirement();

    assertTrue(requirement.isActive());
    assertTrue(requirement.isMandatory());
    assertTrue(requirement.isCritical());
    assertTrue(requirement.appliesTo(ComplianceTargetType.CARGO));
    assertEquals(ComplianceRequirementCode.of("CMP-ADR-001"), requirement.code());
    assertEquals(ComplianceCategory.CARGO, requirement.category());
    assertEquals(ComplianceRequirementType.CARGO_REGULATORY_REQUIRED, requirement.type());
  }

  @Test
  void complianceRuleIsDescriptiveAndNotExecutable() {
    var rule =
        new ComplianceRule(
            "ADR vehicle requirement",
            "Dangerous cargo requires ADR-compatible transport.",
            "Cargo must declare ADR transport requirements.",
            "No automatic check is executed here.");

    assertEquals("ADR vehicle requirement", rule.title());
    assertEquals("Cargo must declare ADR transport requirements.", rule.expectedCondition());
  }

  @Test
  void sourceKeepsOriginOfRequirement() {
    var source =
        new ComplianceSource(
            "Internal safety policy",
            ComplianceSourceType.INTERNAL_POLICY,
            "POL-SAFE-001",
            "Company safety policy for dangerous goods.",
            "Applies before dispatching checks.");

    assertEquals(ComplianceSourceType.INTERNAL_POLICY, source.sourceType());
    assertEquals("POL-SAFE-001", source.referenceCode());
  }

  @Test
  void jurisdictionNormalizesCountryRegionAndScope() {
    var jurisdiction = new ComplianceJurisdiction(" it ", " eu ", " national ", "Italy rule");

    assertEquals("IT", jurisdiction.country());
    assertEquals("EU", jurisdiction.region());
    assertEquals("NATIONAL", jurisdiction.scope());
  }

  @Test
  void complianceStatusIsRequirementLifecycleNotCheckResult() {
    var requirement = adrCargoRequirement();

    requirement.suspend();
    assertEquals(ComplianceRequirementStatus.SUSPENDED, requirement.status());

    requirement.archive();
    assertEquals(ComplianceRequirementStatus.ARCHIVED, requirement.status());
  }

  @Test
  void targetIsAbstractAndDoesNotReferenceConcreteAggregateIds() {
    var target =
        new ComplianceTarget(ComplianceTargetType.DOCUMENT, "Applies to documents generally.");

    assertEquals(ComplianceTargetType.DOCUMENT, target.targetType());
    assertTrue(target.appliesTo(ComplianceTargetType.DOCUMENT));
  }

  @Test
  void blankRequirementCodeIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> ComplianceRequirementCode.of(" "));
  }

  @Test
  void blankRequirementNameIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new ComplianceRequirement(
                null,
                ComplianceRequirementCode.of("CMP-INVALID"),
                " ",
                "Invalid requirement",
                ComplianceRequirementStatus.ACTIVE,
                ComplianceCategory.GENERIC,
                ComplianceRequirementType.INTERNAL_POLICY,
                ComplianceObligationLevel.OPTIONAL,
                ComplianceSeverity.LOW,
                ComplianceTarget.of(ComplianceTargetType.GENERIC),
                new ComplianceRule("Rule", "Statement", "Condition", ""),
                new ComplianceSource("Source", ComplianceSourceType.OTHER, "", "Description", ""),
                ComplianceJurisdiction.companyInternal(),
                ""));
  }

  @Test
  void blankJurisdictionScopeIsRejected() {
    assertThrows(
        IllegalArgumentException.class, () -> new ComplianceJurisdiction("IT", "", " ", ""));
  }

  private static ComplianceRequirement adrCargoRequirement() {
    return new ComplianceRequirement(
        null,
        ComplianceRequirementCode.of("cmp-adr-001"),
        "ADR requirement for dangerous cargo",
        "Dangerous cargo must declare ADR regulatory requirements before planning.",
        ComplianceRequirementStatus.ACTIVE,
        ComplianceCategory.CARGO,
        ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
        ComplianceObligationLevel.MANDATORY,
        ComplianceSeverity.CRITICAL,
        new ComplianceTarget(ComplianceTargetType.CARGO, "Applies to ADR cargo."),
        new ComplianceRule(
            "ADR cargo rule",
            "Cargo marked as ADR must require ADR-compatible transport.",
            "ADR transport requirement must be declared.",
            "Descriptive rule only."),
        new ComplianceSource(
            "European ADR framework",
            ComplianceSourceType.EU_REGULATION,
            "ADR",
            "Reference source for dangerous goods transport.",
            "Conceptual source, no automatic legal check."),
        ComplianceJurisdiction.europeanUnion(),
        "Pure compliance requirement.");
  }
}
