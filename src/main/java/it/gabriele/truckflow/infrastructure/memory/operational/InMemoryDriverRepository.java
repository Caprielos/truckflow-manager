package it.gabriele.truckflow.infrastructure.memory.operational;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.driver.Driver;
import it.gabriele.truckflow.domain.operational.driver.DriverId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the driver repository port. */
public final class InMemoryDriverRepository implements DriverRepository {

  private final Map<DriverId, Driver> driversById = new HashMap<>();
  private final Map<OperationalCode, DriverId> idsByCode = new HashMap<>();
  private final Map<UserId, DriverId> idsByUserId = new HashMap<>();

  @Override
  public Driver save(Driver driver) {
    UseCaseValidationException.requireNonNull(driver, "driver");

    ensureUniqueCode(driver);
    ensureUniqueUserId(driver);

    Driver previousDriver = driversById.put(driver.id(), driver);
    if (previousDriver != null) {
      removePreviousIndexes(previousDriver, driver);
    }

    idsByCode.put(driver.code(), driver.id());
    idsByUserId.put(driver.userId(), driver.id());

    return driver;
  }

  @Override
  public Optional<Driver> findById(DriverId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(driversById.get(id));
  }

  @Override
  public Optional<Driver> findByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    DriverId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<Driver> findByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    DriverId id = idsByUserId.get(userId);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(DriverId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return driversById.containsKey(id);
  }

  @Override
  public boolean existsByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }

  @Override
  public boolean existsByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    return idsByUserId.containsKey(userId);
  }

  private void ensureUniqueCode(Driver driver) {
    DriverId existingId = idsByCode.get(driver.code());
    if (existingId != null && !existingId.equals(driver.id())) {
      throw new DuplicateResourceException("Driver", driver.code().value());
    }
  }

  private void ensureUniqueUserId(Driver driver) {
    DriverId existingId = idsByUserId.get(driver.userId());
    if (existingId != null && !existingId.equals(driver.id())) {
      throw new DuplicateResourceException("Driver", driver.userId().value());
    }
  }

  private void removePreviousIndexes(Driver previousDriver, Driver newDriver) {
    if (!previousDriver.code().equals(newDriver.code())) {
      idsByCode.remove(previousDriver.code());
    }

    if (!previousDriver.userId().equals(newDriver.userId())) {
      idsByUserId.remove(previousDriver.userId());
    }
  }
}
