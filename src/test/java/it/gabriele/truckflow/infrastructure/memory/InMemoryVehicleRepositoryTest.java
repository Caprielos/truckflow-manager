package it.gabriele.truckflow.infrastructure.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
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
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleCombinationRepository;
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleUnitRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InMemoryVehicleRepositoryTest {

  @Test
  void inMemoryVehicleUnitRepositoryImplementsApplicationPortAndFindsByMainIdentifiers() {
    VehicleUnitRepository repository = new InMemoryVehicleUnitRepository();
    var vehicleUnit = tractorUnit("TRC-MEM-001", "MM111MM", "VINMEMTRC001");

    VehicleUnit saved = repository.save(vehicleUnit);

    assertEquals(vehicleUnit, saved);
    assertTrue(repository.existsById(vehicleUnit.id()));
    assertTrue(repository.existsByFleetCode(FleetCode.of("trc-mem-001")));
    assertTrue(repository.existsByVin(VehicleIdentificationNumber.of("vinmemtrc001")));
    assertTrue(repository.existsByLicensePlate(LicensePlate.of("mm111mm")));
    assertEquals(vehicleUnit, repository.findById(vehicleUnit.id()).orElseThrow());
    assertEquals(vehicleUnit, repository.findByFleetCode(vehicleUnit.fleetCode()).orElseThrow());
    assertEquals(vehicleUnit, repository.findByVin(vehicleUnit.vin()).orElseThrow());
    assertEquals(
        vehicleUnit, repository.findByLicensePlate(vehicleUnit.licensePlate()).orElseThrow());
    assertFalse(repository.findById(VehicleUnitId.random()).isPresent());
  }

  @Test
  void inMemoryVehicleUnitRepositoryRejectsDuplicateMainIdentifiersForDifferentVehicleUnits() {
    VehicleUnitRepository repository = new InMemoryVehicleUnitRepository();
    repository.save(tractorUnit("TRC-DUP-MEM-001", "MM222MM", "VINMEMDUP001"));

    assertThrows(
        DuplicateResourceException.class,
        () -> repository.save(tractorUnit("trc-dup-mem-001", "MM333MM", "VINMEMDUP002")));
    assertThrows(
        DuplicateResourceException.class,
        () -> repository.save(tractorUnit("TRC-DUP-MEM-002", "MM444MM", "VINMEMDUP001")));
    assertThrows(
        DuplicateResourceException.class,
        () -> repository.save(tractorUnit("TRC-DUP-MEM-003", "MM222MM", "VINMEMDUP003")));
  }

  @Test
  void inMemoryVehicleCombinationRepositoryImplementsApplicationPortAndFindsById() {
    VehicleCombinationRepository repository = new InMemoryVehicleCombinationRepository();
    var tractor = tractorUnit("TRC-CMB-MEM-001", "CM111CM", "VINMEMCMB001");
    var combination =
        VehicleCombination.fromUnits(
            null,
            VehicleCombinationType.SINGLE_VEHICLE,
            tractor,
            null,
            VehicleStatus.ACTIVE,
            "In-memory vehicle combination test");

    VehicleCombination saved = repository.save(combination);

    assertEquals(combination, saved);
    assertTrue(repository.existsById(combination.id()));
    assertEquals(combination, repository.findById(combination.id()).orElseThrow());
    assertFalse(repository.findById(VehicleCombinationId.random()).isPresent());
  }

  @Test
  void inMemoryVehicleRepositoriesRejectNullInputsAsApplicationValidationErrors() {
    VehicleUnitRepository vehicleUnitRepository = new InMemoryVehicleUnitRepository();
    VehicleCombinationRepository vehicleCombinationRepository =
        new InMemoryVehicleCombinationRepository();

    assertThrows(UseCaseValidationException.class, () -> vehicleUnitRepository.save(null));
    assertThrows(UseCaseValidationException.class, () -> vehicleUnitRepository.findById(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleUnitRepository.findByFleetCode(null));
    assertThrows(UseCaseValidationException.class, () -> vehicleUnitRepository.findByVin(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleUnitRepository.findByLicensePlate(null));
    assertThrows(UseCaseValidationException.class, () -> vehicleUnitRepository.existsById(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleUnitRepository.existsByFleetCode(null));
    assertThrows(UseCaseValidationException.class, () -> vehicleUnitRepository.existsByVin(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleUnitRepository.existsByLicensePlate(null));

    assertThrows(UseCaseValidationException.class, () -> vehicleCombinationRepository.save(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleCombinationRepository.findById(null));
    assertThrows(
        UseCaseValidationException.class, () -> vehicleCombinationRepository.existsById(null));
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
        "In-memory vehicle repository test tractor");
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
}
