package it.gabriele.truckflow.infrastructure.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceSourceType;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;
import it.gabriele.truckflow.infrastructure.memory.compliance.InMemoryComplianceRequirementRepository;
import org.junit.jupiter.api.Test;

class InMemoryComplianceRepositoryTest {

  @Test
  void complianceRequirementRepositoryStoresFindsAndChecksRequirements() {
    var repository = new InMemoryComplianceRequirementRepository();
    var requirement = requirement("CMP-MEM-001");

    ComplianceRequirement saved = repository.save(requirement);

    assertEquals(requirement, saved);
    assertTrue(repository.existsById(requirement.id()));
    assertTrue(repository.existsByCode(ComplianceRequirementCode.of("cmp-mem-001")));
    assertEquals(requirement, repository.findById(requirement.id()).orElseThrow());
    assertEquals(requirement, repository.findByCode(requirement.code()).orElseThrow());
    assertFalse(repository.existsByCode(ComplianceRequirementCode.of("CMP-MEM-404")));
  }

  @Test
  void complianceRequirementRepositoryRejectsDuplicateCodesAcrossDifferentIds() {
    var repository = new InMemoryComplianceRequirementRepository();
    repository.save(requirement("CMP-MEM-DUP"));

    assertThrows(
        DuplicateResourceException.class, () -> repository.save(requirement("cmp-mem-dup")));
  }

  @Test
  void complianceRequirementRepositoryRejectsNullInputs() {
    var repository = new InMemoryComplianceRequirementRepository();

    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
  }

  private static ComplianceRequirement requirement(String code) {
    return new ComplianceRequirement(
        null,
        ComplianceRequirementCode.of(code),
        "Requirement " + code,
        "Application in-memory compliance repository test requirement",
        ComplianceRequirementStatus.ACTIVE,
        ComplianceCategory.CARGO,
        ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
        ComplianceObligationLevel.MANDATORY,
        ComplianceSeverity.CRITICAL,
        new ComplianceTarget(ComplianceTargetType.CARGO, "Applies to cargo."),
        new ComplianceRule("Rule", "Statement", "Condition", "Notes"),
        new ComplianceSource("Source", ComplianceSourceType.OTHER, "", "Description", "Notes"),
        ComplianceJurisdiction.companyInternal(),
        "Application 6L in-memory repository test requirement");
  }
}
