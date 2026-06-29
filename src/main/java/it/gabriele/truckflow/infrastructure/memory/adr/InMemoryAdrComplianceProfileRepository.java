package it.gabriele.truckflow.infrastructure.memory.adr;

import it.gabriele.truckflow.application.port.out.adr.AdrComplianceProfileRepository;
import it.gabriele.truckflow.domain.adr.AdrComplianceProfile;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per AdrComplianceProfile. */
public final class InMemoryAdrComplianceProfileRepository
    extends InMemoryRepository<AdrComplianceProfile> implements AdrComplianceProfileRepository {

  public InMemoryAdrComplianceProfileRepository() {
    super(profile -> profile.profileCode());
  }
}
