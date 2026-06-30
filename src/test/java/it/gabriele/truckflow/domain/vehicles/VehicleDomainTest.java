package it.gabriele.truckflow.domain.vehicles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.vehicles.body.CurtainsiderBodyProfile;
import it.gabriele.truckflow.domain.vehicles.body.RefrigeratedBodyProfile;
import it.gabriele.truckflow.domain.vehicles.body.VehicleBodyType;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
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
import it.gabriele.truckflow.domain.vehicles.unit.PowerSource;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VehicleDomainTest {

  @Test
  void createsArticulatedVehicleFromTractorAndSemiTrailer() {
    var tractor = tractorUnit();
    var semiTrailer = refrigeratedSemiTrailer();

    var combination =
        VehicleCombination.fromUnits(
            null,
            VehicleCombinationType.ARTICULATED_VEHICLE,
            tractor,
            semiTrailer,
            VehicleStatus.ACTIVE,
            "Line haul refrigerated articulated vehicle");

    assertTrue(combination.isActive());
    assertTrue(combination.hasSecondaryUnit());
    assertEquals(tractor.id(), combination.primaryUnitId());
    assertEquals(semiTrailer.id(), combination.secondaryUnitId());
    assertTrue(combination.hasCapability(VehicleCapability.TEMPERATURE_CONTROLLED));
    assertTrue(combination.hasOperationalRole(VehicleOperationalRole.REFRIGERATED_TRANSPORT));
  }

  @Test
  void createsRoadTrainFromRigidTruckAndDrawbarTrailer() {
    var rigidTruck = rigidTruckThatCanTow();
    var trailer = drawbarTrailer();

    var combination =
        VehicleCombination.fromUnits(
            null,
            VehicleCombinationType.ROAD_TRAIN,
            rigidTruck,
            trailer,
            VehicleStatus.ACTIVE,
            "Rigid truck with trailer");

    assertEquals(VehicleCombinationType.ROAD_TRAIN, combination.combinationType());
    assertTrue(combination.hasSecondaryUnit());
  }

  @Test
  void roadTrainRequiresRigidTruckAndTrailer() {
    var tractor = tractorUnit();
    var trailer = drawbarTrailer();

    assertThrows(
        IllegalArgumentException.class,
        () ->
            VehicleCombination.fromUnits(
                null,
                VehicleCombinationType.ROAD_TRAIN,
                tractor,
                trailer,
                VehicleStatus.ACTIVE,
                "Wrong road train"));
  }

  @Test
  void trailerMustHaveNoPowerSource() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new VehicleUnit(
                null,
                FleetCode.of("TRL-999"),
                "XA999AA",
                "VINTRAILER999",
                VehicleUnitType.SEMI_TRAILER,
                VehicleBodyType.CURTAINSIDER,
                PowerSource.DIESEL,
                technicalSpecification(),
                new CurtainsiderBodyProfile(true, true, true, false, ""),
                Set.of(VehicleCapability.SLIDING_CURTAINS),
                Set.of(VehicleOperationalRole.LINE_HAUL),
                new CouplingProfile(CouplingType.KINGPIN, false, true, null, null, ""),
                VehicleStatus.ACTIVE,
                "Invalid trailer"));
  }

  @Test
  void bodyProfileMustMatchBodyType() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new VehicleUnit(
                null,
                FleetCode.of("TRL-888"),
                "XA888AA",
                "VINTRAILER888",
                VehicleUnitType.SEMI_TRAILER,
                VehicleBodyType.CURTAINSIDER,
                PowerSource.NONE,
                technicalSpecification(),
                new RefrigeratedBodyProfile(
                    "FRC", new BigDecimal("-20"), new BigDecimal("4"), true, "Carrier", "X", ""),
                Set.of(VehicleCapability.SLIDING_CURTAINS),
                Set.of(VehicleOperationalRole.LINE_HAUL),
                new CouplingProfile(CouplingType.KINGPIN, false, true, null, null, ""),
                VehicleStatus.ACTIVE,
                "Invalid profile"));
  }

  @Test
  void axleSpecificationKeepsAxleLevelTwinTireInformation() {
    var axles =
        new VehicleAxleSpecification(
            List.of(
                new VehicleAxle(2, false, false, true, "rear twin tires"),
                new VehicleAxle(1, true, false, false, "front steering axle")));

    assertEquals(2, axles.axleCount());
    assertTrue(axles.hasSteerableAxle());
    assertTrue(axles.hasTwinTires());
    assertFalse(axles.hasLiftableAxle());
    assertEquals(1, axles.axles().getFirst().axleNumber());
  }

  @Test
  void singleVehicleCannotUseTrailerAsPrimaryUnit() {
    var trailer = drawbarTrailer();

    assertThrows(
        IllegalArgumentException.class,
        () ->
            VehicleCombination.fromUnits(
                null,
                VehicleCombinationType.SINGLE_VEHICLE,
                trailer,
                null,
                VehicleStatus.ACTIVE,
                "Invalid single vehicle"));
  }

  private static VehicleUnit tractorUnit() {
    return new VehicleUnit(
        null,
        FleetCode.of("TRC-001"),
        "AB123CD",
        "VINTRACTOR001",
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
        "Tractor unit");
  }

  private static VehicleUnit refrigeratedSemiTrailer() {
    return new VehicleUnit(
        null,
        FleetCode.of("TRL-001"),
        "XA123AA",
        "VINTRAILER001",
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
        "Refrigerated semi-trailer");
  }

  private static VehicleUnit rigidTruckThatCanTow() {
    return new VehicleUnit(
        null,
        FleetCode.of("TRK-001"),
        "CD456EF",
        "VINTRUCK001",
        VehicleUnitType.RIGID_TRUCK,
        VehicleBodyType.CURTAINSIDER,
        PowerSource.DIESEL,
        technicalSpecification(),
        new CurtainsiderBodyProfile(true, true, true, false, ""),
        Set.of(VehicleCapability.SLIDING_CURTAINS),
        Set.of(VehicleOperationalRole.DISTRIBUTION),
        new CouplingProfile(
            CouplingType.DRAWBAR_HITCH,
            true,
            false,
            new BigDecimal("18000"),
            new BigDecimal("44000"),
            "Drawbar hitch"),
        VehicleStatus.ACTIVE,
        "Rigid truck");
  }

  private static VehicleUnit drawbarTrailer() {
    return new VehicleUnit(
        null,
        FleetCode.of("DRW-001"),
        "XB456BB",
        "VINDRAWBAR001",
        VehicleUnitType.DRAWBAR_TRAILER,
        VehicleBodyType.CURTAINSIDER,
        PowerSource.NONE,
        technicalSpecification(),
        new CurtainsiderBodyProfile(true, false, true, false, ""),
        Set.of(VehicleCapability.SLIDING_CURTAINS),
        Set.of(VehicleOperationalRole.DISTRIBUTION),
        new CouplingProfile(CouplingType.DRAWBAR, false, true, null, null, "Drawbar"),
        VehicleStatus.ACTIVE,
        "Drawbar trailer");
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
