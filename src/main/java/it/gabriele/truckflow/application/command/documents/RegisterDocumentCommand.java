package it.gabriele.truckflow.application.command.documents;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentContent;
import it.gabriele.truckflow.domain.documents.DocumentMetadata;
import it.gabriele.truckflow.domain.documents.DocumentReference;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;
import java.util.Set;

/** Command used to register a new logical business document. */
public record RegisterDocumentCommand(
    DocumentCode code,
    DocumentType type,
    DocumentCategory category,
    DocumentStatus status,
    DocumentMetadata metadata,
    DocumentContent content,
    Set<DocumentReference> references,
    String notes)
    implements ApplicationCommand {

  public RegisterDocumentCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNonNull(type, "type");
    UseCaseValidationException.requireNonNull(category, "category");
    UseCaseValidationException.requireNonNull(status, "status");
    UseCaseValidationException.requireNonNull(metadata, "metadata");

    if (references == null || references.isEmpty()) {
      references = Set.of();
    } else {
      if (references.stream().anyMatch(reference -> reference == null)) {
        throw new UseCaseValidationException("references must not contain null elements");
      }
      references = Set.copyOf(references);
    }
  }
}
