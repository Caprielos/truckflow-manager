package it.gabriele.truckflow.domain.reporting;

/** Tipo di report disponibile nel dominio. */
public enum ReportType {
  OPERATIONS(true, false, false, false),
  FINANCIAL(false, true, false, false),
  FLEET(true, false, false, false),
  DRIVER(true, false, false, false),
  CUSTOMER(true, false, false, false),
  SUSTAINABILITY(false, false, false, true),
  COMPLIANCE(false, false, true, false),
  CLAIMS(true, false, false, false),
  DOCUMENTS(true, false, true, false);

  private final boolean operationalReport;
  private final boolean financialReport;
  private final boolean complianceReport;
  private final boolean sustainabilityReport;

  ReportType(
      boolean operationalReport,
      boolean financialReport,
      boolean complianceReport,
      boolean sustainabilityReport) {
    this.operationalReport = operationalReport;
    this.financialReport = financialReport;
    this.complianceReport = complianceReport;
    this.sustainabilityReport = sustainabilityReport;
  }

  public boolean isOperationalReport() {
    return operationalReport;
  }

  public boolean isFinancialReport() {
    return financialReport;
  }

  public boolean isComplianceReport() {
    return complianceReport;
  }

  public boolean isSustainabilityReport() {
    return sustainabilityReport;
  }

  public boolean requiresRestrictedAccess() {
    return financialReport || complianceReport;
  }
}
