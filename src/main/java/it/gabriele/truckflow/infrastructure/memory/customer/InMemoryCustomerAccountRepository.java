package it.gabriele.truckflow.infrastructure.memory.customer;

import it.gabriele.truckflow.application.port.out.CustomerAccountRepository;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CustomerAccount. */
public final class InMemoryCustomerAccountRepository extends InMemoryRepository<CustomerAccount>
    implements CustomerAccountRepository {

  public InMemoryCustomerAccountRepository() {
    super(item -> item.getCustomerCode());
  }
}
