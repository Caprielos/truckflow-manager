package it.gabriele.truckflow.infrastructure.memory.compliance;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the compliance requirement repository port. */
public final class InMemoryComplianceRequirementRepository
    implements ComplianceRequirementRepository {

  private final Map<ComplianceRequirementId, ComplianceRequirement> requirementsById =
      new HashMap<>();
  private final Map<ComplianceRequirementCode, ComplianceRequirementId> idsByCode = new HashMap<>();

  @Override
  public ComplianceRequirement save(ComplianceRequirement requirement) {
    UseCaseValidationException.requireNonNull(requirement, "requirement");

    ComplianceRequirementId existingId = idsByCode.get(requirement.code());
    if (existingId != null && !existingId.equals(requirement.id())) {
      throw new DuplicateResourceException("ComplianceRequirement", requirement.code().value());
    }

    ComplianceRequirement previousRequirement = requirementsById.put(requirement.id(), requirement);
    if (previousRequirement != null && !previousRequirement.code().equals(requirement.code())) {
      idsByCode.remove(previousRequirement.code());
    }

    idsByCode.put(requirement.code(), requirement.id());
    return requirement;
  }

  @Override
  public Optional<ComplianceRequirement> findById(ComplianceRequirementId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(requirementsById.get(id));
  }

  @Override
  public Optional<ComplianceRequirement> findByCode(ComplianceRequirementCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    ComplianceRequirementId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(ComplianceRequirementId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return requirementsById.containsKey(id);
  }

  @Override
  public boolean existsByCode(ComplianceRequirementCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }
}
