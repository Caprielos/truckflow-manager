package it.gabriele.truckflow.application.command.cargo;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoCompatibilityRequirement;
import it.gabriele.truckflow.domain.cargo.CargoDimensions;
import it.gabriele.truckflow.domain.cargo.CargoHazard;
import it.gabriele.truckflow.domain.cargo.CargoPackaging;
import it.gabriele.truckflow.domain.cargo.CargoProperties;
import it.gabriele.truckflow.domain.cargo.CargoRegulatory;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoTemperature;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoWeights;
import java.util.Set;

/** Command used to register a new cargo unit. */
public record RegisterCargoUnitCommand(
    CargoCode code,
    String name,
    String description,
    CargoType type,
    Set<CargoCategory> categories,
    CargoDimensions dimensions,
    CargoWeights weights,
    CargoPackaging packaging,
    CargoTemperature temperature,
    CargoHazard hazard,
    CargoRegulatory regulatory,
    CargoProperties properties,
    CargoCompatibilityRequirement compatibilityRequirement,
    CargoStatus status,
    String notes)
    implements ApplicationCommand {

  public RegisterCargoUnitCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNotBlank(name, "name");
    UseCaseValidationException.requireNonNull(type, "type");
    UseCaseValidationException.requireNonNull(status, "status");
    if (categories == null || categories.isEmpty()) {
      throw new UseCaseValidationException("categories must not be empty");
    }
    if (categories.stream().anyMatch(category -> category == null)) {
      throw new UseCaseValidationException("categories must not contain null elements");
    }
    categories = Set.copyOf(categories);
  }
}
