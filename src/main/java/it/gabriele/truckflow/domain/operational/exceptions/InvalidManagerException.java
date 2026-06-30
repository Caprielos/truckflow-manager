package it.gabriele.truckflow.domain.operational.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a manager violates domain validation rules. */
public class InvalidManagerException extends DomainValidationException {

  public InvalidManagerException(String message) {
    super(message);
  }
}
