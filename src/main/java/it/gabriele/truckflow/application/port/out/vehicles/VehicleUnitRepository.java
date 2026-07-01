package it.gabriele.truckflow.application.port.out.vehicles;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import java.util.Optional;

/** Outbound repository port used by vehicle unit use cases. */
public interface VehicleUnitRepository extends RepositoryPort {

  VehicleUnit save(VehicleUnit vehicleUnit);

  Optional<VehicleUnit> findById(VehicleUnitId id);

  Optional<VehicleUnit> findByFleetCode(FleetCode fleetCode);

  Optional<VehicleUnit> findByVin(VehicleIdentificationNumber vin);

  Optional<VehicleUnit> findByLicensePlate(LicensePlate licensePlate);

  boolean existsById(VehicleUnitId id);

  boolean existsByFleetCode(FleetCode fleetCode);

  boolean existsByVin(VehicleIdentificationNumber vin);

  boolean existsByLicensePlate(LicensePlate licensePlate);
}
