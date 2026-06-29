package it.gabriele.truckflow.application.port.in.document;

import it.gabriele.truckflow.domain.document.DocumentVersion;

public interface RegisterDocumentVersionUseCase {

  DocumentVersion handle(Command command);

  record Command(DocumentVersion version) {}
}
