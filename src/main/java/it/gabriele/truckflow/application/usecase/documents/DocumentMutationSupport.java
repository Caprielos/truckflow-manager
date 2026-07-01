package it.gabriele.truckflow.application.usecase.documents;

import it.gabriele.truckflow.domain.documents.Document;

/** Utility used by document use cases to mutate copies before saving. */
final class DocumentMutationSupport {

  private DocumentMutationSupport() {}

  static Document copyOf(Document document) {
    return new Document(
        document.id(),
        document.code(),
        document.type(),
        document.category(),
        document.status(),
        document.metadata(),
        document.content(),
        document.references(),
        document.notes());
  }
}
