package it.gabriele.truckflow.infrastructure.memory.identity;

import it.gabriele.truckflow.application.port.out.UserAccountRepository;
import it.gabriele.truckflow.domain.identity.UserAccount;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per UserAccount. */
public final class InMemoryUserAccountRepository extends InMemoryRepository<UserAccount>
    implements UserAccountRepository {

  public InMemoryUserAccountRepository() {
    super(item -> item.getAccountId());
  }
}
