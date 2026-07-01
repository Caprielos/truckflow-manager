package it.gabriele.truckflow.application.port.in.documents;

import it.gabriele.truckflow.application.command.documents.ArchiveDocumentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Inbound port for archiving documents. */
public interface ArchiveDocumentUseCase extends UseCase<ArchiveDocumentCommand, DocumentResult> {}
