package it.gabriele.truckflow.domain.locations.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a location violates domain validation rules. */
public class InvalidLocationException extends DomainValidationException {

  public InvalidLocationException(String message) {
    super(message);
  }
}
