package it.gabriele.truckflow.application.result.operational;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.driver.Driver;
import it.gabriele.truckflow.domain.operational.driver.DriverId;
import it.gabriele.truckflow.domain.users.UserId;

/** Result returned by driver operational role use cases. */
public record DriverResult(
    DriverId id,
    OperationalCode code,
    UserId userId,
    OperationalStatus status,
    String fullName,
    boolean active,
    int qualificationCount,
    String notes)
    implements ApplicationResult {

  public static DriverResult from(Driver driver) {
    UseCaseValidationException.requireNonNull(driver, "driver");

    return new DriverResult(
        driver.id(),
        driver.code(),
        driver.userId(),
        driver.status(),
        driver.profile().fullName(),
        driver.isActive(),
        driver.qualifications().size(),
        driver.notes());
  }
}
