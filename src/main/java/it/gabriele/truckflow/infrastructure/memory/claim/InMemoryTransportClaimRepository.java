package it.gabriele.truckflow.infrastructure.memory.claim;

import it.gabriele.truckflow.application.port.out.TransportClaimRepository;
import it.gabriele.truckflow.domain.claim.TransportClaim;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TransportClaim. */
public final class InMemoryTransportClaimRepository extends InMemoryRepository<TransportClaim>
    implements TransportClaimRepository {

  public InMemoryTransportClaimRepository() {
    super(item -> item.getClaimNumber());
  }
}
