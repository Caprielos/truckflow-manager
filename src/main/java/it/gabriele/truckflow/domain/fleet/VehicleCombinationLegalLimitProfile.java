package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;

public final class VehicleCombinationLegalLimitProfile {

  private final Weight maximumGrossCombinationWeight;
  private final Dimension maximumExternalDimension;

  private VehicleCombinationLegalLimitProfile(
      Weight maximumGrossCombinationWeight, Dimension maximumExternalDimension) {
    if (maximumGrossCombinationWeight == null) {
      throw new IllegalArgumentException("Il limite massa convoglio è obbligatorio.");
    }
    if (maximumExternalDimension == null) {
      throw new IllegalArgumentException("Il limite dimensionale è obbligatorio.");
    }
    this.maximumGrossCombinationWeight = maximumGrossCombinationWeight;
    this.maximumExternalDimension = maximumExternalDimension;
  }

  public static VehicleCombinationLegalLimitProfile italianStandardAutotreno() {
    return new VehicleCombinationLegalLimitProfile(
        Weight.ofKilograms(44000), Dimension.ofMeters(18.75, 2.55, 4.0));
  }

  public static VehicleCombinationLegalLimitProfile italianStandardRefrigeratedAutotreno() {
    return new VehicleCombinationLegalLimitProfile(
        Weight.ofKilograms(44000), Dimension.ofMeters(18.75, 2.60, 4.0));
  }

  public Weight getMaximumGrossCombinationWeight() {
    return maximumGrossCombinationWeight;
  }

  public Dimension getMaximumExternalDimension() {
    return maximumExternalDimension;
  }
}
