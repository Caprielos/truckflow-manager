package it.gabriele.truckflow.domain.waste;

import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;

/** Documento operativo rifiuti, equivalente al controllo FIR nel dominio. */
public record WasteTransportDocument(
    String documentCode,
    String producerCode,
    String carrierCode,
    String destinationFacilityCode,
    WasteEerCode eerCode,
    WasteCategory category,
    Weight quantity,
    boolean signedByProducer,
    boolean signedByCarrier,
    boolean signedByDestination,
    boolean traceabilityDataComplete) {

  public WasteTransportDocument {
    documentCode = normalize(documentCode, "Il codice documento rifiuti è obbligatorio.");
    producerCode = normalize(producerCode, "Il produttore rifiuto è obbligatorio.");
    carrierCode = normalize(carrierCode, "Il trasportatore rifiuto è obbligatorio.");
    destinationFacilityCode =
        normalize(destinationFacilityCode, "Il destinatario rifiuto è obbligatorio.");
    Objects.requireNonNull(eerCode, "Il codice EER/CER è obbligatorio.");
    Objects.requireNonNull(category, "La categoria rifiuto è obbligatoria.");
    Objects.requireNonNull(quantity, "La quantità rifiuto è obbligatoria.");
  }

  public boolean isCompleteAtDeparture() {
    return signedByProducer && signedByCarrier && traceabilityDataComplete;
  }

  public boolean isClosedAtDestination() {
    return isCompleteAtDeparture() && signedByDestination;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
