package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.operational.ActivateDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.ActivateDriverCommand;
import it.gabriele.truckflow.application.command.operational.FindDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.FindDriverCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDriverCommand;
import it.gabriele.truckflow.application.command.operational.RegisterDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.RegisterDriverCommand;
import it.gabriele.truckflow.application.command.operational.RegisterManagerCommand;
import it.gabriele.truckflow.application.command.operational.RegisterMechanicCommand;
import it.gabriele.truckflow.application.command.operational.RegisterWarehouseOperatorCommand;
import it.gabriele.truckflow.application.command.operational.SuspendDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.SuspendDriverCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.operational.ActivateDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.ActivateDriverService;
import it.gabriele.truckflow.application.usecase.operational.FindDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.FindDriverService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleDriverService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDriverService;
import it.gabriele.truckflow.application.usecase.operational.RegisterManagerService;
import it.gabriele.truckflow.application.usecase.operational.RegisterMechanicService;
import it.gabriele.truckflow.application.usecase.operational.RegisterWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDriverService;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;
import it.gabriele.truckflow.domain.operational.driver.DriverId;
import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.users.UserId;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDispatcherRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDriverRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryManagerRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryMechanicRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryWarehouseOperatorRepository;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationOperationalUseCaseExpansionTest {

  @Test
  void operationalRoleUseCasesRegisterAndFindAllCurrentRoles() {
    var context = new OperationalUseCaseContext();

    var driver = context.registerDriver.execute(driverCommand("DRV-001", UserId.random()));
    var mechanic = context.registerMechanic.execute(mechanicCommand("MEC-001", UserId.random()));
    var warehouseOperator =
        context.registerWarehouseOperator.execute(
            warehouseOperatorCommand("WH-001", UserId.random()));
    var dispatcher =
        context.registerDispatcher.execute(dispatcherCommand("DSP-001", UserId.random()));
    var manager = context.registerManager.execute(managerCommand("MGR-001", UserId.random()));

    assertEquals(OperationalCode.of("drv-001"), driver.code());
    assertEquals(OperationalStatus.ACTIVE, driver.status());
    assertTrue(driver.active());
    assertEquals(1, driver.qualificationCount());
    assertEquals(driver, context.findDriver.execute(new FindDriverCommand(driver.id())));

    assertEquals(OperationalStatus.ACTIVE, mechanic.status());
    assertEquals(1, mechanic.qualificationCount());
    assertEquals(OperationalStatus.ACTIVE, warehouseOperator.status());
    assertEquals(1, warehouseOperator.qualificationCount());
    assertEquals(OperationalStatus.ACTIVE, dispatcher.status());
    assertEquals(1, dispatcher.scopeCount());
    assertEquals(
        dispatcher, context.findDispatcher.execute(new FindDispatcherCommand(dispatcher.id())));
    assertEquals(OperationalStatus.ACTIVE, manager.status());
    assertEquals(1, manager.scopeCount());
  }

  @Test
  void operationalRoleStatusUseCasesUseCopyOnWriteBeforeSaving() {
    var context = new OperationalUseCaseContext();
    var driver = context.registerDriver.execute(driverCommand("DRV-COW-001", UserId.random()));
    var dispatcher =
        context.registerDispatcher.execute(dispatcherCommand("DSP-COW-001", UserId.random()));

    var storedActiveDriver = context.driverRepository.findById(driver.id()).orElseThrow();
    var suspendedDriver =
        context.suspendDriver.execute(new SuspendDriverCommand(driver.id(), "application-6j"));
    assertEquals(OperationalStatus.SUSPENDED, suspendedDriver.status());
    assertEquals(OperationalStatus.ACTIVE, storedActiveDriver.status());

    var notEligibleDriver =
        context.markNotEligibleDriver.execute(
            new MarkNotEligibleDriverCommand(driver.id(), "application-6j"));
    assertEquals(OperationalStatus.NOT_ELIGIBLE, notEligibleDriver.status());

    var activeDriver =
        context.activateDriver.execute(new ActivateDriverCommand(driver.id(), "application-6j"));
    assertEquals(OperationalStatus.ACTIVE, activeDriver.status());

    var storedActiveDispatcher =
        context.dispatcherRepository.findById(dispatcher.id()).orElseThrow();
    var suspendedDispatcher =
        context.suspendDispatcher.execute(
            new SuspendDispatcherCommand(dispatcher.id(), "application-6j"));
    assertEquals(OperationalStatus.SUSPENDED, suspendedDispatcher.status());
    assertEquals(OperationalStatus.ACTIVE, storedActiveDispatcher.status());

    var activeDispatcher =
        context.activateDispatcher.execute(
            new ActivateDispatcherCommand(dispatcher.id(), "application-6j"));
    assertEquals(OperationalStatus.ACTIVE, activeDispatcher.status());
  }

  @Test
  void operationalRoleUseCasesRejectDuplicatesMissingResourcesNullInputsAndNullDependencies() {
    var context = new OperationalUseCaseContext();
    var userId = UserId.random();
    context.registerDriver.execute(driverCommand("DRV-DUP-001", userId));

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerDriver.execute(driverCommand("drv-dup-001", UserId.random())));
    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerDriver.execute(driverCommand("DRV-DUP-002", userId)));

    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findDriver.execute(new FindDriverCommand(DriverId.random())));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.suspendDriver.execute(new SuspendDriverCommand(DriverId.random(), "system")));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findDispatcher.execute(new FindDispatcherCommand(DispatcherId.random())));

    assertThrows(UseCaseValidationException.class, () -> context.registerDriver.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findDriver.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendDriver.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> new SuspendDriverCommand(DriverId.random(), "   "));

    assertThrows(UseCaseValidationException.class, () -> new RegisterDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new MarkNotEligibleDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new RegisterDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDispatcherService(null));
  }

  @Test
  void operationalRoleCommandsRejectMissingRequiredInputsAndNullCollectionElements() {
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDriverCommand(
                null,
                UserId.random(),
                profile("Missing", "Code"),
                qualifications(Qualification.DRIVING_LICENSE_C),
                OperationalStatus.ACTIVE,
                metadata(),
                "Missing code"));

    var suspendedDriverWithoutQualifications =
        new RegisterDriverCommand(
            OperationalCode.of("DRV-NO-QUAL"),
            UserId.random(),
            profile("Suspended", "Driver"),
            null,
            OperationalStatus.SUSPENDED,
            metadata(),
            "Suspended drivers can be registered before qualifications are loaded");
    assertTrue(suspendedDriverWithoutQualifications.qualifications().isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> suspendedDriverWithoutQualifications.qualifications().add(null));

    Set<OperationalQualification> qualificationsWithNull = new HashSet<>();
    qualificationsWithNull.add(null);
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDriverCommand(
                OperationalCode.of("DRV-NULL-QUAL"),
                UserId.random(),
                profile("Null", "Qualification"),
                qualificationsWithNull,
                OperationalStatus.SUSPENDED,
                metadata(),
                "Null qualification"));

    Set<OperationalScope> scopesWithNull = new HashSet<>();
    scopesWithNull.add(null);
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDispatcherCommand(
                OperationalCode.of("DSP-NULL-SCOPE"),
                UserId.random(),
                profile("Null", "Scope"),
                scopesWithNull,
                OperationalStatus.SUSPENDED,
                metadata(),
                "Null scope"));
  }

  private static RegisterDriverCommand driverCommand(String code, UserId userId) {
    return new RegisterDriverCommand(
        OperationalCode.of(code),
        userId,
        profile("Mario", "Driver"),
        qualifications(Qualification.DRIVING_LICENSE_C),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6J driver");
  }

  private static RegisterMechanicCommand mechanicCommand(String code, UserId userId) {
    return new RegisterMechanicCommand(
        OperationalCode.of(code),
        userId,
        profile("Marco", "Mechanic"),
        qualifications(Qualification.TRUCK_MOUNTED_CRANE),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6J mechanic");
  }

  private static RegisterWarehouseOperatorCommand warehouseOperatorCommand(
      String code, UserId userId) {
    return new RegisterWarehouseOperatorCommand(
        OperationalCode.of(code),
        userId,
        profile("Anna", "Warehouse"),
        qualifications(Qualification.FORKLIFT),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6J warehouse operator");
  }

  private static RegisterDispatcherCommand dispatcherCommand(String code, UserId userId) {
    return new RegisterDispatcherCommand(
        OperationalCode.of(code),
        userId,
        profile("Laura", "Dispatcher"),
        scopes("NORTH_AREA"),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6J dispatcher");
  }

  private static RegisterManagerCommand managerCommand(String code, UserId userId) {
    return new RegisterManagerCommand(
        OperationalCode.of(code),
        userId,
        profile("Giulia", "Manager"),
        scopes("OPERATIONS"),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6J manager");
  }

  private static OperationalProfile profile(String firstName, String lastName) {
    return new OperationalProfile(
        firstName,
        lastName,
        firstName.toLowerCase() + "." + lastName.toLowerCase() + "@truckflow.local",
        "",
        "",
        "Operations",
        lastName,
        "Application 6J profile");
  }

  private static Set<OperationalQualification> qualifications(Qualification qualification) {
    return Set.of(OperationalQualification.of(qualification));
  }

  private static Set<OperationalScope> scopes(String code) {
    return Set.of(OperationalScope.of(code, "Scope " + code));
  }

  private static OperationalMetadata metadata() {
    return OperationalMetadata.createdNow("application-6j");
  }

  private static final class OperationalUseCaseContext {

    private final InMemoryDriverRepository driverRepository = new InMemoryDriverRepository();
    private final InMemoryMechanicRepository mechanicRepository = new InMemoryMechanicRepository();
    private final InMemoryWarehouseOperatorRepository warehouseOperatorRepository =
        new InMemoryWarehouseOperatorRepository();
    private final InMemoryDispatcherRepository dispatcherRepository =
        new InMemoryDispatcherRepository();
    private final InMemoryManagerRepository managerRepository = new InMemoryManagerRepository();

    private final RegisterDriverService registerDriver =
        new RegisterDriverService(driverRepository);
    private final FindDriverService findDriver = new FindDriverService(driverRepository);
    private final ActivateDriverService activateDriver =
        new ActivateDriverService(driverRepository);
    private final SuspendDriverService suspendDriver = new SuspendDriverService(driverRepository);
    private final MarkNotEligibleDriverService markNotEligibleDriver =
        new MarkNotEligibleDriverService(driverRepository);
    private final RegisterMechanicService registerMechanic =
        new RegisterMechanicService(mechanicRepository);
    private final RegisterWarehouseOperatorService registerWarehouseOperator =
        new RegisterWarehouseOperatorService(warehouseOperatorRepository);
    private final RegisterDispatcherService registerDispatcher =
        new RegisterDispatcherService(dispatcherRepository);
    private final FindDispatcherService findDispatcher =
        new FindDispatcherService(dispatcherRepository);
    private final ActivateDispatcherService activateDispatcher =
        new ActivateDispatcherService(dispatcherRepository);
    private final SuspendDispatcherService suspendDispatcher =
        new SuspendDispatcherService(dispatcherRepository);
    private final RegisterManagerService registerManager =
        new RegisterManagerService(managerRepository);
  }
}
