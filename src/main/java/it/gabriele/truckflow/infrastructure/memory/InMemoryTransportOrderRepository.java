package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TransportOrderRepository;
import it.gabriele.truckflow.domain.order.TransportOrder;

/** Repository in memoria per TransportOrder. */
public final class InMemoryTransportOrderRepository extends InMemoryRepository<TransportOrder> implements TransportOrderRepository {

    public InMemoryTransportOrderRepository() {
        super(item -> item.getOrderNumber());
    }
}
