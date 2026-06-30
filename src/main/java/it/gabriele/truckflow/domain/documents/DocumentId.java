package it.gabriele.truckflow.domain.documents;

import java.util.UUID;

public record DocumentId(UUID value) {

  public DocumentId {
    value = DocumentValidation.requireNonNull(value, "value");
  }

  public static DocumentId random() {
    return new DocumentId(UUID.randomUUID());
  }
}
