package it.gabriele.truckflow.application.port.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyType;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationType;
import it.gabriele.truckflow.domain.vehicles.coupling.CouplingProfile;
import it.gabriele.truckflow.domain.vehicles.coupling.CouplingType;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleCapability;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleOperationalRole;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleAxle;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleAxleSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleCabSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleChassisSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleDimensions;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleElectricSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleEngineSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleLoadSpace;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleTechnicalSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleTireSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleTransmissionSpecification;
import it.gabriele.truckflow.domain.vehicles.specification.VehicleWeights;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.PowerSource;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationVehicleRepositoryPortTest {

  @Test
  void vehicleUnitRepositoryPortStoresAndFindsVehicleUnitsByMainIdentifiers() {
    var repository = new InMemoryTestVehicleUnitRepository();
    var vehicleUnit = tractorUnit("TRC-PORT-001", "PP111PP", "VINPORTTRC001");

    VehicleUnit saved = repository.save(vehicleUnit);

    assertEquals(vehicleUnit, saved);
    assertTrue(repository.existsById(vehicleUnit.id()));
    assertTrue(repository.existsByFleetCode(FleetCode.of("trc-port-001")));
    assertTrue(repository.existsByVin(VehicleIdentificationNumber.of("vinporttrc001")));
    assertTrue(repository.existsByLicensePlate(LicensePlate.of("pp111pp")));
    assertEquals(vehicleUnit, repository.findById(vehicleUnit.id()).orElseThrow());
    assertEquals(vehicleUnit, repository.findByFleetCode(vehicleUnit.fleetCode()).orElseThrow());
    assertEquals(vehicleUnit, repository.findByVin(vehicleUnit.vin()).orElseThrow());
    assertEquals(
        vehicleUnit, repository.findByLicensePlate(vehicleUnit.licensePlate()).orElseThrow());
    assertFalse(repository.existsByFleetCode(FleetCode.of("TRC-404")));
  }

  @Test
  void vehicleCombinationRepositoryPortStoresAndFindsVehicleCombinationsById() {
    var repository = new InMemoryTestVehicleCombinationRepository();
    var tractor = tractorUnit("TRC-PORT-002", "PP222PP", "VINPORTTRC002");
    var combination =
        VehicleCombination.fromUnits(
            null,
            VehicleCombinationType.SINGLE_VEHICLE,
            tractor,
            null,
            VehicleStatus.ACTIVE,
            "Repository port test single vehicle");

    VehicleCombination saved = repository.save(combination);

    assertEquals(combination, saved);
    assertTrue(repository.existsById(combination.id()));
    assertEquals(combination, repository.findById(combination.id()).orElseThrow());
    assertFalse(repository.existsById(VehicleCombinationId.random()));
  }

  @Test
  void vehicleRepositoryPortsAreApplicationContractsAndNotConcreteInfrastructure() {
    assertTrue(RepositoryPort.class.isAssignableFrom(VehicleUnitRepository.class));
    assertTrue(RepositoryPort.class.isAssignableFrom(VehicleCombinationRepository.class));
    assertTrue(VehicleUnitRepository.class.isInterface());
    assertTrue(VehicleCombinationRepository.class.isInterface());
  }

  private static VehicleUnit tractorUnit(String code, String plate, String vin) {
    return new VehicleUnit(
        null,
        FleetCode.of(code),
        LicensePlate.of(plate),
        VehicleIdentificationNumber.of(vin),
        VehicleUnitType.TRACTOR_UNIT,
        VehicleBodyType.NONE,
        PowerSource.DIESEL,
        technicalSpecification(),
        null,
        Set.of(VehicleCapability.ADR),
        Set.of(VehicleOperationalRole.LINE_HAUL),
        new CouplingProfile(
            CouplingType.FIFTH_WHEEL,
            true,
            false,
            new BigDecimal("36000"),
            new BigDecimal("44000"),
            "Fifth wheel tractor"),
        VehicleStatus.ACTIVE,
        "Application vehicle repository port test tractor");
  }

  private static VehicleTechnicalSpecification technicalSpecification() {
    return new VehicleTechnicalSpecification(
        new VehicleDimensions(new BigDecimal("13.60"), new BigDecimal("2.55"), null, null),
        new VehicleLoadSpace(new BigDecimal("13.40"), new BigDecimal("2.48"), null, null, 33),
        new VehicleWeights(null, new BigDecimal("44000"), null, null, null),
        new VehicleAxleSpecification(
            List.of(
                new VehicleAxle(1, true, false, false, "front"),
                new VehicleAxle(2, false, false, true, "rear"))),
        new VehicleTireSpecification("315/70 R22.5", "ROAD", 6, true),
        VehicleEngineSpecification.empty(),
        VehicleTransmissionSpecification.empty(),
        VehicleChassisSpecification.empty(),
        VehicleElectricSpecification.empty(),
        VehicleCabSpecification.empty());
  }

  private static final class InMemoryTestVehicleUnitRepository implements VehicleUnitRepository {

    private final Map<VehicleUnitId, VehicleUnit> byId = new HashMap<>();
    private final Map<FleetCode, VehicleUnit> byFleetCode = new HashMap<>();
    private final Map<VehicleIdentificationNumber, VehicleUnit> byVin = new HashMap<>();
    private final Map<LicensePlate, VehicleUnit> byLicensePlate = new HashMap<>();

    @Override
    public VehicleUnit save(VehicleUnit vehicleUnit) {
      byId.put(vehicleUnit.id(), vehicleUnit);
      byFleetCode.put(vehicleUnit.fleetCode(), vehicleUnit);
      byVin.put(vehicleUnit.vin(), vehicleUnit);
      if (vehicleUnit.licensePlate() != null) {
        byLicensePlate.put(vehicleUnit.licensePlate(), vehicleUnit);
      }
      return vehicleUnit;
    }

    @Override
    public Optional<VehicleUnit> findById(VehicleUnitId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public Optional<VehicleUnit> findByFleetCode(FleetCode fleetCode) {
      return Optional.ofNullable(byFleetCode.get(fleetCode));
    }

    @Override
    public Optional<VehicleUnit> findByVin(VehicleIdentificationNumber vin) {
      return Optional.ofNullable(byVin.get(vin));
    }

    @Override
    public Optional<VehicleUnit> findByLicensePlate(LicensePlate licensePlate) {
      return Optional.ofNullable(byLicensePlate.get(licensePlate));
    }

    @Override
    public boolean existsById(VehicleUnitId id) {
      return byId.containsKey(id);
    }

    @Override
    public boolean existsByFleetCode(FleetCode fleetCode) {
      return byFleetCode.containsKey(fleetCode);
    }

    @Override
    public boolean existsByVin(VehicleIdentificationNumber vin) {
      return byVin.containsKey(vin);
    }

    @Override
    public boolean existsByLicensePlate(LicensePlate licensePlate) {
      return byLicensePlate.containsKey(licensePlate);
    }
  }

  private static final class InMemoryTestVehicleCombinationRepository
      implements VehicleCombinationRepository {

    private final Map<VehicleCombinationId, VehicleCombination> byId = new HashMap<>();

    @Override
    public VehicleCombination save(VehicleCombination vehicleCombination) {
      byId.put(vehicleCombination.id(), vehicleCombination);
      return vehicleCombination;
    }

    @Override
    public Optional<VehicleCombination> findById(VehicleCombinationId id) {
      return Optional.ofNullable(byId.get(id));
    }

    @Override
    public boolean existsById(VehicleCombinationId id) {
      return byId.containsKey(id);
    }
  }
}
