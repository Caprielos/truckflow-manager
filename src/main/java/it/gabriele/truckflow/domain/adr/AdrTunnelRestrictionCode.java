package it.gabriele.truckflow.domain.adr;

/** Codice restrizione gallerie ADR. A è il meno restrittivo, E il più restrittivo. */
public enum AdrTunnelRestrictionCode {
  A(1),
  B(2),
  C(3),
  D(4),
  E(5),
  NOT_RESTRICTED(0);

  private final int severity;

  AdrTunnelRestrictionCode(int severity) {
    this.severity = severity;
  }

  public boolean allows(AdrTunnelRestrictionCode cargoRestriction) {
    if (cargoRestriction == null) {
      throw new IllegalArgumentException("Il codice restrizione merce è obbligatorio.");
    }
    if (this == NOT_RESTRICTED || cargoRestriction == NOT_RESTRICTED) {
      return true;
    }
    return this.severity <= cargoRestriction.severity;
  }
}
