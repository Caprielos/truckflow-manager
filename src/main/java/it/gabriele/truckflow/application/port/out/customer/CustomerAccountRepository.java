package it.gabriele.truckflow.application.port.out.customer;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.customer.CustomerAccount;

/** Repository port per CustomerAccount. L'implementazione sarà in infrastructure. */
public interface CustomerAccountRepository extends RepositoryPort<CustomerAccount> {}
