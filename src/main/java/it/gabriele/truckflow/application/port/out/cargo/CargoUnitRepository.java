package it.gabriele.truckflow.application.port.out.cargo;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoUnit;
import java.util.Optional;

/** Outbound repository port used by cargo unit use cases. */
public interface CargoUnitRepository extends RepositoryPort {

  CargoUnit save(CargoUnit cargoUnit);

  Optional<CargoUnit> findById(CargoId id);

  Optional<CargoUnit> findByCode(CargoCode code);

  boolean existsById(CargoId id);

  boolean existsByCode(CargoCode code);
}
