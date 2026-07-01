package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Set;

/** Command used to register a new operational warehouse operator role. */
public record RegisterWarehouseOperatorCommand(
    OperationalCode code,
    UserId userId,
    OperationalProfile profile,
    Set<OperationalQualification> qualifications,
    OperationalStatus status,
    OperationalMetadata metadata,
    String notes)
    implements ApplicationCommand {

  public RegisterWarehouseOperatorCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNonNull(userId, "userId");
    UseCaseValidationException.requireNonNull(profile, "profile");
    UseCaseValidationException.requireNonNull(status, "status");
    UseCaseValidationException.requireNonNull(metadata, "metadata");

    qualifications = immutableSetWithoutNullElements(qualifications, "qualifications");
  }

  private static <T> Set<T> immutableSetWithoutNullElements(Set<T> values, String fieldName) {
    if (values == null || values.isEmpty()) {
      return Set.of();
    }

    if (values.stream().anyMatch(value -> value == null)) {
      throw new UseCaseValidationException(fieldName + " must not contain null elements");
    }

    return Set.copyOf(values);
  }
}
