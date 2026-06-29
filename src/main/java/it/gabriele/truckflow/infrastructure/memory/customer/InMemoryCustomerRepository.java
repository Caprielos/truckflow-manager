package it.gabriele.truckflow.infrastructure.memory.customer;

import it.gabriele.truckflow.application.port.out.CustomerRepository;
import it.gabriele.truckflow.domain.customer.Customer;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Customer. */
public final class InMemoryCustomerRepository extends InMemoryRepository<Customer>
    implements CustomerRepository {

  public InMemoryCustomerRepository() {
    super(item -> item.getCode());
  }
}
