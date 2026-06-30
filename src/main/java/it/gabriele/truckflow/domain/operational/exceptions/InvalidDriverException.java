package it.gabriele.truckflow.domain.operational.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a driver violates domain validation rules. */
public class InvalidDriverException extends DomainValidationException {

  public InvalidDriverException(String message) {
    super(message);
  }
}
