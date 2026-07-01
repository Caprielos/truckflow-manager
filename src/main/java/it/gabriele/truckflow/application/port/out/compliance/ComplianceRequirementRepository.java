package it.gabriele.truckflow.application.port.out.compliance;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import java.util.Optional;

/** Outbound repository port used by compliance requirement use cases. */
public interface ComplianceRequirementRepository extends RepositoryPort {

  ComplianceRequirement save(ComplianceRequirement requirement);

  Optional<ComplianceRequirement> findById(ComplianceRequirementId id);

  Optional<ComplianceRequirement> findByCode(ComplianceRequirementCode code);

  boolean existsById(ComplianceRequirementId id);

  boolean existsByCode(ComplianceRequirementCode code);
}
