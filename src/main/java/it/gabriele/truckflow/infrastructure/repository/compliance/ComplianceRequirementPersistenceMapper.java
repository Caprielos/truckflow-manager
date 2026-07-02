package it.gabriele.truckflow.infrastructure.repository.compliance;

import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdictionScope;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirement;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceSourceType;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;
import it.gabriele.truckflow.infrastructure.exception.MappingException;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import java.util.Objects;
import java.util.UUID;

/** Maps compliance requirements to and from Punto 7F file persistence records. */
public final class ComplianceRequirementPersistenceMapper
    implements PersistenceMapper<ComplianceRequirement, ComplianceRequirementPersistenceRecord> {

  @Override
  public ComplianceRequirementPersistenceRecord toPersistence(ComplianceRequirement domainModel) {
    Objects.requireNonNull(domainModel, "domainModel must not be null");

    ComplianceTarget target = domainModel.target();
    ComplianceRule rule = domainModel.rule();
    ComplianceSource source = domainModel.source();
    ComplianceJurisdiction jurisdiction = domainModel.jurisdiction();

    return new ComplianceRequirementPersistenceRecord(
        domainModel.id().value().toString(),
        domainModel.code().value(),
        domainModel.name(),
        domainModel.description(),
        domainModel.status().name(),
        domainModel.category().name(),
        domainModel.type().name(),
        domainModel.obligationLevel().name(),
        domainModel.severity().name(),
        target.targetType().name(),
        target.notes(),
        rule.title(),
        rule.statement(),
        rule.expectedCondition(),
        rule.notes(),
        source.sourceName(),
        source.sourceType().name(),
        source.referenceCode(),
        source.description(),
        source.notes(),
        jurisdiction.countryValue(),
        jurisdiction.regionValue(),
        jurisdiction.scope().name(),
        jurisdiction.notes(),
        domainModel.notes());
  }

  @Override
  public ComplianceRequirement toDomain(ComplianceRequirementPersistenceRecord persistenceModel) {
    Objects.requireNonNull(persistenceModel, "persistenceModel must not be null");

    try {
      return new ComplianceRequirement(
          new ComplianceRequirementId(UUID.fromString(persistenceModel.id())),
          ComplianceRequirementCode.of(persistenceModel.code()),
          persistenceModel.name(),
          persistenceModel.description(),
          ComplianceRequirementStatus.valueOf(persistenceModel.status()),
          ComplianceCategory.valueOf(persistenceModel.category()),
          ComplianceRequirementType.valueOf(persistenceModel.type()),
          ComplianceObligationLevel.valueOf(persistenceModel.obligationLevel()),
          ComplianceSeverity.valueOf(persistenceModel.severity()),
          targetFrom(persistenceModel),
          ruleFrom(persistenceModel),
          sourceFrom(persistenceModel),
          jurisdictionFrom(persistenceModel),
          persistenceModel.notes());
    } catch (IllegalArgumentException exception) {
      throw new MappingException(
          "Unable to rebuild ComplianceRequirement from persistence record.", exception);
    }
  }

  private static ComplianceTarget targetFrom(ComplianceRequirementPersistenceRecord record) {
    return new ComplianceTarget(
        ComplianceTargetType.valueOf(record.targetType()), record.targetNotes());
  }

  private static ComplianceRule ruleFrom(ComplianceRequirementPersistenceRecord record) {
    return new ComplianceRule(
        record.ruleTitle(), record.ruleStatement(), record.expectedCondition(), record.ruleNotes());
  }

  private static ComplianceSource sourceFrom(ComplianceRequirementPersistenceRecord record) {
    return new ComplianceSource(
        record.sourceName(),
        ComplianceSourceType.valueOf(record.sourceType()),
        record.referenceCode(),
        record.sourceDescription(),
        record.sourceNotes());
  }

  private static ComplianceJurisdiction jurisdictionFrom(
      ComplianceRequirementPersistenceRecord record) {
    return new ComplianceJurisdiction(
        record.country(),
        record.region(),
        ComplianceJurisdictionScope.valueOf(record.jurisdictionScope()).name(),
        record.jurisdictionNotes());
  }
}
