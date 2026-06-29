package it.gabriele.truckflow.infrastructure.memory.regulation;

import it.gabriele.truckflow.application.port.out.TransportRegulatorySelectionRepository;
import it.gabriele.truckflow.domain.regulation.TransportRegulatorySelection;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TransportRegulatorySelection. */
public final class InMemoryTransportRegulatorySelectionRepository
    extends InMemoryRepository<TransportRegulatorySelection>
    implements TransportRegulatorySelectionRepository {

  public InMemoryTransportRegulatorySelectionRepository() {
    super(selection -> selection.tenantCode());
  }
}
