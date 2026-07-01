package it.gabriele.truckflow.application.usecase.documents;

import it.gabriele.truckflow.application.command.documents.ActivateDocumentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.documents.ActivateDocumentUseCase;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Application service that activates existing documents. */
public final class ActivateDocumentService implements ActivateDocumentUseCase {

  private final DocumentRepository documentRepository;

  public ActivateDocumentService(DocumentRepository documentRepository) {
    UseCaseValidationException.requireNonNull(documentRepository, "documentRepository");
    this.documentRepository = documentRepository;
  }

  @Override
  public DocumentResult execute(ActivateDocumentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var document =
        documentRepository
            .findById(command.documentId())
            .orElseThrow(() -> new ResourceNotFoundException("Document", command.documentId()));

    var updatedDocument = DocumentMutationSupport.copyOf(document);
    updatedDocument.activate();

    return DocumentResult.from(documentRepository.save(updatedDocument));
  }
}
