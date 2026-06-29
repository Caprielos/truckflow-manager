package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;

/** Repository in memoria per alert enterprise. */
public final class InMemoryAlertEventRepository extends InMemoryRepository<AlertEvent>
    implements AlertEventRepository {

  public InMemoryAlertEventRepository() {
    super(AlertEvent::getAlertCode);
  }
}
