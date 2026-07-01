package it.gabriele.truckflow.application.port.in.documents;

import it.gabriele.truckflow.application.command.documents.ActivateDocumentCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.documents.DocumentResult;

/** Inbound port for activating documents. */
public interface ActivateDocumentUseCase extends UseCase<ActivateDocumentCommand, DocumentResult> {}
