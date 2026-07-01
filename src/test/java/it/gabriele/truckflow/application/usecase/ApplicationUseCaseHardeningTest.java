package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.gabriele.truckflow.application.command.cargo.FindCargoUnitCommand;
import it.gabriele.truckflow.application.command.cargo.RegisterCargoUnitCommand;
import it.gabriele.truckflow.application.command.locations.FindLocationCommand;
import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.command.shipments.AddShipmentItemCommand;
import it.gabriele.truckflow.application.command.shipments.CancelShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.CreateShipmentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.cargo.FindCargoUnitService;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentItemService;
import it.gabriele.truckflow.application.usecase.shipments.CancelShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.CreateShipmentService;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.CargoCompatibilityRequirement;
import it.gabriele.truckflow.domain.cargo.CargoDimensions;
import it.gabriele.truckflow.domain.cargo.CargoHazard;
import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.cargo.CargoPackaging;
import it.gabriele.truckflow.domain.cargo.CargoProperties;
import it.gabriele.truckflow.domain.cargo.CargoRegulatory;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoTemperature;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoWeights;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentId;
import it.gabriele.truckflow.domain.shipments.core.ShipmentPriority;
import it.gabriele.truckflow.domain.shipments.core.ShipmentServiceLevel;
import it.gabriele.truckflow.domain.shipments.core.ShipmentStatus;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentException;
import it.gabriele.truckflow.domain.shipments.items.ShipmentUnitOfMeasure;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentMetrics;
import it.gabriele.truckflow.domain.shipments.notes.ShipmentNotes;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentProperties;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentTemperature;
import it.gabriele.truckflow.domain.shipments.references.ShipmentReferences;
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentRequirementSet;
import it.gabriele.truckflow.infrastructure.memory.cargo.InMemoryCargoUnitRepository;
import it.gabriele.truckflow.infrastructure.memory.locations.InMemoryLocationRepository;
import it.gabriele.truckflow.infrastructure.memory.shipments.InMemoryShipmentRepository;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationUseCaseHardeningTest {

  @Test
  void useCaseServicesRejectNullCommandsBeforeAccessingRepositories() {
    var context = new UseCaseContext();

    assertThrows(UseCaseValidationException.class, () -> context.registerLocation.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findLocation.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.registerCargoUnit.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findCargoUnit.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.createShipment.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.addShipmentItem.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.cancelShipment.execute(null));
  }

  @Test
  void commandConstructorsRejectMissingRequiredApplicationInputs() {
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterLocationCommand(
                null, "Depot", LocationType.DEPOT, LocationStatus.ACTIVE, null, null, ""));

    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterCargoUnitCommand(
                CargoCode.of("CGO-VALID"),
                " ",
                "Invalid blank name",
                CargoType.GENERAL_GOODS,
                Set.of(CargoCategory.DRY),
                CargoDimensions.empty(),
                CargoWeights.empty(),
                CargoPackaging.loose(),
                CargoTemperature.uncontrolled(),
                CargoHazard.none(),
                CargoRegulatory.none(),
                CargoProperties.standard(),
                CargoCompatibilityRequirement.none(),
                CargoStatus.ACTIVE,
                ""));

    assertThrows(
        UseCaseValidationException.class,
        () ->
            new CreateShipmentCommand(
                null,
                "Shipment",
                "Missing code",
                ShipmentPriority.NORMAL,
                ShipmentServiceLevel.STANDARD,
                ShipmentProperties.standard(),
                ShipmentTemperature.uncontrolled(),
                ShipmentRequirementSet.none(),
                ShipmentMetrics.empty(),
                ShipmentReferences.empty(),
                ShipmentNotes.empty(),
                ""));
  }

  @Test
  void findUseCasesReturnApplicationNotFoundErrorsForMissingResources() {
    var context = new UseCaseContext();

    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findLocation.execute(new FindLocationCommand(LocationId.random())));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findCargoUnit.execute(new FindCargoUnitCommand(CargoId.random())));
  }

  @Test
  void registerCargoUnitRejectsDuplicateBusinessCode() {
    var context = new UseCaseContext();

    context.registerCargoUnit.execute(cargoCommand("CGO-DUP-001"));

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerCargoUnit.execute(cargoCommand("cgo-dup-001")));
  }

  @Test
  void cancelShipmentUseCaseCancelsAndPersistsExistingShipment() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(shipmentCommand("SHP-CANCEL-001"));

    var cancelled = context.cancelShipment.execute(new CancelShipmentCommand(shipment.id()));

    assertEquals(ShipmentStatus.CANCELLED, cancelled.status());
    assertFalse(cancelled.confirmed());
    assertEquals(
        ShipmentStatus.CANCELLED,
        context.shipmentRepository.findById(shipment.id()).orElseThrow().status());
  }

  @Test
  void cancelShipmentUseCaseFailsWhenShipmentDoesNotExist() {
    var context = new UseCaseContext();

    assertThrows(
        ResourceNotFoundException.class,
        () -> context.cancelShipment.execute(new CancelShipmentCommand(ShipmentId.random())));
  }

  @Test
  void failedShipmentItemMutationDoesNotPersistPartialState() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(shipmentCommand("SHP-ITEM-FAIL-001"));
    var cargo = context.registerCargoUnit.execute(cargoCommand("CGO-ITEM-FAIL-001"));

    assertThrows(
        InvalidShipmentException.class,
        () ->
            context.addShipmentItem.execute(
                new AddShipmentItemCommand(
                    shipment.id(),
                    cargo.id(),
                    BigDecimal.ZERO,
                    ShipmentUnitOfMeasure.PALLET,
                    "Invalid zero quantity")));

    assertEquals(0, context.shipmentRepository.findById(shipment.id()).orElseThrow().itemCount());
  }

  private static RegisterLocationCommand locationCommand(String code) {
    return new RegisterLocationCommand(
        LocationCode.of(code),
        "Location " + code,
        LocationType.WAREHOUSE,
        LocationStatus.ACTIVE,
        LocationAddress.empty(),
        null,
        "Application hardening test location");
  }

  private static RegisterCargoUnitCommand cargoCommand(String code) {
    return new RegisterCargoUnitCommand(
        CargoCode.of(code),
        "Cargo " + code,
        "Application hardening test cargo",
        CargoType.GENERAL_GOODS,
        Set.of(CargoCategory.DRY, CargoCategory.PALLETIZED),
        CargoDimensions.empty(),
        CargoWeights.empty(),
        CargoPackaging.loose(),
        CargoTemperature.uncontrolled(),
        CargoHazard.none(),
        CargoRegulatory.none(),
        CargoProperties.standard(),
        CargoCompatibilityRequirement.none(),
        CargoStatus.ACTIVE,
        "");
  }

  private static CreateShipmentCommand shipmentCommand(String code) {
    return new CreateShipmentCommand(
        ShipmentCode.of(code),
        "Shipment " + code,
        "Application hardening test shipment",
        ShipmentPriority.NORMAL,
        ShipmentServiceLevel.STANDARD,
        ShipmentProperties.standard(),
        ShipmentTemperature.uncontrolled(),
        ShipmentRequirementSet.none(),
        ShipmentMetrics.empty(),
        ShipmentReferences.empty(),
        ShipmentNotes.empty(),
        "");
  }

  private static final class UseCaseContext {

    private final InMemoryLocationRepository locationRepository = new InMemoryLocationRepository();
    private final InMemoryCargoUnitRepository cargoUnitRepository =
        new InMemoryCargoUnitRepository();
    private final InMemoryShipmentRepository shipmentRepository = new InMemoryShipmentRepository();

    private final RegisterLocationService registerLocation =
        new RegisterLocationService(locationRepository);
    private final FindLocationService findLocation = new FindLocationService(locationRepository);
    private final RegisterCargoUnitService registerCargoUnit =
        new RegisterCargoUnitService(cargoUnitRepository);
    private final FindCargoUnitService findCargoUnit =
        new FindCargoUnitService(cargoUnitRepository);
    private final CreateShipmentService createShipment =
        new CreateShipmentService(shipmentRepository);
    private final AddShipmentItemService addShipmentItem =
        new AddShipmentItemService(shipmentRepository, cargoUnitRepository);
    private final CancelShipmentService cancelShipment =
        new CancelShipmentService(shipmentRepository);
  }
}
