package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.waste.WasteTransportDocument;

public interface RegisterWasteTransportDocumentUseCase {
  WasteTransportDocument handle(Command command);

  record Command(WasteTransportDocument document) {}
}
