package it.gabriele.truckflow.application.port.out.identity;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.identity.UserAccount;

/** Repository port per UserAccount. L'implementazione sarà in infrastructure. */
public interface UserAccountRepository extends RepositoryPort<UserAccount> {}
