package it.gabriele.truckflow.deadlineservice.domain;

import java.util.EnumSet;
import java.util.Set;

/** Definisce come un elemento deve essere governato dal futuro servizio scadenze. */
public record ManagedElementDefinition(
    ManagedElementCode code,
    ManagedElementCategory category,
    ManagedElementOwnerType ownerType,
    Set<DeadlineRuleSourceType> expectedSources,
    boolean dynamicByConfiguredCountry,
    boolean dynamicByManufacturerModel,
    boolean requiresOperationalWorkflow,
    boolean requiresContinuousMonitoring,
    boolean requiresSecurityControl,
    boolean canBlockOperations) {

  public ManagedElementDefinition {
    if (code == null) {
      throw new IllegalArgumentException("Il codice elemento è obbligatorio.");
    }
    if (category == null) {
      throw new IllegalArgumentException("La categoria elemento è obbligatoria.");
    }
    if (ownerType == null) {
      throw new IllegalArgumentException("Il proprietario dell'elemento è obbligatorio.");
    }
    if (expectedSources == null || expectedSources.isEmpty()) {
      throw new IllegalArgumentException("Almeno una fonte regola deve essere prevista.");
    }
    expectedSources = Set.copyOf(expectedSources);
  }

  public boolean hasLegalSource() {
    return expectedSources.contains(DeadlineRuleSourceType.EU_LAW)
        || expectedSources.contains(DeadlineRuleSourceType.NATIONAL_LAW);
  }

  public boolean hasTechnicalSource() {
    return expectedSources.contains(DeadlineRuleSourceType.MANUFACTURER_RULEBOOK);
  }

  public boolean hasOperationalSource() {
    return expectedSources.contains(DeadlineRuleSourceType.INTERNAL_OPERATIONAL_POLICY)
        || expectedSources.contains(DeadlineRuleSourceType.CUSTOMER_CONTRACT);
  }

  public boolean hasMonitoringSource() {
    return expectedSources.contains(DeadlineRuleSourceType.TELEMATICS_EVENT)
        || expectedSources.contains(DeadlineRuleSourceType.SECURITY_EVENT);
  }

  static ManagedElementDefinition legal(
      ManagedElementCode code, ManagedElementCategory category, ManagedElementOwnerType ownerType) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(DeadlineRuleSourceType.EU_LAW, DeadlineRuleSourceType.NATIONAL_LAW),
        true,
        false,
        true,
        false,
        true,
        true);
  }

  static ManagedElementDefinition technical(
      ManagedElementCode code, ManagedElementCategory category, ManagedElementOwnerType ownerType) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(DeadlineRuleSourceType.MANUFACTURER_RULEBOOK),
        false,
        true,
        true,
        false,
        false,
        false);
  }

  static ManagedElementDefinition operational(
      ManagedElementCode code,
      ManagedElementCategory category,
      ManagedElementOwnerType ownerType,
      boolean requiresSecurityControl,
      boolean canBlockOperations) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(
            DeadlineRuleSourceType.INTERNAL_OPERATIONAL_POLICY,
            DeadlineRuleSourceType.CUSTOMER_CONTRACT),
        false,
        false,
        true,
        false,
        requiresSecurityControl,
        canBlockOperations);
  }

  static ManagedElementDefinition monitored(
      ManagedElementCode code,
      ManagedElementCategory category,
      ManagedElementOwnerType ownerType,
      boolean requiresSecurityControl,
      boolean canBlockOperations) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(
            DeadlineRuleSourceType.TELEMATICS_EVENT,
            DeadlineRuleSourceType.INTERNAL_OPERATIONAL_POLICY),
        false,
        false,
        true,
        true,
        requiresSecurityControl,
        canBlockOperations);
  }

  static ManagedElementDefinition security(
      ManagedElementCode code,
      ManagedElementCategory category,
      ManagedElementOwnerType ownerType,
      boolean requiresContinuousMonitoring,
      boolean canBlockOperations) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(
            DeadlineRuleSourceType.SECURITY_EVENT,
            DeadlineRuleSourceType.INTERNAL_OPERATIONAL_POLICY,
            DeadlineRuleSourceType.CUSTOMER_CONTRACT),
        false,
        false,
        true,
        requiresContinuousMonitoring,
        true,
        canBlockOperations);
  }

  static ManagedElementDefinition legalAndTechnical(
      ManagedElementCode code, ManagedElementCategory category, ManagedElementOwnerType ownerType) {
    return new ManagedElementDefinition(
        code,
        category,
        ownerType,
        EnumSet.of(
            DeadlineRuleSourceType.EU_LAW,
            DeadlineRuleSourceType.NATIONAL_LAW,
            DeadlineRuleSourceType.MANUFACTURER_RULEBOOK),
        true,
        true,
        true,
        true,
        true,
        true);
  }
}
