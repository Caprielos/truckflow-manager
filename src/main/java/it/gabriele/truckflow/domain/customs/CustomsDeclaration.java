package it.gabriele.truckflow.domain.customs;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.List;
import java.util.Objects;

/** Pratica doganale collegata a una spedizione internazionale. */
public final class CustomsDeclaration {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int COUNTRY_CODE_LENGTH = 2;

  private final String declarationCode;
  private final String shipmentCode;
  private final String originCountryCode;
  private final String destinationCountryCode;
  private final List<CustomsDocumentType> requiredDocuments;
  private final Money estimatedCost;
  private final CustomsStatus status;
  private final Notes notes;

  private CustomsDeclaration(
      String declarationCode,
      String shipmentCode,
      String originCountryCode,
      String destinationCountryCode,
      List<CustomsDocumentType> requiredDocuments,
      Money estimatedCost,
      CustomsStatus status,
      Notes notes) {
    this.declarationCode =
        validateCode(declarationCode, "Il codice dichiarazione doganale è obbligatorio.");
    this.shipmentCode = validateCode(shipmentCode, "Il codice spedizione doganale è obbligatorio.");
    this.originCountryCode =
        validateCountryCode(originCountryCode, "Il paese origine è obbligatorio.");
    this.destinationCountryCode =
        validateCountryCode(destinationCountryCode, "Il paese destinazione è obbligatorio.");

    if (originCountryCode.equalsIgnoreCase(destinationCountryCode)) {
      throw new IllegalArgumentException("Origine e destinazione doganale devono essere diverse.");
    }

    if (requiredDocuments == null || requiredDocuments.isEmpty()) {
      throw new IllegalArgumentException("La pratica doganale richiede almeno un documento.");
    }

    if (requiredDocuments.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("I documenti doganali non possono contenere null.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato doganale è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note doganali sono obbligatorie.");
    }

    this.requiredDocuments = List.copyOf(requiredDocuments);
    this.estimatedCost = estimatedCost;
    this.status = status;
    this.notes = notes;
  }

  public static CustomsDeclaration draft(
      String declarationCode,
      String shipmentCode,
      String originCountryCode,
      String destinationCountryCode,
      List<CustomsDocumentType> requiredDocuments,
      Money estimatedCost,
      Notes notes) {
    return new CustomsDeclaration(
        declarationCode,
        shipmentCode,
        originCountryCode,
        destinationCountryCode,
        requiredDocuments,
        estimatedCost,
        CustomsStatus.DRAFT,
        notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static String validateCountryCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (!normalizedCode.matches("[A-Z]{" + COUNTRY_CODE_LENGTH + "}")) {
      throw new IllegalArgumentException("Il codice paese deve essere ISO alpha-2.");
    }

    return normalizedCode;
  }

  public CustomsDeclaration submit() {
    if (!CustomsRules.canBeSubmitted(this)) {
      throw new IllegalStateException("La pratica doganale non può essere inviata.");
    }

    return new CustomsDeclaration(
        declarationCode,
        shipmentCode,
        originCountryCode,
        destinationCountryCode,
        requiredDocuments,
        estimatedCost,
        CustomsStatus.SUBMITTED,
        notes);
  }

  public CustomsDeclaration clear() {
    if (!CustomsRules.canBeCleared(this)) {
      throw new IllegalStateException("La pratica doganale non può essere sdoganata.");
    }

    return new CustomsDeclaration(
        declarationCode,
        shipmentCode,
        originCountryCode,
        destinationCountryCode,
        requiredDocuments,
        estimatedCost,
        CustomsStatus.CLEARED,
        notes);
  }

  public String getDeclarationCode() {
    return declarationCode;
  }

  public String getShipmentCode() {
    return shipmentCode;
  }

  public String getOriginCountryCode() {
    return originCountryCode;
  }

  public String getDestinationCountryCode() {
    return destinationCountryCode;
  }

  public List<CustomsDocumentType> getRequiredDocuments() {
    return requiredDocuments;
  }

  public Money getEstimatedCost() {
    return estimatedCost;
  }

  public CustomsStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isPending() {
    return status.isPending();
  }
}
