package it.gabriele.truckflow.domain.order;

/** Rappresenta il tipo di servizio richiesto per un ordine di trasporto. */
public enum TransportServiceType {
  STANDARD(false, false),
  EXPRESS(false, false),
  REFRIGERATED(true, false),
  HAZARDOUS(false, true),
  OVERSIZED(false, false);

  private final boolean supportsTemperatureControl;
  private final boolean supportsHazardousMaterial;

  TransportServiceType(boolean supportsTemperatureControl, boolean supportsHazardousMaterial) {
    this.supportsTemperatureControl = supportsTemperatureControl;
    this.supportsHazardousMaterial = supportsHazardousMaterial;
  }

  public boolean supportsTemperatureControl() {
    return supportsTemperatureControl;
  }

  public boolean supportsHazardousMaterial() {
    return supportsHazardousMaterial;
  }
}
