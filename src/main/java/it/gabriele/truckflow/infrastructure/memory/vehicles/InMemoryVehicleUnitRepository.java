package it.gabriele.truckflow.infrastructure.memory.vehicles;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the vehicle unit repository port. */
public final class InMemoryVehicleUnitRepository implements VehicleUnitRepository {

  private final Map<VehicleUnitId, VehicleUnit> vehicleUnitsById = new HashMap<>();
  private final Map<FleetCode, VehicleUnitId> idsByFleetCode = new HashMap<>();
  private final Map<VehicleIdentificationNumber, VehicleUnitId> idsByVin = new HashMap<>();
  private final Map<LicensePlate, VehicleUnitId> idsByLicensePlate = new HashMap<>();

  @Override
  public VehicleUnit save(VehicleUnit vehicleUnit) {
    UseCaseValidationException.requireNonNull(vehicleUnit, "vehicleUnit");

    ensureUniqueFleetCode(vehicleUnit);
    ensureUniqueVin(vehicleUnit);
    ensureUniqueLicensePlate(vehicleUnit);

    VehicleUnit previousVehicleUnit = vehicleUnitsById.put(vehicleUnit.id(), vehicleUnit);
    if (previousVehicleUnit != null) {
      removePreviousIndexes(previousVehicleUnit, vehicleUnit);
    }

    idsByFleetCode.put(vehicleUnit.fleetCode(), vehicleUnit.id());
    idsByVin.put(vehicleUnit.vin(), vehicleUnit.id());
    if (vehicleUnit.licensePlate() != null) {
      idsByLicensePlate.put(vehicleUnit.licensePlate(), vehicleUnit.id());
    }

    return vehicleUnit;
  }

  @Override
  public Optional<VehicleUnit> findById(VehicleUnitId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(vehicleUnitsById.get(id));
  }

  @Override
  public Optional<VehicleUnit> findByFleetCode(FleetCode fleetCode) {
    UseCaseValidationException.requireNonNull(fleetCode, "fleetCode");
    VehicleUnitId id = idsByFleetCode.get(fleetCode);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<VehicleUnit> findByVin(VehicleIdentificationNumber vin) {
    UseCaseValidationException.requireNonNull(vin, "vin");
    VehicleUnitId id = idsByVin.get(vin);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<VehicleUnit> findByLicensePlate(LicensePlate licensePlate) {
    UseCaseValidationException.requireNonNull(licensePlate, "licensePlate");
    VehicleUnitId id = idsByLicensePlate.get(licensePlate);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(VehicleUnitId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return vehicleUnitsById.containsKey(id);
  }

  @Override
  public boolean existsByFleetCode(FleetCode fleetCode) {
    UseCaseValidationException.requireNonNull(fleetCode, "fleetCode");
    return idsByFleetCode.containsKey(fleetCode);
  }

  @Override
  public boolean existsByVin(VehicleIdentificationNumber vin) {
    UseCaseValidationException.requireNonNull(vin, "vin");
    return idsByVin.containsKey(vin);
  }

  @Override
  public boolean existsByLicensePlate(LicensePlate licensePlate) {
    UseCaseValidationException.requireNonNull(licensePlate, "licensePlate");
    return idsByLicensePlate.containsKey(licensePlate);
  }

  private void ensureUniqueFleetCode(VehicleUnit vehicleUnit) {
    VehicleUnitId existingId = idsByFleetCode.get(vehicleUnit.fleetCode());
    if (existingId != null && !existingId.equals(vehicleUnit.id())) {
      throw new DuplicateResourceException("VehicleUnit", vehicleUnit.fleetCode().value());
    }
  }

  private void ensureUniqueVin(VehicleUnit vehicleUnit) {
    VehicleUnitId existingId = idsByVin.get(vehicleUnit.vin());
    if (existingId != null && !existingId.equals(vehicleUnit.id())) {
      throw new DuplicateResourceException("VehicleUnit", vehicleUnit.vin().value());
    }
  }

  private void ensureUniqueLicensePlate(VehicleUnit vehicleUnit) {
    if (vehicleUnit.licensePlate() == null) {
      return;
    }

    VehicleUnitId existingId = idsByLicensePlate.get(vehicleUnit.licensePlate());
    if (existingId != null && !existingId.equals(vehicleUnit.id())) {
      throw new DuplicateResourceException("VehicleUnit", vehicleUnit.licensePlate().value());
    }
  }

  private void removePreviousIndexes(VehicleUnit previousVehicleUnit, VehicleUnit newVehicleUnit) {
    if (!previousVehicleUnit.fleetCode().equals(newVehicleUnit.fleetCode())) {
      idsByFleetCode.remove(previousVehicleUnit.fleetCode());
    }

    if (!previousVehicleUnit.vin().equals(newVehicleUnit.vin())) {
      idsByVin.remove(previousVehicleUnit.vin());
    }

    if (previousVehicleUnit.licensePlate() != null
        && !previousVehicleUnit.licensePlate().equals(newVehicleUnit.licensePlate())) {
      idsByLicensePlate.remove(previousVehicleUnit.licensePlate());
    }
  }
}
