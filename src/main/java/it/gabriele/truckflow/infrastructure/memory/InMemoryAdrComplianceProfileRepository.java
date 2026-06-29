package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.AdrComplianceProfileRepository;
import it.gabriele.truckflow.domain.adr.AdrComplianceProfile;

/** Repository in memoria per AdrComplianceProfile. */
public final class InMemoryAdrComplianceProfileRepository
    extends InMemoryRepository<AdrComplianceProfile> implements AdrComplianceProfileRepository {

  public InMemoryAdrComplianceProfileRepository() {
    super(profile -> profile.profileCode());
  }
}
