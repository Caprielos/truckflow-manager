package it.gabriele.truckflow.infrastructure.repository.compliance;

import it.gabriele.truckflow.infrastructure.repository.file.FileRecordCodec;
import java.util.List;

/** Encodes compliance requirement persistence records to the Punto 7F file format. */
final class ComplianceRequirementFileRecordCodec
    implements FileRecordCodec<ComplianceRequirementPersistenceRecord> {

  private static final int FIELD_COUNT = 25;

  @Override
  public int fieldCount() {
    return FIELD_COUNT;
  }

  @Override
  public List<String> encode(ComplianceRequirementPersistenceRecord record) {
    return List.of(
        record.id(),
        record.code(),
        record.name(),
        record.description(),
        record.status(),
        record.category(),
        record.type(),
        record.obligationLevel(),
        record.severity(),
        record.targetType(),
        record.targetNotes(),
        record.ruleTitle(),
        record.ruleStatement(),
        record.expectedCondition(),
        record.ruleNotes(),
        record.sourceName(),
        record.sourceType(),
        record.referenceCode(),
        record.sourceDescription(),
        record.sourceNotes(),
        record.country(),
        record.region(),
        record.jurisdictionScope(),
        record.jurisdictionNotes(),
        record.notes());
  }

  @Override
  public ComplianceRequirementPersistenceRecord decode(List<String> fields) {
    return new ComplianceRequirementPersistenceRecord(
        fields.get(0),
        fields.get(1),
        fields.get(2),
        fields.get(3),
        fields.get(4),
        fields.get(5),
        fields.get(6),
        fields.get(7),
        fields.get(8),
        fields.get(9),
        fields.get(10),
        fields.get(11),
        fields.get(12),
        fields.get(13),
        fields.get(14),
        fields.get(15),
        fields.get(16),
        fields.get(17),
        fields.get(18),
        fields.get(19),
        fields.get(20),
        fields.get(21),
        fields.get(22),
        fields.get(23),
        fields.get(24));
  }
}
