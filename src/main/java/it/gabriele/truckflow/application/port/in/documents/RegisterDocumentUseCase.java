package it.gabriele.truckflow.application.port.in.documents;

import it.gabriele.truckflow.application.command.documents.RegisterDocumentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Inbound port for registering documents. */
public interface RegisterDocumentUseCase extends UseCase<RegisterDocumentCommand, DocumentResult> {}
