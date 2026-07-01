package it.gabriele.truckflow.application.result.documents;

import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;

/** Result returned by document use cases. */
public record DocumentResult(
    DocumentId id,
    DocumentCode code,
    DocumentType type,
    DocumentCategory category,
    DocumentStatus status,
    String title,
    boolean hasLogicalContent,
    int referenceCount)
    implements ApplicationResult {

  public static DocumentResult from(Document document) {
    return new DocumentResult(
        document.id(),
        document.code(),
        document.type(),
        document.category(),
        document.status(),
        document.metadata().title(),
        document.hasLogicalContent(),
        document.references().size());
  }
}
