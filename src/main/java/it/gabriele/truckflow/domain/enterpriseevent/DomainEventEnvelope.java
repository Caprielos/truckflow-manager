package it.gabriele.truckflow.domain.enterpriseevent;

import java.time.LocalDateTime;
import java.util.Map;

/** Busta evento domain per audit, automazioni e futuri messaggi asincroni. */
public record DomainEventEnvelope(
    String eventId,
    DomainEventType eventType,
    String aggregateType,
    String aggregateCode,
    LocalDateTime occurredAt,
    String actorCode,
    String reason,
    Map<String, String> attributes) {

  public DomainEventEnvelope {
    eventId = normalize(eventId, "L'id evento è obbligatorio.");
    if (eventType == null) {
      throw new IllegalArgumentException("Il tipo evento è obbligatorio.");
    }
    aggregateType = normalize(aggregateType, "Il tipo aggregate è obbligatorio.");
    aggregateCode = normalize(aggregateCode, "Il codice aggregate è obbligatorio.");
    if (occurredAt == null) {
      throw new IllegalArgumentException("La data evento è obbligatoria.");
    }
    actorCode = normalize(actorCode, "L'attore evento è obbligatorio.");
    reason = reason == null ? "" : reason.trim();
    attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
  }

  public boolean hasReason() {
    return !reason.isBlank();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
