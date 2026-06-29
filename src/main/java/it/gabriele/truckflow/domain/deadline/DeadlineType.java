package it.gabriele.truckflow.domain.deadline;

/** Tipo di scadenza gestita dalla piattaforma. */
public enum DeadlineType {
  DRIVER_LICENSE(true),
  DRIVER_CQC(true),
  DRIVER_ADR(true),
  DRIVER_MEDICAL_CHECK(true),
  DRIVER_TRAINING(false),
  VEHICLE_INSPECTION(true),
  VEHICLE_INSURANCE(true),
  VEHICLE_ATP_CERTIFICATE(true),
  VEHICLE_ADR_CERTIFICATE(true),
  SCHEDULED_MAINTENANCE(false),
  CONTRACT_RENEWAL(false),
  DOCUMENT_EXPIRATION(true),
  COMPLIANCE_AUDIT(false),
  OTHER(false);

  private final boolean blocksOperationsWhenExpired;

  DeadlineType(boolean blocksOperationsWhenExpired) {
    this.blocksOperationsWhenExpired = blocksOperationsWhenExpired;
  }

  public boolean blocksOperationsWhenExpired() {
    return blocksOperationsWhenExpired;
  }
}
