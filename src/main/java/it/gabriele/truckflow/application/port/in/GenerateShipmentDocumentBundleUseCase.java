package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.document.ShipmentDocumentBundle;
import it.gabriele.truckflow.domain.document.TransportDocument;
import it.gabriele.truckflow.domain.document.TransportDocumentType;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.List;
import java.util.Set;

public interface GenerateShipmentDocumentBundleUseCase {

    ShipmentDocumentBundle handle(Command command);

    record Command(
            String bundleCode,
            String shipmentNumber,
            Set<TransportDocumentType> requiredTypes,
            List<TransportDocument> documents,
            Notes notes
    ) {
    }
}
