package it.gabriele.truckflow.shipment;

public class ShipmentStatusRules {

    public boolean canMoveTo(ShipmentStatus currentStatus, ShipmentStatus nextStatus) {
        if (currentStatus == null || nextStatus == null) {
            return false;
        }

        return switch (currentStatus) {
            case CREATED -> nextStatus == ShipmentStatus.ASSIGNED
                    || nextStatus == ShipmentStatus.CANCELLED;

            case ASSIGNED -> nextStatus == ShipmentStatus.IN_TRANSIT
                    || nextStatus == ShipmentStatus.CANCELLED;

            case IN_TRANSIT -> nextStatus == ShipmentStatus.DELIVERED;

            case DELIVERED, CANCELLED -> false;
        };
    }
}
