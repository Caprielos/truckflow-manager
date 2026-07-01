package it.gabriele.truckflow.application.usecase.documents;

import it.gabriele.truckflow.application.command.documents.ArchiveDocumentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.documents.ArchiveDocumentUseCase;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Application service that archives existing documents. */
public final class ArchiveDocumentService implements ArchiveDocumentUseCase {

  private final DocumentRepository documentRepository;

  public ArchiveDocumentService(DocumentRepository documentRepository) {
    UseCaseValidationException.requireNonNull(documentRepository, "documentRepository");
    this.documentRepository = documentRepository;
  }

  @Override
  public DocumentResult execute(ArchiveDocumentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var document =
        documentRepository
            .findById(command.documentId())
            .orElseThrow(() -> new ResourceNotFoundException("Document", command.documentId()));

    var updatedDocument = DocumentMutationSupport.copyOf(document);
    updatedDocument.archive();

    return DocumentResult.from(documentRepository.save(updatedDocument));
  }
}
