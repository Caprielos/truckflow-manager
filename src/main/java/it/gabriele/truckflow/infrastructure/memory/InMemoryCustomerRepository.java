package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CustomerRepository;
import it.gabriele.truckflow.domain.customer.Customer;

/** Repository in memoria per Customer. */
public final class InMemoryCustomerRepository extends InMemoryRepository<Customer>
    implements CustomerRepository {

  public InMemoryCustomerRepository() {
    super(item -> item.getCode());
  }
}
