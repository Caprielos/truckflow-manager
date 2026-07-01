package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.domain.operational.driver.Driver;
import java.util.Set;

final class DriverMutationSupport {

  private DriverMutationSupport() {}

  static Driver copyOf(Driver driver) {
    return new Driver(
        driver.id(),
        driver.code(),
        driver.userId(),
        driver.profile(),
        Set.copyOf(driver.qualifications()),
        driver.status(),
        driver.metadata(),
        driver.notes());
  }
}
