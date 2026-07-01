package it.gabriele.truckflow.application.usecase.cargo;

import it.gabriele.truckflow.application.command.cargo.RegisterCargoUnitCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.cargo.RegisterCargoUnitUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.result.cargo.CargoUnitResult;
import it.gabriele.truckflow.domain.cargo.CargoUnit;

/** Application service that registers cargo units. */
public final class RegisterCargoUnitService implements RegisterCargoUnitUseCase {

  private final CargoUnitRepository cargoUnitRepository;

  public RegisterCargoUnitService(CargoUnitRepository cargoUnitRepository) {
    UseCaseValidationException.requireNonNull(cargoUnitRepository, "cargoUnitRepository");
    this.cargoUnitRepository = cargoUnitRepository;
  }

  @Override
  public CargoUnitResult execute(RegisterCargoUnitCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (cargoUnitRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("CargoUnit", command.code().value());
    }

    var cargoUnit =
        new CargoUnit(
            null,
            command.code(),
            command.name(),
            command.description(),
            command.type(),
            command.categories(),
            command.dimensions(),
            command.weights(),
            command.packaging(),
            command.temperature(),
            command.hazard(),
            command.regulatory(),
            command.properties(),
            command.compatibilityRequirement(),
            command.status(),
            command.notes());

    return CargoUnitResult.from(cargoUnitRepository.save(cargoUnit));
  }
}
