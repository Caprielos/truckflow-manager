package it.gabriele.truckflow.domain.pod;

/** Tipo prova raccolta durante la consegna. */
public enum PodEvidenceType {
  RECIPIENT_SIGNATURE,
  PHOTO_GOODS,
  PHOTO_DAMAGE,
  PHOTO_SEAL,
  GEOLOCATION,
  TIMESTAMP,
  TEMPERATURE_SNAPSHOT,
  RECIPIENT_NAME,
  RESERVATION_NOTE,
  QR_CODE_SCAN,
  BARCODE_SCAN
}
