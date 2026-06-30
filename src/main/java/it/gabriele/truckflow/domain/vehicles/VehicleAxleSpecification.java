package it.gabriele.truckflow.domain.vehicles;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record VehicleAxleSpecification(List<VehicleAxle> axles) {

  public VehicleAxleSpecification {
    axles = axles == null ? List.of() : List.copyOf(axles);
    VehicleValidation.requireNoNullElements(axles, "axles");

    Set<Integer> uniqueAxleNumbers =
        axles.stream().map(VehicleAxle::axleNumber).collect(Collectors.toSet());
    if (uniqueAxleNumbers.size() != axles.size()) {
      throw new IllegalArgumentException("Axle numbers must be unique.");
    }

    axles = axles.stream().sorted(Comparator.comparingInt(VehicleAxle::axleNumber)).toList();
  }

  public static VehicleAxleSpecification empty() {
    return new VehicleAxleSpecification(List.of());
  }

  public int axleCount() {
    return axles.size();
  }

  public boolean hasLiftableAxle() {
    return axles.stream().anyMatch(VehicleAxle::liftable);
  }

  public boolean hasSteerableAxle() {
    return axles.stream().anyMatch(VehicleAxle::steerable);
  }

  public boolean hasTwinTires() {
    return axles.stream().anyMatch(VehicleAxle::twinTires);
  }
}
