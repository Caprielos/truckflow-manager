package it.gabriele.truckflow.domain.shipment;

/** Contiene regole di dominio relative alle spedizioni. */
public final class ShipmentRules {

  private ShipmentRules() {}

  public static boolean canBePlanned(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.canBePlanned();
  }

  public static boolean canBeDispatched(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.canBeDispatched();
  }

  public static boolean canBeMarkedInTransit(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.canBeMarkedInTransit();
  }

  public static boolean canBeDelivered(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.canBeDelivered();
  }

  public static boolean canBeCancelled(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.canBeCancelled();
  }

  public static boolean requiresSpecialHandling(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.requiresTemperatureControlledTransport()
        || shipment.containsHazardousMaterial()
        || shipment.isInternational();
  }

  public static boolean isCompleted(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.getStatus() == ShipmentStatus.DELIVERED;
  }

  public static boolean isTerminal(Shipment shipment) {
    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return shipment.getStatus().isTerminal();
  }
}
