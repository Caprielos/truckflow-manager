package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.UserAccountRepository;
import it.gabriele.truckflow.domain.identity.UserAccount;

/** Repository in memoria per UserAccount. */
public final class InMemoryUserAccountRepository extends InMemoryRepository<UserAccount> implements UserAccountRepository {

    public InMemoryUserAccountRepository() {
        super(item -> item.getAccountId());
    }
}
