package it.gabriele.truckflow.domain.cargo.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when cargo violates domain validation rules. */
public class InvalidCargoException extends DomainValidationException {

  public InvalidCargoException(String message) {
    super(message);
  }
}
