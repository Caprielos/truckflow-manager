package it.gabriele.truckflow.domain.compliance;

public record ComplianceTarget(ComplianceTargetType targetType, String notes) {

  public ComplianceTarget {
    targetType = ComplianceValidation.requireNonNull(targetType, "targetType");
    notes = ComplianceValidation.normalize(notes);
  }

  public static ComplianceTarget of(ComplianceTargetType targetType) {
    return new ComplianceTarget(targetType, "");
  }

  public boolean appliesTo(ComplianceTargetType targetType) {
    return this.targetType == ComplianceValidation.requireNonNull(targetType, "targetType");
  }
}
