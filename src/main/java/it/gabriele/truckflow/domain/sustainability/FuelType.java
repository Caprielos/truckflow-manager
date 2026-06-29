package it.gabriele.truckflow.domain.sustainability;

/**
 * Tipo di alimentazione usata per stimare l'impatto ambientale. I fattori sono semplificati e
 * servono al domain model, non a una certificazione ufficiale.
 */
public enum FuelType {
  DIESEL(true, false),
  HVO(true, true),
  LNG(true, true),
  CNG(true, true),
  ELECTRIC(false, true),
  HYDROGEN(false, true),
  UNKNOWN(true, false);

  private final boolean combustionBased;
  private final boolean lowerEmissionAlternative;

  FuelType(boolean combustionBased, boolean lowerEmissionAlternative) {
    this.combustionBased = combustionBased;
    this.lowerEmissionAlternative = lowerEmissionAlternative;
  }

  public boolean isCombustionBased() {
    return combustionBased;
  }

  public boolean isLowerEmissionAlternative() {
    return lowerEmissionAlternative;
  }

  public boolean isZeroTailpipeEmission() {
    return this == ELECTRIC || this == HYDROGEN;
  }
}
