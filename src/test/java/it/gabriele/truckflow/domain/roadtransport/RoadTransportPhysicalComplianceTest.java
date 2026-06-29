package it.gabriele.truckflow.domain.roadtransport;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.fleet.VehicleUnitType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RoadTransportPhysicalComplianceTest {

  @Test
  void shouldModelVehicleCategoryDriveConfigurationAndAxleLoad() {
    AxleLoadPlan axleLoadPlan =
        new AxleLoadPlan(
            List.of(
                new AxleLoad(1, 7000, 8000, 8500),
                new AxleLoad(2, 10500, 11500, 12000),
                new AxleLoad(3, 10500, 11500, 12000)));

    VehicleRoadUnitProfile profile =
        new VehicleRoadUnitProfile(
            "TRUCK-N3-001",
            VehicleUnitType.RIGID_TRUCK,
            VehicleLegalCategory.N3,
            DriveConfiguration.SIX_BY_FOUR,
            VehicleBodyBaseType.REAR_TIPPER,
            Weight.ofKilograms(32000),
            Weight.ofKilograms(26000),
            Dimension.ofMeters(8.5, 2.55, 3.7),
            axleLoadPlan,
            Set.of(PhysicalTransportCapability.WASTE, PhysicalTransportCapability.HOOKLIFT));

    assertTrue(profile.isHeavyGoodsVehicle());
    assertTrue(profile.requiresTachographControls());
    assertTrue(profile.supports(PhysicalTransportCapability.WASTE));
    assertFalse(profile.hasAxleOverload());
    assertTrue(RoadTransportPhysicalRules.requiresProfessionalComplianceCheck(profile));
  }
}
