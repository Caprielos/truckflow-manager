package it.gabriele.truckflow.application.usecase.documents;

import it.gabriele.truckflow.application.command.documents.FindDocumentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.documents.FindDocumentUseCase;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Application service that finds documents. */
public final class FindDocumentService implements FindDocumentUseCase {

  private final DocumentRepository documentRepository;

  public FindDocumentService(DocumentRepository documentRepository) {
    UseCaseValidationException.requireNonNull(documentRepository, "documentRepository");
    this.documentRepository = documentRepository;
  }

  @Override
  public DocumentResult execute(FindDocumentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return documentRepository
        .findById(command.documentId())
        .map(DocumentResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Document", command.documentId()));
  }
}
