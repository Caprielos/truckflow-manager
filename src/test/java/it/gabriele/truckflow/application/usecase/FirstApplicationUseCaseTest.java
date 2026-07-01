package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.cargo.RegisterCargoUnitCommand;
import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.command.shipments.AddShipmentItemCommand;
import it.gabriele.truckflow.application.command.shipments.AddShipmentLegCommand;
import it.gabriele.truckflow.application.command.shipments.ConfirmShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.CreateShipmentCommand;
import it.gabriele.truckflow.application.command.shipments.FindShipmentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentItemService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentLegService;
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
import it.gabriele.truckflow.domain.cargo.CargoPackagingType;
import it.gabriele.truckflow.domain.cargo.CargoProperties;
import it.gabriele.truckflow.domain.cargo.CargoRegulatory;
import it.gabriele.truckflow.domain.cargo.CargoStatus;
import it.gabriele.truckflow.domain.cargo.CargoTemperature;
import it.gabriele.truckflow.domain.cargo.CargoTransportRequirement;
import it.gabriele.truckflow.domain.cargo.CargoType;
import it.gabriele.truckflow.domain.cargo.CargoWeights;
import it.gabriele.truckflow.domain.locations.GeoCoordinates;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
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
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentTransportRequirement;
import it.gabriele.truckflow.infrastructure.memory.cargo.InMemoryCargoUnitRepository;
import it.gabriele.truckflow.infrastructure.memory.locations.InMemoryLocationRepository;
import it.gabriele.truckflow.infrastructure.memory.shipments.InMemoryShipmentRepository;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FirstApplicationUseCaseTest {

  @Test
  void applicationLayerOrchestratesLocationCargoAndShipmentFlow() {
    var context = new UseCaseContext();

    var origin = context.registerLocation.execute(milanDepotCommand());
    var destination = context.registerLocation.execute(romeDepotCommand());
    var cargo = context.registerCargoUnit.execute(foodCargoCommand());

    var draftShipment = context.createShipment.execute(freshFoodShipmentCommand());
    assertEquals(ShipmentStatus.DRAFT, draftShipment.status());
    assertEquals(0, draftShipment.itemCount());
    assertEquals(0, draftShipment.legCount());

    var shipmentWithItem =
        context.addShipmentItem.execute(
            new AddShipmentItemCommand(
                draftShipment.id(),
                cargo.id(),
                new BigDecimal("12"),
                ShipmentUnitOfMeasure.PALLET,
                "Fresh food pallets"));
    assertEquals(1, shipmentWithItem.itemCount());

    var shipmentWithLeg =
        context.addShipmentLeg.execute(
            new AddShipmentLegCommand(
                draftShipment.id(),
                1,
                ShipmentLegType.PICKUP,
                origin.id(),
                destination.id(),
                new BigDecimal("575"),
                "Milano to Roma direct leg"));
    assertEquals(1, shipmentWithLeg.legCount());

    var confirmed = context.confirmShipment.execute(new ConfirmShipmentCommand(draftShipment.id()));

    assertTrue(confirmed.confirmed());
    assertEquals(ShipmentStatus.CONFIRMED, confirmed.status());
    assertEquals(1, confirmed.itemCount());
    assertEquals(1, confirmed.legCount());

    var found = context.findShipment.execute(new FindShipmentCommand(draftShipment.id()));
    assertEquals(confirmed, found);
  }

  @Test
  void registerLocationRejectsDuplicateBusinessCode() {
    var context = new UseCaseContext();

    context.registerLocation.execute(milanDepotCommand());

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerLocation.execute(milanDepotCommand()));
  }

  @Test
  void addShipmentItemFailsWhenCargoDoesNotExist() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(standardShipmentCommand());

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
  }

  @Test
  void addShipmentLegFailsWhenDestinationLocationDoesNotExist() {
    var context = new UseCaseContext();
    var origin = context.registerLocation.execute(milanDepotCommand());
    var shipment = context.createShipment.execute(standardShipmentCommand());

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
  }

  @Test
  void confirmShipmentPropagatesDomainErrorWhenShipmentIsIncomplete() {
    var context = new UseCaseContext();
    var shipment = context.createShipment.execute(standardShipmentCommand());

    assertThrows(
        InvalidShipmentException.class,
        () -> context.confirmShipment.execute(new ConfirmShipmentCommand(shipment.id())));
  }

  private static RegisterLocationCommand milanDepotCommand() {
    return new RegisterLocationCommand(
        LocationCode.of("dep-mil-001"),
        "Milano Depot",
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        new LocationAddress("Via Roma 10", "Milano", "20100", "MI", "IT", "Main depot"),
        GeoCoordinates.of(new BigDecimal("45.4642"), new BigDecimal("9.1900")),
        "Primary depot");
  }

  private static RegisterLocationCommand romeDepotCommand() {
    return new RegisterLocationCommand(
        LocationCode.of("dep-roma-001"),
        "Roma Depot",
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        new LocationAddress("Via Appia 20", "Roma", "00100", "RM", "IT", "Destination"),
        GeoCoordinates.of(new BigDecimal("41.9028"), new BigDecimal("12.4964")),
        "Destination depot");
  }

  private static RegisterCargoUnitCommand foodCargoCommand() {
    return new RegisterCargoUnitCommand(
        CargoCode.of("food-001"),
        "Prodotti freschi alimentari",
        "Merce alimentare fresca su pallet",
        CargoType.FOOD,
        Set.of(CargoCategory.REFRIGERATED, CargoCategory.PALLETIZED),
        new CargoDimensions(null, null, null, new BigDecimal("18.00")),
        new CargoWeights(new BigDecimal("9600"), new BigDecimal("9300"), new BigDecimal("300")),
        new CargoPackaging(CargoPackagingType.PALLET, null, 12, "", true, "Euro pallet"),
        new CargoTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh food"),
        CargoHazard.none(),
        new CargoRegulatory(false, true, true, false, false, false, "ATP and food grade"),
        new CargoProperties(false, true, false, false, false, "Perishable food"),
        new CargoCompatibilityRequirement(
            Set.of(
                CargoTransportRequirement.REFRIGERATED_VEHICLE_REQUIRED,
                CargoTransportRequirement.TEMPERATURE_CONTROL_REQUIRED,
                CargoTransportRequirement.ATP_CERTIFICATION_REQUIRED,
                CargoTransportRequirement.FOOD_GRADE_BODY_REQUIRED),
            null,
            null,
            null,
            null,
            null,
            "Requires refrigerated ATP transport"),
        CargoStatus.ACTIVE,
        "Fresh food cargo");
  }

  private static CreateShipmentCommand freshFoodShipmentCommand() {
    return new CreateShipmentCommand(
        ShipmentCode.of("shp-001"),
        "Spedizione alimentare Milano - Roma",
        "Spedizione fresca con requisiti ATP",
        ShipmentPriority.HIGH,
        ShipmentServiceLevel.EXPRESS,
        ShipmentProperties.standard(),
        new ShipmentTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh goods"),
        new ShipmentRequirementSet(
            Set.of(
                ShipmentTransportRequirement.ATP_REQUIRED,
                ShipmentTransportRequirement.FOOD_GRADE_REQUIRED,
                ShipmentTransportRequirement.TEMPERATURE_CONTROL_REQUIRED,
                ShipmentTransportRequirement.REFRIGERATED_TRANSPORT_REQUIRED),
            "Fresh shipment requirements"),
        ShipmentMetrics.empty(),
        ShipmentReferences.empty(),
        ShipmentNotes.empty(),
        "General shipment notes");
  }

  private static CreateShipmentCommand standardShipmentCommand() {
    return new CreateShipmentCommand(
        ShipmentCode.of("shp-standard"),
        "Spedizione standard",
        "Draft standard shipment",
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
    private final RegisterCargoUnitService registerCargoUnit =
        new RegisterCargoUnitService(cargoUnitRepository);
    private final CreateShipmentService createShipment =
        new CreateShipmentService(shipmentRepository);
    private final AddShipmentItemService addShipmentItem =
        new AddShipmentItemService(shipmentRepository, cargoUnitRepository);
    private final AddShipmentLegService addShipmentLeg =
        new AddShipmentLegService(shipmentRepository, locationRepository);
    private final ConfirmShipmentService confirmShipment =
        new ConfirmShipmentService(shipmentRepository);
    private final FindShipmentService findShipment = new FindShipmentService(shipmentRepository);
  }
}
