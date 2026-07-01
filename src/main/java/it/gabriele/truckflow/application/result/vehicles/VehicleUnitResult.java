package it.gabriele.truckflow.application.result.vehicles;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyType;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.PowerSource;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;

/** Result returned by vehicle unit use cases. */
public record VehicleUnitResult(
    VehicleUnitId id,
    FleetCode fleetCode,
    LicensePlate licensePlate,
    VehicleIdentificationNumber vin,
    VehicleUnitType unitType,
    VehicleBodyType bodyType,
    PowerSource powerSource,
    VehicleStatus status,
    boolean hasLicensePlate,
    boolean canTow,
    boolean canBeTowed,
    boolean trailer,
    int capabilityCount,
    int operationalRoleCount)
    implements ApplicationResult {

  public static VehicleUnitResult from(VehicleUnit vehicleUnit) {
    UseCaseValidationException.requireNonNull(vehicleUnit, "vehicleUnit");

    return new VehicleUnitResult(
        vehicleUnit.id(),
        vehicleUnit.fleetCode(),
        vehicleUnit.licensePlate(),
        vehicleUnit.vin(),
        vehicleUnit.unitType(),
        vehicleUnit.bodyType(),
        vehicleUnit.powerSource(),
        vehicleUnit.status(),
        vehicleUnit.hasLicensePlate(),
        vehicleUnit.canTow(),
        vehicleUnit.canBeTowed(),
        vehicleUnit.isTrailer(),
        vehicleUnit.capabilities().size(),
        vehicleUnit.operationalRoles().size());
  }
}
