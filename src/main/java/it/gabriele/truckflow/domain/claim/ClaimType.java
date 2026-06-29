package it.gabriele.truckflow.domain.claim;

/** Tipo di reclamo o contestazione collegata al trasporto. */
public enum ClaimType {
  CARGO_DAMAGE(true, false, false, false),
  CARGO_LOSS(true, false, false, false),
  DELAY(false, true, false, false),
  TEMPERATURE_EXCURSION(true, true, false, false),
  DOCUMENT_DISPUTE(false, false, true, false),
  BILLING_DISPUTE(false, false, false, true),
  VEHICLE_DAMAGE(false, false, false, true),
  ACCIDENT(false, true, true, true),
  INSURANCE_CLAIM(false, false, true, true),
  OTHER(false, false, false, false);

  private final boolean cargoRelated;
  private final boolean timeRelated;
  private final boolean documentRelated;
  private final boolean financialDispute;

  ClaimType(
      boolean cargoRelated,
      boolean timeRelated,
      boolean documentRelated,
      boolean financialDispute) {
    this.cargoRelated = cargoRelated;
    this.timeRelated = timeRelated;
    this.documentRelated = documentRelated;
    this.financialDispute = financialDispute;
  }

  public boolean isCargoRelated() {
    return cargoRelated;
  }

  public boolean isTimeRelated() {
    return timeRelated;
  }

  public boolean isDocumentRelated() {
    return documentRelated;
  }

  public boolean isFinancialDispute() {
    return financialDispute;
  }
}
