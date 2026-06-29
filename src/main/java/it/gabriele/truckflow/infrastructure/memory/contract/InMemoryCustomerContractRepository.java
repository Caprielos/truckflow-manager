package it.gabriele.truckflow.infrastructure.memory.contract;

import it.gabriele.truckflow.application.port.out.CustomerContractRepository;
import it.gabriele.truckflow.domain.contract.CustomerContract;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CustomerContract. */
public final class InMemoryCustomerContractRepository extends InMemoryRepository<CustomerContract>
    implements CustomerContractRepository {

  public InMemoryCustomerContractRepository() {
    super(item -> item.getContractCode());
  }
}
