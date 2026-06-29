package it.gabriele.truckflow.infrastructure.memory.quality;

import it.gabriele.truckflow.application.port.out.QualityEventRepository;
import it.gabriele.truckflow.domain.quality.QualityEvent;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per eventi qualità. */
public final class InMemoryQualityEventRepository extends InMemoryRepository<QualityEvent>
    implements QualityEventRepository {

  public InMemoryQualityEventRepository() {
    super(QualityEvent::getEventCode);
  }
}
