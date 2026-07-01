package it.gabriele.truckflow.application.usecase.documents;

import it.gabriele.truckflow.application.command.documents.RegisterDocumentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.documents.RegisterDocumentUseCase;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.result.documents.DocumentResult;
import it.gabriele.truckflow.domain.documents.Document;

/** Application service that registers logical business documents. */
public final class RegisterDocumentService implements RegisterDocumentUseCase {

  private final DocumentRepository documentRepository;

  public RegisterDocumentService(DocumentRepository documentRepository) {
    UseCaseValidationException.requireNonNull(documentRepository, "documentRepository");
    this.documentRepository = documentRepository;
  }

  @Override
  public DocumentResult execute(RegisterDocumentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (documentRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Document", command.code().value());
    }

    var document =
        new Document(
            null,
            command.code(),
            command.type(),
            command.category(),
            command.status(),
            command.metadata(),
            command.content(),
            command.references(),
            command.notes());

    return DocumentResult.from(documentRepository.save(document));
  }
}
