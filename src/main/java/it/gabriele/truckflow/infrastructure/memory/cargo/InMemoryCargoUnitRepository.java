package it.gabriele.truckflow.infrastructure.memory.cargo;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the cargo unit repository port. */
public final class InMemoryCargoUnitRepository implements CargoUnitRepository {

  private final Map<CargoId, CargoUnit> cargoUnitsById = new HashMap<>();
  private final Map<CargoCode, CargoId> idsByCode = new HashMap<>();

  @Override
  public CargoUnit save(CargoUnit cargoUnit) {
    UseCaseValidationException.requireNonNull(cargoUnit, "cargoUnit");

    CargoId existingId = idsByCode.get(cargoUnit.code());
    if (existingId != null && !existingId.equals(cargoUnit.id())) {
      throw new DuplicateResourceException("CargoUnit", cargoUnit.code().value());
    }

    CargoUnit previousCargoUnit = cargoUnitsById.put(cargoUnit.id(), cargoUnit);
    if (previousCargoUnit != null && !previousCargoUnit.code().equals(cargoUnit.code())) {
      idsByCode.remove(previousCargoUnit.code());
    }

    idsByCode.put(cargoUnit.code(), cargoUnit.id());
    return cargoUnit;
  }

  @Override
  public Optional<CargoUnit> findById(CargoId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(cargoUnitsById.get(id));
  }

  @Override
  public Optional<CargoUnit> findByCode(CargoCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    CargoId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(CargoId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return cargoUnitsById.containsKey(id);
  }

  @Override
  public boolean existsByCode(CargoCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }
}
