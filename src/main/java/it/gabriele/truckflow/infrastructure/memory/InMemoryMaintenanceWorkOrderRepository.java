package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.MaintenanceWorkOrderRepository;
import it.gabriele.truckflow.domain.maintenance.MaintenanceWorkOrder;

/** Repository in memoria per MaintenanceWorkOrder. */
public final class InMemoryMaintenanceWorkOrderRepository extends InMemoryRepository<MaintenanceWorkOrder> implements MaintenanceWorkOrderRepository {

    public InMemoryMaintenanceWorkOrderRepository() {
        super(item -> item.getWorkOrderNumber());
    }
}
