package it.gabriele.truckflow.application.command.documents;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.documents.DocumentId;

/** Command used to find an existing document by identity. */
public record FindDocumentCommand(DocumentId documentId) implements ApplicationCommand {

  public FindDocumentCommand {
    UseCaseValidationException.requireNonNull(documentId, "documentId");
  }
}
