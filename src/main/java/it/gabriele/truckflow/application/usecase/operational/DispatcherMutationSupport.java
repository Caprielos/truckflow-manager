package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import java.util.Set;

final class DispatcherMutationSupport {

  private DispatcherMutationSupport() {}

  static Dispatcher copyOf(Dispatcher dispatcher) {
    return new Dispatcher(
        dispatcher.id(),
        dispatcher.code(),
        dispatcher.userId(),
        dispatcher.profile(),
        Set.copyOf(dispatcher.scopes()),
        dispatcher.status(),
        dispatcher.metadata(),
        dispatcher.notes());
  }
}
