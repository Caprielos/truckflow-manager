package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TransportClaimRepository;
import it.gabriele.truckflow.domain.claim.TransportClaim;

/** Repository in memoria per TransportClaim. */
public final class InMemoryTransportClaimRepository extends InMemoryRepository<TransportClaim>
    implements TransportClaimRepository {

  public InMemoryTransportClaimRepository() {
    super(item -> item.getClaimNumber());
  }
}
