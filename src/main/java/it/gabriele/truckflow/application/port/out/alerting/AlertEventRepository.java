package it.gabriele.truckflow.application.port.out.alerting;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.alerting.AlertEvent;

/** Repository port per alert operativi enterprise. */
public interface AlertEventRepository extends RepositoryPort<AlertEvent> {}
