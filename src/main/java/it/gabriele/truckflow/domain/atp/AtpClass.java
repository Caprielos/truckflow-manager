package it.gabriele.truckflow.domain.atp;

/** Classi ATP operative usate per trasporto a temperatura controllata. */
public enum AtpClass {
  IN(false, false),
  IR(false, false),
  FNA(true, false),
  FRA(true, false),
  FRB(true, false),
  FRC(true, true),
  RRA(true, false),
  RRC(true, true);

  private final boolean activeRefrigeration;
  private final boolean frozenTransportCapable;

  AtpClass(boolean activeRefrigeration, boolean frozenTransportCapable) {
    this.activeRefrigeration = activeRefrigeration;
    this.frozenTransportCapable = frozenTransportCapable;
  }

  public boolean hasActiveRefrigeration() {
    return activeRefrigeration;
  }

  public boolean isFrozenTransportCapable() {
    return frozenTransportCapable;
  }
}
