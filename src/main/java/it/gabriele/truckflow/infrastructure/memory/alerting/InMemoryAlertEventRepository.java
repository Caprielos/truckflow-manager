package it.gabriele.truckflow.infrastructure.memory.alerting;

import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per alert enterprise. */
public final class InMemoryAlertEventRepository extends InMemoryRepository<AlertEvent>
    implements AlertEventRepository {

  public InMemoryAlertEventRepository() {
    super(AlertEvent::getAlertCode);
  }
}
