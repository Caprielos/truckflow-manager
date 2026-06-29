package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Bolla/DDT strutturata, non solo documento generico. */
public final class DeliveryNote {

  private static final int MAX_CODE_LENGTH = 50;

  private final String documentNumber;
  private final String shipmentNumber;
  private final String senderCode;
  private final String receiverCode;
  private final String loadingLocationCode;
  private final String unloadingLocationCode;
  private final LocalDate issueDate;
  private final List<DeliveryNoteLine> lines;
  private final TemperatureRange requiredTemperatureRange;
  private final Notes notes;

  private DeliveryNote(
      String documentNumber,
      String shipmentNumber,
      String senderCode,
      String receiverCode,
      String loadingLocationCode,
      String unloadingLocationCode,
      LocalDate issueDate,
      List<DeliveryNoteLine> lines,
      TemperatureRange requiredTemperatureRange,
      Notes notes) {
    this.documentNumber = validateCode(documentNumber, "Il numero bolla è obbligatorio.");
    this.shipmentNumber =
        validateCode(shipmentNumber, "Il numero spedizione bolla è obbligatorio.");
    this.senderCode = validateCode(senderCode, "Il codice mittente bolla è obbligatorio.");
    this.receiverCode = validateCode(receiverCode, "Il codice destinatario bolla è obbligatorio.");
    this.loadingLocationCode =
        validateCode(loadingLocationCode, "Il luogo carico bolla è obbligatorio.");
    this.unloadingLocationCode =
        validateCode(unloadingLocationCode, "Il luogo scarico bolla è obbligatorio.");
    if (issueDate == null) {
      throw new IllegalArgumentException("La data bolla è obbligatoria.");
    }
    this.lines = validateLines(lines);
    if (notes == null) {
      throw new IllegalArgumentException("Le note bolla sono obbligatorie.");
    }
    this.issueDate = issueDate;
    this.requiredTemperatureRange = requiredTemperatureRange;
    this.notes = notes;
  }

  public static DeliveryNote of(
      String documentNumber,
      String shipmentNumber,
      String senderCode,
      String receiverCode,
      String loadingLocationCode,
      String unloadingLocationCode,
      LocalDate issueDate,
      List<DeliveryNoteLine> lines,
      TemperatureRange requiredTemperatureRange,
      Notes notes) {
    return new DeliveryNote(
        documentNumber,
        shipmentNumber,
        senderCode,
        receiverCode,
        loadingLocationCode,
        unloadingLocationCode,
        issueDate,
        lines,
        requiredTemperatureRange,
        notes);
  }

  private static String validateCode(String code, String message) {
    if (code == null) {
      throw new IllegalArgumentException(message);
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice bolla non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice bolla può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static List<DeliveryNoteLine> validateLines(List<DeliveryNoteLine> lines) {
    if (lines == null) {
      throw new IllegalArgumentException("Le righe bolla sono obbligatorie.");
    }
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("La bolla deve avere almeno una riga.");
    }
    if (lines.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le righe bolla non possono contenere null.");
    }
    long uniqueCodes = lines.stream().map(DeliveryNoteLine::getLineCode).distinct().count();
    if (uniqueCodes != lines.size()) {
      throw new IllegalArgumentException("La bolla non può avere codici riga duplicati.");
    }
    return List.copyOf(lines);
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public String getShipmentNumber() {
    return shipmentNumber;
  }

  public String getSenderCode() {
    return senderCode;
  }

  public String getReceiverCode() {
    return receiverCode;
  }

  public String getLoadingLocationCode() {
    return loadingLocationCode;
  }

  public String getUnloadingLocationCode() {
    return unloadingLocationCode;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public List<DeliveryNoteLine> getLines() {
    return lines;
  }

  public Optional<TemperatureRange> getRequiredTemperatureRange() {
    return Optional.ofNullable(requiredTemperatureRange);
  }

  public Notes getNotes() {
    return notes;
  }

  public int calculateTotalPackages() {
    return lines.stream().mapToInt(DeliveryNoteLine::getPackagesCount).sum();
  }

  public double calculateTotalGrossWeightKilograms() {
    return lines.stream().mapToDouble(DeliveryNoteLine::getGrossWeightKilograms).sum();
  }

  public int calculateTotalPallets() {
    return lines.stream().mapToInt(DeliveryNoteLine::getPalletCount).sum();
  }

  public boolean requiresTemperatureControl() {
    return requiredTemperatureRange != null;
  }
}
