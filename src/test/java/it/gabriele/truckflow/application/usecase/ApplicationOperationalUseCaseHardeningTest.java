package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.gabriele.truckflow.application.command.operational.ActivateDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.ActivateDriverCommand;
import it.gabriele.truckflow.application.command.operational.ActivateManagerCommand;
import it.gabriele.truckflow.application.command.operational.ActivateMechanicCommand;
import it.gabriele.truckflow.application.command.operational.ActivateWarehouseOperatorCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDriverCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleManagerCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleMechanicCommand;
import it.gabriele.truckflow.application.command.operational.MarkNotEligibleWarehouseOperatorCommand;
import it.gabriele.truckflow.application.command.operational.RegisterDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.RegisterDriverCommand;
import it.gabriele.truckflow.application.command.operational.RegisterManagerCommand;
import it.gabriele.truckflow.application.command.operational.RegisterMechanicCommand;
import it.gabriele.truckflow.application.command.operational.RegisterWarehouseOperatorCommand;
import it.gabriele.truckflow.application.command.operational.SuspendDispatcherCommand;
import it.gabriele.truckflow.application.command.operational.SuspendDriverCommand;
import it.gabriele.truckflow.application.command.operational.SuspendManagerCommand;
import it.gabriele.truckflow.application.command.operational.SuspendMechanicCommand;
import it.gabriele.truckflow.application.command.operational.SuspendWarehouseOperatorCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.operational.ActivateDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.ActivateDriverService;
import it.gabriele.truckflow.application.usecase.operational.ActivateManagerService;
import it.gabriele.truckflow.application.usecase.operational.ActivateMechanicService;
import it.gabriele.truckflow.application.usecase.operational.ActivateWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.FindDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.FindDriverService;
import it.gabriele.truckflow.application.usecase.operational.FindManagerService;
import it.gabriele.truckflow.application.usecase.operational.FindMechanicService;
import it.gabriele.truckflow.application.usecase.operational.FindWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleDriverService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleManagerService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleMechanicService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDriverService;
import it.gabriele.truckflow.application.usecase.operational.RegisterManagerService;
import it.gabriele.truckflow.application.usecase.operational.RegisterMechanicService;
import it.gabriele.truckflow.application.usecase.operational.RegisterWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDriverService;
import it.gabriele.truckflow.application.usecase.operational.SuspendManagerService;
import it.gabriele.truckflow.application.usecase.operational.SuspendMechanicService;
import it.gabriele.truckflow.application.usecase.operational.SuspendWarehouseOperatorService;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDispatcherException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDriverException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidManagerException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidMechanicException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidWarehouseOperatorException;
import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.users.UserId;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDispatcherRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDriverRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryManagerRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryMechanicRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryWarehouseOperatorRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationOperationalUseCaseHardeningTest {

  @Test
  void operationalStatusUseCasesCoverEveryCurrentRole() {
    var context = new OperationalHardeningContext();

    var driver = context.registerDriver.execute(driverCommand("DRV-HARD-001", UserId.random()));
    var mechanic =
        context.registerMechanic.execute(mechanicCommand("MEC-HARD-001", UserId.random()));
    var warehouseOperator =
        context.registerWarehouseOperator.execute(
            warehouseOperatorCommand("WHO-HARD-001", UserId.random()));
    var dispatcher =
        context.registerDispatcher.execute(dispatcherCommand("DSP-HARD-001", UserId.random()));
    var manager = context.registerManager.execute(managerCommand("MGR-HARD-001", UserId.random()));

    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .suspendDriver
            .execute(new SuspendDriverCommand(driver.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .suspendMechanic
            .execute(new SuspendMechanicCommand(mechanic.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .suspendWarehouseOperator
            .execute(new SuspendWarehouseOperatorCommand(warehouseOperator.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .suspendDispatcher
            .execute(new SuspendDispatcherCommand(dispatcher.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .suspendManager
            .execute(new SuspendManagerCommand(manager.id(), "application-6k"))
            .status());

    assertEquals(
        OperationalStatus.NOT_ELIGIBLE,
        context
            .markNotEligibleDriver
            .execute(new MarkNotEligibleDriverCommand(driver.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.NOT_ELIGIBLE,
        context
            .markNotEligibleMechanic
            .execute(new MarkNotEligibleMechanicCommand(mechanic.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.NOT_ELIGIBLE,
        context
            .markNotEligibleWarehouseOperator
            .execute(
                new MarkNotEligibleWarehouseOperatorCommand(
                    warehouseOperator.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.NOT_ELIGIBLE,
        context
            .markNotEligibleDispatcher
            .execute(new MarkNotEligibleDispatcherCommand(dispatcher.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.NOT_ELIGIBLE,
        context
            .markNotEligibleManager
            .execute(new MarkNotEligibleManagerCommand(manager.id(), "application-6k"))
            .status());

    assertEquals(
        OperationalStatus.ACTIVE,
        context
            .activateDriver
            .execute(new ActivateDriverCommand(driver.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.ACTIVE,
        context
            .activateMechanic
            .execute(new ActivateMechanicCommand(mechanic.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.ACTIVE,
        context
            .activateWarehouseOperator
            .execute(new ActivateWarehouseOperatorCommand(warehouseOperator.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.ACTIVE,
        context
            .activateDispatcher
            .execute(new ActivateDispatcherCommand(dispatcher.id(), "application-6k"))
            .status());
    assertEquals(
        OperationalStatus.ACTIVE,
        context
            .activateManager
            .execute(new ActivateManagerCommand(manager.id(), "application-6k"))
            .status());
  }

  @Test
  void failedOperationalActivationDoesNotMutateStoredInMemoryAggregates() {
    var context = new OperationalHardeningContext();

    var driver =
        context.registerDriver.execute(
            driverCommandWithoutQualifications("DRV-HARD-FAIL", UserId.random()));
    var mechanic =
        context.registerMechanic.execute(
            mechanicCommandWithoutQualifications("MEC-HARD-FAIL", UserId.random()));
    var warehouseOperator =
        context.registerWarehouseOperator.execute(
            warehouseOperatorCommandWithoutQualifications("WHO-HARD-FAIL", UserId.random()));
    var dispatcher =
        context.registerDispatcher.execute(
            dispatcherCommandWithoutScopes("DSP-HARD-FAIL", UserId.random()));
    var manager =
        context.registerManager.execute(
            managerCommandWithoutScopes("MGR-HARD-FAIL", UserId.random()));

    assertThrows(
        InvalidDriverException.class,
        () ->
            context.activateDriver.execute(
                new ActivateDriverCommand(driver.id(), "application-6k")));
    assertEquals(
        OperationalStatus.SUSPENDED,
        context.driverRepository.findById(driver.id()).orElseThrow().status());

    assertThrows(
        InvalidMechanicException.class,
        () ->
            context.activateMechanic.execute(
                new ActivateMechanicCommand(mechanic.id(), "application-6k")));
    assertEquals(
        OperationalStatus.SUSPENDED,
        context.mechanicRepository.findById(mechanic.id()).orElseThrow().status());

    assertThrows(
        InvalidWarehouseOperatorException.class,
        () ->
            context.activateWarehouseOperator.execute(
                new ActivateWarehouseOperatorCommand(warehouseOperator.id(), "application-6k")));
    assertEquals(
        OperationalStatus.SUSPENDED,
        context
            .warehouseOperatorRepository
            .findById(warehouseOperator.id())
            .orElseThrow()
            .status());

    assertThrows(
        InvalidDispatcherException.class,
        () ->
            context.activateDispatcher.execute(
                new ActivateDispatcherCommand(dispatcher.id(), "application-6k")));
    assertEquals(
        OperationalStatus.SUSPENDED,
        context.dispatcherRepository.findById(dispatcher.id()).orElseThrow().status());

    assertThrows(
        InvalidManagerException.class,
        () ->
            context.activateManager.execute(
                new ActivateManagerCommand(manager.id(), "application-6k")));
    assertEquals(
        OperationalStatus.SUSPENDED,
        context.managerRepository.findById(manager.id()).orElseThrow().status());
  }

  @Test
  void operationalUseCasesRejectNullCommandsForEveryCurrentRoleService() {
    var context = new OperationalHardeningContext();

    assertThrows(UseCaseValidationException.class, () -> context.registerDriver.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findDriver.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateDriver.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendDriver.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.markNotEligibleDriver.execute(null));

    assertThrows(UseCaseValidationException.class, () -> context.registerMechanic.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findMechanic.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateMechanic.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendMechanic.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.markNotEligibleMechanic.execute(null));

    assertThrows(
        UseCaseValidationException.class, () -> context.registerWarehouseOperator.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.findWarehouseOperator.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.activateWarehouseOperator.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.suspendWarehouseOperator.execute(null));
    assertThrows(
        UseCaseValidationException.class,
        () -> context.markNotEligibleWarehouseOperator.execute(null));

    assertThrows(UseCaseValidationException.class, () -> context.registerDispatcher.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findDispatcher.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateDispatcher.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendDispatcher.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.markNotEligibleDispatcher.execute(null));

    assertThrows(UseCaseValidationException.class, () -> context.registerManager.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findManager.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateManager.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendManager.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.markNotEligibleManager.execute(null));
  }

  @Test
  void operationalServicesRejectNullRepositoryDependenciesForEveryCurrentRoleService() {
    assertThrows(UseCaseValidationException.class, () -> new RegisterDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendDriverService(null));
    assertThrows(UseCaseValidationException.class, () -> new MarkNotEligibleDriverService(null));

    assertThrows(UseCaseValidationException.class, () -> new RegisterMechanicService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindMechanicService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateMechanicService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendMechanicService(null));
    assertThrows(UseCaseValidationException.class, () -> new MarkNotEligibleMechanicService(null));

    assertThrows(
        UseCaseValidationException.class, () -> new RegisterWarehouseOperatorService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindWarehouseOperatorService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new ActivateWarehouseOperatorService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendWarehouseOperatorService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new MarkNotEligibleWarehouseOperatorService(null));

    assertThrows(UseCaseValidationException.class, () -> new RegisterDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDispatcherService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendDispatcherService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new MarkNotEligibleDispatcherService(null));

    assertThrows(UseCaseValidationException.class, () -> new RegisterManagerService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindManagerService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateManagerService(null));
    assertThrows(UseCaseValidationException.class, () -> new SuspendManagerService(null));
    assertThrows(UseCaseValidationException.class, () -> new MarkNotEligibleManagerService(null));
  }

  private static RegisterDriverCommand driverCommand(String code, UserId userId) {
    return new RegisterDriverCommand(
        OperationalCode.of(code),
        userId,
        profile("Driver", code),
        qualifications(Qualification.DRIVING_LICENSE_C),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6K driver");
  }

  private static RegisterDriverCommand driverCommandWithoutQualifications(
      String code, UserId userId) {
    return new RegisterDriverCommand(
        OperationalCode.of(code),
        userId,
        profile("Driver", code),
        null,
        OperationalStatus.SUSPENDED,
        metadata(),
        "Application 6K driver without qualifications");
  }

  private static RegisterMechanicCommand mechanicCommand(String code, UserId userId) {
    return new RegisterMechanicCommand(
        OperationalCode.of(code),
        userId,
        profile("Mechanic", code),
        qualifications(Qualification.TRUCK_MOUNTED_CRANE),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6K mechanic");
  }

  private static RegisterMechanicCommand mechanicCommandWithoutQualifications(
      String code, UserId userId) {
    return new RegisterMechanicCommand(
        OperationalCode.of(code),
        userId,
        profile("Mechanic", code),
        null,
        OperationalStatus.SUSPENDED,
        metadata(),
        "Application 6K mechanic without qualifications");
  }

  private static RegisterWarehouseOperatorCommand warehouseOperatorCommand(
      String code, UserId userId) {
    return new RegisterWarehouseOperatorCommand(
        OperationalCode.of(code),
        userId,
        profile("Warehouse", code),
        qualifications(Qualification.FORKLIFT),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6K warehouse operator");
  }

  private static RegisterWarehouseOperatorCommand warehouseOperatorCommandWithoutQualifications(
      String code, UserId userId) {
    return new RegisterWarehouseOperatorCommand(
        OperationalCode.of(code),
        userId,
        profile("Warehouse", code),
        null,
        OperationalStatus.SUSPENDED,
        metadata(),
        "Application 6K warehouse operator without qualifications");
  }

  private static RegisterDispatcherCommand dispatcherCommand(String code, UserId userId) {
    return new RegisterDispatcherCommand(
        OperationalCode.of(code),
        userId,
        profile("Dispatcher", code),
        scopes(code),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6K dispatcher");
  }

  private static RegisterDispatcherCommand dispatcherCommandWithoutScopes(
      String code, UserId userId) {
    return new RegisterDispatcherCommand(
        OperationalCode.of(code),
        userId,
        profile("Dispatcher", code),
        null,
        OperationalStatus.SUSPENDED,
        metadata(),
        "Application 6K dispatcher without scopes");
  }

  private static RegisterManagerCommand managerCommand(String code, UserId userId) {
    return new RegisterManagerCommand(
        OperationalCode.of(code),
        userId,
        profile("Manager", code),
        scopes(code),
        OperationalStatus.ACTIVE,
        metadata(),
        "Application 6K manager");
  }

  private static RegisterManagerCommand managerCommandWithoutScopes(String code, UserId userId) {
    return new RegisterManagerCommand(
        OperationalCode.of(code),
        userId,
        profile("Manager", code),
        null,
        OperationalStatus.SUSPENDED,
        metadata(),
        "Application 6K manager without scopes");
  }

  private static OperationalProfile profile(String firstName, String code) {
    return new OperationalProfile(
        firstName,
        "Hardening",
        firstName.toLowerCase() + "." + code.toLowerCase() + "@truckflow.local",
        "",
        "",
        "Operations",
        firstName,
        "Application 6K profile");
  }

  private static Set<OperationalQualification> qualifications(Qualification qualification) {
    return Set.of(OperationalQualification.of(qualification));
  }

  private static Set<OperationalScope> scopes(String code) {
    return Set.of(OperationalScope.of("SCOPE-" + code, "Scope " + code));
  }

  private static OperationalMetadata metadata() {
    return OperationalMetadata.createdNow("application-6k");
  }

  private static final class OperationalHardeningContext {

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
    private final FindMechanicService findMechanic = new FindMechanicService(mechanicRepository);
    private final ActivateMechanicService activateMechanic =
        new ActivateMechanicService(mechanicRepository);
    private final SuspendMechanicService suspendMechanic =
        new SuspendMechanicService(mechanicRepository);
    private final MarkNotEligibleMechanicService markNotEligibleMechanic =
        new MarkNotEligibleMechanicService(mechanicRepository);

    private final RegisterWarehouseOperatorService registerWarehouseOperator =
        new RegisterWarehouseOperatorService(warehouseOperatorRepository);
    private final FindWarehouseOperatorService findWarehouseOperator =
        new FindWarehouseOperatorService(warehouseOperatorRepository);
    private final ActivateWarehouseOperatorService activateWarehouseOperator =
        new ActivateWarehouseOperatorService(warehouseOperatorRepository);
    private final SuspendWarehouseOperatorService suspendWarehouseOperator =
        new SuspendWarehouseOperatorService(warehouseOperatorRepository);
    private final MarkNotEligibleWarehouseOperatorService markNotEligibleWarehouseOperator =
        new MarkNotEligibleWarehouseOperatorService(warehouseOperatorRepository);

    private final RegisterDispatcherService registerDispatcher =
        new RegisterDispatcherService(dispatcherRepository);
    private final FindDispatcherService findDispatcher =
        new FindDispatcherService(dispatcherRepository);
    private final ActivateDispatcherService activateDispatcher =
        new ActivateDispatcherService(dispatcherRepository);
    private final SuspendDispatcherService suspendDispatcher =
        new SuspendDispatcherService(dispatcherRepository);
    private final MarkNotEligibleDispatcherService markNotEligibleDispatcher =
        new MarkNotEligibleDispatcherService(dispatcherRepository);

    private final RegisterManagerService registerManager =
        new RegisterManagerService(managerRepository);
    private final FindManagerService findManager = new FindManagerService(managerRepository);
    private final ActivateManagerService activateManager =
        new ActivateManagerService(managerRepository);
    private final SuspendManagerService suspendManager =
        new SuspendManagerService(managerRepository);
    private final MarkNotEligibleManagerService markNotEligibleManager =
        new MarkNotEligibleManagerService(managerRepository);
  }
}
