package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CustomerContractRepository;
import it.gabriele.truckflow.domain.contract.CustomerContract;

/** Repository in memoria per CustomerContract. */
public final class InMemoryCustomerContractRepository extends InMemoryRepository<CustomerContract> implements CustomerContractRepository {

    public InMemoryCustomerContractRepository() {
        super(item -> item.getContractCode());
    }
}
