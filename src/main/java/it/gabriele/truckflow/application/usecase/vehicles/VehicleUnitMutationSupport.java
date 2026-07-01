package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;

/** Utility used by vehicle unit use cases to mutate copies before saving. */
final class VehicleUnitMutationSupport {

  private VehicleUnitMutationSupport() {}

  static VehicleUnit copyOf(VehicleUnit vehicleUnit) {
    return new VehicleUnit(
        vehicleUnit.id(),
        vehicleUnit.fleetCode(),
        vehicleUnit.licensePlate(),
        vehicleUnit.vin(),
        vehicleUnit.unitType(),
        vehicleUnit.bodyType(),
        vehicleUnit.powerSource(),
        vehicleUnit.technicalSpecification(),
        vehicleUnit.bodyProfile(),
        vehicleUnit.capabilities(),
        vehicleUnit.operationalRoles(),
        vehicleUnit.couplingProfile(),
        vehicleUnit.status(),
        vehicleUnit.notes());
  }
}
