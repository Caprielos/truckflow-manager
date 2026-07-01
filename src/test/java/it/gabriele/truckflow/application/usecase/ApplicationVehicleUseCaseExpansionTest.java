package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.vehicles.ActivateVehicleUnitCommand;
import it.gabriele.truckflow.application.command.vehicles.DismissVehicleUnitCommand;
import it.gabriele.truckflow.application.command.vehicles.FindVehicleCombinationCommand;
import it.gabriele.truckflow.application.command.vehicles.FindVehicleUnitCommand;
import it.gabriele.truckflow.application.command.vehicles.MarkVehicleUnitOutOfServiceCommand;
import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleCombinationCommand;
import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleUnitCommand;
import it.gabriele.truckflow.application.command.vehicles.SuspendVehicleUnitCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.vehicles.ActivateVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.DismissVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.MarkVehicleUnitOutOfServiceService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.SuspendVehicleUnitService;
import it.gabriele.truckflow.domain.vehicles.body.CurtainsiderBodyProfile;
import it.gabriele.truckflow.domain.vehicles.body.RefrigeratedBodyProfile;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyType;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationType;
import it.gabriele.truckflow.domain.vehicles.coupling.CouplingProfile;
import it.gabriele.truckflow.domain.vehicles.coupling.CouplingType;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleCombinationException;
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
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleCombinationRepository;
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleUnitRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationVehicleUseCaseExpansionTest {

  @Test
  void vehicleUnitUseCasesRegisterFindAndManageStatusesUsingCopyOnWrite() {
    var context = new VehicleUseCaseContext();

    var registered =
        context.registerVehicleUnit.execute(tractorCommand("TRC-001", "AB123CD", "VINTRC001"));

    assertEquals(FleetCode.of("trc-001"), registered.fleetCode());
    assertEquals(VehicleStatus.ACTIVE, registered.status());
    assertTrue(registered.canTow());
    assertFalse(registered.trailer());
    assertEquals(1, registered.capabilityCount());

    var found = context.findVehicleUnit.execute(new FindVehicleUnitCommand(registered.id()));
    assertEquals(registered, found);

    var storedActive = context.vehicleUnitRepository.findById(registered.id()).orElseThrow();
    var suspended =
        context.suspendVehicleUnit.execute(new SuspendVehicleUnitCommand(registered.id()));
    assertEquals(VehicleStatus.SUSPENDED, suspended.status());
    assertEquals(VehicleStatus.ACTIVE, storedActive.status());

    var outOfService =
        context.markVehicleUnitOutOfService.execute(
            new MarkVehicleUnitOutOfServiceCommand(registered.id()));
    assertEquals(VehicleStatus.OUT_OF_SERVICE, outOfService.status());

    var dismissed =
        context.dismissVehicleUnit.execute(new DismissVehicleUnitCommand(registered.id()));
    assertEquals(VehicleStatus.DISMISSED, dismissed.status());

    var activeAgain =
        context.activateVehicleUnit.execute(new ActivateVehicleUnitCommand(registered.id()));
    assertEquals(VehicleStatus.ACTIVE, activeAgain.status());
    assertEquals(
        VehicleStatus.ACTIVE,
        context.vehicleUnitRepository.findById(registered.id()).orElseThrow().status());
  }

  @Test
  void vehicleUnitUseCasesRejectDuplicateFleetCodesVinsAndLicensePlates() {
    var context = new VehicleUseCaseContext();
    context.registerVehicleUnit.execute(tractorCommand("TRC-DUP-001", "AA111AA", "VINDUP001"));

    assertThrows(
        DuplicateResourceException.class,
        () ->
            context.registerVehicleUnit.execute(
                tractorCommand("trc-dup-001", "BB222BB", "VINDUP002")));
    assertThrows(
        DuplicateResourceException.class,
        () ->
            context.registerVehicleUnit.execute(
                tractorCommand("TRC-DUP-002", "CC333CC", "VINDUP001")));
    assertThrows(
        DuplicateResourceException.class,
        () ->
            context.registerVehicleUnit.execute(
                tractorCommand("TRC-DUP-003", "AA111AA", "VINDUP003")));
  }

  @Test
  void vehicleUnitUseCasesRejectMissingResourcesNullCommandsAndNullDependencies() {
    var context = new VehicleUseCaseContext();
    var unknownVehicleUnitId = VehicleUnitId.random();

    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findVehicleUnit.execute(new FindVehicleUnitCommand(unknownVehicleUnitId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.activateVehicleUnit.execute(
                new ActivateVehicleUnitCommand(unknownVehicleUnitId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.suspendVehicleUnit.execute(
                new SuspendVehicleUnitCommand(unknownVehicleUnitId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.markVehicleUnitOutOfService.execute(
                new MarkVehicleUnitOutOfServiceCommand(unknownVehicleUnitId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.dismissVehicleUnit.execute(
                new DismissVehicleUnitCommand(unknownVehicleUnitId)));

    assertThrows(UseCaseValidationException.class, () -> context.registerVehicleUnit.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findVehicleUnit.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateVehicleUnit.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendVehicleUnit.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.markVehicleUnitOutOfService.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.dismissVehicleUnit.execute(null));

    assertThrows(UseCaseValidationException.class, () -> new RegisterVehicleUnitService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindVehicleUnitService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateVehicleUnitService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendVehicleUnitService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new MarkVehicleUnitOutOfServiceService(null));
    assertThrows(UseCaseValidationException.class, () -> new DismissVehicleUnitService(null));
  }

  @Test
  void vehicleUnitCommandRejectsMissingRequiredInputsAndNormalizesOptionalSets() {
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterVehicleUnitCommand(
                null,
                LicensePlate.of("AB123CD"),
                VehicleIdentificationNumber.of("VIN001"),
                VehicleUnitType.TRACTOR_UNIT,
                VehicleBodyType.NONE,
                PowerSource.DIESEL,
                technicalSpecification(),
                null,
                Set.of(VehicleCapability.ADR),
                Set.of(VehicleOperationalRole.LINE_HAUL),
                tractorCouplingProfile(),
                VehicleStatus.ACTIVE,
                "Missing fleet code"));

    var command =
        new RegisterVehicleUnitCommand(
            FleetCode.of("WH-001"),
            null,
            VehicleIdentificationNumber.of("VINWH001"),
            VehicleUnitType.WAREHOUSE_EQUIPMENT,
            VehicleBodyType.NONE,
            PowerSource.ELECTRIC,
            technicalSpecification(),
            null,
            null,
            null,
            CouplingProfile.none(),
            VehicleStatus.ACTIVE,
            "Warehouse equipment without plate");

    assertTrue(command.capabilities().isEmpty());
    assertTrue(command.operationalRoles().isEmpty());
    assertThrows(UnsupportedOperationException.class, () -> command.capabilities().add(null));
  }

  @Test
  void vehicleCombinationUseCasesRegisterAndFindArticulatedVehiclesFromExistingUnits() {
    var context = new VehicleUseCaseContext();
    var tractor =
        context.registerVehicleUnit.execute(
            tractorCommand("TRC-CMB-001", "CC111CC", "VINCMBTRC001"));
    var semiTrailer =
        context.registerVehicleUnit.execute(
            semiTrailerCommand("TRL-CMB-001", "XA111AA", "VINCMBTRL001"));

    var registeredCombination =
        context.registerVehicleCombination.execute(
            new RegisterVehicleCombinationCommand(
                VehicleCombinationType.ARTICULATED_VEHICLE,
                tractor.id(),
                semiTrailer.id(),
                VehicleStatus.ACTIVE,
                "Application 6I articulated vehicle"));

    assertEquals(
        VehicleCombinationType.ARTICULATED_VEHICLE, registeredCombination.combinationType());
    assertEquals(tractor.id(), registeredCombination.primaryUnitId());
    assertEquals(semiTrailer.id(), registeredCombination.secondaryUnitId());
    assertTrue(registeredCombination.hasSecondaryUnit());
    assertEquals(VehicleStatus.ACTIVE, registeredCombination.status());
    assertEquals(4, registeredCombination.capabilityCount());

    var found =
        context.findVehicleCombination.execute(
            new FindVehicleCombinationCommand(registeredCombination.id()));
    assertEquals(registeredCombination, found);
  }

  @Test
  void vehicleCombinationUseCasesRejectMissingResourcesInvalidShapesAndNullDependencies() {
    var context = new VehicleUseCaseContext();
    var tractor =
        context.registerVehicleUnit.execute(
            tractorCommand("TRC-ERR-001", "EE111EE", "VINERRTRC001"));

    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.registerVehicleCombination.execute(
                new RegisterVehicleCombinationCommand(
                    VehicleCombinationType.ARTICULATED_VEHICLE,
                    tractor.id(),
                    VehicleUnitId.random(),
                    VehicleStatus.ACTIVE,
                    "Missing semi-trailer")));
    assertThrows(
        InvalidVehicleCombinationException.class,
        () ->
            context.registerVehicleCombination.execute(
                new RegisterVehicleCombinationCommand(
                    VehicleCombinationType.ARTICULATED_VEHICLE,
                    tractor.id(),
                    null,
                    VehicleStatus.ACTIVE,
                    "Invalid articulated vehicle without semi-trailer")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.findVehicleCombination.execute(
                new FindVehicleCombinationCommand(VehicleCombinationId.random())));
    assertThrows(
        UseCaseValidationException.class, () -> context.registerVehicleCombination.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.findVehicleCombination.execute(null));
    assertThrows(
        UseCaseValidationException.class,
        () -> new RegisterVehicleCombinationService(null, context.vehicleCombinationRepository));
    assertThrows(
        UseCaseValidationException.class,
        () -> new RegisterVehicleCombinationService(context.vehicleUnitRepository, null));
    assertThrows(UseCaseValidationException.class, () -> new FindVehicleCombinationService(null));
  }

  private static RegisterVehicleUnitCommand tractorCommand(String code, String plate, String vin) {
    return new RegisterVehicleUnitCommand(
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
        tractorCouplingProfile(),
        VehicleStatus.ACTIVE,
        "Application 6I tractor unit");
  }

  private static RegisterVehicleUnitCommand semiTrailerCommand(
      String code, String plate, String vin) {
    return new RegisterVehicleUnitCommand(
        FleetCode.of(code),
        LicensePlate.of(plate),
        VehicleIdentificationNumber.of(vin),
        VehicleUnitType.SEMI_TRAILER,
        VehicleBodyType.REFRIGERATED,
        PowerSource.NONE,
        technicalSpecification(),
        new RefrigeratedBodyProfile(
            "FRC", new BigDecimal("-20"), new BigDecimal("4"), true, "Carrier", "Vector", ""),
        Set.of(
            VehicleCapability.ATP,
            VehicleCapability.REEFER_UNIT,
            VehicleCapability.TEMPERATURE_CONTROLLED),
        Set.of(VehicleOperationalRole.REFRIGERATED_TRANSPORT, VehicleOperationalRole.FULL_LOAD),
        new CouplingProfile(CouplingType.KINGPIN, false, true, null, null, "Kingpin"),
        VehicleStatus.ACTIVE,
        "Application 6I refrigerated semi-trailer");
  }

  private static CouplingProfile tractorCouplingProfile() {
    return new CouplingProfile(
        CouplingType.FIFTH_WHEEL,
        true,
        false,
        new BigDecimal("36000"),
        new BigDecimal("44000"),
        "Fifth wheel tractor");
  }

  @SuppressWarnings("unused")
  private static CurtainsiderBodyProfile curtainsiderBodyProfile() {
    return new CurtainsiderBodyProfile(true, true, true, false, "");
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

  private static final class VehicleUseCaseContext {

    private final InMemoryVehicleUnitRepository vehicleUnitRepository =
        new InMemoryVehicleUnitRepository();
    private final InMemoryVehicleCombinationRepository vehicleCombinationRepository =
        new InMemoryVehicleCombinationRepository();

    private final RegisterVehicleUnitService registerVehicleUnit =
        new RegisterVehicleUnitService(vehicleUnitRepository);
    private final FindVehicleUnitService findVehicleUnit =
        new FindVehicleUnitService(vehicleUnitRepository);
    private final ActivateVehicleUnitService activateVehicleUnit =
        new ActivateVehicleUnitService(vehicleUnitRepository);
    private final SuspendVehicleUnitService suspendVehicleUnit =
        new SuspendVehicleUnitService(vehicleUnitRepository);
    private final MarkVehicleUnitOutOfServiceService markVehicleUnitOutOfService =
        new MarkVehicleUnitOutOfServiceService(vehicleUnitRepository);
    private final DismissVehicleUnitService dismissVehicleUnit =
        new DismissVehicleUnitService(vehicleUnitRepository);
    private final RegisterVehicleCombinationService registerVehicleCombination =
        new RegisterVehicleCombinationService(vehicleUnitRepository, vehicleCombinationRepository);
    private final FindVehicleCombinationService findVehicleCombination =
        new FindVehicleCombinationService(vehicleCombinationRepository);
  }
}
