package it.gabriele.truckflow.infrastructure.repository.file;

import java.util.List;

/** Encodes and decodes one technical file persistence record. */
public interface FileRecordCodec<R> {

  /** Returns the expected number of file fields for this record type. */
  int fieldCount();

  /** Converts a persistence record into ordered text fields. */
  List<String> encode(R record);

  /** Rebuilds a persistence record from ordered text fields. */
  R decode(List<String> fields);
}
