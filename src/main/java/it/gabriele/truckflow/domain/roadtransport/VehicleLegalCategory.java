package it.gabriele.truckflow.domain.roadtransport;

import it.gabriele.truckflow.domain.shared.Weight;

/** Categoria tecnica europea per veicoli merci. */
public enum VehicleLegalCategory {
  N1(3500),
  N2(12000),
  N3(Double.MAX_VALUE);

  private final double maximumGrossMassKilograms;

  VehicleLegalCategory(double maximumGrossMassKilograms) {
    this.maximumGrossMassKilograms = maximumGrossMassKilograms;
  }

  public double getMaximumGrossMassKilograms() {
    return maximumGrossMassKilograms;
  }

  public boolean accepts(Weight grossMass) {
    if (grossMass == null) {
      throw new IllegalArgumentException("La massa complessiva è obbligatoria.");
    }
    return grossMass.getKilograms() <= maximumGrossMassKilograms;
  }

  public boolean requiresProfessionalHeavyGoodsControls() {
    return this == N2 || this == N3;
  }

  public static VehicleLegalCategory fromGrossMass(Weight grossMass) {
    if (grossMass == null) {
      throw new IllegalArgumentException("La massa complessiva è obbligatoria.");
    }
    if (grossMass.getKilograms() <= N1.maximumGrossMassKilograms) {
      return N1;
    }
    if (grossMass.getKilograms() <= N2.maximumGrossMassKilograms) {
      return N2;
    }
    return N3;
  }
}
