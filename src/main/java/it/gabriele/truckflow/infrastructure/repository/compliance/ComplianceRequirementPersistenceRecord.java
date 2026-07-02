package it.gabriele.truckflow.infrastructure.repository.compliance;

import java.util.Objects;

/** File-oriented persistence representation for compliance requirement repository expansion. */
public record ComplianceRequirementPersistenceRecord(
    String id,
    String code,
    String name,
    String description,
    String status,
    String category,
    String type,
    String obligationLevel,
    String severity,
    String targetType,
    String targetNotes,
    String ruleTitle,
    String ruleStatement,
    String expectedCondition,
    String ruleNotes,
    String sourceName,
    String sourceType,
    String referenceCode,
    String sourceDescription,
    String sourceNotes,
    String country,
    String region,
    String jurisdictionScope,
    String jurisdictionNotes,
    String notes) {

  /** Creates a normalized persistence record. */
  public ComplianceRequirementPersistenceRecord {
    id = requireText(id, "id");
    code = requireText(code, "code");
    name = requireText(name, "name");
    description = normalize(description);
    status = requireText(status, "status");
    category = requireText(category, "category");
    type = requireText(type, "type");
    obligationLevel = requireText(obligationLevel, "obligationLevel");
    severity = requireText(severity, "severity");
    targetType = requireText(targetType, "targetType");
    targetNotes = normalize(targetNotes);
    ruleTitle = requireText(ruleTitle, "ruleTitle");
    ruleStatement = requireText(ruleStatement, "ruleStatement");
    expectedCondition = normalize(expectedCondition);
    ruleNotes = normalize(ruleNotes);
    sourceName = requireText(sourceName, "sourceName");
    sourceType = requireText(sourceType, "sourceType");
    referenceCode = normalize(referenceCode);
    sourceDescription = normalize(sourceDescription);
    sourceNotes = normalize(sourceNotes);
    country = normalize(country);
    region = normalize(region);
    jurisdictionScope = requireText(jurisdictionScope, "jurisdictionScope");
    jurisdictionNotes = normalize(jurisdictionNotes);
    notes = normalize(notes);
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);
    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return normalized;
  }

  private static String normalize(String value) {
    return Objects.toString(value, "").strip();
  }
}
