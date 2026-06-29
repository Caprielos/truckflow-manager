package it.gabriele.truckflow.domain.roadtransport;

/** Configurazione trazione/assi del veicolo, espressa come ruote totali x ruote motrici. */
public enum DriveConfiguration {
  FOUR_BY_TWO("4x2", 2, 1),
  SIX_BY_TWO("6x2", 3, 1),
  SIX_BY_FOUR("6x4", 3, 2),
  EIGHT_BY_TWO("8x2", 4, 1),
  EIGHT_BY_FOUR("8x4", 4, 2),
  TEN_BY_FOUR("10x4", 5, 2),
  TRAILER_NO_DRIVE("0x0", 0, 0);

  private final String label;
  private final int axleCount;
  private final int drivenAxles;

  DriveConfiguration(String label, int axleCount, int drivenAxles) {
    this.label = label;
    this.axleCount = axleCount;
    this.drivenAxles = drivenAxles;
  }

  public String getLabel() {
    return label;
  }

  public int getAxleCount() {
    return axleCount;
  }

  public int getDrivenAxles() {
    return drivenAxles;
  }

  public boolean isHeavyDutyConfiguration() {
    return this == SIX_BY_FOUR || this == EIGHT_BY_FOUR || this == TEN_BY_FOUR;
  }

  public boolean isTrailerConfiguration() {
    return this == TRAILER_NO_DRIVE;
  }
}
