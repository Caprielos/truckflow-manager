package it.gabriele.truckflow.domain.documents.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a document violates domain validation rules. */
public class InvalidDocumentException extends DomainValidationException {

  public InvalidDocumentException(String message) {
    super(message);
  }
}
