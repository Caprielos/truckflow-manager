package it.gabriele.truckflow.domain.roadtransport;

import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.fleet.VehicleUnitType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;
import java.util.Set;

/** Profilo fisico-normativo di una unità veicolare. */
public record VehicleRoadUnitProfile(
    String unitCode,
    VehicleUnitType unitType,
    VehicleLegalCategory legalCategory,
    DriveConfiguration driveConfiguration,
    VehicleBodyBaseType bodyBaseType,
    Weight technicalGrossMass,
    Weight authorizedGrossMass,
    Dimension externalDimensions,
    AxleLoadPlan axleLoadPlan,
    Set<PhysicalTransportCapability> capabilities) {

  public VehicleRoadUnitProfile {
    unitCode = normalize(unitCode, "Il codice unità veicolare è obbligatorio.");
    Objects.requireNonNull(unitType, "Il tipo unità veicolare è obbligatorio.");
    Objects.requireNonNull(legalCategory, "La categoria legale è obbligatoria.");
    Objects.requireNonNull(driveConfiguration, "La configurazione trazione è obbligatoria.");
    Objects.requireNonNull(bodyBaseType, "Il tipo allestimento base è obbligatorio.");
    Objects.requireNonNull(technicalGrossMass, "La massa tecnica è obbligatoria.");
    Objects.requireNonNull(authorizedGrossMass, "La massa omologata è obbligatoria.");
    Objects.requireNonNull(externalDimensions, "Le dimensioni esterne sono obbligatorie.");
    Objects.requireNonNull(axleLoadPlan, "Il piano carichi asse è obbligatorio.");
    if (authorizedGrossMass.isGreaterThan(technicalGrossMass)) {
      throw new IllegalArgumentException("La massa omologata non può superare la massa tecnica.");
    }
    if (!legalCategory.accepts(authorizedGrossMass)) {
      throw new IllegalArgumentException("La massa omologata non è coerente con la categoria N.");
    }
    capabilities = capabilities == null ? Set.of() : Set.copyOf(capabilities);
  }

  public boolean isHeavyGoodsVehicle() {
    return legalCategory.requiresProfessionalHeavyGoodsControls();
  }

  public boolean requiresTachographControls() {
    return authorizedGrossMass.getKilograms() > 3500;
  }

  public boolean supports(PhysicalTransportCapability capability) {
    if (capability == null) {
      throw new IllegalArgumentException("La capacità da verificare è obbligatoria.");
    }
    return capabilities.contains(capability);
  }

  public boolean hasAxleOverload() {
    return axleLoadPlan.hasLegalOverload();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
