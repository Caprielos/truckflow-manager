package it.gabriele.truckflow.application.port.out.customer;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.customer.Customer;

/** Repository port per Customer. L'implementazione sarà in infrastructure. */
public interface CustomerRepository extends RepositoryPort<Customer> {}
