package it.gabriele.truckflow.application.port.out.maintenance;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.maintenance.MaintenanceWorkOrder;

/** Repository port per MaintenanceWorkOrder. L'implementazione sarà in infrastructure. */
public interface MaintenanceWorkOrderRepository extends RepositoryPort<MaintenanceWorkOrder> {}
