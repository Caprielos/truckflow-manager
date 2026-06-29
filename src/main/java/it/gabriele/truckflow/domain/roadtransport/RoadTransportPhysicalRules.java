package it.gabriele.truckflow.domain.roadtransport;

import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;

/** Regole fisiche generali per mezzi, masse, assi e capacità operative. */
public final class RoadTransportPhysicalRules {

  private RoadTransportPhysicalRules() {}

  public static boolean canCarryPayload(VehicleRoadUnitProfile profile, Weight payloadWeight) {
    Objects.requireNonNull(profile, "Il profilo veicolo è obbligatorio.");
    Objects.requireNonNull(payloadWeight, "Il peso del carico è obbligatorio.");
    return payloadWeight.getKilograms() <= profile.authorizedGrossMass().getKilograms()
        && !profile.hasAxleOverload();
  }

  public static boolean canPerformSpecialTransport(
      VehicleRoadUnitProfile profile, PhysicalTransportCapability capability) {
    Objects.requireNonNull(profile, "Il profilo veicolo è obbligatorio.");
    Objects.requireNonNull(capability, "La capacità speciale è obbligatoria.");
    return profile.supports(capability) && !profile.hasAxleOverload();
  }

  public static boolean requiresProfessionalComplianceCheck(VehicleRoadUnitProfile profile) {
    Objects.requireNonNull(profile, "Il profilo veicolo è obbligatorio.");
    return profile.isHeavyGoodsVehicle()
        || profile.supports(PhysicalTransportCapability.ADR)
        || profile.supports(PhysicalTransportCapability.ATP)
        || profile.supports(PhysicalTransportCapability.WASTE)
        || profile.supports(PhysicalTransportCapability.LIVESTOCK)
        || profile.supports(PhysicalTransportCapability.OVERSIZED);
  }
}
