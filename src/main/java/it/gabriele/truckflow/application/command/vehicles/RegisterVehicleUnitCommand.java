package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyProfile;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyType;
import it.gabriele.truckflow.domain.vehicles.coupling.CouplingProfile;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleCapability;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleOperationalRole;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleTechnicalSpecification;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.PowerSource;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;
import java.util.Set;

/** Command used to register a new physical vehicle unit. */
public record RegisterVehicleUnitCommand(
    FleetCode fleetCode,
    LicensePlate licensePlate,
    VehicleIdentificationNumber vin,
    VehicleUnitType unitType,
    VehicleBodyType bodyType,
    PowerSource powerSource,
    VehicleTechnicalSpecification technicalSpecification,
    VehicleBodyProfile bodyProfile,
    Set<VehicleCapability> capabilities,
    Set<VehicleOperationalRole> operationalRoles,
    CouplingProfile couplingProfile,
    VehicleStatus status,
    String notes)
    implements ApplicationCommand {

  public RegisterVehicleUnitCommand {
    UseCaseValidationException.requireNonNull(fleetCode, "fleetCode");
    UseCaseValidationException.requireNonNull(vin, "vin");
    UseCaseValidationException.requireNonNull(unitType, "unitType");
    UseCaseValidationException.requireNonNull(bodyType, "bodyType");
    UseCaseValidationException.requireNonNull(powerSource, "powerSource");
    UseCaseValidationException.requireNonNull(technicalSpecification, "technicalSpecification");
    UseCaseValidationException.requireNonNull(status, "status");

    capabilities = immutableSetWithoutNullElements(capabilities, "capabilities");
    operationalRoles = immutableSetWithoutNullElements(operationalRoles, "operationalRoles");
  }

  private static <T> Set<T> immutableSetWithoutNullElements(Set<T> values, String fieldName) {
    if (values == null || values.isEmpty()) {
      return Set.of();
    }

    if (values.stream().anyMatch(value -> value == null)) {
      throw new UseCaseValidationException(fieldName + " must not contain null elements");
    }

    return Set.copyOf(values);
  }
}
