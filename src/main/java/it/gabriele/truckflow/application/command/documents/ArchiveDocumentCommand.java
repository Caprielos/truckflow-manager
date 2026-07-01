package it.gabriele.truckflow.application.command.documents;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.documents.DocumentId;

/** Command used to archive an existing document. */
public record ArchiveDocumentCommand(DocumentId documentId) implements ApplicationCommand {

  public ArchiveDocumentCommand {
    UseCaseValidationException.requireNonNull(documentId, "documentId");
  }
}
