package it.gabriele.truckflow.domain.shipments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.cargo.CargoId;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentPriority;
import it.gabriele.truckflow.domain.shipments.core.ShipmentServiceLevel;
import it.gabriele.truckflow.domain.shipments.core.ShipmentStatus;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentException;
import it.gabriele.truckflow.domain.shipments.items.ShipmentItem;
import it.gabriele.truckflow.domain.shipments.items.ShipmentUnitOfMeasure;
import it.gabriele.truckflow.domain.shipments.legs.ShipmentLeg;
import it.gabriele.truckflow.domain.shipments.legs.ShipmentLegType;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentMetrics;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentVolume;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentWeight;
import it.gabriele.truckflow.domain.shipments.notes.ShipmentNotes;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentProperties;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentTemperature;
import it.gabriele.truckflow.domain.shipments.references.ShipmentReferences;
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentRequirementSet;
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentTransportRequirement;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ShipmentDomainTest {

  @Test
  void createsConfirmedFoodShipmentWithItemsLegsAndRequirements() {
    var shipment = confirmedFoodShipment();

    assertTrue(shipment.isConfirmed());
    assertEquals(ShipmentCode.of("SHP-001"), shipment.code());
    assertEquals(ShipmentPriority.HIGH, shipment.priority());
    assertEquals(ShipmentServiceLevel.EXPRESS, shipment.serviceLevel());
    assertEquals(2, shipment.itemCount());
    assertEquals(2, shipment.legCount());
    assertTrue(shipment.isTemperatureControlled());
    assertTrue(shipment.requires(ShipmentTransportRequirement.TEMPERATURE_CONTROL_REQUIRED));
    assertTrue(shipment.requires(ShipmentTransportRequirement.REFRIGERATED_TRANSPORT_REQUIRED));
  }

  @Test
  void shipmentLegsAreSortedBySequenceNumber() {
    var origin = LocationId.random();
    var hub = LocationId.random();
    var destination = LocationId.random();

    var legTwo =
        new ShipmentLeg(
            null,
            2,
            ShipmentLegType.DELIVERY,
            hub,
            destination,
            new BigDecimal("200"),
            "Second leg");
    var legOne =
        new ShipmentLeg(
            null, 1, ShipmentLegType.PICKUP, origin, hub, new BigDecimal("100"), "First leg");

    var shipment =
        new Shipment(
            null,
            ShipmentCode.of("SHP-SORT"),
            "Sorted shipment",
            "Shipment with unordered input legs",
            ShipmentStatus.CONFIRMED,
            ShipmentPriority.NORMAL,
            ShipmentServiceLevel.STANDARD,
            List.of(item()),
            List.of(legTwo, legOne),
            ShipmentProperties.standard(),
            ShipmentTemperature.uncontrolled(),
            ShipmentRequirementSet.none(),
            ShipmentMetrics.empty(),
            ShipmentReferences.empty(),
            ShipmentNotes.empty(),
            "");

    assertEquals(1, shipment.legs().get(0).sequenceNumber());
    assertEquals(2, shipment.legs().get(1).sequenceNumber());
    assertTrue(shipment.isContinuous());
  }

  @Test
  void confirmedShipmentMustHaveAtLeastOneItem() {
    assertThrows(
        InvalidShipmentException.class,
        () ->
            new Shipment(
                null,
                ShipmentCode.of("SHP-NO-ITEMS"),
                "Invalid shipment",
                "Confirmed shipment without items",
                ShipmentStatus.CONFIRMED,
                ShipmentPriority.NORMAL,
                ShipmentServiceLevel.STANDARD,
                List.of(),
                List.of(leg()),
                ShipmentProperties.standard(),
                ShipmentTemperature.uncontrolled(),
                ShipmentRequirementSet.none(),
                ShipmentMetrics.empty(),
                ShipmentReferences.empty(),
                ShipmentNotes.empty(),
                ""));
  }

  @Test
  void confirmedShipmentMustHaveAtLeastOneLeg() {
    assertThrows(
        InvalidShipmentException.class,
        () ->
            new Shipment(
                null,
                ShipmentCode.of("SHP-NO-LEGS"),
                "Invalid shipment",
                "Confirmed shipment without legs",
                ShipmentStatus.CONFIRMED,
                ShipmentPriority.NORMAL,
                ShipmentServiceLevel.STANDARD,
                List.of(item()),
                List.of(),
                ShipmentProperties.standard(),
                ShipmentTemperature.uncontrolled(),
                ShipmentRequirementSet.none(),
                ShipmentMetrics.empty(),
                ShipmentReferences.empty(),
                ShipmentNotes.empty(),
                ""));
  }

  @Test
  void controlledTemperatureRequiresTransportRequirement() {
    assertThrows(
        InvalidShipmentException.class,
        () ->
            new Shipment(
                null,
                ShipmentCode.of("SHP-TEMP"),
                "Invalid temperature shipment",
                "Temperature shipment missing requirement",
                ShipmentStatus.CONFIRMED,
                ShipmentPriority.HIGH,
                ShipmentServiceLevel.EXPRESS,
                List.of(item()),
                List.of(leg()),
                ShipmentProperties.standard(),
                new ShipmentTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh"),
                ShipmentRequirementSet.none(),
                ShipmentMetrics.empty(),
                ShipmentReferences.empty(),
                ShipmentNotes.empty(),
                ""));
  }

  @Test
  void duplicateLegSequenceNumbersAreRejected() {
    var origin = LocationId.random();
    var hub = LocationId.random();
    var destination = LocationId.random();

    var first = new ShipmentLeg(null, 1, ShipmentLegType.PICKUP, origin, hub, BigDecimal.TEN, "");
    var second =
        new ShipmentLeg(null, 1, ShipmentLegType.DELIVERY, hub, destination, BigDecimal.TEN, "");

    assertThrows(
        InvalidShipmentException.class,
        () ->
            new Shipment(
                null,
                ShipmentCode.of("SHP-DUP"),
                "Duplicate leg sequence shipment",
                "Invalid duplicate leg sequence",
                ShipmentStatus.CONFIRMED,
                ShipmentPriority.NORMAL,
                ShipmentServiceLevel.STANDARD,
                List.of(item()),
                List.of(first, second),
                ShipmentProperties.standard(),
                ShipmentTemperature.uncontrolled(),
                ShipmentRequirementSet.none(),
                ShipmentMetrics.empty(),
                ShipmentReferences.empty(),
                ShipmentNotes.empty(),
                ""));
  }

  @Test
  void itemQuantityMustBePositive() {
    assertThrows(
        InvalidShipmentException.class,
        () ->
            new ShipmentItem(
                null, CargoId.random(), BigDecimal.ZERO, ShipmentUnitOfMeasure.PALLET, ""));
  }

  @Test
  void shipmentWeightRejectsNetGreaterThanGross() {
    assertThrows(
        InvalidShipmentException.class,
        () -> ShipmentWeight.kg(new BigDecimal("100"), new BigDecimal("120")));
  }

  @Test
  void draftShipmentCanExistWithoutItemsAndLegs() {
    var shipment =
        new Shipment(
            null,
            ShipmentCode.of("SHP-DRAFT"),
            "Draft shipment",
            "Draft without operational details",
            ShipmentStatus.DRAFT,
            ShipmentPriority.NORMAL,
            ShipmentServiceLevel.STANDARD,
            List.of(),
            List.of(),
            ShipmentProperties.standard(),
            ShipmentTemperature.uncontrolled(),
            ShipmentRequirementSet.none(),
            ShipmentMetrics.empty(),
            ShipmentReferences.empty(),
            ShipmentNotes.empty(),
            "Draft notes");

    assertFalse(shipment.isConfirmed());
    assertEquals(0, shipment.itemCount());
    assertEquals(0, shipment.legCount());
  }

  @Test
  void failedConfirmDoesNotMutateDraftShipment() {
    var shipment =
        new Shipment(
            null,
            ShipmentCode.of("SHP-ATOMIC"),
            "Atomic shipment",
            "Draft without items and legs",
            ShipmentStatus.DRAFT,
            ShipmentPriority.NORMAL,
            ShipmentServiceLevel.STANDARD,
            List.of(),
            List.of(),
            ShipmentProperties.standard(),
            ShipmentTemperature.uncontrolled(),
            ShipmentRequirementSet.none(),
            ShipmentMetrics.empty(),
            ShipmentReferences.empty(),
            ShipmentNotes.empty(),
            "");

    assertThrows(InvalidShipmentException.class, shipment::confirm);

    assertEquals(ShipmentStatus.DRAFT, shipment.status());
    assertEquals(0, shipment.itemCount());
    assertEquals(0, shipment.legCount());
  }

  private static Shipment confirmedFoodShipment() {
    var origin = LocationId.random();
    var hub = LocationId.random();
    var destination = LocationId.random();

    return new Shipment(
        null,
        ShipmentCode.of("shp-001"),
        "Spedizione alimentare Milano - Roma",
        "Spedizione fresca con requisiti ATP",
        ShipmentStatus.CONFIRMED,
        ShipmentPriority.HIGH,
        ShipmentServiceLevel.EXPRESS,
        List.of(
            new ShipmentItem(
                null, CargoId.random(), new BigDecimal("33"), ShipmentUnitOfMeasure.PALLET, "Food"),
            new ShipmentItem(
                null,
                CargoId.random(),
                new BigDecimal("4"),
                ShipmentUnitOfMeasure.PALLET,
                "Pharma")),
        List.of(
            new ShipmentLeg(
                null, 1, ShipmentLegType.PICKUP, origin, hub, new BigDecimal("210"), "Pickup leg"),
            new ShipmentLeg(
                null,
                2,
                ShipmentLegType.DELIVERY,
                hub,
                destination,
                new BigDecimal("370"),
                "Delivery leg")),
        new ShipmentProperties(false, true, true, true, true, "Separated goods"),
        new ShipmentTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh goods"),
        new ShipmentRequirementSet(
            Set.of(
                ShipmentTransportRequirement.ATP_REQUIRED,
                ShipmentTransportRequirement.FOOD_GRADE_REQUIRED,
                ShipmentTransportRequirement.TEMPERATURE_CONTROL_REQUIRED,
                ShipmentTransportRequirement.REFRIGERATED_TRANSPORT_REQUIRED,
                ShipmentTransportRequirement.SEPARATION_REQUIRED),
            "Fresh shipment requirements"),
        new ShipmentMetrics(
            ShipmentVolume.cubicMeters(new BigDecimal("60")),
            ShipmentWeight.kg(new BigDecimal("12000"), new BigDecimal("11800")),
            "Declared metrics"),
        new ShipmentReferences("ORD-2024-033", "SUP-8831", "INT-0001", "PO-778", "SO-551", ""),
        new ShipmentNotes("Verificare separazione merce pharma", "Consegnare in area refrigerata"),
        "General shipment notes");
  }

  private static ShipmentItem item() {
    return new ShipmentItem(
        null, CargoId.random(), BigDecimal.ONE, ShipmentUnitOfMeasure.PALLET, "Test item");
  }

  private static ShipmentLeg leg() {
    return new ShipmentLeg(
        null,
        1,
        ShipmentLegType.PICKUP,
        LocationId.random(),
        LocationId.random(),
        BigDecimal.TEN,
        "Test leg");
  }
}
