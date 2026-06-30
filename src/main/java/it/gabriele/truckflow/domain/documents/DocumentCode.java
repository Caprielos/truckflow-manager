package it.gabriele.truckflow.domain.documents;

import it.gabriele.truckflow.domain.documents.exceptions.InvalidDocumentException;

public record DocumentCode(String value) {

  public DocumentCode {
    value = DocumentValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new InvalidDocumentException(
          "Document code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static DocumentCode of(String value) {
    return new DocumentCode(value);
  }
}
