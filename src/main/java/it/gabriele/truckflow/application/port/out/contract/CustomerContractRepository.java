package it.gabriele.truckflow.application.port.out.contract;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.contract.CustomerContract;

/** Repository port per CustomerContract. L'implementazione sarà in infrastructure. */
public interface CustomerContractRepository extends RepositoryPort<CustomerContract> {}
