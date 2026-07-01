package it.gabriele.truckflow.application.port.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceSourceType;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ApplicationComplianceRepositoryPortTest {

  @Test
  void complianceRequirementRepositoryPortStoresAndFindsRequirementsByIdAndCode() {
    var repository = new InMemoryTestComplianceRequirementRepository();
    var requirement = requirement("CMP-PORT-001");

    ComplianceRequirement saved = repository.save(requirement);

    assertEquals(requirement, saved);
    assertTrue(repository.existsById(requirement.id()));
    assertTrue(repository.existsByCode(ComplianceRequirementCode.of("cmp-port-001")));
    assertEquals(requirement, repository.findById(requirement.id()).orElseThrow());
    assertEquals(requirement, repository.findByCode(requirement.code()).orElseThrow());
    assertFalse(repository.existsByCode(ComplianceRequirementCode.of("CMP-PORT-404")));
  }

  @Test
  void complianceRepositoryPortIsAnApplicationContractAndNotConcreteInfrastructure() {
    assertTrue(RepositoryPort.class.isAssignableFrom(ComplianceRequirementRepository.class));
    assertTrue(ComplianceRequirementRepository.class.isInterface());
  }

  private static ComplianceRequirement requirement(String code) {
    return new ComplianceRequirement(
        null,
        ComplianceRequirementCode.of(code),
        "Requirement " + code,
        "Application compliance repository port test requirement",
        ComplianceRequirementStatus.ACTIVE,
        ComplianceCategory.CARGO,
        ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
        ComplianceObligationLevel.MANDATORY,
        ComplianceSeverity.CRITICAL,
        new ComplianceTarget(ComplianceTargetType.CARGO, "Applies to cargo."),
        new ComplianceRule("Rule", "Statement", "Condition", "Notes"),
        new ComplianceSource("Source", ComplianceSourceType.OTHER, "", "Description", "Notes"),
        ComplianceJurisdiction.companyInternal(),
        "Application 6L repository port test requirement");
  }

  private static final class InMemoryTestComplianceRequirementRepository
      implements ComplianceRequirementRepository {

    private final Map<ComplianceRequirementId, ComplianceRequirement> byId = new HashMap<>();
    private final Map<ComplianceRequirementCode, ComplianceRequirement> byCode = new HashMap<>();

    @Override
    public ComplianceRequirement save(ComplianceRequirement requirement) {
      byId.put(requirement.id(), requirement);
      byCode.put(requirement.code(), requirement);
      return requirement;
    }

    @Override
    public Optional<ComplianceRequirement> findById(ComplianceRequirementId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<ComplianceRequirement> findByCode(ComplianceRequirementCode code) {
      return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public boolean existsById(ComplianceRequirementId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByCode(ComplianceRequirementCode code) {
      return byCode.containsKey(code);
    }
  }
}
