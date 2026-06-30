package it.gabriele.truckflow.domain.operational.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a mechanic violates domain validation rules. */
public class InvalidMechanicException extends DomainValidationException {

  public InvalidMechanicException(String message) {
    super(message);
  }
}
