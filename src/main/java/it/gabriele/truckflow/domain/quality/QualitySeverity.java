package it.gabriele.truckflow.domain.quality;

/** Gravità di un evento qualità. */
public enum QualitySeverity {
  LOW(false),
  MEDIUM(false),
  HIGH(true),
  CRITICAL(true);

  private final boolean managementReviewRequired;

  QualitySeverity(boolean managementReviewRequired) {
    this.managementReviewRequired = managementReviewRequired;
  }

  public boolean requiresManagementReview() {
    return managementReviewRequired;
  }
}
