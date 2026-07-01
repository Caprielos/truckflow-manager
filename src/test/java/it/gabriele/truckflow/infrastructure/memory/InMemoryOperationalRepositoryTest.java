package it.gabriele.truckflow.infrastructure.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;
import it.gabriele.truckflow.domain.operational.driver.Driver;
import it.gabriele.truckflow.domain.operational.driver.DriverId;
import it.gabriele.truckflow.domain.operational.manager.Manager;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;
import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.users.UserId;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDispatcherRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDriverRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryManagerRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryMechanicRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryWarehouseOperatorRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InMemoryOperationalRepositoryTest {

  @Test
  void inMemoryDriverRepositoryImplementsApplicationPortAndFindsByIdCodeAndUserId() {
    DriverRepository repository = new InMemoryDriverRepository();
    var driver = driver("DRV-REP-001", UserId.random());

    Driver saved = repository.save(driver);

    assertEquals(driver, saved);
    assertTrue(repository.existsById(driver.id()));
    assertTrue(repository.existsByCode(OperationalCode.of("drv-rep-001")));
    assertTrue(repository.existsByUserId(driver.userId()));
    assertEquals(driver, repository.findById(driver.id()).orElseThrow());
    assertEquals(driver, repository.findByCode(driver.code()).orElseThrow());
    assertEquals(driver, repository.findByUserId(driver.userId()).orElseThrow());
    assertFalse(repository.findById(DriverId.random()).isPresent());
  }

  @Test
  void inMemoryOperationalRepositoriesRejectDuplicateCodesAndUserIds() {
    DriverRepository repository = new InMemoryDriverRepository();
    var userId = UserId.random();
    repository.save(driver("DRV-DUP-REP-001", userId));

    assertThrows(
        DuplicateResourceException.class,
        () -> repository.save(driver("drv-dup-rep-001", UserId.random())));
    assertThrows(
        DuplicateResourceException.class, () -> repository.save(driver("DRV-DUP-REP-002", userId)));
  }

  @Test
  void allInMemoryOperationalRepositoriesRejectNullInputs() {
    DriverRepository driverRepository = new InMemoryDriverRepository();
    MechanicRepository mechanicRepository = new InMemoryMechanicRepository();
    WarehouseOperatorRepository warehouseOperatorRepository =
        new InMemoryWarehouseOperatorRepository();
    DispatcherRepository dispatcherRepository = new InMemoryDispatcherRepository();
    ManagerRepository managerRepository = new InMemoryManagerRepository();

    assertNullInputContract(driverRepository);
    assertNullInputContract(mechanicRepository);
    assertNullInputContract(warehouseOperatorRepository);
    assertNullInputContract(dispatcherRepository);
    assertNullInputContract(managerRepository);
  }

  @Test
  void inMemoryOperationalRepositoriesHandleAllCurrentOperationalRoleTypes() {
    MechanicRepository mechanicRepository = new InMemoryMechanicRepository();
    WarehouseOperatorRepository warehouseOperatorRepository =
        new InMemoryWarehouseOperatorRepository();
    DispatcherRepository dispatcherRepository = new InMemoryDispatcherRepository();
    ManagerRepository managerRepository = new InMemoryManagerRepository();

    var mechanic = mechanic("MEC-REP-001", UserId.random());
    var warehouseOperator = warehouseOperator("WHO-REP-001", UserId.random());
    var dispatcher = dispatcher("DSP-REP-001", UserId.random());
    var manager = manager("MGR-REP-001", UserId.random());

    assertEquals(mechanic, mechanicRepository.save(mechanic));
    assertEquals(warehouseOperator, warehouseOperatorRepository.save(warehouseOperator));
    assertEquals(dispatcher, dispatcherRepository.save(dispatcher));
    assertEquals(manager, managerRepository.save(manager));

    assertEquals(mechanic, mechanicRepository.findById(mechanic.id()).orElseThrow());
    assertEquals(
        warehouseOperator,
        warehouseOperatorRepository.findById(warehouseOperator.id()).orElseThrow());
    assertEquals(dispatcher, dispatcherRepository.findById(dispatcher.id()).orElseThrow());
    assertEquals(manager, managerRepository.findById(manager.id()).orElseThrow());
    assertFalse(mechanicRepository.findById(MechanicId.random()).isPresent());
    assertFalse(warehouseOperatorRepository.findById(WarehouseOperatorId.random()).isPresent());
    assertFalse(dispatcherRepository.findById(DispatcherId.random()).isPresent());
    assertFalse(managerRepository.findById(ManagerId.random()).isPresent());
  }

  private static void assertNullInputContract(DriverRepository repository) {
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByUserId(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByUserId(null));
  }

  private static void assertNullInputContract(MechanicRepository repository) {
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByUserId(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByUserId(null));
  }

  private static void assertNullInputContract(WarehouseOperatorRepository repository) {
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByUserId(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByUserId(null));
  }

  private static void assertNullInputContract(DispatcherRepository repository) {
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByUserId(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByUserId(null));
  }

  private static void assertNullInputContract(ManagerRepository repository) {
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByUserId(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByUserId(null));
  }

  private static Driver driver(String code, UserId userId) {
    return new Driver(
        null,
        OperationalCode.of(code),
        userId,
        profile("Driver", code),
        qualifications(Qualification.DRIVING_LICENSE_C),
        OperationalStatus.ACTIVE,
        metadata(),
        "In-memory operational repository driver");
  }

  private static Mechanic mechanic(String code, UserId userId) {
    return new Mechanic(
        null,
        OperationalCode.of(code),
        userId,
        profile("Mechanic", code),
        qualifications(Qualification.TRUCK_MOUNTED_CRANE),
        OperationalStatus.ACTIVE,
        metadata(),
        "In-memory operational repository mechanic");
  }

  private static WarehouseOperator warehouseOperator(String code, UserId userId) {
    return new WarehouseOperator(
        null,
        OperationalCode.of(code),
        userId,
        profile("Warehouse", code),
        qualifications(Qualification.FORKLIFT),
        OperationalStatus.ACTIVE,
        metadata(),
        "In-memory operational repository warehouse operator");
  }

  private static Dispatcher dispatcher(String code, UserId userId) {
    return new Dispatcher(
        null,
        OperationalCode.of(code),
        userId,
        profile("Dispatcher", code),
        scopes(code),
        OperationalStatus.ACTIVE,
        metadata(),
        "In-memory operational repository dispatcher");
  }

  private static Manager manager(String code, UserId userId) {
    return new Manager(
        null,
        OperationalCode.of(code),
        userId,
        profile("Manager", code),
        scopes(code),
        OperationalStatus.ACTIVE,
        metadata(),
        "In-memory operational repository manager");
  }

  private static OperationalProfile profile(String firstName, String code) {
    return new OperationalProfile(
        firstName,
        "Repository",
        firstName.toLowerCase() + "." + code.toLowerCase().replace('_', '-') + "@truckflow.local",
        "",
        "",
        "Operations",
        firstName,
        "Repository test profile");
  }

  private static Set<OperationalQualification> qualifications(Qualification qualification) {
    return Set.of(OperationalQualification.of(qualification));
  }

  private static Set<OperationalScope> scopes(String code) {
    return Set.of(OperationalScope.of("SCOPE-" + code, "Scope " + code));
  }

  private static OperationalMetadata metadata() {
    return OperationalMetadata.createdNow("repository-test");
  }
}
