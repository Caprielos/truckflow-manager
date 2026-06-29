package it.gabriele.truckflow.application.usecase.shipment;

import it.gabriele.truckflow.application.port.in.CreateShipmentFromAcceptedOrderUseCase;
import it.gabriele.truckflow.application.port.out.ShipmentRepository;
import it.gabriele.truckflow.application.port.out.TransportOrderRepository;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.shipment.Shipment;
import java.util.Objects;

/** Caso d'uso: trasformare un ordine accettato in una spedizione. */
public final class DefaultCreateShipmentFromAcceptedOrderUseCase
    implements CreateShipmentFromAcceptedOrderUseCase {

  private final TransportOrderRepository orderRepository;
  private final ShipmentRepository shipmentRepository;

  public DefaultCreateShipmentFromAcceptedOrderUseCase(
      TransportOrderRepository orderRepository, ShipmentRepository shipmentRepository) {
    this.orderRepository =
        Objects.requireNonNull(orderRepository, "Il repository ordini è obbligatorio.");
    this.shipmentRepository =
        Objects.requireNonNull(shipmentRepository, "Il repository spedizioni è obbligatorio.");
  }

  @Override
  public Shipment handle(Command command) {
    Objects.requireNonNull(command, "Il comando creazione spedizione è obbligatorio.");
    TransportOrder order =
        orderRepository.getRequired(command.orderNumber(), "Ordine di trasporto");
    Shipment shipment =
        Shipment.fromAcceptedOrder(command.shipmentNumber(), order, command.notes());
    shipmentRepository.save(shipment);
    return shipment;
  }
}
