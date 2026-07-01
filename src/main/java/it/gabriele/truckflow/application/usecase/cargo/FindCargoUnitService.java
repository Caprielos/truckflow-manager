package it.gabriele.truckflow.application.usecase.cargo;

import it.gabriele.truckflow.application.command.cargo.FindCargoUnitCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.cargo.FindCargoUnitUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.result.cargo.CargoUnitResult;

/** Application service that finds cargo units. */
public final class FindCargoUnitService implements FindCargoUnitUseCase {

  private final CargoUnitRepository cargoUnitRepository;

  public FindCargoUnitService(CargoUnitRepository cargoUnitRepository) {
    UseCaseValidationException.requireNonNull(cargoUnitRepository, "cargoUnitRepository");
    this.cargoUnitRepository = cargoUnitRepository;
  }

  @Override
  public CargoUnitResult execute(FindCargoUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return cargoUnitRepository
        .findById(command.cargoId())
        .map(CargoUnitResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("CargoUnit", command.cargoId()));
  }
}
