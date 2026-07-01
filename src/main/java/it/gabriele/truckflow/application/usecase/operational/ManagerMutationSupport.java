package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.domain.operational.manager.Manager;
import java.util.Set;

final class ManagerMutationSupport {

  private ManagerMutationSupport() {}

  static Manager copyOf(Manager manager) {
    return new Manager(
        manager.id(),
        manager.code(),
        manager.userId(),
        manager.profile(),
        Set.copyOf(manager.scopes()),
        manager.status(),
        manager.metadata(),
        manager.notes());
  }
}
