package it.gabriele.truckflow.domain.shipment;

import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.order.TransportOrderStatus;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Rappresenta una spedizione operativa generata da un ordine di trasporto accettato.
 */
public final class Shipment {

    private static final int MAX_SHIPMENT_NUMBER_LENGTH = 50;

    private final String shipmentNumber;
    private final TransportOrder transportOrder;
    private final ShipmentStatus status;
    private final Notes notes;

    private Shipment(
            String shipmentNumber,
            TransportOrder transportOrder,
            ShipmentStatus status,
            Notes notes
    ) {
        this.shipmentNumber = validateShipmentNumber(shipmentNumber);

        if (transportOrder == null) {
            throw new IllegalArgumentException("L'ordine di trasporto è obbligatorio.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato della spedizione è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note della spedizione sono obbligatorie.");
        }

        this.transportOrder = transportOrder;
        this.status = status;
        this.notes = notes;
    }

    public static Shipment fromAcceptedOrder(
            String shipmentNumber,
            TransportOrder transportOrder,
            Notes notes
    ) {
        if (transportOrder == null) {
            throw new IllegalArgumentException("L'ordine di trasporto è obbligatorio.");
        }

        if (transportOrder.getStatus() != TransportOrderStatus.ACCEPTED) {
            throw new IllegalArgumentException("Una spedizione può essere creata solo da un ordine accettato.");
        }

        return new Shipment(shipmentNumber, transportOrder, ShipmentStatus.CREATED, notes);
    }

    private static String validateShipmentNumber(String shipmentNumber) {
        if (shipmentNumber == null) {
            throw new IllegalArgumentException("Il numero spedizione è obbligatorio.");
        }

        String normalizedShipmentNumber = shipmentNumber.trim().toUpperCase();

        if (normalizedShipmentNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero spedizione non può essere vuoto.");
        }

        if (normalizedShipmentNumber.length() > MAX_SHIPMENT_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero spedizione non può superare " + MAX_SHIPMENT_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedShipmentNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero spedizione può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedShipmentNumber;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public TransportOrder getTransportOrder() {
        return transportOrder;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public Notes getNotes() {
        return notes;
    }

    public CustomerAccount getCustomerAccount() {
        return transportOrder.getCustomerAccount();
    }

    public CargoLoad getCargoLoad() {
        return transportOrder.getCargoLoad();
    }

    public Facility getPickupFacility() {
        return transportOrder.getPickupFacility();
    }

    public Facility getDeliveryFacility() {
        return transportOrder.getDeliveryFacility();
    }

    public boolean isInternational() {
        return transportOrder.isInternational();
    }

    public boolean requiresTemperatureControlledTransport() {
        return transportOrder.requiresTemperatureControlledTransport();
    }

    public boolean containsHazardousMaterial() {
        return transportOrder.containsHazardousMaterial();
    }

    public boolean canBePlanned() {
        return status == ShipmentStatus.CREATED;
    }

    public boolean canBeDispatched() {
        return status == ShipmentStatus.PLANNED;
    }

    public boolean canBeMarkedInTransit() {
        return status == ShipmentStatus.DISPATCHED;
    }

    public boolean canBeDelivered() {
        return status == ShipmentStatus.IN_TRANSIT;
    }

    public boolean canBeCancelled() {
        return !status.isTerminal();
    }

    public Shipment plan() {
        if (!canBePlanned()) {
            throw new IllegalStateException("La spedizione non può essere pianificata.");
        }

        return withStatus(ShipmentStatus.PLANNED);
    }

    public Shipment dispatch() {
        if (!canBeDispatched()) {
            throw new IllegalStateException("La spedizione non può essere spedita.");
        }

        return withStatus(ShipmentStatus.DISPATCHED);
    }

    public Shipment markInTransit() {
        if (!canBeMarkedInTransit()) {
            throw new IllegalStateException("La spedizione non può essere messa in transito.");
        }

        return withStatus(ShipmentStatus.IN_TRANSIT);
    }

    public Shipment deliver() {
        if (!canBeDelivered()) {
            throw new IllegalStateException("La spedizione non può essere consegnata.");
        }

        return withStatus(ShipmentStatus.DELIVERED);
    }

    public Shipment cancel() {
        if (!canBeCancelled()) {
            throw new IllegalStateException("La spedizione non può essere cancellata.");
        }

        return withStatus(ShipmentStatus.CANCELLED);
    }

    private Shipment withStatus(ShipmentStatus newStatus) {
        return new Shipment(shipmentNumber, transportOrder, newStatus, notes);
    }

    public String formatSingleLine() {
        return shipmentNumber + " - " + transportOrder.getOrderNumber() + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment shipment)) return false;
        return shipmentNumber.equals(shipment.shipmentNumber)
                && transportOrder.equals(shipment.transportOrder)
                && status == shipment.status
                && notes.equals(shipment.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentNumber, transportOrder, status, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
