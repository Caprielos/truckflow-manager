package it.gabriele.truckflow.domain.pod;

import java.time.LocalDateTime;
import java.util.List;

/** Prova di consegna digitale con firma, foto, timestamp e posizione. */
public record DigitalProofOfDelivery(
    String podCode,
    String shipmentCode,
    String missionCode,
    String recipientName,
    LocalDateTime deliveredAt,
    Double latitude,
    Double longitude,
    PodStatus status,
    List<PodEvidence> evidences,
    boolean hasReservations) {

  public DigitalProofOfDelivery {
    podCode = normalize(podCode, "Il codice POD è obbligatorio.");
    shipmentCode = normalize(shipmentCode, "Il codice spedizione è obbligatorio.");
    missionCode = normalize(missionCode, "Il codice missione è obbligatorio.");
    recipientName = normalize(recipientName, "Il nome ricevente è obbligatorio.");
    if (deliveredAt == null) {
      throw new IllegalArgumentException("La data consegna è obbligatoria.");
    }
    if (status == null) {
      throw new IllegalArgumentException("Lo stato POD è obbligatorio.");
    }
    evidences = evidences == null ? List.of() : List.copyOf(evidences);
  }

  public boolean hasEvidence(PodEvidenceType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo evidenza è obbligatorio.");
    }
    return evidences.stream().anyMatch(evidence -> evidence.evidenceType() == type);
  }

  public boolean hasGeoLocation() {
    return latitude != null && longitude != null;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
