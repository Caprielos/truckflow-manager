package it.gabriele.truckflow.application.result.operational;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;
import it.gabriele.truckflow.domain.users.UserId;

/** Result returned by dispatcher operational role use cases. */
public record DispatcherResult(
    DispatcherId id,
    OperationalCode code,
    UserId userId,
    OperationalStatus status,
    String fullName,
    boolean active,
    int scopeCount,
    String notes)
    implements ApplicationResult {

  public static DispatcherResult from(Dispatcher dispatcher) {
    UseCaseValidationException.requireNonNull(dispatcher, "dispatcher");

    return new DispatcherResult(
        dispatcher.id(),
        dispatcher.code(),
        dispatcher.userId(),
        dispatcher.status(),
        dispatcher.profile().fullName(),
        dispatcher.isActive(),
        dispatcher.scopes().size(),
        dispatcher.notes());
  }
}
