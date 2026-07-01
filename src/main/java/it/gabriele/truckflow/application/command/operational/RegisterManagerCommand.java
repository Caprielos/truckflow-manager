package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Set;

/** Command used to register a new operational manager role. */
public record RegisterManagerCommand(
    OperationalCode code,
    UserId userId,
    OperationalProfile profile,
    Set<OperationalScope> scopes,
    OperationalStatus status,
    OperationalMetadata metadata,
    String notes)
    implements ApplicationCommand {

  public RegisterManagerCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNonNull(userId, "userId");
    UseCaseValidationException.requireNonNull(profile, "profile");
    UseCaseValidationException.requireNonNull(status, "status");
    UseCaseValidationException.requireNonNull(metadata, "metadata");

    scopes = immutableSetWithoutNullElements(scopes, "scopes");
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
