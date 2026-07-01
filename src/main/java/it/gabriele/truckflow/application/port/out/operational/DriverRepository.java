package it.gabriele.truckflow.application.port.out.operational;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.driver.Driver;
import it.gabriele.truckflow.domain.operational.driver.DriverId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Optional;

/** Outbound repository port used by driver operational role use cases. */
public interface DriverRepository extends RepositoryPort {

  Driver save(Driver driver);

  Optional<Driver> findById(DriverId id);

  Optional<Driver> findByCode(OperationalCode code);

  Optional<Driver> findByUserId(UserId userId);

  boolean existsById(DriverId id);

  boolean existsByCode(OperationalCode code);

  boolean existsByUserId(UserId userId);
}
