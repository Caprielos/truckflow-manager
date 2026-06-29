package it.gabriele.truckflow.domain.cargo;

/** Rappresenta il gruppo di imballaggio ADR. */
public enum PackingGroup {
  I("High danger"),
  II("Medium danger"),
  III("Low danger");

  private final String description;

  PackingGroup(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public boolean isHighDanger() {
    return this == I;
  }

  public boolean isMediumDanger() {
    return this == II;
  }

  public boolean isLowDanger() {
    return this == III;
  }
}
