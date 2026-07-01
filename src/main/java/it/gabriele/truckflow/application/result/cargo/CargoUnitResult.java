package it.gabriele.truckflow.application.result.cargo;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoUnit;

/** Result returned by cargo unit use cases. */
public record CargoUnitResult(
    CargoId id, CargoCode code, String name, CargoType type, CargoStatus status)
    implements ApplicationResult {

  public static CargoUnitResult from(CargoUnit cargoUnit) {
    UseCaseValidationException.requireNonNull(cargoUnit, "cargoUnit");

    return new CargoUnitResult(
        cargoUnit.id(), cargoUnit.code(), cargoUnit.name(), cargoUnit.type(), cargoUnit.status());
  }
}
