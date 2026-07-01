package it.gabriele.truckflow.application.command.documents;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.documents.DocumentId;

/** Command used to activate an existing document. */
public record ActivateDocumentCommand(DocumentId documentId) implements ApplicationCommand {

  public ActivateDocumentCommand {
    UseCaseValidationException.requireNonNull(documentId, "documentId");
  }
}
