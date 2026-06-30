package it.gabriele.truckflow.domain.compliance;

public record ComplianceSource(
    String sourceName,
    ComplianceSourceType sourceType,
    String referenceCode,
    String description,
    String notes) {

  public ComplianceSource {
    sourceName = ComplianceValidation.requireText(sourceName, "sourceName");
    sourceType = ComplianceValidation.requireNonNull(sourceType, "sourceType");
    referenceCode = ComplianceValidation.normalize(referenceCode);
    description = ComplianceValidation.normalize(description);
    notes = ComplianceValidation.normalize(notes);
  }
}
