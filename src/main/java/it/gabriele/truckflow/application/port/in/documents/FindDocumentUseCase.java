package it.gabriele.truckflow.application.port.in.documents;

import it.gabriele.truckflow.application.command.documents.FindDocumentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Inbound port for finding documents. */
public interface FindDocumentUseCase extends UseCase<FindDocumentCommand, DocumentResult> {}
