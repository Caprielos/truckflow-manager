package it.gabriele.truckflow.domain.vehicles;

import java.util.Set;

public record ContainerChassisBodyProfile(
    Set<String> supportedContainerTypes, boolean containerLocks, boolean extendable, String notes)
    implements VehicleBodyProfile {

  public ContainerChassisBodyProfile {
    supportedContainerTypes =
        supportedContainerTypes == null ? Set.of() : Set.copyOf(supportedContainerTypes);
    VehicleValidation.requireNoNullElements(supportedContainerTypes, "supportedContainerTypes");
    supportedContainerTypes =
        supportedContainerTypes.stream()
            .map(
                type -> VehicleValidation.requireText(type, "supportedContainerType").toUpperCase())
            .collect(java.util.stream.Collectors.toUnmodifiableSet());
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.CONTAINER_CHASSIS;
  }
}
