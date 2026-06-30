package it.gabriele.truckflow.domain.compliance;

public final class ComplianceRequirement {

  private final ComplianceRequirementId id;
  private final ComplianceRequirementCode code;
  private String name;
  private String description;
  private ComplianceRequirementStatus status;
  private ComplianceCategory category;
  private ComplianceRequirementType type;
  private ComplianceObligationLevel obligationLevel;
  private ComplianceSeverity severity;
  private ComplianceTarget target;
  private ComplianceRule rule;
  private ComplianceSource source;
  private ComplianceJurisdiction jurisdiction;
  private String notes;

  public ComplianceRequirement(
      ComplianceRequirementId id,
      ComplianceRequirementCode code,
      String name,
      String description,
      ComplianceRequirementStatus status,
      ComplianceCategory category,
      ComplianceRequirementType type,
      ComplianceObligationLevel obligationLevel,
      ComplianceSeverity severity,
      ComplianceTarget target,
      ComplianceRule rule,
      ComplianceSource source,
      ComplianceJurisdiction jurisdiction,
      String notes) {
    this.id = id == null ? ComplianceRequirementId.random() : id;
    this.code = ComplianceValidation.requireNonNull(code, "code");
    this.name = ComplianceValidation.requireText(name, "name");
    this.description = ComplianceValidation.normalize(description);
    this.status = ComplianceValidation.requireNonNull(status, "status");
    this.category = ComplianceValidation.requireNonNull(category, "category");
    this.type = ComplianceValidation.requireNonNull(type, "type");
    this.obligationLevel = ComplianceValidation.requireNonNull(obligationLevel, "obligationLevel");
    this.severity = ComplianceValidation.requireNonNull(severity, "severity");
    this.target = ComplianceValidation.requireNonNull(target, "target");
    this.rule = ComplianceValidation.requireNonNull(rule, "rule");
    this.source = ComplianceValidation.requireNonNull(source, "source");
    this.jurisdiction = ComplianceValidation.requireNonNull(jurisdiction, "jurisdiction");
    this.notes = ComplianceValidation.normalize(notes);
  }

  public ComplianceRequirementId id() {
    return id;
  }

  public ComplianceRequirementCode code() {
    return code;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public ComplianceRequirementStatus status() {
    return status;
  }

  public ComplianceCategory category() {
    return category;
  }

  public ComplianceRequirementType type() {
    return type;
  }

  public ComplianceObligationLevel obligationLevel() {
    return obligationLevel;
  }

  public ComplianceSeverity severity() {
    return severity;
  }

  public ComplianceTarget target() {
    return target;
  }

  public ComplianceRule rule() {
    return rule;
  }

  public ComplianceSource source() {
    return source;
  }

  public ComplianceJurisdiction jurisdiction() {
    return jurisdiction;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == ComplianceRequirementStatus.ACTIVE;
  }

  public boolean isMandatory() {
    return obligationLevel == ComplianceObligationLevel.MANDATORY;
  }

  public boolean isCritical() {
    return severity == ComplianceSeverity.CRITICAL;
  }

  public boolean appliesTo(ComplianceTargetType targetType) {
    return target.appliesTo(targetType);
  }

  public void rename(String name, String description) {
    this.name = ComplianceValidation.requireText(name, "name");
    this.description = ComplianceValidation.normalize(description);
  }

  public void reclassify(ComplianceCategory category, ComplianceRequirementType type) {
    ComplianceCategory updatedCategory = ComplianceValidation.requireNonNull(category, "category");
    ComplianceRequirementType updatedType = ComplianceValidation.requireNonNull(type, "type");

    this.category = updatedCategory;
    this.type = updatedType;
  }

  public void changeImportance(
      ComplianceObligationLevel obligationLevel, ComplianceSeverity severity) {
    ComplianceObligationLevel updatedObligationLevel =
        ComplianceValidation.requireNonNull(obligationLevel, "obligationLevel");
    ComplianceSeverity updatedSeverity = ComplianceValidation.requireNonNull(severity, "severity");

    this.obligationLevel = updatedObligationLevel;
    this.severity = updatedSeverity;
  }

  public void replaceTarget(ComplianceTarget target) {
    this.target = ComplianceValidation.requireNonNull(target, "target");
  }

  public void replaceRule(ComplianceRule rule) {
    this.rule = ComplianceValidation.requireNonNull(rule, "rule");
  }

  public void replaceSource(ComplianceSource source) {
    this.source = ComplianceValidation.requireNonNull(source, "source");
  }

  public void replaceJurisdiction(ComplianceJurisdiction jurisdiction) {
    this.jurisdiction = ComplianceValidation.requireNonNull(jurisdiction, "jurisdiction");
  }

  public void updateNotes(String notes) {
    this.notes = ComplianceValidation.normalize(notes);
  }

  public void activate() {
    status = ComplianceRequirementStatus.ACTIVE;
  }

  public void suspend() {
    status = ComplianceRequirementStatus.SUSPENDED;
  }

  public void archive() {
    status = ComplianceRequirementStatus.ARCHIVED;
  }

  public void discontinue() {
    status = ComplianceRequirementStatus.DISCONTINUED;
  }
}
