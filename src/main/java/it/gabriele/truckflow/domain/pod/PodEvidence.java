package it.gabriele.truckflow.domain.pod;

import java.time.LocalDateTime;

/** Evidenza singola del POD: foto, firma, geolocalizzazione o dato tecnico. */
public record PodEvidence(
    String evidenceCode,
    PodEvidenceType evidenceType,
    String reference,
    LocalDateTime capturedAt,
    String capturedBy) {

  public PodEvidence {
    evidenceCode = normalize(evidenceCode, "Il codice evidenza è obbligatorio.");
    if (evidenceType == null) {
      throw new IllegalArgumentException("Il tipo evidenza è obbligatorio.");
    }
    reference = normalize(reference, "Il riferimento evidenza è obbligatorio.");
    if (capturedAt == null) {
      throw new IllegalArgumentException("La data cattura è obbligatoria.");
    }
    capturedBy = normalize(capturedBy, "L'operatore che ha catturato l'evidenza è obbligatorio.");
  }

  public boolean isPhotographicEvidence() {
    return evidenceType == PodEvidenceType.PHOTO_GOODS
        || evidenceType == PodEvidenceType.PHOTO_DAMAGE
        || evidenceType == PodEvidenceType.PHOTO_SEAL;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim();
  }
}
