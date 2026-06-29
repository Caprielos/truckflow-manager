package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;

/** Pesi e portate del singolo mezzo. */
public final class VehicleMassSpecification {

  private final Weight grossVehicleWeight;
  private final Weight tareWeight;
  private final Weight maxTowableWeight;
  private final Weight maximumFifthWheelLoad;

  private VehicleMassSpecification(
      Weight grossVehicleWeight,
      Weight tareWeight,
      Weight maxTowableWeight,
      Weight maximumFifthWheelLoad) {
    if (grossVehicleWeight == null) {
      throw new IllegalArgumentException("La massa complessiva è obbligatoria.");
    }
    if (tareWeight == null) {
      throw new IllegalArgumentException("La tara è obbligatoria.");
    }
    if (tareWeight.isGreaterThan(grossVehicleWeight)) {
      throw new IllegalArgumentException("La tara non può superare la massa complessiva.");
    }
    this.grossVehicleWeight = grossVehicleWeight;
    this.tareWeight = tareWeight;
    this.maxTowableWeight = maxTowableWeight;
    this.maximumFifthWheelLoad = maximumFifthWheelLoad;
  }

  public static VehicleMassSpecification of(
      Weight grossVehicleWeight,
      Weight tareWeight,
      Weight maxTowableWeight,
      Weight maximumFifthWheelLoad) {
    return new VehicleMassSpecification(
        grossVehicleWeight, tareWeight, maxTowableWeight, maximumFifthWheelLoad);
  }

  public Weight getGrossVehicleWeight() {
    return grossVehicleWeight;
  }

  public Weight getTareWeight() {
    return tareWeight;
  }

  public Weight getMaxTowableWeight() {
    return maxTowableWeight;
  }

  public Weight getMaximumFifthWheelLoad() {
    return maximumFifthWheelLoad;
  }

  public Weight calculateNetPayload() {
    return Weight.ofKilograms(grossVehicleWeight.getKilograms() - tareWeight.getKilograms());
  }

  public VehicleWeightClass calculateWeightClass() {
    return VehicleWeightClass.fromGrossWeight(grossVehicleWeight);
  }

  public boolean canTow(Weight trailerGrossWeight) {
    if (trailerGrossWeight == null) {
      throw new IllegalArgumentException("La massa del rimorchio è obbligatoria.");
    }
    return maxTowableWeight != null && trailerGrossWeight.isLessThanOrEqualTo(maxTowableWeight);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VehicleMassSpecification that)) return false;
    return grossVehicleWeight.equals(that.grossVehicleWeight)
        && tareWeight.equals(that.tareWeight)
        && Objects.equals(maxTowableWeight, that.maxTowableWeight)
        && Objects.equals(maximumFifthWheelLoad, that.maximumFifthWheelLoad);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grossVehicleWeight, tareWeight, maxTowableWeight, maximumFifthWheelLoad);
  }
}
