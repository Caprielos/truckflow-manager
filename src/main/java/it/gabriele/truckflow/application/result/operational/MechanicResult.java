package it.gabriele.truckflow.application.result.operational;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;
import it.gabriele.truckflow.domain.users.UserId;

/** Result returned by mechanic operational role use cases. */
public record MechanicResult(
    MechanicId id,
    OperationalCode code,
    UserId userId,
    OperationalStatus status,
    String fullName,
    boolean active,
    int qualificationCount,
    String notes)
    implements ApplicationResult {

  public static MechanicResult from(Mechanic mechanic) {
    UseCaseValidationException.requireNonNull(mechanic, "mechanic");

    return new MechanicResult(
        mechanic.id(),
        mechanic.code(),
        mechanic.userId(),
        mechanic.status(),
        mechanic.profile().fullName(),
        mechanic.isActive(),
        mechanic.qualifications().size(),
        mechanic.notes());
  }
}
