package it.gabriele.truckflow.domain.operational.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a dispatcher violates domain validation rules. */
public class InvalidDispatcherException extends DomainValidationException {

  public InvalidDispatcherException(String message) {
    super(message);
  }
}
