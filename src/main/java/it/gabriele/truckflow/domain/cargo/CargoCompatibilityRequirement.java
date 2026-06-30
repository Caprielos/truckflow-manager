package it.gabriele.truckflow.domain.cargo;

import java.math.BigDecimal;
import java.util.Set;

public record CargoCompatibilityRequirement(
    Set<CargoTransportRequirement> transportRequirements,
    BigDecimal minPayloadKg,
    BigDecimal minVolumeCubicMeters,
    BigDecimal minInternalLengthMeters,
    BigDecimal minInternalWidthMeters,
    BigDecimal minInternalHeightMeters,
    String notes) {

  public CargoCompatibilityRequirement {
    transportRequirements = transportRequirements == null ? Set.of() : transportRequirements;
    CargoValidation.requireNoNullElements(transportRequirements, "transportRequirements");
    transportRequirements = Set.copyOf(transportRequirements);
    minPayloadKg = CargoValidation.nonNegativeOrNull(minPayloadKg, "minPayloadKg");
    minVolumeCubicMeters =
        CargoValidation.nonNegativeOrNull(minVolumeCubicMeters, "minVolumeCubicMeters");
    minInternalLengthMeters =
        CargoValidation.nonNegativeOrNull(minInternalLengthMeters, "minInternalLengthMeters");
    minInternalWidthMeters =
        CargoValidation.nonNegativeOrNull(minInternalWidthMeters, "minInternalWidthMeters");
    minInternalHeightMeters =
        CargoValidation.nonNegativeOrNull(minInternalHeightMeters, "minInternalHeightMeters");
    notes = CargoValidation.normalize(notes);
  }

  public boolean requires(CargoTransportRequirement requirement) {
    CargoValidation.requireNonNull(requirement, "requirement");
    return transportRequirements.contains(requirement);
  }

  public static CargoCompatibilityRequirement none() {
    return new CargoCompatibilityRequirement(Set.of(), null, null, null, null, null, "");
  }
}
