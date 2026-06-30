package it.gabriele.truckflow.domain.operational;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import it.gabriele.truckflow.domain.operational.driver.Driver;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDispatcherException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDriverException;
import it.gabriele.truckflow.domain.operational.manager.Manager;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperationalDomainTest {

  @Test
  void createsActiveDriverWithOperationalQualification() {
    var driver =
        new Driver(
            null,
            OperationalCode.of("drv-001"),
            UserId.random(),
            profile("Mario", "Rossi", "Transport", "Driver"),
            Set.of(qualification(Qualification.DRIVING_LICENSE_C)),
            OperationalStatus.ACTIVE,
            OperationalMetadata.createdNow("system"),
            "Main heavy truck driver");

    assertTrue(driver.isActive());
    assertEquals("DRV-001", driver.code().value());
    assertTrue(driver.hasQualification(Qualification.DRIVING_LICENSE_C));
  }

  @Test
  void activeDriverRequiresAtLeastOneQualification() {
    assertThrows(
        InvalidDriverException.class,
        () ->
            new Driver(
                null,
                OperationalCode.of("DRV-002"),
                UserId.random(),
                profile("Luca", "Bianchi", "Transport", "Driver"),
                Set.of(),
                OperationalStatus.ACTIVE,
                OperationalMetadata.createdNow("system"),
                ""));
  }

  @Test
  void createsMechanicAndWarehouseOperatorWithQualifications() {
    var mechanic =
        new Mechanic(
            null,
            OperationalCode.of("MEC-001"),
            UserId.random(),
            profile("Anna", "Verdi", "Maintenance", "Mechanic"),
            Set.of(qualification(Qualification.FORKLIFT)),
            OperationalStatus.ACTIVE,
            OperationalMetadata.createdNow("system"),
            "");

    var warehouseOperator =
        new WarehouseOperator(
            null,
            OperationalCode.of("WH-001"),
            UserId.random(),
            profile("Sara", "Neri", "Warehouse", "Warehouse Operator"),
            Set.of(qualification(Qualification.MEWP)),
            OperationalStatus.ACTIVE,
            OperationalMetadata.createdNow("system"),
            "");

    assertTrue(mechanic.hasQualification(Qualification.FORKLIFT));
    assertTrue(warehouseOperator.hasQualification(Qualification.MEWP));
  }

  @Test
  void createsDispatcherAndManagerWithScopes() {
    var scope = new OperationalScope("north_it", "North Italy", "Domestic transport area", "Italy");

    var dispatcher =
        new Dispatcher(
            null,
            OperationalCode.of("DSP-001"),
            UserId.random(),
            profile("Paolo", "Gialli", "Operations", "Dispatcher"),
            Set.of(scope),
            OperationalStatus.ACTIVE,
            OperationalMetadata.createdNow("system"),
            "");

    var manager =
        new Manager(
            null,
            OperationalCode.of("MNG-001"),
            UserId.random(),
            profile("Giulia", "Blu", "Management", "Operations Manager"),
            Set.of(scope),
            OperationalStatus.ACTIVE,
            OperationalMetadata.createdNow("system"),
            "");

    assertTrue(dispatcher.hasScope("NORTH_IT"));
    assertTrue(manager.hasScope("north_it"));
  }

  @Test
  void activeDispatcherRequiresAtLeastOneScope() {
    assertThrows(
        InvalidDispatcherException.class,
        () ->
            new Dispatcher(
                null,
                OperationalCode.of("DSP-002"),
                UserId.random(),
                profile("Marco", "Viola", "Operations", "Dispatcher"),
                Set.of(),
                OperationalStatus.ACTIVE,
                OperationalMetadata.createdNow("system"),
                ""));
  }

  @Test
  void operationalCodeIsMandatory() {
    assertThrows(DomainValidationException.class, () -> OperationalCode.of(""));
    assertThrows(DomainValidationException.class, () -> OperationalCode.of("   "));
    assertThrows(DomainValidationException.class, () -> OperationalCode.of(null));
  }

  @Test
  void driverRequiresOperationalCode() {
    assertThrows(
        InvalidDriverException.class,
        () ->
            new Driver(
                null,
                null,
                UserId.random(),
                profile("Code", "Missing", "Transport", "Driver"),
                Set.of(qualification(Qualification.DRIVING_LICENSE_C)),
                OperationalStatus.ACTIVE,
                OperationalMetadata.createdNow("system"),
                ""));
  }

  @Test
  void failedDriverActivationDoesNotMutateStatus() {
    var driver =
        new Driver(
            null,
            OperationalCode.of("DRV-ATOMIC"),
            UserId.random(),
            profile("Luca", "Bianchi", "Transport", "Driver"),
            Set.of(),
            OperationalStatus.SUSPENDED,
            OperationalMetadata.createdNow("system"),
            "");

    assertThrows(InvalidDriverException.class, () -> driver.activate("admin"));

    assertEquals(OperationalStatus.SUSPENDED, driver.status());
  }

  private static OperationalProfile profile(
      String firstName, String lastName, String department, String position) {
    return new OperationalProfile(
        firstName,
        lastName,
        firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com",
        "",
        "+390000000000",
        department,
        position,
        "");
  }

  private static OperationalQualification qualification(Qualification qualification) {
    return new OperationalQualification(qualification, "", "IT", "", "");
  }
}
