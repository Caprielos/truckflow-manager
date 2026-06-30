package it.gabriele.truckflow.domain.compliance;

public record ComplianceRule(
    String title, String statement, String expectedCondition, String notes) {

  public ComplianceRule {
    title = ComplianceValidation.requireText(title, "title");
    statement = ComplianceValidation.requireText(statement, "statement");
    expectedCondition = ComplianceValidation.normalize(expectedCondition);
    notes = ComplianceValidation.normalize(notes);
  }
}
