package it.gabriele.truckflow.infrastructure.repository.documents;

import it.gabriele.truckflow.infrastructure.repository.file.FileRecordCodec;
import java.util.List;

/** Encodes document persistence records to the Punto 7F file format. */
final class DocumentFileRecordCodec implements FileRecordCodec<DocumentPersistenceRecord> {

  private static final int FIELD_COUNT = 15;

  @Override
  public int fieldCount() {
    return FIELD_COUNT;
  }

  @Override
  public List<String> encode(DocumentPersistenceRecord record) {
    return List.of(
        record.id(),
        record.code(),
        record.type(),
        record.category(),
        record.status(),
        record.title(),
        record.author(),
        record.description(),
        record.version(),
        record.tags(),
        record.body(),
        record.summary(),
        record.contentNotes(),
        record.references(),
        record.notes());
  }

  @Override
  public DocumentPersistenceRecord decode(List<String> fields) {
    return new DocumentPersistenceRecord(
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
        fields.get(14));
  }
}
