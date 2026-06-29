package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shipment.Shipment;

public interface CreateShipmentFromAcceptedOrderUseCase {

    Shipment handle(Command command);

    record Command(String shipmentNumber, String orderNumber, Notes notes) {
    }
}
