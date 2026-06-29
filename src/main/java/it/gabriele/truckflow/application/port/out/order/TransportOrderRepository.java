package it.gabriele.truckflow.application.port.out.order;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.order.TransportOrder;

/** Repository port per TransportOrder. L'implementazione sarà in infrastructure. */
public interface TransportOrderRepository extends RepositoryPort<TransportOrder> {}
