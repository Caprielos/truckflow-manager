package it.gabriele.truckflow.domain.shipments.requirements;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import java.util.Set;

public record ShipmentRequirementSet(Set<ShipmentTransportRequirement> requirements, String notes) {

  public ShipmentRequirementSet {
    requirements = requirements == null ? Set.of() : requirements;
    ShipmentValidation.requireNoNullElements(requirements, "requirements");
    requirements = Set.copyOf(requirements);
    notes = ShipmentValidation.normalize(notes);
  }

  public boolean requires(ShipmentTransportRequirement requirement) {
    ShipmentValidation.requireNonNull(requirement, "requirement");
    return requirements.contains(requirement);
  }

  public static ShipmentRequirementSet none() {
    return new ShipmentRequirementSet(Set.of(), "");
  }
}
