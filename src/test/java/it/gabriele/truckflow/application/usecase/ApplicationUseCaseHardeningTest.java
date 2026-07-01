package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.gabriele.truckflow.application.command.cargo.FindCargoUnitCommand;
import it.gabriele.truckflow.application.command.cargo.RegisterCargoUnitCommand;
import it.gabriele.truckflow.application.command.locations.FindLocationCommand;
import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.command.shipments.AddShipmentItemCommand;
import it.gabriele.truckflow.application.command.shipments.AddShipmentLegCommand;
import it.gabriele.truckflow.application.command.shipments.CancelShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.ConfirmShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.CreateShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.FindShipmentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.cargo.FindCargoUnitService;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentItemService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentLegService;
import it.gabriele.truckflow.application.usecase.shipments.CancelShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.ConfirmShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.CreateShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.FindShipmentService;
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
import it.gabriele.truckflow.domain.shipments.legs.ShipmentLegType;
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
    assertThrows(UseCaseValidationException.class, () -> context.findShipment.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.addShipmentItem.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.addShipmentLeg.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.confirmShipment.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.cancelShipment.execute(null));
  }

  @Test
  void useCaseServicesRejectMissingRepositoryDependenciesAtConstructionTime() {
    var context = new UseCaseContext();

    assertThrows(UseCaseValidationException.class, () -> new RegisterLocationService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindLocationService(null));
    assertThrows(UseCaseValidationException.class, () -> new RegisterCargoUnitService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindCargoUnitService(null));
    assertThrows(UseCaseValidationException.class, () -> new CreateShipmentService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindShipmentService(null));
    assertThrows(
        UseCaseValidationException.class,
        () -> new AddShipmentItemService(null, context.cargoUnitRepository));
    assertThrows(
        UseCaseValidationException.class,
        () -> new AddShipmentItemService(context.shipmentRepository, null));
    assertThrows(
        UseCaseValidationException.class,
        () -> new AddShipmentLegService(null, context.locationRepository));
    assertThrows(
        UseCaseValidationException.class,
        () -> new AddShipmentLegService(context.shipmentRepository, null));
    assertThrows(UseCaseValidationException.class, () -> new ConfirmShipmentService(null));
    assertThrows(UseCaseValidationException.class, () -> new CancelShipmentService(null));
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

    assertThrows(
        UseCaseValidationException.class,
        () ->
            new AddShipmentItemCommand(
                null,
                CargoId.random(),
                BigDecimal.ONE,
                ShipmentUnitOfMeasure.PALLET,
                "Missing shipment"));
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new AddShipmentLegCommand(
                ShipmentId.random(),
                1,
                ShipmentLegType.PICKUP,
                null,
                LocationId.random(),
                BigDecimal.TEN,
                "Missing origin"));
    assertThrows(UseCaseValidationException.class, () -> new ConfirmShipmentCommand(null));
    assertThrows(UseCaseValidationException.class, () -> new CancelShipmentCommand(null));
    assertThrows(UseCaseValidationException.class, () -> new FindShipmentCommand(null));
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
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findShipment.execute(new FindShipmentCommand(ShipmentId.random())));
  }

  @Test
  void registerUseCasesRejectDuplicateBusinessCodes() {
    var context = new UseCaseContext();

    context.registerLocation.execute(locationCommand("LOC-DUP-001"));
    context.registerCargoUnit.execute(cargoCommand("CGO-DUP-001"));
    context.createShipment.execute(shipmentCommand("SHP-DUP-001"));

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerLocation.execute(locationCommand("loc-dup-001")));
    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerCargoUnit.execute(cargoCommand("cgo-dup-001")));
    assertThrows(
        DuplicateResourceException.class,
        () -> context.createShipment.execute(shipmentCommand("shp-dup-001")));
  }

  @Test
  void shipmentMutationUseCasesReturnNotFoundForMissingRequiredResources() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(shipmentCommand("SHP-MISSING-RESOURCES-001"));
    var origin = context.registerLocation.execute(locationCommand("LOC-MISSING-ORIGIN-001"));

    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.addShipmentItem.execute(
                new AddShipmentItemCommand(
                    ShipmentId.random(),
                    CargoId.random(),
                    BigDecimal.ONE,
                    ShipmentUnitOfMeasure.PALLET,
                    "Missing shipment")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.addShipmentItem.execute(
                new AddShipmentItemCommand(
                    shipment.id(),
                    CargoId.random(),
                    BigDecimal.ONE,
                    ShipmentUnitOfMeasure.PALLET,
                    "Missing cargo")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.addShipmentLeg.execute(
                new AddShipmentLegCommand(
                    ShipmentId.random(),
                    1,
                    ShipmentLegType.PICKUP,
                    origin.id(),
                    LocationId.random(),
                    BigDecimal.TEN,
                    "Missing shipment")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.addShipmentLeg.execute(
                new AddShipmentLegCommand(
                    shipment.id(),
                    1,
                    ShipmentLegType.PICKUP,
                    LocationId.random(),
                    origin.id(),
                    BigDecimal.TEN,
                    "Missing origin")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.addShipmentLeg.execute(
                new AddShipmentLegCommand(
                    shipment.id(),
                    1,
                    ShipmentLegType.PICKUP,
                    origin.id(),
                    LocationId.random(),
                    BigDecimal.TEN,
                    "Missing destination")));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.confirmShipment.execute(new ConfirmShipmentCommand(ShipmentId.random())));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.cancelShipment.execute(new CancelShipmentCommand(ShipmentId.random())));
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

    var persisted = context.shipmentRepository.findById(shipment.id()).orElseThrow();
    assertEquals(ShipmentStatus.DRAFT, persisted.status());
    assertEquals(0, persisted.itemCount());
  }

  @Test
  void failedShipmentLegMutationDoesNotPersistPartialState() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(shipmentCommand("SHP-LEG-FAIL-001"));
    var origin = context.registerLocation.execute(locationCommand("LOC-LEG-ORIGIN-001"));
    var destination = context.registerLocation.execute(locationCommand("LOC-LEG-DEST-001"));

    context.addShipmentLeg.execute(
        new AddShipmentLegCommand(
            shipment.id(),
            1,
            ShipmentLegType.PICKUP,
            origin.id(),
            destination.id(),
            BigDecimal.TEN,
            "First valid leg"));

    assertThrows(
        InvalidShipmentException.class,
        () ->
            context.addShipmentLeg.execute(
                new AddShipmentLegCommand(
                    shipment.id(),
                    1,
                    ShipmentLegType.DELIVERY,
                    origin.id(),
                    destination.id(),
                    BigDecimal.TEN,
                    "Duplicate sequence")));

    var persisted = context.shipmentRepository.findById(shipment.id()).orElseThrow();
    assertEquals(ShipmentStatus.DRAFT, persisted.status());
    assertEquals(1, persisted.legCount());
  }

  @Test
  void failedConfirmShipmentDoesNotPersistPartialStatusChange() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(shipmentCommand("SHP-CONFIRM-FAIL-001"));

    assertThrows(
        InvalidShipmentException.class,
        () -> context.confirmShipment.execute(new ConfirmShipmentCommand(shipment.id())));

    var persisted = context.shipmentRepository.findById(shipment.id()).orElseThrow();
    assertEquals(ShipmentStatus.DRAFT, persisted.status());
    assertEquals(0, persisted.itemCount());
    assertEquals(0, persisted.legCount());
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
    private final FindShipmentService findShipment = new FindShipmentService(shipmentRepository);
    private final AddShipmentItemService addShipmentItem =
        new AddShipmentItemService(shipmentRepository, cargoUnitRepository);
    private final AddShipmentLegService addShipmentLeg =
        new AddShipmentLegService(shipmentRepository, locationRepository);
    private final ConfirmShipmentService confirmShipment =
        new ConfirmShipmentService(shipmentRepository);
    private final CancelShipmentService cancelShipment =
        new CancelShipmentService(shipmentRepository);
  }
}
