package it.gabriele.truckflow.application.result.operational;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.manager.Manager;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;
import it.gabriele.truckflow.domain.users.UserId;

/** Result returned by manager operational role use cases. */
public record ManagerResult(
    ManagerId id,
    OperationalCode code,
    UserId userId,
    OperationalStatus status,
    String fullName,
    boolean active,
    int scopeCount,
    String notes)
    implements ApplicationResult {

  public static ManagerResult from(Manager manager) {
    UseCaseValidationException.requireNonNull(manager, "manager");

    return new ManagerResult(
        manager.id(),
        manager.code(),
        manager.userId(),
        manager.status(),
        manager.profile().fullName(),
        manager.isActive(),
        manager.scopes().size(),
        manager.notes());
  }
}
