package it.gabriele.truckflow.domain.triptemplates.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a trip template violates domain validation rules. */
public class InvalidTripTemplateException extends DomainValidationException {

  public InvalidTripTemplateException(String message) {
    super(message);
  }
}
