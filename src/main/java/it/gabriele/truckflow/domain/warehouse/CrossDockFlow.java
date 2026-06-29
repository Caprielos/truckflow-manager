package it.gabriele.truckflow.domain.warehouse;

import java.time.Duration;
import java.time.LocalDateTime;

/** Flusso cross-docking: merce ricevuta e rilanciata senza stoccaggio lungo. */
public record CrossDockFlow(
    String flowCode,
    String inboundShipmentCode,
    String outboundShipmentCode,
    LocalDateTime receivedAt,
    LocalDateTime dispatchedAt,
    int handlingUnits) {

  public CrossDockFlow {
    flowCode = normalize(flowCode, "Il codice flusso cross-dock è obbligatorio.");
    inboundShipmentCode = normalize(inboundShipmentCode, "La spedizione inbound è obbligatoria.");
    outboundShipmentCode =
        normalize(outboundShipmentCode, "La spedizione outbound è obbligatoria.");
    if (receivedAt == null) {
      throw new IllegalArgumentException("La ricezione è obbligatoria.");
    }
    if (dispatchedAt != null && dispatchedAt.isBefore(receivedAt)) {
      throw new IllegalArgumentException("La partenza non può precedere la ricezione.");
    }
    if (handlingUnits <= 0) {
      throw new IllegalArgumentException("Le unità movimentate devono essere positive.");
    }
  }

  public Duration dwellTime() {
    if (dispatchedAt == null) {
      return Duration.ZERO;
    }
    return Duration.between(receivedAt, dispatchedAt);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
